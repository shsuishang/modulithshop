package com.suisung.shopsuite.trade.model.input;

import com.suisung.shopsuite.trade.model.entity.OrderBase;
import com.suisung.shopsuite.trade.model.req.OrderCommentItemReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCommentInput {

    private String orderId;

    private OrderBase orderBase;

    private OrderCommentItemReq commentItemReq;

    private List<String> commentImage;

    @ApiModelProperty("描述相符")
    private BigDecimal storeDesccredit = BigDecimal.ZERO;

    @ApiModelProperty("服务评价")
    private BigDecimal storeServicecredit = BigDecimal.ZERO;

    @ApiModelProperty("物流评价")
    private BigDecimal storeDeliverycredit = BigDecimal.ZERO;

}
