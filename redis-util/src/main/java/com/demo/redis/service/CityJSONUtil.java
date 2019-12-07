package com.demo.redis.service;

/**
 * redis 【城市JSON】处理公共处理类
 * 
 * @since 2019-11-17
 * @author lihui
 * @version 1.0
 */
public interface CityJSONUtil {

	/**
	 * 获取城市JSON字符串
	 * 
	 * @return {@code String}
	 */
	public String getCityJSON();

	/**
	 * 将城市JSON字符串新到缓存
	 * 
	 * @param json
	 * @return {@code String}
	 */
	public String refreshCityJSON(String json);

}
