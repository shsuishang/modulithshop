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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_return")
@ApiModel(value = "OrderReturn对象", description = "退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderReturn implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("退单号")
    @TableId(value = "return_id", type = IdType.AUTO)
    private String returnId;

    @ApiModelProperty("服务类型(ENUM):1-退款;2-退货;3-换货;4-维修")
    @TableField("service_type_id")
    private Integer serviceTypeId;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("退款金额")
    @TableField("return_refund_amount")
    private BigDecimal returnRefundAmount;

    @ApiModelProperty("积分部分")
    @TableField("return_refund_point")
    private BigDecimal returnRefundPoint;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("买家编号")
    @TableField("buyer_user_id")
    private Integer buyerUserId;

    @ApiModelProperty("买家是否有店铺")
    @TableField("buyer_store_id")
    private Integer buyerStoreId;

    @ApiModelProperty("添加时间")
    @TableField("return_add_time")
    private Long returnAddTime;

    @ApiModelProperty("退款理由编号")
    @TableField("return_reason_id")
    private Integer returnReasonId;

    @ApiModelProperty("买家退货备注")
    @TableField("return_buyer_message")
    private String returnBuyerMessage;

    @ApiModelProperty("收货人")
    @TableField("return_addr_contacter")
    private String returnAddrContacter;

    @ApiModelProperty("联系电话")
    @TableField("return_tel")
    private String returnTel;

    @ApiModelProperty("收货地址详情")
    @TableField("return_addr")
    private String returnAddr;

    @ApiModelProperty("邮编")
    @TableField("return_post_code")
    private Integer returnPostCode;

    @ApiModelProperty("物流公司编号")
    @TableField("express_id")
    private Integer expressId;

    @ApiModelProperty("物流名称")
    @TableField("return_tracking_name")
    private String returnTrackingName;

    @ApiModelProperty("物流单号")
    @TableField("return_tracking_number")
    private String returnTrackingNumber;

    @ApiModelProperty("申请状态平台(ENUM):3180-未申请;3181-待处理;3182-为已完成")
    @TableField("plantform_return_state_id")
    private Integer plantformReturnStateId;

    @ApiModelProperty("卖家处理状态(ENUM): 3100-【客户】提交退单;3105-退单审核;3110-收货确认;3115-退款确认;3120-客户】收款确认;3125-完成")
    @TableField("return_state_id")
    private Integer returnStateId;

    @ApiModelProperty("退款完成")
    @TableField("return_is_paid")
    private Boolean returnIsPaid;

    @ApiModelProperty("退货类型(BOOL): 0-退款单;1-退运费单")
    @TableField("return_is_shipping_fee")
    private Integer returnIsShippingFee;

    @ApiModelProperty("退运费额度")
    @TableField("return_shipping_fee")
    private BigDecimal returnShippingFee;

    @ApiModelProperty("退货类型(ENUM): 0-不用退货;1-需要退货")
    @TableField("return_flag")
    private Integer returnFlag;

    @ApiModelProperty("申请类型(ENUM): 1-退款申请; 2-退货申请; 3-虚拟退款  ")
    @TableField("return_type")
    private Integer returnType;

    @ApiModelProperty("订单锁定类型(BOOL):1-不用锁定;2-需要锁定")
    @TableField("return_order_lock")
    private Integer returnOrderLock;

    @ApiModelProperty("物流状态(LIST):2030-待发货;2040-已发货/待收货确认;2060-已完成/已签收;2070-已取消/已作废;")
    @TableField("return_item_state_id")
    private Integer returnItemStateId;

    @ApiModelProperty("商家处理时间")
    @TableField("return_store_time")
    private Date returnStoreTime;

    @ApiModelProperty("商家备注")
    @TableField("return_store_message")
    private String returnStoreMessage;

    @ApiModelProperty("退还佣金")
    @TableField("return_commision_fee")
    private BigDecimal returnCommisionFee;

    @ApiModelProperty("退款完成时间")
    @TableField("return_finish_time")
    private Date returnFinishTime;

    @ApiModelProperty("平台留言")
    @TableField("return_platform_message")
    private String returnPlatformMessage;

    @ApiModelProperty("订单是否结算(BOOL): 0-未结算; 1-已结算")
    @TableField("return_is_settlemented")
    private Boolean returnIsSettlemented;

    @ApiModelProperty("订单结算时间")
    @TableField("return_settlement_time")
    private Date returnSettlementTime;

    @ApiModelProperty("退款渠道(ENUM):money-余额;alipay-支付宝;wx_native-微信")
    @TableField("return_channel_code")
    private String returnChannelCode;

    @ApiModelProperty("渠道是否退款(ENUM): 0-待退; 1-已退; 2-异常")
    @TableField("return_channel_flag")
    private Integer returnChannelFlag;

    @ApiModelProperty("渠道退款时间")
    @TableField("return_channel_time")
    private Date returnChannelTime;

    @ApiModelProperty("渠道退款单号")
    @TableField("return_channel_trans_id")
    private String returnChannelTransId;

    @ApiModelProperty("交易号")
    @TableField("deposit_trade_no")
    private String depositTradeNo;

    @ApiModelProperty("支付渠道")
    @TableField("payment_channel_id")
    private Integer paymentChannelId;

    @ApiModelProperty("实付金额:在线支付金额")
    @TableField("trade_payment_amount")
    private BigDecimal tradePaymentAmount;

    @ApiModelProperty("联系人")
    @TableField("return_contact_name")
    private String returnContactName;

    @ApiModelProperty("审核人员id")
    @TableField("return_store_user_id")
    private Integer returnStoreUserId;

    @ApiModelProperty("提现审核(BOOL):0-未审核; 1-已审核")
    @TableField("return_withdraw_confirm")
    private Boolean returnWithdrawConfirm;

    @ApiModelProperty("退款财务确认(BOOL):0-未确认; 1-已确认")
    @TableField("return_financial_confirm")
    private Boolean returnFinancialConfirm;

    @ApiModelProperty("退款财务确认时间")
    @TableField("return_financial_confirm_time")
    private Date returnFinancialConfirmTime;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;
}
