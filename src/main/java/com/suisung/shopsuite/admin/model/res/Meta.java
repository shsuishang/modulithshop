package com.suisung.shopsuite.admin.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "前端路由对象属性")
public class Meta {

    @ApiModelProperty(value = "菜单编号")
    @JsonProperty("menu_id")
    private Integer menuId;

    @ApiModelProperty(value = "红点")
    private Boolean dot;

    @ApiModelProperty(value = "路由目录中文标题")
    private String title;

    @ApiModelProperty(value = "路由目录图片")
    private String icon;

    @ApiModelProperty(value = "路由目录能否被关闭")
    private Boolean noClosable;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;
}
