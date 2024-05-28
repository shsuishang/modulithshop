package com.suisung.shopsuite.marketing.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.marketing.model.entity.ActivityItem;
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
@ApiModel(value = "活动商品列表")
public class ActivityItemRes extends ActivityItem {

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty(value = "商品主图")
    private String productImage;

    @ApiModelProperty("销售中")
    private Boolean isOnSale = true;

    @ApiModelProperty("副标题(DOT):SKU名称")
    private String itemName;

    @ApiModelProperty("是否启用(LIST):1001-正常;1002-下架仓库中;1000-违规禁售")
    private Integer itemEnable;

    @ApiModelProperty(value = "可用库存")
    private Integer availableQuantity;
}
