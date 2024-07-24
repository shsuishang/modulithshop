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
package com.suisung.shopsuite.pt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserDeliveryAddressRepository;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.entity.ActivityItem;
import com.suisung.shopsuite.marketing.model.vo.ActivityInfoVo;
import com.suisung.shopsuite.marketing.model.vo.ItemNumVo;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.marketing.service.ActivityItemService;
import com.suisung.shopsuite.pt.dao.ProductIndexDao;
import com.suisung.shopsuite.pt.model.entity.*;
import com.suisung.shopsuite.pt.model.input.ProductDetailInput;
import com.suisung.shopsuite.pt.model.input.ProductIndexInput;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.model.output.ItemOutput;
import com.suisung.shopsuite.pt.model.output.ProductOutput;
import com.suisung.shopsuite.pt.model.req.ProductIndexListReq;
import com.suisung.shopsuite.pt.model.res.ActivityInfoRes;
import com.suisung.shopsuite.pt.model.res.ItemListRes;
import com.suisung.shopsuite.pt.model.res.ProductDetailRes;
import com.suisung.shopsuite.pt.model.res.ProductListRes;
import com.suisung.shopsuite.pt.repository.*;
import com.suisung.shopsuite.pt.service.ProductCategoryService;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import com.suisung.shopsuite.shop.model.entity.StoreTransportItem;
import com.suisung.shopsuite.shop.model.vo.StoreTransportItemVo;
import com.suisung.shopsuite.shop.repository.StoreTransportTypeRepository;
import com.suisung.shopsuite.sys.model.entity.ContractType;
import com.suisung.shopsuite.sys.model.entity.DistrictBase;
import com.suisung.shopsuite.sys.repository.ContractTypeRepository;
import com.suisung.shopsuite.sys.repository.DistrictBaseRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 产品索引表-不读取数据只读主键 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Service
public class ProductIndexServiceImpl extends BaseServiceImpl<ProductIndexRepository, ProductIndex, ProductIndexListReq> implements ProductIndexService {

    @Autowired
    private ProductBaseRepository productBaseRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductAssistRepository productAssistRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ActivityItemService activityItemService;

    @Autowired
    private StoreTransportTypeRepository storeTransportTypeRepository;

    @Autowired
    private DistrictBaseRepository districtBaseRepository;

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    private ProductCommentRepository productCommentRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ActivityBaseRepository activityBaseRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private UserDeliveryAddressRepository deliveryAddressRepository;

