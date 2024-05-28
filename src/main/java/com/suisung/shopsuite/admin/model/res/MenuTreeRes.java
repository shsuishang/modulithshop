package com.suisung.shopsuite.admin.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "前端路由对象")
public class MenuTreeRes {

    @ApiModelProperty(value = "路径")
    @JsonProperty("menu_id")
    private Integer menuId;

    @ApiModelProperty("菜单父编号")
    @JsonProperty("menu_parent_id")
    private Integer menuParentId;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("组件名称")
    @JsonProperty("menu_name")
    private String menuName;

    @ApiModelProperty("组件路由")
    @JsonProperty("menu_path")
    private String menuPath;

    @ApiModelProperty("组件路径")
    @JsonProperty("menu_component")
    private String menuComponent;

    @ApiModelProperty("菜单名称")
    @JsonProperty("menu_title")
    private String menuTitle;

    @ApiModelProperty("图标设置")
    @JsonProperty("menu_icon")
    private String menuIcon;

    @ApiModelProperty("是否红点(BOOL):0-隐藏;1-显示")
    @JsonProperty("menu_dot")
    private Boolean menuDot;

    @ApiModelProperty("菜单标签")
    @JsonProperty("menu_bubble")
    private String menuBubble;

    @ApiModelProperty("菜单排序")
    @JsonProperty("menu_sort")
    private Integer menuSort;

    @ApiModelProperty("权限标识:后端地址")
    @JsonProperty("menu_permission")
    private String menuPermission;

    @ApiModelProperty("系统内置(BOOL):1-是; 0-否")
    @JsonProperty("menu_buildin")
    private Boolean menuBuildin;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "菜单类型")
    @JsonProperty("menu_type")
    private Integer menuType;

    @ApiModelProperty(value = "路由目录属性")
    private Meta meta;

    /*private String children;*/
    @JsonProperty("menu_hidden")
    private boolean menuHidden = false;
    @ApiModelProperty(value = "路由子集")
    private List<MenuTreeRes> children;
}
