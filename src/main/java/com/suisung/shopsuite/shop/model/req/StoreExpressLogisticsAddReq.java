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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 物流 = shop_store_express
 * </p>
 *
 * @author Xinze
 * @since 2021-05-19
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "物流 = shop_store_express参数")
public class StoreExpressLogisticsAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺")
    private Integer storeId;

    @ApiModelProperty("store_express_logistics_cond ")
    private Integer chainId;

    @ApiModelProperty("物流名称")
    private String logisticsName;

    @ApiModelProperty("物流")
    private String logisticsPinyin;

    @ApiModelProperty("物流公司编号")
    private Integer logisticsNumber;

    @ApiModelProperty("电子面单状态")
    private Boolean logisticsState;

    @ApiModelProperty("对应快递公司")
    private Integer expressId;

    @ApiModelProperty("快递公司")
    private String expressName;

    @ApiModelProperty("是否为默认(BOOL):1-默认;0-非默认")
    private Boolean logisticsIsDefault;

    @ApiModelProperty("国家编码")
    private String logisticsIntl;

    @ApiModelProperty("联系手机")
    private String logisticsMobile;

    @ApiModelProperty("联系人")
    private String logisticsContacter;

    @ApiModelProperty("联系地址")
    private String logisticsAddress;

    @ApiModelProperty("物流运费")
    private String logisticsFee;

    @ApiModelProperty("是否启用(BOOL):1-启用;0-禁用")
    private Boolean logisticsIsEnable;


}
