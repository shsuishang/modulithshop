package com.suisung.shopsuite.trade.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "订单数量")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderNumOutput {

    @ApiModelProperty("完成订单数")
    private Long finNum = 0L;

    @ApiModelProperty("完成订单数-实物")
    private Long finNumEntity = 0L;

    @ApiModelProperty("完成订单数-虚拟")
    private Long finNumV = 0L;


    @ApiModelProperty("取消订单数")
    private Long cancelNum = 0L;

    @ApiModelProperty("取消订单数-实物")
    private Long cancelNumEntity = 0L;

    @ApiModelProperty("取消订单数-虚拟")
    private Long cancelNumV = 0L;

    @ApiModelProperty("待发货货订单数")
    private Long waitShippingNum = 0L;

    @ApiModelProperty("待发货货订单数-实物")
    private Long waitShippingNumEntity = 0L;

    @ApiModelProperty("待发货货订单数-虚拟")
    private Long waitShippingNumV = 0L;

    @ApiModelProperty("已发货订单数")
    private Long shipNum = 0L;

    @ApiModelProperty("已发货订单数-实物")
    private Long shipNumEntity = 0L;

    @ApiModelProperty("已发货订单数-虚拟")
    private Long shipNumV = 0L;

    @ApiModelProperty("等待支付订单数")
    private Long waitPayNum = 0L;

    @ApiModelProperty("等待支付订单数-实物")
    private Long waitPayNumEntity = 0L;

    @ApiModelProperty("等待支付订单数-虚拟")
    private Long waitPayNumV = 0L;


    @ApiModelProperty("售后订单数")
    private Long returningNum = 0L;
}
