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
package com.suisung.shopsuite.marketing.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 参与活动商品表-用户筛选计算
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "参与活动商品表-用户筛选计算参数")
public class ActivityItemAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("活动类型编号")
    private Integer activityTypeId;

    @ApiModelProperty("活动编号")
    private Integer activityId;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("商品编号")
    private Long itemId;

    @ApiModelProperty("商品分类")
    private Integer categoryId;

    @ApiModelProperty("开始时间")
    private Long activityItemStarttime;

    @ApiModelProperty("结束时间")
    private Long activityItemEndtime;

    @ApiModelProperty("活动价格")
    private BigDecimal activityItemPrice;

    @ApiModelProperty("购买下限")
    private Integer activityItemMinQuantity;

    @ApiModelProperty("活动状态(ENUM):0-未开启;1-正常;2-已结束;3-管理员关闭")
    private Integer activityItemState;

    @ApiModelProperty("推荐标志(BOOL):0-未推荐;1-已推荐")
    private Boolean activityItemRecommend;


}
