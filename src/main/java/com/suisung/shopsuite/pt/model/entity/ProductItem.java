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
import com.suisung.shopsuite.marketing.model.vo.ActivityInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品SKU表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pt_product_item")
@ApiModel(value = "ProductItem对象", description = "商品SKU表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品编号-SKU编号")
    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;

    @ApiModelProperty("副标题(DOT):SKU名称")
    @TableField("item_name")
    private String itemName;

    @ApiModelProperty("产品编号")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("颜色SKU，规格值")
    @TableField("color_id")
    private Long colorId;

    @ApiModelProperty("是否为默认展示的商品，必须为item_enable")
    @TableField("item_is_default")
    private Boolean itemIsDefault;

    @ApiModelProperty("SKU商家编码:SKU商家编码为非必填项，若不填写，系统会自动生成一个SKU商家编码。")
    @TableField("item_number")
    private String itemNumber;

    @ApiModelProperty("条形码")
    @TableField("item_barcode")
    private String itemBarcode;

    @ApiModelProperty("成本价")
    @TableField("item_cost_price")
    private BigDecimal itemCostPrice;

    @ApiModelProperty("商品价格")
    @TableField("item_unit_price")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("市场价")
    @TableField("item_market_price")
    private BigDecimal itemMarketPrice;

    @ApiModelProperty("积分价格")
    @TableField("item_unit_points")
    private BigDecimal itemUnitPoints = BigDecimal.ZERO;

    @ApiModelProperty("商品库存")
    @TableField("item_quantity")
    private Integer itemQuantity;

    @ApiModelProperty("商品冻结库存")
    @TableField("item_quantity_frozen")
    private Integer itemQuantityFrozen;

    @ApiModelProperty("库存预警值")
    @TableField("item_warn_quantity")
    private Integer itemWarnQuantity;

    @ApiModelProperty("商品规格序列化(JSON):{spec_id:spec_item_id, spec_id:spec_item_id, spec_id:spec_item_id}")
    @TableField("item_spec")
    private String itemSpec;

    @ApiModelProperty("商品规格值编号")
    @TableField("spec_item_ids")
    private String specItemIds;

    @ApiModelProperty("是否启用(LIST):1001-正常;1002-下架仓库中;1000-违规禁售")
    @TableField("item_enable")
    private Integer itemEnable;

    @ApiModelProperty("被改动(BOOL):0-未改动;1-已改动分销使用")
    @TableField("item_is_change")
    private Boolean itemIsChange;

    @ApiModelProperty("商品重量:KG")
    @TableField("item_weight")
    private BigDecimal itemWeight;

    @ApiModelProperty("商品体积:立方米")
    @TableField("item_volume")
    private BigDecimal itemVolume;

    @ApiModelProperty("微小店分销佣金")
    @TableField("item_fx_commission")
    private BigDecimal itemFxCommission;

    @ApiModelProperty("返利额度")
    @TableField("item_rebate")
    private BigDecimal itemRebate;

    @ApiModelProperty("供应商SKU编号")
    @TableField("item_src_id")
    private Long itemSrcId;

    @ApiModelProperty("商品分类")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty("课程分类")
    @TableField("course_category_id")
    private Integer courseCategoryId;

    @ApiModelProperty("所属店铺")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "可用库存")
    @TableField(exist = false)
    private Integer availableQuantity;

    @ApiModelProperty(value = "Spec名称")
    @TableField(exist = false)
    private String productItemName;

    @ApiModelProperty("当前使用活动编号")
    @TableField(exist = false)
    private Integer activityId;

    @ApiModelProperty(value = "商品描述")
    @TableField(exist = false)
    private String productDetail;


    @ApiModelProperty("活动信息")
    @TableField(exist = false)
    private ActivityInfoVo activityInfo;

    @ApiModelProperty("商品销售价")
    @TableField(exist = false)
    private BigDecimal itemSalePrice;

    @ApiModelProperty("节省单价")
    @TableField(exist = false)
    private BigDecimal itemSavePrice;

    @ApiModelProperty("标签名称(DOT)")
    @TableField(exist = false)
    private List<String> productTagNames = new ArrayList<>();

    @ApiModelProperty("商品卖点:商品广告词")
    @TableField(exist = false)
    private String productTips;


    public Integer getAvailableQuantity() {
        return itemQuantity - itemQuantityFrozen;
    }
}
