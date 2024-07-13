package com.suisung.shopsuite.account.model.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.account.model.req.LoginReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "")
public class RegInput extends LoginReq implements Serializable {

    @ApiModelProperty("用户昵称")
    private String userNickname;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "国家编码")
    private String userIntl;

    @ApiModelProperty("手机号")
    private Long userMobile;

    @ApiModelProperty("邮箱")
    private String userEmail;

    @ApiModelProperty("活动编号")
    private Integer activityId;

    @ApiModelProperty("来源用户编号")
    private Integer userParentId;


    @ApiModelProperty("渠道码")
    private String sourceUccCode;

    @ApiModelProperty("注册方式=>BindConnectCode")
    private Integer bindType = 3;

    @ApiModelProperty("角色编号:0-用户;2-商家;3-门店;9-平台;")
    private Integer roleId = 0;

    @ApiModelProperty("店铺编号")
    private Integer storeId = 0;

    @ApiModelProperty("门店编号")
    private Integer chainId = 0;
}
