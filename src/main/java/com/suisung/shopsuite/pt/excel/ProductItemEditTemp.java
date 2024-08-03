package com.suisung.shopsuite.pt.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@HeadRowHeight(35)
@ColumnWidth(value = 25)
public class ProductItemEditTemp {

    @ExcelProperty(index = 0, value = "*SKU编号")
    private Long itemId;

    @ExcelProperty(index = 1, value = "零售价格")
    private BigDecimal itemUnitPrice;

    @ExcelProperty(index = 2, value = "类别:2700-出库;2750-入库;")
    private Integer billTypeId;

    @ExcelProperty(index = 3, value = "库存")
    private Integer itemQuantity;

}
