/*
FileName: ExcelExportVo

Function Description: excel������

Author: yiqiang-Chen
Date: 2016-11-19 17:00
Version: V1.0
Copyright @ YEE.All rights reserved.
*/

package com.yee.util;


import java.util.Collection;

public class ExcelExportVo {

    /**
     * ��������
     */
    private String title;

    /**
     * ���������������
     */
    private String[] headers;

    /**
     * ����ʵ���ֶ�����
     */
    private String[] fields;

    /**
     * ���ݼ���
     */
    private Collection<?> dataset;

    /**
     * ��Ҫ������ҵ��ʵ������
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
