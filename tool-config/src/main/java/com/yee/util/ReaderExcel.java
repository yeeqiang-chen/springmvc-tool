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
			// ����һ��Excelģ��
			HSSFWorkbook excelModel = new HSSFWorkbook(fileInputStream);
			// �õ�һ��excel
			HSSFSheet excel = excelModel.getSheetAt(0);
			// �õ����һ�е�����
			int rowCount = excel.getLastRowNum();
			if(rowCount == 0)
			{
				return null;
			}
			HSSFRow row = excel.getRow(0);
			// �õ�Excel�������һ�е�����
			int cellCount = row.getLastCellNum();
			// ����һ����ά�������ڱ���Excel���е���Ϣ������ͷ��Ϣ
			String[][] str = new String[rowCount+1][cellCount];
			for (int i = 0; i <= rowCount; i++) {
				row = excel.getRow(i);
				// �����Ϊ�յĻ�������ѭ��������һ��
				if (row == null) {
					break;
				}
				String temp = "";
				HSSFCell cell = null;
				for (int j = 0; j < cellCount; j++) {
					
					// �õ�Excel����
					cell = row.getCell((short) j);
					// �ж�Excel���Ƿ�Ϊ�����Ϊ��ֱ����ȡ��һ����Ԫ��,��ʱȡֵΪ��
					int cellType = 0;
					
					if (cell != null) {
						// ��ȡExcel�е�����
						cellType = cell.getCellType();
					} else {
						str[i][j] = "";
						continue;
					}
					// ���������ͽ����ж�
					switch (cellType) {
					// ��ֵ����(����,����)
					case HSSFCell.CELL_TYPE_NUMERIC:
						// ����
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							temp = cell.getDateCellValue().toLocaleString();
						} else {
							// ����
							temp = cell.getNumericCellValue() + "";
						}
						break;
					// �ַ�������
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
