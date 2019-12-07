package com.demo.redis.comparator;

import java.util.Comparator;

import com.demo.redis.vo.CodeVo;

/**
 * 
 * @since 2019-11-16
 * @author lihui
 */
public class CodeVoComparator implements Comparator<CodeVo> {

	@Override
	public int compare(CodeVo o1, CodeVo o2) {
		String code1 = o1.getCode();
		String code2 = o2.getCode();
		if (code1.length() == code2.length()) {
			return code1.compareTo(code2);
		}
		return code2.compareTo(code1);
	}

}
