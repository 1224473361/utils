package com.demo.redis.service.impl;

import org.springframework.data.redis.core.RedisTemplate;

import com.demo.redis.service.TagUtil;
import com.demo.redis.service.models.RedisStringService;

/**
 * 采用hash方式存储数据
 * 
 * @author lihui
 * @since 2019-11-17
 * @version 1.0
 */
public class TagUtilImpl extends RedisStringService implements TagUtil {

	public TagUtilImpl(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public String getDemandJSON() {
		return String.valueOf(this.get(KEY_TAG_DEMANDS));
	}

	@Override
	public String refreshDemands(String json) {
		return this.refreshData(KEY_TAG_DEMANDS, json);
	}

	@Override
	public String getAbilitiesJSON() {
		return String.valueOf(this.get(KEY_TAG_ABILITIES));
	}

	@Override
	public String refreshAbilities(String json) {
		return this.refreshData(KEY_TAG_ABILITIES, json);
	}

}
