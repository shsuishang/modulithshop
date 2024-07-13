package com.suisung.shopsuite.pt.model.input;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDetailInput {
    @ApiModelProperty("用户编号")
    Integer userId;

    @ApiModelProperty("SKU编号")
    Long itemId;

    @ApiModelProperty("配送地区")
    private Integer districtId;

    @ApiModelProperty("拼团活动编号")
    private Integer gbId;

    @ApiModelProperty("A+B组合套餐活动编号")
    private Integer gifgbagId;

    @ApiModelProperty("砍价活动编号")
    private Integer cutpriceId;
}
