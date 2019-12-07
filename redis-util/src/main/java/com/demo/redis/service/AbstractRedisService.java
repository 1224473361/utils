package com.demo.redis.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;

import com.demo.redis.constant.KeyConstant;
import com.demo.redis.vo.CodeVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抽象基类<br>
 * 用于封装redisTemplate的处理方法
 * 
 * @since 2019-11-17
 * @author lihui
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public abstract class AbstractRedisService extends KeyConstant {

	/**
	 * spring-redis对redis的五种数据类型<br>
	 * HashOperations：对hash类型的数据操作<br>
	 * ValueOperations：对redis字符串类型数据操作<br>
	 * ListOperations：对链表类型的数据操作<br>
	 * SetOperations：对无序集合类型的数据操作<br>
	 * ZSetOperations：对有序集合类型的数据操作<br>
	 */
	protected RedisTemplate<String, Object> redisTemplate;

	/**
	 * 根据key获取其对应的列表数据
	 * 
	 * @param key
	 * @return
	 */
	protected abstract List<CodeVo> getList(String key);

	/**
	 * 根据key获取其对应的列表数据，并按照code正序排序
	 * 
	 * @param key
	 * @return
	 */
	protected abstract List<CodeVo> getListAndSortByCode(String key);

	/**
	 * 根据key获取code键对象的数据
	 * 
	 * @param key
	 * @param code
	 * @return
	 */
	protected abstract CodeVo getCodeVOByCode(String key, String code);

	/**
	 * 根据key获取codes获取多个数据
	 * 
	 * @param key
	 * @param code
	 * @return
	 */
	protected abstract List<CodeVo> getCodeVOByCodes(String key, String... code);

	/**
	 * 根据key保存code键对象
	 * 
	 * @param key
	 * @param code
	 * @return
	 */
	protected abstract CodeVo setCodeVOByCode(String key, CodeVo code);

	/**
	 * 根据key保存数据列表
	 * 
	 * @param key
	 * @param list
	 * @return
	 */
	protected abstract List<CodeVo> setList(String key, List<CodeVo> list);

	/**
	 * 将数据刷新到缓存
	 * 
	 * @param key
	 * @param list
	 * @return
	 */
	protected List<CodeVo> refreshData(String key, List<CodeVo> list) {
		// 删除已有数据
		this.redisTemplate.delete(key);
		// 加载新数据
		this.setList(key, list);
		return list;
	}
}
