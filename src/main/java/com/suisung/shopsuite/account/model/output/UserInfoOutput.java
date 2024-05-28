package com.suisung.shopsuite.account.model.output;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户基本信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Data
@ApiModel(value = "初始化用户对象", description = "初始化用户对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoOutput implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    @TableField("user_nickname")
    private String userNickname;

    @ApiModelProperty("用户头像")
    @TableField("user_avatar")
    private String userAvatar;

    @ApiModelProperty("手机号码(mobile)")
    @TableField("user_mobile")
    private String userMobile;

    @ApiModelProperty("国家编码")
    @TableField("user_intl")
    private String userIntl;

    @ApiModelProperty("性别(ENUM):0-保密;1-男;  2-女;")
    @TableField("user_gender")
    private Integer userGender;

    @ApiModelProperty("生日(DATE)")
    @TableField("user_birthday")
    private Date userBirthday;

    @ApiModelProperty("用户邮箱(email)")
    @TableField("user_email")
    private String userEmail;

    @ApiModelProperty("用户等级")
    @TableField("user_level_id")
    private Integer userLevelId;

    @ApiModelProperty(value = "真实姓名")
    @TableField("user_realname")
    private String userRealname;

    @ApiModelProperty(value = "身份证")
    @TableField("user_idcard")
    private String userIdcard;

    @ApiModelProperty(value = "身份证图片(DOT)")
    @TableField("user_idcard_images")
    private String userIdcardImages;

    @ApiModelProperty("身份证图片(DOT)")
    @TableField(exist = false)
    private List<String> userIdcardImageList = new ArrayList<>();

    @ApiModelProperty("认证状态(ENUM):0-未认证;1-待审核;2-认证通过;3-认证失败")
    @TableField("user_is_authentication")
    private Integer userIsAuthentication;

    @ApiModelProperty("状态(ENUM):0-锁定;1-已激活;2-未激活;")
    @TableField("user_state")
    private Integer userState;

    //用户资源
    @ApiModelProperty("货币编号")
    @TableField("currency_id")
    private Integer currencyId;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("用户资金")
    @TableField("user_money")
    private BigDecimal userMoney;

    @ApiModelProperty("冻结资金:待结算余额")
    @TableField("user_money_frozen")
    private BigDecimal userMoneyFrozen;

    @ApiModelProperty("充值卡余额")
    @TableField("user_recharge_card")
    private BigDecimal userRechargeCard;

    @ApiModelProperty("冻结充值卡:待结算")
    @TableField("user_recharge_card_frozen")
    private BigDecimal userRechargeCardFrozen;

    @ApiModelProperty("积分")
    @TableField("user_points")
    private BigDecimal userPoints;

    @ApiModelProperty("冻结积分")
    @TableField("user_points_frozen")
    private BigDecimal userPointsFrozen;

    @ApiModelProperty("经验值")
    @TableField("user_exp")
    private Long userExp;

    @ApiModelProperty("可用信用")
    @TableField("user_credit")
    private BigDecimal userCredit;

    @ApiModelProperty("冻结额度")
    @TableField("user_credit_frozen")
    private BigDecimal userCreditFrozen;

    @ApiModelProperty("使用信用")
    @TableField("user_credit_used")
    private BigDecimal userCreditUsed;

    @ApiModelProperty("信用额度")
    @TableField("user_credit_total")
    private BigDecimal userCreditTotal;

    @ApiModelProperty("保证金")
    @TableField("user_margin")
    private BigDecimal userMargin;

    @ApiModelProperty("红包额度")
    @TableField("user_redpack")
    private BigDecimal userRedpack;

    @ApiModelProperty("红包冻结额度")
    @TableField("user_redpack_frozen")
    private BigDecimal userRedpackFrozen;


    //其它信息
    @ApiModelProperty("角色列表")
    @TableField(exist = false)
    private List<String> roles;

    @ApiModelProperty("权限列表")
    @TableField(exist = false)
    private List<String> permissions;

    @ApiModelProperty("角色编号:0-用户;2-商家;3-门店;9-平台;")
    @TableField(exist = false)
    private Integer roleId = 0;

    @ApiModelProperty("分站编号:0-总站")
    @TableField(exist = false)
    private Integer siteId = 0;

    @ApiModelProperty("店铺编号")
    @TableField(exist = false)
    private Integer storeId = 0;

    @ApiModelProperty("门店编号")
    @TableField(exist = false)
    private Integer chainId = 0;

    @ApiModelProperty("后台管理:admin=1;移动端front=0")
    @TableField(exist = false)
    private Integer clientId = 0;

    @ApiModelProperty("详细地址")
    @TableField(exist = false)
    private String udAddress;

    @ApiModelProperty("等级名称")
    @TableField(exist = false)
    private String userLevelName;

    @ApiModelProperty("注册时间")
    private Long userRegTime;

    @ApiModelProperty("登录时间")
    @TableField(exist = false)
    private Long userLoginTime;

    @ApiModelProperty("标签标题(DOT)")
    @TableField(exist = false)
    private String tagTitles;

    @ApiModelProperty("标签标题(DOT)")
    @TableField(exist = false)
    private List<String> tagTitleList;

    @ApiModelProperty("分组名称(DOT)")
    @TableField(exist = false)
    private String tagGroupNames;

    @ApiModelProperty("用户标签(DOT)")
    @TableField(exist = false)
    private String tagIds;

    @ApiModelProperty("本月订单")
    @TableField(exist = false)
    private BigDecimal monthOrder;

    @ApiModelProperty("总计订单")
    @TableField(exist = false)
    private BigDecimal totalOrder;

    @ApiModelProperty("本月消费金额")
    @TableField(exist = false)
    private BigDecimal monthTrade;

    @ApiModelProperty("总消费金额")
    @TableField(exist = false)
    private BigDecimal totalTrade;

    @ApiModelProperty("优惠券数量")
    @TableField(exist = false)
    private Long voucher;

    @ApiModelProperty("待付款数量")
    @TableField(exist = false)
    private Long waitPayNum = 0L;

    @ApiModelProperty("收藏数量")
    @TableField(exist = false)
    private Long favoritesGoodsNum = 0L;

    @ApiModelProperty("关注数量")
    @TableField(exist = false)
    private Long concernNum = 0L;

    @ApiModelProperty("未读消息数量")
    @TableField(exist = false)
    private Integer unreadNumber = 0;

    @ApiModelProperty("佣金总额:历史总额度")
    @TableField(exist = false)
    private BigDecimal commissionAmount = BigDecimal.ZERO;

    @ApiModelProperty("累计佣金")
    @TableField(exist = false)
    private BigDecimal userCommissionNow = BigDecimal.ZERO;

    @ApiModelProperty("本月预估收益")
    @TableField(exist = false)
    private BigDecimal monthCommissionBuy = BigDecimal.ZERO;

    @ApiModelProperty("上级用户编号")
    @TableField(exist = false)
    private Integer userParentId;
}
