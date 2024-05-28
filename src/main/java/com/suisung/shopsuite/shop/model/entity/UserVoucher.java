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
package com.suisung.shopsuite.shop.model.entity;

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
 * 用户优惠券表
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("shop_user_voucher")
@ApiModel(value = "UserVoucher对象", description = "用户优惠券表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserVoucher implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("代金券编号")
    @TableId(value = "user_voucher_id", type = IdType.AUTO)
    private Integer userVoucherId;

    @ApiModelProperty("代金券模版编号")
    @TableField("activity_id")
    private Integer activityId;

    @ApiModelProperty("活动名称")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty("活动规则(JSON):不检索{rule_id:{}, rule_id:{}},统一解析规则{\"requirement\":{\"buy\":{\"item\":[1,2,3],\"subtotal\":\"通过计算修正满足的条件\"}},\"rule\":[{\"total\":100,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}},{\"total\":200,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}}]}")
    @TableField("activity_rule")
    private String activityRule;

    @ApiModelProperty("代金券状态(ENUM):1501-未用;1502-已用;1503-过期;1504-收回")
    @TableField("voucher_state_id")
    private Integer voucherStateId;

    @ApiModelProperty("代金券发放日期")
    @TableField("user_voucher_time")
    private Date userVoucherTime;

    @ApiModelProperty("所属用户")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("使用时间")
    @TableField("user_voucher_activetime")
    private Date userVoucherActivetime;

    @ApiModelProperty("优惠券可抵扣价格")
    @TableField("voucher_price")
    private BigDecimal voucherPrice;

    @ApiModelProperty("使用优惠券的订单金额")
    @TableField("voucher_subtotal")
    private BigDecimal voucherSubtotal;

    @ApiModelProperty("优惠券失效日期")
    @TableField("voucher_end_date")
    private Long voucherEndDate;

    @ApiModelProperty("所属店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("单品优惠商品编号(DOT)")
    @TableField("item_id")
    private String itemId;

    @ApiModelProperty("优惠券类型(ENUM): 0-普通优惠券;1-免拼券")
    @TableField("voucher_type")
    private Integer voucherType;

    @ApiModelProperty("优惠券开始日期")
    @TableField("voucher_start_date")
    private Long voucherStartDate;

    @ApiModelProperty("线下活动提货码")
    @TableField("writeoff_code")
    private String writeoffCode;
}
