package com.suisung.shopsuite.trade.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.suisung.shopsuite.trade.model.entity.OrderComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCommentVo extends OrderComment implements Serializable {

    @ApiModelProperty("评论上传的图片：|分割多张图片")
    @TableField(value = "comment_images")
    private Object commentImages;

}
