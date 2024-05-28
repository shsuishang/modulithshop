package com.suisung.shopsuite.sys.model.req;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "移动页面保存")
public class MobileEditReq implements Serializable {
    @ApiModelProperty("JSON字符串")
    private String appPageList;

    @ApiModelProperty("JSON字符串")
    private String appMemberCenter;
}
