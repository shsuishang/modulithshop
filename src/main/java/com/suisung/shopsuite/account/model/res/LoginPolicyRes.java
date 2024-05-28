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
@ApiModel(value = "隐私政策")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginPolicyRes {

    @ApiModelProperty("文档内容")
    private String document;

    @ApiModelProperty("提现协议状态")
    private Integer state;
}
