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


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.invoicing.model.entity.StockBillItem;
import com.suisung.shopsuite.invoicing.repository.StockBillItemRepository;
import com.suisung.shopsuite.pt.model.entity.*;
import com.suisung.shopsuite.pt.model.input.ProductSaveInput;
import com.suisung.shopsuite.pt.model.output.ProductDataOutput;
import com.suisung.shopsuite.pt.model.req.ProductBaseListReq;
import com.suisung.shopsuite.pt.repository.*;
import com.suisung.shopsuite.pt.service.ProductBaseService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.NumberSeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 商品基础表-SPU表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Service
public class ProductBaseServiceImpl extends BaseServiceImpl<ProductBaseRepository, ProductBase, ProductBaseListReq> implements ProductBaseService {

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductValidPeriodRepository productValidPeriodRepository;

    @Autowired
    private NumberSeqService numberSeqService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private StockBillItemRepository stockBillItemRepository;

    @Autowired
    private MessageService messageService;

    @Override
    @Transactional
    public boolean saveProduct(ProductSaveInput in) {
        boolean newAddFlag = false; //新增标记
        Long productId;

        ProductBase productBase = in.getProductBase();
        ProductIndex productIndex = in.getProductIndex();
        ProductInfo productInfo = in.getProductInfo();
        List<ProductItem> productItems = in.getProductItems();
        List<ProductImage> productImages = in.getProductImages();

        //todo 过滤替换词汇

        //todo 是否需要审核

        //判断新增 or 修改
        if (CheckUtil.isEmpty(productBase.getProductId())) {
            newAddFlag = true;
            productId = numberSeqService.getNextSeqInt("product_id");

            //初始化默认值
            if (CheckUtil.isEmpty(productIndex.getProductStateId())) {
                productIndex.setProductStateId(StateCode.PRODUCT_STATE_NORMAL);
            }

            if (productIndex.getProductStateId().equals(StateCode.PRODUCT_STATE_OFF_THE_SHELF)) {
                //in.ProductIndex.ProductSaleTime
            }

            //上架时间
            if (productIndex.getProductStateId().equals(StateCode.PRODUCT_STATE_NORMAL)) {
                productIndex.setProductSaleTime(new Date().getTime());
            }

            productIndex.setProductSpEnable(false);//供应商是否允许批发市场分销
            productIndex.setProductDistEnable(true);//是否允许三级分销
            productIndex.setProductAddTime(new Date().getTime());
            productIndex.setProductFrom(1000);
        } else {
            productId = productBase.getProductId();
        }

        //默认商品设置判断
        boolean isSetDefault = false; //设置了默认SKU

        for (ProductItem v : productItems) {
            if (v.getItemIsDefault()) {
                isSetDefault = true;
                break;
            }
        }

        //如果未设置， 将第SKU设置为默认
        if (!isSetDefault) {
            productItems.get(0).setItemIsDefault(true);
        }

        //处理主图
        String productImage = "";

        //根据默认商品获取默认主图
        for (ProductItem productItem : productItems) {
            if (productItem.getItemIsDefault()) {
                isSetDefault = true;

                for (ProductImage image : productImages) {
                    if (image.getColorId().equals(productItem.getColorId())) {
                        //
                        if (CheckUtil.isEmpty(image.getItemImageDefault())) {
                            image.setItemImageDefault(configBaseService.getDefaultImage());
                        }

                        productImage = image.getItemImageDefault();
                        break;
                    }
                }

                break;
            }
        }

        //商品价格最大值及最小值
        BigDecimal productUnitPriceMin = new BigDecimal(-1);
        BigDecimal productUnitPriceMax = BigDecimal.ZERO;
        BigDecimal productUnitPointsMin = new BigDecimal(-1);
        BigDecimal productUnitPointsMax = BigDecimal.ZERO;


        for (ProductItem v : productItems) {
            //价格
            if (productUnitPriceMin.equals(new BigDecimal(-1))) {
                productUnitPriceMin = v.getItemUnitPrice();
            }

            if (v.getItemUnitPrice().compareTo(productUnitPriceMin) < 0) {
                productUnitPriceMin = v.getItemUnitPrice();
            }


            if (v.getItemUnitPrice().compareTo(productUnitPriceMax) > 0) {
                productUnitPriceMax = v.getItemUnitPrice();
            }

            //积分
            if (productUnitPointsMin.equals(new BigDecimal(-1))) {
                productUnitPointsMin = v.getItemUnitPoints();
            }

            if (v.getItemUnitPoints().compareTo(productUnitPointsMin) < 0) {
                productUnitPointsMin = v.getItemUnitPoints();
            }


            if (v.getItemUnitPoints().compareTo(productUnitPointsMax) > 0) {
                productUnitPointsMax = v.getItemUnitPoints();
            }

        }

        productIndex.setProductUnitPriceMin(productUnitPriceMin);
        productIndex.setProductUnitPriceMax(productUnitPriceMax);
        productIndex.setProductUnitPointsMin(productUnitPointsMin);
        productIndex.setProductUnitPointsMax(productUnitPointsMax);

        //初始化商品状态

        //开启事务

        //商品基础表ProductBase
        productBase.setProductId(productId);
        productBase.setProductImage(productImage);

        boolean flag = repository.save(productBase);

        if (!flag) {
            throw new BusinessException(__("商品基数数据错误"));
        }

        //商品辅助属性 Product_AssistIndexModel
        //处理辅助属性
        Map<String, List<Integer>> productAssistMap = new HashMap<>();

        // 读取JSON字符串
        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            productAssistMap = objectMapper.readValue(productInfo.getProductAssist(), new TypeReference<Map<String, List<Integer>>>() {
            });
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        List<Integer> productAssistData = new ArrayList<>();

        productAssistMap.forEach((assistId, assistItemIdList) -> {
            //g.Log().Info(ctx, assistId, assistItemIdList)
            //System.out.print(assistId);
            //System.out.print(assistItemIdList);
            productAssistData.addAll(assistItemIdList);
        });

        //商品索引表
        //product_assist_data unit_price_min/max
        productIndex.setProductId(productId);
        productIndex.setProductVerifyId(StateCode.PRODUCT_VERIFY_PASSED);
        productIndex.setProductNameIndex(productIndex.getProductName());

        //商品状态 product_state_id 商品状态判断修正： 是否需要审核等
        ProductCategory productCategory = productCategoryRepository.get(productIndex.getCategoryId());
        productIndex.setTypeId(productCategory == null ? 0 : productCategory.getTypeId());
        productIndex.setProductAssistData(CollUtil.join(productAssistData, ","));

        flag = productIndexRepository.save(productIndex);
        if (!flag) {
            throw new BusinessException(__("商品索引数据错误"));
        }

        //商品SKU Product_ItemModel
        //读取已经存在的SKU, 需要删除的记录
        List<ProductItem> oldProductItems = productItemRepository.find(new QueryWrapper<ProductItem>().eq("product_id", productId));
        //List<Serializable> itemIds = productItemRepository.findKey(new QueryWrapper<ProductItem>().eq("product_id", productId));
        List<Long> itemIds = CommonUtil.column(oldProductItems, ProductItem::getItemId);

        for (ProductItem v : productItems) {
            v.setProductId(productId);
            v.setCategoryId(productIndex.getCategoryId());

            if (itemIds.contains(v.getItemId())) {
                itemIds.removeIf(s -> v.getItemId().equals(s));
            }
        }

        if (CollUtil.isNotEmpty(itemIds)) {
            productItemRepository.remove(itemIds);
        }

        //处理ItemName
        for (ProductItem v : productItems) {
            List<String> itemNames = new ArrayList<>();

            List<Map> specs = new ArrayList<>();

            specs = JSONUtil.parseArray(v.getItemSpec(), Map.class);

            for (Map spec : specs) {
                //ISpecVo
                Map item = (Map) spec.get("item");
                itemNames.add(Convert.toStr(item.get("name")));
            }
            v.setItemName(CollUtil.join(itemNames, " "));
        }

        flag = productItemRepository.saves(productItems);

        if (!flag) {
            throw new BusinessException(__("商品SKU数据错误"));
        }

        //处理product_uniqid
        Map<String, List<Object>> productUniqid = new HashMap<>();

        for (ProductItem v : productItems) {
            List<Integer> specItemIds = new ArrayList<>();

            List<Map> specs = new ArrayList<>();

            specs = JSONUtil.parseArray(v.getItemSpec(), Map.class);

            for (Map spec : specs) {
                //ISpecVo
                Map item = (Map) spec.get("item");
                specItemIds.add(Convert.toInt(item.get("id")));
            }
            /*
            List<ISpecVo> specs = new ArrayList<>();

            specs = JSONUtil.parseArray(v.getItemSpec(), ISpecVo.class);

            for (ISpecVo spec : specs) {
                //ISpecVo
                ISpecItemVo item = spec.getItem();
                specItemIds.add(item.getId());
                itemNames.add(item.getId());
            }
             */

            // Sort the slice in ascending order
            Collections.sort(specItemIds);

            String colorImage = "";

            for (ProductImage image : productImages) {
                if (image.getColorId().equals(v.getColorId())) {
                    colorImage = image.getItemImageDefault();
                    break;
                }
            }

            //[]interface{}{"ItemId", "item_unit_price", "item_quantity", "ItemEnable", "color_id", "color_img", "item_name"}
            productUniqid.put(CollUtil.join(specItemIds, "-"), new ArrayList<Object>(Arrays.asList(v.getItemId(), v.getItemUnitPrice(), v.getItemQuantity(), v.getItemEnable(), v.getColorId(), colorImage, v.getItemName())));
        }

        //商品图片 图片和规格属性一起保存  !如果是编辑，允许增加SKU，需要删除不使用的记录
        for (ProductImage v : productImages) {
            v.setProductId(productId);
        }

        flag = productImageRepository.saves(productImages);

        if (!flag) {
            throw new BusinessException(__("商品图片数据错误"));
        }


        if (productIndex.getKindId() == StateCode.PRODUCT_KIND_FUWU
            || productIndex.getKindId() == StateCode.PRODUCT_KIND_EDU) {
            ProductValidPeriod productValidPeriod = in.getProductValidPeriod();
            productValidPeriod.setProductId(productId);

            flag = productValidPeriodRepository.save(productValidPeriod);

            if (!flag) {
                throw new BusinessException(__("商品虚拟商品数据错误"));
            }
        }

        //商品信息表
        productInfo.setProductId(productId);
        productInfo.setProductUniqid(JSONUtil.toJSONString(productUniqid));

        List<Map> productSpecList = new ArrayList<>();
        productSpecList = JSONUtil.parseArray(productInfo.getProductSpec(), Map.class);

        try {
            List<Integer> column = new ArrayList<>();
            for (Map spec : productSpecList) {
                column.add(Convert.toInt(spec.get("id")));
            }

            productInfo.setSpecIds(CollUtil.join(column, ","));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        flag = productInfoRepository.save(productInfo);

        if (!flag) {
            throw new BusinessException(__("商品信息数据错误"));
        }


        List<Long> oldItemIds = CommonUtil.column(oldProductItems, ProductItem::getItemId);

        // 添加商品，设置期初库存
        // 编辑商品，设置库存变动
        List<StockBillItem> stockBillItems = new ArrayList<>();
        for (ProductItem v : productItems) {
            StockBillItem stockBillItem = new StockBillItem();
            stockBillItem.setProductId(v.getProductId());
            stockBillItem.setProductName(productBase.getProductName());
            stockBillItem.setItemId(v.getItemId());
            stockBillItem.setItemName(v.getItemName());

            //oldItemIds 已经存在的SKU, 修改
            if (oldItemIds.contains(v.getItemId())) {
                //查找
                ProductItem findItem = oldProductItems.stream().filter(s -> {
                    return s.getItemId().equals(v.getItemId());
                }).findFirst().orElse(null);

                if (findItem != null) {
                    int i = v.getItemQuantity() - findItem.getItemQuantity();

                    if (i == 0) {
                        continue;
                    } else if (i > 0) {
                        stockBillItem.setBillTypeId(StateCode.BILL_TYPE_IN);
                        stockBillItem.setStockTransportTypeId(StateCode.STOCK_IN_OTHER);
                        stockBillItems.add(stockBillItem);
                    } else {
                        stockBillItem.setBillTypeId(StateCode.BILL_TYPE_OUT);
                        stockBillItem.setStockTransportTypeId(StateCode.STOCK_OUT_OTHER);
                        stockBillItems.add(stockBillItem);
                    }

                    stockBillItem.setBillItemQuantity(Math.abs(i));
                    stockBillItem.setWarehouseItemQuantity(findItem.getItemQuantity());
                }
            } else {
                stockBillItem.setBillTypeId(StateCode.BILL_TYPE_IN);
                stockBillItem.setStockTransportTypeId(StateCode.STOCK_IN_INIT);
                stockBillItem.setBillItemQuantity(v.getItemQuantity());
                stockBillItems.add(stockBillItem);
                stockBillItem.setWarehouseItemQuantity(0);
            }

            stockBillItem.setBillItemUnitPrice(v.getItemUnitPrice());
            stockBillItem.setBillItemSubtotal(v.getItemUnitPrice().multiply(Convert.toBigDecimal(stockBillItem.getBillItemQuantity())));
        }

        if (CollUtil.isNotEmpty(stockBillItems)) {
            boolean add = stockBillItemRepository.saves(stockBillItems);
        }


        return true;
    }

    @Override
    public ProductDataOutput getProduct(Long productId) {
        ProductDataOutput productDataOutput = new ProductDataOutput();

        //基础表
        ProductBase productBase = repository.get(productId);

        if (ObjectUtil.isEmpty(productBase)) {
            throw new BusinessException(__("商品基础数据有误！"));
        }

        productDataOutput.setProductBase(productBase);

        //索引表
        ProductIndex productIndex = productIndexRepository.get(productId);

        if (ObjectUtil.isEmpty(productIndex)) {
            throw new BusinessException(__("商品索引数据有误！"));
        }

        productDataOutput.setProductIndex(productIndex);

        //信息表
        ProductInfo productInfo = productInfoRepository.get(productId);

        if (ObjectUtil.isEmpty(productInfo)) {
            throw new BusinessException(__("商品信息数据有误！"));
        }

        productDataOutput.setProductInfo(productInfo);

        //SKU表
        List<ProductItem> productItems = productItemRepository.find(new QueryWrapper<ProductItem>().eq("product_id", productId));
        if (CollUtil.isEmpty(productItems)) {
            LogUtil.error(ConstantLog.DEFAULT, String.format(__("商品 %d SKU数据有误！"), productId));
            //throw new BusinessException(__("商品SKU数据有误！"));
        }

        productDataOutput.setProductItem(productItems);

        //图片表
        List<ProductImage> productImages = productImageRepository.find(new QueryWrapper<ProductImage>().eq("product_id", productId));
        if (CollUtil.isEmpty(productImages)) {
            LogUtil.error(ConstantLog.DEFAULT, String.format(__("商品 %d 图片数据有误！"), productId));
            //throw new BusinessException(__("商品图片数据有误！"));
        }

        productDataOutput.setProductImage(productImages);

        if (productIndex.getKindId().intValue() == StateCode.PRODUCT_KIND_FUWU) {
            ProductValidPeriod productValidPeriod = productValidPeriodRepository.get(productId);
            productDataOutput.setProductValidPeriod(productValidPeriod);
        }

        return productDataOutput;
    }

    @Override
    @Transactional
    public boolean removeProduct(Long productId) {
        //todo 是否可以删除判断
        // 1、生效或者待生效 活动商品不可删除
        // 2、装修商品 提示
        if (true) {

        }

        // RemoveProdcut 删除商品
        productInfoRepository.remove(productId);
        productIndexRepository.remove(productId);

        productImageRepository.remove(new QueryWrapper<ProductImage>().eq("product_id", productId));
        productItemRepository.remove(new QueryWrapper<ProductItem>().eq("product_id", productId));

        productValidPeriodRepository.remove(productId);

        repository.remove(productId);

        return true;
    }

    @Transactional
    @Override
    public boolean batchEditState(List<Long> productIds, Integer productStateId) {

        Date productSaleTime = null;
        for (Long productId : productIds) {

            ProductBase productBase = get(productId);
            if (productBase == null) {
                throw new BusinessException(__("商品不存在！"));
            }

            ProductIndex productIndex = productIndexRepository.get(productId);
            if (productIndex == null) {
                throw new BusinessException(__("商品索引数据有误！"));
            }

            if (productStateId.equals(StateCode.PRODUCT_STATE_NORMAL)) {
                Integer productVerifyId = productIndex.getProductVerifyId();

                if (Arrays.asList(StateCode.PRODUCT_VERIFY_WAITING, StateCode.PRODUCT_VERIFY_REFUSED).contains(productVerifyId)) {
                    throw new BusinessException(String.format(__("商品编号: %s 尚未审核通过，不可以上架"), productIndex.getProductId()));
                }
                productSaleTime = new Date();

            } else if (productStateId.equals(StateCode.PRODUCT_STATE_OFF_THE_SHELF)) {
                productSaleTime = DateUtil.offsetMonth(new Date(), 12 * 10);// 待上架时间
            } else if (productStateId.equals((StateCode.PRODUCT_STATE_ILLEGAL))) {

                // 违规下架
                /*String messageId = "illegal-commodity-shelves";
                Map<String, Object> args = new HashMap<>();
                args.put("des", "");
                args.put("product_id", productId);
                args.put("product_name", productIndex.getProductName());
                messageService.sendNoticeMsg(0, messageId, args);*/
            }
            productIndex.setProductStateId(productStateId);

            if (productSaleTime != null) {
                productIndex.setProductSaleTime(productSaleTime.getTime());
            }
            if (!productIndexRepository.edit(productIndex)) {
                throw new BusinessException(ResultCode.FAILED);
            }
        }

        return true;
    }
}
