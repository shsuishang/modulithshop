package com.suisung.shopsuite.analytics.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommonNumVo {

    @ApiModelProperty(value = "数量")
    private BigDecimal num = BigDecimal.ZERO;
}
