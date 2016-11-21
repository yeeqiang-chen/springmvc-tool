/*
FileName: DeleteFileUtil

Function Description:

Author: yiqiang-Chen
Date: 2016-11-21 15:34
Version: V1.0
Copyright © YEE.All rights reserved.
*/

package com.yee.util;

import java.io.File;

/**
 * @Description: 地柜删除文件(WINDOWS 平台)
 * @author: chenyiqiang
 * @date: 2016-11-21 15:34
 */
public class DeleteFileUtil {
    //删除文件夹方法
    private void deleteDir(File file) {
        if (file.listFiles().length == 0)
            file.getAbsoluteFile().delete();
    }
    //删除文件方法
    public void deleteFile(File file) {
        File[] temp = file.listFiles();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].isDirectory()) {
                if (temp[i].listFiles().length != 0)
                    //如果文件夹里不为空递归调用 方法
                    this.deleteFile(temp[i]);
                this.deleteDir(temp[i]);
            }
            else {
                temp[i].delete();
            }
        }
    }
}
