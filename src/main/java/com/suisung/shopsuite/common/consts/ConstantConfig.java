package com.suisung.shopsuite.common.consts;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 相关常量定义
 */
@Component
@ToString
public class ConstantConfig {

    public static final Integer MAX_LIST_NUM = 999999;

    public static String URL_BASE;

    public static String URL_STATIC; //涉及静态文件网址

    public static String URL_PC; //移动端PC访问网址

    public static String URL_H5; //移动端H5访问网址

    public static String URL_ADMIN; //管理后台访问网址

    public static String URL_API_ADMIN;

    public static String URL_API_MOBILE;

    public static String STATIC_FILE_PATH;

    public static String MALL_SOCKET_URL;

    @Value("${url.base}")
    public void setUrlBase(String urlBase) {
        URL_BASE = urlBase;
    }

    @Value("${url.pc}")
    public void setUrlPc(String urlPc) {
        URL_PC = urlPc;
    }

    @Value("${url.h5}")
    public void setUrlH5(String urlH5) {
        URL_H5 = urlH5;
    }

    @Value("${url.admin}")
    public void setUrlAdmin(String urlAdmin) {
        URL_ADMIN = urlAdmin;
    }

    @Value("${url.api.manage}")
    public void setUrlApiAdmin(String urlApiAdmin) {
        URL_API_ADMIN = urlApiAdmin;
    }

    @Value("${url.api.front}")
    public void setUrlApiMobile(String urlApiMobile) {
        URL_API_MOBILE = urlApiMobile;
    }

    @Value("${static.file.path}")
    public void setStaticFilePath(String staticFilePath) {
        STATIC_FILE_PATH = staticFilePath;
    }

    @Value("${mall.socket.url}")
    public void setMallSocketUrl(String mallSocketUrl) {
        MALL_SOCKET_URL = mallSocketUrl;
    }
}
