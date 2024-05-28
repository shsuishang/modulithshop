package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.marketing.model.vo.ActivityInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ActivityInfoRes {
    @ApiModelProperty("活动列表")
    List<ActivityInfoVo> items = new ArrayList<>();
}
