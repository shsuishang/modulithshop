package com.suisung.shopsuite.common.pojo.dto;

import cn.hutool.core.collection.CollUtil;
import lombok.Getter;

import java.util.*;

@Getter
public enum ErrorTypeEnum {

    ERR_NOT_DEFINITION("未分类异常", 0),
    ERR_WX_JSPI("微信JSPI异常", 2001),
    ERR_WX_XCX("微信小程序异常", 2002),
    ERR_WX_MP("微信公众号异常", 2003),
    ERR_ALI_PAY("支付宝支付异常", 3001),
    ERR_PSUH_MSG("消息推送异常", 4001),
    ERR_ALI_SERVICE("阿里云服务异常", 5001),
    ERR_TENCENT_SERVICE("腾讯云服务异常", 5002),
    ERR_HUAWEI_SERVICE("华为云服务异常", 5003),

    ERR_ORDER_SERVICE("订单处理异常", 6001);

    private final String label;

    private final int value;

    ErrorTypeEnum(String label, int value) {
        this.label = label;
        this.value = value;
    }

    private static final List<Map<String, Object>> errorTypes = new ArrayList<>();

    public static List<Map<String, Object>> getAllValues() {
        if (CollUtil.isNotEmpty(errorTypes)) {
            return errorTypes;
        }
        ErrorTypeEnum[] typeEnumList = values();
        for (ErrorTypeEnum typeEnum : typeEnumList) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("label", typeEnum.getLabel());
            errorMap.put("value", typeEnum.getValue());
            errorTypes.add(errorMap);
        }
        return errorTypes;
    }

}
