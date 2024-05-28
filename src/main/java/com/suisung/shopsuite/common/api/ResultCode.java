package com.suisung.shopsuite.common.api;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(0, 200, "操作成功！", "success"),
    FAILED(0, 250, "操作失败！", "failed"),
    VALIDATE_FAILED(0, 250, "参数检验失败！", "failed"),
    UNAUTHORIZED(30, 250, "暂未登录或token已经过期！", "failed"),
    FORBIDDEN(0, 250, "没有相关权限！", "failed"),
    NOT_IS_PLATFORM(0, 250, "非平台管理员账号，没有相关权限！", "failed"),
    NOT_IS_STORE(0, 250, "非店铺管理员账号，没有相关权限！", "failed"),
    NOT_IS_CHAIN(0, 250, "非门店管理员账号，没有相关权限！", "failed"),
    NOT_IS_SUBSITE(0, 250, "非分站管理员账号，没有相关权限！", "failed"),
    ORDER_FORBIDDEN(0, 250, "无该订单访问权限！", "failed"),
    NEED_LOGIN(30, 250, "需要登录！", "failed"),
    USERPWD_FAILED(1005, 250, "用户名或密码错误", "failed"),
    VERIFYCODE_FAILED(1003, 250, "验证码错误", "failed"),
    REMOTE_CALL_FAILED(0, 250, "远程调用异常！", "failed"),
    PAY_PWD_FAILED(101, 250, "未设置支付密码！", "failed"),
    SERVICE_UNAVAILABLE(0, 503, "服务不可用，请稍后再试。", "Service Unavailable"),
    TOO_MANY_REQUESTS(0, 429, "请求过于频繁，请稍后再试。", "Too Many Requests");

    /*
    __("操作成功！")
    __("操作失败！")
    __("参数检验失败！")
    __("暂未登录或token已经过期！")
    __("没有相关权限！")
    __("无该订单访问权限！")
    __("需要登录！")
    __("用户名或密码错误")
    __("验证码错误")
    __("远程调用异常！")
    __("未设置支付密码！")
    */
    private final long code;
    private long status;
    private final String message;
    private String msg;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultCode(long code, long status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    ResultCode(long code, String message, String msg) {
        this.code = code;
        this.message = message;
        this.msg = msg;
    }

    ResultCode(long code, long status, String message, String msg) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.msg = msg;
    }

    public long getCode() {
        return code;
    }

    public long getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMsg() {
        return msg;
    }
}
