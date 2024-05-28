package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.pt.model.output.ItemOutput;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ItemListRes extends BaseListRes<ItemOutput> {
    @ApiModelProperty("分类辅助属性")
    List<ProductAssistOutput> assists = new ArrayList<>();

    @ApiModelProperty("活动信息")
    private ActivityBase activityBase;
}
