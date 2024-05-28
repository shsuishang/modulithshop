package com.suisung.shopsuite.marketing.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "规则")
public class RuleVo {
    @ApiModelProperty("总额")
    private BigDecimal amount;

    @ApiModelProperty("最大总额")
    private BigDecimal maxAmount;

    @ApiModelProperty("百分比")
    private BigDecimal percent;

    @ApiModelProperty("总数量")
    private Integer num;

    @ApiModelProperty("最多数量")
    private Integer maxNum;

    private Integer pointsStandard;

    private Integer pointsDouble;

    @ApiModelProperty("产品及数量")
    private List<ItemNumVo> item;
}