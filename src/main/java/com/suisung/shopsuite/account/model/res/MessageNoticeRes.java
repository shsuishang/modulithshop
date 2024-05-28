package com.suisung.shopsuite.account.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "后台通知对象")
public class MessageNoticeRes {

    @ApiModelProperty("消息数量")
    private Integer total;

    @ApiModelProperty("站内信数量")
    private List<UserMessageRes> items;

}
