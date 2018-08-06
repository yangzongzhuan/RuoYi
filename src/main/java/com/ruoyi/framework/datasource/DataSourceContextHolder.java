package com.ruoyi.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当前线程数据源
 * 
 * @author ruoyi
 */
public class DataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType)
    {
        log.info("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB()
    {
        return contextHolder.get();
    }

    public static void clearDB()
    {
        contextHolder.remove();
    }

}
