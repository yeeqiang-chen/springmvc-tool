/*
FileName: PoiUtil

Function Description: excel����������

Author: yiqiang-Chen
Date: 2016-11-19 17:10
Version: V1.0
Copyright @ YEE.All rights reserved.
*/

package com.yee.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

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
	 * ����EXCEL�ĵ�
	 * @param sheetName
	 *            sheet����
	 * @param datas
	 *            ��Ҫ���������ݱ��
	 * @param out
	 *            �����
	 * @param pattern
	 *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyyy-MM-dd"
	 * @throws Exception
	 * @author chenyiqiang(004205)
	 * @date 2016��8��18��
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcel(String sheetName, ArrayList<ExcelExportVo> datas, OutputStream out, String pattern) throws Exception {
		// ����һ��������
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ����һ�����
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// ����һ����ͼ�Ķ���������
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// ���ñ��Ĭ���п��Ϊ15���ֽ�
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
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// �Ӵ�
		styleHeader.setFont(font);
	      /*
	      // ����ע�͵Ĵ�С��λ��,����ĵ�
	      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
	      // ����ע������
	      comment.setString(new HSSFRichTextString("������POI�����ע�ͣ�"));
	      // ����ע�����ߣ�������ƶ�����Ԫ�����ǿ�����״̬���п���������.
	      comment.setAuthor("lenovo");
	      */
		if (CollectionUtils.isNotEmpty(datas)) {
			int headRowIndex = 0;
			for (int i = 0, size = datas.size(); i < size; i++) {
				// ������������
				HSSFRow row = sheet.createRow(headRowIndex);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(new HSSFRichTextString(datas.get(i).getTitle() == null ? "" : datas.get(i).getTitle()));
				calcAndSetRowHeigt(row,datas.get(i).getTitle());
				cell.setCellStyle(styleHeader);

                // �ϲ���Ԫ��(�ĸ������ֱ���:��ʼ��,��ʼ��,������,������)
				sheet.addMergedRegion(new CellRangeAddress(headRowIndex, headRowIndex, 0, datas.get(i).getHeaders().length - 1));

				// ��������ͷ��
				row = sheet.createRow(headRowIndex + 1);

				// һ��������
				int columnSize = datas.get(i).getHeaders().length;
				for (short j = 0; j < columnSize; j++) {
					cell = row.createCell(j);
					HSSFRichTextString text = new HSSFRichTextString(datas.get(i).getHeaders()[j]);
					cell.setCellValue(text);
					cell.setCellStyle(styleHeader);
				}

				// �����������ݣ�����������
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
						// ϵͳ����Щ����û���ϸ���javabean��д�淶,����:cName ---> getcName
						if (isUpperCase(field)) {
							methodName += field.substring(0, 1).toLowerCase()+ field.substring(1);
						} else {
							methodName += field.substring(0, 1).toUpperCase()+ field.substring(1);
						}
						try {
							Method getMethod = datas.get(i).getClazz().getMethod(methodName,new Class[] {});
							Object value = getMethod.invoke(object, new Object[]{});
							// �ж�ֵ�����ͺ����ǿ������ת��
							String textValue;
							if (value instanceof Date) {
								Date date = (Date) value;
								SimpleDateFormat sdf = new SimpleDateFormat(pattern);
								textValue = sdf.format(date);
								cell.setCellValue(textValue);
							}  else if (value instanceof byte[]) {
								// ��ͼƬʱ�������и�Ϊ60px;
								row.setHeightInPoints(60);
								// ����ͼƬ�����п��Ϊ80px,ע�����ﵥλ��һ������
								sheet.setColumnWidth(index, (short) (35.7 * 80));
								byte[] bsValue = (byte[]) value;
								HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) 6, index, (short) 6, index);
								anchor.setAnchorType(2);
								patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
							} else{
								// �����������Ͷ������ַ����򵥴���
								textValue = value == null ? "" : value.toString();
								cell.setCellValue(textValue);
							}
							// ����Ӧ�п�
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
				// ��ȡ��ǰ�������
				int currentRowNum = sheet.getLastRowNum();
				// ��ǰ������һ���հ���,������ӡ����ı��
				headRowIndex = currentRowNum + 1;
			}
		}
		// д�����
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
	 * ������תΪ�ַ���
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
	 * �ж��ַ�������ĸ�Ƿ��д
	 * @param word
	 * @return
	 * 			true : ��д
	 * 			false :Сд
	 * @author chenyiqiang
	 * @date 2016��11��19��
	 */
	private static boolean isUpperCase(String word) {
		Character firstLetter = word.charAt(0);
		return Character.isUpperCase(firstLetter) ? true : false;
	}

	/**
	 * ���ݻ��з����������и�,�ж��ٸ����з�,�߶Ⱦͳ��Ի��з�����
	 * @param sourceRow
	 * @param cellContent
	 * @author chenyiqiang
	 * @date 2016��11��19��
	 */
	public static void calcAndSetRowHeigt(HSSFRow sourceRow, String cellContent) {
		if (StringUtils.isNotEmpty(cellContent)) {
			short height = sourceRow.getHeight();
			int size = cellContent.split("\n").length;
			sourceRow.setHeight((short) (size*height));
		}
	}
}
