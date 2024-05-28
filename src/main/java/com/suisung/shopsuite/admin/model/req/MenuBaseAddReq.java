package com.suisung.shopsuite.admin.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "系统菜单表参数")
public class MenuBaseAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单父编号")
    private Integer menuParentId;

    @ApiModelProperty("菜单名称")
    private String menuTitle;

    @ApiModelProperty("页面网址")
    private String menuUrl;

    @ApiModelProperty("组件名称")
    private String menuName;

    @ApiModelProperty("组件路由")
    private String menuPath;

    @ApiModelProperty("组件路径")
    private String menuComponent;

    @ApiModelProperty("重定向")
    private String menuRedirect;

    @ApiModelProperty("允许关闭(BOOL):0-禁止;1-允许")
    private Boolean menuClose;

    @ApiModelProperty("是否隐藏(BOOL):0-展示;1-隐藏")
    private Boolean menuHidden;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    private Boolean menuEnable;

    @ApiModelProperty("样式class")
    private String menuClass;

    @ApiModelProperty("图标设置")
    private String menuIcon;

    @ApiModelProperty("是否红点(BOOL):0-隐藏;1-显示")
    private Boolean menuDot;

    @ApiModelProperty("菜单标签")
    private String menuBubble;

    @ApiModelProperty("菜单排序")
    private Integer menuSort;

    @ApiModelProperty("菜单类型(LIST):0-按钮;1-菜单")
    private Boolean menuType;

    @ApiModelProperty("备注")
    private String menuNote;

    @ApiModelProperty("功能开启:设置config_key")
    private String menuFunc;

    @ApiModelProperty("角色类型(LIST):1-平台;2-商户;3-门店")
    private Integer menuRole;

    @ApiModelProperty("url参数")
    private String menuParam;

    @ApiModelProperty("权限标识:后端地址")
    private String menuPermission;

    @ApiModelProperty("最后更新时间")
    private Date menuTime;


}
