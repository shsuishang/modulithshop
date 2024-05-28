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
package com.suisung.shopsuite.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.res.ProductCategoryRes;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * PC分类导航表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_page_category_nav")
@ApiModel(value = "PageCategoryNav对象", description = "PC分类导航表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageCategoryNav implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "category_nav_id", type = IdType.AUTO)
    private Integer categoryNavId;

    @ApiModelProperty("分类名称")
    @TableField("category_nav_name")
    private String categoryNavName;

    @ApiModelProperty("分类图片")
    @TableField("category_nav_image")
    private String categoryNavImage;

    @ApiModelProperty("推荐分类(JSON)")
    @TableField("category_ids")
    private String categoryIds;

    @ApiModelProperty("推荐商品(DOT)")
    @TableField("item_ids")
    private String itemIds;

    @ApiModelProperty("推荐品牌(DOT)")
    @TableField("brand_ids")
    private String brandIds;

    @ApiModelProperty("广告数据(JSON)")
    @TableField("category_nav_adv")
    private String categoryNavAdv;

    @ApiModelProperty("模板分类(ENUM):1-分类模板1;2-商品模板")
    @TableField("category_nav_type")
    private Integer categoryNavType;

    @ApiModelProperty("排序")
    @TableField("category_nav_order")
    private Integer categoryNavOrder;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    @TableField("category_nav_enable")
    private Boolean categoryNavEnable;


    @ApiModelProperty("分类tree")
    @TableField(exist = false)
    private ProductCategoryRes productCategoryTree;

    @ApiModelProperty("商品数据")
    @TableField(exist = false)
    private List<ProductItemVo> productItems;
}
