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
 * 平台推广粉丝产生佣金汇总表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_distribution_generated_commission")
@ApiModel(value = "DistributionGeneratedCommission对象", description = "平台推广粉丝产生佣金汇总表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionGeneratedCommission implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("贡献佣金编号:user_id + '-' + level")
    @TableId(value = "ugc_id", type = IdType.AUTO)
    private String ugcId;

    @ApiModelProperty("推广粉丝")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("买家头像")
    @TableField(exist = false)
    private String userAvatar;

    @ApiModelProperty("买家名称")
    @TableField(exist = false)
    private String userNickname;

    @ApiModelProperty("上级推广员编号")
    @TableField("user_parent_id")
    private Integer userParentId;

    @ApiModelProperty("推广员名称")
    @TableField(exist = false)
    private String parentNickname;

    @ApiModelProperty("推广层级: 1父  ,2祖父, 记录不变，如果关系更变，则增加其它记录")
    @TableField("ugc_level")
    private Integer ugcLevel;

    @ApiModelProperty("产生佣金")
    @TableField("ugc_amount")
    private BigDecimal ugcAmount;

    @ApiModelProperty("订单数量")
    @TableField("ugc_num")
    private Integer ugcNum;

    @ApiModelProperty("注册时间")
    @TableField("user_time")
    private Long userTime;
}
