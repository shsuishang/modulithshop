package com.suisung.shopsuite.analytics.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "仪表板看板对象")
public class AdminDashBoardRes {

    @ApiModelProperty(value = "总交易额")
    private Long tradeAmount;

    @ApiModelProperty(value = "总交易额增长率")
    private Double tradeAmountIncreaseRate;

    @ApiModelProperty(value = "总成交")
    private Long orderFinishNum;

    @ApiModelProperty(value = "总成交增长率")
    private Double orderFinishNumIncreaseRate;

    @ApiModelProperty(value = "会员总数")
    private Long userCertificationNum;

    @ApiModelProperty(value = "会员总数增长率")
    private Double userCertificationNumIncreaseRate;

    @ApiModelProperty(value = "订单总量")
    private Long orderNum;

    @ApiModelProperty(value = "订单总量增长率")
    private Double orderNumIncreaseRate;

}
