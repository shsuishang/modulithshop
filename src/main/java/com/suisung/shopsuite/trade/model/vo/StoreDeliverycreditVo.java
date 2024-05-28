package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreDeliverycreditVo implements Serializable {

    @ApiModelProperty("")
    private Float credit;

    @ApiModelProperty("")
    private String percent;

    @ApiModelProperty("")
    private String percent_class;

    @ApiModelProperty("")
    private String percent_text;

    @ApiModelProperty("")
    private String text;

}
