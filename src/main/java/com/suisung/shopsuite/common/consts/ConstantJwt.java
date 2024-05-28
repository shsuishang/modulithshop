package com.suisung.shopsuite.common.consts;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 相关常量定义
 */
@Component
@ToString
public class ConstantJwt {
    /**
     * token过期时间, 单位秒
     */
    public static Long TOKEN_EXPIRETIME = 60 * 60 * 24L;

    /**
     * token快要过期自动刷新时间, 单位分钟
     */
    public static int TOKEN_REFRESHTIME = 30;

    /**
     * 生成token的密钥Key的base64字符
     */
    public static String TOKEN_KEY;

    /**
     * token通过header传递的名称
     */
    public static final String TOKEN_HEADER_NAME = "Authorization";

    /**
     * token通过参数传递的名称
     */
    public static final String TOKEN_PARAM_NAME = "access_token";

    /**
     * token认证类型
     */
    public static final String TOKEN_TYPE = "Bearer";

    /**
     * 登录过期错误码
     */
    public static final int TOKEN_EXPIRED_CODE = 401;

    /**
     * 登录过期提示信息
     */
    public static final String TOKEN_EXPIRED_MSG = "登录已过期";


    @Value("${config.token-key}")
    public void setTokenKey(String tokenKey) {
        TOKEN_KEY = tokenKey;
    }
}
