package com.suisung.shopsuite.pt.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductBase;
import com.suisung.shopsuite.pt.model.entity.ProductInfo;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductBaseAndInfoOutput {

    private ProductBase productBase;

    private ProductInfo productInfo;

}
