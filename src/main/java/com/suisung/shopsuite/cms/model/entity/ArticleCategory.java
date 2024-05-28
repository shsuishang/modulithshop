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
package com.suisung.shopsuite.cms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章分类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_article_category")
@ApiModel(value = "ArticleCategory对象", description = "文章分类")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ArticleCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类编号")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    @ApiModelProperty("分类名称")
    @TableField("category_name")
    private String categoryName;

    @TableField("category_parent_id")
    private Integer categoryParentId;

    @ApiModelProperty("分类图标")
    @TableField("category_image_url")
    private String categoryImageUrl;

    @ApiModelProperty("分类关键词")
    @TableField("category_keywords")
    private String categoryKeywords;

    @ApiModelProperty("分类描述")
    @TableField("category_desc")
    private String categoryDesc;

    @ApiModelProperty("分类内容数量")
    @TableField("category_count")
    private Integer categoryCount;

    @ApiModelProperty("分类模板")
    @TableField("category_template")
    private String categoryTemplate;

    @TableField("category_alias")
    private String categoryAlias;

    @ApiModelProperty("分类排序")
    @TableField("category_order")
    private Integer categoryOrder;

    @ApiModelProperty("系统内置(ENUM):0-非内置;1-内置;")
    @TableField("category_buildin")
    private Boolean categoryBuildin;

    @ApiModelProperty("是否叶节点")
    @TableField("category_is_leaf")
    private Boolean categoryIsLeaf;
}
