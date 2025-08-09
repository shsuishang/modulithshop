package com.suisung.shopsuite.shop.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.shop.model.entity.StoreBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "店铺列表")
public class StoreBaseRes extends StoreBase {

    @ApiModelProperty("国家编码")
    private String storeIntl;

    @ApiModelProperty("卖家电话")
    private String storeTel;

    @ApiModelProperty("营业时间")
    private String storeOpeningHours;

    @ApiModelProperty("打烊时间")
    private String storeCloseHours;

    @ApiModelProperty("所在区域")
    private String storeArea;

    @ApiModelProperty("所属地区(DOT)")
    private String storeDistrictId;

    @ApiModelProperty("店铺详细地址")
    private String storeAddress;



    @ApiModelProperty("用户账号")
    private String userAccount;

}
