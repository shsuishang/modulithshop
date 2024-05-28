package com.suisung.shopsuite.sys.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageMenuVo {

    @ApiModelProperty("编号")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("是否显示")
    @JsonProperty("isShow")
    private Boolean isShow;

    @ApiModelProperty("分类")
    private Integer cat;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("键")
    @JsonProperty("FeatureKey")
    private String featureKey;

    @ApiModelProperty("访问路径")
    private String url;
}
