package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "编号管理表参数")
public class PageDetailReq implements Serializable {
    @ApiModelProperty("页面编号")
    private Long pageId;

    @ApiModelProperty("主页类型")
    private String pageIndex;

    @ApiModelProperty("分类编号")
    private Integer categoryId;

}
