package pers.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Style;

import org.apache.poi.ss.formula.functions.Value;

import com.alibaba.fastjson.JSON;

import pers.analyze.util.EncodeUtil;
import pers.analyze.util.PropertyUtil;

/**
 * Created by StSahana on 2017/8/8.
 */
public class Infomation {
	private Map<String, String[]> chineseInfo;
	private Map<String, String[]> englishInfo;
	private Map<String, Integer> cellNum;
	private Map<String, String> regex;
	private final String[] chineseEncodeStyle = { "Unicode", "UTF8", "UTF8Url", "Gb2312Url", "Gb2312", "LowerMD5",
			"UpperMD5", "Base64" };
	private final String[] englishEncodeStyle = { "Unicode", "Base64", "LowerMD5", "UpperMD5" };

	{
		// 加载中文信息
		chineseInfo = new HashMap<String, String[]>();
		Map<String, String> chineseMap = PropertyUtil.init("chineseInfo.properties");
		chineseMap.forEach((k, v) -> {
			if (!v.equals("")) {
				chineseInfo.put(k, v.split(","));
			}
		});

		// 加载英文信息
		englishInfo = new HashMap<String, String[]>();
		Map<String, String> englishMap = PropertyUtil.init("englishInfo.properties");
		englishMap.forEach((k, v) -> {
			if (!v.equals("")) {
				englishInfo.put(k, v.split(","));
			}
		});

		// 加载列号
		cellNum = new HashMap<String, Integer>();
		Map<String, String> cellMap = PropertyUtil.init("cellNumInfo.properties");
		cellMap.forEach((k, v) -> {
			if (!v.equals("")) {
				cellNum.put(k, Integer.parseInt(v));
			}
		});
		
		//加载需要做正则匹配的字段
		regex= PropertyUtil.init("regexInfo.properties");

		// 对中文编码
		Map<String, String[]> tempChineseInfo = new HashMap<String, String[]>();
		chineseInfo.forEach((key, value) -> {
			String[] o = null;
			for (String style : chineseEncodeStyle) {
				o = new String[value.length];
				for (int i = 0; i < value.length; i++) {
					try {
						o[i] = (String) EncodeUtil.class.getDeclaredMethod("to" + style, String.class).invoke(null,
								value[i]);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
				tempChineseInfo.put(key + "_" + style, o);
			}
		});
		chineseInfo.putAll(tempChineseInfo);

		// 对英文编码
		Map<String, String[]> tempEnglisInfo = new HashMap<String, String[]>();
		englishInfo.forEach((key, value) -> {
			String[] o = new String[value.length];
			for (String style : englishEncodeStyle) {
				for (int i = 0; i < value.length; i++) {
					try {
						o[i] = (String) EncodeUtil.class.getDeclaredMethod("to" + style, String.class).invoke(null,
								value[i]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				tempEnglisInfo.put(key + "_" + style, o);
			}
		});
		englishInfo.putAll(tempEnglisInfo);

		/*
		 * System.out.println(JSON.toJSONString(chineseInfo));
		 * System.out.println(JSON.toJSONString(englishInfo));
		 * System.out.println(chineseInfo.size());
		 * System.out.println(englishInfo.size());
		 */
	}

	public Map<String, Integer> getCellNum() {
		return cellNum;
	}

	public Map<String, String[]> getChineseInfo() {
		return chineseInfo;
	}

	public Map<String, String[]> getEnglishInfo() {
		return englishInfo;
	}

}
