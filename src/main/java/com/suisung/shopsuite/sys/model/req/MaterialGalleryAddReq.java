package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 素材分类表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "素材分类表参数")
public class MaterialGalleryAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类名称")
    private String galleryName;

    @ApiModelProperty("分类描述")
    private String galleryDesc;

    @ApiModelProperty("素材数量")
    private Integer galleryNum;

    @ApiModelProperty("是否默认(BOOL):0-否;1-是")
    private Integer galleryIsDefault;

    @ApiModelProperty("分类排序")
    private Integer gallerySort;

    @ApiModelProperty("分类类型")
    private String galleryType;

    @ApiModelProperty("分类封面")
    private String galleryCover;

    @ApiModelProperty("所属店铺")
    private Integer storeId;


}
