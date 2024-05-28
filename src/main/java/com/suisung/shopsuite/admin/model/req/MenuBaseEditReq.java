package com.suisung.shopsuite.admin.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "系统菜单表参数")
public class MenuBaseEditReq extends MenuBaseAddReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单编号")
    private Integer menuId;


}
