package com.suisung.shopsuite.cms.model.res;


import com.suisung.shopsuite.cms.model.entity.ArticleCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "文章前端对象")
public class ArticleCategoryRes extends ArticleCategory {
    @ApiModelProperty(value = "文章子集")
    private List<ArticleCategoryRes> children;
}
