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
public class PagePopUpVo {

    @ApiModelProperty("是否展示弹窗图片")
    private Boolean popUpEnable;

    @ApiModelProperty("弹窗图片")
    private String popUpImage;

    @ApiModelProperty("弹窗网址")
    private String popUpUrl;
}
