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
package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.annotation.QueryField;
import com.suisung.shopsuite.core.annotation.QueryType;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息模板表
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "消息模板表分页查询")
public class MessageTemplateListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模板编号")
    private String messageId;

    @ApiModelProperty("模板编码")
    private String messageCode;

    @ApiModelProperty("模板名称")
    @QueryField(type = QueryType.LIKE)
    private String messageName;

    @ApiModelProperty("邮件标题")
    private String messageEmailTitle;

    @ApiModelProperty("邮件内容")
    private String messageEmailContent;

    @ApiModelProperty("站内消息")
    private String messageContent;

    @ApiModelProperty("短信内容")
    private String messageSms;

    @ApiModelProperty("APP内容")
    private String messageApp;

    @ApiModelProperty("消息类型(ENUM):1-用户;2-商家;3-平台;")
    private Integer messageType;

    @ApiModelProperty("站内通知(BOOL):0-禁用;1-启用")
    private Boolean messageEnable;

    @ApiModelProperty("短息通知(BOOL):0-禁用;1-启用")
    private Boolean messageSmsEnable;

    @ApiModelProperty("邮件通知(BOOL):0-禁用;1-启用")
    private Boolean messageEmailEnable;

    @ApiModelProperty("微信通知(BOOL):0-禁用;1-启用")
    private Boolean messageWechatEnable;

    @ApiModelProperty("小程序通知(BOOL):0-禁用;1-启用")
    private Boolean messageXcxEnable;

    @ApiModelProperty("APP推送(BOOL):0-禁用;1-启用")
    private Boolean messageAppEnable;

    @ApiModelProperty("手机短信(BOOL):0-不强制;1-强制")
    private Boolean messageSmsForce;

    @ApiModelProperty("邮件(BOOL):0-不强制;1-强制")
    private Boolean messageEmailForce;

    @ApiModelProperty("APP(BOOL):0-不强制;1-强制")
    private Boolean messageAppForce;

    @ApiModelProperty("站内信(BOOL):0-不强制;1-强制")
    private Boolean messageForce;

    @ApiModelProperty("消息分组(ENUM):0-默认消息;1-公告消息;2-订单消息;3-商品消息;4-余额卡券;5-服务消息")
    private Integer messageCategory;

    @ApiModelProperty("消息排序")
    private Integer messageOrder;

    @ApiModelProperty("模板编号")
    private String messageTplId;

    public MessageTemplateListReq() {
        setSidx("message_id");
    }
}
