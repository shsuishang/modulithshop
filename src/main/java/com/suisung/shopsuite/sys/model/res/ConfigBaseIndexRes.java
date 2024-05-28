package com.suisung.shopsuite.sys.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@ApiModel(value = "站点设置前端对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConfigBaseIndexRes {


    @ApiModelProperty("分组编号")
    private Integer configTypeId;

    @ApiModelProperty("分组名称")
    private String configTypeName;

    @ApiModelProperty("配置集合")
    private List<Map<String, Object>> items;


}
