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
import java.util.Date;

/**
 * <p>
 * 文章内容
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_article_base")
@ApiModel(value = "ArticleBase对象", description = "文章内容")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ArticleBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    @ApiModelProperty("标题")
    @TableField("article_title")
    private String articleTitle;

    @ApiModelProperty("文章别名slug")
    @TableField("article_name")
    private String articleName;

    @ApiModelProperty("文章摘要")
    @TableField("article_excerpt")
    private String articleExcerpt;

    @ApiModelProperty("文章内容(HTML)")
    @TableField("article_content")
    private String articleContent;

    @ApiModelProperty("调用网址:默认为本页面构造的网址")
    @TableField("article_url")
    private String articleUrl;

    @ApiModelProperty("所属分类")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty("模板")
    @TableField("article_template")
    private String articleTemplate;

    @ApiModelProperty("SEO标题")
    @TableField("article_seo_title")
    private String articleSeoTitle;

    @ApiModelProperty("SEO关键字")
    @TableField("article_seo_keywords")
    private String articleSeoKeywords;

    @ApiModelProperty("SEO描述")
    @TableField("article_seo_description")
    private String articleSeoDescription;

    @ApiModelProperty("是否启用问答留言(BOOL):0-否;1-是")
    @TableField("article_reply_flag")
    private Boolean articleReplyFlag;

    @ApiModelProperty("语言")
    @TableField("article_lang")
    private String articleLang;

    @ApiModelProperty("文章类型(ENUM):1-文章;2-公告")
    @TableField("article_type")
    private Integer articleType;

    @ApiModelProperty("排序")
    @TableField("article_sort")
    private Integer articleSort;

    @ApiModelProperty("状态(BOOL):0-关闭;1-启用")
    @TableField("article_status")
    private Boolean articleStatus;

    @ApiModelProperty("添加时间")
    @TableField("article_add_time")
    private Date articleAddTime;

    @ApiModelProperty("文章图片")
    @TableField("article_image")
    private String articleImage;

    @ApiModelProperty("文章作者")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("文章标签(DOT):文章标签")
    @TableField("article_tags")
    private String articleTags;

    @ApiModelProperty("是否热门")
    @TableField("article_is_popular")
    private Boolean articleIsPopular;

}
