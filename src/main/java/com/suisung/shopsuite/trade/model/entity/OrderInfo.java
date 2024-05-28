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
import java.util.Date;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_info")
@ApiModel(value = "OrderInfo对象", description = "订单信息表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "order_id", type = IdType.AUTO)
    private String orderId;

    @ApiModelProperty("订单标题")
    @TableField("order_title")
    private String orderTitle;

    @ApiModelProperty("卖家店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("买家编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("订单种类(ENUM): 1201-实物 ; 1202-教育类 ; 1203-电子卡券  ; 1204-其它")
    @TableField("kind_id")
    private Integer kindId;

    @ApiModelProperty("锁定状态(BOOL):0-正常;1-锁定,退款退货")
    @TableField("order_lock_status")
    private Boolean orderLockStatus;

    @ApiModelProperty("订单是否结算(BOOL):0-未结算; 1-已结算")
    @TableField("order_is_settlemented")
    private Boolean orderIsSettlemented;

    @ApiModelProperty("订单结算时间")
    @TableField("order_settlement_time")
    private Date orderSettlementTime;

    @ApiModelProperty("买家针对订单对店铺评价(ENUM): 0-未评价;1-已评价;  2-已过期未评价")
    @TableField("order_buyer_evaluation_status")
    private Integer orderBuyerEvaluationStatus;

    @ApiModelProperty("卖家评价状态(ENUM):0-未评价;1-已评价;  2-已过期未评价")
    @TableField("order_seller_evaluation_status")
    private Integer orderSellerEvaluationStatus;

    @ApiModelProperty("买家删除(BOOL): 1-是; 0-否")
    @TableField("order_buyer_hidden")
    private Boolean orderBuyerHidden;

    @ApiModelProperty("店铺删除(BOOL): 1-是; 0-否")
    @TableField("order_shop_hidden")
    private Boolean orderShopHidden;

    @ApiModelProperty("支付方式(ENUM): 1301-货到付款; 1302-在线支付; 1303-白条支付; 1304-现金支付; 1305-线下支付; ")
    @TableField("payment_type_id")
    private Integer paymentTypeId;

    @ApiModelProperty("付款时间")
    @TableField("payment_time")
    private Date paymentTime;

    @ApiModelProperty("订单状态(LIST):2011-待订单审核;2013-待财务审核;2020-待配货/待出库审核;2030-待发货;2040-已发货/待收货确认;2060-已完成/已签收;2070-已取消/已作废;")
    @TableField("order_state_id")
    private Integer orderStateId;

    @ApiModelProperty("订单审核(BOOL):0-未审核;1-已审核;")
    @TableField("order_is_review")
    private Boolean orderIsReview;

    @ApiModelProperty("财务状态(BOOL):0-未审核;1-已审核")
    @TableField("order_finance_review")
    private Boolean orderFinanceReview;

    @ApiModelProperty("付款状态(ENUM):3010-未付款;3011-付款待审核;3012-部分付款;3013-已付款")
    @TableField("order_is_paid")
    private Integer orderIsPaid;

    @ApiModelProperty("出库状态(ENUM):3020-未出库;3021-部分出库通过拆单解决这种问题;3022-已出库")
    @TableField("order_is_out")
    private Integer orderIsOut;

    @ApiModelProperty("发货状态(ENUM):3030-未发货;3032-已发货;3031-部分发货")
    @TableField("order_is_shipped")
    private Integer orderIsShipped;

    @ApiModelProperty("收货状态(BOOL):0-未收货;1-已收货")
    @TableField("order_is_received")
    private Boolean orderIsReceived;

    @ApiModelProperty("订单签收时间")
    @TableField("order_received_time")
    private Date orderReceivedTime;

    @ApiModelProperty("门店编号")
    @TableField("chain_id")
    private Integer chainId;

    @ApiModelProperty("配送方式")
    @TableField("delivery_type_id")
    private Integer deliveryTypeId;

    @ApiModelProperty("线下订单(BOOL):0-线上;1-线下")
    @TableField("order_is_offline")
    private Boolean orderIsOffline;

    @ApiModelProperty("是否打印(BOOL):0-未打印;1-已打印")
    @TableField("order_express_print")
    private Boolean orderExpressPrint;

    @ApiModelProperty("活动编号(DOT)")
    @TableField("activity_id")
    private String activityId;

    @ApiModelProperty("活动类型(DOT)")
    @TableField("activity_type_id")
    private String activityTypeId;

    @ApiModelProperty("销售员编号:用户编号")
    @TableField("salesperson_id")
    private Integer salespersonId;

    @ApiModelProperty("是否ERP同步(BOOL):0-未同步; 1-已同步")
    @TableField("order_is_sync")
    private Boolean orderIsSync;

    @ApiModelProperty("是否自营(ENUM): 1-自营;0-非自营")
    @TableField("store_is_selfsupport")
    private Boolean storeIsSelfsupport;

    @ApiModelProperty("店铺类型(ENUM): 1-卖家店铺; 2-供应商店铺")
    @TableField("store_type")
    private Integer storeType;

    @ApiModelProperty("ERP订单编号")
    @TableField("order_erp_id")
    private String orderErpId;

    @ApiModelProperty("分销商编号:用户编号")
    @TableField("distributor_user_id")
    private Integer distributorUserId;

    @ApiModelProperty("跨境订单(BOOL):0-否; 1-是")
    @TableField("order_is_cb")
    private Boolean orderIsCb;

    @ApiModelProperty("是否报关(BOOL):0-否; 1-是")
    @TableField("order_is_cb_sync")
    private Boolean orderIsCbSync;

    @ApiModelProperty("来源订单")
    @TableField("src_order_id")
    private String srcOrderId;

    @ApiModelProperty("是否代发(BOOL):0-否; 1-是")
    @TableField("order_is_transfer")
    private Boolean orderIsTransfer;

    @ApiModelProperty("转单执行结果")
    @TableField("order_is_transfer_note")
    private String orderIsTransferNote;

    @ApiModelProperty("佣金是否发放(BOOL):0 -未发放;1 -已发放")
    @TableField("order_fx_is_settlemented")
    private Boolean orderFxIsSettlemented;

    @ApiModelProperty("佣金结算时间")
    @TableField("order_fx_settlement_time")
    private Date orderFxSettlementTime;

    @ApiModelProperty("订单类型(ENUM)")
    @TableField("order_type")
    private Integer orderType;

    @ApiModelProperty("提现审核(BOOL):0-未审核; 1-已审核")
    @TableField("order_withdraw_confirm")
    private Boolean orderWithdrawConfirm;

    @ApiModelProperty("支付方式(BOOL):1-先预约后支付;0-先支付后预约")
    @TableField("payment_form_id")
    private Integer paymentFormId;

    @ApiModelProperty("购买类型(ENUM):1-购买; 2-积分兑换; 3-赠品; 4-活动促销")
    @TableField("cart_type_id")
    private Integer cartTypeId;

    @ApiModelProperty("商品绑定卡片类型(ENUM): 1001-次卡商品; 1002-优惠券商品;1003-券码商品;")
    @TableField("card_kind_id")
    private Integer cardKindId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Long createTime;

    @ApiModelProperty("修改时间")
    @TableField("update_time")
    private Long updateTime;

    @ApiModelProperty("版本")
    @Version
    private Integer version;
}
