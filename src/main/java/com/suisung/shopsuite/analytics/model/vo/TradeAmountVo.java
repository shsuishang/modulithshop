package com.suisung.shopsuite.analytics.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradeAmountVo {

    @ApiModelProperty(value = "销售额")
    private BigDecimal amount = BigDecimal.ZERO;
}
