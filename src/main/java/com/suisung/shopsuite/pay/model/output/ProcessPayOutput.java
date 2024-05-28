package com.suisung.shopsuite.pay.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "支付结果")
public class ProcessPayOutput {
    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("支付完成")
    private Boolean paid;
}
