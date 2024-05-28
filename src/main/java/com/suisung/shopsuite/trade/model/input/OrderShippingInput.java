package com.suisung.shopsuite.trade.model.input;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "OrderShippingInput对象", description = "OrderShippingInput对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderShippingInput implements Serializable {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("出库单编号")
    private Integer stockBillId;

    @ApiModelProperty("发货地址")
    private Integer ssId;

    @ApiModelProperty("发货物流编号")
    private Integer logisticsId;

    @ApiModelProperty("发货时间")
    private Long logisticsTime;

    @ApiModelProperty("运单号")
    private String orderTrackingNumber;

    @ApiModelProperty("备注")
    private String logisticsExplain;

    @ApiModelProperty("发货标记(BOOL):true-默认全发， false-指定发货")
    private Boolean shippingFlag;

}
