package com.suisung.shopsuite.sys.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.sys.model.entity.PageBase;
import com.suisung.shopsuite.sys.model.vo.ImConfigVo;
import com.suisung.shopsuite.sys.model.vo.PagePopUpVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "页面表")
public class PageBaseRes extends PageBase {
    @ApiModelProperty("IM配置")
    private ImConfigVo im;

    @ApiModelProperty("IM配置")
    private boolean pageLoaded;

    @ApiModelProperty("弹窗集合")
    private List<PagePopUpVo> popUps;
}
