package com.suisung.shopsuite.common.web;

import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.common.consts.AuthConstant;
import com.suisung.shopsuite.common.utils.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * request context user
 *
 * @author Xinze
 * @since 2021-08-26 22:14:43
 */
@Data
public class ContextUser {
    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    private String userNickname;

    @ApiModelProperty("salt值")
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

    public boolean isPlatform() {
        return this.roleId != null && this.roleId == 9;
    }

    public boolean isSubsite() {
        return this.roleId != null && this.roleId == 9 && this.siteId > 0;
    }

    public boolean isStore() {
        return CheckUtil.isNotEmpty(this.storeId);
    }

    public boolean isChain() {
        return CheckUtil.isNotEmpty(this.chainId);
    }

    public boolean isAdmin() {
        return ObjectUtil.equal(chainId, AuthConstant.ADMIN_CLIENT_ID);
    }

    public boolean isMobile() {
        return ObjectUtil.equal(clientId, AuthConstant.MOBILE_CLIENT_ID);
    }

}
