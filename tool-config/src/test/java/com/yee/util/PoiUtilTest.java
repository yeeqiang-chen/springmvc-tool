package com.yee.util;

import com.yee.vo.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * PoiUtil Tester.
 *
 * @author <yiqiang-chen>
 * @version 1.0
 * @since <pre>2016-11-19</pre>
 */
public class PoiUtilTest {

    public long startTimeMillis;
    public long endTimeMillis;
    public long costTimeMillis;
    public List<Employee> employeeList;
    public HttpServletResponse response;
    public String sheetName;


    @Before
    public void before() throws Exception {
        startTimeMillis = System.currentTimeMillis();
        Employee e1 = new Employee(1, "YEE1", "0", 21, "汉", "15812332111", "xxxxx1");
        Employee e2 = new Employee(2, "YEE2", "1", 22, "汉", "15812332112", "xxxxx2");
        Employee e3 = new Employee(3, "YEE3", "0", 23, "汉", "15812332113", "xxxxx3");
        Employee e4 = new Employee(4, "YEE4", "1", 24, "汉", "15812332114", "xxxxx4");
        Employee e5 = new Employee(5, "YEE5", "0", 25, "汉", "15812332115", "xxxxx5");
        Employee e6 = new Employee(6, "YEE6", "1", 26, "汉", "15812332116", "xxxxx6");
        employeeList = new ArrayList<Employee>();
        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);
        employeeList.add(e4);
        employeeList.add(e5);
        employeeList.add(e6);
        sheetName = "employee_info";
    }

    @After
    public void after() throws Exception {
        endTimeMillis = System.currentTimeMillis();
        costTimeMillis = endTimeMillis - startTimeMillis;
        System.out.println("Cost : " + costTimeMillis / 1000 + " s");
        employeeList.clear();
    }

    @Test
    public void testExcelExport() {
        ArrayList<ExcelExportVo> exportVoList = new ArrayList<>();
        ExcelExportVo exportVo = new ExcelExportVo();
        exportVo.setTitle("员工信息");
        exportVo.setHeaders(new String[]{"序号", "名称", "性别", "年龄", "名族", "电码号码", "住址"});
        exportVo.setFields(new String[]{"id", "employeeName", "sex", "age", "nationality", "cellPhone", "address"});
        exportVo.setDataset(employeeList);
        exportVo.setClazz(Employee.class);
        exportVoList.add(exportVo);
        exportVoList.add(exportVo);
//        response.setContentType("application/msexcel;charset=utf-8");
//        response.setHeader("Content-disposition", "attachment; filename=" + sheetName + ".xls");
        try {
            FileOutputStream xlsFile = new FileOutputStream("E:\\"+ sheetName + ".xls");
            PoiUtil.exportExcel(sheetName, exportVoList, xlsFile, "yyyy-MM-dd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
