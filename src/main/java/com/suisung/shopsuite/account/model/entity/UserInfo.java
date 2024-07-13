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
package com.suisung.shopsuite.account.model.entity;

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
import java.util.Date;

/**
 * <p>
 * 用户详细信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("account_user_info")
@ApiModel(value = "UserInfo对象", description = "用户详细信息表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    @TableField("user_nickname")
    private String userNickname;

    @ApiModelProperty("用户头像")
    @TableField("user_avatar")
    private String userAvatar;

    @ApiModelProperty("状态(ENUM):0-锁定;1-已激活;2-未激活;")
    @TableField("user_state")
    private Integer userState;

    @ApiModelProperty("手机号码(mobile)")
    @TableField("user_mobile")
    private String userMobile;

    @ApiModelProperty("国家编码")
    @TableField("user_intl")
    private String userIntl;

    @ApiModelProperty("性别(ENUM):0-保密;1-男;  2-女;")
    @TableField("user_gender")
    private Integer userGender;

    @ApiModelProperty("生日(DATE)")
    @TableField("user_birthday")
    private Date userBirthday;

    @ApiModelProperty("用户邮箱(email)")
    @TableField("user_email")
    private String userEmail;

    @ApiModelProperty("等级编号")
    @TableField("user_level_id")
    private Integer userLevelId;

    @ApiModelProperty(value = "真实姓名")
    @TableField("user_realname")
    private String userRealname;

    @ApiModelProperty(value = "身份证")
    @TableField("user_idcard")
    private String userIdcard;

    @ApiModelProperty(value = "身份证图片(DOT)")
    @TableField("user_idcard_images")
    private String userIdcardImages;

    @ApiModelProperty("认证状态(ENUM):0-未认证;1-待审核;2-认证通过;3-认证失败")
    @TableField("user_is_authentication")
    private Integer userIsAuthentication;

    @ApiModelProperty("用户标签(DOT)")
    @TableField("tag_ids")
    private String tagIds;

    @ApiModelProperty("用户来源(ENUM):2310-其它;2311-pc;2312-H5;2313-APP;2314-小程序;2315-公众号")
    @TableField("user_from")
    private Integer userFrom;

    @ApiModelProperty("新人标识(BOOL):0-不是;1-是")
    @TableField("user_new")
    private Boolean userNew;

    @ApiModelProperty("上级用户编号")
    @TableField(exist = false)
    private Integer userParentId;

    @ApiModelProperty("平台标识")
    @TableField(exist = false)
    private Integer puid;

    @ApiModelProperty("用户标识")
    @TableField(exist = false)
    private Integer suid;
}
