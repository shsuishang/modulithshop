package com.suisung.shopsuite.pay.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "微信jsapi支付结果")
public class WechatNativePayRes extends MoneyPayRes {
    @ApiModelProperty("微信V3支付响应")
    private String response;

    @ApiModelProperty("支付链接")
    private String codeUrl;

    @ApiModelProperty("付款额度")
    private BigDecimal paymentAmount;
}
