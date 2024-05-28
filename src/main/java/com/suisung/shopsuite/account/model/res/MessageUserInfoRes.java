package com.suisung.shopsuite.account.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "站内信userInfo")
public class MessageUserInfoRes {

    @ApiModelProperty("用户昵称")
    private String username;

    @ApiModelProperty("uid")
    private Integer uid;

    @ApiModelProperty("uid")
    private String face;

}