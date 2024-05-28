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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 店铺参数设置表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("shop_store_config")
@ApiModel(value = "StoreConfig对象", description = "店铺参数设置表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺编号")
    @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    @ApiModelProperty("销售员购买权限(BOOL):0-关闭;1-开启,购买权限开启状态下，销售员自己购买的订单将会算入业绩")
    @TableField("sc_allow_seller_buy")
    private Boolean scAllowSellerBuy;

    @ApiModelProperty("分销申请消费额")
    @TableField("sc_expenditure")
    private Integer scExpenditure;

    @ApiModelProperty("一级佣金比例")
    @TableField("sc_cps_rate")
    private Float scCpsRate;

    @ApiModelProperty("二级销售(BOOL): 0-关闭; 1-开启")
    @TableField("sc_second_is_enable")
    private Boolean scSecondIsEnable;

    @ApiModelProperty("二级佣金比例")
    @TableField("sc_second_cps_rate")
    private Float scSecondCpsRate;

    @ApiModelProperty("三级分佣比例")
    @TableField("sc_third_cps_rate")
    private Float scThirdCpsRate;

    @ApiModelProperty("客户关系 期限， 销售员带来的客户（成为店铺的消费者开始计算时间）超过一定期限后，则不再享受分佣。 消费者在店铺消费第一单时间后，在某个期限内消费才可以产生佣金。 ")
    @TableField("sc_directseller_customer_exptime")
    private Integer scDirectsellerCustomerExptime;

    @ApiModelProperty("客户有效期(ENUM):1-永久，建立客户关系,客户以后在店铺的购买都分佣; 2-短期，只根据链接购买获取佣金， 且一定期限后，链接失效。 不需要建立客户关系")
    @TableField("sc_directseller_exptime_type")
    private Boolean scDirectsellerExptimeType;

    @ApiModelProperty("客户关系保护期 - 带来的客户关系在一定期限内不给抢走， 其它销售可以通过购买链接生效，但是在保护期内部更改关系")
    @TableField("sc_directseller_rel_exptime")
    private Integer scDirectsellerRelExptime;

    @ApiModelProperty("销售员审核(BOOL):0-不需要审核; 1-需要审核")
    @TableField("sc_is_verify")
    private Boolean scIsVerify;

    @ApiModelProperty("结算方式(BOOL):0-手动结算 ;1-自动结算")
    @TableField("sc_auto_settle")
    private Boolean scAutoSettle;

    @ApiModelProperty("结算时间")
    @TableField("sc_settle_time_type")
    private Integer scSettleTimeType;

    @ApiModelProperty("启用商品起订量")
    @TableField("sc_is_use_min_quantity")
    private Boolean scIsUseMinQuantity;

    @ApiModelProperty("启用商品限购量")
    @TableField("sc_is_use_max_quantity")
    private Boolean scIsUseMaxQuantity;

    @ApiModelProperty("启用商品品牌")
    @TableField("sc_is_use_product_brand")
    private Boolean scIsUseProductBrand;

    @ApiModelProperty("启用商品重量字段")
    @TableField("sc_is_use_product_weight")
    private Boolean scIsUseProductWeight;

    @ApiModelProperty("启用商品自定义字段")
    @TableField("sc_is_use_product_defined")
    private Boolean scIsUseProductDefined;

    @ApiModelProperty("商品标签管理(JSON)")
    @TableField("sc_product_tags")
    private String scProductTags;

    @ApiModelProperty("交货日期必填")
    @TableField("sc_is_necessary_delivery_date")
    private Date scIsNecessaryDeliveryDate;

    @ApiModelProperty("启用最低下单金额限制(BOOL):0-关闭;1-启用")
    @TableField("sc_is_use_order_min_money")
    private Boolean scIsUseOrderMinMoney;

    @ApiModelProperty("启用最低下单金额限制")
    @TableField("sc_order_min_money")
    private BigDecimal scOrderMinMoney;

    @ApiModelProperty("订货仓库限制(ENUM):1-按总仓库订购商品,不限制仓库定向供货指定的客户;2-按客户对应仓库订购商品,不限制仓库定向供货指定的客户")
    @TableField("sc_deduct_inventory_mode")
    private Boolean scDeductInventoryMode;

    @ApiModelProperty("客户订货系统(ENUM):0-不显示库存,客户订货无库存功能;1-显示存库有无客户而在商品下单时显示商品库存有无，但不显示具体数量，未填库存信息显示无库存信息;2-显示存库数量客户订货系统可在商品、下单时显示商品库存数量，未填写库存信息显示无库存信息")
    @TableField("sc_inventory_agent_show_type")
    private Boolean scInventoryAgentShowType;

    @ApiModelProperty("商品库存扣减预购值锁定(ENUM):1-下单扣减锁定;2-支付扣减锁定,数量小于等于零，禁止订货")
    @TableField("sc_is_enabled_inventory_check")
    private Boolean scIsEnabledInventoryCheck;

    @ApiModelProperty("客户资金账户(JSON):[{\"baseId\":1,\"name\":\"预付款账户\",\"code\":\"0001\",\"can_pay\":\"1\",\"canRecharge\":\"1\",\"enableStatus\":\"1\",\"autoAuditFlag\":\"1\"},{\"baseId\":2,\"name\":\"返点账户\",\"code\":\"0002\",\"can_pay\":\"1\",\"canRecharge\":\"0\",\"enableStatus\":\"1\",\"autoAuditFlag\":\"1\"},{\"baseId\":3,\"name\":\"保证金账户\",\"code\":\"0003\",\"can_pay\":\"0\",\"canRecharge\":\"1\",\"enableStatus\":\"1\",\"autoAuditFlag\":\"0\"}]")
    @TableField("sc_fund_account_settings")
    private String scFundAccountSettings;

    @ApiModelProperty("订货流程设置(DOT)")
    @TableField("sc_order_process")
    private String scOrderProcess;

    @ApiModelProperty("退货流程设置(DOT)")
    @TableField("sc_order_return_process")
    private String scOrderReturnProcess;

    @ApiModelProperty("启用客户商品分享(BOOL):1-启用;0-禁用")
    @TableField("sc_is_enabled_share")
    private Boolean scIsEnabledShare;

    @ApiModelProperty("启用进销存管理(BOOL):1-启用;0-禁用")
    @TableField("sc_is_enabled_invoicing")
    private Boolean scIsEnabledInvoicing;

    @ApiModelProperty("结算周期:30为整月")
    @TableField("sc_settle_circle")
    private Integer scSettleCircle;

    @ApiModelProperty("上次结算时间")
    @TableField("sc_settle_last_time")
    private Long scSettleLastTime;

    @ApiModelProperty("下次结算时间")
    @TableField("sc_settle_next_time")
    private Long scSettleNextTime;

    @ApiModelProperty("消费送积分比率")
    @TableField("sc_points_consume_rate")
    private BigDecimal scPointsConsumeRate;

    @ApiModelProperty("跑腿-起送距离")
    @TableField("sc_start_distance")
    private BigDecimal scStartDistance;

    @ApiModelProperty("跑腿-起送金额")
    @TableField("sc_start_amount")
    private BigDecimal scStartAmount;

    @ApiModelProperty("跑腿-超出起送距离外每公里金额")
    @TableField("sc_extra_amount")
    private BigDecimal scExtraAmount;

    @ApiModelProperty("节日优惠-活动名称")
    @TableField("sc_festival_name")
    private String scFestivalName;

    @ApiModelProperty("节日优惠-开始时间")
    @TableField("sc_festival_start")
    private String scFestivalStart;

    @ApiModelProperty("节日优惠-结束时间")
    @TableField("sc_festival_end")
    private String scFestivalEnd;

    @ApiModelProperty("节日优惠-金额上限")
    @TableField("sc_festival_amount_upper")
    private BigDecimal scFestivalAmountUpper;

    @ApiModelProperty("节日优惠-节日下限")
    @TableField("sc_festival_amount_down")
    private BigDecimal scFestivalAmountDown;

    @ApiModelProperty("节日优惠-每日浮动比例")
    @TableField("sc_festival_float_proportion")
    private BigDecimal scFestivalFloatProportion;

    @ApiModelProperty("节日优惠-浮动方式")
    @TableField("sc_festival_float_type")
    private Boolean scFestivalFloatType;

    @ApiModelProperty("店铺免运费(BOOL):0-关闭 ;1-开启")
    @TableField("sc_is_free_freight")
    private Boolean scIsFreeFreight;

    @ApiModelProperty("采集默认分类")
    @TableField("sc_crawl_category_id")
    private Integer scCrawlCategoryId;

    @ApiModelProperty("是否开票(BOOL):1-启用;0-禁用")
    @TableField("sc_is_enabled_invoice")
    private Boolean scIsEnabledInvoice;
}
