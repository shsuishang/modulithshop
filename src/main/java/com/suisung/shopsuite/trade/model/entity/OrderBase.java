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
 * 订单详细信息-检索不分表也行，cache
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_base")
@ApiModel(value = "OrderBase对象", description = "订单详细信息-检索不分表也行，cache")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "order_id", type = IdType.AUTO)
    private String orderId;

    @ApiModelProperty("订单编号")
    @TableField("order_number")
    private String orderNumber;

    @ApiModelProperty("下单时间")
    @TableField("order_time")
    private Date orderTime;

    @ApiModelProperty("商品原价总和:商品发布原价")
    @TableField("order_product_amount")
    private BigDecimal orderProductAmount;

    @ApiModelProperty("应付金额:order_product_amount - order_discount_amount + order_shipping_fee - order_voucher_price - order_points_fee - order_adjust_fee")
    @TableField("order_payment_amount")
    private BigDecimal orderPaymentAmount;

    @ApiModelProperty("货币编号")
    @TableField("currency_id")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty("买家编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("买家昵称")
    @TableField("user_nickname")
    private String userNickname;

    @ApiModelProperty("订单状态(LIST):2011-待订单审核;2013-待财务审核;2020-待配货/待出库审核;2030-待发货;2040-已发货/待收货确认;2060-已完成/已签收;2070-已取消/已作废;")
    @TableField("order_state_id")
    private Integer orderStateId;
}
