package com.suisung.shopsuite.shop.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.shop.model.entity.UserFavoritesItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "收藏商品列表")
public class UserFavoritesItemRes extends UserFavoritesItem {

    @ApiModelProperty("商品SKU全名")
    private String productItemName;

    @ApiModelProperty("商品主图")
    private String productImage;

    @ApiModelProperty("商品价格")
    private BigDecimal itemUnitPrice;

}
