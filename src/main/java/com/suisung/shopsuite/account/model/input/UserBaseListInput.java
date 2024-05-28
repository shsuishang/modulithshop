package com.suisung.shopsuite.account.model.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suisung.shopsuite.core.web.model.input.BaseListInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 用户基本信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户基本信息表分页查询")
public class UserBaseListInput extends BaseListInput {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @JsonProperty("user_id")
    private Integer userId;

    @ApiModelProperty("用户账号")
    @JsonProperty("user_account")
    private String userAccount;

    @ApiModelProperty("用户密码")
    @JsonProperty("user_password")
    private String userPassword;

    @ApiModelProperty("salt值")
    @JsonProperty("user_salt")
    private String userSalt;


}
