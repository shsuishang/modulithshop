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
package com.suisung.shopsuite.trade.model.entity;

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

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_user_cart")
@ApiModel(value = "UserCart对象", description = "购物车表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserCart implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Long cartId;

    @ApiModelProperty("买家编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("门店编号")
    @TableField("chain_id")
    private Integer chainId;

    @ApiModelProperty("产品编号")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("商品编号")
    @TableField("item_id")
    private Long itemId;

    @ApiModelProperty("购买商品数量")
    @TableField("cart_quantity")
    private Integer cartQuantity;

    @ApiModelProperty("购买类型(ENUM):1-购买; 2-积分兑换; 3-赠品; 4-活动促销")
    @TableField("cart_type")
    private Integer cartType;

    @ApiModelProperty("活动Id-加价购等等加入购物的需要提示")
    @TableField("activity_id")
    private Integer activityId;

    @ApiModelProperty("加入购物车所属活动Item编号")
    @TableField("activity_item_id")
    private Long activityItemId;

    @ApiModelProperty("选中状态(BOOL):0-未选;1-已选")
    @TableField("cart_select")
    private Boolean cartSelect;

    @ApiModelProperty("有效时间戳")
    @TableField("cart_ttl")
    private Long cartTtl;

    @ApiModelProperty("添加时间戳")
    @TableField("cart_time")
    private Long cartTime;

    @ApiModelProperty("文件")
    @TableField("cart_file")
    private String cartFile;
}
