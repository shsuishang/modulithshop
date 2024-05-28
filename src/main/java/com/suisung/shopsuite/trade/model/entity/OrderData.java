// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.trade.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单详细信息
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_data")
@ApiModel(value = "OrderData对象", description = "订单详细信息")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderData implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "order_id", type = IdType.AUTO)
    private String orderId;

    @ApiModelProperty("订单描述")
    @TableField("order_desc")
    private String orderDesc;

    @ApiModelProperty("延迟时间,默认为0 - 收货确认")
    @TableField("order_delay_time")
    private Integer orderDelayTime;

    @ApiModelProperty("配送方式")
    @TableField("delivery_type_id")
    private Integer deliveryTypeId;

    @ApiModelProperty("配送时间:要求，不限、周一~周五、周末等等")
    @TableField("delivery_time_id")
    private Integer deliveryTimeId;

    @ApiModelProperty("配送日期")
    @TableField("delivery_time")
    private Long deliveryTime;

    @ApiModelProperty("是否定时配送(BOOL):0-不定时;1-定时")
    @TableField("delivery_istimer")
    private Boolean deliveryIstimer;

    @ApiModelProperty("买家订单留言")
    @TableField("order_message")
    private String orderMessage;

    @ApiModelProperty("商品总价格/商品金额, 不包含运费")
    @TableField("order_item_amount")
    private BigDecimal orderItemAmount;

    @ApiModelProperty("折扣价格/优惠总金额")
    @TableField("order_discount_amount")
    private BigDecimal orderDiscountAmount;

    @ApiModelProperty("手工调整费用店铺优惠")
    @TableField("order_adjust_fee")
    private BigDecimal orderAdjustFee;

    @ApiModelProperty("积分抵扣费用")
    @TableField("order_points_fee")
    private BigDecimal orderPointsFee;

    @ApiModelProperty("运费价格/运费金额")
    @TableField("order_shipping_fee_amount")
    private BigDecimal orderShippingFeeAmount;

    @ApiModelProperty("实际运费金额-卖家可修改")
    @TableField("order_shipping_fee")
    private BigDecimal orderShippingFee;

    @ApiModelProperty("代金券id/优惠券/返现:发放选择使用")
    @TableField("voucher_id")
    private Integer voucherId;

    @ApiModelProperty("代金券编码")
    @TableField("voucher_number")
    private String voucherNumber;

    @ApiModelProperty("代金券面额")
    @TableField("voucher_price")
    private BigDecimal voucherPrice;

    @ApiModelProperty("红包id-平台代金券")
    @TableField("redpacket_id")
    private Integer redpacketId;

    @ApiModelProperty("红包编码")
    @TableField("redpacket_number")
    private String redpacketNumber;

    @ApiModelProperty("红包面额")
    @TableField("redpacket_price")
    private BigDecimal redpacketPrice;

    @ApiModelProperty("红包抵扣订单金额")
    @TableField("order_redpacket_price")
    private BigDecimal orderRedpacketPrice;

    @ApiModelProperty("第二需要支付资源例如积分")
    @TableField("order_resource_ext1")
    private BigDecimal orderResourceExt1;

    @ApiModelProperty("众宝")
    @TableField("order_resource_ext2")
    private BigDecimal orderResourceExt2;

    @ApiModelProperty("金宝")
    @TableField("order_resource_ext3")
    private BigDecimal orderResourceExt3;

    @ApiModelProperty("余额支付")
    @TableField("trade_payment_money")
    private BigDecimal tradePaymentMoney;

    @ApiModelProperty("充值卡支付")
    @TableField("trade_payment_recharge_card")
    private BigDecimal tradePaymentRechargeCard;

    @ApiModelProperty("信用支付")
    @TableField("trade_payment_credit")
    private BigDecimal tradePaymentCredit;

    @ApiModelProperty("退款状态:0-是无退款;1-是部分退款;2-是全部退款")
    @TableField("order_refund_status")
    private Integer orderRefundStatus;

    @ApiModelProperty("退款金额:申请额度")
    @TableField("order_refund_amount")
    private BigDecimal orderRefundAmount;

    @ApiModelProperty("退款金额:同意额度")
    @TableField("order_refund_agree_amount")
    private BigDecimal orderRefundAgreeAmount;

    @ApiModelProperty("已同意退的现金")
    @TableField("order_refund_agree_cash")
    private BigDecimal orderRefundAgreeCash;

    @ApiModelProperty("已退的积分额度")
    @TableField("order_refund_agree_points")
    private BigDecimal orderRefundAgreePoints;

    @ApiModelProperty("退货状态(ENUM):0-是无退货;1-是部分退货;2-是全部退货")
    @TableField("order_return_status")
    private Integer orderReturnStatus;

    @ApiModelProperty("退货数量")
    @TableField("order_return_num")
    private Integer orderReturnNum;

    @ApiModelProperty("退货单编号s(DOT):冗余")
    @TableField("order_return_ids")
    private String orderReturnIds;

    @ApiModelProperty("平台交易佣金")
    @TableField("order_commission_fee")
    private BigDecimal orderCommissionFee;

    @ApiModelProperty("交易佣金-退款")
    @TableField("order_commission_fee_refund")
    private BigDecimal orderCommissionFeeRefund;

    @ApiModelProperty("订单赠送积分")
    @TableField("order_points_add")
    private BigDecimal orderPointsAdd;

    @ApiModelProperty("促销信息")
    @TableField("order_activity_data")
    private String orderActivityData;

    @ApiModelProperty("订单取消者身份(ENUM):1-买家; 2-卖家; 3-系统")
    @TableField("order_cancel_identity")
    private Boolean orderCancelIdentity;

    @ApiModelProperty("订单取消原因")
    @TableField("order_cancel_reason")
    private String orderCancelReason;

    @ApiModelProperty("订单取消时间")
    @TableField("order_cancel_time")
    private Date orderCancelTime;

    @ApiModelProperty("赠送资源2")
    @TableField("order_bp_add")
    private Integer orderBpAdd;

    @ApiModelProperty("订单返利")
    @TableField("order_rebate")
    private BigDecimal orderRebate;

    @ApiModelProperty("手机号码")
    @TableField("buyer_mobile")
    private Integer buyerMobile;

    @ApiModelProperty("联系人")
    @TableField("buyer_contacter")
    private String buyerContacter;

    @ApiModelProperty("满返优惠券活动id(DOT)")
    @TableField("activity_manhui_id")
    private String activityManhuiId;

    @ApiModelProperty("活动-多倍积分id")
    @TableField("activity_double_points_id")
    private Integer activityDoublePointsId;

    @ApiModelProperty("活动-多倍积分")
    @TableField("order_double_points_add")
    private BigDecimal orderDoublePointsAdd;

    @ApiModelProperty("满返用户优惠券id(DOT)")
    @TableField("activity_voucher_id")
    private String activityVoucherId;

    @ApiModelProperty("满返优惠券发放状态(ENUM):1000-无需发放;1001-待发放; 1002-已发放; 1003-发放异常")
    @TableField("order_activity_manhui_state")
    private Integer orderActivityManhuiState;

    @ApiModelProperty("版本")
    @Version
    private Integer version;
}
