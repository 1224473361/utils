package com.demo.redis.enums;

/**
 * 城市级别枚举
 * 
 * @date 2019年11月14日
 * @author lihui
 */
public enum CityLevel {

	PROVINCE(1, "省"), CITY(2, "市"), AREA(3, "区");

	private Integer level;
	private String name;

	public Integer getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param level
	 * @param name
	 */
	private CityLevel(Integer level, String name) {
		this.level = level;
		this.name = name;
	}

}
