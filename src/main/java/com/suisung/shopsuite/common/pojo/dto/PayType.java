package com.suisung.shopsuite.common.pojo.dto;

/**
 * 支付类型枚举类
 * JSAPI 微信公众号支付
 * WXh5 微信h5 支付
 */
public enum PayType {

    ALIPAY_H5(1), WX_XCX(2), WX_H5(3), WX_JSAPI(4), WX_APP(5), ALIPAY_APP(6), WX_PC_PAY(7), ALI_PC_PAY(8);

    private final int code;

    PayType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
