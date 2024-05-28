package com.suisung.shopsuite.common.weblog;

/**
 * <p>此处填写功能描述</p>
 * <p>修改历史：<ul>2021年09月28日 - 创建</ul></p>
 *
 * @author 尹锴
 */
public class RequestNoContext {
    /**
     * 接口调用进程代码
     */
    public static ThreadLocal<Long> currentThreadId = new ThreadLocal<>();

    /**
     * 调用接口路径
     */
    public static ThreadLocal<String> apiUrl = new ThreadLocal<>();

    /**
     * 请求开始时间戳
     */
    public static ThreadLocal<Long> callTime = new ThreadLocal<>();

    /**
     * 请求参数
     */
    public static ThreadLocal<String> requestParam = new ThreadLocal<>();

    /**
     * 清除ThreadLocal变量
     */
    public static void remove() {
        currentThreadId.remove();
        apiUrl.remove();
        callTime.remove();
        requestParam.remove();
    }
}
