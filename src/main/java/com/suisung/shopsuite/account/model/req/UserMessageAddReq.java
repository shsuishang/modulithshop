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
package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 短消息-聊天记录
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "短消息-聊天记录参数")
public class UserMessageAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级编号")
    private Integer messageParentId;

    @ApiModelProperty("所属用户:发送者或者接收者，如果message_kind=1则为当前用户发送的消息。")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String userNickname;

    @ApiModelProperty("消息种类(ENUM):1-发送消息;2-接收消息")
    private Integer messageKind;

    @ApiModelProperty("相关用户:发送者或者接收者")
    private Integer userOtherId;

    @ApiModelProperty("相关昵称:发送者或者接收者")
    private String userOtherNickname;

    @ApiModelProperty("消息标题")
    private String messageTitle;

    @ApiModelProperty("消息内容")
    private String messageContent;

    @ApiModelProperty("发送时间")
    private Long messageTime;

    @ApiModelProperty("是否读取(BOOL):0-未读;1-已读")
    private Boolean messageIsRead;

    @ApiModelProperty("是否删除(BOOL):0-正常状态;1-删除状态")
    private Boolean messageIsDelete;

    @ApiModelProperty("消息类型(ENUM):1-系统消息;2-用户消息")
    private Integer messageType;

    @ApiModelProperty("消息类型(ENUM):text-文本消息;img-图片消息;video-视频消息;file:文件;location:位置;redpack:红包")
    private String messageCat;

    @ApiModelProperty("消息分类(ENUM):0-默认消息;1-公告消息;2-订单消息;3-商品消息;4-余额卡券;5-服务消息")
    private Integer messageDataType;

    @ApiModelProperty("消息数据:商品编号|订单编号")
    private String messageDataId;

    @ApiModelProperty("消息长度")
    private Integer messageLength;

    @ApiModelProperty("图片宽度")
    private Integer messageW;

    @ApiModelProperty("图片高度")
    private Integer messageH;


}
