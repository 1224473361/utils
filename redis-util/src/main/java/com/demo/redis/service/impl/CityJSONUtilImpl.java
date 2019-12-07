package com.demo.redis.service.impl;

import org.springframework.data.redis.core.RedisTemplate;

import com.demo.redis.service.CityJSONUtil;
import com.demo.redis.service.models.RedisStringService;

/**
 * 采用hash方式存储数据
 * 
 * @author lihui
 * @since 2019-11-17
 * @version 1.0
 */
public class CityJSONUtilImpl extends RedisStringService implements CityJSONUtil {

	public CityJSONUtilImpl(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public String getCityJSON() {
		return String.valueOf(this.get(KEY_CITY_JSON));
	}

	@Override
	public String refreshCityJSON(String json) {
		return this.refreshData(KEY_CITY_JSON, json);
	}

}
