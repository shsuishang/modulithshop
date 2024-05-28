package com.suisung.shopsuite.pay.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "线下支付信息")
public class OfflinePayVo {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("支付渠道")
    private Integer paymentChannelId = 26;

    @ApiModelProperty("交易号")
    private String depositTradeNo;

    @ApiModelProperty("通知时间")
    private Long depositNotifyTime;

    @ApiModelProperty("交易金额")
    private BigDecimal depositTotalFee;
}
