package com.suisung.shopsuite.pt.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品SKU表参数")
public class ProductItemStateEditReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品编号-SKU编号")
    @NotNull(message = "商品编号不能为空")
    private Long itemId;

    @ApiModelProperty("是否启用(LIST):1001-正常;1002-下架仓库中;1000-违规禁售")
    @NotNull(message = "启用状态不能为空")
    private Integer itemEnable;

}
