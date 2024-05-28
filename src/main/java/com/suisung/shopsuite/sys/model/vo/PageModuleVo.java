package com.suisung.shopsuite.sys.model.vo;


import cn.hutool.json.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageModuleVo {

    private Integer pmId;

    @ApiModelProperty("模块名称")
    private String pmName;

    private Long pageId;

    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("颜色")
    private String pmColor;

    @ApiModelProperty("所在页面")
    private String pmType;

    @ApiModelProperty("模版")
    private String moduleId;

    @ApiModelProperty("更新时间")
    private Date pmUtime;

    @ApiModelProperty("排序")
    private Integer pmOrder;

    @ApiModelProperty("是否显示")
    private Boolean pmEnable;

    @ApiModelProperty("模块html代码")
    private String pmHtml;

    @ApiModelProperty("模块JSON代码(JSON)")
    private JSON pmJson;

    @ApiModelProperty("所属分站Id:0-总站")
    private Integer subsiteId;

    @ApiModelProperty("column_left:content_top")
    private String pmPosition;

}
