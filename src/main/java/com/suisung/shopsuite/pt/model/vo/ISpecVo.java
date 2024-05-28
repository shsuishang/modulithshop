package com.suisung.shopsuite.pt.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ISpecVo {
    @ApiModelProperty("规格编号")
    private Integer id;

    @ApiModelProperty("规格名称")
    private String name;

    @ApiModelProperty("商品主图")
    private Integer specFormat;

    @ApiModelProperty("规格值")
    private ISpecItemVo item;

}
