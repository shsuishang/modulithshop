package com.suisung.shopsuite.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "上传文件DTO", description = "上传文件DTO")
public class UploadDto {

    @ApiModelProperty(value = "上传类型")
    private Integer uploadType;

    @ApiModelProperty(value = "上传文件")
    private File file;

    @ApiModelProperty(value = "上传文件")
    private MultipartFile multipartfile;

    @ApiModelProperty(value = "二进制文件")
    private InputStream inputStream;

    private String materialType;

}
