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
import java.util.Date;

/**
 * <p>
 * 用户发票管理表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户发票管理表参数")
public class UserInvoiceAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("纳税人识别号")
    private String invoiceCompanyCode;

    @ApiModelProperty("发票内容")
    private String invoiceContent;

    @ApiModelProperty("公司开票(BOOL):0-个人;1-公司")
    private Boolean invoiceIsCompany;

    @ApiModelProperty("电子发票(ENUM):0-纸质发票;1-电子发票")
    private Integer invoiceIsElectronic;

    @ApiModelProperty("发票类型(ENUM):1-普通发票;2-增值税专用发票")
    private Integer invoiceType;

    @ApiModelProperty("添加时间")
    private Date invoiceDatetime;

    @ApiModelProperty("单位地址")
    private String invoiceAddress;

    @ApiModelProperty("单位电话")
    private String invoicePhone;

    @ApiModelProperty("开户银行")
    private String invoiceBankname;

    @ApiModelProperty("银行账号")
    private String invoiceBankaccount;

    @ApiModelProperty("收票人手机")
    private String invoiceContactMobile;

    @ApiModelProperty("收票人邮箱")
    private String invoiceContactEmail;

    @ApiModelProperty("是否默认")
    private Boolean invoiceIsDefault;

    @ApiModelProperty("收票人")
    private String invoiceContactName;

    @ApiModelProperty("收票人地区")
    private String invoiceContactArea;

    @ApiModelProperty("收票详细地址")
    private String invoiceContactAddress;


}
