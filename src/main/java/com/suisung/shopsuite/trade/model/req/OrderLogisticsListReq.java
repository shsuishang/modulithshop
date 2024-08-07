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
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单发货物流信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "订单发货物流信息表分页查询")
public class OrderLogisticsListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单物流编号")
    private Long orderLogisticsId;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("出入库单据id=stock_bill_id")
    private String stockBillId;

    @ApiModelProperty("订单物流单号AIRWAY BILL number")
    private String orderTrackingNumber;

    @ApiModelProperty("卖家备注发货备忘")
    private String logisticsExplain;

    @ApiModelProperty("发货时间配送时间")
    private Long logisticsTime;

    @ApiModelProperty("对应快递公司")
    private Integer logisticsId;

    @ApiModelProperty("物流名称")
    private String logisticsName;

    @ApiModelProperty("物流公司编号")
    private String logisticsNumber;

    @ApiModelProperty("送货编号")
    private Integer ssId;

    @ApiModelProperty("送货联系电话")
    private String logisticsPhone;

    @ApiModelProperty("送货联系手机")
    private String logisticsMobile;

    @ApiModelProperty("送货联系人")
    private String logisticsContacter;

    @ApiModelProperty("送货联系地址")
    private String logisticsAddress;

    @ApiModelProperty("邮政编码")
    private String logisticsPostcode;

    @ApiModelProperty("是否有效(BOOL):1-有效; 0-无效")
    private Boolean logisticsEnable;


}
