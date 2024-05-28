package com.suisung.shopsuite.common.utils;

import com.suisung.shopsuite.common.consts.ConstantLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: shopsuite
 * @description:
 * @author: 刘超
 * @create: 2022-12-08 14:57
 **/
public class UploadImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadImageUtil.class);

    // 定义一个目标路径，就是我们要把图片上传的位置
    private static final String BASE_PATH = "C:\\opt\\apps\\mall\\public\\static\\media\\plantform";

    // 定义访问图片路径
    private static final String SERVER_PATH = "http://localhost:8081/images/";

    public static String upload(MultipartFile file) {
        // 获取上传图片的名称
        String filename = file.getOriginalFilename();
        // 为了保证图片在服务器中名字的唯一性，使用UUID来对filename进行改写
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 将生成的uuid和filename惊醒拼接
        String newFileName = uuid + '-' + filename;
        // 创建一个文件实例对象
        File image = new File(BASE_PATH, newFileName);
        // 对这个文件进行上传操作
        try {
            file.transferTo(image);
        } catch (IOException e) {
            LogUtil.error(ConstantLog.UPLOAD, e);
        }
        System.out.println(SERVER_PATH);
        return SERVER_PATH + newFileName;
    }

}