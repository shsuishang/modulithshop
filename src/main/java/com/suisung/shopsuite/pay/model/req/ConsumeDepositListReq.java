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
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 支付表-支付回调callback使用-确认付款
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "支付表-支付回调callback使用-确认付款分页查询")
public class ConsumeDepositListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("支付流水号")
    private Long depositId;

    @ApiModelProperty("商城支付编号")
    private String depositNo;

    @ApiModelProperty("交易号:支付宝etc")
    private String depositTradeNo;

    @ApiModelProperty("商户网站唯一订单号(DOT):合并支付则为多个订单号, 没有创建联合支付交易号")
    private String orderId;

    @ApiModelProperty("支付渠道")
    private Integer paymentChannelId;

    @ApiModelProperty("商品名称")
    @QueryField(type = QueryType.LIKE)
    private String depositSubject;

    @ApiModelProperty("支付方式(ENUM):1301-货到付款; 1302-在线支付; 1303-白条支付; 1304-现金支付; 1305-线下支付; ")
    private Integer depositPaymentType;

    @ApiModelProperty("交易状态")
    private String depositTradeStatus;

    @ApiModelProperty("卖家户号:支付宝etc")
    private String depositSellerId;

    @ApiModelProperty("卖家支付账号")
    private String depositSellerEmail;

    @ApiModelProperty("买家支付用户号")
    private String depositBuyerId;

    @ApiModelProperty("买家支付账号")
    private String depositBuyerEmail;

    @ApiModelProperty("货币编号")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    private String currencySymbolLeft;

    @ApiModelProperty("交易金额")
    private BigDecimal depositTotalFee;

    @ApiModelProperty("购买数量")
    private Integer depositQuantity;

    @ApiModelProperty("商品单价")
    private BigDecimal depositPrice;

    @ApiModelProperty("商品描述")
    private String depositBody;

    @ApiModelProperty("交易创建时间")
    private Date depositGmtCreate;

    @ApiModelProperty("交易付款时间")
    private Date depositGmtPayment;

    private Date depositGmtClose;

    @ApiModelProperty("是否调整总价")
    private Boolean depositIsTotalFeeAdjust;

    @ApiModelProperty("是否使用红包买家")
    private Boolean depositUseCoupon;

    @ApiModelProperty("折扣")
    private BigDecimal depositDiscount;

    @ApiModelProperty("通知时间")
    private Long depositNotifyTime;

    @ApiModelProperty("通知类型")
    private String depositNotifyType;

    @ApiModelProperty("通知校验编号")
    private String depositNotifyId;

    @ApiModelProperty("签名方式")
    private String depositSignType;

    @ApiModelProperty("签名")
    private String depositSign;

    @ApiModelProperty("额外参数")
    private String depositExtraParam;

    @ApiModelProperty("支付")
    private String depositService;

    @ApiModelProperty("支付状态:0-默认; 1-接收正确数据处理完逻辑; 9-异常订单")
    private Integer depositState;

    @ApiModelProperty("是否同步(BOOL):0-同步; 1-异步回调使用")
    private Boolean depositAsync;

    @ApiModelProperty("收款确认(BOOL):0-未确认;1-已确认")
    private Boolean depositReview;

    @ApiModelProperty("是否作废(BOOL):1-正常; 2-作废")
    private Boolean depositEnable;

    @ApiModelProperty("所属店铺:直接交易起作用")
    private Integer storeId;

    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("所属门店:直接交易起作用")
    private Integer chainId;

    @ApiModelProperty("所属分站:直接交易起作用")
    private Integer subsiteId;

    @ApiModelProperty("支付时间")
    private Long depositTime;

    public ConsumeDepositListReq() {
        setSidx("deposit_id");
        setDesc();
    }
}
