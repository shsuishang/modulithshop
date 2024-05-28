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
package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 访问日志表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "访问日志表分页查询")
public class AccessHistoryListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private Long accessId;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("唯一客户编号")
    private String accessClientId;

    @ApiModelProperty("操作系统")
    private String accessOs;

    @ApiModelProperty("浏览器名称")
    private String accessBrowserName;

    @ApiModelProperty("浏览器版本")
    private String accessBrowserVersion;

    @ApiModelProperty("搜索引擎")
    private String accessSpider;

    @ApiModelProperty("国家")
    private String accessCountry;

    @ApiModelProperty("省份")
    private String accessProvince;

    @ApiModelProperty("市")
    private String accessCity;

    @ApiModelProperty("区")
    private String accessCounty;

    @ApiModelProperty("语言")
    private String accessLang;

    @ApiModelProperty("访问IP")
    private String accessIp;

    @ApiModelProperty("访问地址")
    private String accessUrl;

    @ApiModelProperty("访问时间")
    private Long accessTime;

    @ApiModelProperty("年")
    private Integer accessYear;

    @ApiModelProperty("月")
    private Integer accessMonth;

    @ApiModelProperty("日")
    private Integer accessDay;

    @ApiModelProperty("时")
    private Integer accessHour;

    @ApiModelProperty("年月日")
    private Date accessDate;

    @ApiModelProperty("时间")
    private Date accessDatetime;

    @ApiModelProperty("来源")
    private String accessReferDomain;

    @ApiModelProperty("来源")
    private String accessReferUrl;

    @ApiModelProperty("是否手机")
    private Boolean accessMobile;

    @ApiModelProperty("是否平板")
    private Boolean accessPad;

    @ApiModelProperty("是否PC")
    private Boolean accessPc;

    @ApiModelProperty("终端(ENUM):1-Phone;2-Pad;3-Pc")
    private Boolean accessDevice;

    @ApiModelProperty("终端来源(ENUM):2310-其它;2311-pc;2312-H5;2313-APP;2314-小程序")
    private Integer accessType;

    @ApiModelProperty("终端来源(ENUM):2320-其它;2321-微信;2322-百度;2323-支付宝;2324-头条")
    private Integer accessFrom;

    @ApiModelProperty("请求数据")
    private String accessData;


}
