package com.suisung.shopsuite.pay.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MoneyVo implements Serializable {

    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("余额")
    private BigDecimal recordTotal;

    @ApiModelProperty("交易类型")
    private Integer tradeTypeDeposit;

    @ApiModelProperty("描述")
    private String recordDesc;

    @ApiModelProperty("支付方式")
    private Integer paymentTypeId;

    @ApiModelProperty("佣金")
    private BigDecimal recordCommissionFee;

    @ApiModelProperty("订单编号")
    private String orderId;
}
