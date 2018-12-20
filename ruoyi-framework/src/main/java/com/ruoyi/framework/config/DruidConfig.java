package com.ruoyi.framework.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.framework.datasource.DynamicDataSource;
import com.ruoyi.framework.datasource.SysDatasource;

/**
 * 数据源配置类
 * 
 * @author wangchl
 *
 */
@Configuration
public class DruidConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.druid.master")
	public DataSource masterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.druid.slave")
	@ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
	public DataSource slaveDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 * 
	 * @param masterDataSource
	 * @param slaveDataSource
	 * @return
	 */
	@Bean(name = "dynamicDataSource")
	@Primary
	public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		// 设置数据源列表
		targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
		// 从数据库中直接读取数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);
		List<SysDatasource> dsList = jdbcTemplate.query("select * from sys_datasource", new Object[] {},
				new BeanPropertyRowMapper<SysDatasource>(SysDatasource.class));
		if (dsList != null) {
			for (SysDatasource ds : dsList) {
				DruidDataSource dds = new DruidDataSource();
				dds.setUrl(ds.getUrl());
				dds.setUsername(ds.getUser());
				dds.setPassword(ds.getPwd());
				dds.setDriverClassName("com.mysql.cj.jdbc.Driver");
				// dds.setDbType(dbType);
				targetDataSources.put(ds.getName(), dds);
			}
		}
		return new DynamicDataSource(masterDataSource, targetDataSources);
	}
}
