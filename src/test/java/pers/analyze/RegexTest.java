package pers.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class RegexTest {
	
	@Test
	public void testRegex(){
		String content = "I am noob " +
		        "from runoob.com.";
		 
		      String pattern = ".*runoob.*";
		 
		      boolean isMatch = Pattern.matches(pattern, content);
//		      System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
		      System.out.println(Pattern.matches("Gb2312、.*|Gb2312|.*、Gb2312|.*Gb2312","Gb2312UX、UTF8"));
		      System.out.println(Pattern.matches("(a*b)", "aaaaab"));
	}
	
//	@Test
	public void testRegex2(){
		// 按指定模式在字符串查找
	      String line = "This order was placed for QT3000! OK?";
	      String pattern = "(\\D*)(\\d+)(.*)";
	 
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern);
	 
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
	         System.out.println("Found value: " + m.group(2) );
	         System.out.println("Found value: " + m.group(3) ); 
	      } else {
	         System.out.println("NO MATCH");
	      }
	}

//	@Test
	public void testString(){
//		String s="x";
//		System.out.println(s.substring(0,s.lastIndexOf("_")));
		 Map<Integer,List<String>> contentRecord=new HashMap<Integer,List<String>>();//需要记录内容的部分
		 contentRecord.put(1,new ArrayList<String>());
		 contentRecord.get(1).add("a");
		 contentRecord.get(1).add("a");
		 System.out.println(JSON.toJSONString(contentRecord));
	}
}
