package com.suisung.shopsuite.pt.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FreightVo {
    @ApiModelProperty("默认运费")
    private Integer freight;

    @ApiModelProperty("是否可销售")
    private Integer ifStore;
}
