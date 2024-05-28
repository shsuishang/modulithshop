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
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 访问日志表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_access_history")
@ApiModel(value = "AccessHistory对象", description = "访问日志表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccessHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "access_id", type = IdType.AUTO)
    private Long accessId;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("唯一客户编号")
    @TableField("access_client_id")
    private String accessClientId;

    @ApiModelProperty("操作系统")
    @TableField("access_os")
    private String accessOs;

    @ApiModelProperty("浏览器名称")
    @TableField("access_browser_name")
    private String accessBrowserName;

    @ApiModelProperty("浏览器版本")
    @TableField("access_browser_version")
    private String accessBrowserVersion;

    @ApiModelProperty("搜索引擎")
    @TableField("access_spider")
    private String accessSpider;

    @ApiModelProperty("国家")
    @TableField("access_country")
    private String accessCountry;

    @ApiModelProperty("省份")
    @TableField("access_province")
    private String accessProvince;

    @ApiModelProperty("市")
    @TableField("access_city")
    private String accessCity;

    @ApiModelProperty("区")
    @TableField("access_county")
    private String accessCounty;

    @ApiModelProperty("语言")
    @TableField("access_lang")
    private String accessLang;

    @ApiModelProperty("访问IP")
    @TableField("access_ip")
    private String accessIp;

    @ApiModelProperty("访问地址")
    @TableField("access_url")
    private String accessUrl;

    @ApiModelProperty("访问SKU")
    @TableField("item_id")
    private Long itemId;

    @ApiModelProperty("请求方法")
    @TableField("access_method")
    private String accessMethod;

    @ApiModelProperty("访问时间")
    @TableField("access_time")
    private Long accessTime;

    @ApiModelProperty("年")
    @TableField("access_year")
    private Integer accessYear;

    @ApiModelProperty("月")
    @TableField("access_month")
    private Integer accessMonth;

    @ApiModelProperty("日")
    @TableField("access_day")
    private Integer accessDay;

    @ApiModelProperty("时")
    @TableField("access_hour")
    private Integer accessHour;

    @ApiModelProperty("年月日")
    @TableField("access_date")
    private Date accessDate;

    @ApiModelProperty("时间")
    @TableField("access_datetime")
    private Date accessDatetime;

    @ApiModelProperty("来源")
    @TableField("access_refer_domain")
    private String accessReferDomain;

    @ApiModelProperty("来源")
    @TableField("access_refer_url")
    private String accessReferUrl;

    @ApiModelProperty("是否手机")
    @TableField("access_mobile")
    private Boolean accessMobile;

    @ApiModelProperty("是否平板")
    @TableField("access_pad")
    private Boolean accessPad;

    @ApiModelProperty("是否PC")
    @TableField("access_pc")
    private Boolean accessPc;

    @ApiModelProperty("终端(ENUM):1-Phone;2-Pad;3-Pc")
    @TableField("access_device")
    private Integer accessDevice;

    @ApiModelProperty("终端来源(ENUM):2310-其它;2311-pc;2312-H5;2313-APP;2314-小程序")
    @TableField("access_type")
    private Integer accessType;

    @ApiModelProperty("终端来源(ENUM):2320-其它;2321-微信;2322-百度;2323-支付宝;2324-头条")
    @TableField("access_from")
    private Integer accessFrom;

    @ApiModelProperty("请求数据")
    @TableField("access_data")
    private String accessData;

    @ApiModelProperty("请求数据")
    @TableField(exist = false)
    private Map<String, String> accessReq;
}
