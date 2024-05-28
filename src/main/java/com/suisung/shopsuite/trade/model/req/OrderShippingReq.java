package com.suisung.shopsuite.trade.model.req;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "订单发货审核接口")
public class OrderShippingReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @NotBlank(message = "请输入订单编号")
    private String orderId;

    @ApiModelProperty("出库单编号")
    private String stockBillId;

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

    @ApiModelProperty("发货标记:true-默认全发， false-指定发货")
    private Boolean shippingFlag;
}