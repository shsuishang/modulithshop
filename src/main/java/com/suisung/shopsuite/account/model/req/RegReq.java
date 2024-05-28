package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "登录参数")
public class RegReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户账号")
    @NotEmpty(message = "请输入用户账号")
    private String userAccount;

    @ApiModelProperty("用户密码")
    @NotEmpty(message = "请输入用户密码")
    private String password;

    @ApiModelProperty("验证码")
    private String verifyCode;

    @ApiModelProperty("验证码KEY")
    private String verifyKey;

    @ApiModelProperty("密码是否加密")
    private Boolean encrypt = false;

    @ApiModelProperty("注册方式=>BindConnectCode")
    private Integer bindType;

    @ApiModelProperty("活动编号")
    private Integer activityId;

    @ApiModelProperty("来源用户编号")
    private Integer sourceUserId;

    @ApiModelProperty("渠道码")
    private String sourceUccCode;
}
