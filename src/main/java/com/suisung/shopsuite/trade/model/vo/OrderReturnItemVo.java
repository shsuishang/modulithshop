package com.suisung.shopsuite.trade.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.trade.model.entity.OrderReturnReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "退货订单商品")
public class OrderReturnItemVo {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("订单item_id")
    private Long orderItemId;

    @ApiModelProperty("商品名称")
    private String productItemName;

    @ApiModelProperty("单位")
    private String unitName;

    @ApiModelProperty("单价")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("退款凭据")
    private List<String> returnItemImageList;

    @ApiModelProperty("退货商品数量")
    private Integer returnItemNum;

    @ApiModelProperty("退款总额")
    private BigDecimal returnItemSubtotal;

    @ApiModelProperty("商品图片")
    private String orderItemImage;

    @ApiModelProperty("商品实际成交价单价")
    private BigDecimal orderItemSalePrice;

    @ApiModelProperty("商品数量")
    private Integer orderItemQuantity;

    @ApiModelProperty("货品编号")
    private Long itemId;

    @ApiModelProperty("商品名称")
    private String itemName;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("可退金额")
    private BigDecimal canRefundAmount;

    @ApiModelProperty("可退数量")
    private Integer canRefundNum;

    @ApiModelProperty("退货原因集合")
    List<OrderReturnReason> returnReasonList;

}
