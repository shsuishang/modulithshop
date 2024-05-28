package com.suisung.shopsuite.trade.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.trade.model.vo.OrderItemVo;
import com.suisung.shopsuite.trade.model.vo.StoreInfoVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "评论显示接口响应")
public class OrderCommentRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<OrderItemVo> items;

    private Integer no;

    private Object orderEvaluation;

    private StoreInfoVo storeInfo;

    private Integer yes;

}
