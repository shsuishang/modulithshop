package com.suisung.shopsuite.trade.model.req;

import com.suisung.shopsuite.common.api.StateCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderAddReq {
    @ApiModelProperty(value = "收货地址编号")
    private Integer udId = 0;

    @ApiModelProperty(value = "下单商品数据:商品编号|数量,商品编号|数量...")
    String cartId = "";

    @ApiModelProperty(value = "门店编号")
    private Integer chainId = 0;

    @ApiModelProperty(value = "活动编号")
    private Integer activityId = 0;

    @ApiModelProperty(value = "拼团编号")
    private Integer gbId = 0;

    @ApiModelProperty("配送方式")
    private Integer deliveryTypeId = StateCode.DELIVERY_TYPE_EXP;

    @ApiModelProperty(value = "来源渠道 0:正常下单;1:直播渠道")
    private Integer channelType = 0;

    @ApiModelProperty("付款方式")
    private Integer paymentTypeId;


    @ApiModelProperty("消息")
    private String orderMessage;


    @ApiModelProperty("优惠券")
    private String userVoucherIds;

    @ApiModelProperty("发票")
    private Integer userInvoiceId;

    @ApiModelProperty("买家编号")
    private Integer userId;

    @ApiModelProperty("SKU信息")
    private String productItems;
}
