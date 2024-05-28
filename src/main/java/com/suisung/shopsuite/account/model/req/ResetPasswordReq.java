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
public class ResetPasswordReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户密码")
    @NotEmpty(message = "请输入用户密码")
    private String password;

    @ApiModelProperty("用户密码")
    private String oldPassword;


    @ApiModelProperty("验证码")
    private String verifyCode;

    @ApiModelProperty("验证码KEY")
    private String verifyKey;

    @ApiModelProperty("密码是否加密")
    private Boolean encrypt = false;

    @ApiModelProperty("注册方式=>BindConnectCode")
    private Integer bindType;
}
