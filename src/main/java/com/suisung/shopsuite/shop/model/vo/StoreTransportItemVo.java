package com.suisung.shopsuite.shop.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.shop.model.entity.StoreTransportItem;
import com.suisung.shopsuite.shop.model.entity.StoreTransportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreTransportItemVo extends StoreTransportType {
    @ApiModelProperty("运费项目")
    private StoreTransportItem item;
}
