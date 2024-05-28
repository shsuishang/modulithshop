package com.suisung.shopsuite.pay.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "微信jsapi支付结果")
public class WechatJSAPIPayRes extends MoneyPayRes {

    @ApiModelProperty("微信V3支付结果")
    private Map<String, String> data;

    @ApiModelProperty("微信V3支付响应")
    private String response;

    @ApiModelProperty("支付跳转链接")
    private String mwebUrl;

}
