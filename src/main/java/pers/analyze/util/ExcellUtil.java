package pers.analyze.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import pers.analyze.Infomation;

public class ExcellUtil {
	private static int rownum = 0;

	public static void WriteToExcell(Map<Integer, Set<String>> map) {
		HSSFWorkbook wb = new HSSFWorkbook();
		// create a new sheet
		HSSFSheet sheet1 = wb.createSheet("匹配结果");
		Map<String, Integer> cellNum = new Infomation().getCellNum();

		// Define a few rows

		map.forEach((k, v) -> {
			rownum++;
			HSSFRow row = sheet1.createRow(rownum);

			v.forEach(colName -> {
				try {
					HSSFCell c1 = row.createCell(cellNum.get(colName));
					c1.setCellValue("√");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("文件写入错误：" + colName + "没有对应列号");
				}
			});

		});
		// Save
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("src/main/resources/匹配结果.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null)
					wb.write(out);
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
