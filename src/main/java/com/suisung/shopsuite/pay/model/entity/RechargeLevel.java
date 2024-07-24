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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 定额充值表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_recharge_level")
@ApiModel(value = "RechargeLevel对象", description = "定额充值表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RechargeLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "recharge_level_id", type = IdType.AUTO)
    private Integer rechargeLevelId;

    @ApiModelProperty("额度名称")
    @TableField("recharge_level_name")
    private String rechargeLevelName;

    @ApiModelProperty("图片")
    @TableField("recharge_level_img")
    private String rechargeLevelImg;

    @ApiModelProperty("充值额度")
    @TableField("recharge_level_value")
    private Integer rechargeLevelValue;

    @ApiModelProperty("额外赠送")
    @TableField("recharge_level_gift")
    private BigDecimal rechargeLevelGift;

    @ApiModelProperty("有效期:按天计算")
    @TableField("recharge_level_validity")
    private Integer rechargeLevelValidity;

    @ApiModelProperty("每日本金利息百分比")
    @TableField("recharge_level_rate")
    private BigDecimal rechargeLevelRate;

    @ApiModelProperty("修改时间")
    @TableField("recharge_level_time")
    private Date rechargeLevelTime;

    @ApiModelProperty("重复购买(BOOL):0-购买一次;1-不限制")
    @TableField("recharge_level_repeat")
    private Boolean rechargeLevelRepeat;

    @ApiModelProperty("描述")
    @TableField("recharge_level_description")
    private String rechargeLevelDescription;

    @ApiModelProperty("有效时长单位为年")
    @TableField("recharge_level_duration")
    private BigDecimal rechargeLevelDuration;
}
