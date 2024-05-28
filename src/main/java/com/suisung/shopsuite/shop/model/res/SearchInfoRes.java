package com.suisung.shopsuite.shop.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.shop.model.vo.SearchWordVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "搜索记录前端对象")
public class SearchInfoRes implements Serializable {

    @ApiModelProperty("搜索历史")
    private List<String> searchHistoryWords;

    @ApiModelProperty("热门搜索词")
    private List<String> searchHotWords;

    @ApiModelProperty("建议搜索词")
    private SearchWordVo suggestSearchWords;

}