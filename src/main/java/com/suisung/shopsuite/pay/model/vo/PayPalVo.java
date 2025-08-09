package com.suisung.shopsuite.pay.model.vo;

import com.ijpay.paypal.PayPalApiConfig;
import com.ijpay.paypal.PayPalApiConfigKit;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayPalVo {
    @ApiModelProperty("clientId")
    private String clientId;

    @ApiModelProperty("私钥")
    private String secret;

    @ApiModelProperty("sandBox")
    private Boolean sandBox = false;

    @ApiModelProperty("域名")
    private String domain = ConstantConfig.URL_BASE;

    @ApiModelProperty("回调地址")
    private String notifyUrl = ConstantConfig.URL_BASE + "/front/pay/callback/paypalNotify";
    private String notifyCertUrl = ConstantConfig.URL_BASE + "/front/pay/callback/paypalCertNotify";

    @ApiModelProperty("返回地址")
    private String returnUrl = ConstantConfig.URL_BASE + "/front/pay/callback/paypalReturn";
    private String returnCertUrl = ConstantConfig.URL_BASE + "/front/pay/callback/paypalCertReturn";

    @ApiModelProperty("取消地址")
    private String cancelUrl = ConstantConfig.URL_BASE + "/front/pay/callback/paypalCancel";


    public PayPalApiConfig getConfig() {
        PayPalApiConfig config = new PayPalApiConfig();
        config.setClientId(getClientId());
        config.setSecret(getSecret());
        config.setSandBox(getSandBox());
        config.setDomain(getDomain());
        PayPalApiConfigKit.setThreadLocalApiConfig(config);
        return config;
    }
}
