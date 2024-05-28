package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 配置分组表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "配置分组表分页查询")
public class ConfigTypeListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分组编号")
    private Integer configTypeId;

    @ApiModelProperty("分组名称")
    private String configTypeName;

    @ApiModelProperty("分组排序:从小到大")
    private Integer configTypeSort;

    @ApiModelProperty("是否有效(BOOL):0-禁用;1-启用")
    private Boolean configTypeEnable;

    public ConfigTypeListReq() {
        setSidx("config_type_sort");
    }
}
