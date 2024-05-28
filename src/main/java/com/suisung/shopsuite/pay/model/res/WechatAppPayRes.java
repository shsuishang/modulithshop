package com.suisung.shopsuite.pay.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "微信app支付结果")
public class WechatAppPayRes extends MoneyPayRes {

    @ApiModelProperty("微信V3支付结果")
    private Map<String, String> data;

    @ApiModelProperty("微信V3支付响应")
    private String response;
}
