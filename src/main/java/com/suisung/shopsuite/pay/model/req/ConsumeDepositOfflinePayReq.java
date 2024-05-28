package com.suisung.shopsuite.pay.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "线下支付接口")
public class ConsumeDepositOfflinePayReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @NotBlank(message = "请输入订单编号")
    private String orderId;

    @ApiModelProperty("支付渠道")
    private Integer paymentChannelId = 1422;

    @ApiModelProperty("支付方式")
    private Integer depositPaymentType = 1305;

    @ApiModelProperty("交易凭证号")
    @NotBlank(message = "请输入交易凭证号")
    private String depositTradeNo;

    @ApiModelProperty("时间")
    private Long depositTime;

    @ApiModelProperty("交易金额")
    private BigDecimal depositTotalFee;

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
}
