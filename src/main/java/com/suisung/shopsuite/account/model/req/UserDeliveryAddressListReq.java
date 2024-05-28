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
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户地址表分页查询")
public class UserDeliveryAddressListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地址编号")
    private Integer udId;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("联系人")
    private String udName;

    @ApiModelProperty("国家编码")
    private String udIntl;

    @ApiModelProperty("手机号码")
    private String udMobile;

    @ApiModelProperty("联系电话")
    private String udTelephone;

    @ApiModelProperty("省编号")
    private Integer udProvinceId;

    @ApiModelProperty("省份")
    private String udProvince;

    @ApiModelProperty("市编号")
    private Integer udCityId;

    @ApiModelProperty("市")
    private String udCity;

    @ApiModelProperty("县")
    private Integer udCountyId;

    @ApiModelProperty("县区")
    private String udCounty;

    @ApiModelProperty("详细地址")
    private String udAddress;

    @ApiModelProperty("邮政编码")
    private String udPostalcode;

    @ApiModelProperty("地址标签(ENUM):1001-家里;1002-公司")
    private String udTagName;

    @ApiModelProperty("经度")
    private Double udLongitude;

    @ApiModelProperty("纬读")
    private Double udLatitude;

    @ApiModelProperty("添加时间")
    private Date udTime;

    @ApiModelProperty("是否默认(BOOL):0-非默认;1-默认")
    private Boolean udIsDefault;

    public UserDeliveryAddressListReq() {
        setSidx("ud_is_default");
        setSort(Constants.ORDER_BY_DESC);
    }
}
