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
package com.suisung.shopsuite.trade.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单店铺评价表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "订单店铺评价表参数")
public class OrderCommentAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("卖家店铺编号-冗余")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("买家编号")
    private Integer userId;

    @ApiModelProperty("买家姓名")
    private String userName;

    @ApiModelProperty("获得积分-冗余，独立表记录")
    private BigDecimal commentPoints;

    @ApiModelProperty("评价星级1-5积分")
    private Integer commentScores;

    @ApiModelProperty("评价内容")
    private String commentContent;

    @ApiModelProperty("评论上传的图片：|分割多张图片")
    private String commentImage;

    @ApiModelProperty("有帮助")
    private Integer commentHelpful;

    @ApiModelProperty("无帮助")
    private Integer commentNohelpful;

    @ApiModelProperty("评价时间")
    private Date commentTime;

    @ApiModelProperty("匿名评价")
    private Boolean commentIsAnonymous;

    @ApiModelProperty("评价信息的状态(BOOL): 1-正常显示; 0-禁止显示")
    private Boolean commentEnable;

    @ApiModelProperty("描述相符评分 - order_buyer_evaluation_status , 评价状态改变后不需要再次评论，根据订单走")
    private Boolean commentStoreDescCredit;

    @ApiModelProperty("服务态度评分 - order_buyer_evaluation_status")
    private Boolean commentStoreServiceCredit;

    @ApiModelProperty("发货速度评分 - order_buyer_evaluation_status")
    private Boolean commentStoreDeliveryCredit;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;


}
