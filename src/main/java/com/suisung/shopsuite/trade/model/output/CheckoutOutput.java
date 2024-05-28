package com.suisung.shopsuite.trade.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.account.model.entity.UserDeliveryAddress;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.vo.StoreItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "CheckoutVo对象", description = "CheckoutVo对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CheckoutOutput implements Serializable {

    @ApiModelProperty("店铺信息")
    List<StoreItemVo> items = new ArrayList<>();

    @ApiModelProperty("商品原价总价")
    BigDecimal orderProductAmount;

    @ApiModelProperty("单品优惠总价")
    BigDecimal orderItemAmount;

    @ApiModelProperty("运费总价")
    BigDecimal orderFreightAmount;

    @ApiModelProperty("优惠总额度=orderProductAmount-orderMoneyAmount")
    BigDecimal orderDiscountAmount;

    @ApiModelProperty("商品最终总价：单品优惠总价+运费-代金券")
    BigDecimal orderMoneyAmount;

    @ApiModelProperty("订单需要总积分")
    BigDecimal orderPointsAmount;

    @ApiModelProperty("订单需要总积分2")
    BigDecimal orderSpAmount;

    //前端使用跳转判断
    @ApiModelProperty("是否支付完成")
    Boolean isPaid = false;

    @ApiModelProperty("买家编号")
    private Integer userId;

    @ApiModelProperty("地址信息")
    private UserDeliveryAddress userDeliveryAddress;

    @ApiModelProperty("输入参数")
    private CheckoutInput in;
}