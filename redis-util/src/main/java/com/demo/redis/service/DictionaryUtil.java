package com.demo.redis.service;

import java.util.List;

import com.demo.redis.vo.CodeVo;

/**
 * redis 【码表】处理公共处理类
 * 
 * @since 2019-11-17
 * @author lihui
 * @version 1.0
 */
public interface DictionaryUtil {

	/**
	 * 获取所有码表
	 * 
	 * @return {@code List<CodeVo>}
	 */
	public List<CodeVo> getAllDictionaries();

	/**
	 * 获取所有码表,数据按照code正序排列
	 * 
	 * @return {@code List<CodeVo>}
	 */
	public List<CodeVo> getAllDictionariesAndSortByCode();

	/**
	 * 通过code获取码表
	 * 
	 * @param code
	 * @return {@code String}
	 */
	public CodeVo getDictionaryByCode(String code);

	/**
	 * 通过多个code获取码表
	 * 
	 * @param code
	 * @return {@code String}
	 */
	public List<CodeVo> getDictionariesByCodes(String... code);

	/**
	 * 将数据刷新到缓存
	 * 
	 * @param list
	 * @return {@code List<CodeVo>}
	 */
	public List<CodeVo> refreshDictionaries(List<CodeVo> list);

	/**
	 * 根据code查找其直接子节点<br>
	 * 例如:20040005可以查找到{200400050005，200400050002};2004可以查找到{20040001，20040005};
	 * 
	 * @param code
	 * @return
	 */
	public List<CodeVo> getDirectSubDictionariesByParentCode(String code);

	/**
	 * 根据code查找其所有子节点<br>
	 * 例如:2004可以查找到{20040005，200400050005};
	 * 
	 * @param code
	 * @return
	 */
	public List<CodeVo> getSubDictionariesByParentCode(String code);

}
