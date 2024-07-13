package com.suisung.shopsuite.account.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "站内信content")
public class MessageContentRes {

    @ApiModelProperty("消息内容")
    private String text;

    @ApiModelProperty("消息长度")
    private Integer messageLength;

    @ApiModelProperty("图片宽度")
    private Integer messageW;

    @ApiModelProperty("图片高度")
    private Integer messageH;

    @ApiModelProperty("商品价格单价")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("商品名称")
    private String productItemName;

    @ApiModelProperty("商品图片")
    private String productImage;

    @ApiModelProperty("商品编号")
    private Long itemId;
}
