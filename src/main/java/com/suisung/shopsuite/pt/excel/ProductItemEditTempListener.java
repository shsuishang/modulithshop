package com.suisung.shopsuite.pt.excel;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.WxHttpUtil;
import com.suisung.shopsuite.pt.model.entity.*;
import com.suisung.shopsuite.pt.model.input.ProductEditStockInput;
import com.suisung.shopsuite.pt.service.ProductItemService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;


public class ProductItemEditTempListener extends AnalysisEventListener<ProductItemEditTemp> {

    private final ArrayList<ProductEditStockInput> stockList = new ArrayList<>();
    private final ArrayList<ProductItem> productItems = new ArrayList<>();


    public static ProductItemService productItemService;


    static {
        initDictionary();
    }

    static void initDictionary() {
        productItemService = WxHttpUtil.getBean(ProductItemService.class);
    }

    /**
     * 每解析一行，回调该方
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(ProductItemEditTemp data, AnalysisContext context) {
        initDictionary();

        Long itemId = data.getItemId();
        BigDecimal itemUnitPrice = data.getItemUnitPrice();
        Integer billTypeId = data.getBillTypeId();
        Integer itemQuantity = data.getItemQuantity();

        if (CheckUtil.isEmpty(itemId)) {
            throw new BusinessException(__("SKU编号不能为空！"));
        }

        if (CheckUtil.isNotEmpty(itemUnitPrice) && itemUnitPrice.compareTo(BigDecimal.ZERO) > 0) {
            ProductItem productItem = new ProductItem();
            productItem.setItemId(itemId);
            productItem.setItemUnitPrice(itemUnitPrice);
            productItems.add(productItem);
        }

        if (CheckUtil.isNotEmpty(billTypeId) && CheckUtil.isNotEmpty(itemQuantity)) {
            ProductEditStockInput stockInput = new ProductEditStockInput();
            stockInput.setItemId(itemId);
            stockInput.setBillTypeId(billTypeId);
            stockInput.setItemQuantity(itemQuantity);
            stockList.add(stockInput);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (productItems.size() == 0 && stockList.size() == 0) return;

        if (CollectionUtil.isNotEmpty(productItems)) {
            productItemService.batchEditUnitPrice(productItems);
        }

        if (CollectionUtil.isNotEmpty(stockList)) {
            productItemService.batchEditStock(stockList);
        }
        // 清除数据
        productItems.clear();
        stockList.clear();
    }
}
