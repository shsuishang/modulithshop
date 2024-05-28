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
package com.suisung.shopsuite.sns.model.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 动态信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "动态信息表分页查询")
public class StoryBaseListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private Integer storyId;

    @ApiModelProperty("会员编号")
    private Integer userId;

    @ApiModelProperty("搜索索引:检索使用")
    private String storyIndex;

    @ApiModelProperty("标题")
    private String storyTitle;

    @ApiModelProperty("内容(HTMl)")
    private String storyContent;

    @ApiModelProperty("图片(DOT)")
    private String storyFile;

    @ApiModelProperty("视频地址")
    private String storyVideo;

    @ApiModelProperty("类型(ENUM): 1-文字; 2-图片; 3-音乐; 4-视频; 5-商品")
    private Boolean storyType;

    @ApiModelProperty("转发源")
    private Integer storySrcId;

    @ApiModelProperty("添加时间")
    private Long storyTime;

    @ApiModelProperty("状态(ENUM):0-草稿;1-发布")
    private Boolean storyStatus;

    @ApiModelProperty("是否删除(BOOL):0-删除;1-有效")
    private Boolean storyEnable;

    @ApiModelProperty("隐私可见度(ENUM):0-所有人可见; 1-好友可见; 2-仅自己可见")
    private Boolean storyPrivacy;

    @ApiModelProperty("评论数")
    private Integer storyCommentCount;

    @ApiModelProperty("转发数")
    private Integer storyForwardCount;

    @ApiModelProperty("点赞数")
    private Integer storyLikeCount;

    @ApiModelProperty("帖子收藏数")
    private Integer storyCollectionCount;

    @ApiModelProperty("是否可以转发(BOOL):0-不可转发;1-可以转发")
    private Boolean storyForward;

    @ApiModelProperty("置顶(BOOL):0-正常;1-置顶")
    private Boolean storyIsTop;

    @ApiModelProperty("访问数")
    private Integer storyBrowerCount;

    @ApiModelProperty("商品编号(DOT)")
    private String itemId;

    @ApiModelProperty("分类编号")
    private Integer storyCategoryId;

    @ApiModelProperty("标签(DOT)")
    private String storyTags;

    @ApiModelProperty("数据源(ENUM): 1001-帖子; 1002-商品评论; 1003-商品问答")
    private Integer storySource;

    @ApiModelProperty("商品编号")
    private Long productId;

    @TableField(exist = false)
    private String sidx = "story_id";


}
