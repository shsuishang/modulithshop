package com.suisung.shopsuite.marketing.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "活动规则")
public class PageDataVo {

    @ApiModelProperty("主键")
    private Integer itemId;

    @ApiModelProperty("标题")
    private String itemName;

    @ApiModelProperty("产品图片")
    private String productImage;

    @ApiModelProperty("销售价")
    private Integer groupSalePrice;

    @ApiModelProperty("市场价")
    private Integer itemUnitPrice;

    @ApiModelProperty("开团人数")
    private Integer groupQuantity;

    @ApiModelProperty("参团人数")
    private Integer groupUserAmount;

    @ApiModelProperty("优惠券图片")
    private String voucherImage;

    @ApiModelProperty("优惠券价格")
    private Integer voucherPrice;

    @ApiModelProperty("活动开始时间")
    private String activityStarttime;

    @ApiModelProperty("活动结束时间")
    private String activityEndtime;

    @ApiModelProperty("优惠券数量")
    private Integer voucherPreQuantity;

    @ApiModelProperty("售出数量")
    private Integer productSaleNum;


}
