package com.suisung.shopsuite.account.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.account.model.entity.UserMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "站内信")
public class UserMessageRes extends UserMessage {

    @ApiModelProperty("站内信数量")
    private Integer num;

    @ApiModelProperty("聊天网址")
    private UserMessage msgRow;

    @ApiModelProperty("消息编号")
    private Integer messageOtherId;

    @ApiModelProperty("站内信userInfo")
    private MessageUserInfoRes userinfo;

    @ApiModelProperty("站内信content")
    private MessageContentRes content;

    @ApiModelProperty("已读信息数")
    private Integer redNumber = 0;

    @ApiModelProperty("未读信息数")
    private Integer unreadNumber = 0;

    @ApiModelProperty("type")
    private String type;

}
