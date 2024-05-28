package com.suisung.shopsuite.pay.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PointStepVo {

    @ApiModelProperty("时间")
    private String times;

    @ApiModelProperty("天数")
    private Integer days;

    @ApiModelProperty("倍数")
    private String multiples;

    @ApiModelProperty("前端映射 天数或倍数")
    private String valueStr;

}
