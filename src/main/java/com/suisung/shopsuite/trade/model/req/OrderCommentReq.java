package com.suisung.shopsuite.trade.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "添加评论请求数据")
public class OrderCommentReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("评论详细 -> 对应OrderCommentItemReq")
    private String item;

    @ApiModelProperty("评论内容")
    private String commentContent;

    @ApiModelProperty("描述相符")
    private BigDecimal storeDesccredit = BigDecimal.ZERO;

    @ApiModelProperty("服务评价")
    private BigDecimal storeServicecredit = BigDecimal.ZERO;

    @ApiModelProperty("物流评价")
    private BigDecimal storeDeliverycredit = BigDecimal.ZERO;

    @ApiModelProperty("匿名评价")
    private Boolean commentIsAnonymous;

}
