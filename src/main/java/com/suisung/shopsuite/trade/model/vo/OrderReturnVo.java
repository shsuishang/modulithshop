package com.suisung.shopsuite.trade.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.trade.model.entity.OrderReturn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "退货订单详情")
public class OrderReturnVo extends OrderReturn {

    @ApiModelProperty("客户名称")
    private String buyerUserName;

    @ApiModelProperty("联系人")
    private String daName;

    @ApiModelProperty("手机号码")
    private String daMobile;

    @ApiModelProperty("省份")
    private String daProvince;

    @ApiModelProperty("市")
    private String daCity;

    @ApiModelProperty("县区")
    private String daCounty;

    @ApiModelProperty("详细地址")
    private String daAddress;

    @ApiModelProperty("商品列表")
    private List<OrderReturnItemVo> returnItemList;

}
