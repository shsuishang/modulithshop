package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 用户详细信息表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户详细信息表参数")
public class UserInfoStateEditReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @NotNull(message = "用户编号不能为空")
    private Integer userId;

    @ApiModelProperty("认证状态(ENUM):0-未认证;1-待审核;2-认证通过;3-认证失败")
    @NotNull(message = "认证状态不能为空")
    private Integer userIsAuthentication;
}
