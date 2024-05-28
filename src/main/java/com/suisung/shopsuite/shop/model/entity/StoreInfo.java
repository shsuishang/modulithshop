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
 * 店铺表-用户可以多店铺-需要分表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("shop_store_info")
@ApiModel(value = "StoreInfo对象", description = "店铺表-用户可以多店铺-需要分表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺编号")
    @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    @ApiModelProperty("店铺口号")
    @TableField("store_slogan")
    private String storeSlogan;

    @ApiModelProperty("店铺banner")
    @TableField("store_banner")
    private String storeBanner;

    @ApiModelProperty("卖家电话")
    @TableField("store_tel")
    private String storeTel;

    @ApiModelProperty("免运费额度")
    @TableField("store_free_shipping")
    private Integer storeFreeShipping;

    @ApiModelProperty("店铺绑定模板")
    @TableField("store_template")
    private String storeTemplate;

    @ApiModelProperty("店铺资料信息状态(ENUM):3210-待完善资料;   3220-等待审核 ;  3230-资料审核没有通过;     3240-资料审核通过,待付款")
    @TableField("store_state_id")
    private Integer storeStateId;

    @ApiModelProperty("付款状态(ENUM):0-未付款;   1-已付款待审核;   2-审核通过  ")
    @TableField("store_payment_state")
    private Integer storePaymentState;

    @ApiModelProperty("本次开始时间")
    @TableField("store_start_time")
    private Date storeStartTime;

    @ApiModelProperty("有效期截止时间")
    @TableField("store_end_time")
    private Date storeEndTime;

    @ApiModelProperty("关闭原因")
    @TableField("store_close_reason")
    private String storeCloseReason;

    @ApiModelProperty("购买须知")
    @TableField("store_notice")
    private String storeNotice;

    @ApiModelProperty("营业时间")
    @TableField("store_opening_hours")
    private String storeOpeningHours;

    @ApiModelProperty("打烊时间")
    @TableField("store_close_hours")
    private String storeCloseHours;

    @ApiModelProperty("所在区域")
    @TableField("store_area")
    private String storeArea;

    @ApiModelProperty("所属地区(DOT)")
    @TableField("store_district_id")
    private String storeDistrictId;

    @ApiModelProperty("店铺详细地址")
    @TableField("store_address")
    private String storeAddress;
}
