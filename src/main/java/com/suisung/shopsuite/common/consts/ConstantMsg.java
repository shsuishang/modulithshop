package com.suisung.shopsuite.common.consts;

/**
 * 系统响应常量
 * Created by Xinze on 2019-10-29 15:55
 */
public class ConstantMsg {
    /**
     * 业务误码
     */
    public static final int CODE_BUSINESS_VALIDATION_FAILED = 300;

    /**
     * 无权限错误码
     */
    public static final int UNAUTHORIZED_CODE = 403;

    /**
     * 无权限提示信息
     */
    public static final String UNAUTHORIZED_MSG = "没有访问权限";

    /**
     * 未认证错误码
     */
    public static final int UNAUTHENTICATED_CODE = 401;

    /**
     * 未认证提示信息
     */
    public static final String UNAUTHENTICATED_MSG = "请先登录";


    /**
     * 非法token错误码
     */
    public static final int BAD_CREDENTIALS_CODE = 401;

    /**
     * 非法token提示信息
     */
    public static final String BAD_CREDENTIALS_MSG = "请退出重新登录";

}
