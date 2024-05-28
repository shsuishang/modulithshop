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
package com.suisung.shopsuite.invoicing.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品出入库单据表参数")
public class StockBillAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("是否审核(BOOL):1-已审核;  0-未审核")
    private Boolean stockBillChecked;

    @ApiModelProperty("单据日期")
    private Date stockBillDate;

    @ApiModelProperty("更新时间")
    private Date stockBillModifyTime;

    @ApiModelProperty("创建时间")
    private Long stockBillTime;

    @ApiModelProperty("业务类别purchase_type_id, sale_type_id(ENUM):2750-入库;2700-出库;2855-采购订单;2850-销售订单")
    private Integer billTypeId;

    @ApiModelProperty("库存类型(ENUM)")
    private Integer stockTransportTypeId;

    @ApiModelProperty("所属店铺")
    private Integer storeId;

    @ApiModelProperty("所属仓库")
    private Integer warehouseId;

    @ApiModelProperty("源单号码:一个订单一个出入库记录可以拆单")
    private String orderId;

    @ApiModelProperty("备注")
    private String stockBillRemark;

    @ApiModelProperty("经办人")
    private Integer employeeId;

    @ApiModelProperty("制单人")
    private Integer adminId;

    @ApiModelProperty("其它金额")
    private BigDecimal stockBillOtherMoney;

    @ApiModelProperty("单据金额")
    private BigDecimal stockBillAmount;

    @ApiModelProperty("是否有效(BOOL):1-有效; 0-无效")
    private Boolean stockBillEnable;

    @ApiModelProperty("关联编号")
    private String stockBillSrcId;


}
