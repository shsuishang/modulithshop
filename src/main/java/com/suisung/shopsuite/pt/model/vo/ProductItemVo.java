package com.suisung.shopsuite.pt.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "商品下单使用", description = "商品下单使用")
public class ProductItemVo extends ProductItem {

    @ApiModelProperty(value = "SPU商品名称")
    private String productName;

    @ApiModelProperty("商品卖点:商品广告词")
    private String productTips;

    @ApiModelProperty("图片信息")
    private String productImage;

    @ApiModelProperty("运费模板")
    private Integer transportTypeId;

    @ApiModelProperty("商品标签")
    private String productTags;

    @ApiModelProperty("商品状态")
    private Integer productStateId;

    @ApiModelProperty("冻结库存")
    private Integer productInventoryLock;

    @ApiModelProperty("类型编号")
    private Integer kindId;

    @ApiModelProperty("购物车编号")
    private Long cartId;

    @ApiModelProperty("可用库存")
    private Integer availableQuantity;

    @ApiModelProperty("购物数量")
    private Integer cartQuantity = 0;

    @ApiModelProperty("是否选中")
    private Boolean cartSelect = true;

    @ApiModelProperty("超出配送区域")
    private Boolean isOos = false;

    @ApiModelProperty("销售中")
    private Boolean isOnSale = true;

    @ApiModelProperty("三级分销允许分销(BOOL):1-启用分销;0-禁用分销")
    private Boolean productDistEnable;

    @ApiModelProperty(value = "平台佣金比率")
    private BigDecimal productCommissionRate;

    @ApiModelProperty("折扣率")
    private BigDecimal itemPolicyDiscountrate;

    @ApiModelProperty("优惠总额:只考虑单品的，订单及店铺总活动优惠不影响")
    private BigDecimal itemDiscountAmount;

    @ApiModelProperty("金额小计:salePrice * cartQuantity - 折扣")
    private BigDecimal itemSubtotal;

    @ApiModelProperty("应付积分小计")
    private BigDecimal itemPointsSubtotal;

    @ApiModelProperty("分配优惠券额度")
    private BigDecimal itemVoucher = BigDecimal.ZERO;

    @ApiModelProperty("分配满减额度")
    private BigDecimal itemReduction = BigDecimal.ZERO;

    @ApiModelProperty("底价")
    private BigDecimal itemRatePrice;

    @ApiModelProperty("礼品-废弃")
    private List<Object> pulseGiftCart = new ArrayList<>();
    private List<Object> pulseReduction = new ArrayList<>();
    private List<Object> pulseMultple = new ArrayList<>();
    private List<Object> pulseBargainsCart = new ArrayList<>();
    private List<Object> pulseBargains = new ArrayList<>();
}
