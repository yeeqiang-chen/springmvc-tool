/*
FileName: PoiUtil

Function Description: excel导出工具类

Author: yiqiang-Chen
Date: 2016-11-19 17:10
Version: V1.0
Copyright @ YEE.All rights reserved.
*/

package com.yee.util;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoiUtil {

	private static String role = "^.([0-9]*)";
	private static Pattern pattern = Pattern.compile(role);

	/**
	 * 导出EXCEL文档
	 * @param sheetName
	 *            sheet名称
	 * @param datas
	 *            需要导出的数据表格
	 * @param out
	 *            输出流
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
	 * @throws Exception
	 * @author chenyiqiang
	 * @date 2016年8月18日
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcel(String sheetName, ArrayList<ExcelExportVo> datas, OutputStream out, String pattern) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		HSSFCellStyle styleData = workbook.createCellStyle();
		styleData.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleData.setTopBorderColor(HSSFColor.BLACK.index);
		styleData.setBottomBorderColor(HSSFColor.BLACK.index);
		styleData.setLeftBorderColor(HSSFColor.BLACK.index);
		styleData.setRightBorderColor(HSSFColor.BLACK.index);
		styleData.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleData.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleData.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleData.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleData.setWrapText(true);

		HSSFCellStyle styleHeader = workbook.createCellStyle();
		styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHeader.setTopBorderColor(HSSFColor.BLACK.index);
		styleHeader.setBottomBorderColor(HSSFColor.BLACK.index);
		styleHeader.setLeftBorderColor(HSSFColor.BLACK.index);
		styleHeader.setRightBorderColor(HSSFColor.BLACK.index);
		styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeader.setWrapText(true);

		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		styleHeader.setFont(font);
	      /*
	      // 定义注释的大小和位置,详见文档
	      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
	      // 设置注释内容
	      comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
	      // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
	      comment.setAuthor("lenovo");
	      */
		if (CollectionUtils.isNotEmpty(datas)) {
			int headRowIndex = 0;
			for (int i = 0, size = datas.size(); i < size; i++) {
				// 产生表格标题行
				HSSFRow row = sheet.createRow(headRowIndex);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(new HSSFRichTextString(datas.get(i).getTitle() == null ? "" : datas.get(i).getTitle()));
				calcAndSetRowHeigt(row,datas.get(i).getTitle());
				cell.setCellStyle(styleHeader);

				// 合并单元格(四个参数分别是:起始行,起始列,结束行,结束列)
				CellRangeAddress cellRangeAddress = new CellRangeAddress(headRowIndex, headRowIndex, 0, datas.get(i).getHeaders().length - 1);
				sheet.addMergedRegion(cellRangeAddress);
				setRegionBorder(HSSFCellStyle.BORDER_THIN,cellRangeAddress,sheet,workbook);

				// 产生表格表头行
				row = sheet.createRow(headRowIndex + 1);

				// 一共多少列
				int columnSize = datas.get(i).getHeaders().length;
				for (short j = 0; j < columnSize; j++) {
					cell = row.createCell(j);
					HSSFRichTextString text = new HSSFRichTextString(datas.get(i).getHeaders()[j]);
					cell.setCellValue(text);
					cell.setCellStyle(styleHeader);
				}

				// 遍历集合数据，产生数据行
				Iterator<?> it = datas.get(i).getDataset().iterator();
				int index = headRowIndex + 1;
				while (it.hasNext()) {
					index++;
					row = sheet.createRow(index);
					Object object =  it.next();
					for (short k = 0; k < datas.get(i).getFields().length; k++) {
						cell = row.createCell(k);
						cell.setCellStyle(styleData);
						String field = datas.get(i).getFields()[k];
						String methodName = "get";
						// 系统中有些属性没有严格按照javabean编写规范,例如:cName ---> getcName
						if (isUpperCase(field)) {
							methodName += field.substring(0, 1).toLowerCase()+ field.substring(1);
						} else {
							methodName += field.substring(0, 1).toUpperCase()+ field.substring(1);
						}
						try {
							Method getMethod = datas.get(i).getClazz().getMethod(methodName,new Class[] {});
							Object value = getMethod.invoke(object, new Object[]{});
							// 判断值的类型后进行强制类型转换
							String textValue;
							if (value instanceof Date) {
								Date date = (Date) value;
								SimpleDateFormat sdf = new SimpleDateFormat(pattern);
								textValue = sdf.format(date);
								cell.setCellValue(textValue);
							}  else if (value instanceof byte[]) {
								// 有图片时，设置行高为60px;
								row.setHeightInPoints(60);
								// 设置图片所在列宽度为80px,注意这里单位的一个换算
								sheet.setColumnWidth(index, (short) (35.7 * 80));
								byte[] bsValue = (byte[]) value;
								HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) 6, index, (short) 6, index);
								anchor.setAnchorType(2);
								patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
							} else{
								// 其它数据类型都当作字符串简单处理
								textValue = value == null ? "" : value.toString();
								cell.setCellValue(textValue);
							}
							// 自适应列宽
							for (short m = 0; m < columnSize; m++) {
								sheet.autoSizeColumn(m);
							}
						} catch (Exception e) {
							e.printStackTrace();
							out.close();
							throw e;
						}
					}
				}
				// 获取当前表格行数
				int currentRowNum = sheet.getLastRowNum();
				// 当前行数加一个空白行,继续打印后面的表格
				headRowIndex = currentRowNum + 2;
			}
		}
		// 写出表格
		try {
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.close();
			throw e;
		}
	}

	/**
	 * 设置合并后单元格边框
	 * @param border
	 * @param region
	 * @param sheet
	 * @param wb
	 */
	private static void setRegionBorder(int border, CellRangeAddress region, Sheet sheet,Workbook wb){
		RegionUtil.setBorderBottom(border, region, sheet, wb);
		RegionUtil.setBorderLeft(border,region, sheet, wb);
		RegionUtil.setBorderRight(border,region, sheet, wb);
		RegionUtil.setBorderTop(border,region, sheet, wb);

	}
	/**
	 * 将数字转为字符串
	 * @param value
	 * @return
	 */
	public static String checkNum(String value){
		Matcher isNum = pattern.matcher(value);
		if(isNum.matches()){
			BigDecimal decimal = new BigDecimal(value);
			return decimal.toString();
		}
		return value;
	}

	/**
	 * 判断字符串首字母是否大写
	 * @param word
	 * @return
	 * 			true : 大写
	 * 			false :小写
	 * @author chenyiqiang
	 * @date 2016年11月19日
	 */
	private static boolean isUpperCase(String word) {
		Character firstLetter = word.charAt(0);
		return Character.isUpperCase(firstLetter) ? true : false;
	}

	/**
	 * 根据换行符重新设置行高,有多少个换行符,高度就乘以换行符个数
	 * @param sourceRow
	 * @param cellContent
	 * @author chenyiqiang
	 * @date 2016年11月19日
	 */
	public static void calcAndSetRowHeigt(HSSFRow sourceRow, String cellContent) {
		if (StringUtils.isNotEmpty(cellContent)) {
			short height = sourceRow.getHeight();
			int size = cellContent.split("\n").length;
			sourceRow.setHeight((short) (size*height));
		}
	}
}
