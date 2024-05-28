package com.suisung.shopsuite.account.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "操作用户经验对象", description = "操作用户经验对象")
public class ExperienceVo {

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("经验值")
    private BigDecimal exp;

    @ApiModelProperty("等级编号")
    private Integer expTypeId;

    @ApiModelProperty("描述")
    private String desc;

}
