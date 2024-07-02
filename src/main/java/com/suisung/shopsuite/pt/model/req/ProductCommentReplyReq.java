package com.suisung.shopsuite.pt.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "商品评价回复表参数")
public class ProductCommentReplyReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论编号")
    @NotNull(message = "评论编号不能为空")
    private Long commentId;

    @ApiModelProperty("评论回复内容")
    @NotNull(message = "商品编号不能为空")
    private String commentReplyContent;

}
