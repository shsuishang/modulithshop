package com.suisung.shopsuite.sys.model.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserCenterOutput {
    @ApiModelProperty("菜单信息")
    private Map menus;

    @ApiModelProperty("订单信息")
    private Map orders;
}
