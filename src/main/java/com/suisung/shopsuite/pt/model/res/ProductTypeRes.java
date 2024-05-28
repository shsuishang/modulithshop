package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductBrand;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import com.suisung.shopsuite.pt.model.output.ProductSpecOutput;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductTypeRes {

    private List<ProductAssistOutput> assists;

    private List<ProductBrand> brands;

    private List<ProductSpecOutput> specs;

}
