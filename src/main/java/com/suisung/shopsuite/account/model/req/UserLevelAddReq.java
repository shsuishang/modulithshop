package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用户等级表-平台
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户等级表-平台参数")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLevelAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("等级名称")
    private String userLevelName;

    @ApiModelProperty(value = "升级经验值")
    private Integer userLevelExp;

    @ApiModelProperty("累计消费")
    private Integer userLevelSpend;

    @ApiModelProperty("LOGO")
    private String userLevelLogo;

    @ApiModelProperty("折扣率百分比")
    private BigDecimal userLevelRate;

    @ApiModelProperty("修改时间")
    private Long userLevelTime;

    @ApiModelProperty("系统内置(BOOL):0-否;1-是")
    private Boolean userLevelIsBuildin;


}
