package com.demo.redis.comparator;

import java.util.Comparator;

import com.demo.redis.vo.CodeVo;

/**
 * 使用string自带排序规则
 * 
 * @since 2019-11-17
 * @author lihui
 */
public class CodeVoStringComparator implements Comparator<CodeVo> {

	@Override
	public int compare(CodeVo o1, CodeVo o2) {
		String code1 = o1.getCode();
		String code2 = o2.getCode();
		return code1.compareTo(code2);
	}

}
