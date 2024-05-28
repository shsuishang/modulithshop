package com.suisung.shopsuite.pt.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductOutput extends ProductIndex {
    //base
    @ApiModelProperty("商品卖点:商品广告词")
    private String productTips;

    @ApiModelProperty("商品主图")
    private String productImage;

    @ApiModelProperty("产品视频 ")
    private String productVideo;

    @ApiModelProperty("选择售卖区域:完成售卖区域及运费设置")
    private Integer transportTypeId;

    @ApiModelProperty("每人限购")
    private Integer productBuyLimit;

    @ApiModelProperty(value = "平台佣金比率")
    private BigDecimal productCommissionRate;

    //info
    @ApiModelProperty("规格(JSON)-规格、规格值、goods_id  规格不需要全选就可以添加对应数据[{'id' : spec_id, 'name' : spec_name, 'item':[{'id' : spec_item_id, 'name' : spec_item_name}, {'id' : spec_item_id, 'name' : spec_item_name}]},{'id' : spec_id, 'name' : spec_name, 'item':[{'id' : spec_item_id, 'name' : spec_item_name}, {'id' : spec_item_id, 'name' : spec_item_name}]}]")
    private String productSpec;

    @ApiModelProperty("商品SKU(JSON):{'uniq_id':[item_id, price, url]}")
    private String productUniqid;


    @ApiModelProperty("商品SKU全名")
    private String productItemName;


    //
    @ApiModelProperty("默认SKU")
    private Long itemId;

    @ApiModelProperty("SKU信息")
    private List<ProductItem> items;

    @ApiModelProperty("活动信息")
    private Object activity;
}
