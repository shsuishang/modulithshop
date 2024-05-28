package com.suisung.shopsuite.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class RequestUtil {
    @Resource
    private HttpServletRequest servletRequest;

    private static HttpServletRequest staticServletRequest;

    @PostConstruct
    public void init() {
        RequestUtil.staticServletRequest = servletRequest;
    }

    /**
     * 根据key 从 request 中获取值
     *
     * @param key
     * @return
     */
    public static String getParameter(String key) {
        String return_param = null;
        try {
            return_param = StrUtil.isBlank(key) ? null : staticServletRequest.getParameter(key);
        } catch (Exception e) {

        }

        return return_param;
    }

    /**
     * 根据key 从 request 中获取值
     *
     * @param key
     * @param type 想要值的类型
     * @param <T>
     * @return
     */
    public static <T> T getParameter(String key, Class<T> type) {
        return Convert.convert(type, getParameter(key));
    }

    /**
     * 根据key 从 request 中获取值
     *
     * @param key
     * @param defaultValue 默认值
     * @param <T>
     * @return
     */
    public static <T> T getParameter(String key, T defaultValue) {
        String value = getParameter(key);
        if (StrUtil.isBlank(value)) return defaultValue;
        if (ObjectUtil.isEmpty(defaultValue)) {
            return (T) value;
        }
        return Convert.convert((Class<T>) defaultValue.getClass(), value);
    }
}
