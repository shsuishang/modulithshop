package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 素材表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "素材表参数")
public class MaterialBaseAddReq implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("素材标题")
    private String materialName;

    @ApiModelProperty("素材描述")
    private String materialDesc;

    @ApiModelProperty("分类编号")
    private String galleryId;

    @ApiModelProperty("分类编号")
    private String materialType;

    @ApiModelProperty("文件URL")
    private String materialUrl;


}
