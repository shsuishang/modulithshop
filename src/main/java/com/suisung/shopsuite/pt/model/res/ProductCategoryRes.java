package com.suisung.shopsuite.pt.model.res;

import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryRes extends ProductCategory {

    @ApiModelProperty("子节点列表")
    private List<ProductCategoryRes> children;

}
