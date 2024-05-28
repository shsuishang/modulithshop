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
package com.suisung.shopsuite.pt.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品基础表-SPU表
 * </p>
 *
 * @author Xinze
 * @since 2021-03-20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品基础表-SPU表参数")
public class ProductBaseAddReq implements Serializable {

    private static final Long serialVersionUID = 1L;

    @ApiModelProperty("SPU商家编码:货号")
    @JsonProperty("product_number")
    private String productNumber;

    @ApiModelProperty("产品名称")
    @JsonProperty("product_name")
    @NotBlank(message = "产品名称不能为空")
    private String productName;

    @ApiModelProperty("商品卖点:商品广告词")
    @JsonProperty("product_tips")
    private String productTips;

    @ApiModelProperty("商品主图")
    @JsonProperty("product_image")
    private String productImage;

    @ApiModelProperty("产品视频")
    @JsonProperty("product_video")
    private String productVideo;

    @ApiModelProperty("选择售卖区域:完成售卖区域及运费设置")
    @JsonProperty("transport_type_id")
    private Integer transportTypeId;

    @ApiModelProperty("每人限购")
    @JsonProperty("product_buy_limit")
    private Integer productBuyLimit;

    @ApiModelProperty(value = "平台佣金比率")
    @JsonProperty("product_commission_rate")
    private BigDecimal productCommissionRate;

    //index
    @ApiModelProperty("产品编号:定为SPU编号")
    @JsonProperty("product_id")
    private Long productId;

    @ApiModelProperty("商品分类")
    @JsonProperty("category_id")
    private Integer categoryId;

    @ApiModelProperty("品牌编号")
    @JsonProperty("brand_id")
    private Integer brandId;

    @ApiModelProperty("售后服务(DOT)")
    @JsonProperty("product_service_type_ids")
    private String productServiceTypeIds;

    @ApiModelProperty("商品状态:1001-正常;1002-下架仓库中;1003-待审核; 1000-违规禁售")
    @JsonProperty("product_state_id")
    private Integer productStateId;

    @ApiModelProperty("销售区域(DOT): district_id=1000全部区域")
    @JsonProperty("product_sale_district_ids")
    private String productSaleDistrictIds;

    @ApiModelProperty("商品种类:1201-实物;1202-虚拟")
    @JsonProperty("kind_id")
    @NotBlank(message = "商品种类不能为空")
    private Integer kindId;

    @ApiModelProperty("消费者保障(DOT):由店铺映射到商品")
    @JsonProperty("contract_type_ids")
    private List<Integer> contractTypeIds;

    @ApiModelProperty("所属区域(DOT)")
    @JsonProperty("product_region_district_ids")
    private String productRegionDistrictIds;

    @ApiModelProperty("商品标签(DOT)")
    @JsonProperty("product_tags")
    private String productTags;

    @ApiModelProperty("允许分销(BOOL):1-启用分销;0-禁用分销")
    @JsonProperty("product_sp_enable")
    private Boolean productSpEnable;

    @ApiModelProperty("三级分销允许分销(BOOL):1-启用分销;0-禁用分销")
    @JsonProperty("product_dist_enable")
    private Boolean productDistEnable;

    @ApiModelProperty("上架时间:预设上架时间,可以动态修正状态")
    @JsonProperty("product_sale_time")
    private Long productSaleTime;

    @ApiModelProperty("所属商圈(DOT)")
    @JsonProperty("market_category_id")
    private String marketCategoryId;

    @ApiModelProperty("配送服务(ENUM):1001-快递发货;1002-到店自提;1003-上门服务")
    @JsonProperty("product_transport_id")
    private List<Integer> productTransportId;

    @ApiModelProperty("库存锁定(ENUM):1001-下单锁定;1002-支付锁定;")
    @JsonProperty("product_inventory_lock")
    @NotNull(message = "库存锁定方式不能为空")
    private Integer productInventoryLock;

    // info
    @ApiModelProperty("属性(JSON) - 辅助属性及VAL")
    @JsonProperty("product_assist")
    private String productAssist;

    @ApiModelProperty("规格(JSON)-规格、规格值、goods_id  规格不需要全选就可以添加对应数据")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty("商品描述")
    @JsonProperty("product_detail")
    private String productDetail;

    @ApiModelProperty("Meta Tag 标题")
    @JsonProperty("product_meta_title")
    private String productMetaTitle;

    @ApiModelProperty("Meta Tag 描述")
    @JsonProperty("product_meta_description")
    private String productMetaDescription;

    @ApiModelProperty("Meta Tag 关键字")
    @JsonProperty("product_meta_keyword")
    private String productMetaKeyword;

    //虚拟
    @ApiModelProperty("有效期:1001-长期有效;1002-自定义有效期;1003-购买起有效时长年单位")
    @JsonProperty("product_valid_period")
    private Integer productValidPeriod;

    @ApiModelProperty("开始时间")
    @JsonProperty("product_validity_start")
    private Long productValidityStart;

    @ApiModelProperty("失效时间")
    @JsonProperty("product_validity_end")
    private Long productValidityEnd;

    @ApiModelProperty("以天为单位")
    @JsonProperty("product_validity_duration")
    private Integer productValidityDuration;

    @ApiModelProperty("服务类型(ENUM):1001-到店服务;1002-上门服务")
    @JsonProperty("product_valid_type")
    private Integer productValidType;

    @ApiModelProperty("填写预约日期(BOOL):0-否;1-是")
    @JsonProperty("product_service_date_flag")
    private Boolean productServiceDateFlag;

    @ApiModelProperty("填写联系人(BOOL):0-否;1-是")
    @JsonProperty("product_service_contactor_flag")
    private Boolean productServiceContactorFlag;

    @ApiModelProperty("支持过期退款(BOOL):0-否;1-是")
    @JsonProperty("product_valid_refund_flag")
    private Boolean productValidRefundFlag;
}
