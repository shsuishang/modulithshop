// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.pt.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.account.repository.UserLevelRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.marketing.model.vo.ActivityInfoVo;
import com.suisung.shopsuite.marketing.repository.ActivityItemRepository;
import com.suisung.shopsuite.marketing.service.ActivityItemService;
import com.suisung.shopsuite.pt.dao.ProductBaseDao;
import com.suisung.shopsuite.pt.model.entity.ProductBase;
import com.suisung.shopsuite.pt.model.entity.ProductImage;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.pt.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * <p>
 * 商品基础表-SPU表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-03-20
 */
@Repository
public class ProductBaseRepositoryImpl extends BaseRepositoryImpl<ProductBaseDao, ProductBase> implements ProductBaseRepository {
    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private ActivityItemRepository activityItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    @Autowired
    private ActivityItemService activityItemService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserLevelRepository userLevelRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductItemVo> getItems(List<Long> itemIds, Integer userId) {
        // 获取商品SKU基本信息
        List<ProductItemVo> output = new ArrayList<>();

        if (CollUtil.isNotEmpty(itemIds)) {
            //todo 参与活动信息，格式化活动数据
            List<ActivityInfoVo> activityInfoList = activityItemService.getActivityInfo(itemIds);

            List<ProductItem> itemList = productItemRepository.gets(itemIds);

            for (ProductItem productItem : itemList) {
                ProductItemVo itVo = new ProductItemVo();
                BeanUtils.copyProperties(productItem, itVo);
                itVo.setItemSalePrice(itVo.getItemUnitPrice());


                // 1、如果存在活动，则优先采用活动价、如果没有活动，判断等级折扣价
                //用户等级折扣
                Integer userLevelRate = 100;

                if (CheckUtil.isNotEmpty(userId)) {
                    UserInfo userInfo = userInfoRepository.get(userId);

                    Map<Integer, Integer> userLevelRateMap = userLevelRepository.getUserLevelRateMap();
                    userLevelRate = userLevelRateMap.get(userInfo.getUserLevelId());
                }

                //是否有活动信息
                ActivityInfoVo activityInfoVo = activityInfoList.stream().filter(s -> ObjectUtil.equal(s.getItemId(), itVo.getItemId())).findFirst().orElse(null);

                if (ObjectUtil.isNotEmpty(activityInfoVo) && checkSingleActivity(activityInfoVo.getActivityTypeId())) {
                    itVo.setActivityInfo(activityInfoVo);

                    //判断是否执行活动信息
                    if (true) {
                        itVo.setActivityId(activityInfoVo.getActivityId());
                    }

                } else {
                    //用户等级判断
                    if (!userLevelRate.equals(100)) {
                        itVo.setItemSalePrice(itVo.getItemUnitPrice().multiply(BigDecimal.valueOf(userLevelRate)).divide(BigDecimal.valueOf(100)));
                        itVo.setItemSavePrice(itVo.getItemUnitPrice().subtract(itVo.getItemSalePrice()));

                        itVo.setItemDiscountAmount(itVo.getItemSavePrice().multiply(Convert.toBigDecimal(itVo.getCartQuantity())));
                    }
                }

                output.add(itVo);
            }
        }

        List<Long> productIds = output.stream().map(s -> s.getProductId()).distinct().collect(Collectors.toList());

        if (CollUtil.isNotEmpty(productIds)) {
            List<ProductBase> productBases = gets(productIds);
            List<ProductIndex> productIndexs = productIndexRepository.gets(productIds);

            //SKU图片
            List<ProductImage> productImages = productImageRepository.find(new QueryWrapper<ProductImage>().in("product_id", productIds));

            // product_id, color_id 决定图片
            for (ProductItemVo itemVo : output) {
                Long productId = itemVo.getProductId();
                Optional<ProductBase> productOpl = productBases.stream().filter(s -> s.getProductId().equals(productId)).findFirst();
                ProductBase productBase = productOpl.orElse(new ProductBase());
                String productName = Optional.ofNullable(productBase.getProductName()).orElse("");
                String productTips = Optional.ofNullable(productBase.getProductTips()).orElse("");

                String itemName = itemVo.getItemName();
                String itemSpecName = StrUtil.replaceChars(itemName, ",", " ");

                String productItemName = productName + " " + itemSpecName;
                itemVo.setProductItemName(productItemName);
                itemVo.setProductName(productName);
                itemVo.setProductTips(productTips);

                // product_commission_rate, product_fx_enable, product_dist_enable
                BigDecimal productCommissionRate = Optional.ofNullable(productBase.getProductCommissionRate()).orElse(BigDecimal.ZERO);
                itemVo.setProductCommissionRate(productCommissionRate);

                Optional<ProductIndex> productIndexOpl = productIndexs.stream().filter(s -> s.getProductId().equals(productId)).findFirst();
                ProductIndex productIndex = productIndexOpl.orElse(new ProductIndex());

                Integer transportTypeId = productBase.getTransportTypeId();
                String productTags = productIndex.getProductTags();
                itemVo.setTransportTypeId(transportTypeId);
                itemVo.setProductTags(productTags);
                Boolean productDistEnable = productIndex.getProductDistEnable();
                itemVo.setProductDistEnable(productDistEnable);

//                if (productDistEnable) {
//                    // 修正单品显示上额度
//                    if (productCommissionRate.compareTo(BigDecimal.ZERO) > 0) {
//                        ProductCategory productCategory = productCategoryRepository.get(itemVo.getCategoryId());
//
//                        if (productCategory != null) {
//                            BigDecimal categoryCommissionRate = productCategory.getCategoryCommissionRate();
//
//                            if (categoryCommissionRate.compareTo(BigDecimal.ZERO) > 0) {
//                                BigDecimal itemRebate = itemVo.getItemRebate();
//                                BigDecimal resultItemRebate = NumberUtil.div(NumberUtil.mul(itemRebate, productCommissionRate), categoryCommissionRate);
//                                itemVo.setItemRebate(resultItemRebate);
//                            } else {
//                            }
//                        }
//
//                    }
//                } else {
//                    itemVo.setItemRebate(BigDecimal.ZERO);
//                }

                // 商品状态
                Integer productStateId = productIndex.getProductStateId();
                Integer productInventoryLock = productIndex.getProductInventoryLock();
                Integer kindId = productIndex.getKindId();

                //SKU是否启用
                if (itemVo.getItemEnable() == StateCode.PRODUCT_STATE_NORMAL) {
                    //可用库存不足，设为下架状态
                    if (itemVo.getItemQuantity() - itemVo.getItemQuantityFrozen() > 0) {

                    } else {
                        productStateId = StateCode.PRODUCT_STATE_OFF_THE_SHELF;
                    }
                } else {
                    productStateId = StateCode.PRODUCT_STATE_OFF_THE_SHELF;
                }

                itemVo.setProductStateId(productStateId);
                itemVo.setProductInventoryLock(productInventoryLock);
                itemVo.setKindId(kindId);
                //itemVo.setIsVirtual(kindId.equals(StateCode.PRODUCT_KIND_ENTITY) ? 0 : 1);


                itemVo.setProductImage(productBase.getProductImage());

                // 主图，不同颜色规格一致。
                ProductImage productImage = productImages.stream().filter(s -> {
                    return s.getProductId().equals(itemVo.getProductId()) && s.getColorId().equals(itemVo.getColorId());
                }).findFirst().orElse(new ProductImage());
                if (ObjectUtil.isNotEmpty(productImage.getItemImageDefault())) {
                    itemVo.setProductImage(productImage.getItemImageDefault());
                }

                // 限购信息
                Integer productBuyLimit = productBase.getProductBuyLimit();
                //itemVo.setProductBuyLimit(productBuyLimit);
            }
        }

        return output;
    }

    /**
     * 单品直接购买活动，判断是否通过活动
     *
     * @param activityTypeId
     * @return
     */
    private boolean checkSingleActivity(Integer activityTypeId) {

        return true;
    }
}
