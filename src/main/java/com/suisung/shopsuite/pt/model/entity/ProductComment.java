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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品评价表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pt_product_comment")
@ApiModel(value = "ProductComment对象", description = "商品评价表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductComment implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评价编号")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("产品编号")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("商品编号")
    @TableField("item_id")
    private Long itemId;

    @ApiModelProperty("商品规格")
    @TableField("item_name")
    private String itemName;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty("买家编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("买家姓名:user_nickname")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("获得积分:冗余，独立表记录")
    @TableField("comment_points")
    private BigDecimal commentPoints;

    @ApiModelProperty("评价星级:1-5积分")
    @TableField("comment_scores")
    private Integer commentScores;

    @ApiModelProperty("评价内容")
    @TableField("comment_content")
    private String commentContent;

    @ApiModelProperty("评论上传的图片(DOT)")
    @TableField("comment_image")
    private String commentImage;

    @ApiModelProperty("有帮助")
    @TableField("comment_helpful")
    private Integer commentHelpful;

    @ApiModelProperty("无帮助")
    @TableField("comment_nohelpful")
    private Integer commentNohelpful;

    @ApiModelProperty("评价时间")
    @TableField("comment_time")
    private Date commentTime;

    @ApiModelProperty("匿名评价")
    @TableField("comment_is_anonymous")
    private Boolean commentIsAnonymous;

    @ApiModelProperty("评价信息的状态(BOOL): 1-正常显示; 0-禁止显示")
    @TableField("comment_enable")
    private Boolean commentEnable;

    @ApiModelProperty("门店编号")
    @TableField("chain_id")
    private Integer chainId;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("评论上传的图片(DOT)")
    @TableField(exist = false)
    private List<String> commentImages;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userAvatar;

    @ApiModelProperty("")
    @TableField(exist = false)
    private ProductCommentReply commentReply;

    @ApiModelProperty("")
    @TableField(exist = false)
    private Integer helpful;

    @ApiModelProperty("产品名称")
    @TableField(exist = false)
    private String productName;

}
