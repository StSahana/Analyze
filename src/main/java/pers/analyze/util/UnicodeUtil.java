package pers.analyze.util;

public class UnicodeUtil {
	/**
	 * 字符串转换unicode
	 */
	public static String stringToUnicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	/**
	 * 你好，world->\\u4f60\\u597d\\u2c\\u77\\u6f\\u72\\u6c\\u64 unicode 转字符串
	 */
	public String unicodeToString(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

}
