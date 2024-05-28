package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户等级表-平台
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户等级表-平台参数")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLevelEditReq extends UserLevelAddReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("等级编号")
    private Integer userLevelId;


}
