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
package com.suisung.shopsuite.pt.model.entity;

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

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pt_product_brand")
@ApiModel(value = "ProductBrand对象", description = "品牌表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductBrand implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌编号")
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Integer brandId;

    @ApiModelProperty("品牌名称")
    @TableField("brand_name")
    private String brandName;

    @ApiModelProperty("品牌拼音")
    @TableField("brand_code")
    private String brandCode;

    @ApiModelProperty("首字母")
    @TableField("brand_initial")
    private String brandInitial;

    @ApiModelProperty("品牌描述")
    @TableField("brand_desc")
    private String brandDesc;

    @ApiModelProperty("所属分类:一级分类即可")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty("展示方式(ENUM):1-图片; 2-文字  | 在“全部品牌”页面的展示方式，如果设置为“图片”则显示该品牌的“品牌图片标识”，如果设置为“文字”则显示该品牌的“品牌名”")
    @TableField("brand_show_type")
    private Integer brandShowType;

    @ApiModelProperty("品牌LOGO")
    @TableField("brand_image")
    private String brandImage;

    @ApiModelProperty("是否推荐(BOOL):1-是; 0-否")
    @TableField("brand_recommend")
    private Boolean brandRecommend;

    @ApiModelProperty("是否启用(BOOL):1-启用; 0-禁用")
    @TableField("brand_enable")
    private Boolean brandEnable;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("品牌申请(ENUM):0-申请中; 1-通过 | 申请功能是会员使用，系统后台默认为1")
    @TableField("brand_apply")
    private Integer brandApply;

    @ApiModelProperty("背景图")
    @TableField("brand_bg")
    private String brandBg;
}
