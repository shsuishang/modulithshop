package com.suisung.shopsuite.pt.model.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "满即送", description = "满即送")
public class ProductItemInfoVo implements Serializable {

    @ApiModelProperty("商品编号-SKU编号")
    private Long itemId;

    @ApiModelProperty("商品销售价")
    private BigDecimal itemSalePrice;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("图片信息")
    private String productImage;

    @ApiModelProperty(value = "Spec名称")
    private String productItemName;

    @ApiModelProperty(value = "SPU商品名称")
    private String productName;

    @ApiModelProperty("运费模板")
    private Integer transportTypeId;

    @ApiModelProperty("商品卖点:商品广告词")
    private String productTips;
}
