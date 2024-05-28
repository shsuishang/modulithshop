package com.suisung.shopsuite.analytics.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AnalyticsNumVo {

    @ApiModelProperty(value = "本周期")
    private Object current = 0;

    @ApiModelProperty(value = "上个周期")
    private Object pre = 0;

    @ApiModelProperty(value = "周期环比")
    private Object daym2m = null;
}
