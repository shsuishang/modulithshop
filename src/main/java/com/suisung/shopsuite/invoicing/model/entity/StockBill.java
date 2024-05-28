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
package com.suisung.shopsuite.invoicing.model.entity;

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

/**
 * <p>
 * 商品出入库单据表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("invoicing_stock_bill")
@ApiModel(value = "StockBill对象", description = "商品出入库单据表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StockBill implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("购货(退货)单编号")
    @TableId(value = "stock_bill_id", type = IdType.AUTO)
    private String stockBillId;

    @ApiModelProperty("是否审核(BOOL):1-已审核;  0-未审核")
    @TableField("stock_bill_checked")
    private Boolean stockBillChecked;

    @ApiModelProperty("单据日期")
    @TableField("stock_bill_date")
    private Date stockBillDate;

    @ApiModelProperty("更新时间")
    @TableField("stock_bill_modify_time")
    private Date stockBillModifyTime;

    @ApiModelProperty("创建时间")
    @TableField("stock_bill_time")
    private Long stockBillTime;

    @ApiModelProperty("业务类别purchase_type_id, sale_type_id(ENUM):2750-入库;2700-出库;2855-采购订单;2850-销售订单")
    @TableField("bill_type_id")
    private Integer billTypeId;

    @ApiModelProperty("库存类型(ENUM)")
    @TableField("stock_transport_type_id")
    private Integer stockTransportTypeId;

    @ApiModelProperty("所属店铺")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("所属仓库")
    @TableField("warehouse_id")
    private Integer warehouseId;

    @ApiModelProperty("源单号码:一个订单一个出入库记录可以拆单")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("备注")
    @TableField("stock_bill_remark")
    private String stockBillRemark;

    @ApiModelProperty("经办人")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty("制单人")
    @TableField("admin_id")
    private Integer adminId;

    @ApiModelProperty("其它金额")
    @TableField("stock_bill_other_money")
    private BigDecimal stockBillOtherMoney;

    @ApiModelProperty("单据金额")
    @TableField("stock_bill_amount")
    private BigDecimal stockBillAmount;

    @ApiModelProperty("是否有效(BOOL):1-有效; 0-无效")
    @TableField("stock_bill_enable")
    private Boolean stockBillEnable;

    @ApiModelProperty("关联编号")
    @TableField("stock_bill_src_id")
    private String stockBillSrcId;
}
