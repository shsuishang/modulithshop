package com.suisung.shopsuite.cms.model.res;


import com.suisung.shopsuite.cms.model.entity.ArticleBase;
import com.suisung.shopsuite.cms.model.entity.ArticleTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "文章内容前端对象")
public class ArticleBaseRes extends ArticleBase {

    @ApiModelProperty(value = "文章标签集合")
    private List<ArticleTag> articleTagList;

    @ApiModelProperty("用户昵称")
    private String userNickname;
}
