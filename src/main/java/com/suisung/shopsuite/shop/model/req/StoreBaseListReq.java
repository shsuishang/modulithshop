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
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 店铺基础信息表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "店铺基础信息表分页查询")
public class StoreBaseListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("店铺等级")
    private Integer storeGradeId;

    @ApiModelProperty("店铺logo")
    private String storeLogo;

    @ApiModelProperty("纬度")
    private Double storeLatitude;

    @ApiModelProperty("经度")
    private Double storeLongitude;

    @ApiModelProperty("配送区域(DOT)")
    private String storeDeliverDistrictId;

    @ApiModelProperty("是否自营(ENUM): 1-自营;0-非自营")
    private Boolean storeIsSelfsupport;

    @ApiModelProperty("店铺类型(ENUM): 1-卖家店铺; 2-供应商店铺")
    private Integer storeType;

    @ApiModelProperty("店铺状态(BOOL):0-关闭;  1-运营中")
    private Boolean storeIsOpen;

    @ApiModelProperty("店铺分类编号")
    private Integer storeCategoryId;

    @ApiModelProperty("免费服务(DOT)")
    private String storeO2oTags;

    @ApiModelProperty("是否O2O(BOOL):0-否;1-是")
    private Boolean storeO2oFlag;

    @ApiModelProperty("所属商圈(DOT)")
    private String storeCircle;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;


}
