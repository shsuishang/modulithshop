package com.suisung.shopsuite.pt.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDataOutput {
    @ApiModelProperty("商品基础信息")
    private ProductBase productBase;

    @ApiModelProperty("商品索引")
    private ProductIndex productIndex;

    @ApiModelProperty("商品信息")
    private ProductInfo productInfo;

    @ApiModelProperty("商品SKU")
    private List<ProductItem> productItem;

    @ApiModelProperty("商品图片表")
    private List<ProductImage> productImage;

    @ApiModelProperty("虚拟商品表")
    private ProductValidPeriod productValidPeriod;

}
