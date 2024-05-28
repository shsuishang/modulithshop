package com.suisung.shopsuite.analytics.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "仪表板看板折线图对象")
public class TimelineRes {

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "数量")
    private Integer num;


}
