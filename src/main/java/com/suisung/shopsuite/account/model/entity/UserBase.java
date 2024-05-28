package com.suisung.shopsuite.account.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.admin.model.entity.MenuBase;
import com.suisung.shopsuite.admin.model.entity.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户基本信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("account_user_base")
@ApiModel(value = "UserBase对象", description = "用户基本信息表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty("用户密码")
    @TableField("user_password")
    private String userPassword;

    @ApiModelProperty("salt值")
    @TableField("user_salt")
    private String userSalt;

    @ApiModelProperty("角色列表")
    @TableField(exist = false)
    private List<UserRole> roles;

    @ApiModelProperty("权限列表")
    @TableField(exist = false)
    private List<MenuBase> authorities;

    @ApiModelProperty("角色编号:0-用户;2-商家;3-门店;9-平台;")
    @TableField(exist = false)
    private Integer roleId = 0;

    @ApiModelProperty("分站编号:0-总账")
    @TableField(exist = false)
    private Integer siteId = 0;

    @ApiModelProperty("店铺编号")
    @TableField(exist = false)
    private Integer storeId = 0;

    @ApiModelProperty("门店编号")
    @TableField(exist = false)
    private Integer chainId = 0;

    @ApiModelProperty("后台管理:admin=1;移动端front=0")
    @TableField(exist = false)
    private Integer clientId = 0;

    @ApiModelProperty("用户昵称")
    @TableField(exist = false)
    private String userNickname;
}