    @Override
    public ProductListRes listItem(ProductIndexInput in) {
        ProductListRes output = new ProductListRes();
        QueryWrapper<ProductIndex> wrapper = new BaseQueryWrapper<ProductIndex, ProductIndexInput>(in).getWrapper();

        //指定优惠券跳转商品列表
        if (StrUtil.isNotEmpty(in.getItemIds())) {
            List<ProductItem> productItems = productItemRepository.gets(Convert.toList(Long.class, in.getItemIds()));

            if (CollectionUtil.isNotEmpty(productItems)) {
                List<Long> productIds = productItems.stream().map(ProductItem::getProductId).distinct().collect(Collectors.toList());
                wrapper.in("product_id", productIds);
            }
        }
        wrapper.ne("kind_id", StateCode.PRODUCT_KIND_EDU);
        wrapper.orderByAsc("product_order");
        wrapper.orderByDesc("product_id");

        //处理辅助属性assist
        if (CheckUtil.isNotEmpty(in.getAssist())) {
            Map<String, List<Integer>> productAssistMap = new HashMap<>();

            // 创建ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                productAssistMap = objectMapper.readValue(in.getAssist(), new TypeReference<Map<String, List<Integer>>>() {
                });
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }

            productAssistMap.forEach((assistId, assistItemIdList) -> {
                //AND 关系添加查询条件
                if (assistItemIdList.size() > 0) {
                    CheckUtil.handleFindInSet(assistItemIdList, "product_assist_data", wrapper);
                }
            });
        }

        IPage<ProductIndex> lists = lists(wrapper, in.getPage(), in.getSize());

        // 查询商品索引信息
        output.setRecords(Convert.toInt(lists.getTotal()));
        output.setSize(Convert.toInt(lists.getSize()));
        output.setPage(Convert.toInt(lists.getCurrent()));
        output.setTotal(Convert.toInt(lists.getPages()));

        List<ProductOutput> records = BeanUtil.copyToList(lists.getRecords(), ProductOutput.class);
        output.setItems(records);

        List<Long> productIds = CommonUtil.column(lists.getRecords(), ProductIndex::getProductId);

        if (CollUtil.isNotEmpty(productIds)) {
            //基础表数据
            List<ProductBase> productBases = productBaseRepository.gets(productIds);
            List<ProductInfo> productInfos = productInfoRepository.gets(productIds);

            /*
            //读取图片
            List<ProductImage> productImages = productImageRepository.find(new QueryWrapper<ProductImage>().in("product_id", productIds));

            //处理为Map
            Map<Long, String> imageMap = new HashMap<>();

            for (ProductImage image : productImages) {
                if (!imageMap.containsKey(image.getColorId())) {
                    imageMap.put(image.getColorId(), image.getItemImageDefault());
                }
            }
             */

            //读取SKU
            List<ProductItem> productItems = productItemRepository.find(new QueryWrapper<ProductItem>().in("product_id", productIds).orderByAsc("item_id"));


            // 处理为map
            Map<Long, List<ProductItem>> itemMap = new HashMap<>();
            Map<Long, Long> defaultItemMap = new HashMap<>();

            if (CollUtil.isNotEmpty(productItems)) {
                for (ProductItem item : productItems) {
                    //
                    if (!itemMap.containsKey(item.getProductId())) {
                        itemMap.put(item.getProductId(), new ArrayList<>());
                    }

                    itemMap.get(item.getProductId()).add(item);

                    //默认item
                    if (item.getItemIsDefault()) {
                        if (!defaultItemMap.containsKey(item.getProductId())) {
                            defaultItemMap.put(item.getProductId(), item.getItemId());
                        }
                    }
                }
            }

            for (ProductOutput vo : records) {
                for (ProductBase item : productBases) {
                    if (vo.getProductId().equals(item.getProductId())) {
                        vo.setProductTips(item.getProductTips());
                        vo.setProductImage(item.getProductImage());
                        vo.setProductVideo(item.getProductVideo());
                        vo.setTransportTypeId(item.getTransportTypeId());
                        vo.setProductBuyLimit(item.getProductBuyLimit());
                        vo.setProductCommissionRate(item.getProductCommissionRate());
                    }
                }

                for (ProductInfo info : productInfos) {
                    if (vo.getProductId().equals(info.getProductId())) {
                        vo.setProductSpec(info.getProductSpec());
                        vo.setProductUniqid(info.getProductUniqid());
                    }
                }

                //默认
                //默认商品为下架状态 改为下个上架商品
                Long defaultItemId = defaultItemMap.get(vo.getProductId());
                List<ProductItem> productItemList = itemMap.get(vo.getProductId());

                if (CollectionUtil.isNotEmpty(productItemList)) {
                    Long finalDefaultItemId = defaultItemId;
                    Optional<ProductItem> firstItemOptional = productItemList.stream()
                            .filter(item -> item.getItemId().equals(finalDefaultItemId))
                            .findFirst();

                    if (firstItemOptional.isPresent()) {
                        ProductItem productItem = firstItemOptional.get();

                        if (!productItem.getItemEnable().equals(StateCode.PRODUCT_STATE_NORMAL)) {
                            for (ProductItem item : productItemList) {

                                if (item.getItemEnable().equals(StateCode.PRODUCT_STATE_NORMAL)) {
                                    defaultItemId = item.getItemId();

                                    break;
                                }
                            }
                        }
                    }
                }

                vo.setItemId(defaultItemId);
                vo.setItems(productItemList);
            }

            //活动价格
            List<Long> itemIds = records.stream().map(ProductOutput::getItemId).distinct().collect(Collectors.toList());
            QueryWrapper<ActivityItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.in("item_id", itemIds)
                    .eq("activity_item_state", StateCode.ACTIVITY_STATE_NORMAL);
            List<ActivityItem> activityItemList = activityItemService.find(itemQueryWrapper);

            if (CollectionUtil.isNotEmpty(activityItemList)) {
                Map<Long, BigDecimal> itemPriceMap = activityItemList.stream().collect(Collectors.toMap(ActivityItem::getItemId, ActivityItem::getActivityItemPrice, (k1, k2) -> k1));

                if (ObjectUtil.isNotEmpty(itemPriceMap)) {
                    for (ProductOutput productOutput : records) {
                        if (ObjectUtil.isNotEmpty(itemPriceMap.get(productOutput.getItemId()))) {
                            productOutput.setProductUnitPriceMin(itemPriceMap.get(productOutput.getItemId()));
                        }
                    }
                }
            }
        }

        //判断是否固定分类读取数据
        /*
        if (CheckUtil.isNotEmpty(in.getCategoryId())) {
            List<Integer> ids = Convert.toList(Integer.class, in.getCategoryId());
            if (ids.size() == 1) {
                Integer categoryId = ids.get(0);

                if (CheckUtil.isNotEmpty(categoryId)) {
                    ProductCategory productCategory = productCategoryRepository.get(categoryId);

                    if (ObjectUtil.isNotEmpty(productCategory)) {
                        ProductType productType = productTypeRepository.get(productCategory.getTypeId());

                        if (ObjectUtil.isNotEmpty(productType)) {
                            List<ProductAssistOutput> assists = productAssistRepository.getAssists(productType.getAssistIds());
                            output.setAssists(assists);
                        }
                    }
                }
            }
        }
         */

        return output;
    }

    @Override
    public ProductDetailRes detail(Long itemId) {
        return detail(itemId, null, null);
    }

    /**
     * 商品详情
     *
     * @param input
     * @return
     */
    @Override
    public ProductDetailRes detail(ProductDetailInput input) {
        Long itemId = input.getItemId();
        Integer districtId = input.getDistrictId();
        Integer gbId = input.getGbId();

        ProductDetailRes out = new ProductDetailRes();
        out.setItemId(itemId);

        ProductItem productItem = productItemRepository.get(itemId);
        out.setItemRow(productItem);

        //设置销售价
        productItem.setItemSalePrice(productItem.getItemUnitPrice());

        //读取活动信息
        List<ActivityInfoVo> activityInfoVoList = activityItemService.getActivityInfo(Convert.toList(Long.class, itemId));
        if (CollUtil.isNotEmpty(activityInfoVoList)) {
            ActivityInfoVo activityInfoVo = activityInfoVoList.get(0);
            productItem.setActivityId(activityInfoVo.getActivityId());
            productItem.setActivityInfo(activityInfoVo);

            if (CheckUtil.isNotEmpty(activityInfoVo.getActivityItemPrice())) {
                productItem.setItemSalePrice(activityInfoVo.getActivityItemPrice());
            }
        }

        Long productId = productItem.getProductId();

        ProductIndex productIndex = get(productId);
        ProductBase productBase = productBaseRepository.get(productId);
        ProductInfo productInfo = productInfoRepository.get(productId);

        BeanUtils.copyProperties(productIndex, out);
        BeanUtils.copyProperties(productBase, out);
        BeanUtils.copyProperties(productInfo, out);

        //SKU图片
        ProductImage image = productImageRepository.findOne(new QueryWrapper<ProductImage>().eq("product_id", productItem.getProductId()).eq("color_id", productItem.getColorId()));
        out.setImage(image);
        out.setProductImage(image.getItemImageDefault());

        String itemName = productItem.getItemName();
        String itemSpecName = StrUtil.replaceChars(itemName, ",", " ");

        String productItemName = productBase.getProductName() + " " + itemSpecName;
        out.setProductItemName(productItemName);

        //是否可销售
        if (productItem.getAvailableQuantity() > 0) {
            out.setIfStore(true);

            //可售区域
            if (CheckUtil.isNotEmpty(districtId)) {

                //商品也，默认三级分类
                if (CheckUtil.isNotEmpty(districtId)) {
                    // 读取上级分类信息
                    DistrictBase districtBase = districtBaseRepository.get(districtId);

                    if (districtBase != null) {
                        districtId = districtBase.getDistrictParentId();
                    }
                }

                StoreTransportItemVo storeTransportItemVo = storeTransportTypeRepository.getFreight(productBase.getTransportTypeId(), districtId);

                if (storeTransportItemVo == null) {
                    out.setIfStore(false);
                } else {
                    if (!storeTransportItemVo.getTransportTypeFree() && ObjectUtil.isEmpty(storeTransportItemVo.getItem())) {
                        out.setIfStore(false);
                    }
                    StoreTransportItem transportItem = storeTransportItemVo.getItem();

                    if (transportItem != null) {
                        out.setFreight(transportItem.getTransportItemDefaultPrice());
                    }
                }
            }
        } else {
            out.setIfStore(false);
        }

        Integer categoryId = out.getCategoryId();

        if (ObjectUtil.isNotNull(categoryId)) {
            // 读取上级分类信息
            List<ProductCategory> parentCategoryListById = productCategoryRepository.getParentCategory(categoryId);
            out.setProductCategorys(parentCategoryListById);
        }

        //服务
        QueryWrapper<ContractType> contractTypeQueryWrapper = new QueryWrapper<>();
        contractTypeQueryWrapper.eq("contract_type_enable", true);
        contractTypeQueryWrapper.orderByAsc("contract_type_order");
        List<ContractType> contractTypes = contractTypeRepository.find(contractTypeQueryWrapper);

        if (CollectionUtil.isNotEmpty(contractTypes)) {
            out.setContracts(contractTypes);
        }

        // 商品评论
        QueryWrapper<ProductComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("product_id", productId).eq("comment_enable", 1).orderByDesc("comment_id");
        List<ProductComment> productCommentList = productCommentRepository.lists(commentQueryWrapper, 1, 5).getRecords();
        if (CollUtil.isNotEmpty(productCommentList)) {

            List<Integer> userIds = productCommentList.stream().map(ProductComment::getUserId).distinct().collect(Collectors.toList());
            List<UserInfo> userInfos = userInfoRepository.gets(userIds);
            productCommentList.forEach(comment -> {
                comment.setCommentContent(StrUtil.isEmpty(comment.getCommentContent()) ? "无评论" : comment.getCommentContent());
                userInfos.stream()
                        .filter(userInfo -> userInfo.getUserId().equals(comment.getUserId()))
                        .findFirst()
                        .ifPresent(userInfo -> comment.setUserAvatar(userInfo.getUserAvatar()));
                String commentImage = comment.getCommentImage();
                if (StrUtil.isNotEmpty(commentImage)) {
                    String replaceImg = commentImage.replace("[", "").replace("]", "");
                    comment.setCommentImages(Convert.toList(String.class, replaceImg));
                }
            });

            out.setLastComments(productCommentList);
            out.setLastComment(productCommentList.get(0));
        }

        return out;
    }

    /**
     * 商品详情
     *
     * @param itemId
     * @param districtId
     * @param gbId
     * @return
     */
    @Override
    public ProductDetailRes detail(Long itemId, Integer districtId, Integer gbId) {
        ProductDetailInput input = new ProductDetailInput();
        input.setItemId(itemId);
        input.setDistrictId(districtId);
        input.setGbId(gbId);

        return detail(input);
    }

    /**
     * 商品活动信息
     *
     * @param itemId
     * @return
     */
    @Override
    public ActivityInfoRes getActivityInfo(Long itemId) {
        //读取活动信息
        List<ActivityInfoVo> activityInfoVoList = activityItemService.getActivityInfo(Convert.toList(Long.class, itemId));
        if (CollUtil.isNotEmpty(activityInfoVoList)) {
            ActivityInfoVo activityInfoVo = activityInfoVoList.get(0);
        }

        ActivityInfoRes res = new ActivityInfoRes();
        res.setItems(activityInfoVoList);

        return res;
    }

    @Override
    public ItemListRes listItem(ProductItemInput productItemListReq) {
        ItemListRes output = new ItemListRes();

        //参加活动 产品及数量 - 活动信息使用
        Map<Long, ItemNumVo> itemNumVoMap = new HashMap<>();
        if (CheckUtil.isNotEmpty(productItemListReq.getActivityId())) {
            ActivityBase activityBase = activityBaseRepository.get(productItemListReq.getActivityId());

            if (activityBase != null) {
                String activityRule = activityBase.getActivityRule();

                if (StrUtil.isNotEmpty(activityRule)) {
                    itemNumVoMap = activityBaseRepository.getActivityItemNum(activityBase);
                }

                productItemListReq.setItemId(Convert.toList(Long.class, activityBase.getActivityItemIds()));
                output.setActivityBase(activityBase);
            }
        }

        if (productItemListReq.getItemId() != null && productItemListReq.getItemId().isEmpty()) {
            productItemListReq.setItemId(null);
        }

        IPage<Long> lists = productItemRepository.listItemKey(new Page<>(productItemListReq.getPage(), productItemListReq.getSize()), productItemListReq);
        List<Long> itemIds = lists.getRecords();

        output.setRecords(Convert.toInt(lists.getTotal()));
        output.setSize(Convert.toInt(lists.getSize()));
        output.setPage(Convert.toInt(lists.getCurrent()));
        output.setTotal(Convert.toInt(lists.getPages()));

        List<ItemOutput> records = new ArrayList<>();
        output.setItems(records);

        if (CollUtil.isNotEmpty(itemIds)) {
            List<ProductItem> productItems = productItemRepository.gets(itemIds);
            List<Long> productIds = CommonUtil.column(productItems, ProductItem::getProductId);

            records = BeanUtil.copyToList(productItems, ItemOutput.class);
            output.setItems(records);

            //SKU图片
            List<ProductImage> productImages = productImageRepository.find(new QueryWrapper<ProductImage>().in("product_id", productIds));

            //基础表数据
            List<ProductBase> productBases = productBaseRepository.gets(productIds);

            //产品状态
            List<ProductIndex> productIndices = productIndexRepository.gets(productIds);
            Map<Long, ProductIndex> productIndexMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(productIndices)) {
                productIndexMap = productIndices.stream().collect(Collectors.toMap(ProductIndex::getProductId, ProductIndex -> ProductIndex, (k1, k2) -> k1));
            }

            for (ItemOutput vo : records) {
                //ProductImage productImage = productImages.stream().filter(s -> {return s.getProductId().equals(vo.getProductId()) && s.getColorId().equals(vo.getColorId());}).findFirst().orElse(new ProductImage());
                ProductImage productImage = productImages.stream().filter(s -> {
                    return s.getProductId().equals(vo.getProductId()) && s.getColorId().equals(vo.getColorId());
                }).findFirst().orElse(new ProductImage());

                for (ProductBase base : productBases) {
                    if (vo.getProductId().equals(base.getProductId())) {
                        String itemName = vo.getItemName();
                        String itemSpecName = StrUtil.replaceChars(itemName, ",", " ");

                        String productItemName = base.getProductName() + " " + itemSpecName;
                        vo.setItemSpecName(productItemName);

                        vo.setProductName(base.getProductName());
                        vo.setProductTips(base.getProductTips());
                        vo.setProductVideo(base.getProductVideo());
                        vo.setTransportTypeId(base.getTransportTypeId());
                        vo.setProductBuyLimit(base.getProductBuyLimit());

                        vo.setProductImage(base.getProductImage());
                        if (ObjectUtil.isNotEmpty(productImage.getItemImageDefault())) {
                            vo.setProductImage(productImage.getItemImageDefault());
                        }

                        //商品状态
                        if (!productIndexMap.isEmpty()) {
                            ProductIndex productIndex = productIndexMap.get(base.getProductId());

                            if (productIndex != null) {
                                vo.setProductStateId(productIndex.getProductStateId());
                            }
                        }
                    }
                }
                //活动产品数量
                if (CollUtil.isNotEmpty(itemNumVoMap)) {
                    ItemNumVo itemNumVo = itemNumVoMap.get(vo.getItemId());

                    if (itemNumVo != null) {
                        vo.setActivityItemNum(itemNumVo.getNum());
                    }
                }

            }
        }

        return output;
    }

    @Override
    public void autoSaleProduct() {
        QueryWrapper<ProductIndex> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_verify_id", StateCode.PRODUCT_VERIFY_PASSED);
        queryWrapper.eq("product_state_id", StateCode.PRODUCT_STATE_OFF_THE_SHELF);
        queryWrapper.le("product_sale_time", new Date().getTime());
        List<ProductIndex> productIndices = productIndexRepository.find(queryWrapper);

        if (CollectionUtil.isNotEmpty(productIndices)) {
            List<Long> productIds = CommonUtil.column(productIndices, ProductIndex::getProductId);

            QueryWrapper<ProductItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.in("product_id", productIds);
            List<ProductItem> productItems = productItemRepository.find(itemQueryWrapper);

            if (CollectionUtil.isEmpty(productItems)) {
                throw new BusinessException(__("商品SKU集合为空！"));
            }
            List<ProductIndex> indices = new ArrayList<>();

            for (ProductIndex productIndex : productIndices) {
                Long productId = productIndex.getProductId();
                List<ProductItem> productItemList = productItems.stream().filter(item -> item.getProductId().equals(productId)).collect(Collectors.toList());

                if (CollectionUtil.isEmpty(productItemList)) {
                    throw new BusinessException(String.format(__("商品编号: %s ,SKU数据为空！"), productId));
                }
                boolean itemEnable = productItemList.stream()
                        .anyMatch(item -> ObjectUtil.equal(item.getItemEnable(), StateCode.PRODUCT_STATE_NORMAL));

                if (!itemEnable) {
                    continue;
                }

                ProductIndex editIndex = new ProductIndex();
                editIndex.setProductId(productId);
                editIndex.setProductStateId(StateCode.PRODUCT_STATE_NORMAL);
                editIndex.setProductSaleTime(new Date().getTime());
                editIndex.setProductEvaluationNum(productIndex.getProductEvaluationNum());
                indices.add(editIndex);
            }

            if (!productIndexRepository.saveOrUpdate(indices)) {
                throw new BusinessException(__("商品定时上架失败！"));
            }
        }
    }
}
