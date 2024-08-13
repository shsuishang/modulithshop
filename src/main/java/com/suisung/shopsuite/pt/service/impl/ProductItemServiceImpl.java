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
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.excel.EasyExcelUtil;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.invoicing.model.entity.StockBillItem;
import com.suisung.shopsuite.invoicing.repository.StockBillItemRepository;
import com.suisung.shopsuite.pt.dao.ProductItemDao;
import com.suisung.shopsuite.pt.excel.ProductItemEditTemp;
import com.suisung.shopsuite.pt.excel.ProductItemEditTempListener;
import com.suisung.shopsuite.pt.model.entity.ProductImage;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductInfo;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.input.ProductEditStockInput;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.model.output.ItemOutput;
import com.suisung.shopsuite.pt.model.req.ProductItemListReq;
import com.suisung.shopsuite.pt.repository.ProductImageRepository;
import com.suisung.shopsuite.pt.repository.ProductIndexRepository;
import com.suisung.shopsuite.pt.repository.ProductInfoRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.pt.service.ProductItemService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
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

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductItemDao productItemDao;

    @Autowired
    private ConfigBaseService configBaseService;

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
    public boolean batchEditStock(List<ProductEditStockInput> inputs) {
        List<Long> itemIds = CommonUtil.column(inputs, ProductEditStockInput::getItemId);

        List<ProductItem> productItems = gets(itemIds);

        if (CollectionUtil.isEmpty(productItems)) {
            throw new BusinessException(__("商品SKU信息不存在！"));
        }
        Map<Long, ProductEditStockInput> editStockInputMap = inputs.stream().collect(Collectors.toMap(ProductEditStockInput::getItemId, ProductEditStockInput -> ProductEditStockInput, (k1, k2) -> k1));

        List<StockBillItem> stockBillItems = new ArrayList<>();

        for (ProductItem v : productItems) {
            ProductEditStockInput input = editStockInputMap.get(v.getItemId());

            if (input != null) {
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

                    if (v.getAvailableQuantity().intValue() >= input.getItemQuantity()) {
                        v.setItemQuantity(v.getItemQuantity() - input.getItemQuantity());
                    } else {
                        throw new BusinessException(__("出库数量不能大于总库存！"));
                    }
                }

                stockBillItem.setBillItemUnitPrice(v.getItemUnitPrice());
                stockBillItem.setBillItemSubtotal(v.getItemUnitPrice().multiply(Convert.toBigDecimal(stockBillItem.getBillItemQuantity())));

                stockBillItems.add(stockBillItem);
            }
        }

        if (!stockBillItemRepository.saveOrUpdate(stockBillItems)) {
            throw new BusinessException(__("保存出入库单据失败！"));
        }

        if (!productItemRepository.saveOrUpdate(productItems)) {
            throw new BusinessException(__("修改商品SKU信息失败！"));
        }

        return true;
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

    @Override
    @Transactional
    public boolean batchEditUnitPrice(List<ProductItem> inputItems) {
        List<Long> itemIds = CommonUtil.column(inputItems, ProductItem::getItemId);

        List<ProductItem> productItems = gets(itemIds);

        if (CollectionUtil.isEmpty(productItems)) {
            throw new BusinessException(__("商品SKU信息不存在！"));
        }
        List<Long> productIds = CommonUtil.column(productItems, ProductItem::getProductId);

        List<ProductIndex> productIndices = productIndexRepository.gets(productIds);

        if (CollectionUtil.isEmpty(productIndices)) {
            throw new BusinessException(__("产品索引集合为空！"));
        }

        List<ProductInfo> productInfos = productInfoRepository.gets(productIds);

        if (CollectionUtil.isEmpty(productInfos)) {
            throw new BusinessException(__("商品信息集合为空！"));
        }

        QueryWrapper<ProductImage> productImageQueryWrapper = new QueryWrapper<>();
        productImageQueryWrapper.in("product_id", productIds);
        List<ProductImage> productImages = productImageRepository.find(productImageQueryWrapper);

        if (CollectionUtil.isEmpty(productImages)) {
            throw new BusinessException(__("商品信息集合为空！"));
        }

        if (!productItemRepository.saveOrUpdate(inputItems)) {
            throw new BusinessException(__("修改商品SKU商品价格失败！"));
        }

        QueryWrapper<ProductItem> productItemQueryWrapper = new QueryWrapper<>();
        productItemQueryWrapper.in("product_id", productIds);
        List<ProductItem> productItemList = find(productItemQueryWrapper);

        if (CollectionUtil.isEmpty(productItemList)) {
            throw new BusinessException(__("商品SKU集合为空！"));
        }


        for (ProductIndex productIndex : productIndices) {
            Long productId = productIndex.getProductId();
            List<ProductItem> items = productItemList.stream().filter(item -> item.getProductId().equals(productId)).collect(Collectors.toList());
            //商品价格最大值及最小值
            BigDecimal productUnitPriceMin = new BigDecimal(-1);
            BigDecimal productUnitPriceMax = BigDecimal.ZERO;

            for (ProductItem v : items) {
                if (productUnitPriceMin.equals(new BigDecimal(-1))) {
                    productUnitPriceMin = v.getItemUnitPrice();
                }

                if (v.getItemUnitPrice().compareTo(productUnitPriceMin) < 0) {
                    productUnitPriceMin = v.getItemUnitPrice();
                }

                if (v.getItemUnitPrice().compareTo(productUnitPriceMax) > 0) {
                    productUnitPriceMax = v.getItemUnitPrice();
                }
            }
            productIndex.setProductUnitPriceMin(productUnitPriceMin);
            productIndex.setProductUnitPriceMax(productUnitPriceMax);
        }

        if (!productIndexRepository.saveOrUpdate(productIndices)) {
            throw new BusinessException(__("产品索引信息失败！"));
        }

        for (ProductInfo productInfo : productInfos) {
            Long productId = productInfo.getProductId();
            List<ProductItem> items = productItemList.stream().filter(item -> item.getProductId().equals(productId)).collect(Collectors.toList());
            List<ProductImage> imageList = productImages.stream().filter(item -> item.getProductId().equals(productId)).collect(Collectors.toList());

            //处理product_uniqid
            Map<String, List<Object>> productUniqid = new HashMap<>();

            for (ProductItem v : items) {
                List<Integer> specItemIds = new ArrayList<>();

                List<Map> specs = new ArrayList<>();

                specs = JSONUtil.parseArray(v.getItemSpec(), Map.class);

                for (Map spec : specs) {
                    //ISpecVo
                    Map item = (Map) spec.get("item");
                    specItemIds.add(Convert.toInt(item.get("id")));
                }
                Collections.sort(specItemIds);

                String colorImage = "";

                for (ProductImage image : imageList) {
                    if (image.getColorId().equals(v.getColorId())) {
                        colorImage = image.getItemImageDefault();
                        break;
                    }
                }

                productUniqid.put(CollUtil.join(specItemIds, "-"), new ArrayList<Object>(Arrays.asList(v.getItemId(), v.getItemUnitPrice(), v.getItemQuantity(), v.getItemEnable(), v.getColorId(), colorImage, v.getItemName())));
            }

            productInfo.setProductUniqid(JSONUtil.toJSONString(productUniqid));
        }

        if (!productInfoRepository.saveOrUpdate(productInfos)) {
            throw new BusinessException(__("产品索引信息失败！"));
        }

        return true;
    }

    @Override
    public IPage<ItemOutput> getStockWarningItems(ProductItemInput input) {
        Integer stockWarning = configBaseService.getConfig("stock_warning", 5);
        input.setStockWarning(stockWarning);

        return productItemDao.getStockWarningItems(new Page<>(input.getPage(), input.getSize()), input);
    }

    @Override
    public void exportTemp(HttpServletResponse response) {
        //编码问题
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(__("" +
                    "" +
                    "") + "-" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(ProductItemEditTemp.class, 0, "导入批量修改价格、库存模版");
            excelWriter.write(new ArrayList<ProductItemEditTemp>(), writeSheet);
            excelWriter.finish();
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(__("导出Excel编码异常"));
        } catch (IOException e) {
            throw new BusinessException(__("导出Excel文件异常"));
        }
    }

    @Override
    public void importTemp(MultipartFile file) throws Exception {
        AnalysisEventListener productItemEditTempListener = new ProductItemEditTempListener();
        Class<?> tempClass = ProductItemEditTemp.class;

        InputStream inputStream = file.getInputStream();
        EasyExcel.read(inputStream)
                // 注册监听器，可以在这里校验字段
                .registerReadListener(productItemEditTempListener)
                .head(tempClass)
                // 设置sheet,默认读取第一个
                .sheet()
                // 设置标题所在行数
                .headRowNumber(1)
                .doReadSync();
    }
}
