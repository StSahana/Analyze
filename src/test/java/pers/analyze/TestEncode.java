package pers.analyze;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import pers.analyze.util.EncodeUtil;
import pers.analyze.util.HexUtil;

public class TestEncode {
	@Test
	public void testEncode() throws UnsupportedEncodingException {
		String a = "中国科学院";
		byte[] s = null;
		System.out.println("Unicode:\t" + EncodeUtil.toUnicode(a));
		System.out.println("UTF8:\t" + EncodeUtil.toUTF8(a));
		System.out.println("Gb2312:\t" + EncodeUtil.toGb2312(a));
		System.out.println("UTF8Url:\t" + EncodeUtil.toUTF8Url(a));
		System.out.println("Gb2312Url:\t" + EncodeUtil.toGb2312Url(a));
		System.out.println("Base64:\t" + EncodeUtil.toBase64(a));
		System.out.println("LowerMD5:\t" + EncodeUtil.toLowerMD5(a));
	}

	@Test
	public void testRefect() {
		String s = null;
		String m = null;
		try {
			s = (String) EncodeUtil.class.getDeclaredMethod("toUnicode", String.class).invoke(null, "你好,world");
			m = (String) EncodeUtil.class.getDeclaredMethod("toUTF8", String.class).invoke(null, "你好,world");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("++++" + s);
		System.out.println("++++" + m);
	}

	@Test
	public void testString() {
		System.out.println(HexUtil.toHexString("-2-179968912504401190111011401080100"));
		System.out.println(HexUtil.toHexString("%C4%E3%BA%C3%2Cworld"));
		String s = "a_b";
		System.out.println(s.split("_")[0]);
		System.out.println(s.split("_")[1]);
	}
}
