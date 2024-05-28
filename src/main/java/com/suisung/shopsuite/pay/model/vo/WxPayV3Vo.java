package com.suisung.shopsuite.pay.model.vo;

import com.suisung.shopsuite.common.consts.ConstantConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WxPayV3Vo {
    @ApiModelProperty("appid")
    private String appId;

    @ApiModelProperty("密钥keyPath")
    private String keyPath;

    @ApiModelProperty("微信支付证书Path")
    private String certPath;

    @ApiModelProperty("")
    private String certP12Path;

    @ApiModelProperty("微信平台证书")
    private String platformCertPath;

    @ApiModelProperty("商户编号")
    private String mchId;

    @ApiModelProperty("秘钥")
    private String apiKey;

    @ApiModelProperty("秘钥")
    private String apiKey3;

    @ApiModelProperty("域名")
    private String domain = ConstantConfig.URL_BASE;

    //不是必须，可以从证书中读取
    @ApiModelProperty("证书序列号")
    private String serialNo;

    @ApiModelProperty("回调地址")
    private String notifyUrl = ConstantConfig.URL_BASE + "/front/pay/callback/wechatNotify";
}
