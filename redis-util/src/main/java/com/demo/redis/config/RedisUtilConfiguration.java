package com.demo.redis.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.demo.redis.service.CityJSONUtil;
import com.demo.redis.service.CitysUtil;
import com.demo.redis.service.DictionaryUtil;
import com.demo.redis.service.TagUtil;
import com.demo.redis.service.impl.CityJSONUtilImpl;
import com.demo.redis.service.impl.CitysUtilImpl;
import com.demo.redis.service.impl.DictionaryUtilImpl;
import com.demo.redis.service.impl.TagUtilImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * redis工具类加载配置文件
 * 
 * @since 2019-11-17
 * @author lihui
 */
@Configuration
@ComponentScan(basePackages = { "com.emotte.redis.util" })
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Slf4j
public class RedisUtilConfiguration {

	/**
	 * 当上下文没有redisTemplate时，自己创建
	 * 
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisUtilTemplate(LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();

		// 配置连接工厂
		template.setConnectionFactory(redisConnectionFactory);

		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);

		ObjectMapper om = new ObjectMapper();
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		om.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		jacksonSeial.setObjectMapper(om);

		// 值采用json序列化
		template.setValueSerializer(jacksonSeial);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());

		// 设置hash key 和value序列化模式
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jacksonSeial);
		template.afterPropertiesSet();

		// false:此时redis不支持事物。RedisTemplate会在使用完了之后自动释放连接；
		// true:此时redis支持事物。RedisTemplate是不会主动释放连接的，需要手动释放连接
		template.setEnableTransactionSupport(false);
		log.info("加载redisUtilTemplate");
		return template;
	}

	/**
	 * 注入工具类
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CityJSONUtil getCityJSONUtil(RedisTemplate<String, Object> redisTemplate) {
		// 默认时hash存储方式
		return new CityJSONUtilImpl(redisTemplate);
	}

	/**
	 * 注入工具类
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public DictionaryUtil getDictionaryUtil(RedisTemplate<String, Object> redisTemplate) {
		// 默认时hash存储方式
		return new DictionaryUtilImpl(redisTemplate);
	}

	/**
	 * 注入工具类
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public TagUtil getTagUtil(RedisTemplate<String, Object> redisTemplate) {
		// 默认时hash存储方式
		return new TagUtilImpl(redisTemplate);
	}

	/**
	 * 注入工具类
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CitysUtil getCitysUtil(RedisTemplate<String, Object> redisTemplate) {
		// 默认时hash存储方式
		return new CitysUtilImpl(redisTemplate);
	}

}
