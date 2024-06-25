package com.suisung.shopsuite.analytics.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "仪表板柱形图对象", description = "[订单数据，用户数据，商品数据，销售额数据]")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DashBoardTimelineRes {

    @ApiModelProperty(value = "最近一周订单增长数据")
    private List<TimelineOutput> orderTimeLine;

    @ApiModelProperty(value = "最近一周订单用户数据")
    private List<TimelineOutput> userTimeLine;

    @ApiModelProperty(value = "最近一周订单商品数据")
    private List<TimelineOutput> ptTimeLine;

    @ApiModelProperty(value = "最近一周销售额增长数据")
    private List<TimelineOutput> payTimeLine;

}
