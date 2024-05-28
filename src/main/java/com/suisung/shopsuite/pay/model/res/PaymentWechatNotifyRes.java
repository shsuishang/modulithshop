package com.suisung.shopsuite.pay.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "微信支付回调响应")
public class PaymentWechatNotifyRes {

    @ApiModelProperty("微信V3通知响应")
    private String v3NotifyRsp;

    // Getters and setters
}