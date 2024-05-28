package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductBrand;
import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import com.suisung.shopsuite.sys.model.entity.ContractType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductCategoryFilterRes implements Serializable {
    @ApiModelProperty("分类辅助属性")
    List<ProductAssistOutput> assists = new ArrayList<>();

    @ApiModelProperty("服务")
    List<ContractType> contracts = new ArrayList<>();

    @ApiModelProperty("商圈")
    List<Object> markets = new ArrayList<>();

    @ApiModelProperty("下级分类")
    private List<ProductCategory> children = new ArrayList<>();

    @ApiModelProperty("上级分类")
    private List<ProductCategory> parent = new ArrayList<>();

    @ApiModelProperty("品牌")
    private List<ProductBrand> brands = new ArrayList<>();

    @ApiModelProperty("信息")
    private ProductCategory info;
}
