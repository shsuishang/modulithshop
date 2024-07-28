package com.suisung.shopsuite.pay.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pay.model.vo.PointStepVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "签到信息对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SignInfoRes implements Serializable {

    @ApiModelProperty("连续签到天数")
    private Integer continueSignDays;

    @ApiModelProperty("已签到日期集合")
    private List<String> signDayArr;

    @ApiModelProperty("签到规则")
    private List<PointStepVo> signList;

    @ApiModelProperty("今日是否签到")
    private Integer todayIsSign;

}
