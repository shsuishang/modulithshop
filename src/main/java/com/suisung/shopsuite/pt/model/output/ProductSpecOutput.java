package com.suisung.shopsuite.pt.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductSpec;
import com.suisung.shopsuite.pt.model.entity.ProductSpecItem;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductSpecOutput extends ProductSpec {

    private List<ProductSpecItem> items;

}
