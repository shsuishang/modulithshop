package com.suisung.shopsuite.pt.model.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDetailReq {
    @ApiModelProperty("SKU编号")
    Long itemId;

    @ApiModelProperty("配送地区")
    private Integer districtId;
}
