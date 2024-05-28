package com.suisung.shopsuite.trade.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "添加评论请求详细数据")
public class OrderCommentItemReq {

    private Integer orderItemReturnNum;

    private Integer itemPurchasePrice;

    private Integer buyerId;

    private Integer orderItemReturnAgreeAmount;

    private Integer orderItemUnitPrice;

    private Integer orderItemInventoryLock;

    private Integer itemSrcId;

    private Boolean commentIsAnonymous = false;

    private Integer productId;

    private Integer activityId;

    private String orderItemImage;

    private Integer itemPurchaseRate;

    private String orderItemConfirmFile;

    private Integer orderItemSalerId;

    private String designFileImages;

    private Integer orderItemReturnAgreeNum;

    private Integer orderItemAmount;

    private String srcOrderId;

    private Integer itemPlatformPrice;

    private Integer itemUnitPrice;

    private String activityCode;

    private Integer itemChannelType;

    private Integer version;

    private Integer orderItemCommissionFeeRefund;

    private Integer orderItemId;

    private Integer orderItemEvaluationStatus;

    private Integer orderItemConfirmStatus;

    private Integer itemCostPrice;

    private String orderId;

    private String specInfo;

    private Integer itemSalesRate;

    private Integer orderItemCommissionFee;

    private Integer orderItemQuantity;

    private Integer orderItemReturnSubtotal;

    private String orderItemNote;

    private Integer itemVoucher;

    private Integer itemUnitPoIntegers;

    private Integer categoryId;

    private Integer specId;

    private Integer orderItemDiscountAmount;

    private Integer orderItemAdjustFee;

    private Integer orderGiveId;

    private Integer itemUnitSp;

    private Integer storeId;

    private Integer orderItemPoIntegersFee;

    private Integer itemId;

    private Integer orderItemRedemptionVoucher;

    private Integer policyDiscountrate;

    private String orderItemFile;

    private String itemName;

    private Integer orderItemSupplierSync;

    private Integer activityTypeId;

    private Integer orderItemCommissionRate;

    private Integer orderItemPaymentAmount;

    private String content;

    private String commentContent;

    private List<String> commentImage;

    private Integer commentScores;

    private Integer scores;

    private String comment;


}
