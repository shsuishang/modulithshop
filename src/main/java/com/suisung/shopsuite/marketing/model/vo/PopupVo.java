package com.suisung.shopsuite.marketing.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "弹窗")
public class PopupVo {

    @ApiModelProperty("弹窗图片")
    private String popUpImage;

    @ApiModelProperty("弹窗网址")
    private String popUpUrl;

    @ApiModelProperty("弹窗活动类型(ENUM):0-新人礼包;1-其他活动")
    private Integer popUpType;
}
