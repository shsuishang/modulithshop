package com.suisung.shopsuite.pay.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "微信支付回调接口")
public class PaymentWechatNotifyReq {

    @ApiModelProperty("微信支付回调")
    private String wechatNotify;

    // Getters and setters
}
