package com.suisung.shopsuite.admin.model.entity;

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
 * 用户基本信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("admin_user_role")
@ApiModel(value = "UserRole对象", description = "权限组表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色编号")
    @TableId(value = "user_role_id", type = IdType.AUTO)
    private Integer userRoleId;

    @ApiModelProperty("角色名称")
    @TableField("user_role_name")
    private String userRoleName;

    @ApiModelProperty("角色名称")
    @TableField("user_role_code")
    private String userRoleCode;

    @ApiModelProperty("请求列表(DOT)")
    @TableField("menu_ids")
    private String menuIds;

    @ApiModelProperty("创建时间")
    @TableField("user_role_ctime")
    private Date userRoleCTime;

    @ApiModelProperty("创建时间")
    @TableField("user_role_utime")
    private Date userRoleUtime;

    @ApiModelProperty("是否内置")
    @TableField("user_role_buildin")
    private Boolean userRoleBuildin;
}