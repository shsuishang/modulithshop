package com.suisung.shopsuite.sys.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.sys.model.entity.FeedbackCategory;
import com.suisung.shopsuite.sys.model.entity.FeedbackType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@ApiModel(value = "平台反馈")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FeedbackTypeRes extends FeedbackType {

    @ApiModelProperty("反馈分类集合")
    private List<FeedbackCategory> rows;

}
