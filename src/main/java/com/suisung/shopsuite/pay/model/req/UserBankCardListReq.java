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
package com.suisung.shopsuite.pay.model.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 结算账户表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "结算账户表分页查询")
public class UserBankCardListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("卡编号")
    private Integer userBankId;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("银行编号")
    private Integer bankId;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("账户类别(ENUM):1001-微信;1002-支付宝;1003-现金;1004-银行")
    private Integer settlementAccountTypeId;

    @ApiModelProperty("卡号账户名称")
    private String userBankCardName;

    @ApiModelProperty("银行卡卡号")
    private String userBankCardCode;

    @ApiModelProperty("开户支行名称")
    private String userBankCardAddress;

    @ApiModelProperty("银行卡添加时间")
    private Date userBankCardTime;

    @ApiModelProperty("银行预留手机号")
    private String userBankCardMobile;

    @ApiModelProperty("开户省份编号")
    private String provinceId;

    @ApiModelProperty("开户城市编号")
    private String cityId;

    @ApiModelProperty("卡的类型编号")
    private Integer typeId;

    @ApiModelProperty("用户当前所选默认提现卡")
    private Boolean userBankDefault;

    @ApiModelProperty("是否可用(BOOL):1-启用;0-禁用")
    private Integer userBankEnable;

    @ApiModelProperty("余额日期")
    private Integer userBankBeginDate;

    @ApiModelProperty("账户余额")
    private BigDecimal userBankAmountMoney;

    @ApiModelProperty("国家区号")
    private String userIntl;

    @TableField(exist = false)
    private String sidx = "user_bank_id";


}
