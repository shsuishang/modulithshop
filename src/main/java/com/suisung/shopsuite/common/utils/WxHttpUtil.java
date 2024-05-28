package com.suisung.shopsuite.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.suisung.shopsuite.account.service.WechatService;
import com.suisung.shopsuite.common.exception.BusinessException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * Author: Lwg
 * Description: WeChat request tool
 */
@Component
public class WxHttpUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static WechatService accountService;

    private static final int MAX_RETRIES = 3;       // 最大重试次数

    private static final int RETRY_DELAY_MS = 1000;    // 重试延迟毫秒数

    /**
     * 封装统一请求，获取token失败后触发重试机制
     *
     * @param methodType 请求方式 (ENUM):GET; POST;
     * @param wxType     微信类型 (ENUM):MP-公众号; XCX-小程序;
     * @param token      微信 AccessToken
     * @param url        请求地址
     * @param params     请求参数 接收Map类型
     * @param params     请求参数 接收字符串类型
     * @return
     */
    public static String request(MethodType methodType, WxType wxType, String token, String url, Map<String, Object> params, String paramStr) {
        accountService = applicationContext.getBean(WechatService.class);
        int retries = 0;
        String result = null;
        while (retries < MAX_RETRIES) {
            try {
                if (methodType.equals(MethodType.GET)) {
                    result = HttpUtil.get(url);
                } else if (methodType.equals(MethodType.POST)) {

                    if (StrUtil.isNotEmpty(paramStr) && CollUtil.isNotEmpty(params)) {
                        throw new BusinessException(__("传递参数有误！"));
                    }

                    if (StrUtil.isNotEmpty(paramStr)) {
                        result = HttpUtil.post(url, paramStr);
                    } else if (CollUtil.isNotEmpty(params)) {
                        result = HttpUtil.post(url, params);
                    }
                }
                JSONObject jsonObject = JSONUtil.parseObj(result);
                Integer errcode = Convert.toInt(jsonObject.get("errcode"));
                if (errcode.equals(40001)) {
                    String tempToken = null;
                    if (wxType.equals(WxType.MP)) {
                        tempToken = accountService.getMpAccessToken(false);
                    } else if (wxType.equals(WxType.XCX)) {
                        tempToken = accountService.getXcxAccessToken(false);
                    }
                    url = url.replace(token, tempToken);
                    token = tempToken;
                    retries++;
                    continue;
                }
                break;
            } catch (Exception e) {
                retries++;
                if (retries == MAX_RETRIES) {
                    // 最后一次重试失败则抛出异常
                    throw new BusinessException(__("已达到最大重试次数！"));
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return result;
    }

    /**
     * 定义请求方式
     */
    public enum MethodType {
        GET,
        POST,
    }

    public enum WxType {
        MP,
        XCX,
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (WxHttpUtil.applicationContext == null) {
            WxHttpUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
