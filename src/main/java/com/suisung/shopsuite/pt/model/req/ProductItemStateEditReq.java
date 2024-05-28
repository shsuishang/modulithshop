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

    @ApiModelProperty("活动状态(ENUM):0-未开启;1-正常;2-已结束;3-管理员关闭")
    @NotNull(message = "活动状态不能为空")
    private Integer activityItemState;

}
