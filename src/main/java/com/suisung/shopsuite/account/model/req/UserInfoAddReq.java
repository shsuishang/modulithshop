package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户详细信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户详细信息表参数")
public class UserInfoAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户账号")
    private String userAccount;


    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户昵称")
    private String userNickname;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("状态(ENUM):0-锁定;1-已激活;2-未激活;")
    private Integer userState;

    @ApiModelProperty("性别(ENUM):0-保密;1-男;  2-女;")
    private Integer userGender;

    @ApiModelProperty("生日(DATE)")
    private Date userBirthday;

    @ApiModelProperty("国家编码")
    private String userIntl;

    @ApiModelProperty("手机号码(mobile)")
    private String userMobile;

    @ApiModelProperty("用户邮箱(email)")
    private String userEmail;

    @ApiModelProperty("用户等级")
    private Integer userLevelId;

    @ApiModelProperty(value = "真实姓名")
    private String userRealname;

    @ApiModelProperty(value = "身份证")
    private String userIdcard;

    @ApiModelProperty(value = "身份证图片(DOT)")
    private String userIdcardImages;

    @ApiModelProperty("认证状态(ENUM):0-未认证;1-待审核;2-认证通过;3-认证失败")
    private Integer userIsAuthentication;

    @ApiModelProperty("用户标签(DOT)")
    private String tagIds;

    @ApiModelProperty("用户来源(ENUM):2310-其它;2311-pc;2312-H5;2313-APP;2314-小程序;2315-公众号")
    private Integer userFrom;

    @ApiModelProperty("新人标识(BOOL):0-不是;1-是")
    private Boolean userNew;

    @ApiModelProperty("上级用户编号")
    private Integer userParentId;
}
