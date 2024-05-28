package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 配置分组表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "配置分组表参数")
public class ConfigTypeEditReq extends ConfigTypeAddReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分组编号")
    @NotBlank(message = "请输入分组编号")
    private Integer configTypeId;
}
