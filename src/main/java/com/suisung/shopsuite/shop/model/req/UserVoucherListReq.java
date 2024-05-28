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
package com.suisung.shopsuite.shop.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户优惠券表
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户优惠券表分页查询")
public class UserVoucherListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("代金券编号")
    private Integer userVoucherId;

    @ApiModelProperty("代金券模版编号")
    private Integer activityId;

    @ApiModelProperty("代金券状态(ENUM):1501-未用;1502-已用;1503-过期;1504-收回")
    private Integer voucherStateId;

    @ApiModelProperty("代金券发放日期")
    private Date userVoucherTime;

    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("使用时间")
    private Date userVoucherActivetime;

    @ApiModelProperty("优惠券可抵扣价格")
    private BigDecimal voucherPrice;

    @ApiModelProperty("使用优惠券的订单金额")
    private BigDecimal voucherSubtotal;

    @ApiModelProperty("失效日期")
    private Date voucherEndDate;

    @ApiModelProperty("所属店铺编号")
    private Integer storeId;

    @ApiModelProperty("单品优惠商品编号(DOT)")
    private String itemId;

    @ApiModelProperty("优惠券类型(ENUM): 0-普通优惠券;1-免拼券")
    private Integer voucherType;

    @ApiModelProperty("到期使用时间")
    private Date voucherStartDate;

    @ApiModelProperty("线下活动提货码")
    private String writeoffCode;

    @ApiModelProperty("使用方式")
    private Integer voucherUserWay;

    @ApiModelProperty("优惠券是否生效(BOOL): false-未生效;true-生效")
    private Boolean voucherEffect = false;

    public UserVoucherListReq() {
        setSidx("user_voucher_id");
        setSort(Constants.ORDER_BY_DESC);
    }

}
