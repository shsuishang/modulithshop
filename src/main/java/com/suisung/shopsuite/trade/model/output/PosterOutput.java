package com.suisung.shopsuite.trade.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "海报DTO", description = "海报DTO")
public class PosterOutput {
    @ApiModelProperty(value = "二维码")
    private String qrcode;

    @ApiModelProperty(value = "邀请网址")
    private String inviteUrl;

    @ApiModelProperty(value = "下载网址")
    private String downloadUrl;

    @ApiModelProperty(value = "海报网址")
    private String posterUrl;
}
