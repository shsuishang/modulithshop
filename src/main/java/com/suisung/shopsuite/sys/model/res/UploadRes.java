package com.suisung.shopsuite.sys.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@ApiModel(value = "上传结果")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UploadRes {
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long FileSize;

    @ApiModelProperty(value = "文件网址")
    private String FileUrl;

    @ApiModelProperty(value = "文件网址 等于 FileUrl")
    private String Url;

    @ApiModelProperty(value = "文件类型:.jpg, 仅仅兼容PC装修")
    private String type = ".jpg";

    @ApiModelProperty(value = "文件类型")
    private String FileType;

    @ApiModelProperty(value = "文件路径")
    private String FilePath;

    @ApiModelProperty(value = "素材类型")
    private String MimeType;

    @ApiModelProperty(value = "用户编号")
    private Integer UserId;

    @ApiModelProperty(value = "素材时长:（音频/视频）")
    private String materialDuration;
}
