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
package com.suisung.shopsuite.cms.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "文章内容参数")
public class ArticleBaseAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标题")
    private String articleTitle;

    @ApiModelProperty("文章别名slug")
    private String articleName;

    @ApiModelProperty("文章摘要")
    private String articleExcerpt;

    @ApiModelProperty("文章内容(HTML)")
    private String articleContent;

    @ApiModelProperty("调用网址:默认为本页面构造的网址")
    private String articleUrl;

    @ApiModelProperty("所属分类")
    private Integer categoryId;

    @ApiModelProperty("模板")
    private String articleTemplate;

    @ApiModelProperty("SEO标题")
    private String articleSeoTitle;

    @ApiModelProperty("SEO关键字")
    private String articleSeoKeywords;

    @ApiModelProperty("SEO描述")
    private String articleSeoDescription;

    @ApiModelProperty("是否启用问答留言(BOOL):0-否;1-是")
    private Boolean articleReplyFlag;

    @ApiModelProperty("语言")
    private String articleLang;

    @ApiModelProperty("文章类型(ENUM):1-文章;2-公告")
    private Integer articleType;

    @ApiModelProperty("排序")
    private Integer articleSort;

    @ApiModelProperty("状态(BOOL):0-关闭;1-启用")
    private Boolean articleStatus;

    @ApiModelProperty("添加时间")
    private Date articleAddTime;

    @ApiModelProperty("文章图片")
    private String articleImage;

    @ApiModelProperty("文章作者")
    private Integer userId;

    @ApiModelProperty("文章标签(DOT):文章标签")
    private String articleTags;

    @ApiModelProperty("是否热门")
    private Boolean articleIsPopular;


}
