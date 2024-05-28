package com.suisung.shopsuite.pay.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPointsVo implements Serializable {
    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("积分")
    private BigDecimal points;

    @ApiModelProperty("积分类型")
    private Integer pointsTypeId;

    @ApiModelProperty("描述")
    private String pointsLogDesc;

    @ApiModelProperty("相关用户")
    private Integer userIdOther;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("卖家店铺编号")
    private Integer storeId = 0;
}
