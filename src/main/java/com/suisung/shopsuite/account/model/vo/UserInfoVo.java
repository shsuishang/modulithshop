package com.suisung.shopsuite.account.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "指定导出用户详细信息", description = "指定导出用户详细信息")
public class UserInfoVo {

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    private String userNickname;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("状态")
    private String userState;

    @ApiModelProperty("手机号码")
    private String userMobile;

    @ApiModelProperty("国家编码")
    private String userIntl;

    @ApiModelProperty("性别")
    private String userGender;

    @ApiModelProperty("生日")
    private String userBirthday;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("等级编号")
    private Integer userLevelId;

    @ApiModelProperty("认证状态")
    private String userIsAuthentication;

    @ApiModelProperty("用户标签")
    private String tagIds;

    @ApiModelProperty("用户来源")
    private Integer userFrom;

    @ApiModelProperty("新人标识")
    private String userNew;

}
