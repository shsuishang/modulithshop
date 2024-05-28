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
package com.suisung.shopsuite.trade.model.entity;

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
 * 订单发货物流信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_order_logistics")
@ApiModel(value = "OrderLogistics对象", description = "订单发货物流信息表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderLogistics implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单物流编号")
    @TableId(value = "order_logistics_id", type = IdType.AUTO)
    private Long orderLogisticsId;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("出入库单据id=stock_bill_id")
    @TableField("stock_bill_id")
    private String stockBillId;

    @ApiModelProperty("订单物流单号AIRWAY BILL number")
    @TableField("order_tracking_number")
    private String orderTrackingNumber;

    @ApiModelProperty("卖家备注发货备忘")
    @TableField("logistics_explain")
    private String logisticsExplain;

    @ApiModelProperty("发货时间配送时间")
    @TableField("logistics_time")
    private Long logisticsTime;

    @ApiModelProperty("对应快递公司")
    @TableField("logistics_id")
    private Integer logisticsId;

    @ApiModelProperty("快递名称")
    @TableField("express_name")
    private String expressName;

    @ApiModelProperty("快递编号")
    @TableField("express_id")
    private Integer expressId;

    @ApiModelProperty("送货编号")
    @TableField("ss_id")
    private Integer ssId;

    @ApiModelProperty("送货联系电话")
    @TableField("logistics_phone")
    private String logisticsPhone;

    @ApiModelProperty("送货联系手机")
    @TableField("logistics_mobile")
    private String logisticsMobile;

    @ApiModelProperty("送货联系人")
    @TableField("logistics_contacter")
    private String logisticsContacter;

    @ApiModelProperty("送货联系地址")
    @TableField("logistics_address")
    private String logisticsAddress;

    @ApiModelProperty("邮政编码")
    @TableField("logistics_postcode")
    private String logisticsPostcode;

    @ApiModelProperty("是否有效(BOOL):1-有效; 0-无效")
    @TableField("logistics_enable")
    private Boolean logisticsEnable;
}
