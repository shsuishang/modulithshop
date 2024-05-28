package com.suisung.shopsuite.pt.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ISpecItemVo {
    @ApiModelProperty("规格值编号")
    private Integer id;

    @ApiModelProperty("规格值名称")
    private String name;
}
