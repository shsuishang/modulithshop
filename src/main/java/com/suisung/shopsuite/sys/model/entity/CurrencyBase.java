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
package com.suisung.shopsuite.sys.model.entity;

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
 * 货币设置表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_currency_base")
@ApiModel(value = "CurrencyBase对象", description = "货币设置表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CurrencyBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号:全球各国的电话区号")
    @TableId(value = "currency_id", type = IdType.AUTO)
    private Integer currencyId;

    @ApiModelProperty("货币名称")
    @TableField("currency_title")
    private String currencyTitle;

    @ApiModelProperty("货币图标")
    @TableField("currency_lang")
    private String currencyLang;

    @ApiModelProperty("货币图标")
    @TableField("currency_img")
    private String currencyImg;

    @ApiModelProperty("左符号")
    @TableField("currency_symbol_left")
    private String currencySymbolLeft;

    @ApiModelProperty("右符号")
    @TableField("currency_symbol_right")
    private String currencySymbolRight;

    @ApiModelProperty("小数位")
    @TableField("currency_decimal_place")
    private Boolean currencyDecimalPlace;

    @ApiModelProperty("汇率")
    @TableField("currency_exchange_rate")
    private BigDecimal currencyExchangeRate;

    @ApiModelProperty("是否启用(BOOL):0-停用; 1-启用")
    @TableField("currency_status")
    private Boolean currencyStatus;

    @ApiModelProperty("修改时间")
    @TableField("currency_time")
    private Date currencyTime;

    @ApiModelProperty("默认汇率(BOOL):0-否; 1-默认")
    @TableField("currency_is_default")
    private Boolean currencyIsDefault;

    @ApiModelProperty("默认语言(BOOL):0-否; 1-默认")
    @TableField("currency_default_lang")
    private Boolean currencyDefaultLang;

    @ApiModelProperty("默认UI(BOOL):0-否; 1-默认")
    @TableField("currency_is_standard")
    private Boolean currencyIsStandard;

    @ApiModelProperty("排序")
    @TableField("currency_sort")
    private Integer currencySort;


}
