package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统参数设置表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "系统参数设置表分页查询")
public class ConfigBaseListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("配置编码")
    private String configKey;

    @ApiModelProperty("配置标题")
    private String configTitle;

    @ApiModelProperty("所属分类")
    private Integer configTypeId;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    private Boolean configEnable;

    public ConfigBaseListReq() {
        setSidx("config_sort");
    }
}
