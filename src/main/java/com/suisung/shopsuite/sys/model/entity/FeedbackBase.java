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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平台反馈表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_feedback_base")
@ApiModel(value = "FeedbackBase对象", description = "平台反馈表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FeedbackBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("反馈编号")
    @TableId(value = "feedback_id", type = IdType.AUTO)
    private Integer feedbackId;

    @ApiModelProperty("分类编号")
    @TableField("feedback_category_id")
    private Integer feedbackCategoryId;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    @TableField("user_nickname")
    private String userNickname;

    @ApiModelProperty("反馈问题:在这里描述您遇到的问题")
    @TableField("feedback_question")
    private String feedbackQuestion;

    @ApiModelProperty("页面链接")
    @TableField("feedback_question_url")
    private String feedbackQuestionUrl;

    @ApiModelProperty("反馈问题")
    @TableField("feedback_question_answer")
    private String feedbackQuestionAnswer;

    @ApiModelProperty("反馈时间")
    @TableField("feedback_question_time")
    private Date feedbackQuestionTime;

    @ApiModelProperty("回复时间")
    @TableField("feedback_question_answer_time")
    private Date feedbackQuestionAnswerTime;

    @ApiModelProperty("举报状态(BOOL):0-未处理;1-已处理")
    @TableField("feedback_question_status")
    private Boolean feedbackQuestionStatus;

    @ApiModelProperty("举报处理结果(ENUM):1-无效举报;2-恶意举报;3-有效举报")
    @TableField("feedback_question_result")
    private Integer feedbackQuestionResult;

    @ApiModelProperty("产品编号")
    @TableField("item_id")
    private Long itemId;

    @ApiModelProperty("回复人员")
    @TableField("admin_id")
    private Integer adminId;
}
