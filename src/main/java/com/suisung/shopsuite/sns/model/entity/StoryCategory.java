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
package com.suisung.shopsuite.sns.model.entity;

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

/**
 * <p>
 * 版块
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sns_story_category")
@ApiModel(value = "StoryCategory对象", description = "版块")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoryCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("版块编号")
    @TableId(value = "story_category_id", type = IdType.AUTO)
    private Integer storyCategoryId;

    @ApiModelProperty("版块名称")
    @TableField("story_category_name")
    private String storyCategoryName;

    @ApiModelProperty("所属父类")
    @TableField("story_category_parent_id")
    private Integer storyCategoryParentId;

    @ApiModelProperty("版块图标")
    @TableField("story_category_logo")
    private String storyCategoryLogo;

    @ApiModelProperty("版块背景")
    @TableField("story_category_bg")
    private String storyCategoryBg;

    @ApiModelProperty("版块关键词")
    @TableField("story_category_keywords")
    private String storyCategoryKeywords;

    @ApiModelProperty("版块描述")
    @TableField("story_category_desc")
    private String storyCategoryDesc;

    @ApiModelProperty("版块内容数量")
    @TableField("story_category_count")
    private Integer storyCategoryCount;

    @ApiModelProperty("版块模板")
    @TableField("story_category_template")
    private String storyCategoryTemplate;

    @ApiModelProperty("版块别名")
    @TableField("story_category_alias")
    private String storyCategoryAlias;

    @ApiModelProperty("版块排序")
    @TableField("story_category_order")
    private Integer storyCategoryOrder;

    @ApiModelProperty("系统内置(ENUM):0-非内置;1-内置;")
    @TableField("story_category_buildin")
    private Boolean storyCategoryBuildin;

    @ApiModelProperty("板块链接")
    @TableField("story_category_link")
    private String storyCategoryLink;

    @ApiModelProperty("打开方式:1-弹出打开; 0-窗内打开。")
    @TableField("story_open_mode")
    private Boolean storyOpenMode;
}
