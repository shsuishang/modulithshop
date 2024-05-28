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
package com.suisung.shopsuite.marketing.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 活动表-通过插件实现
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("marketing_activity_base")
@ApiModel(value = "ActivityBase对象", description = "活动表-通过插件实现	 ")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ActivityBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("活动编号")
    @TableId(value = "activity_id", type = IdType.AUTO)
    private Integer activityId;

    @ApiModelProperty("店铺编号")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("活动名称")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty("活动标题")
    @TableField("activity_title")
    private String activityTitle;

    @ApiModelProperty("活动说明")
    @TableField("activity_remark")
    private String activityRemark;

    @ApiModelProperty("活动类型")
    @TableField("activity_type_id")
    private Integer activityTypeId;

    @ApiModelProperty("活动开始时间")
    @TableField("activity_starttime")
    private Long activityStarttime;

    @ApiModelProperty("活动结束时间")
    @TableField("activity_endtime")
    private Long activityEndtime;

    @ApiModelProperty("活动状态(ENUM):0-未开启;1-正常;2-已结束;3-关闭;")
    @TableField("activity_state")
    private Integer activityState;

    @ApiModelProperty("活动规则(JSON):不检索{rule_id:{}, rule_id:{}},统一解析规则{\"requirement\":{\"buy\":{\"item\":[1,2,3],\"subtotal\":\"通过计算修正满足的条件\"}},\"rule\":[{\"total\":100,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}},{\"total\":200,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}}]}")
    @TableField("activity_rule")
    private String activityRule;

    @ApiModelProperty("已经参与数量")
    @TableField("activity_effective_quantity")
    private Integer activityEffectiveQuantity;

    @ApiModelProperty("参与类型(ENUM):1-免费参与;2-积分参与;3-购买参与;4-分享参与")
    @TableField("activity_type")
    private Integer activityType;

    @ApiModelProperty("活动排序")
    @TableField("activity_sort")
    private Integer activitySort;

    @ApiModelProperty("活动是否完成(ENUM):0-未完成;1-已完成;2-已解散(目前用于团购)")
    @TableField("activity_is_finish")
    private Integer activityIsFinish;

    @ApiModelProperty("分站编号")
    @TableField("subsite_id")
    private String subsiteId;

    @ApiModelProperty("使用等级(DOT)")
    @TableField("activity_use_level")
    private String activityUseLevel;

    @ApiModelProperty("活动SKU(DOT):activity_rule中数据冗余")
    @TableField("activity_item_ids")
    private String activityItemIds;


    @ApiModelProperty("活动SKU商品信息")
    @TableField(exist = false)
    private List<ProductItemVo> activityItems;

    @ApiModelProperty("添加时间")
    @TableField("activity_addtime")
    private Date activityAddtime;

    @ApiModelProperty("版本")
    @Version
    private Integer version;
}
