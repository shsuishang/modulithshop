package com.suisung.shopsuite.marketing.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.vo.ActivityRuleVo;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "优惠券列表")
public class ActivityBaseRes extends ActivityBase {

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("活动规则(JSON):不检索{rule_id:{}, rule_id:{}},统一解析规则{\"requirement\":{\"buy\":{\"item\":[1,2,3],\"subtotal\":\"通过计算修正满足的条件\"}},\"rule\":[{\"total\":100,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}},{\"total\":200,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}}]}")
    private ActivityRuleVo activityRuleJson;

    @ApiModelProperty("是否领取")
    private Boolean ifGain;

    @ApiModelProperty("优惠套装数量")
    private Integer itemNumber;

    @ApiModelProperty("商品信息集合")
    private List<ProductItemVo> item;

    @ApiModelProperty("会员等级")
    private String useLevel;

    @ApiModelProperty("折扣商品")
    private String productItemName;

    @ApiModelProperty("使用等级名称(DOT)")
    private String activityUseLevelName;

}
