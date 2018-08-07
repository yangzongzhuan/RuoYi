package com.ruoyi.project.tool.redis;


import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Redis配置类
 * @author wangchl  
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	 @Bean
	   public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
	      RedisTemplate<Object, Object> template = new RedisTemplate<>();
	      template.setConnectionFactory(connectionFactory);
	 
	      //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
	      Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
	 
	      ObjectMapper mapper = new ObjectMapper();
	      mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	      serializer.setObjectMapper(mapper);
	 
	      template.setValueSerializer(serializer);
	      //使用StringRedisSerializer来序列化和反序列化redis的key值
	      template.setKeySerializer(new StringRedisSerializer());
	      template.afterPropertiesSet();
	      return template;
	   }

}