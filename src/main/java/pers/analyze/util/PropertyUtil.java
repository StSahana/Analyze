package pers.analyze.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {

	public static Map<String, String> init(String fileName) {
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("src/main/resources/" + fileName);
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) prop.clone();
		prop = null;
		return map;
	}

}
