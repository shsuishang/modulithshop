package com.suisung.shopsuite.account.model.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户基本信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户基本信息表参数")
public class UserBaseAddInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("salt值")
    private String userSalt;


}
