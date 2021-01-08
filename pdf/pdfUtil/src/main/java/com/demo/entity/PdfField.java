package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表单域封装对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfField {

	private Float positionX;
	private Float positionY;
	private Integer page;
	private String fieldKey;
	private String fieldValue;

}
