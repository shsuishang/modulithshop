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
package com.suisung.shopsuite.trade.model.entity;

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
 * 收货地址表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_delivery_address")
@ApiModel(value = "OrderDeliveryAddress对象", description = "收货地址表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDeliveryAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "order_id", type = IdType.AUTO)
    private String orderId;

    @ApiModelProperty("联系人")
    @TableField("da_name")
    private String daName;

    @ApiModelProperty("国家编码")
    @TableField("da_intl")
    private String daIntl;

    @ApiModelProperty("手机号码")
    @TableField("da_mobile")
    private String daMobile;

    @ApiModelProperty("联系电话")
    @TableField("da_telephone")
    private String daTelephone;

    @ApiModelProperty("省编号")
    @TableField("da_province_id")
    private Integer daProvinceId;

    @ApiModelProperty("省份")
    @TableField("da_province")
    private String daProvince;

    @ApiModelProperty("市编号")
    @TableField("da_city_id")
    private Integer daCityId;

    @ApiModelProperty("市")
    @TableField("da_city")
    private String daCity;

    @ApiModelProperty("县")
    @TableField("da_county_id")
    private Integer daCountyId;

    @ApiModelProperty("县区")
    @TableField("da_county")
    private String daCounty;

    @ApiModelProperty("详细地址")
    @TableField("da_address")
    private String daAddress;

    @ApiModelProperty("邮政编码")
    @TableField("da_postalcode")
    private String daPostalcode;

    @ApiModelProperty("地址标签：家里、公司等等")
    @TableField("da_tag_name")
    private String daTagName;

    @ApiModelProperty("添加时间")
    @TableField("da_time")
    private Date daTime;

    @ApiModelProperty("经度")
    @TableField("da_longitude")
    private Double daLongitude;

    @ApiModelProperty("纬读")
    @TableField("da_latitude")
    private Double daLatitude;
}
