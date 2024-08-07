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

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.annotation.QueryField;
import com.suisung.shopsuite.core.annotation.QueryType;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 产品索引表-不读取数据只读主键
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "产品索引表-不读取数据只读主键分页查询")
public class ProductIndexListReq extends BaseListReq {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品编号:定为SPU编号")
    private Long productId;

    @ApiModelProperty("SPU商家编码:货号")
    private String productNumber;

    @ApiModelProperty("产品名称:店铺平台先在对用表中检索后通过id检索,检索使用")
    private String productName;

    @ApiModelProperty("搜索关键词")
    private String keywords;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("店铺状态(BOOL):0-关闭;1-运营中")
    private Boolean storeIsOpen;

    @ApiModelProperty("店铺类型(ENUM): 1-卖家店铺; 2-供应商店铺")
    private Boolean storeType;

    @ApiModelProperty("店铺分类编号(DOT)")
    private String storeCategoryIds;

    @ApiModelProperty("商品分类")
    private Integer categoryId;

    @ApiModelProperty("课程分类")
    private Integer courseCategoryId;

    @ApiModelProperty("类型编号:冗余检索")
    private Integer typeId;

    @ApiModelProperty("商品库存:冗余计算")
    private Integer productQuantity;

    @ApiModelProperty("预警数量")
    private Integer productWarnQuantity;

    @ApiModelProperty("品牌编号")
    private String brandId;

    @ApiModelProperty("售后服务(DOT)")
    private String productServiceTypeIds;

    @ApiModelProperty("商品状态:1001-正常;1002-下架仓库中;1003-待审核; 1000-违规禁售")
    private Integer productStateId;

    @ApiModelProperty("销售区域(DOT): district_id=1000全部区域")
    private String productSaleDistrictIds;

    @ApiModelProperty("商品审核(ENUM):3001-审核通过;3002-审核中;3000-审核未通过")
    private Integer productVerifyId;

    @ApiModelProperty("是否开票(BOOL): 1-是; 0-否")
    private Boolean productIsInvoices;

    @ApiModelProperty("是否允许退换货(BOOL): 1-是; 0-否")
    private Boolean productIsReturn;

    @ApiModelProperty("商品推荐(BOOL):1-是; 0-否")
    private Boolean productIsRecommend;

    @ApiModelProperty("缺货状态(ENUM):1-有现货;2-预售商品;3-缺货;4-2至3天")
    private Boolean productStockStatus;

    @ApiModelProperty("商品种类:1201-实物;1202-虚拟")
    private Integer kindId;

    @ApiModelProperty("参与活动(DOT)")
    private String activityTypeIds;

    @ApiModelProperty("消费者保障(DOT):由店铺映射到商品")
    private String contractTypeIds;

    @ApiModelProperty("辅助属性值列(DOT):assist_item_id每个都不用 , setFilter(tagid, array(2,3,4));是表示含有标签值2,3,4中的任意一个即符合筛选，这里是or关系。 setFilter(‘tagid’, array(2)); setFilter(‘tagid’, array(3)); 形成and关系| msyql where FIND_IN_SET('1', product_assist_data) ")
    @TableField(exist = false)
    private String assist;

    //@ApiModelProperty("辅助属性值列(DOT):assist_item_id每个都不用 , setFilter(tagid, array(2,3,4));是表示含有标签值2,3,4中的任意一个即符合筛选，这里是or关系。 setFilter(‘tagid’, array(2)); setFilter(‘tagid’, array(3)); 形成and关系| msyql where FIND_IN_SET('1', product_assist_data) ")
    //private String productAssistData;

    @ApiModelProperty("商品单价")
    @QueryField(type = QueryType.GE)
    private BigDecimal productUnitPriceMin;

    @ApiModelProperty("商品最高单价")
    @QueryField(type = QueryType.LE)
    private BigDecimal productUnitPriceMax;

    @ApiModelProperty("商品积分")
    @QueryField(type = QueryType.GE)
    private BigDecimal productUnitPointsMin;

    @ApiModelProperty("商品积分")
    @QueryField(type = QueryType.LE)
    private BigDecimal productUnitPointsMax;

    @ApiModelProperty("销售量")
    private Integer productSaleNum;

    @ApiModelProperty("收藏数量人气")
    private Integer productFavoriteNum;

    @ApiModelProperty("商品点击数量")
    private Integer productClick;

    @ApiModelProperty("评价次数")
    private Integer productEvaluationNum;

    @ApiModelProperty("所属区域(DOT)")
    private String productRegionDistrictIds;

    @ApiModelProperty("运费:包邮为0，检索使用")
    private BigDecimal productFreight;

    @ApiModelProperty("商品标签(DOT)")
    private String productTags;

    @ApiModelProperty("是否自营(BOOL):1-自营;0-非自营")
    private Boolean storeIsSelfsupport;

    @ApiModelProperty("允许分销(BOOL):1-启用分销;0-禁用分销")
    private Boolean productSpEnable;

    @ApiModelProperty("三级分销允许分销(BOOL):1-启用分销;0-禁用分销")
    private Boolean productDistEnable;

    @ApiModelProperty("添加时间")
    private Long productAddTime;

    @ApiModelProperty("上架时间:预设上架时间,可以动态修正状态")
    private Long productSaleTime;

    @ApiModelProperty("排序:越小越靠前")
    private Integer productOrder;

    @ApiModelProperty("产品来源编号")
    private Long productSrcId;

    @ApiModelProperty("所属商圈(DOT)")
    private String marketCategoryId;

    @ApiModelProperty("纬度")
    private Double storeLatitude;

    @ApiModelProperty("经度")
    private Double storeLongitude;

    @ApiModelProperty("是否视频(BOOL):1-有视频;0-无视频")
    private Boolean productIsVideo;

    @ApiModelProperty("配送服务(ENUM):1001-快递发货;1002-到店自提;1003-上门服务")
    private String productTransportId;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;

    @ApiModelProperty("商品编号(DOT)")
    private String itemIds;
}
