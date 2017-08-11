package pers.analyze;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
	
	@Test
	public void testRegex(){
		String content = "I am noob " +
		        "from runoob.com.";
		 
		      String pattern = ".*runoob.*";
		 
		      boolean isMatch = Pattern.matches(pattern, content);
		      System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
	}
	
	@Test
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

}
