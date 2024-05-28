package com.suisung.shopsuite.trade.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价Vo
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
public class OrderAddInfoVo {

    @ApiModelProperty("消息")
    private Map<Integer, String> message;

    @ApiModelProperty("优惠券")
    private List<Integer> userVoucherIds;

    @ApiModelProperty("发票")
    private Integer userInvoiceId;

}
