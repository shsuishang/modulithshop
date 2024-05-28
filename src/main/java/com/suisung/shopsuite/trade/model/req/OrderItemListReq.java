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

/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "订单商品表分页查询")
public class OrderItemListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private Long orderItemId;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("买家编号")
    private Integer userId;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("货品编号")
    private Long itemId;

    @ApiModelProperty("商品名称")
    private String itemName;

    @ApiModelProperty("分类编号")
    private Integer categoryId;

    @ApiModelProperty("成本价")
    private BigDecimal itemCostPrice;

    @ApiModelProperty("商品价格单价")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("资源1单价")
    private BigDecimal itemUnitPoints;

    @ApiModelProperty("资源2单价")
    private BigDecimal itemUnitSp;

    @ApiModelProperty("商品实际成交价单价")
    private BigDecimal orderItemSalePrice;

    @ApiModelProperty("商品数量")
    private Integer orderItemQuantity;

    @ApiModelProperty("库存锁定(ENUM):1001-下单锁定;1002-支付锁定;")
    private Integer orderItemInventoryLock;

    @ApiModelProperty("商品图片")
    private String orderItemImage;

    @ApiModelProperty("退货数量")
    private Integer orderItemReturnNum;

    @ApiModelProperty("退款总额")
    private BigDecimal orderItemReturnSubtotal;

    @ApiModelProperty("退款金额:同意额度")
    private BigDecimal orderItemReturnAgreeAmount;

    @ApiModelProperty("商品实际总金额: order_item_sale_price * order_item_quantity")
    private BigDecimal orderItemAmount;

    @ApiModelProperty("优惠金额")
    private BigDecimal orderItemDiscountAmount;

    @ApiModelProperty("手工调整金额")
    private BigDecimal orderItemAdjustFee;

    @ApiModelProperty("积分费用")
    private BigDecimal orderItemPointsFee;

    @ApiModelProperty("赠送积分")
    private BigDecimal orderItemPointsAdd;

    @ApiModelProperty("实付金额: order_item_payment_amount =  order_item_amount - order_item_discount_amount - order_item_adjust_fee - order_item_point_fee")
    private BigDecimal orderItemPaymentAmount;

    @ApiModelProperty("评价状态(ENUM): 0-未评价;1-已评价;2-失效评价")
    private Boolean orderItemEvaluationStatus;

    @ApiModelProperty("活动类型(ENUM):0-默认;1101-加价购=搭配宝;1102-店铺满赠-小礼品;1103-限时折扣;1104-优惠套装;1105-店铺代金券coupon优惠券;1106-拼团;1107-满减送;1108-阶梯价;1109-积分换购")
    private Integer activityTypeId;

    @ApiModelProperty("促销活动ID:与activity_type_id搭配使用, 团购ID/限时折扣ID/优惠套装ID/积分兑换编号")
    private Integer activityId;

    @ApiModelProperty("礼包活动对应兑换码code")
    private String activityCode;

    @ApiModelProperty("分佣金比例百分比")
    private BigDecimal orderItemCommissionRate;

    @ApiModelProperty("佣金")
    private BigDecimal orderItemCommissionFee;

    @ApiModelProperty("退款佣金")
    private BigDecimal orderItemCommissionFeeRefund;

    @ApiModelProperty("价格策略折扣率")
    private BigDecimal policyDiscountrate;

    @ApiModelProperty("分配优惠券额度")
    private BigDecimal orderItemVoucher;

    @ApiModelProperty("分配满减额度")
    private BigDecimal orderItemReduce;

    @ApiModelProperty("备注")
    private String orderItemNote;

    @ApiModelProperty("订单附件")
    private String orderItemFile;

    @ApiModelProperty("商家附件")
    private String orderItemConfirmFile;

    @ApiModelProperty("买家确认状态(BOOL):0-为确认;1-已确认")
    private Boolean orderItemConfirmStatus;

    @ApiModelProperty("单品分销者编号")
    private Integer orderItemSalerId;

    @ApiModelProperty("分销商品编号")
    private Long itemSrcId;

    @ApiModelProperty("拆单同步状态(BOOL):0-未同步;1-已同步")
    private Boolean orderItemSupplierSync;

    @ApiModelProperty("来源订单")
    private String srcOrderId;

    @ApiModelProperty("同意退货数量")
    private Integer orderItemReturnAgreeNum;

    @ApiModelProperty("满返优惠券id")
    private Integer orderGiveId;


}
