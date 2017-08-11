package pers.analyze;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import pers.analyze.util.PropertyUtil;

public class TestPropertyUtil {
	@Test
	public void testPro() throws UnsupportedEncodingException {
		// Map<String,String> map =PropertyUtil.init("test.properties");
		// System.out.println(map.get("cc"));
		String s = "你好";
		System.out.println(JSON.toJSONString(s.getBytes("gb2312")));
	}

}
