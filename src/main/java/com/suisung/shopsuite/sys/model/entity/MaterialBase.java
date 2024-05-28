// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 素材表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_material_base")
@ApiModel(value = "MaterialBase对象", description = "素材表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MaterialBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("素材编号")
    @TableId(value = "material_id", type = IdType.AUTO)
    private Long materialId;

    @ApiModelProperty("附件md5")
    @TableField("material_number")
    private String materialNumber;

    @ApiModelProperty("分类编号")
    @TableField("gallery_id")
    private Long galleryId;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("文件URL")
    @TableField("material_url")
    private String materialUrl;

    @ApiModelProperty("素材来源")
    @TableField("material_source")
    private String materialSource;

    @ApiModelProperty("素材排序")
    @TableField("material_sort")
    private Integer materialSort;

    @ApiModelProperty("素材path:本地存储")
    @TableField("material_path")
    private String materialPath;

    @ApiModelProperty("素材类型")
    @TableField("material_type")
    private String materialType;

    @ApiModelProperty("素材高度")
    @TableField("material_image_h")
    private Integer materialImageH;

    @ApiModelProperty("素材宽度")
    @TableField("material_image_w")
    private Integer materialImageW;

    @ApiModelProperty("素材大小")
    @TableField("material_size")
    private Long materialSize;

    @ApiModelProperty("素材类型")
    @TableField("material_mime_type")
    private String materialMimeType;

    @ApiModelProperty("素材alt")
    @TableField("material_alt")
    private String materialAlt;

    @ApiModelProperty("素材标题")
    @TableField("material_name")
    private String materialName;

    @ApiModelProperty("素材描述")
    @TableField("material_desc")
    private String materialDesc;

    @ApiModelProperty("素材时长:（音频/视频）")
    @TableField("material_duration")
    private String materialDuration;

    @ApiModelProperty("素材日期")
    @TableField("material_time")
    private Date materialTime;
}
