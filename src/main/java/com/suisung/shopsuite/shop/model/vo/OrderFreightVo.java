package com.suisung.shopsuite.shop.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderFreightVo implements Serializable {
    @ApiModelProperty("是否可配送")
    private Boolean canDelivery;

    @ApiModelProperty("免运费额度")
    private BigDecimal freightFreeMin;

    @ApiModelProperty("运费")
    private BigDecimal freight = BigDecimal.ZERO;

}
