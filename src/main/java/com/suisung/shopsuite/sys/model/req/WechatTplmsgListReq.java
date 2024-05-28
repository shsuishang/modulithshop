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

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "消息模板表分页查询")
public class WechatTplmsgListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模版编号")
    private Integer tplmsgId;

    @ApiModelProperty("模版标题")
    private String tplmsgTitle;

    @ApiModelProperty("模版类型(LIST):1-订单提醒; 2-支付提醒;3-发货提醒")
    private Integer tplmsgTypeId;

    @ApiModelProperty("微信消息模板标题")
    private String tplmsgName;

    @ApiModelProperty("模版库编号")
    private String tplmsgNumber;

    @ApiModelProperty("模版库编号")
    private String tplmsgTplId;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    private Boolean tplmsgEnable;

    @ApiModelProperty("系统内置(BOOL):0-否;1-是")
    private Boolean tplmsgIsBuildin;

    @ApiModelProperty("备注")
    private String tplmsgRemark;

    @ApiModelProperty("排序")
    private Integer tplmsgSort;

    @ApiModelProperty("商城编号")
    private Integer storeId;

    @ApiModelProperty("消息模板id:关联消息模板")
    private String messageId;

    @TableField(exist = false)
    private String sidx = "tplmsg_id";


}
