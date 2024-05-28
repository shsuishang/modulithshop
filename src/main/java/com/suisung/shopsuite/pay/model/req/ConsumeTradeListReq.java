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
package com.suisung.shopsuite.pay.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.annotation.QueryField;
import com.suisung.shopsuite.core.annotation.QueryType;
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易订单表-强调唯一订单-充值则先创建充值订单
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "交易订单表-强调唯一订单-充值则先创建充值订单分页查询")
public class ConsumeTradeListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("交易订单编号")
    private Integer consumeTradeId;

    @ApiModelProperty("标题")
    private String tradeTitle;

    @ApiModelProperty("商户订单编号")
    private String orderId;

    @ApiModelProperty("买家编号")
    private Integer buyerId;

    @ApiModelProperty("买家是否有店铺")
    private Integer buyerStoreId;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;

    @ApiModelProperty("卖家编号")
    private Integer sellerId;

    @ApiModelProperty("门店编号")
    private Integer chainId;

    @ApiModelProperty("支付状态")
    private Integer tradeIsPaid;

    @ApiModelProperty("交易类型(ENUM):1201-购物; 1202-转账; 1203-充值; 1204-提现; 1205-销售; 1206-佣金;")
    private Integer tradeTypeId;

    @ApiModelProperty("支付渠道")
    private Integer paymentChannelId;

    @ApiModelProperty("交易模式(ENUM):1-担保交易;  2-直接交易")
    private Integer tradeModeId;

    @ApiModelProperty("充值编号")
    private Integer rechargeLevelId;

    @ApiModelProperty("货币编号")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    private String currencySymbolLeft;

    @ApiModelProperty("总付款额度: trade_payment_amount + trade_payment_money + trade_payment_recharge_card + trade_payment_points")
    private BigDecimal orderPaymentAmount;

    @ApiModelProperty("平台交易佣金")
    private BigDecimal orderCommissionFee;

    @ApiModelProperty("实付金额:在线支付金额,此为订单默认需要支付额度。")
    private BigDecimal tradePaymentAmount;

    @ApiModelProperty("余额支付")
    private BigDecimal tradePaymentMoney;

    @ApiModelProperty("充值卡余额支付")
    private BigDecimal tradePaymentRechargeCard;

    @ApiModelProperty("积分支付")
    private BigDecimal tradePaymentPoints;

    @ApiModelProperty("众宝支付")
    private BigDecimal tradePaymentSp;

    @ApiModelProperty("信用支付")
    private BigDecimal tradePaymentCredit;

    @ApiModelProperty("红包支付")
    private BigDecimal tradePaymentRedpack;

    @ApiModelProperty("折扣优惠")
    private BigDecimal tradeDiscount;

    @ApiModelProperty("总额虚拟:trade_order_amount + trade_discount")
    private BigDecimal tradeAmount;

    @ApiModelProperty("描述")
    private String tradeDesc;

    @ApiModelProperty("备注")
    private String tradeRemark;

    @ApiModelProperty("创建时间")
    private Long tradeCreateTime;

    @ApiModelProperty("付款时间")
    private Date TradePaidTime;

    @ApiModelProperty("是否删除")
    private Boolean tradeDelete;

    @ApiModelProperty("创建时间-开始")
    @QueryField(value = "trade_create_time", type = QueryType.GE)
    private Long tradeTimeStart;

    @ApiModelProperty("创建时间-结束")
    @QueryField(value = "trade_create_time", type = QueryType.LE)
    private Long tradeTimeEnd;

    public ConsumeTradeListReq() {
        setSidx("consume_trade_id");
        setSort(Constants.ORDER_BY_DESC);
    }

}
