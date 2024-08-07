package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户详细信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户详细信息表参数")
public class UserInfoEditReq extends UserInfoAddReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private Integer userId;


}
