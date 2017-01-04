package com.yee.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReaderExcel {
	FileInputStream fileInputStream = null;

	public void CreateExcel(String filePath) {
		try {
			fileInputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[][] ReaderExcel(String filePath) {
		CreateExcel(filePath);
		try {
			// 创建一个Excel模型
			HSSFWorkbook excelModel = new HSSFWorkbook(fileInputStream);
			// 得到一个excel
			HSSFSheet excel = excelModel.getSheetAt(0);
			// 得到最后一行的索引
			int rowCount = excel.getLastRowNum();
			if(rowCount == 0)
			{
				return null;
			}
			HSSFRow row = excel.getRow(0);
			// 得到Excel表中最后一列的索引
			int cellCount = row.getLastCellNum();
			// 声明一个二维数组用于保存Excel表中的信息包括表头信息
			String[][] str = new String[rowCount+1][cellCount];
			for (int i = 0; i <= rowCount; i++) {
				row = excel.getRow(i);
				// 如果行为空的话，继续循环进入下一行
				if (row == null) {
					break;
				}
				String temp = "";
				HSSFCell cell = null;
				for (int j = 0; j < cellCount; j++) {
					
					// 得到Excel的列
					cell = row.getCell((short) j);
					// 判断Excel列是否为空如果为空直接提取下一个单元格,此时取值为空
					int cellType = 0;
					
					if (cell != null) {
						// 获取Excel列的类型
						cellType = cell.getCellType();
					} else {
						str[i][j] = "";
						continue;
					}
					// 对列列类型进行判断
					switch (cellType) {
					// 数值类型(数字,日期)
					case HSSFCell.CELL_TYPE_NUMERIC:
						// 日期
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							temp = cell.getDateCellValue().toLocaleString();
						} else {
							// 数字
							temp = cell.getNumericCellValue() + "";
						}
						break;
					// 字符串类型
					case HSSFCell.CELL_TYPE_STRING:
						temp = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						temp = cell.getErrorCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						temp ="";
						break;
					default:
						break;
					}
					str[i][j] = temp;
				}
			}
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
