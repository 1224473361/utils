package com.emotte.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.demo.redis.comparator.CodeVoStringComparator;
import com.demo.redis.vo.CodeVo;

/**
 * 
 * @since 2019-11-17
 * @author lihui
 */
public class ComparatorTest {

	public static void main(String[] args) {
		List<CodeVo> list = new ArrayList<>();
		list.add(new CodeVo("v1", "v1"));
		list.add(new CodeVo("v4", "v1"));
		list.add(new CodeVo("v2", "v1"));
		list.add(new CodeVo("v5", "v1"));
		list.add(new CodeVo("v11", "v1"));
		list.add(new CodeVo("v21", "v1"));
		list.add(new CodeVo("v", "v1"));
		System.out.println("原始数据》》》》》》");
		list.forEach(System.out::println);
		System.out.println("默认排序》》》》》》");
		Collections.sort(list, new CodeVoStringComparator());
		list.forEach(System.out::println);
	}

}
