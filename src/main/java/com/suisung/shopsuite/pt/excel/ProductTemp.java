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
public class ProductTemp {

    @ExcelProperty(index = 0, value = "*商品分类编号")
    private Integer categoryId;

    @ExcelProperty(index = 1, value = "*商品种类:1201-实物;1202-虚拟")
    private Integer kindId;

    @ExcelProperty(index = 2, value = "*商品名称")
    private String productName;

    @ExcelProperty(index = 3, value = "商品货号")
    private String productNumber;

    @ExcelProperty(index = 4, value = "商品卖点")
    private String productTips;

    @ExcelProperty(index = 5, value = "产品视频地址")
    private String productVideo;

    @ExcelProperty(index = 6, value = "商品标签编号(多个请用,隔开)")
    private String productTags;

    @ExcelProperty(index = 7, value = "*运费设置")
    private Integer transportTypeId;

    @ExcelProperty(index = 8, value = "商品描述")
    private String productDetail;

    @ExcelProperty(index = 9, value = "规格(JSON)")
    private String productSpec;

    @ExcelProperty(index = 10, value = "商品主图")
    private String itemImageDefault;

    @ExcelProperty(index = 11, value = "副图(DOT)")
    private String itemImageOther;

    @ExcelProperty(index = 12, value = "*市场价")
    private BigDecimal itemMarketPrice;

    @ExcelProperty(index = 13, value = "*商品价格")
    private BigDecimal itemUnitPrice;

    @ExcelProperty(index = 14, value = "积分价格")
    private BigDecimal itemUnitPoints;

    @ExcelProperty(index = 15, value = "*商品库存")
    private Integer itemQuantity;

    @ExcelProperty(index = 16, value = "商品SKU规格(JSON)")
    private String itemSpec;

    @ExcelProperty(index = 17, value = "品牌编号")
    private Integer brandId;

    @ExcelProperty(index = 18, value = "辅助属性(JSON)")
    private String productAssist;
}
