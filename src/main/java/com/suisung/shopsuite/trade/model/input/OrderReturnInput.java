package com.suisung.shopsuite.trade.model.input;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemInputVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "退货申请对象", description = "退货申请")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderReturnInput {

    @ApiModelProperty("退单号")
    private String returnId;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("订单商品表编号")
    private List<OrderReturnItemInputVo> returnItems = new ArrayList<>();

    @ApiModelProperty("买家退货备注")
    private String returnBuyerMessage;

    @ApiModelProperty("联系电话")
    private String returnTel;

    @ApiModelProperty("退款理由编号")
    private Integer returnReasonId = 0;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("退款凭据(DOT)")
    private String returnItemImage;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("退货标记(BOOL):true-全退， false-单品退")
    private Boolean returnAllFlag = false;

    @ApiModelProperty("退货类型(ENUM): 0-不用退货;1-需要退货")
    private Integer returnFlag = 1;

    @ApiModelProperty("自动审核(BOOL): 0-不审核;1-自动审核")
    private Boolean reviewFlag = false;
}
