package com.suisung.shopsuite.trade.model.vo;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "PickingItem对象", description = "PickingItem对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PickingItem implements Serializable {

    @ApiModelProperty("SKU编号")
    private Long itemId;

    @ApiModelProperty("订单SKU编号")
    private Long orderItemId;

    @ApiModelProperty("商品数量")
    private Integer billItemQuantity;

    @ApiModelProperty("商品单价")
    private BigDecimal billItemPrice;

    @ApiModelProperty("商品编号")
    private Long productId;

}