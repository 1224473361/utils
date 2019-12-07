package com.demo.redis.service.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.demo.redis.comparator.CodeVoStringComparator;
import com.demo.redis.service.AbstractRedisService;
import com.demo.redis.vo.CodeVo;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 采用hash方式存储数据（使用map数据结构）
 * 
 * @since 2019-11-17
 * @author lihui
 */
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class RedisHashService extends AbstractRedisService {
	
	protected HashOperations<String, Object, Object> hashOperations;

	public RedisHashService(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * Hash-获取指定hashKey对应item键值的对象
	 * 
	 * @param key  键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	protected Object hget(String key, String item) {
		return hashOperations.get(key, item);
	}

	/**
	 * Hash-获取指定hashKey对应item键值的对象
	 * 
	 * @param key      键 不能为null
	 * @param hashKeys 不能为null
	 * @return 值
	 */
	protected List<Object> multiGet(String key, Collection<Object> hashKeys) {
		return hashOperations.multiGet(key, hashKeys);
	}

	/**
	 * Hash-获取hashKey对应的所有键值
	 * 
	 * @param key 键
	 * @return 对应的多个键值
	 */
	protected Map<Object, Object> hmget(String key) {
		return hashOperations.entries(key);
	}

	/**
	 * Hash-把map存放到hashKey
	 * 
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	protected boolean hmset(String key, Map<String, Object> map) {
		try {
			hashOperations.putAll(key, map);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Hash-把key,value存放到hashKey
	 * 
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	private boolean hset(String key, String item, Object value) {
		try {
			hashOperations.put(key, item, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	protected List<CodeVo> getList(String key) {
		if (null == key) {
			return Collections.emptyList();
		}
		Map<Object, Object> map = this.hmget(key);
		// 把redis里面的hash数据转为list数据
		return map.entrySet().stream().map(e -> new CodeVo(String.valueOf(e.getKey()), String.valueOf(e.getValue())))
				.collect(Collectors.toList());
	}

	@Override
	protected List<CodeVo> getListAndSortByCode(String key) {
		List<CodeVo> list = this.getList(key);
		// 按照code进行排序
		Collections.sort(list, new CodeVoStringComparator());
		return list;
	}

	@Override
	protected CodeVo getCodeVOByCode(String key, String code) {
		if (null == code || StringUtils.isEmpty(code)) {
			return null;
		}
		Object value = this.hget(key, code);
		if (null == value) {
			return null;
		}
		return new CodeVo(code, String.valueOf(value));
	}

	@Override
	protected CodeVo setCodeVOByCode(String key, CodeVo code) {
		if (null == code) {
			return null;
		}
		this.hset(key, code.getCode(), code.getDesc());
		return code;
	}

	@Override
	protected List<CodeVo> setList(String key, List<CodeVo> list) {
		if (null == list || list.isEmpty()) {
			return Collections.emptyList();
		}
		// 使用LinkedHashMap令map变为有序
		Map<String, Object> seqMap = new LinkedHashMap<>();
		for (CodeVo codeVo : list) {
			seqMap.put(codeVo.getCode(), codeVo.getDesc());
		}
		this.hmset(key, seqMap);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List<CodeVo> getCodeVOByCodes(String key, String... codes) {
		List<Object> codeList = (List) Arrays.asList(codes);
		List<Object> datalist = this.multiGet(key, codeList);
		List<CodeVo> list = new ArrayList<>(datalist.size());
		for (int i = 0; i < codes.length; i++) {
			String code = codes[i];
			list.add(new CodeVo(code, String.valueOf(datalist.get(i))));
		}
		return list;
	}

}
