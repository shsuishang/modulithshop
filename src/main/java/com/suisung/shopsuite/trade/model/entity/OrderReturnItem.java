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

/**
 * <p>
 * 订单退货详情表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_return_item")
@ApiModel(value = "OrderReturnItem对象", description = "订单退货详情表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderReturnItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "order_return_item_id", type = IdType.AUTO)
    private Integer orderReturnItemId;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("退单号")
    @TableField("return_id")
    private String returnId;

    @ApiModelProperty("订单item_id")
    @TableField("order_item_id")
    private Long orderItemId;

    @ApiModelProperty("退货商品数量")
    @TableField("return_item_num")
    private Integer returnItemNum;

    @ApiModelProperty("商家备注")
    @TableField("return_item_store_remark")
    private String returnItemStoreRemark;

    @ApiModelProperty("退款理由")
    @TableField("return_reason_id")
    private Integer returnReasonId;

    @ApiModelProperty("退货申请原因")
    @TableField("return_item_note")
    private String returnItemNote;

    @ApiModelProperty("退款总额")
    @TableField("return_item_subtotal")
    private BigDecimal returnItemSubtotal;

    @ApiModelProperty("退款佣金总额")
    @TableField("return_item_commision_fee")
    private BigDecimal returnItemCommisionFee;

    @ApiModelProperty("退款凭据(DOT)")
    @TableField("return_item_image")
    private String returnItemImage;

    @ApiModelProperty("卖家处理状态(ENUM): 3100-【客户】提交退单;3105-退单审核;3110-收货确认;3115-退款确认;3120-客户】收款确认;3125-完成")
    @TableField("return_state_id")
    private Integer returnStateId;
}
