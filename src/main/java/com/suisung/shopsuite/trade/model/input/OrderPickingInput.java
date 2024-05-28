package com.suisung.shopsuite.trade.model.input;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.trade.model.vo.PickingItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "OrderPickingInput对象", description = "OrderPickingInput对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderPickingInput implements Serializable {


    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("出库商品信息")
    private List<PickingItem> items;

    @ApiModelProperty("单据金额")
    private BigDecimal stockBillAmount;

    @ApiModelProperty("业务类型")
    private Integer billTypeId;

    @ApiModelProperty("地址编号")
    private Integer warehouseId;

    @ApiModelProperty("库存类型")
    private Integer stockTransportTypeId;

    @ApiModelProperty("出库标记(BOOL):true-默认全出， false-指定出库")
    private Boolean pickingFlag = true;

}
