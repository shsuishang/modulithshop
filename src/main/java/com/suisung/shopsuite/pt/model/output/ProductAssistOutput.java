package com.suisung.shopsuite.pt.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductAssist;
import com.suisung.shopsuite.pt.model.entity.ProductAssistItem;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductAssistOutput extends ProductAssist {

    private List<ProductAssistItem> items;

}
