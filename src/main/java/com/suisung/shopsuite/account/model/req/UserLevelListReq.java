package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户等级表-平台分页查询")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLevelListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("等级编号")
    private Integer userLevelId;

    @ApiModelProperty("等级名称")
    private String userLevelName;

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
