package com.suisung.shopsuite.common.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Jwt载体
 *
 * @author Xinze
 * @since 2021-09-03 00:11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtSubject implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("用户盐值")
    private String userSalt;

    @ApiModelProperty("角色编号:0-用户;2-商家;3-门店;9-平台;")
    private Integer roleId;

    @ApiModelProperty("分站编号:0-总账")
    private Integer siteId;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("门店编号")
    private Integer chainId;

    @ApiModelProperty("后台管理:admin=1;移动端front=0")
    private Integer clientId;
}
