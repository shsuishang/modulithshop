package com.suisung.shopsuite.admin.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.annotation.QueryField;
import com.suisung.shopsuite.core.annotation.QueryType;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "系统菜单表分页查询")
public class MenuTreeReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单名称")
    @QueryField(type = QueryType.LIKE)
    private String menuTitle;

    @ApiModelProperty("查询标识")
    private Integer type = 1;

    @ApiModelProperty("权限角色")
    private Integer userRoleId;
}
