package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "退货申请对象", description = "退货申请")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderReturnItemInputVo {
    @ApiModelProperty("订单商品表编号")
    private Long orderItemId;

    @ApiModelProperty("退款金额")
    private BigDecimal returnRefundAmount;

    @ApiModelProperty("退货商品数量")
    private Integer returnItemNum;
}
