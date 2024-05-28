package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "店铺及商品信息", description = "店铺及商品信息")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WithdrawVo {

    @ApiModelProperty("最低提现额度")
    private BigDecimal min_withdraw = BigDecimal.ZERO;

    @ApiModelProperty("最低提现额度")
    private BigDecimal plantform_fx_withdraw_min_amount = BigDecimal.ZERO;

    @ApiModelProperty("提现最低金额")
    private BigDecimal withdraw_min_amount = BigDecimal.ZERO;

    @ApiModelProperty("客户确认收货后N天可提现")
    private Float withdraw_received_day = 0F;

    @ApiModelProperty("佣金提现日期")
    private BigDecimal withdraw_monthday = BigDecimal.ZERO;

    @ApiModelProperty("提现佣金比例")
    private BigDecimal withdraw_fee_rate = BigDecimal.ZERO;

    @ApiModelProperty("总消费佣金")
    private BigDecimal commission_buy_amount = BigDecimal.ZERO;

    @ApiModelProperty("总消费佣金-已经结算")
    private BigDecimal user_commission_buy = BigDecimal.ZERO;

    @ApiModelProperty("当前可用佣金余额")
    private BigDecimal user_commission_now = BigDecimal.ZERO;

    @ApiModelProperty("历史总额度")
    private BigDecimal commission_amount = BigDecimal.ZERO;

}
