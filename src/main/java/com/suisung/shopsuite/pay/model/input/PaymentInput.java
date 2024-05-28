package com.suisung.shopsuite.pay.model.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "支付接口")
public class PaymentInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @NotEmpty(message = "请输入订单编号")
    private List<String> orderId;

    @ApiModelProperty("支付渠道")
    private Integer paymentChannelId = 1403;

    @ApiModelProperty("支付方式(ENUM):1301-货到付款; 1302-在线支付; 1303-白条支付; 1304-现金支付; 1305-线下支付;")
    private Integer depositPaymentType = 1302;


    @ApiModelProperty("支付密码")
    private String password;

    @ApiModelProperty("余额支付")
    private BigDecimal pmMoney;

    @ApiModelProperty("充值卡支付")
    private BigDecimal pmRechargeCard;

    @ApiModelProperty("积分支付")
    private BigDecimal pmPoints;

    @ApiModelProperty("积分支付")
    private BigDecimal pmCredit;
}
