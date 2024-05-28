package com.suisung.shopsuite.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "邮件配置DTO", description = "邮件配置DTO")
public class EmailDto {
    @ApiModelProperty("发信邮箱")
    private String emailAddr;

    @ApiModelProperty("发信人")
    private String emailFromname;

    @ApiModelProperty("SMTP 服务器")
    private String emailHost;

    @ApiModelProperty("SMTP 端口号")
    private Integer emailPort;

    @ApiModelProperty("单行SMTP 身份验证用户名")
    private String emailId;

    @ApiModelProperty("SMTP 身份验证密码")
    private String emailPass;

    @ApiModelProperty("是否加密")
    private String emailSecure;

    @ApiModelProperty("调试级别")
    private String emailDebug;

    @ApiModelProperty("测试接收的邮件地址")
    private String emailToAddress;

    @ApiModelProperty("邮件主题")
    private String subject;

    @ApiModelProperty("邮件内容")
    private String content;
}