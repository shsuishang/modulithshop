package com.suisung.shopsuite.trade.model.req;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "订单出库审核接口")
public class OrderPickingReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @NotBlank(message = "请输入订单编号")
    private String orderId;

    @ApiModelProperty("出库商品信息")
    private String items;

    @ApiModelProperty("业务类型")
    private Integer billTypeId;

    @ApiModelProperty("地址编号")
    private Integer warehouseId;

    @ApiModelProperty("库存类型")
    private Integer stockTransportTypeId;

    @ApiModelProperty("出库标记:true-默认全出， false-指定出库")
    private Boolean pickingFlag = true;
}