package com.suisung.shopsuite.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "短信发送DTO", description = "短信发送DTO")
public class SmsDto {

    @ApiModelProperty(value = "短信类型")
    private Integer smsType;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "短信内容")
    private String content;

    @ApiModelProperty(value = "模板编号-扩展使用")
    private String tplId;

    @ApiModelProperty(value = "签名通道号-扩展使用")
    private String messageTplSender;

    @ApiModelProperty(value = "动态参数-扩展使用")
    private Map tplParas;

    @ApiModelProperty(value = "账号/平台id")
    private String serviceUserId;

    @ApiModelProperty(value = "短信KEY/平台KEY")
    private String serviceAppKey;

    @ApiModelProperty(value = "阿里云模板code")
    private String templateCode;

    @ApiModelProperty(value = "阿里云短信参数")
    private Map<String, Object> paramMap;

    @ApiModelProperty(value = "腾讯云模板id")
    private Integer tengxunTemplateId;

}