package com.suisung.shopsuite.analytics.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TimelineOutput {

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "数量")
    private Integer num;

}
