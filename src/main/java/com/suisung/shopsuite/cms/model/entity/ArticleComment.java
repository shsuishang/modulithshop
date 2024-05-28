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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章评论表
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("cms_article_comment")
@ApiModel(value = "ArticleComment对象", description = "文章评论表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ArticleComment implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论编号")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @ApiModelProperty("文章编号")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty("评论者的IP地址 ")
    @TableField("comment_user_ip")
    private String commentUserIp;

    @ApiModelProperty("评论的日期和时间")
    @TableField("comment_time")
    private Date commentTime;

    @ApiModelProperty("评论内容 ")
    @TableField("comment_content")
    private String commentContent;

    @ApiModelProperty("评论的karma值 ")
    @TableField("comment_karma")
    private Integer commentKarma;

    @ApiModelProperty("评论许可（0，1，或'spam'） ")
    @TableField("comment_approved")
    private String commentApproved;

    @ApiModelProperty("评论代理（浏览器，操作系统等） ")
    @TableField("comment_agent")
    private String commentAgent;

    @ApiModelProperty("有意义的评论类型（pingback|trackback），常规评论类型时为空 ")
    @TableField("comment_type")
    private String commentType;

    @TableField("comment_parent_id")
    private Long commentParentId;

    @ApiModelProperty("评论者登录后的用户编号（未登录则为0） ")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("有帮助数")
    @TableField("comment_helpful_num")
    private Integer commentHelpfulNum;

    @ApiModelProperty("是否显示(BOOL):1-显示;0-不显示")
    @TableField("comment_is_show")
    private Boolean commentIsShow;
}
