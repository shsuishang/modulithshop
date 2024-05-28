package com.suisung.shopsuite.analytics.model.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DashboardTopRes {

    @ApiModelProperty(value = "今日")
    private Object today = 0;

    @ApiModelProperty(value = "昨日")
    private Object yestoday = 0;

    @ApiModelProperty(value = "日环比")
    private Object daym2m = null;

    @ApiModelProperty(value = "本月")
    private Object month = 0;

}
