package com.suisung.shopsuite.analytics.model.output;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "商品销售统计", description = "商品销售统计")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnalyticsOrderItemNumOutput {
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

    @ApiModelProperty("分类编号")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty("成本价")
    @TableField("item_cost_price")
    private BigDecimal itemCostPrice;

    @ApiModelProperty("商品价格单价")
    @TableField("item_unit_price")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("资源1单价")
    @TableField("item_unit_points")
    private BigDecimal itemUnitPoints;

    @ApiModelProperty("资源2单价")
    @TableField("item_unit_sp")
    private BigDecimal itemUnitSp;

    @ApiModelProperty("商品实际成交价单价")
    @TableField("order_item_sale_price")
    private BigDecimal orderItemSalePrice;

    @ApiModelProperty("商品数量")
    @TableField("order_item_quantity")
    private Integer orderItemQuantity;

    @ApiModelProperty("商品图片")
    @TableField("order_item_image")
    private String orderItemImage;

    @ApiModelProperty("退货数量")
    @TableField("order_item_return_num")
    private Integer orderItemReturnNum;

    @ApiModelProperty("退款总额")
    @TableField("order_item_return_subtotal")
    private BigDecimal orderItemReturnSubtotal;

    @ApiModelProperty("退款金额:同意额度")
    @TableField("order_item_return_agree_amount")
    private BigDecimal orderItemReturnAgreeAmount;

    @ApiModelProperty("商品实际总金额: order_item_sale_price * order_item_quantity")
    @TableField("order_item_amount")
    private BigDecimal orderItemAmount;

    @ApiModelProperty("优惠金额:只考虑单品的，订单及店铺总活动优惠不影响")
    @TableField("order_item_discount_amount")
    private BigDecimal orderItemDiscountAmount;

    @ApiModelProperty("手工调整金额")
    @TableField("order_item_adjust_fee")
    private BigDecimal orderItemAdjustFee;

    @ApiModelProperty("积分费用")
    @TableField("order_item_points_fee")
    private BigDecimal orderItemPointsFee;

    @ApiModelProperty("赠送积分")
    @TableField("order_item_points_add")
    private BigDecimal orderItemPointsAdd;

    @ApiModelProperty("实付金额: order_item_payment_amount =  order_item_amount - order_item_discount_amount - order_item_adjust_fee - order_item_point_fee")
    @TableField("order_item_payment_amount")
    private BigDecimal orderItemPaymentAmount;

    @ApiModelProperty("评价状态(ENUM): 0-未评价;1-已评价;2-失效评价")
    @TableField("order_item_evaluation_status")
    private Boolean orderItemEvaluationStatus;

    @ApiModelProperty("活动类型(ENUM):0-默认;1105-店铺代金券coupon优惠券;")
    @TableField("activity_type_id")
    private Integer activityTypeId;

    @ApiModelProperty("促销活动ID:与activity_type_id搭配使用, 团购ID/限时折扣ID/优惠套装ID/积分兑换编号")
    @TableField("activity_id")
    private Integer activityId;

    @ApiModelProperty("礼包活动对应兑换码code")
    @TableField("activity_code")
    private String activityCode;

    @ApiModelProperty("分佣金比例百分比")
    @TableField("order_item_commission_rate")
    private BigDecimal orderItemCommissionRate;

    @ApiModelProperty("佣金")
    @TableField("order_item_commission_fee")
    private BigDecimal orderItemCommissionFee;

    @ApiModelProperty("退款佣金")
    @TableField("order_item_commission_fee_refund")
    private BigDecimal orderItemCommissionFeeRefund;

    @ApiModelProperty("价格策略折扣率")
    @TableField("policy_discountrate")
    private BigDecimal policyDiscountrate;

    @ApiModelProperty("分配优惠券额度")
    @TableField("order_item_voucher")
    private BigDecimal orderItemVoucher;

    @ApiModelProperty("分配满减额度")
    @TableField("order_item_reduce")
    private BigDecimal orderItemReduce;


    @ApiModelProperty("同意退货数量")
    @TableField("order_item_return_agree_num")
    private Integer orderItemReturnAgreeNum;


    @ApiModelProperty("统计数量")
    @TableField("order_item_amount_sum")
    private BigDecimal orderItemAmountSum;

    @ApiModelProperty("统计数量")
    @TableField("num")
    private Long num;
}

