package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "批量加购物车", description = "批量加购物车")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CartBatVo implements Serializable {
    @ApiModelProperty("商品编号")
    @NotEmpty(message = "请输入商品编号")
    private Long itemId;

    @ApiModelProperty("购买商品数量")
    @NotEmpty(message = "请输入商品数量")
    private Integer quantity;
}
