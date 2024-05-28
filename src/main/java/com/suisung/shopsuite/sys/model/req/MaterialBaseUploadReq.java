package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 素材表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "添加上传参数")
public class MaterialBaseUploadReq {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("素材分类")
    private Long galleryId;

    @ApiModelProperty("素材类型")
    private String materialType;

    @ApiModelProperty("素材关键字")
    private String materialKey;
}
