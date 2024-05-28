package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "字典项表参数")
public class DictItemEditReq extends DictItemAddReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字典项编号")
    private String dictItemId;


}
