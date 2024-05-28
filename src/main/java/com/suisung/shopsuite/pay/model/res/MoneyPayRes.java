package com.suisung.shopsuite.pay.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "余额支付结果")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MoneyPayRes {
    @ApiModelProperty("订单编号(DOT)")
    private String orderId;

    @ApiModelProperty("状态码")
    private Integer statusCode = 200;

    @ApiModelProperty("订单已支付完成")
    private boolean paid = false;

    @ApiModelProperty("101：需要支付密码")
    private Integer code = 0;

}
