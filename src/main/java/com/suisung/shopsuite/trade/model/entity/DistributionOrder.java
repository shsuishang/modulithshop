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
 * 推广订单收益详情表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_distribution_order")
@ApiModel(value = "DistributionOrder对象", description = "推广订单收益详情表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单收益编号")
    @TableId(value = "uo_id", type = IdType.AUTO)
    private Integer uoId;

    @ApiModelProperty("用户编号:上级ID,获取佣金推广员")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("推广员佣金")
    @TableField("uo_buy_commission")
    private BigDecimal uoBuyCommission;

    @ApiModelProperty("销售员佣金")
    @TableField("uo_directseller_commission")
    private BigDecimal uoDirectsellerCommission;

    @ApiModelProperty("买家编号")
    @TableField("buyer_user_id")
    private Integer buyerUserId;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("等级")
    @TableField("uo_level")
    private Integer uoLevel;

    @ApiModelProperty("时间")
    @TableField("uo_time")
    private Long uoTime;

    @ApiModelProperty("是否有效(BOOL):0-未生效;1-有效")
    @TableField("uo_active")
    private Boolean uoActive;

    @ApiModelProperty("备注")
    @TableField("uo_remark")
    private String uoRemark;

    @ApiModelProperty("是否有效(BOOL):0-未支付;1-已支付")
    @TableField("uo_is_paid")
    private Boolean uoIsPaid;

    @ApiModelProperty("支付时间")
    @TableField("uo_paytime")
    private Long uoPaytime;

    @ApiModelProperty("收货时间")
    @TableField("uo_receivetime")
    private Long uoReceivetime;

    @ApiModelProperty("买家头像")
    @TableField(exist = false)
    private String buyerUserAvatar;

    @ApiModelProperty("买家昵称")
    @TableField(exist = false)
    private String buyerUserName;

    @ApiModelProperty("等级名称")
    @TableField(exist = false)
    private String buyerUserLevelName;

    @ApiModelProperty("推广员昵称")
    @TableField(exist = false)
    private String userNickname;
}
