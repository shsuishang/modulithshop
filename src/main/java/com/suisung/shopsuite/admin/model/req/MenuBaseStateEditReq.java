package com.suisung.shopsuite.admin.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class MenuBaseStateEditReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单编号")
    @NotNull(message = "是否读取不能为空")
    private Integer menuId;

    @ApiModelProperty("允许关闭(BOOL):0-禁止;1-允许")
    @NotNull(message = "允许关闭不能为空")
    private Boolean menuClose;

    @ApiModelProperty("是否隐藏(BOOL):0-展示;1-隐藏")
    @NotNull(message = "是否隐藏不能为空")
    private Boolean menuHidden;

    @ApiModelProperty("是否红点(BOOL):0-隐藏;1-显示")
    @NotNull(message = "是否红点不能为空")
    private Boolean menuDot;

}
