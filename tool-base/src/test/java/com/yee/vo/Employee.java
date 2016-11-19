/*
FileName: Employee

Function Description:

Author: yiqiang-Chen
Date: 2016-11-19 17:11
Version: V1.0
Copyright © YEE.All rights reserved.
*/

package com.yee.vo;

/**
 * @Description:
 * @author: chenyiqiang
 * @date: 2016-11-19 17:10
 */
public class Employee {
    /**
     * 序号
     */
    private Integer id;

    /**
     * 员工名称
     */
    private String employeeName;

    /**
     * 性别:0-男,1-女
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 手机号码
     */
    private String cellPhone;

    /**
     * 住址
     */
    private String address;

    public Employee() {
    }

    public Employee(Integer id, String employeeName, String sex, Integer age, String nationality, String cellPhone, String address) {
        this.id = id;
        this.employeeName = employeeName;
        this.sex = sex;
        this.age = age;
        this.nationality = nationality;
        this.cellPhone = cellPhone;
        this.address = address;
    }

    public Integer getSeq() {
        return id;
    }

    public void setSeq(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
