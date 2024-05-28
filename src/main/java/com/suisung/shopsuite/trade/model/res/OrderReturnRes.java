package com.suisung.shopsuite.trade.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.trade.model.entity.OrderReturn;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "售后服务")
public class OrderReturnRes extends OrderReturn {

    @ApiModelProperty("退货商品总数量")
    private Integer returnNum;

    @ApiModelProperty("退款金额")
    private BigDecimal submitReturnRefundAmount;

    @ApiModelProperty("售后理由")
    private String returnReasonName;

    @ApiModelProperty("退货订单商品信息")
    private List<OrderReturnItemVo> items;

}
