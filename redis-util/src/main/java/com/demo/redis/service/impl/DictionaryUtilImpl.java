package com.demo.redis.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.demo.redis.service.DictionaryUtil;
import com.demo.redis.service.models.RedisHashService;
import com.demo.redis.vo.CodeVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 采用hash方式存储数据
 * 
 * @author lihui
 * @since 2019-11-17
 * @version 1.0
 */
@Slf4j
public class DictionaryUtilImpl extends RedisHashService implements DictionaryUtil {

	public DictionaryUtilImpl(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public List<CodeVo> getAllDictionaries() {
		return this.getList(KEY_DICTIONARYS);
	}

	@Override
	public List<CodeVo> getAllDictionariesAndSortByCode() {
		return this.getListAndSortByCode(KEY_DICTIONARYS);
	}

	@Override
	public CodeVo getDictionaryByCode(String code) {
		return this.getCodeVOByCode(KEY_DICTIONARYS, code);
	}

	@Override
	public List<CodeVo> getDictionariesByCodes(String... code) {
		return this.getCodeVOByCodes(KEY_DICTIONARYS, code);
	}

	@Override
	public List<CodeVo> refreshDictionaries(List<CodeVo> list) {
		return this.refreshData(KEY_DICTIONARYS, list);
	}

	@Override
	public List<CodeVo> getSubDictionariesByParentCode(String code) {
		ScanOptions options = ScanOptions.scanOptions().match(code + "*").count(1000).build();
		Cursor<Entry<Object, Object>> entries = this.hashOperations.scan(KEY_DICTIONARYS, options);
		List<CodeVo> resultList = new ArrayList<>();
		while (entries.hasNext()) {
			Entry<Object, Object> entry = entries.next();
			resultList.add(new CodeVo(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
		}
		if (!entries.isClosed()) {
			try {
				entries.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return resultList;
	}

	@Override
	public List<CodeVo> getDirectSubDictionariesByParentCode(String code) {
		ScanOptions options = ScanOptions.scanOptions().match(code + "????").count(1000).build();
		Cursor<Entry<Object, Object>> entries = this.hashOperations.scan(KEY_DICTIONARYS, options);
		List<CodeVo> resultList = new ArrayList<>();
		while (entries.hasNext()) {
			Entry<Object, Object> entry = entries.next();
			resultList.add(new CodeVo(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
		}
		if (!entries.isClosed()) {
			try {
				entries.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return resultList;
	}

}
