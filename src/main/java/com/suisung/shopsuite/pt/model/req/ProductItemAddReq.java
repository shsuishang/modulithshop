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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品SKU表
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品SKU表参数")
public class ProductItemAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("副标题(DOT):SKU名称")
    private String itemName;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("颜色SKU，规格值")
    private Long colorId;

    @ApiModelProperty("是否为默认展示的商品-列表页展示，必须为item_enable")
    private Boolean itemIsDefault;

    @ApiModelProperty("SKU商家编码:SKU商家编码为非必填项，若不填写，系统会自动生成一个SKU商家编码。")
    private String itemNumber;

    @ApiModelProperty("条形码")
    private String itemBarcode;

    @ApiModelProperty("成本价")
    private BigDecimal itemCostPrice;

    @ApiModelProperty("商品价格")
    private BigDecimal itemUnitPrice;

    @ApiModelProperty("市场价")
    private BigDecimal itemMarketPrice;

    @ApiModelProperty("积分价格")
    private BigDecimal itemUnitPoints;

    @ApiModelProperty("商品库存")
    private Integer itemQuantity;

    @ApiModelProperty("商品冻结库存")
    private Integer itemQuantityFrozen;

    @ApiModelProperty("库存预警值")
    private Integer itemWarnQuantity;

    @ApiModelProperty("商品规格序列化(JSON):{spec_id:spec_item_id, spec_id:spec_item_id, spec_id:spec_item_id}")
    private String itemSpec;

    @ApiModelProperty("商品规格值编号")
    private String specItemIds;

    @ApiModelProperty("是否启用(LIST):1001-正常;1002-下架仓库中;1000-违规禁售")
    private Integer itemEnable;

    @ApiModelProperty("被改动(BOOL):0-未改动;1-已改动分销使用")
    private Boolean itemIsChange;

    @ApiModelProperty("商品重量:KG")
    private BigDecimal itemWeight;

    @ApiModelProperty("商品体积:立方米")
    private BigDecimal itemVolume;

    @ApiModelProperty("微小店分销佣金")
    private BigDecimal itemFxCommission;

    @ApiModelProperty("返利额度")
    private BigDecimal itemRebate;

    @ApiModelProperty("供应商SKU编号")
    private Long itemSrcId;

    @ApiModelProperty("商品分类")
    private Integer categoryId;

    @ApiModelProperty("课程分类")
    private Integer courseCategoryId;

    @ApiModelProperty("所属店铺")
    private Integer storeId;


}
