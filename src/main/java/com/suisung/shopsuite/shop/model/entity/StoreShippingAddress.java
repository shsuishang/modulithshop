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
import java.util.Date;

/**
 * <p>
 * 发货地址表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("shop_store_shipping_address")
@ApiModel(value = "StoreShippingAddress对象", description = "发货地址表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreShippingAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地址编号")
    @TableId(value = "ss_id", type = IdType.AUTO)
    private Integer ssId;

    @ApiModelProperty("联系人")
    @TableField("ss_name")
    private String ssName;

    @ApiModelProperty("国家编码")
    @TableField("ss_intl")
    private String ssIntl;

    @ApiModelProperty("手机号码")
    @TableField("ss_mobile")
    private String ssMobile;

    @ApiModelProperty("联系电话")
    @TableField("ss_telephone")
    private String ssTelephone;

    @ApiModelProperty("联系人(未启用)")
    @TableField("ss_contacter")
    private String ssContacter;

    @ApiModelProperty("邮编")
    @TableField("ss_postalcode")
    private String ssPostalcode;

    @ApiModelProperty("省编号")
    @TableField("ss_province_id")
    private Integer ssProvinceId;

    @ApiModelProperty("省份")
    @TableField("ss_province")
    private String ssProvince;

    @ApiModelProperty("市编号")
    @TableField("ss_city_id")
    private Integer ssCityId;

    @ApiModelProperty("市")
    @TableField("ss_city")
    private String ssCity;

    @ApiModelProperty("县")
    @TableField("ss_county_id")
    private Integer ssCountyId;

    @ApiModelProperty("县区")
    @TableField("ss_county")
    private String ssCounty;

    @ApiModelProperty("详细地址:不必重复填写地区")
    @TableField("ss_address")
    private String ssAddress;

    @ApiModelProperty("添加时间")
    @TableField("ss_time")
    private Date ssTime;

    @ApiModelProperty("默认地址(ENUM):0-否;1-是")
    @TableField("ss_is_default")
    private Boolean ssIsDefault;

    @ApiModelProperty("所属店铺")
    @TableField("store_id")
    private Integer storeId;
}
