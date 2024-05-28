package com.suisung.shopsuite.pt.model.res;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import com.suisung.shopsuite.pt.model.entity.ProductComment;
import com.suisung.shopsuite.pt.model.entity.ProductImage;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import com.suisung.shopsuite.pt.model.output.ProductOutput;
import com.suisung.shopsuite.sys.model.entity.ContractType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDetailRes extends ProductOutput {
    @ApiModelProperty("商品描述")
    @TableField("product_detail")
    private String productDetail;

    @ApiModelProperty("SKU")
    ProductItem itemRow;

    @ApiModelProperty("Image")
    ProductImage image;

    @ApiModelProperty("默认运费")
    private BigDecimal freight;

    @ApiModelProperty("默认区域")
    private List<Integer> districtList;

    @ApiModelProperty("是否可销售")
    private Boolean ifStore;

    @ApiModelProperty("是否收藏")
    private Boolean isFavorite;

    @ApiModelProperty("最后几条评论")
    private List<ProductComment> lastComments;

    @ApiModelProperty("最后一条评论")
    private ProductComment lastComment;

    @ApiModelProperty("分类辅助属性")
    List<ProductAssistOutput> assists = new ArrayList<>();

    @ApiModelProperty("服务")
    List<ContractType> contracts = new ArrayList<>();

    @ApiModelProperty("商圈")
    List<Object> markets = new ArrayList<>();

    @ApiModelProperty("商品分类")
    private List<ProductCategory> productCategorys = new ArrayList<>();
}
