package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.shop.model.res.UserVoucherRes;
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
@ApiModel(value = "店铺及商品信息", description = "店铺及商品信息")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreItemVo implements Serializable {
    @ApiModelProperty("店铺编号")
    Integer storeId = 0;

    @ApiModelProperty("店铺名称")
    String storeName;

    @ApiModelProperty("商品信息")
    List<ProductItemVo> items = new ArrayList<>();

    @ApiModelProperty("店铺活动")
    ActivitysVo activitys = new ActivitysVo();

    //过程
    @ApiModelProperty("非排他活动")
    ActivityBase activityBase = null;

    @ApiModelProperty("提货券")
    List<ActivitysVo> redemptionItems = new ArrayList<>();

    @ApiModelProperty("优惠券")
    List<UserVoucherRes> voucherItems = new ArrayList<>();

    @ApiModelProperty("商品原价总价")
    BigDecimal productAmount;

    @ApiModelProperty("运费总价")
    BigDecimal freightAmount;

    @ApiModelProperty("还差N免运费")
    BigDecimal freightFreeBalance;

    @ApiModelProperty("优惠总额度")
    BigDecimal discountAmount;

    @ApiModelProperty("单品优惠总价:单纯商品优惠后累加")
    BigDecimal moneyItemAmount;

    @ApiModelProperty("商品最终总价:单品优惠总价+运费-代金券")
    BigDecimal moneyAmount;

    @ApiModelProperty("需要总积分")
    BigDecimal pointsAmount;

    @ApiModelProperty("需要总积分2")
    BigDecimal spAmount;

    @ApiModelProperty("优惠券编号")
    private Integer userVoucherId;

    @ApiModelProperty("代金券")
    BigDecimal voucherAmount = BigDecimal.ZERO;

    @ApiModelProperty("订单类型")
    Integer kindId;

    @ApiModelProperty("是否虚拟")
    Boolean isVirtual;
}
