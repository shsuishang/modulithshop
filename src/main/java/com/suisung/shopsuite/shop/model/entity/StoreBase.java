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
package com.suisung.shopsuite.shop.model.entity;

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
 * 店铺基础信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("shop_store_base")
@ApiModel(value = "StoreBase对象", description = "店铺基础信息表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺编号")
    @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty("店铺等级")
    @TableField("store_grade_id")
    private Integer storeGradeId;

    @ApiModelProperty("店铺logo")
    @TableField("store_logo")
    private String storeLogo;

    @ApiModelProperty("纬度")
    @TableField("store_latitude")
    private Double storeLatitude;

    @ApiModelProperty("经度")
    @TableField("store_longitude")
    private Double storeLongitude;

    @ApiModelProperty("配送区域(DOT)")
    @TableField("store_deliver_district_id")
    private String storeDeliverDistrictId;

    @ApiModelProperty("是否自营(ENUM): 1-自营;0-非自营")
    @TableField("store_is_selfsupport")
    private Boolean storeIsSelfsupport;

    @ApiModelProperty("店铺类型(ENUM): 1-卖家店铺; 2-供应商店铺")
    @TableField("store_type")
    private Integer storeType;

    @ApiModelProperty("店铺状态(BOOL):0-关闭;  1-运营中")
    @TableField("store_is_open")
    private Boolean storeIsOpen;

    @ApiModelProperty("店铺分类编号")
    @TableField("store_category_id")
    private Integer storeCategoryId;

    @ApiModelProperty("免费服务(DOT)")
    @TableField("store_o2o_tags")
    private String storeO2oTags;

    @ApiModelProperty("是否O2O(BOOL):0-否;1-是")
    @TableField("store_o2o_flag")
    private Boolean storeO2oFlag;

    @ApiModelProperty("所属商圈(DOT)")
    @TableField("store_circle")
    private String storeCircle;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;
}
