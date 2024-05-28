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
package com.suisung.shopsuite.pt.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品基础表-SPU表
 * </p>
 *
 * @author Xinze
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品基础表-SPU表分页查询")
public class ProductBaseListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("SPU商家编码:货号")
    private String productNumber;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("商品卖点:商品广告词")
    private String productTips;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("商品主图")
    private String productImage;

    @ApiModelProperty("产品视频 ")
    private String productVideo;

    @ApiModelProperty("选择售卖区域:完成售卖区域及运费设置")
    private Integer transportTypeId;

    @ApiModelProperty("库存锁定(ENUM):1001-下单锁定;1002-支付锁定;")
    private Integer productInventoryLock;

    @ApiModelProperty("每人限购")
    private Integer productBuyLimit;

    @ApiModelProperty("是否锁定(BOOL):0-未锁定; 1-锁定,参加团购的商品不予许修改")
    private Boolean productIsLock;

    @ApiModelProperty("产品来源编号")
    private Long productSrcId;

    @ApiModelProperty("商品来源(ENUM):1000-发布;1001-天猫;1002-淘宝;1003-阿里巴巴;1004-京东;")
    private Integer productFrom;


}
