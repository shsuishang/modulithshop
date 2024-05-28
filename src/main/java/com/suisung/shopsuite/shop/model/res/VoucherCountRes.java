package com.suisung.shopsuite.shop.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "自定义不同优惠券数量")
public class VoucherCountRes implements Serializable {

    @ApiModelProperty("所有优惠券")
    private Long voucherAllNum;

    @ApiModelProperty("线下优惠券")
    private Long voucherOfflinedNum;

    @ApiModelProperty("线上优惠券")
    private Long voucherOnlinedNum;

    @ApiModelProperty("附加优惠券")
    private Long voucherCarcouponNum;

    @ApiModelProperty("未使用优惠券")
    private Long voucherUnusedNum;

    @ApiModelProperty("已使用优惠券")
    private Long voucherUsedNum;

    @ApiModelProperty("已过期优惠券")
    private Long voucherTimeoutNum;

}
