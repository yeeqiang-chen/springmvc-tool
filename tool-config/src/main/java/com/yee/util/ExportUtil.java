package com.yee.util;

import com.util.PropertiesUtil;
import com.yee.vo.common.ExportVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportUtil {
	
	public static ExportVo setExportData(List<Map<String,String>> dataList,String sheetTitle,String headers,String methods){
		//获取导出字段
		com.util.PropertiesUtil.setPropertiesName("system.properties");
		//定义第一张sheet的内容
		ExportVo  sheetVo =new ExportVo();
		String columnsString = com.util.PropertiesUtil.getProperty(headers);
		//数据列
		String[] strColumns = columnsString.split(",");
		sheetVo.setSheetTitle(sheetTitle);
		sheetVo.setRowheadList(strColumns);

		List<List<String>> sheetdataList = new ArrayList<List<String>>();
		String methodGetter = PropertiesUtil.getProperty(methods);
		//数据列
		String[] strMethods = methodGetter.split(",");
		for(Map<String,String> map:dataList){
			List<String> sheetColList = new ArrayList<String>();
			for(String method:strMethods){
				sheetColList.add(map.get(method));
			}
			sheetdataList.add(sheetColList);
		}
		sheetVo.setContentDataList(sheetdataList);
		return sheetVo;
	}
}
