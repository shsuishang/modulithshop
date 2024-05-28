package com.suisung.shopsuite.analytics.model.output;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ApiModel(value = "商品浏览统计", description = "商品浏览统计")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnalyticsAccessItemOutput {
    @ApiModelProperty("产品编号")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("货品编号")
    @TableField("item_id")
    private Long itemId;

    @ApiModelProperty("商品名称")
    @TableField("item_name")
    private String itemName;

    @ApiModelProperty("商品价格")
    @TableField("item_unit_price")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("浏览量")
    @TableField("num")
    private Long num;
}

