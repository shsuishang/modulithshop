package com.suisung.shopsuite.account.model.input;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "站内信添加")
public class UserMessageAddInput {

    @ApiModelProperty("消息类型")
    private String messageCat;

    @ApiModelProperty("相关昵称:发送者或者接收者")
    private String userNickname;

    @ApiModelProperty("相关用户:发送者或者接收者")
    private Integer userOtherId;

    @ApiModelProperty("json对象")
    private String to;

    @ApiModelProperty("消息内容")
    private String messageContent;

    @ApiModelProperty("消息标题")
    private String messageTitle;

    @ApiModelProperty("json对象")
    private String mine;

    @ApiModelProperty("消息长度")
    private Integer messageLength;

    @ApiModelProperty("图片宽度")
    private Integer messageW;

    @ApiModelProperty("图片高度")
    private Integer messageH;
}
