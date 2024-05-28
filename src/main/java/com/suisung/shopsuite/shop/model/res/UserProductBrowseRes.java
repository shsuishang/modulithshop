package com.suisung.shopsuite.shop.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.shop.model.entity.UserProductBrowse;
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
@ApiModel(value = "我的足迹列表")
public class UserProductBrowseRes extends UserProductBrowse {

    @ApiModelProperty("商品销售价")
    private BigDecimal itemSalePrice;

    @ApiModelProperty("图片信息")
    private String productImage;

    @ApiModelProperty(value = "Spec名称")
    private String productItemName;

    @ApiModelProperty("活动类型编号")
    private Integer activityTypeId;

    @ApiModelProperty("活动类型名称")
    private String activityTypeName;

}
