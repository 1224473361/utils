package com.demo.redis.service.models;

import java.util.Collections;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.demo.redis.service.AbstractRedisService;
import com.demo.redis.vo.CodeVo;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 采用String方式存储数据
 * 
 * @since 2019-11-17
 * @author lihui
 */
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class RedisStringService extends AbstractRedisService {

	private ValueOperations<String, Object> opsForValue;

	public RedisStringService(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
		opsForValue = redisTemplate.opsForValue();
	}

	/**
	 * string-获取缓存
	 * 
	 * @param key 键
	 * @return 值
	 */
	protected Object get(String key) {
		return opsForValue.get(key);
	}

	/**
	 * string-添加缓存
	 * 
	 * @param key   键
	 * @param value 值
	 * @return true成功 false失败
	 */
	protected boolean set(String key, Object value) {
		try {
			opsForValue.set(key, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CodeVo> getList(String key) {
		Object obj = this.get(key);
		if (null == obj) {
			return Collections.emptyList();
		}
		return (List<CodeVo>) obj;
	}

	@Override
	protected List<CodeVo> getListAndSortByCode(String key) {
		return null;
	}

	@Override
	protected CodeVo getCodeVOByCode(String key, String code) {
		if (null == code) {
			return null;
		}
		return new CodeVo(code, String.valueOf(this.get(code)));
	}

	@Override
	protected CodeVo setCodeVOByCode(String key, CodeVo code) {
		return null;
	}

	@Override
	protected List<CodeVo> setList(String key, List<CodeVo> list) {
		return null;
	}

	@Override
	protected List<CodeVo> getCodeVOByCodes(String key, String... code) {
		return null;
	}

	/**
	 * 将数据刷新到缓存
	 * 
	 * @param key
	 * @param json
	 * @return
	 */
	protected String refreshData(String key, String json) {
		// 删除已有数据
		this.redisTemplate.delete(key);
		// 加载新数据
		this.set(key, json);
		return json;
	}

}
