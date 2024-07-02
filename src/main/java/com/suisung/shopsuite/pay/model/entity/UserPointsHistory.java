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
 * 会员积分日志表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_user_points_history")
@ApiModel(value = "UserPointsHistory对象", description = "会员积分日志表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPointsHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "points_log_id", type = IdType.AUTO)
    private Integer pointsLogId;

    @ApiModelProperty("类型(ENUM):1-获取积分;2-消费积分;")
    @TableField("points_kind_id")
    private Integer pointsKindId;

    @ApiModelProperty("积分类型(ENUM):1-会员注册;2-会员登录;3-商品评论;4-购买商品;5-管理员操作;7-积分换购商品;8-积分兑换代金券")
    @TableField("points_type_id")
    private Integer pointsTypeId;

    @ApiModelProperty("会员编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("可用积分")
    @TableField("points_log_points")
    private BigDecimal pointsLogPoints;

    @ApiModelProperty("当前积分")
    @TableField("user_points")
    private BigDecimal userPoints;

    @ApiModelProperty("创建时间")
    @TableField("points_log_time")
    private Long pointsLogTime;

    @ApiModelProperty("描述")
    @TableField("points_log_desc")
    private String pointsLogDesc;

    @ApiModelProperty("所属店铺")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("积分日期")
    @TableField("points_log_date")
    private Date pointsLogDate;

    @ApiModelProperty("交互会员")
    @TableField("user_id_other")
    private Integer userIdOther;

    @ApiModelProperty("领取状态(BOOL):0-未领取;1-已领取")
    @TableField("points_log_state")
    private Boolean pointsLogState;

    @ApiModelProperty("关联单号")
    @TableField("ext_id")
    private String extId;

    @ApiModelProperty("用户昵称")
    @TableField(exist = false)
    private String userNickname;
}
