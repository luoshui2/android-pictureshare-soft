package com.example.demo2.file_upload;

import java.util.UUID;

/**
 * 生成文件名
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}