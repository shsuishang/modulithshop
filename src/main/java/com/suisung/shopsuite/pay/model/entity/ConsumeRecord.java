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
import java.util.Date;

/**
 * <p>
 * 交易明细表-账户收支明细-资金流水表-账户金额变化流水
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_consume_record")
@ApiModel(value = "ConsumeRecord对象", description = "交易明细表-账户收支明细-资金流水表-账户金额变化流水")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConsumeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("支付流水号")
    @TableId(value = "consume_record_id", type = IdType.AUTO)
    private Long consumeRecordId;

    @ApiModelProperty("商户订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("所属用编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("昵称")
    @TableField("user_nickname")
    private String userNickname;

    @ApiModelProperty("货币编号")
    @TableField("currency_id")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("金额")
    @TableField("record_total")
    private BigDecimal recordTotal;

    @ApiModelProperty("金额:record_total-佣金")
    @TableField("record_money")
    private BigDecimal recordMoney;

    @ApiModelProperty("佣金:平台佣金针对销售收款")
    @TableField("record_commission_fee")
    private BigDecimal recordCommissionFee;

    @ApiModelProperty("分销佣金:针对销售收款")
    @TableField("record_distribution_commission_fee")
    private BigDecimal recordDistributionCommissionFee;

    @ApiModelProperty("年-月-日")
    @TableField("record_date")
    private Date recordDate;

    @ApiModelProperty("年")
    @TableField("record_year")
    private Integer recordYear;

    @ApiModelProperty("月")
    @TableField("record_month")
    private Integer recordMonth;

    @ApiModelProperty("日")
    @TableField("record_day")
    private Integer recordDay;

    @ApiModelProperty("标题")
    @TableField("record_title")
    private String recordTitle;

    @ApiModelProperty("描述")
    @TableField("record_desc")
    private String recordDesc;

    @ApiModelProperty("支付时间")
    @TableField("record_time")
    private Long recordTime;

    @ApiModelProperty("交易类型(ENUM):1201-购物; 1202-转账; 1203-充值; 1204-提现; 1205-销售; 1206-佣金; 1207-退货付款;1208-退货收款;1209-转账收款")
    @TableField("trade_type_id")
    private Integer tradeTypeId;

    @ApiModelProperty("支付方式(ENUM):1301-货到付款; 1302-在线支付; 1303-白条支付; 1304-现金支付; 1305-线下支付; ")
    @TableField("payment_type_id")
    private Integer paymentTypeId;

    @ApiModelProperty("支付渠道")
    @TableField("payment_channel_id")
    private Integer paymentChannelId;

    @ApiModelProperty("所属店铺")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("所属门店")
    @TableField("chain_id")
    private Integer chainId;

    @ApiModelProperty("消费类型(ENUM):1-余额支付; 2-充值卡支付; 3-积分支付; 4-信用支付; 5-红包支付")
    @TableField("payment_met_id")
    private Integer paymentMetId;

    @ApiModelProperty("状态(BOOL):1-已收款;0-作废")
    @TableField("record_enable")
    private Boolean recordEnable;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;
}
