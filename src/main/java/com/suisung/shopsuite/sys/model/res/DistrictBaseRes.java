package com.suisung.shopsuite.sys.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.sys.model.entity.DistrictBase;
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
@ApiModel(value = "地区树形")
public class DistrictBaseRes extends DistrictBase {
    @ApiModelProperty(value = "地区树形")
    private List<DistrictBaseRes> children;
}
