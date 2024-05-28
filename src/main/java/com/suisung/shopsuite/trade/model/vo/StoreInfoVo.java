package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.shop.model.entity.StoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "店铺及商品信息", description = "店铺及商品信息")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreInfoVo extends StoreInfo implements Serializable {

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("店铺等级")
    private Integer storeGradeId;

    @ApiModelProperty("店铺logo")
    private String storeLogo;

    @ApiModelProperty("纬度")
    private Double storeLatitude;

    @ApiModelProperty("经度")
    private Double storeLongitude;

    @ApiModelProperty("配送区域(DOT)")
    private String storeDeliverDistrictId;

    @ApiModelProperty("是否自营(ENUM): 1-自营;0-非自营")
    private Boolean storeIsSelfsupport;

    @ApiModelProperty("店铺类型(ENUM): 1-卖家店铺; 2-供应商店铺")
    private Integer storeType;

    @ApiModelProperty("店铺状态(BOOL):0-关闭;  1-运营中")
    private Boolean storeIsOpen;

    @ApiModelProperty("店铺分类编号")
    private Integer storeCategoryId;

    @ApiModelProperty("免费服务(DOT)")
    private String storeO2oTags;

    @ApiModelProperty("是否O2O(BOOL):0-否;1-是")
    private Boolean storeO2oFlag;

    @ApiModelProperty("所属商圈(DOT)")
    private String storeCircle;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;

}
