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
package com.suisung.shopsuite.pay.model.entity;

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

/**
 * <p>
 * 交易订单表-强调唯一订单-充值则先创建充值订单
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_consume_trade")
@ApiModel(value = "ConsumeTrade对象", description = "交易订单表-强调唯一订单-充值则先创建充值订单")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConsumeTrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("交易订单编号")
    @TableId(value = "consume_trade_id", type = IdType.AUTO)
    private Integer consumeTradeId;

    @ApiModelProperty("标题")
    @TableField("trade_title")
    private String tradeTitle;

    @ApiModelProperty("商户订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("买家编号")
    @TableField("buyer_id")
    private Integer buyerId;

    @ApiModelProperty("买家是否有店铺")
    @TableField("buyer_store_id")
    private Integer buyerStoreId;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("卖家编号")
    @TableField("seller_id")
    private Integer sellerId;

    @ApiModelProperty("门店编号")
    @TableField("chain_id")
    private Integer chainId;

    @ApiModelProperty("支付状态")
    @TableField("trade_is_paid")
    private Integer tradeIsPaid;

    @ApiModelProperty("交易类型(ENUM):1201-购物; 1202-转账; 1203-充值; 1204-提现; 1205-销售; 1206-佣金;")
    @TableField("trade_type_id")
    private Integer tradeTypeId;

    @ApiModelProperty("支付渠道")
    @TableField("payment_channel_id")
    private Integer paymentChannelId;

    @ApiModelProperty("交易模式(ENUM):1-担保交易;  2-直接交易")
    @TableField("trade_mode_id")
    private Integer tradeModeId;

    @ApiModelProperty("充值编号")
    @TableField("recharge_level_id")
    private Integer rechargeLevelId;

    @ApiModelProperty("货币编号")
    @TableField("currency_id")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("总付款额度: trade_payment_amount + trade_payment_money + trade_payment_recharge_card + trade_payment_points")
    @TableField("order_payment_amount")
    private BigDecimal orderPaymentAmount;

    @ApiModelProperty("平台交易佣金")
    @TableField("order_commission_fee")
    private BigDecimal orderCommissionFee;

    @ApiModelProperty("实付金额:在线支付金额,此为订单默认需要支付额度。")
    @TableField("trade_payment_amount")
    private BigDecimal tradePaymentAmount;

    @ApiModelProperty("余额支付")
    @TableField("trade_payment_money")
    private BigDecimal tradePaymentMoney;

    @ApiModelProperty("充值卡余额支付")
    @TableField("trade_payment_recharge_card")
    private BigDecimal tradePaymentRechargeCard;

    @ApiModelProperty("积分支付")
    @TableField("trade_payment_points")
    private BigDecimal tradePaymentPoints;

    @ApiModelProperty("众宝支付")
    @TableField("trade_payment_sp")
    private BigDecimal tradePaymentSp;

    @ApiModelProperty("信用支付")
    @TableField("trade_payment_credit")
    private BigDecimal tradePaymentCredit;

    @ApiModelProperty("红包支付")
    @TableField("trade_payment_redpack")
    private BigDecimal tradePaymentRedpack;

    @ApiModelProperty("折扣优惠")
    @TableField("trade_discount")
    private BigDecimal tradeDiscount;

    @ApiModelProperty("总额虚拟:trade_order_amount + trade_discount")
    @TableField("trade_amount")
    private BigDecimal tradeAmount;

    @ApiModelProperty("描述")
    @TableField("trade_desc")
    private String tradeDesc;

    @ApiModelProperty("备注")
    @TableField("trade_remark")
    private String tradeRemark;

    @ApiModelProperty("创建时间")
    @TableField("trade_create_time")
    private Long tradeCreateTime;

    @ApiModelProperty("付款时间")
    @TableField("trade_paid_time")
    private Long TradePaidTime;

    @ApiModelProperty("是否删除")
    @TableField("trade_delete")
    private Boolean tradeDelete;
}
