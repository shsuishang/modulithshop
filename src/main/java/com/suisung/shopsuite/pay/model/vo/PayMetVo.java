package com.suisung.shopsuite.pay.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "支付方式信息")
public class PayMetVo {

    @ApiModelProperty("付款账户")
    private Integer paymentMetId;

    @ApiModelProperty("余额")
    private BigDecimal pmMoney;

    @ApiModelProperty("充值卡")
    private BigDecimal pmRechargeCard;

    @ApiModelProperty("积分")
    private BigDecimal pmPoints;

    @ApiModelProperty("信用账户")
    private BigDecimal pmCredit;

    @ApiModelProperty("红包账户")
    private BigDecimal pmRedpack;

    @ApiModelProperty("支付渠道")
    private Integer paymentChannelId;

    @ApiModelProperty("支付方式")
    private Integer paymentTypeId;
}