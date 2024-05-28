package com.suisung.shopsuite.pay.model.vo;

import com.suisung.shopsuite.common.consts.ConstantConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AliPayVo {

    @ApiModelProperty("appid")
    private String appId;

    @ApiModelProperty("私钥")
    private String privateKey;

    @ApiModelProperty("公钥")
    private String publicKey;

    @ApiModelProperty("应用证书路径")
    private String appCertPath;

    @ApiModelProperty("支付宝证书路径")
    private String aliPayCertPath;

    @ApiModelProperty("支付宝证书根路径")
    private String aliPayRootCertPath;

    @ApiModelProperty("支付宝网关(固定)")
    private String serverUrl = "https://openapi.alipay.com/gateway.do";

    @ApiModelProperty("域名")
    private String domain = ConstantConfig.URL_BASE;

    @ApiModelProperty("回调地址")
    private String notifyUrl = ConstantConfig.URL_BASE + "/front/pay/callback/alipayNotify";
    private String notifyCertUrl = ConstantConfig.URL_BASE + "/front/pay/callback/alipayCertNotify";

    @ApiModelProperty("返回地址")
    private String returnUrl = ConstantConfig.URL_BASE + "/front/pay/callback/alipayReturn";
    private String returnCertUrl = ConstantConfig.URL_BASE + "/front/pay/callback/alipayCertReturn";
}
