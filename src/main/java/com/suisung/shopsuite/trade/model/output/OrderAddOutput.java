package com.suisung.shopsuite.trade.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderAddOutput extends CheckoutOutput {

    @ApiModelProperty("订单编号")
    List<String> orderIds = new ArrayList<>();

    @ApiModelProperty("是否绑定手机")
    boolean mobileIsBind = true;

    @ApiModelProperty("拼团编号")
    Integer gbId;
}