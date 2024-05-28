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
package com.suisung.shopsuite.trade.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "订单详细信息分页查询")
public class OrderDataListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("订单描述")
    private String orderDesc;

    @ApiModelProperty("延迟时间,默认为0 - 收货确认")
    private Integer orderDelayTime;

    @ApiModelProperty("配送方式")
    private Integer deliveryTypeId;

    @ApiModelProperty("配送时间:要求，不限、周一~周五、周末等等")
    private Integer deliveryTimeId;

    @ApiModelProperty("配送日期")
    private Date deliveryTime;

    @ApiModelProperty("定时配送 时")
    private Integer deliveryTimeH;

    @ApiModelProperty("定时配送 分")
    private Integer deliveryTimeI;

    @ApiModelProperty("是否定时配送(BOOL):0-不定时;1-定时")
    private Boolean deliveryIstimer;

    @ApiModelProperty("买家订单留言")
    private String orderMessage;

    @ApiModelProperty("商品总价格/商品金额, 不包含运费")
    private BigDecimal orderItemAmount;

    @ApiModelProperty("折扣价格/优惠总金额")
    private BigDecimal orderDiscountAmount;

    @ApiModelProperty("手工调整费用店铺优惠")
    private BigDecimal orderAdjustFee;

    @ApiModelProperty("积分抵扣费用")
    private BigDecimal orderPointsFee;

    @ApiModelProperty("运费价格/运费金额")
    private BigDecimal orderShippingFeeAmount;

    @ApiModelProperty("实际运费金额-卖家可修改")
    private BigDecimal orderShippingFee;

    @ApiModelProperty("代金券id/优惠券/返现:发放选择使用")
    private Integer voucherId;

    @ApiModelProperty("代金券编码")
    private String voucherNumber;

    @ApiModelProperty("代金券面额")
    private BigDecimal voucherPrice;

    @ApiModelProperty("红包id-平台代金券")
    private Integer redpacketId;

    @ApiModelProperty("红包编码")
    private String redpacketNumber;

    @ApiModelProperty("红包面额")
    private BigDecimal redpacketPrice;

    @ApiModelProperty("红包抵扣订单金额")
    private BigDecimal orderRedpacketPrice;

    @ApiModelProperty("第二需要支付资源例如积分")
    private BigDecimal orderResourceExt1;

    @ApiModelProperty("众宝")
    private BigDecimal orderResourceExt2;

    @ApiModelProperty("金宝")
    private BigDecimal orderResourceExt3;

    @ApiModelProperty("余额支付")
    private BigDecimal tradePaymentMoney;

    @ApiModelProperty("充值卡支付")
    private BigDecimal tradePaymentRechargeCard;

    @ApiModelProperty("信用支付")
    private BigDecimal tradePaymentCredit;

    @ApiModelProperty("退款状态:0-是无退款;1-是部分退款;2-是全部退款")
    private Integer orderRefundStatus;

    @ApiModelProperty("退款金额:申请额度")
    private BigDecimal orderRefundAmount;

    @ApiModelProperty("退款金额:同意额度")
    private BigDecimal orderRefundAgreeAmount;

    @ApiModelProperty("已同意退的现金")
    private BigDecimal orderRefundAgreeCash;

    @ApiModelProperty("已退的积分额度")
    private BigDecimal orderRefundAgreePoints;

    @ApiModelProperty("退货状态(ENUM):0-是无退货;1-是部分退货;2-是全部退货")
    private Integer orderReturnStatus;

    @ApiModelProperty("退货数量")
    private Integer orderReturnNum;

    @ApiModelProperty("退货单编号s(DOT):冗余")
    private String orderReturnIds;

    @ApiModelProperty("平台交易佣金")
    private BigDecimal orderCommissionFee;

    @ApiModelProperty("交易佣金-退款")
    private BigDecimal orderCommissionFeeRefund;

    @ApiModelProperty("订单赠送积分")
    private BigDecimal orderPointsAdd;

    @ApiModelProperty("促销信息")
    private String orderActivityData;

    @ApiModelProperty("订单取消者身份(ENUM):1-买家; 2-卖家; 3-系统")
    private Boolean orderCancelIdentity;

    @ApiModelProperty("订单取消原因")
    private String orderCancelReason;

    @ApiModelProperty("订单取消时间")
    private Date orderCancelTime;

    @ApiModelProperty("赠送资源2")
    private Integer orderBpAdd;

    @ApiModelProperty("订单返利")
    private BigDecimal orderRebate;

    @ApiModelProperty("手机号码")
    private Integer buyerMobile;

    @ApiModelProperty("联系人")
    private String buyerContacter;

    @ApiModelProperty("满返优惠券活动id(DOT)")
    private String activityManhuiId;

    @ApiModelProperty("活动-多倍积分id")
    private Integer activityDoublePointsId;

    @ApiModelProperty("活动-多倍积分")
    private BigDecimal orderDoublePointsAdd;

    @ApiModelProperty("满返用户优惠券id(DOT)")
    private String activityVoucherId;

    @ApiModelProperty("满返优惠券发放状态(ENUM):1000-无需发放;1001-待发放; 1002-已发放; 1003-发放异常")
    private Integer orderActivityManhuiState;


}
