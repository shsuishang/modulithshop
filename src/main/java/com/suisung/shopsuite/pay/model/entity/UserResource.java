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

import com.baomidou.mybatisplus.annotation.*;
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
 * 用户资源表-资金账户表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_user_resource")
@ApiModel(value = "UserResource对象", description = "用户资源表-资金账户表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("货币编号")
    @TableField("currency_id")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("用户资金")
    @TableField("user_money")
    private BigDecimal userMoney;

    @ApiModelProperty("冻结资金:待结算余额")
    @TableField("user_money_frozen")
    private BigDecimal userMoneyFrozen;

    @ApiModelProperty("充值卡余额")
    @TableField("user_recharge_card")
    private BigDecimal userRechargeCard;

    @ApiModelProperty("冻结充值卡:待结算")
    @TableField("user_recharge_card_frozen")
    private BigDecimal userRechargeCardFrozen;

    @ApiModelProperty("积分")
    @TableField("user_points")
    private BigDecimal userPoints;

    @ApiModelProperty("冻结积分")
    @TableField("user_points_frozen")
    private BigDecimal userPointsFrozen;

    @ApiModelProperty(value = "平台总经验")
    @TableField("user_exp")
    private Long userExp;

    @ApiModelProperty("可用信用")
    @TableField("user_credit")
    private BigDecimal userCredit;

    @ApiModelProperty("冻结额度")
    @TableField("user_credit_frozen")
    private BigDecimal userCreditFrozen;

    @ApiModelProperty("使用信用")
    @TableField("user_credit_used")
    private BigDecimal userCreditUsed;

    @ApiModelProperty("信用额度")
    @TableField("user_credit_total")
    private BigDecimal userCreditTotal;

    @ApiModelProperty("保证金")
    @TableField("user_margin")
    private BigDecimal userMargin;

    @ApiModelProperty("红包额度")
    @TableField("user_redpack")
    private BigDecimal userRedpack;

    @ApiModelProperty("红包冻结额度")
    @TableField("user_redpack_frozen")
    private BigDecimal userRedpackFrozen;

    @ApiModelProperty("积分")
    @TableField("user_sp")
    private BigDecimal userSp;

    @ApiModelProperty("冻结积分")
    @TableField("user_sp_frozen")
    private BigDecimal userSpFrozen;

    @ApiModelProperty("版本")
    @Version
    private Integer version;
}
