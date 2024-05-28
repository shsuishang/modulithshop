package com.suisung.shopsuite.pay.model.res;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "支付宝网页支付结果")
public class AliPayRes {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("处理其它逻辑状态")
    private Integer statusCode;

    @ApiModelProperty("订单已支付完成")
    private Boolean paid;

    @ApiModelProperty("支付跳转链接")
    private String mwebUrl;

}
