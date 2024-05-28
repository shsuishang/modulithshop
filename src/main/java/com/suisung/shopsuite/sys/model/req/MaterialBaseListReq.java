package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.annotation.QueryField;
import com.suisung.shopsuite.core.annotation.QueryType;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 素材表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "素材表分页查询")
public class MaterialBaseListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("素材编号")
    private Long materialId;

    @ApiModelProperty("附件md5")
    private String materialNumber;

    @ApiModelProperty("分类编号")
    private Long galleryId;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;

    @ApiModelProperty("文件URL")
    private String materialUrl;

    @ApiModelProperty("素材来源")
    private String materialSource;

    @ApiModelProperty("素材排序")
    private Integer materialSort;

    @ApiModelProperty("素材域名")
    private String materialDomain;

    @ApiModelProperty("素材path:本地存储")
    private String materialPath;

    @ApiModelProperty("素材类型")
    private String materialType;

    @ApiModelProperty("素材高度")
    private Integer materialImageH;

    @ApiModelProperty("素材宽度")
    private Integer materialImageW;

    @ApiModelProperty("素材大小")
    private Integer materialSize;

    @ApiModelProperty("素材类型")
    private String materialMimeType;

    @ApiModelProperty("metadata")
    private String materialMetadata;

    @ApiModelProperty("素材alt")
    private String materialAlt;

    @ApiModelProperty("素材标题")
    @QueryField(type = QueryType.LIKE)
    private String materialName;

    @ApiModelProperty("素材描述")
    private String materialDesc;

    @ApiModelProperty("浏览密码")
    private String materialPassword;

    @ApiModelProperty("素材日期")
    private Date materialTime;

    @ApiModelProperty("素材时长:（音频/视频）")
    private String materialDuration;

    public MaterialBaseListReq() {
        setSidx("material_id");
        setSort("DESC");
    }
}
