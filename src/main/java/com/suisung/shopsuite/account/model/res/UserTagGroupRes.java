package com.suisung.shopsuite.account.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.account.model.entity.UserTagBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "标签分组集合")
public class UserTagGroupRes {

    @ApiModelProperty("分组名称")
    private String tagTitle;

    @ApiModelProperty(value = "子集标签")
    private List<UserTagBase> children;

}
