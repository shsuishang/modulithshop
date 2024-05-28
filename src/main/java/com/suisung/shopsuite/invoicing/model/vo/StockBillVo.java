package com.suisung.shopsuite.invoicing.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.invoicing.model.entity.StockBill;
import com.suisung.shopsuite.invoicing.model.entity.StockBillItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StockBillVo extends StockBill {
    @ApiModelProperty("出库订单SKU")
    private List<StockBillItem> items;
}
