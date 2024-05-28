package com.suisung.shopsuite.account.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@ApiModel(value = "用户等级规则")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExpRuleRes {

    @ApiModelProperty("注册增加")
    private String exp_reg;

    @ApiModelProperty("评论增加")
    private String exp_evaluate_good;

    @ApiModelProperty("登录增加")
    private String exp_login;

    @ApiModelProperty("消费额与增加比例")
    private String exp_consume_rate;

    @ApiModelProperty("每单最多增加")
    private String exp_consume_max;

}
