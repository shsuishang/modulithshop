package com.suisung.shopsuite.trade.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "海报DTO", description = "海报DTO")
public class PosterInput {
    @ApiModelProperty(value = "推广员编号")
    private Integer userId;

    @ApiModelProperty(value = "海报图网址")
    private String posterImg;

    @ApiModelProperty(value = "文字标题")
    private String posterTitle;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal posterPrice;

    @ApiModelProperty(value = "二维码网址")
    private String path;

    @ApiModelProperty(value = "海报类型:1-H5;2-小程序")
    private Integer posterType;

    @ApiModelProperty(value = "海报名称")
    private String posterName;
}
