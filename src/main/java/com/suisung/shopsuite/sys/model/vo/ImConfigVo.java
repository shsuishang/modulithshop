package com.suisung.shopsuite.sys.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ImConfigVo {
    @ApiModelProperty("IM平台编号")
    private Integer puid;

    @ApiModelProperty("用户编号")
    private Integer imUserId;

    @ApiModelProperty("是否启用IM")
    private Boolean imEnable;
}
