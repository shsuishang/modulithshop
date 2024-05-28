package com.suisung.shopsuite.shop.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.marketing.model.vo.ActivityRuleVo;
import com.suisung.shopsuite.shop.model.entity.UserVoucher;
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
public class UserVoucherRes extends UserVoucher {

    @ApiModelProperty("代金券编号")
    private Integer id;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("活动规则(JSON):不检索{rule_id:{}, rule_id:{}},统一解析规则{\"requirement\":{\"buy\":{\"item\":[1,2,3],\"subtotal\":\"通过计算修正满足的条件\"}},\"rule\":[{\"total\":100,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}},{\"total\":200,\"max_num\":1,\"item\":{\"1\":1,\"1200\":3}}]}")
    private ActivityRuleVo activityRuleJson;

    @ApiModelProperty("单品优惠商品编号(DOT)")
    private List<String> itemIds;

    @ApiModelProperty("活动状态(ENUM):0-未开启;1-正常;2-已结束;3-管理员关闭;4-商家关闭")
    private Integer activityState;

    @ApiModelProperty("优惠券是否生效(BOOL): false-未生效;true-生效")
    private Boolean voucherEffect = true;

}
