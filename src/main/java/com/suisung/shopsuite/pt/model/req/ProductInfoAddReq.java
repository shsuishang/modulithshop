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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品信息表参数")
public class ProductInfoAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("属性(JSON) - 辅助属性及VAL")
    private String productAssist;

    @ApiModelProperty("规格(JSON)-规格、规格值、goods_id  规格不需要全选就可以添加对应数据[{'id' : spec_id, 'name' : spec_name, 'item':[{'id' : spec_item_id, 'name' : spec_item_name}, {'id' : spec_item_id, 'name' : spec_item_name}]},{'id' : spec_id, 'name' : spec_name, 'item':[{'id' : spec_item_id, 'name' : spec_item_name}, {'id' : spec_item_id, 'name' : spec_item_name}]}] ")
    private String productSpec;

    @ApiModelProperty("商品SKU(JSON):{'uniq_id':[item_id, price, url]}")
    private String productUniqid;

    @ApiModelProperty("商品描述")
    private String productDetail;

    @ApiModelProperty("Meta Tag 标题")
    private String productMetaTitle;

    @ApiModelProperty("Meta Tag 描述")
    private String productMetaDescription;

    @ApiModelProperty("Meta Tag 关键字")
    private String productMetaKeyword;


}
