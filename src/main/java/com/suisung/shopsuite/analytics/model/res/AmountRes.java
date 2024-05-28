package com.suisung.shopsuite.analytics.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "订单销售金额对比图对象")
public class AmountRes {

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "金额")
    private Integer amount;

}
