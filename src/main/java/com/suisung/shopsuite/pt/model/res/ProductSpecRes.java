package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductSpec;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductSpecRes {

    @ApiModelProperty("规格分类编号")
    private Integer specId;

    @ApiModelProperty("规格名称")
    private String specName;

    @ApiModelProperty("规格数据")
    private List<ProductSpec> children;

}
