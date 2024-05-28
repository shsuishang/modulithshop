package com.suisung.shopsuite.trade.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionOrderVo implements Serializable {
    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("卖家店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("买家编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("平台交易佣金")
    private BigDecimal orderCommissionFee;

    @ApiModelProperty("销售员编号:用户编号")
    private Integer salespersonId;

    @ApiModelProperty("分销商编号:用户编号")
    private Integer distributorUserId;
}
