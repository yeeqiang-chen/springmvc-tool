/*
FileName: ExcelExportVo

Function Description: excel导出类

Author: yiqiang-Chen
Date: 2016-11-19 17:00
Version: V1.0
Copyright @ YEE.All rights reserved.
*/

package com.yee.util;


import java.util.Collection;

public class ExcelExportVo {

    /**
     * 表格标题名
     */
    private String title;

    /**
     * 导出表格列名数组
     */
    private String[] headers;

    /**
     * 导出实体字段数组
     */
    private String[] fields;

    /**
     * 数据集合
     */
    private Collection<?> dataset;

    /**
     * 需要导出的业务实体类型
     */
    private Class<?> clazz;

    public ExcelExportVo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title.trim();
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public Collection<?> getDataset() {
        return dataset;
    }

    public void setDataset(Collection<?> dataset) {
        this.dataset = dataset;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
