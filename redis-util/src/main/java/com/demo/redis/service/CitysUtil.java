package com.demo.redis.service;

import java.util.List;

import com.demo.redis.enums.CityLevel;
import com.demo.redis.vo.CityVO;

/**
 * redis 【城市】处理公共处理类
 * 
 * @since 2019-11-17
 * @author lihui
 * @version 1.0
 */
public interface CitysUtil {

	/**
	 * 将城市数据刷新到缓存
	 * 
	 * @param list
	 * @return {@code List<CodeVo>}
	 */
	public List<CityVO> refreshCitys(List<CityVO> list);

	/**
	 * 获取所有城市
	 * 
	 * @return {@code List<CodeVo>}
	 */
	public List<CityVO> getAllCitys();

	/**
	 * 通过code获取城市
	 * 
	 * @param code
	 * @return {@code String}
	 */
	public CityVO getCityByCode(String code);

	/**
	 * 根据code查找其直接子节点<br>
	 * 例如:120100可以查找到{120101，120102};140000可以查找到{140100，140200};
	 * 
	 * @param code
	 * @return
	 */
	public List<CityVO> getDirectSubCitysByParentCode(String code);

	/**
	 * 通过code获取父级城市<br>
	 * 例如:120101可以查找到{120100};140100可以查找到{140000};140000可以查找到{0};
	 * 
	 * @param code
	 * @return {@code String}
	 */
	public CityVO getParentCityByCode(String code);

	/**
	 * 根据级别获取城市列表
	 * 
	 * @param level
	 * @return
	 */
	public List<CityVO> getCitysByLevel(CityLevel level);
}
