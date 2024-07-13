package com.suisung.shopsuite.pt.model.output;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "商品展示使用", description = "商品展示使用")
public class ItemOutput extends ProductItem {

    @ApiModelProperty("商品状态(ENUM):1001-正常;1002-下架仓库中;1003-待审核; 1000-违规禁售")
    private Integer productStateId;

    @ApiModelProperty("产品编号")
    @TableId(value = "product_id", type = IdType.AUTO)
    private Long productId;

    @ApiModelProperty("SPU货号:货号")
    @TableField("product_number")
    private String productNumber;

    @ApiModelProperty("产品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("Spec名称")
    private String itemSpecName;

    @ApiModelProperty("商品卖点:商品广告词")
    @TableField("product_tips")
    private String productTips;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("商品主图")
    @TableField("product_image")
    private String productImage;

    @ApiModelProperty("产品视频 ")
    @TableField("product_video")
    private String productVideo;

    @ApiModelProperty("选择售卖区域:完成售卖区域及运费设置")
    @TableField("transport_type_id")
    private Integer transportTypeId;

    @ApiModelProperty("每人限购")
    @TableField("product_buy_limit")
    private Integer productBuyLimit;

    @ApiModelProperty("活动产品数量")
    @TableField(exist = false)
    private Integer activityItemNum;


    @ApiModelProperty("门店商品编号")
    @TableField(exist = false)
    private Long chainItemId;

    @ApiModelProperty("商品商品库存")
    @TableField(exist = false)
    private Integer chainItemQuantity;

    @ApiModelProperty("门店单价")
    @TableField(exist = false)
    private BigDecimal chainItemUnitPrice;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    @TableField(exist = false)
    private Boolean chainItemEnable;
}
