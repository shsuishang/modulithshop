package com.suisung.shopsuite.pt.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.utils.WxHttpUtil;
import com.suisung.shopsuite.pt.model.entity.*;
import com.suisung.shopsuite.pt.model.input.ProductSaveInput;
import com.suisung.shopsuite.pt.service.ProductBaseService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;


public class ProductTempListener extends AnalysisEventListener<ProductTemp> {

    private final ArrayList<ProductSaveInput> list = new ArrayList<>();


    public static ProductBaseService productBaseService;


    static {
        initDictionary();
    }

    static void initDictionary() {
        productBaseService = WxHttpUtil.getBean(ProductBaseService.class);
    }

    /**
     * 每解析一行，回调该方
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(ProductTemp data, AnalysisContext context) {
        initDictionary();
        ProductSaveInput productSaveInput = new ProductSaveInput();
        Integer kindId = data.getKindId();
        Long colorId = 0L;
        String colorName = null;

        if (StrUtil.isEmpty(data.getItemSpec())) {
            data.setItemSpec("[]");
        }

        if (StrUtil.isEmpty(data.getProductSpec())) {
            data.setProductSpec("[]");
        }

        if (StrUtil.isEmpty(data.getProductAssist())) {
            data.setProductAssist("{}");
        }

        if (StrUtil.isEmpty(data.getProductDetail())) {
            data.setProductDetail("");
        }

        if (CheckUtil.isEmpty(kindId)) {
            throw new BusinessException(__("商品种类存在空数据！"));
        }

        if (CheckUtil.isEmpty(data.getTransportTypeId())) {
            throw new BusinessException(__("运费设置存在空数据！"));
        }

        List<Map> mapList = JSONUtil.parseArray(data.getItemSpec(), Map.class);

        if (CollectionUtil.isNotEmpty(mapList)) {

            for (Map map : mapList) {
                Map itemMap = Convert.toMap(String.class, Object.class, map.get("item"));
                colorId = Convert.toLong(itemMap.get("id"));
                colorName = Convert.toStr(itemMap.get("name"));
            }
        }

        ProductBase productBase = BeanUtil.copyProperties(data, ProductBase.class);
        productSaveInput.setProductBase(productBase);

        ProductIndex productIndex = BeanUtil.copyProperties(data, ProductIndex.class);
        productIndex.setProductStateId(StateCode.PRODUCT_STATE_NORMAL);
        productIndex.setProductInventoryLock(1001);
        productSaveInput.setProductIndex(productIndex);

        ProductInfo productInfo = BeanUtil.copyProperties(data, ProductInfo.class);
        productSaveInput.setProductInfo(productInfo);

        if (CheckUtil.isEmpty(data.getItemUnitPoints())) {
            data.setItemUnitPoints(BigDecimal.ZERO);
        }

        ProductItem productItem = BeanUtil.copyProperties(data, ProductItem.class);
        productItem.setItemIsDefault(true);
        productItem.setColorId(colorId);
        productItem.setItemEnable(StateCode.PRODUCT_STATE_NORMAL);
        productSaveInput.setProductItems(Collections.singletonList(productItem));

        ProductImage productImage = BeanUtil.copyProperties(data, ProductImage.class);
        productImage.setColorId(colorId);
        productImage.setColorName(colorName);
        productSaveInput.setProductImages(Collections.singletonList(productImage));

        if (kindId.equals(StateCode.PRODUCT_KIND_FUWU)) {
            ProductValidPeriod productValidPeriod = BeanUtil.copyProperties(data, ProductValidPeriod.class);
            productValidPeriod.setProductValidPeriod(1001);
            productSaveInput.setProductValidPeriod(productValidPeriod);
        }

        list.add(productSaveInput);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (list.size() == 0) return;
        for (ProductSaveInput productSaveInput : list) {
            productBaseService.saveProduct(productSaveInput);
        }
        // 清除数据
        list.clear();
    }
}
