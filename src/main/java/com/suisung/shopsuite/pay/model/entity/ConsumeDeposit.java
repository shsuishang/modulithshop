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
 * 支付表-支付回调callback使用-确认付款
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_consume_deposit")
@ApiModel(value = "ConsumeDeposit对象", description = "支付表-支付回调callback使用-确认付款")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConsumeDeposit implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("支付流水号")
    @TableId(value = "deposit_id", type = IdType.AUTO)
    private Long depositId;

    @ApiModelProperty("商城支付编号")
    @TableField("deposit_no")
    private String depositNo;

    @ApiModelProperty("交易号:支付宝etc")
    @TableField("deposit_trade_no")
    private String depositTradeNo;

    @ApiModelProperty("商户网站唯一订单号(DOT):合并支付则为多个订单号, 没有创建联合支付交易号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("支付渠道")
    @TableField("payment_channel_id")
    private Integer paymentChannelId;

    @ApiModelProperty("商品名称")
    @TableField("deposit_subject")
    private String depositSubject;

    @ApiModelProperty("支付方式(ENUM):1301-货到付款; 1302-在线支付; 1303-白条支付; 1304-现金支付; 1305-线下支付; ")
    @TableField("deposit_payment_type")
    private Integer depositPaymentType;

    @ApiModelProperty("交易状态")
    @TableField("deposit_trade_status")
    private String depositTradeStatus;

    @ApiModelProperty("卖家户号:支付宝etc")
    @TableField("deposit_seller_id")
    private String depositSellerId;

    @ApiModelProperty("卖家支付账号")
    @TableField("deposit_seller_email")
    private String depositSellerEmail;

    @ApiModelProperty("买家支付用户号")
    @TableField("deposit_buyer_id")
    private String depositBuyerId;

    @ApiModelProperty("买家支付账号")
    @TableField("deposit_buyer_email")
    private String depositBuyerEmail;

    @ApiModelProperty("货币编号")
    @TableField("currency_id")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("交易金额")
    @TableField("deposit_total_fee")
    private BigDecimal depositTotalFee;

    @ApiModelProperty("购买数量")
    @TableField("deposit_quantity")
    private Integer depositQuantity;

    @ApiModelProperty("商品单价")
    @TableField("deposit_price")
    private BigDecimal depositPrice;

    @ApiModelProperty("商品描述")
    @TableField("deposit_body")
    private String depositBody;

    @ApiModelProperty("是否调整总价")
    @TableField("deposit_is_total_fee_adjust")
    private Boolean depositIsTotalFeeAdjust;

    @ApiModelProperty("是否使用红包买家")
    @TableField("deposit_use_coupon")
    private Boolean depositUseCoupon;

    @ApiModelProperty("折扣")
    @TableField("deposit_discount")
    private BigDecimal depositDiscount;

    @ApiModelProperty("通知时间")
    @TableField("deposit_notify_time")
    private String depositNotifyTime;

    @ApiModelProperty("通知类型")
    @TableField("deposit_notify_type")
    private String depositNotifyType;

    @ApiModelProperty("通知校验编号")
    @TableField("deposit_notify_id")
    private String depositNotifyId;

    @ApiModelProperty("签名方式")
    @TableField("deposit_sign_type")
    private String depositSignType;

    @ApiModelProperty("签名")
    @TableField("deposit_sign")
    private String depositSign;

    @ApiModelProperty("额外参数")
    @TableField("deposit_extra_param")
    private String depositExtraParam;

    @ApiModelProperty("支付")
    @TableField("deposit_service")
    private String depositService;

    @ApiModelProperty("支付状态:0-默认; 1-接收正确数据处理完逻辑; 9-异常订单")
    @TableField("deposit_state")
    private Integer depositState;

    @ApiModelProperty("是否同步(BOOL):0-同步; 1-异步回调使用")
    @TableField("deposit_async")
    private Boolean depositAsync;

    @ApiModelProperty("收款确认(BOOL):0-未确认;1-已确认")
    @TableField("deposit_review")
    private Boolean depositReview;

    @ApiModelProperty("是否作废(BOOL):1-正常; 2-作废")
    @TableField("deposit_enable")
    private Boolean depositEnable;

    @ApiModelProperty("所属店铺:直接交易起作用")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("所属用户")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("所属门店:直接交易起作用")
    @TableField("chain_id")
    private Integer chainId;

    @ApiModelProperty("所属分站:直接交易起作用")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("支付时间")
    @TableField("deposit_time")
    private Long depositTime;
}
