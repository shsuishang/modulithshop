package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 系统参数设置表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "系统参数设置表参数")
public class ConfigBaseStateEditReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("配置编码")
    @NotNull(message = "配置编码不能为空")
    private String configKey;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    @NotNull(message = "是否启用不能为空")
    private Boolean configEnable;
}
