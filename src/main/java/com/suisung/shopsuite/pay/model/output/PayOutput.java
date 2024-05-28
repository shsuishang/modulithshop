package com.suisung.shopsuite.pay.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "支付业务处理返回")
public class PayOutput {

    @ApiModelProperty("交易订单号")
    private String tradeNo;

    @ApiModelProperty("订单标题")
    private String title;

    @ApiModelProperty("支付金额")
    private int amount;

}
