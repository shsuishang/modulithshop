package com.suisung.shopsuite.marketing.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "优惠券")
public class VoucherVo {

    @ApiModelProperty("优惠券开始时间")
    private Long voucherStartDate;

    @ApiModelProperty("优惠券失效日期")
    private Long voucherEndDate;

    @ApiModelProperty("优惠券价格")
    private BigDecimal voucherPrice;

    @ApiModelProperty("已领取张数")
    private Integer voucherQuantityUse;

    @ApiModelProperty("优惠券图片")
    private String voucherImage;

    @ApiModelProperty("优惠券数量")
    private Integer voucherQuantity;

    @ApiModelProperty("可领数量")
    private Integer voucherQuantityFree;

    @ApiModelProperty("优惠券限制")
    private Integer voucherPreQuantity;

    @ApiModelProperty("适用商品(ENUM):1-全部商品可用;2-指定商品可用")
    private Integer voucherProductLimit;
}
