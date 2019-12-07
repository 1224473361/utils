package com.demo.redis.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.util.StringUtils;

import com.demo.redis.enums.CityLevel;
import com.demo.redis.service.CitysUtil;
import com.demo.redis.service.models.RedisHashService;
import com.demo.redis.vo.CityVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 采用hash方式存储数据
 * 
 * @author lihui
 * @since 2019-11-17
 * @version 1.0
 */
@Slf4j
public class CitysUtilImpl extends RedisHashService implements CitysUtil {

	public CitysUtilImpl(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public List<CityVO> refreshCitys(List<CityVO> list) {
		// 删除已有数据
		this.redisTemplate.delete(KEY_CITYS);
		// 加载新数据
		if (null == list || list.isEmpty()) {
			return Collections.emptyList();
		}
		// 使用LinkedHashMap令map变为有序
		Map<String, Object> seqMap = new LinkedHashMap<>();
		for (CityVO cityVO : list) {
			seqMap.put(cityVO.getId(), cityVO);
		}
		this.hmset(KEY_CITYS, seqMap);
		return list;
	}

	@Override
	public List<CityVO> getAllCitys() {
		if (null == KEY_CITYS) {
			return Collections.emptyList();
		}
		Map<Object, Object> map = this.hmget(KEY_CITYS);
		// 把redis里面的hash数据转为list数据
		return map.entrySet().stream().map(e -> CityVO.mapToBean(e.getValue())).collect(Collectors.toList());
	}

	@Override
	public CityVO getCityByCode(String code) {
		if (null == code || StringUtils.isEmpty(code)) {
			return null;
		}
		Object value = this.hget(KEY_CITYS, code);
		if (null == value) {
			return null;
		}
		return CityVO.mapToBean(value);
	}

	@Override
	public List<CityVO> getDirectSubCitysByParentCode(String code) {
		// 例如:120100可以查找到{120101，120102};140000可以查找到{140100，140200};
		String pattern = code;
		if ("0".equals(code)) {
			// 查询中国下面的所有省
			pattern = "??0000";
		} else if (code.indexOf("0000") != -1) {
			// 查询省下面的所有市
			pattern = pattern.replace("0000", "??00");
		} else if (code.indexOf("00") != -1) {
			// 查询市下面的所有区
			pattern = pattern.replace("00", "??");
		}
		ScanOptions options = ScanOptions.scanOptions().match(pattern).count(1000).build();
		Cursor<Entry<Object, Object>> entries = this.hashOperations.scan(KEY_CITYS, options);
		List<CityVO> resultList = new ArrayList<>();

		while (entries.hasNext()) {
			Entry<Object, Object> entry = entries.next();
			if (!String.valueOf(entry.getKey()).equals(code)) {
				resultList.add(CityVO.mapToBean(entry.getValue()));
			}
		}

		if (!entries.isClosed()) {
			try {
				entries.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return resultList;
	}

	@Override
	public CityVO getParentCityByCode(String code) {
		// 例如:120101可以查找到{120100};140100可以查找到{140000};140000可以查找到{0};
		String key;
		if (code.indexOf("0000") != -1) {
			// 通过省查询中国
			key = "0";
		} else if (code.indexOf("00") != -1) {
			// 通过市查询
			key = code.substring(0, 2) + "0000";
		} else {
			key = code.substring(0, 4) + "00";
		}
		return this.getCityByCode(key);
	}

	@Override
	public List<CityVO> getCitysByLevel(CityLevel level) {
		List<CityVO> list = this.getAllCitys();
		List<CityVO> plist = new ArrayList<>(34);
		List<CityVO> clist = new ArrayList<>(362);
		List<CityVO> alist = new ArrayList<>(2916);
		for (CityVO vo : list) {
			switch (vo.getDistLevel()) {
			case CityVO.DISTLEVEL_1:
				plist.add(vo);
				break;
			case CityVO.DISTLEVEL_2:
				clist.add(vo);
				break;
			case CityVO.DISTLEVEL_3:
				alist.add(vo);
				break;
			default:
				break;
			}
		}
		if (CityLevel.PROVINCE.equals(level)) {
			return plist;
		} else if (CityLevel.CITY.equals(level)) {
			return clist;
		} else if (CityLevel.AREA.equals(level)) {
			return alist;
		} else {
			return Collections.emptyList();
		}
	}
}
