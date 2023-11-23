package com.example.demo2.file_upload;

import com.example.demo2.database_picture.Picture;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     *
     * @param file 文件
     * @param sourcepath 文件存放路径
     * @param new_filename 源文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String sourcepath, String thpath,String new_filename){


        // 原图片路径
        String realPath = sourcepath + "/" + new_filename;
        //缩略图路径
        String falsePath = thpath + "/" + new_filename;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);

            //保存缩略图
            Thumbnails.of(dest).size(200,200).toFile(falsePath);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }
}