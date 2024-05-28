package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 评价Vo
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "评价Vo")
public class EvaluationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("评价状态")
    private List<Integer> orderItemEvaluationStatus;

    @ApiModelProperty("订单编号")
    private String orderId;

}
