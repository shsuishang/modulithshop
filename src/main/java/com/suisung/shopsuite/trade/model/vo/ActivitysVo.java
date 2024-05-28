package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "店铺及商品信息", description = "店铺及商品信息")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ActivitysVo implements Serializable {
    @ApiModelProperty("店铺赠品")
    List gift = new ArrayList<>();

    @ApiModelProperty("店铺满减")
    List reduction = new ArrayList<>();

    @ApiModelProperty("店铺多件折")
    List multple = new ArrayList<>();

    @ApiModelProperty("加价购")
    List bargains = new ArrayList<>();
}
