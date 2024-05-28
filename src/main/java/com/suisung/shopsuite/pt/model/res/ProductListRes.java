package com.suisung.shopsuite.pt.model.res;

import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import com.suisung.shopsuite.pt.model.output.ProductOutput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductListRes extends BaseListRes<ProductOutput> {
    @ApiModelProperty("分类辅助属性")
    List<ProductAssistOutput> assists = new ArrayList<>();
}
