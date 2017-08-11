package pers.analyze.util;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.poi.hssf.record.common.UnicodeString;

import com.alibaba.fastjson.JSON;

public class EncodeUtil {

	public static String byteToString(byte[] input) {
		String result = "";
		for (byte b : input) {
			result += b;
		}
		return result;

		// return JSON.toJSONString(input);
	}

	public static String toUnicode(String str) {
		// return byteToString(str.getBytes(Charset.forName("unicode")));
		return byteToString(str.getBytes(Charset.forName("unicode")));
	}

	public static String toUTF8(String str) {
		return byteToString(str.getBytes(Charset.forName("UTF-8")));
	}

	public static String toUTF8Url(String str) throws UnsupportedEncodingException {
		return byteToString(URLEncoder.encode(str, "UTF-8").getBytes());
		// return URLEncoder.encode(str,"UTF-8");
	}

	public static String toGb2312Url(String str) throws UnsupportedEncodingException {
		return byteToString(URLEncoder.encode(str, "gb2312").getBytes());
		// return URLEncoder.encode(str,"gb2312");
	}

	public static String toGb2312(String str) {
		return byteToString(str.getBytes(Charset.forName("gb2312")));
	}

	public static String toLowerMD5(String str) {
		return byteToString(MD5Util.MD5(str).toLowerCase().getBytes());
		// return MD5Util.MD5(str).toLowerCase();
	}

	public static String toUpperMD5(String str) {
		return byteToString(MD5Util.MD5(str).toUpperCase().getBytes());
		// return MD5Util.MD5(str).toUpperCase();
	}

	public static String toBase64(String str) {
		// return new
		// String(Base64.getEncoder().encode(str.getBytes(Charset.forName("UTF-8"))));
		return byteToString(Base64.getEncoder().encode(str.getBytes(Charset.forName("UTF-8"))));
	}

}
