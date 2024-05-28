package com.suisung.shopsuite.analytics.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegUserVo {

    @ApiModelProperty(value = "注册用户数量")
    private BigDecimal num;
}
