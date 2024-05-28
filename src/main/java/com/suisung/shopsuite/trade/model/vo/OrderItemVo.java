package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.trade.model.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "店铺及商品信息", description = "店铺及商品信息")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderItemVo extends OrderItem implements Serializable {

    @ApiModelProperty("评价编号")
    private Long commentId;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("产品编号")
    private Long productId;

    @ApiModelProperty("商品编号")
    private Long itemId;

    @ApiModelProperty("商品规格")
    private String itemName;

    @ApiModelProperty("店铺编号")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("买家编号")
    private Integer userId;

    @ApiModelProperty("买家姓名:user_nickname")
    private String userName;

    @ApiModelProperty("获得积分:冗余，独立表记录")
    private BigDecimal commentPoints;

    @ApiModelProperty("评价星级:1-5积分")
    private Integer commentScores;

    @ApiModelProperty("评价内容")
    private String commentContent;

    @ApiModelProperty("评论上传的图片(DOT)")
    private List<String> commentImage;

    @ApiModelProperty("有帮助")
    private Integer commentHelpful;

    @ApiModelProperty("无帮助")
    private Integer commentNohelpful;

    @ApiModelProperty("评价时间")
    private Date commentTime;

    @ApiModelProperty("匿名评价")
    private Boolean commentIsAnonymous;

    @ApiModelProperty("评价信息的状态(BOOL): 1-正常显示; 0-禁止显示")
    private Boolean commentEnable;

    @ApiModelProperty("门店编号")
    private Integer chainId;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;
}
