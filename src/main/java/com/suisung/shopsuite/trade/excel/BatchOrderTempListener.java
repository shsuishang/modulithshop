package com.suisung.shopsuite.trade.excel;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.WxHttpUtil;
import com.suisung.shopsuite.marketing.model.vo.ItemNumVo;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.pt.repository.ProductBaseRepository;
import com.suisung.shopsuite.pt.service.ProductItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BatchOrderTempListener extends AnalysisEventListener<BatchOrderTemp> {

    private final ArrayList<ProductItemVo> productItems = new ArrayList<>();

    private final ArrayList<ProductItemVo> itemTemps = new ArrayList<>();


    public static ProductItemService productItemService;

    public static ProductBaseRepository productBaseRepository;


    static {
        initDictionary();
    }

    static void initDictionary() {
        productItemService = WxHttpUtil.getBean(ProductItemService.class);
        productBaseRepository = WxHttpUtil.getBean(ProductBaseRepository.class);
    }

    /**
     * 每解析一行，回调该方
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(BatchOrderTemp data, AnalysisContext context) {
        initDictionary();
        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setItemNumber(data.getItemNumber());
        productItemVo.setCartQuantity(data.getCartQuantity());

        itemTemps.add(productItemVo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtil.isNotEmpty(productItems)) {
            productItems.clear();
        }
       //查询封装商品item
        if (CollectionUtil.isNotEmpty(itemTemps)) {
            List<String> numbers = CommonUtil.column(itemTemps, ProductItemVo::getItemNumber);

            QueryWrapper<ProductItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.in("item_number", numbers);
            List<ProductItem> productItemList = productItemService.find(itemQueryWrapper);

            if (CollectionUtil.isNotEmpty(productItemList)) {
                Map<String, Integer> numberMap = itemTemps.stream().collect(Collectors.toMap(ProductItemVo::getItemNumber, ProductItemVo::getCartQuantity, (v1, v2) -> v1));
                List<ProductItemVo> productItemVos = productBaseRepository.getItems(CommonUtil.column(productItemList, ProductItem::getItemId), null);
                for (ProductItemVo productItemVo : productItemVos) {
                    productItemVo.setCartQuantity(numberMap.get(productItemVo.getItemNumber()));
                }
                productItems.addAll(productItemVos);
            }
        }

        // 清除数据
        itemTemps.clear();
    }

    public List<ProductItemVo> getItems() {
        return productItems;
    }
}
