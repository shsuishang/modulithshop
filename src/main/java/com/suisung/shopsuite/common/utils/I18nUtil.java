package com.suisung.shopsuite.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * I18n获取类
 */
@Component
@Slf4j
public class I18nUtil {
    @Resource
    private MessageSource messageSource;

    private static MessageSource staticMessageSource;

    @PostConstruct
    public void init() {
        I18nUtil.staticMessageSource = messageSource;
    }

    /**
     * 通过code 返回对应的提示信息
     *
     * @param code 待翻译内容
     * @return String
     */
    public static String __(String code) {
        return __(code, null);
    }

    /**
     * 返回带参数的提示信息
     *
     * @param code 待翻译内容
     * @param args 参数
     * @return String
     */
    public static String __(String code, Object[] args) {
        return __(code, args, code);
    }

    /**
     * 根据语种查询信息
     *
     * @param code           code
     * @param args           参数
     * @param defaultMessage 默认的提示消息
     * @return String
     */
    public static String __(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.equals(Locale.SIMPLIFIED_CHINESE)) {
            return code;
        }

        String content;
        try {
            content = staticMessageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            //log.info("获取提示消息失败： ->", e);
            content = defaultMessage;
        }

        return content;
    }
}