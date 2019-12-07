package com.demo.redis.vo;

import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 城市对象
 * 
 * @date 2019年12月5日
 * @author lihui
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CityVO {

	/**
	 * 行政级别 0国
	 */
	public static final String DISTLEVEL_0 = "0";
	/**
	 * 行政级别 1省
	 */
	public static final String DISTLEVEL_1 = "1";
	/**
	 * 行政级别 2市
	 */
	public static final String DISTLEVEL_2 = "2";
	/**
	 * 行政级别 3区/县
	 */
	public static final String DISTLEVEL_3 = "3";

	/**
	 * code码
	 */
	private String id;

	/**
	 * 国/省/市/区名
	 */
	private String distName;

	/**
	 * 父级行政区 0：无
	 */
	private String parentId;

	/**
	 * 行政级别 0国 1省 2市 3区/县
	 */
	private String distLevel;

	/**
	 * 首字母
	 */
	private String firstWord;

	/**
	 * 汉语拼音
	 */
	private String allWord;

	/**
	 * 字母简写
	 */
	private String wordAbbreviation;

	/**
	 * 汉语简写
	 */
	private String chineseAbbreviation;

	/**
	 * map转对象
	 * 
	 * @param <T>
	 * @param map
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static CityVO mapToBean(Object value) {
		Map<String, Object> map = (Map<String, Object>) value;
		CityVO city = new CityVO();
		BeanMap beanMap = BeanMap.create(city);
		beanMap.putAll(map);
		return city;
	}

}
