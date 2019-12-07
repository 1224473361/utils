package com.demo.redis.service;

/**
 * redis 【标签】处理公共处理类
 * 
 * @since 2019-11-17
 * @author lihui
 * @version 1.0
 */
public interface TagUtil {

	/**
	 * 获取需求标签JSON字符串
	 * 
	 * @return {@code String}
	 */
	public String getDemandJSON();

	/**
	 * 将需求标签数据刷新到缓存
	 * 
	 * @param json
	 * @return {@code String}
	 */
	public String refreshDemands(String json);

	/**
	 * 获取阿姨标签JSON字符串
	 * 
	 * @return {@code String}
	 */
	public String getAbilitiesJSON();

	/**
	 * 将阿姨标签数据刷新到缓存
	 * 
	 * @param json
	 * @return {@code String}
	 */
	public String refreshAbilities(String json);
}
