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

/**
 * <p>
 * 消息模板表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_message_template")
@ApiModel(value = "MessageTemplate对象", description = "消息模板表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MessageTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模板编号")
    @TableId(value = "message_id", type = IdType.AUTO)
    private String messageId;

    @ApiModelProperty("模板编码")
    @TableField("message_code")
    private String messageCode;

    @ApiModelProperty("模板名称")
    @TableField("message_name")
    private String messageName;

    @ApiModelProperty("邮件标题")
    @TableField("message_email_title")
    private String messageEmailTitle;

    @ApiModelProperty("邮件内容")
    @TableField("message_email_content")
    private String messageEmailContent;

    @ApiModelProperty("站内消息")
    @TableField("message_content")
    private String messageContent;

    @ApiModelProperty("短信内容")
    @TableField("message_sms")
    private String messageSms;

    @ApiModelProperty("APP内容")
    @TableField("message_app")
    private String messageApp;

    @ApiModelProperty("消息类型(ENUM):1-用户;2-商家;3-平台;")
    @TableField("message_type")
    private Integer messageType;

    @ApiModelProperty("站内通知(BOOL):0-禁用;1-启用")
    @TableField("message_enable")
    private Boolean messageEnable;

    @ApiModelProperty("短息通知(BOOL):0-禁用;1-启用")
    @TableField("message_sms_enable")
    private Boolean messageSmsEnable;

    @ApiModelProperty("邮件通知(BOOL):0-禁用;1-启用")
    @TableField("message_email_enable")
    private Boolean messageEmailEnable;

    @ApiModelProperty("微信通知(BOOL):0-禁用;1-启用")
    @TableField("message_wechat_enable")
    private Boolean messageWechatEnable;

    @ApiModelProperty("小程序通知(BOOL):0-禁用;1-启用")
    @TableField("message_xcx_enable")
    private Boolean messageXcxEnable;

    @ApiModelProperty("APP推送(BOOL):0-禁用;1-启用")
    @TableField("message_app_enable")
    private Boolean messageAppEnable;

    @ApiModelProperty("手机短信(BOOL):0-不强制;1-强制")
    @TableField("message_sms_force")
    private Boolean messageSmsForce;

    @ApiModelProperty("邮件(BOOL):0-不强制;1-强制")
    @TableField("message_email_force")
    private Boolean messageEmailForce;

    @ApiModelProperty("APP(BOOL):0-不强制;1-强制")
    @TableField("message_app_force")
    private Boolean messageAppForce;

    @ApiModelProperty("站内信(BOOL):0-不强制;1-强制")
    @TableField("message_force")
    private Boolean messageForce;

    @ApiModelProperty("消息分组(ENUM):0-默认消息;1-公告消息;2-订单消息;3-商品消息;4-余额卡券;5-服务消息")
    @TableField("message_category")
    private Integer messageCategory;

    @ApiModelProperty("消息排序")
    @TableField("message_order")
    private Integer messageOrder;

    @ApiModelProperty("模板编号")
    @TableField("message_tpl_id")
    private String messageTplId;
}
