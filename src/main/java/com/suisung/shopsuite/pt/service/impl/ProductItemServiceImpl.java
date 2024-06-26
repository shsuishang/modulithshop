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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.invoicing.model.entity.StockBillItem;
import com.suisung.shopsuite.invoicing.repository.StockBillItemRepository;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductInfo;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.input.ProductEditStockInput;
import com.suisung.shopsuite.pt.model.output.ItemOutput;
import com.suisung.shopsuite.pt.model.req.ProductItemListReq;
import com.suisung.shopsuite.pt.repository.ProductIndexRepository;
import com.suisung.shopsuite.pt.repository.ProductInfoRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.pt.service.ProductItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 商品SKU表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Service
public class ProductItemServiceImpl extends BaseServiceImpl<ProductItemRepository, ProductItem, ProductItemListReq> implements ProductItemService {

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private StockBillItemRepository stockBillItemRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    /**
     * 获取itemDialog数据
     *
     * @param productItemListReq
     * @return
     */
    @Override
    public IPage<ItemOutput> listItem(ProductItemListReq productItemListReq) {
        Page<ProductItem> itemPage = lists(productItemListReq);
        List<ProductItem> itemList = itemPage.getRecords();
        List<Long> productIds = itemList.stream().map(ProductItem::getProductId).collect(Collectors.toList());
        List<ProductIndex> indexList = productIndexRepository.gets(productIds);

        IPage<ItemOutput> dialogResIPage = new Page<>();
        List<ItemOutput> dialogResList = new ArrayList<>();
        for (ProductItem productItem : itemList) {
            for (ProductIndex productIndex : indexList) {
                if (productItem.getProductId().equals(productIndex.getProductId())) {
                    ItemOutput dialogRes = new ItemOutput();
                    dialogRes.setProductStateId(productIndex.getProductStateId());
                    BeanUtils.copyProperties(productItem, dialogRes);
                    dialogResList.add(dialogRes);
                }
            }
        }

        dialogResIPage.setPages(itemPage.getPages());
        dialogResIPage.setCurrent(itemPage.getCurrent());
        dialogResIPage.setSize(itemPage.getSize());
        dialogResIPage.setTotal(itemPage.getTotal());
        dialogResIPage.setRecords(dialogResList);
        return dialogResIPage;
    }

    @Override
    @Transactional
    public boolean editStock(ProductEditStockInput input) {
        boolean flag = false;
        ProductItem v = get(input.getItemId());

        if (ObjectUtil.isNotEmpty(v)) {
            StockBillItem stockBillItem = new StockBillItem();
            stockBillItem.setProductId(v.getProductId());
            //stockBillItem.setProductName(productBase.getProductName());
            stockBillItem.setItemId(v.getItemId());
            stockBillItem.setItemName(v.getItemName());

            stockBillItem.setBillItemQuantity(input.getItemQuantity());
            stockBillItem.setWarehouseItemQuantity(v.getItemQuantity());

            if (input.getBillTypeId().equals(StateCode.BILL_TYPE_IN)) {
                stockBillItem.setBillTypeId(StateCode.BILL_TYPE_IN);
                stockBillItem.setStockTransportTypeId(StateCode.STOCK_IN_OTHER);

                v.setItemQuantity(v.getItemQuantity() + input.getItemQuantity());
            } else {
                stockBillItem.setBillTypeId(StateCode.BILL_TYPE_OUT);
                stockBillItem.setStockTransportTypeId(StateCode.STOCK_OUT_OTHER);

                v.setItemQuantity(v.getItemQuantity() - input.getItemQuantity());
            }


            stockBillItem.setBillItemUnitPrice(v.getItemUnitPrice());
            stockBillItem.setBillItemSubtotal(v.getItemUnitPrice().multiply(Convert.toBigDecimal(stockBillItem.getBillItemQuantity())));

            flag = stockBillItemRepository.add(stockBillItem);

            if (flag) {
                flag = edit(v);
            }
        }

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editState(ProductItem item) {
        Long itemId = item.getItemId();
        Integer itemEnable = item.getItemEnable();
        ProductItem productItem = get(itemId);

        if (productItem == null) {
            throw new BusinessException(__("商品SKU不存在！"));
        }
        productItem.setItemEnable(itemEnable);

        if (!edit(productItem)) {
            throw new BusinessException(__("修改商品SKU状态失败！"));
        }
        Long productId = productItem.getProductId();
        //修改商品SKU(JSON)
        ProductInfo productInfo = productInfoRepository.get(productId);

        if (productInfo == null) {
            throw new BusinessException(__("商品信息为空！"));
        }
        List<Integer> specItemIds = new ArrayList<>();

        if (StrUtil.isNotEmpty(productItem.getItemSpec())) {
            List<Map> specs = JSONUtil.parseArray(productItem.getItemSpec(), Map.class);

            for (Map spec : specs) {
                //ISpecVo
                Map row = (Map) spec.get("item");
                specItemIds.add(Convert.toInt(row.get("id")));
            }
        }

        if (CollectionUtil.isNotEmpty(specItemIds)) {
            Collections.sort(specItemIds);
            String specItem = CollUtil.join(specItemIds, "-");
            String productUniqid = productInfo.getProductUniqid();

            if (StrUtil.isNotEmpty(productUniqid)) {
                JSONObject jsonObject = JSONObject.parseObject(productUniqid);
                List<Object> objects = Convert.toList(Object.class, jsonObject.get(specItem));

                if (CollectionUtil.isNotEmpty(objects) && objects.size() > 3) {
                    objects.set(3, itemEnable);
                    jsonObject.put(specItem, objects);
                    productInfo.setProductUniqid(JSONUtil.toJSONString(jsonObject));

                    if (!productInfoRepository.edit(productInfo)) {
                        throw new BusinessException(__("修改商品信息失败！"));
                    }
                }
            }
        }

        //修改产品索引表状态
        if (itemEnable.equals(StateCode.PRODUCT_STATE_OFF_THE_SHELF)) {
            QueryWrapper<ProductItem> productItemQueryWrapper = new QueryWrapper<>();
            productItemQueryWrapper.eq("product_id", productId);
            List<ProductItem> productItemList = find(productItemQueryWrapper);

            if (CollectionUtil.isEmpty(productItemList)) {
                throw new BusinessException(__("商品SKU集合为空！"));
            }
            boolean enable = productItemList.stream()
                    .anyMatch(row -> ObjectUtil.equal(row.getItemEnable(), StateCode.PRODUCT_STATE_NORMAL));

            if (!enable) {
                ProductIndex productIndex = productIndexRepository.get(productId);

                if (productIndex == null) {
                    throw new BusinessException(__("产品索引信息不存在！"));
                }
                productIndex.setProductStateId(StateCode.PRODUCT_STATE_OFF_THE_SHELF);

                if (!productIndexRepository.edit(productIndex)) {
                    throw new BusinessException(__("修改产品索引信息失败！"));
                }
            }
        }

        return true;
    }

}
