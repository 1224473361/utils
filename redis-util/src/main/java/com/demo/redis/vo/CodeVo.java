package com.demo.redis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @since 2019-11-17
 * @author lihui
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CodeVo {

	/**
	 * code码
	 */
	private String code;

	/**
	 * 描述
	 */
	private String desc;

}
