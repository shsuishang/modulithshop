package com.suisung.shopsuite.common.consts;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 相关常量定义
 */
@Component
@ToString
public class ConstantUpload {
    /**
     * 文件上传磁盘位置
     */
    public static String DIR;
    public static Integer DIR_INDEX;

    /**
     * 文件上传是否使用uuid命名
     */
    public static boolean UUID_NAME;

    /**
     * 文件上传生成缩略图的大小(kb)
     */
    public static Integer THUMBNAIL_SIZE = 60;

    /**
     * 上传附件访问网址
     */
    public static String WEB_URL;

    /**
     * OpenOffice的安装目录
     */
    public static String OPEN_OFFICE_HOME;

    @Value("${upload.dir}")
    public void setDir(String dir) {
        DIR = dir;
    }


    @Value("${upload.dir-index}")
    public void setDirIndex(Integer index) {
        DIR_INDEX = index;
    }

    @Value("${url.base}")
    public void setWebUrl(String url) {
        WEB_URL = url;
    }

    @Value("${upload.uuid-name}")
    public void setUuidName(boolean uuidName) {
        UUID_NAME = uuidName;
    }

    @Value("${upload.thumbnail-size}")
    public void setThumbnailSize(Integer size) {
        THUMBNAIL_SIZE = size;
    }

    @Value("${upload.open-office-home}")
    public void setOpenOfficeHome(String openoffice) {
        OPEN_OFFICE_HOME = openoffice;
    }
}
