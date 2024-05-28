package com.suisung.shopsuite.marketing.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "活动规则")
public class ActivityRuleVo {

    @ApiModelProperty("规则")
    private List<RuleVo> rule;

    @ApiModelProperty("前置条件")
    private RequirementVo requirement;

    @ApiModelProperty("优惠券对象")
    private VoucherVo voucher;

}