package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductAssist;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductAssistRes {

    @ApiModelProperty("属性分类编号")
    private Integer assistId;

    @ApiModelProperty("属性名称")
    private String assistName;

    @ApiModelProperty("属性数据")
    private List<ProductAssist> children;

}
