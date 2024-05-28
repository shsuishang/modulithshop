package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "字典类型表参数")
public class DictBaseAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("显示顺序:从小到大")
    private Integer dictSort;

    @ApiModelProperty("字典备注")
    private String dictNote;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    private Boolean dictEnable;


}
