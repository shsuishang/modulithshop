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
package com.suisung.shopsuite.trade.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserDeliveryAddress;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.entity.UserInvoice;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.account.service.UserInvoiceService;
import com.suisung.shopsuite.admin.model.entity.UserAdmin;
import com.suisung.shopsuite.admin.repository.UserAdminRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.PointsType;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.invoicing.model.entity.StockBill;
import com.suisung.shopsuite.invoicing.model.entity.StockBillItem;
import com.suisung.shopsuite.invoicing.model.entity.WarehouseItem;
import com.suisung.shopsuite.invoicing.model.vo.StockBillVo;
import com.suisung.shopsuite.invoicing.repository.StockBillItemRepository;
import com.suisung.shopsuite.invoicing.repository.StockBillRepository;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.pay.model.entity.ConsumeRecord;
import com.suisung.shopsuite.pay.model.entity.ConsumeTrade;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.model.vo.PointsVo;
import com.suisung.shopsuite.pay.repository.ConsumeRecordRepository;
import com.suisung.shopsuite.pay.repository.ConsumeTradeRepository;
import com.suisung.shopsuite.pay.repository.UserResourceRepository;
import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.input.ProductEditStockInput;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.pt.repository.ProductCategoryRepository;
import com.suisung.shopsuite.pt.repository.ProductIndexRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.pt.service.ProductItemService;
import com.suisung.shopsuite.shop.model.entity.StoreExpressLogistics;
import com.suisung.shopsuite.shop.model.entity.StoreShippingAddress;
import com.suisung.shopsuite.shop.model.entity.UserVoucher;
import com.suisung.shopsuite.shop.repository.StoreExpressLogisticsRepository;
import com.suisung.shopsuite.shop.repository.StoreShippingAddressRepository;
import com.suisung.shopsuite.shop.repository.UserVoucherRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.NumberSeqService;
import com.suisung.shopsuite.trade.model.entity.*;
import com.suisung.shopsuite.trade.model.input.*;
import com.suisung.shopsuite.trade.model.output.CheckoutOutput;
import com.suisung.shopsuite.trade.model.output.OrderAddOutput;
import com.suisung.shopsuite.trade.model.output.OrderNumOutput;
import com.suisung.shopsuite.trade.model.req.OrderInfoListReq;
import com.suisung.shopsuite.trade.model.req.OrderInvoiceAddReq;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemInputVo;
import com.suisung.shopsuite.trade.model.vo.OrderVo;
import com.suisung.shopsuite.trade.model.vo.PickingItem;
import com.suisung.shopsuite.trade.model.vo.StoreItemVo;
import com.suisung.shopsuite.trade.repository.*;
import com.suisung.shopsuite.trade.service.OrderInfoService;
import com.suisung.shopsuite.trade.service.OrderReturnService;
import com.suisung.shopsuite.trade.service.OrderService;
import com.suisung.shopsuite.trade.service.UserCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 订单详细信息-检索不分表也行，cache 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Repository
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderBaseRepository orderBaseRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderDataRepository orderDataRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ConsumeTradeRepository consumeTradeRepository;

    @Autowired
    private OrderDeliveryAddressRepository orderDeliveryAddressRepository;

    @Autowired
    private OrderStateLogRepository orderStateLogRepository;

    @Autowired
    private OrderLogisticsRepository orderLogisticsRepository;

    @Autowired
    private ConsumeRecordRepository consumeRecordRepository;

    @Autowired
    private StockBillRepository stockBillRepository;

    @Autowired
    private StockBillItemRepository stockBillItemRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private NumberSeqService numberSeqService;

    @Autowired
    private OrderReturnRepository orderReturnRepository;

    @Autowired
    private OrderReturnItemRepository orderReturnItemRepository;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private UserResourceRepository userResourceRepository;

    @Autowired
    private ChainCodeRepository chainCodeRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private UserInvoiceService userInvoiceService;

    @Autowired
    private OrderInvoiceRepository orderInvoiceRepository;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private ProductItemService productItemService;

    @Autowired
    private UserAdminRepository userAdminRepository;

    @Autowired
    private OrderReturnService orderReturnService;

    @Autowired
    private StoreExpressLogisticsRepository storeExpressLogisticsRepository;

    @Autowired
    private StoreShippingAddressRepository storeShippingAddressRepository;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ActivityBaseRepository activityBaseRepository;

    /**
     * 读取订单
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderVo detail(String orderId) {

        OrderVo detail = new OrderVo();

        // 信息表
        OrderInfo orderInfo = orderInfoRepository.get(orderId);
        BeanUtils.copyProperties(orderInfo, detail);

        // 详情表
        OrderData orderData = orderDataRepository.get(orderId);
        BeanUtils.copyProperties(orderData, detail);

        // 基础表
        OrderBase orderBase = orderBaseRepository.get(orderId);
        BeanUtils.copyProperties(orderBase, detail);

        // 读取订单商品
        List<OrderItem> orderItems = orderItemRepository.find(new QueryWrapper<OrderItem>().in("order_id", orderId));

        if (CollectionUtil.isNotEmpty(orderItems)) {
            for (OrderItem orderItem : orderItems) {
                String itemSpecName = StrUtil.replaceChars(orderItem.getItemName(), ",", " ");
                orderItem.setProductItemName(orderItem.getProductName() + " " + itemSpecName);
            }
        }

        detail.setItems(orderItems);

        //售后服务
        List<OrderReturnItem> returnItemList = orderReturnItemRepository.find(new QueryWrapper<OrderReturnItem>().eq("order_id", orderId).ne("return_state_id", StateCode.RETURN_PROCESS_CANCEL));

        // 处理为map
        Map<Long, List<String>> orderReturnItemMap = new HashMap<>();

        for (OrderReturnItem item : returnItemList) {
            if (!orderReturnItemMap.containsKey(item.getOrderItemId())) {
                orderReturnItemMap.put(item.getOrderItemId(), new ArrayList<>());
            }

            orderReturnItemMap.get(item.getOrderItemId()).add(item.getReturnId());
        }

        for (OrderItem item : orderItems) {
            // 是否可以退货
            item.setIfReturn(item.getOrderItemQuantity() > item.getOrderItemReturnNum() && ifReturn(orderInfo.getOrderStateId(), orderInfo.getOrderIsPaid()));

            List<String> returnIds = ObjectUtil.defaultIfNull(orderReturnItemMap.get(item.getOrderItemId()), new ArrayList<>());
            item.setReturnIds(returnIds);
        }


        //商品库存
        List<Long> itemIds = CommonUtil.column(orderItems, OrderItem::getItemId);
        List<ProductItem> productItems = productItemRepository.gets(itemIds);

        List<WarehouseItem> warehouseItems = new ArrayList<>();
        for (ProductItem it : productItems) {
            WarehouseItem i = new WarehouseItem();
            BeanUtils.copyProperties(it, i);
            i.setWarehouseId(0);
            i.setItemId(it.getItemId());
            i.setWarehouseItemQuantity(it.getItemQuantity());
            warehouseItems.add(i);
        }

        detail.setWarehouseItems(warehouseItems);

        // 配送地址
        OrderDeliveryAddress orderDeliveryAddress = orderDeliveryAddressRepository.get(orderId);
        detail.setDelivery(orderDeliveryAddress);

        // 订单日志
        List<OrderStateLog> orderStateLogs = orderStateLogRepository.find(new QueryWrapper<OrderStateLog>().in("order_id", orderId));
        detail.setLogItems(orderStateLogs);

        // 物流记录
        List<OrderLogistics> orderLogistics = orderLogisticsRepository.find(new QueryWrapper<OrderLogistics>().in("order_id", orderId));
        detail.setLogistics(orderLogistics);

        // StockBill
        List<StockBillVo> stockBill = stockBillRepository.findDetail(new QueryWrapper<StockBill>().in("order_id", orderId));
        detail.setStockBill(stockBill);

        // ConsumeRecord
        List<ConsumeRecord> consumeRecords = consumeRecordRepository.find(new QueryWrapper<ConsumeRecord>().in("order_id", orderId).eq("trade_type_id", StateCode.TRADE_TYPE_SALES));
        detail.setConsumeRecord(consumeRecords);


        // ConsumeTrade
        List<ConsumeTrade> consumeTrades = consumeTradeRepository.find(new QueryWrapper<ConsumeTrade>().in("order_id", orderId));
        if (!consumeTrades.isEmpty()) {
            detail.setConsumeTrade(consumeTrades.get(0));
        }

        // 虚拟兑换码
        ChainCode chainCode = chainCodeRepository.get(orderId);
        if (chainCode != null) {
            BeanUtils.copyProperties(chainCode, detail);
        }

        //是否取消
        detail.setIfBuyerCancel(isCancel(orderInfo.getOrderStateId(), orderInfo.getOrderIsPaid()));

        // 订单倒计时
        boolean showCancelTime = configBaseService.getConfig("show_cancel_time", false);
        Float orderAutocancelTime = configBaseService.getConfig("order_autocancel_time", 0.0f);
        if (showCancelTime && orderAutocancelTime > 0 && detail.getOrderStateId().equals(StateCode.ORDER_STATE_WAIT_PAY)) {
            Date orderTime = detail.getOrderTime();
            orderTime = DateUtil.offsetSecond(orderTime, (int) (orderAutocancelTime * 60 * 60));
            long remainPayTime = (orderTime.getTime() - new DateTime().getTime()) / 1000;
            detail.setRemainPayTime(remainPayTime);
        }

        return detail;
    }

    /**
     * 订单搜索查询列表
     *
     * @param in
     * @return
     */
    @Override
    public Page<OrderVo> lists(OrderInfoListReq in) {
        Page<OrderVo> out = new Page<>();
        QueryWrapper<OrderInfo> queryWrapper = null;

        if (CheckUtil.isEmpty(in.getOrderStateId())) {
            in.setOrderStateId(null);
            queryWrapper = new BaseQueryWrapper<OrderInfo, OrderInfoListReq>(in).getWrapper();
        } else {
            if (in.getOrderStateId().equals(StateCode.ORDER_STATE_WAIT_SHIPPING) || in.getOrderStateId().equals(StateCode.ORDER_STATE_PICKING)) {
                in.setOrderStateId(null);
                queryWrapper = new BaseQueryWrapper<OrderInfo, OrderInfoListReq>(in).getWrapper().in("order_state_id", Arrays.asList(StateCode.ORDER_STATE_PICKING, StateCode.ORDER_STATE_WAIT_SHIPPING));
            } else {
                queryWrapper = new BaseQueryWrapper<OrderInfo, OrderInfoListReq>(in).getWrapper();
            }
        }

        // 订单搜索查询列表
        Page<OrderInfo> lists = orderInfoRepository.lists(queryWrapper, in.getPage(), in.getSize());
        BeanUtils.copyProperties(lists, out);
        out.setRecords(BeanUtil.copyToList(lists.getRecords(), OrderVo.class));

        //是否可以取消
        if (CollectionUtil.isNotEmpty(out.getRecords())) {
            for (OrderVo orderVo : out.getRecords()) {
                orderVo.setIfBuyerCancel(isCancel(orderVo.getOrderStateId(), orderVo.getOrderIsPaid()));
            }
        }

        List<String> order_id_row = CommonUtil.column(out.getRecords(), OrderVo::getOrderId);

        //判断是否有退款退货
        List<String> orderReturnIdList = new ArrayList<>();

        if (CollUtil.isNotEmpty(order_id_row)) {
            QueryWrapper<OrderReturn> qw = new QueryWrapper<>();
            qw.in("order_id", order_id_row);
            List<OrderReturn> orderReturnList = orderReturnRepository.find(qw);

            orderReturnIdList = CommonUtil.column(orderReturnList, OrderReturn::getOrderId);
        }

        //判断是否开了发票
        List<String> orderInvoiceIdList = new ArrayList<>();

        if (CollUtil.isNotEmpty(order_id_row)) {
            QueryWrapper<OrderInvoice> orderInvoiceQueryWrapper = new QueryWrapper<>();
            orderInvoiceQueryWrapper.in("order_id", order_id_row);
            List<OrderInvoice> orderInvoiceList = orderInvoiceRepository.find(orderInvoiceQueryWrapper);

            orderInvoiceIdList = CommonUtil.column(orderInvoiceList, OrderInvoice::getOrderId);
        }

        List<String> ids = new ArrayList<>();

        // 补全商品基础表信息
        ids = CommonUtil.column(lists.getRecords(), OrderInfo::getOrderId);

        if (CollUtil.isNotEmpty(ids)) {
            List<OrderData> orderDataList = orderDataRepository.gets(ids);
            List<OrderBase> orderBaseList = orderBaseRepository.gets(ids);

            // 读取订单商品
            List<OrderItem> orderItems = orderItemRepository.find(new QueryWrapper<OrderItem>().in("order_id", ids));

            // 处理为map
            Map<String, List<OrderItem>> orderItemMap = new HashMap<>();

            for (OrderItem item : orderItems) {
                if (!orderItemMap.containsKey(item.getOrderId())) {
                    orderItemMap.put(item.getOrderId(), new ArrayList<>());
                }

                orderItemMap.get(item.getOrderId()).add(item);
            }

            for (OrderBase item : orderBaseList) {
                for (OrderVo vo : out.getRecords()) {
                    if (item.getOrderId().equals(vo.getOrderId())) {
                        vo.setOrderNumber(item.getOrderNumber());
                        vo.setOrderTime(item.getOrderTime());
                        vo.setOrderProductAmount(item.getOrderProductAmount());
                        vo.setOrderPaymentAmount(item.getOrderPaymentAmount());
                        vo.setCurrencyId(item.getCurrencyId());
                        vo.setCurrencySymbolLeft(item.getCurrencySymbolLeft());
                        vo.setStoreName(item.getStoreName());
                        vo.setUserNickname(item.getUserNickname());
                    }
                }
            }

            for (OrderData item : orderDataList) {
                for (OrderVo vo : out.getRecords()) {
                    if (item.getOrderId().equals(vo.getOrderId())) {
                        vo.setOrderReturnStatus(item.getOrderReturnStatus());
                        vo.setOrderRefundStatus(item.getOrderRefundStatus());
                    }
                }
            }

            // ConsumeTrade
            List<ConsumeTrade> consumeTrades = consumeTradeRepository.find(new QueryWrapper<ConsumeTrade>().in("order_id", ids));
            for (ConsumeTrade item : consumeTrades) {
                for (OrderVo vo : out.getRecords()) {
                    if (item.getOrderId().equals(vo.getOrderId())) {
                        vo.setTradePaymentAmount(item.getTradePaymentAmount());
                    }
                }
            }

            for (OrderVo vo : out.getRecords()) {
                vo.setItems(orderItemMap.get(vo.getOrderId()));

                vo.setReturnFlag(orderReturnIdList.contains(vo.getOrderId()));

                //是否开了发票
                vo.setInvoiceIsApply(orderInvoiceIdList.contains(vo.getOrderId()));
            }
        }

        return out;
    }

    /**
     * 新增
     *
     * @param in
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderAddOutput add(CheckoutInput in) {
        CheckoutOutput out = userCartService.checkout(in);

        out.setIn(in);

        //添加订单
        OrderAddOutput output = addOrder(out);

        return output;
    }

    /**
     * 下单操作
     *
     * @param cartData 信息
     * @return bool  是否成功
     */
    public OrderAddOutput addOrder(CheckoutOutput cartData) {
        Date now = new Date();

        Integer userId = cartData.getUserId();
        String buyerUserNickname = cartData.getIn().getUserNickname();
        Integer gbId = cartData.getIn().getGbId();
        Integer activityId = cartData.getIn().getActivityId();


        BigDecimal orderSelMoneyAmount = BigDecimal.ZERO;
        BigDecimal orderSelPointsAmount = BigDecimal.ZERO;
        BigDecimal orderSelSpAmount = BigDecimal.ZERO;

        // 判断积分支付的积分是否足够，不够则提示。 使用第一种资源扩展字段。
        BigDecimal orderNeedPayPointsAmount = cartData.getOrderPointsAmount();
        BigDecimal orderResourceExt1 = BigDecimal.ZERO;
        BigDecimal orderResourceExt1Use = BigDecimal.ZERO;

        UserResource userResource = userResourceRepository.get(cartData.getUserId());
        List<String> orderIdRow = null;

        boolean usePoint = false;
        if (CheckUtil.isNotEmpty(orderNeedPayPointsAmount)) {
            orderResourceExt1 = orderNeedPayPointsAmount;

            // 判断资源是否足够
            if (userResource != null) {
                BigDecimal userPoints = userResource.getUserPoints();

                if (ObjectUtil.compare(userPoints, orderNeedPayPointsAmount) >= 0) {
                    orderResourceExt1Use = orderResourceExt1;
                } else {
                    Float pointsVaueRate = configBaseService.getConfig("points_vaue_rate", 0f);

                    // 必须扣积分,设置错误，不扣积分，报异常。
                    if (pointsVaueRate.floatValue() <= 0) {
                        orderResourceExt1Use = BigDecimal.ZERO;
                        throw new BusinessException(__("积分价值配置有误，无法下单！"));
                    } else {
                        // 按照已有的去使用
                        orderResourceExt1Use = userPoints;
                    }
                }
            }

            // 扣除积分
            if (ObjectUtil.compare(orderResourceExt1Use, BigDecimal.ZERO) != 0) {
                String desc = String.format("%s 积分兑换", orderResourceExt1Use);

                PointsVo pointsVo = new PointsVo();
                pointsVo.setUserId(cartData.getUserId());
                pointsVo.setPoints(orderResourceExt1Use.negate());
                pointsVo.setPointsTypeId(PointsType.POINTS_TYPE_EXCHANGE_PRODUCT);
                pointsVo.setPointsLogDesc(desc);

                if (!userResourceRepository.points(pointsVo)) {
                    throw new BusinessException(__("积分操作失败！"));
                }
                usePoint = true;
            }
        }

        // 判断积分支付的积分是否足够，不够则提示。 使用第一种资源扩展字段。
        BigDecimal orderNeedSpAmount = cartData.getOrderSpAmount();
        BigDecimal orderResourceExt2 = BigDecimal.ZERO;
        BigDecimal orderResourceExt2Use = BigDecimal.ZERO;

        if (CheckUtil.isNotEmpty(orderNeedSpAmount)) {
            orderResourceExt2 = orderNeedSpAmount;

            BigDecimal userSpTotal = userResource.getUserSp();
            if (userSpTotal.compareTo(orderNeedSpAmount) > -1) {
                orderResourceExt2Use = orderResourceExt2;
            } else {
                Float spVaueRate = configBaseService.getConfig("sp_vaue_rate", 0f);

                // 必须扣积分,设置错误，不扣积分，报异常。
                if (spVaueRate.floatValue() <= 0) {
                    orderResourceExt2Use = BigDecimal.ZERO;
                    throw new BusinessException(__("积分2不足，无法下单！"));
                } else {
                    // 按照已有的去使用
                    orderResourceExt2Use = userSpTotal;
                }
            }

            // 扣除众宝
            if (ObjectUtil.compare(orderResourceExt2Use, BigDecimal.ZERO) != 0) {
                String desc = String.format("%s 众宝兑换", orderResourceExt2Use);
                // todo User_ResourceModel::sp
            }
        }

        List<Long> cartIds = new ArrayList<>();
        orderIdRow = new ArrayList<>();

        CheckoutInput checkoutRow = cartData.getIn();
        Map category_rate_row = new HashMap();

        Integer chain_id = checkoutRow.getChainId();

        // 判断库存是否可以下单
        for (StoreItemVo storeItem : cartData.getItems()) {
            List<ProductItemVo> itemList = storeItem.getItems();

            for (ProductItemVo itemTmpRow : itemList) {
                if (itemTmpRow.getIsOos()) {
                    throw new BusinessException(String.format(__("商品: %s 不在可售区域"), itemTmpRow.getProductName()));
                }

                //0元主商品，不可以下单
                if (Convert.toBigDecimal(itemTmpRow.getItemSalePrice()).compareTo(BigDecimal.ZERO) <= 0 && Convert.toBigDecimal(itemTmpRow.getItemUnitPoints()).compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException(String.format(__("商品: %s 总价为0，不可以下单，请联系商家！"), itemTmpRow.getProductName()));
                }

                Integer cart_quantity = itemTmpRow.getCartQuantity();

                // 直接判断库存
                Integer item_quantity = itemTmpRow.getItemQuantity();
                Integer kind_id = itemTmpRow.getKindId();

                if (item_quantity < cart_quantity && ObjectUtil.notEqual(kind_id, StateCode.PRODUCT_KIND_EDU)) {
                    throw new BusinessException(String.format(__("商品: %s 库存不足！当前可购买： %d"), itemTmpRow.getProductName(), itemTmpRow.getItemQuantity()));
                }

                // 判断下单数量必须大于0
                if (cart_quantity <= 0) {
                    throw new BusinessException(String.format(__("商品: %s 购买数量必须大于： %d"), itemTmpRow.getProductName(), 0));
                }
            }
        }

        for (StoreItemVo storeItemVo : cartData.getItems()) {
            List<ProductItemVo> itemsList = storeItemVo.getItems();

            /*
            // 判断店铺状态，关闭状态不可以下单
            Boolean store_is_open = Convert.toBool(storeItemVo.get("store_is_open"));
            if (!store_is_open) {
                throw new BusinessException(__("店铺关闭中，不可以下单！"));
            }
             */

            //类型判断
            List<Integer> kindIds = CommonUtil.column(itemsList, ProductItemVo::getKindId);

            if (kindIds.size() > 1) {
                throw new BusinessException(__("订单只可以购买同一种类商品"));
            }

            List<Integer> kinds = Arrays.asList(StateCode.PRODUCT_KIND_FUWU, StateCode.PRODUCT_KIND_CARD, StateCode.PRODUCT_KIND_EDU);

            if (kinds.contains(kindIds.get(0))) {
                if (itemsList.size() > 1) {
                    throw new BusinessException(__("服务类商品每单只可以购买一种商品"));
                }
            }

            OrderData orderData = new OrderData();
            String orderId = numberSeqService.getNextSeqString("JD");

            List<OrderItem> item_rows = new ArrayList();
            OrderInfo orderInfo = new OrderInfo();

            //订单信息
            if (StrUtil.isNotBlank(orderId)) {
                // 快递信息更新
                UserDeliveryAddress address = cartData.getUserDeliveryAddress();

                if (ObjectUtil.isNotEmpty(address)) {
                    OrderDeliveryAddress delivery = new OrderDeliveryAddress();
                    delivery.setOrderId(orderId);
                    delivery.setDaName(address.getUdName());
                    delivery.setDaIntl(address.getUdIntl());
                    delivery.setDaMobile(address.getUdMobile());
                    delivery.setDaTelephone(address.getUdTelephone());
                    delivery.setDaProvinceId(address.getUdProvinceId());
                    delivery.setDaProvince(address.getUdProvince());
                    delivery.setDaCityId(address.getUdCityId());
                    delivery.setDaCity(address.getUdCity());
                    delivery.setDaCountyId(address.getUdCountyId());
                    delivery.setDaCounty(address.getUdCounty());
                    delivery.setDaAddress(address.getUdAddress());
                    delivery.setDaPostalcode(address.getUdPostalcode());
                    delivery.setDaTagName(address.getUdTagName());
                    delivery.setDaLatitude(address.getUdLatitude());
                    delivery.setDaLongitude(address.getUdLongitude());
                    delivery.setDaTime(now);

                    if (!orderDeliveryAddressRepository.save(delivery)) {
                        throw new BusinessException(__("保存订单配送地址数据失败!"));
                    }
                }
            } else {
                throw new BusinessException(__("生成订单编号异常!"));
            }

            Integer storeId = storeItemVo.getStoreId();
            String store_name = storeItemVo.getStoreName();

            // 优惠券使用标记更新
            if (CheckUtil.isNotEmpty(storeItemVo.getUserVoucherId())) {
                UserVoucher userVoucher = new UserVoucher();
                userVoucher.setUserVoucherId(storeItemVo.getUserVoucherId());
                userVoucher.setVoucherStateId(StateCode.VOUCHER_STATE_USED);
                userVoucher.setOrderId(orderId);
                userVoucher.setUserVoucherActivetime(now);

                if (!userVoucherRepository.edit(userVoucher)) {
                    throw new BusinessException(__("订单优惠券信息失败"));
                }
            }
            // end 优惠券使用标记更新


            //使用掉的积分额度
            BigDecimal order_resource_ext1_use_current = BigDecimal.ZERO;
            BigDecimal order_resource_ext1_need_money = BigDecimal.ZERO; //积分不足，使用钱支付的部分。

            // 需要ext1，但是ext1不足， 将ext1变为money使用
            if (CheckUtil.isNotEmpty(orderResourceExt1)) {

                BigDecimal productPointsSel = storeItemVo.getPointsAmount();

                if (orderResourceExt1.compareTo(orderResourceExt1Use) > 0) {
                    order_resource_ext1_use_current = NumberUtil.div(NumberUtil.mul(orderResourceExt1Use, productPointsSel), orderResourceExt1);
                    // 将积分变成钱去支付
                    BigDecimal order_resource_ext1_need = NumberUtil.sub(productPointsSel, order_resource_ext1_use_current);

                    // 将积分换成钱
                    Float points_vaue_rate = configBaseService.getConfig("points_vaue_rate", 0.01f);
                    order_resource_ext1_need_money = NumberUtil.mul(points_vaue_rate, order_resource_ext1_need);
                    BigDecimal order_money_select_items = storeItemVo.getMoneyAmount();

                    storeItemVo.setMoneyAmount(NumberUtil.add(order_money_select_items, NumberUtil.max(BigDecimal.ZERO, order_resource_ext1_need_money)));
                } else {
                    order_resource_ext1_use_current = productPointsSel;
                }
            }

            storeItemVo.setPointsAmount(order_resource_ext1_use_current);

            //积分不足，使用钱支付的部分均分到对应商品上。
            BigDecimal order_money_select_items = storeItemVo.getMoneyItemAmount();
            BigDecimal freight = storeItemVo.getFreightAmount();

            BigDecimal voucherAmount = storeItemVo.getVoucherAmount();

            BigDecimal order_payment_amount = storeItemVo.getMoneyAmount();
            order_payment_amount = NumberUtil.max(order_payment_amount, BigDecimal.valueOf(0));


            // begain 均分积分
            //order_resource_ext1_use_current;

            BigDecimal item_share_points = BigDecimal.valueOf(0);  //SKU占比
            BigDecimal now_tmp_points_total = BigDecimal.valueOf(0); //已分配额度
            BigDecimal tmp_item_points_total = BigDecimal.valueOf(0); //使用积分商品总额

            if (CheckUtil.isNotEmpty(order_resource_ext1_use_current)) {
                List<Long> points_item_ids = new ArrayList<>();

                //涉及商品个数
                int size = 0;

                //取出参与的产品的总值
                for (int idx = 0; idx < itemsList.size(); idx++) {
                    ProductItemVo _item = itemsList.get(idx);

                    if (_item.getItemUnitPoints().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal subtotal_tmp = _item.getItemPointsSubtotal();
                        tmp_item_points_total = NumberUtil.add(tmp_item_points_total, subtotal_tmp);

                        size++;
                    }
                }

                int i = 0;

                for (int idx = 0; idx < itemsList.size(); idx++) {
                    ProductItemVo _item = itemsList.get(idx);

                    if (_item.getItemUnitPoints().compareTo(BigDecimal.ZERO) > 0) {
                        i++;

                        //最后一个商品
                        if (i == size) {
                            item_share_points = NumberUtil.sub(order_resource_ext1_use_current, now_tmp_points_total);
                        } else {
                            item_share_points = NumberUtil.round(NumberUtil.mul(NumberUtil.div(_item.getItemSubtotal(), tmp_item_points_total), order_resource_ext1_use_current), 2);
                            now_tmp_points_total = NumberUtil.add(now_tmp_points_total, item_share_points);
                        }
                    }

                    _item.setItemPointsSubtotal(item_share_points);
                }

                usePoint = true;
            } else {
                usePoint = false;
            }


            // begain 均分积分

            // begain 均分优惠券
            BigDecimal item_share_voucher = BigDecimal.valueOf(0);  //SKU占比
            BigDecimal now_tmp_voucher_total = BigDecimal.valueOf(0); //已分配额度
            BigDecimal tmp_item_total = BigDecimal.valueOf(0); //使用优惠券商品总额

            if (CheckUtil.isNotEmpty(voucherAmount)) {
                List<Long> voucher_item_ids = new ArrayList<>();
                UserVoucher userVoucher = userVoucherRepository.get(storeItemVo.getUserVoucherId());

                if (userVoucher != null && StrUtil.isNotEmpty(userVoucher.getItemId())) {
                    voucher_item_ids = Convert.toList(Long.class, userVoucher.getItemId());
                }

                //涉及商品个数
                int size = 0;

                //取出参与的产品的总值
                for (int idx = 0; idx < itemsList.size(); idx++) {
                    ProductItemVo _item = itemsList.get(idx);

                    if ((CollUtil.isNotEmpty(voucher_item_ids) && voucher_item_ids.contains(_item.getItemId())) || CollUtil.isEmpty(voucher_item_ids)) {
                        BigDecimal subtotal_tmp = Convert.toBigDecimal(_item.getItemSubtotal());
                        tmp_item_total = NumberUtil.add(tmp_item_total, subtotal_tmp);

                        size++;
                    }
                }

                int i = 0;

                for (int idx = 0; idx < itemsList.size(); idx++) {
                    ProductItemVo _item = itemsList.get(idx);

                    if ((CollUtil.isNotEmpty(voucher_item_ids) && voucher_item_ids.contains(_item.getItemId())) || CollUtil.isEmpty(voucher_item_ids)) {
                        i++;

                        //最后一个商品
                        if (i == size) {
                            item_share_voucher = NumberUtil.sub(voucherAmount, now_tmp_voucher_total);
                        } else {
                            item_share_voucher = NumberUtil.round(NumberUtil.mul(NumberUtil.div(_item.getItemSubtotal(), tmp_item_total), voucherAmount), 2);
                            now_tmp_voucher_total = NumberUtil.add(now_tmp_voucher_total, item_share_voucher);
                        }
                        _item.setItemVoucher(item_share_voucher);
                    }

                }
            }
            //end 优惠券均分

            //todo 处理店铺活动优惠

            //todo 满减活动
            /*
            BigDecimal item_share_giftbag = BigDecimal.ZERO;

            //满减均摊
            if (CollUtil.isNotEmpty(reduction)) {
                for (int ri = 0; ri < ddr_len; ri++) {
                    Map value = discount_detail_rows.get(ri);

                    if (Convert.toList(value.get("item_ids")).contains(item.get("item_id"))) {
                        //数组最后一个用减法
                        if ((ri + 1) == itemsList.size()) {
                            item_share_giftbag = NumberUtil.sub(Convert.toBigDecimal(value.get("discount")), reduction_discount);
                            reduction_discount = BigDecimal.ZERO;
                        } else {
                            item_share_giftbag = NumberUtil.mul(NumberUtil.div(Convert.toBigDecimal(value.get("discount")), Convert.toBigDecimal(value.get("old_amount"))), Convert.toBigDecimal(item.get("subtotal")));
                            reduction_discount = NumberUtil.add(reduction_discount, item_share_giftbag);
                        }
                    }
                }
            }

             */


            //1、订单基础表
            OrderBase orderBase = new OrderBase();

            // 订单默认状态
            List<Integer> stateIdList = configBaseService.getStateIdList();
            Integer orderStateId = stateIdList.get(0);

            orderBase.setOrderId(orderId);
            orderBase.setOrderStateId(orderStateId);
            orderBase.setOrderProductAmount(storeItemVo.getProductAmount());
            orderBase.setOrderPaymentAmount(order_payment_amount);

            // 增加汇率
            /*
            Map currency_label_row = shopBaseCurrencyService.getCurrencyLabel();

            orderBase.put("currency_id", currency_label_row.get("currency_id"));
            orderBase.put("currency_symbol_left", currency_label_row.get("currency_symbol_left"));
             */

            // 修改最终下单数据
            orderSelMoneyAmount = NumberUtil.add(orderSelMoneyAmount, order_payment_amount);
            orderSelPointsAmount = NumberUtil.add(orderSelPointsAmount, order_resource_ext1_use_current);
            orderSelSpAmount = NumberUtil.add(orderSelSpAmount, orderResourceExt2);


            // 应付金额/应支付金额:order_goods_amount - order_discount_amount + order_shipping_fee - order_voucher_price - order_points_fee - order_adjust_fee
            // 手工调整默认为0，order_points_fee积分折扣暂未开启

            orderBase.setOrderTime(now);
            orderBase.setStoreId(storeId);
            orderBase.setStoreName(store_name);
            orderBase.setUserId(userId);
            orderBase.setUserNickname(buyerUserNickname);

            // 订单基础信息保存
            boolean flag = orderBaseRepository.save(orderBase);

            Integer delivery_type_id = checkoutRow.getDeliveryTypeId();

            // 2、订单信息保存处理
            if (flag) {
                //String orderTitle = itemsList.stream().map(s -> s.getItemName()).collect(Collectors.joining("|"));
                //String collect = itemsList.stream().map(ProductItemVo::getProductName).collect(Collectors.joining("|"));
                String orderTitle = itemsList.stream().map(s -> s.getProductName()).collect(Collectors.joining("|"));
                orderTitle = orderTitle.substring(0, Math.min(orderTitle.length(), 190));

                Integer subsite_id = 0;
                Boolean store_is_selfsupport = true;
                Integer payment_type_id = checkoutRow.getPaymentTypeId();
                boolean order_is_offline = false;
                Integer salesperson_id = 0;
                Integer distributor_user_id = 0;
                Integer store_type = 1;
                Integer _order_type = checkoutRow.getOrderType();
                String src_order_id = checkoutRow.getSrcOrderId();
                Integer payment_form_id = 1;
                Integer cart_type_id = 1;


                orderInfo.setOrderId(orderId);
                orderInfo.setOrderTitle(orderTitle); // 订单标题
                orderInfo.setStoreId(storeId);  // 卖家店铺编号
                orderInfo.setSubsiteId(subsite_id); // 所属分站
                orderInfo.setUserId(userId); // 买家编号
                orderInfo.setKindId(storeItemVo.getKindId()); // 订单种类(ENUM): 1201-实物 ; 1202-虚拟
                orderInfo.setOrderLockStatus(false);  // 锁定状态(BOOL):0-是正常;1-锁定
                orderInfo.setOrderIsSettlemented(false); // 订单是否结算(BOOL): 1-已结算; 0-未结算

                orderInfo.setOrderBuyerEvaluationStatus(0); // 买家针对订单对店铺评价(ENUM): 0-未评价;1-已评价;  2-已过期未评价
                orderInfo.setOrderSellerEvaluationStatus(0); // 卖家评价状态(ENUM):0-未评价;1-已评价;  2-已过期未评价
                orderInfo.setCreateTime(now.getTime());
                orderInfo.setOrderBuyerHidden(false); // 买家删除(BOOL): 1-是; 0-否
                orderInfo.setOrderShopHidden(false); // 店铺删除(BOOL): 1-是; 0-否
                orderInfo.setPaymentTypeId(payment_type_id); // 支付方式(ENUM):2-到付;1-在线支付
                orderInfo.setOrderStateId(orderStateId);
                orderInfo.setUpdateTime(now.getTime());
                orderInfo.setOrderIsReceived(false); // 订单审核(BOOL):0-未审核;1-已审核;
                orderInfo.setOrderFinanceReview(false); // 财务状态(BOOL):0-未审核;1-已审核
                orderInfo.setOrderIsPaid(StateCode.ORDER_PAID_STATE_NO); // 付款状态(BOOL):0-未付款;6-付款待审核;7-部分付款;1-已付款
                orderInfo.setOrderIsOut(StateCode.ORDER_PICKING_STATE_NO);
                orderInfo.setOrderIsShipped(StateCode.ORDER_SHIPPED_STATE_NO);
                orderInfo.setOrderIsReceived(false); // 收货状态(BOOL):0-未收货;1-已收货
                orderInfo.setChainId(chain_id);
                orderInfo.setDeliveryTypeId(delivery_type_id); // 配送方式
                orderInfo.setOrderIsOffline(order_is_offline); // 线下订单
                orderInfo.setCartTypeId(cart_type_id); // 类型
                orderInfo.setSalespersonId(salesperson_id); // 销售员编号，可以扩展为数组
                orderInfo.setDistributorUserId(distributor_user_id); // 分销商ID distributor_id = storeId
                orderInfo.setStoreIsSelfsupport(store_is_selfsupport);
                orderInfo.setStoreType(store_type);
                orderInfo.setSrcOrderId(src_order_id);
                orderInfo.setOrderType(_order_type);
                /*
                if (ObjectUtil.isNotEmpty(productIndex) && CheckUtil.isNotEmpty(productIndex.getCoupon_typeId())) {
                    orderInfo.setCoupon_typeId(productIndex.getCoupon_typeId());
                }
                */
                //orderInfo.setActivity_json(JSONUtil.toJsonStr(storeItemVo.get("discount_detail_rows")));

                orderInfo.setPaymentFormId(payment_form_id);

                // 店铺活动 - 非排他
                if (ObjectUtil.isNotEmpty(storeItemVo.getActivityBase())) {
                    orderInfo.setActivityId(Convert.toStr(storeItemVo.getActivityBase().getActivityId()));
                    orderInfo.setActivityTypeId(Convert.toStr(storeItemVo.getActivityBase().getActivityTypeId()));
                } else {
                    //单品活动
                    List<Integer> activityIds = CommonUtil.column(itemsList, ProductItemVo::getActivityId);

                    if (CollUtil.isNotEmpty(activityIds)) {
                        List<Integer> activityTypeIds = itemsList.stream().map(s -> {
                            if (ObjectUtil.isNotEmpty(s.getActivityInfo())) {
                                return s.getActivityInfo().getActivityTypeId();
                            }

                            return null;
                        }).distinct().collect(Collectors.toList());

                        orderInfo.setActivityId(StrUtil.join(",", activityIds));
                        orderInfo.setActivityTypeId(StrUtil.join(",", activityTypeIds));
                    }
                }

                // 订单基本info信息保存
                if (!orderInfoRepository.save(orderInfo)) {
                    throw new BusinessException(__("保存订单基础数据失败!"));
                }
            } else {
                throw new BusinessException(__("保存订单基础数据失败!"));
            }
            // end 订单信息保存处理

            if (flag) {
                // 服务类虚拟订单数据
                if (StateCode.PRODUCT_KIND_ENTITY != storeItemVo.getKindId().intValue()) {
                    if (StateCode.PRODUCT_KIND_EDU == storeItemVo.getKindId().intValue()) {
                        // todo 具体业务逻辑， 在支付完成处理。
                    } else {
                        if (storeItemVo.getKindId().equals(StateCode.PRODUCT_KIND_CARD)) {
                            // 卡券类，发送code， 则代表交易完成，类似充话费、虚拟卡号等等
                            // todo 具体业务逻辑， 在支付完成处理。
                        }

                        // 发送虚拟码，并记录客户递交的数据。
                        ChainCode virtual_row = new ChainCode();
                        Long item_id = itemsList.get(0).getItemId();

                        //Date virtual_service_time = Convert.toDate(getParameter("virtual_service_time"));
                        //Date virtual_service_date = Convert.toDate(getParameter("virtual_service_date"));

                        virtual_row.setChainCode("");
                        virtual_row.setOrderId(orderId);
                        virtual_row.setItemId(item_id);
                        virtual_row.setChainCodeStatus(0);
                        //virtual_row.setVirtual_service_date(virtual_service_date);
                        //virtual_row.setVirtual_service_time(virtual_service_time);
                        virtual_row.setChainId(chain_id);
                        virtual_row.setUserId(userId);
                        virtual_row.setStoreId(storeId);

                        if (!chainCodeRepository.save(virtual_row)) {
                            throw new BusinessException(__("保存服务订单数据失败!"));
                        }
                    }
                }

                // 自提码数据
                if (ObjectUtil.equal(StateCode.PRODUCT_KIND_ENTITY, storeItemVo.getKindId()) && ObjectUtil.equal(delivery_type_id, StateCode.DELIVERY_TYPE_SELF_PICK_UP)) {
                    // 发送虚拟码，并记录客户递交的数据。
                    ChainCode chain_code_row = new ChainCode();

                    /*Integer chain_code_status = Convert.toInt(getParameter("chain_code_status"));
                    Date virtual_service_time = Convert.toDate(getParameter("virtual_service_time"));
                    Date virtual_service_date = Convert.toDate(getParameter("virtual_service_date"));*/

                    chain_code_row.setChainCode("");
                    chain_code_row.setOrderId(orderId);
                    //chain_code_row.setItemId(item_id);
                    chain_code_row.setChainCodeStatus(0);
                    //chain_code_row.setVirtual_service_date(virtual_service_date);
                    //chain_code_row.setVirtual_service_time(virtual_service_time);
                    chain_code_row.setChainId(chain_id);
                    chain_code_row.setUserId(userId);
                    chain_code_row.setStoreId(storeId);

                    if (!chainCodeRepository.save(chain_code_row)) {
                        throw new BusinessException(__("保存自提码数据失败!"));
                    }
                }

                // 店铺商品信息item_rows
                for (int i = 0; i < itemsList.size(); i++) {
                    ProductItemVo item = itemsList.get(i);

                    Long cartId = item.getCartId();

                    if (CheckUtil.isNotEmpty(cartId)) {
                        cartIds.add(cartId);
                    }

                    Boolean is_on_sale = item.getIsOnSale();

                    if (is_on_sale) {
                        OrderItem item_row = new OrderItem();

                        item_row.setOrderId(orderId);
                        item_row.setUserId(userId); // 买家user_id  冗余
                        item_row.setStoreId(storeId); // 店铺ID
                        item_row.setProductId(item.getProductId()); // 产品id
                        item_row.setProductName(item.getProductName());
                        item_row.setItemId(item.getItemId()); // 货品id
                        item_row.setItemSrcId(item.getItemSrcId()); // 货品id
                        item_row.setItemName(item.getItemName()); // 商品名称
                        item_row.setOrderItemFile(""); // 订单附件
                        item_row.setCategoryId(item.getCategoryId()); // 商品对应的类目ID

                        item_row.setItemUnitPrice(item.getItemUnitPrice()); // 商品价格单价
                        item_row.setItemUnitPoints(item.getItemUnitPoints()); // 商品价格单价
                        item_row.setItemUnitSp(BigDecimal.valueOf(0)); // 商品价格单价
                        item_row.setOrderItemSalePrice(item.getItemSalePrice()); // 商品实际成交价单价

                        item_row.setItemCostPrice(item.getItemCostPrice()); // 成本价
                        item_row.setOrderItemQuantity(item.getCartQuantity()); // 商品数量
                        item_row.setOrderItemInventoryLock(item.getProductInventoryLock()); // 锁库存时机
                        item_row.setOrderItemImage(item.getProductImage()); // 商品图片
                        item_row.setOrderItemReturnNum(0); // 退货数量
                        item_row.setOrderItemAmount(item.getItemSubtotal()); // 商品实际总金额 =  item_sale_price * goods_quantity
                        item_row.setOrderItemDiscountAmount(item.getItemDiscountAmount()); // 优惠金额  负数
                        item_row.setPolicyDiscountrate(item.getItemPolicyDiscountrate()); // 价格策略折扣率%
                        item_row.setOrderItemVoucher(item.getItemVoucher()); // 均分优惠券
                        //item_row.setOrder_item_redemption_voucher(item_share_redemption); // 均分提货券
                        item_row.setOrderItemConfirmStatus(true);// 默认用户审核

                        /*
                        item_row.setItem_purchase_price(item_purchase_price);
                        item_row.setItem_purchase_rate(item_purchase_rate);
                        item_row.setItem_sales_rate(item_sales_rate);*/

                        // todo 积分费用
                        item_row.setOrderItemAdjustFee(BigDecimal.ZERO); // 手工调整金额 负数

                        item_row.setOrderItemPointsFee(usePoint ? item.getItemPointsSubtotal() : BigDecimal.ZERO); // 积分费用

                        item_row.setOrderItemPaymentAmount(item.getItemSubtotal()); // // 实付金额 : goods_payment_amount =  goods_amount + goods_discount_amount + goods_adjust_fee + goods_point_fee

                        item_row.setOrderItemEvaluationStatus(false); // 评价状态: 0-未评价;1-已评价

                        //活动订单处理
                        if (ObjectUtil.isNotEmpty(item.getActivityId())) {
                            item_row.setActivityTypeId(item.getActivityInfo().getActivityTypeId()); // 活动类型:0-默认;1101-加价购=搭配宝;1102-店铺满赠-小礼品;1103-限时折扣;1104-优惠套装;1105-店铺优惠券coupon优惠券;1106-拼团;1107-满减送;1108-阶梯价
                            item_row.setActivityId(item.getActivityId()); // 促销活动ID:与activity_type_id搭配使用, 团购ID/限时折扣ID/优惠套装ID
                        }

                        item_row.setOrderItemDiscountAmount(item.getItemDiscountAmount()); // 优惠金额  负数


                        // 根据分类获取category_commission_rate
                        if (ObjectUtil.notEqual(item.getKindId(), StateCode.PRODUCT_KIND_EDU)) {
                            if (category_rate_row.get(item.getCategoryId()) != null) {

                            } else {
                                ProductCategory category_row = productCategoryRepository.get(item.getCategoryId());
                                category_rate_row.put(item.getCategoryId(), category_row.getCategoryCommissionRate());
                            }
                        }

                        /*
                        // 1.判断是否为供应商产品

                        // 根据单品计算佣金
                        if (store_tmp_row != null && store_tmp_row.getStore_type() == 2) {
                            // 供应商订单，不分佣。
                            item_row.setOrder_item_commission_rate(BigDecimal.valueOf(0)); // 分佣金比例

                            BigDecimal order_item_payment_amount = item_row.getOrder_item_payment_amount();
                            BigDecimal order_item_commission_rate = item_row.getOrder_item_commission_rate();
                            item_row.setOrder_item_commission_fee(NumberUtil.mul(order_item_payment_amount, NumberUtil.div(order_item_commission_rate, 100))); // 分佣金

                            // 平台价 - 成本结算价 = 分佣额度
                            BigDecimal item_platform_supplier_commission_fee = BigDecimal.valueOf(0);
                            BigDecimal item_fee = NumberUtil.max(BigDecimal.valueOf(0), item_platform_supplier_commission_fee);

                            BigDecimal _item_unit_price = item_row.getItem_unit_price();
                            item_row.setOrder_item_commission_rate(NumberUtil.mul(NumberUtil.div(item_fee, _item_unit_price), 100)); // 分佣金比例
                            item_row.setOrder_item_commission_fee(item_fee); // 分佣金
                        } else {
                        */
                        // 允许分销
                        Boolean productDistEnable = Convert.toBool(item.getProductDistEnable(), false);

                        if (productDistEnable) {
                            // 存在单品平台佣金行为
                            BigDecimal productCommissionRate = item.getProductCommissionRate();
                            BigDecimal orderItemPaymentAmount = item_row.getOrderItemPaymentAmount();

                            if (ObjectUtil.compare(productCommissionRate, BigDecimal.ZERO) > 0) {
                                BigDecimal orderItemCommissionRate = productCommissionRate;
                                item_row.setOrderItemCommissionRate(orderItemCommissionRate); // 分佣金比例
                                item_row.setOrderItemCommissionFee(NumberUtil.div(NumberUtil.mul(orderItemPaymentAmount, orderItemCommissionRate), 100)); // 分佣金
                            } else {
                                BigDecimal orderItemCommissionRate = (BigDecimal) category_rate_row.get(item.getCategoryId());
                                item_row.setOrderItemCommissionRate(orderItemCommissionRate);
                                item_row.setOrderItemCommissionFee(NumberUtil.div(NumberUtil.mul(orderItemPaymentAmount, orderItemCommissionRate), 100)); // 分佣金
                            }
                        } else {
                            item_row.setOrderItemCommissionRate(BigDecimal.valueOf(0));
                            item_row.setOrderItemCommissionFee(BigDecimal.valueOf(0)); // 分佣金
                        }
                        /*
                        }


                        // 单品佣金
                        JSONObject source_item_id = (JSONObject) cartData.get("source_item_id");
                        JSONObject json_item = (JSONObject) source_item_id.get(item_row.getItemId());
                        if (json_item != null) {
                            Integer tmp_user_id = Convert.toInt(json_item.get("u"));
                            item_row.setOrderItemSalerId(tmp_user_id);
                        } else {
                            item_row.setOrderItemSalerId(0);
                        }

                        // 来源订单
                        String src_order_id = Convert.toStr(cartData.get("src_order_id"));
                        if (StrUtil.isNotBlank(src_order_id)) {
                            item_row.setSrc_orderId(src_order_id);
                        } else {
                            item_row.setSrc_orderId("");
                        }

                        if (CollUtil.isEmpty(pulse_packages)) {
                            item_rows.add(item_row);
                        }
                        */

                        // start 判断增加冻结库存
                        if (ObjectUtil.equal(item.getProductInventoryLock(), 1001)) {
                            if (productItemRepository.lockSkuStock(item.getItemId(), item.getCartQuantity()) <= 0) {
                                throw new BusinessException(String.format(__("更改: %s 冻结库存失败!"), item.getItemId()));
                            }
                        }
                        // end

                        item_rows.add(item_row);
                    } else {
                        throw new BusinessException(String.format(__("商品： %s 已经下架，不可下单"), item.getItemName()));
                    }
                }

                for (OrderItem item_row : item_rows) {
                    if (item_row.getOrderItemPaymentAmount().compareTo(BigDecimal.ZERO) < 0) {
                        throw new BusinessException(__("活动价格设置有误"));
                    }
                }

                if (!orderItemRepository.saves(item_rows)) {
                    throw new BusinessException(__("保存订单信息数据失败!"));
                }


                orderData.setOrderId(orderId); // 订单编号
                orderData.setOrderDesc(""); // 订单描述
                orderData.setOrderDelayTime(0); // 延迟时间,默认为0 - 收货确认
                orderData.setDeliveryTypeId(delivery_type_id); // 配送方式

                /*
                //orderData.setDeliveryTimeId(delivery_time_id); // 配送时间:要求，不限、周一~周五、周末等等

                // 配送时间新加
                orderData.setDelivery_time(delivery_time);
                orderData.setDelivery_time_rang(CheckUtil.isNotEmpty(delivery_istimer) ? delivery_time_rang : 0);
                orderData.setDelivery_istimer(delivery_istimer);
                orderData.setBuyer_mobile(buyer_mobile);
                orderData.setOrder_heka(order_heka);
                 */
                String userMessage = "";
                if (CollUtil.isNotEmpty(checkoutRow.getMessage())) {
                    userMessage = ObjectUtil.defaultIfNull(checkoutRow.getMessage().get(storeId), "");
                }
                orderData.setOrderMessage(userMessage); // 买家订单留言
                orderData.setOrderItemAmount(storeItemVo.getMoneyItemAmount()); // 商品总价格/商品金额, 不包含运费
                orderData.setOrderAdjustFee(BigDecimal.valueOf(0)); // 手工调整费用店铺优惠
                orderData.setOrderPointsFee(storeItemVo.getPointsAmount()); // 积分费用

                // 积分抵扣费用 是否采用负数？
                orderData.setOrderDiscountAmount(storeItemVo.getDiscountAmount()); // 折扣价格/优惠总金额
                orderData.setOrderShippingFeeAmount(storeItemVo.getFreightAmount()); // 运费价格/运费金额
                orderData.setOrderShippingFee(storeItemVo.getFreightAmount()); // 实际运费金额-卖家可修改


                orderData.setVoucherId(storeItemVo.getUserVoucherId()); // 优惠券id/优惠券/返现:发放选择使用
                //orderData.setVoucher_number(voucher_code); // 优惠券编码
                orderData.setVoucherPrice(storeItemVo.getVoucherAmount()); // 优惠券面额

                orderData.setOrderResourceExt1(order_resource_ext1_use_current);
                /*
                BigDecimal store_rebate = Convert.toBigDecimal(storeItemVo.get("store_rebate"));


                // todo 红包
                orderData.setRedpacketId(0); // 红包id-平台优惠券
                orderData.setRedpacket_number("0"); // 红包编码
                orderData.setRedpacket_price(BigDecimal.ZERO); // 红包面额
                orderData.setOrder_redpacket_price(BigDecimal.ZERO); // 红包抵扣订单金额

                orderData.setOrder_resource_ext2(order_resource_ext2_use);
                orderData.setOrder_refund_status(0); // 退款状态:0-是无退款;1-是部分退款;2-是全部退款
                orderData.setOrder_return_status(0); // 退货状态(ENUM):0-是无退货;1-是部分退货;2-是全部退货
                orderData.setOrder_return_num(0); // 退货数量
                */

                BigDecimal orderCommissionFee = item_rows.stream().map(s -> s.getOrderItemCommissionFee()).reduce(BigDecimal::add).get();
                orderData.setOrderCommissionFee(orderCommissionFee); // 平台交易佣金

                /*
                orderData.setOrder_commission_fee_refund(BigDecimal.ZERO); // 平台交易佣金-退款
                orderData.setOrder_points_add(BigDecimal.ZERO); // 订单赠送积分
                orderData.setOrder_double_points_add(item_sum_points); // 多倍积分赠送
                orderData.setActivity_double_pointsId(activity_double_points_id);
                orderData.setOrder_bp_add(0); // 订单赠送积分
                orderData.setOrder_rebate(store_rebate);
                orderData.setOrder_promotion_info("0");
                orderData.setOrder_cancel_identity(1);
                orderData.setOrder_cancel_reason("");
                orderData.setOrder_redemption_price(dedu_price);// 订单提货券抵扣金额

                 */


                //如果有满返优惠券，先保存活动ID,付款后发放优惠券。
                /*
                List<Map> manhuis = (List<Map>) ObjectUtil.defaultIfNull(activitys.get("manhui"), new ArrayList());
                if (CollUtil.isNotEmpty(manhuis)) {
                    List<Integer> activity_manhui_id = manhuis.stream().filter(s -> Convert.toBool(s.get("give_enable"))).map(s -> Convert.toInt(s.get("give_id"))).distinct().collect(Collectors.toList());
                    orderData.setActivity_manhuiId(CollUtil.join(activity_manhui_id, ","));
                    orderData.setOrder_activity_manhui_state(StateCode.CHECK_STATE_NO);
                } else {
                    orderData.setOrder_activity_manhui_state(StateCode.CHECK_STATE_NO);
                }
                 */

                if (!orderDataRepository.save(orderData)) {
                    throw new BusinessException(__("保存订单数据失败!"));
                }

                if (CheckUtil.isNotEmpty(checkoutRow.getUserInvoiceId())) {
                    UserInvoice userInvoice = userInvoiceService.get(checkoutRow.getUserInvoiceId());

                    if (userInvoice != null) {

                        OrderInvoice orderInvoice = new OrderInvoice();

                        orderInvoice.setUserId(orderBase.getUserId());
                        orderInvoice.setStoreId(orderBase.getStoreId());
                        orderInvoice.setOrderId(orderId);


                        orderInvoice.setInvoiceTitle(userInvoice.getInvoiceTitle());
                        orderInvoice.setInvoiceCompanyCode(userInvoice.getInvoiceCompanyCode());
                        orderInvoice.setInvoiceIsCompany(userInvoice.getInvoiceIsCompany());
                        orderInvoice.setInvoiceAddress(userInvoice.getInvoiceAddress());
                        orderInvoice.setInvoicePhone(userInvoice.getInvoicePhone());
                        orderInvoice.setInvoiceBankname(userInvoice.getInvoiceBankname());
                        orderInvoice.setInvoiceBankaccount(userInvoice.getInvoiceBankaccount());
                        orderInvoice.setInvoiceContent(orderInfo.getOrderTitle());

                        orderInvoice.setInvoiceType(userInvoice.getInvoiceType());
                        orderInvoice.setInvoiceAmount(orderBase.getOrderPaymentAmount());
                        orderInvoice.setOrderIsPaid(false);
                        orderInvoice.setInvoiceTime(now.getTime());

                        orderInvoice.setInvoiceContactName(userInvoice.getInvoiceContactName());

                        if (!orderInvoiceRepository.save(orderInvoice)) {
                            throw new BusinessException(__("保存发票数据失败!"));
                        }
                    }
                }
            }

            orderIdRow.add(orderId);

            /*
            // 获取店铺主账号
            ShopStoreBase store_base_row = shopStoreBaseService.get((Integer) orderBase.get("storeId"));
            Integer seller_id = store_base_row.getUserId();
            invoicingCustomerBaseService.doStoreAddCustomer((Integer) orderBase.get("storeId"), (Integer) orderBase.get("buyer_user_id"));

            if (CheckUtil.isEmpty(seller_id)) {
                throw new BusinessException(String.format(__("商家信息有误！商家主账号Id: %s"), seller_id));
            }

             */


            //或者通过API
            ConsumeTrade consume_trade_row = new ConsumeTrade();

            Integer subsite_id = 0;

            Integer _chainId = chain_id;
            BigDecimal _order_money_select_items = storeItemVo.getMoneyItemAmount();

            consume_trade_row.setOrderId(orderId);
            consume_trade_row.setBuyerId(userId); // 买家编号

            //买家是否开店
            Integer buyer_store_id = 0;
            consume_trade_row.setBuyerStoreId(buyer_store_id);

            //获取超管用户编号
            UserAdmin userAdmin = userAdminRepository.findOne(new QueryWrapper<UserAdmin>().eq("user_is_superadmin", true));

            consume_trade_row.setSellerId(userAdmin.getUserId()); // 卖家id, 店铺主账号
            consume_trade_row.setStoreId(storeId); // 卖家id
            consume_trade_row.setSubsiteId(subsite_id); // 所属分站
            consume_trade_row.setChainId(_chainId);
            //consume_trade_row.setOrder_stateId(orderInfo.getOrder_stateId()); // 订单状态
            consume_trade_row.setTradeIsPaid(StateCode.ORDER_PAID_STATE_NO); // 未支付
            consume_trade_row.setTradeTypeId(StateCode.TRADE_TYPE_SHOPPING); // 交易类型
            consume_trade_row.setPaymentChannelId(0); // 支付渠道

            consume_trade_row.setTradeModeId(1); // 交易类型:1-担保交易;  2-直接交易
            //consume_trade_row.setCurrencyId((Integer) orderBase.get("currency_id"));
            //consume_trade_row.setCurrency_symbol_left((String) orderBase.get("currency_symbol_left"));
            consume_trade_row.setOrderPaymentAmount(orderBase.getOrderPaymentAmount()); // 总付款额度: trade_payment_amount + trade_payment_money + trade_payment_recharge_card + trade_payment_points
            consume_trade_row.setOrderCommissionFee(orderData.getOrderCommissionFee()); // 平台交易佣金
            consume_trade_row.setTradePaymentAmount(orderBase.getOrderPaymentAmount()); // 实付金额:在线支付金额
            consume_trade_row.setTradePaymentMoney(BigDecimal.valueOf(0)); // 余额支付
            consume_trade_row.setTradePaymentRechargeCard(BigDecimal.valueOf(0)); // 充值卡余额支付
            consume_trade_row.setTradePaymentPoints(BigDecimal.valueOf(0)); // 积分支付
            consume_trade_row.setTradePaymentCredit(BigDecimal.valueOf(0)); // 信用支付
            consume_trade_row.setTradePaymentRedpack(BigDecimal.valueOf(0)); // 红包支付
            consume_trade_row.setTradeDiscount(orderData.getOrderDiscountAmount()); // 折扣优惠
            consume_trade_row.setTradeAmount(orderData.getOrderItemAmount()); // 总额虚拟:trade_order_amount + trade_discount

            consume_trade_row.setTradeTitle(orderInfo.getOrderTitle()); // 标题
            consume_trade_row.setTradeCreateTime(new Date().getTime());


            if (!consumeTradeRepository.save(consume_trade_row)) {
                throw new BusinessException(__("订单支付信息失败!"));
            } else {
                BigDecimal _order_payment_amount = orderBase.getOrderPaymentAmount();

                if (ObjectUtil.compare(_order_payment_amount, BigDecimal.ZERO) <= 0) {
                    // 订单付款状态处理，
                    // 不需要添加收款记录，直接修改订单状态
                    if (!setPaidYes(orderId)) {
                        throw new BusinessException(__("订单支付状态修改失败!"));
                    } else {
                        cartData.setIsPaid(true);
                    }
                }

                if (CollUtil.isNotEmpty(cartIds)) {
                    if (!userCartService.remove(cartIds)) {
                        throw new BusinessException("删除购物车失败");
                    }
                }
                // 添加订单事件
            }
        }

        cartData.setOrderMoneyAmount(orderSelMoneyAmount);
        cartData.setOrderPointsAmount(orderSelPointsAmount);
        cartData.setOrderSpAmount(orderSelSpAmount);


        OrderAddOutput orderAddOutput = BeanUtil.copyProperties(cartData, OrderAddOutput.class);
        orderAddOutput.setOrderIds(orderIdRow);
        orderAddOutput.setGbId(gbId);

        return orderAddOutput;
    }

    /**
     * 是否可以取消
     *
     * @param orderStateId 订单状态
     * @param orderIsPaid
     * @return boolean true-可，false-不可
     */
    private boolean ifCancel(Integer orderStateId, Integer orderIsPaid) {
        List<Integer> orderStates = Arrays.asList(StateCode.ORDER_STATE_WAIT_PAY, StateCode.ORDER_STATE_WAIT_REVIEW, StateCode.ORDER_STATE_WAIT_FINANCE_REVIEW, StateCode.ORDER_STATE_PICKING, StateCode.ORDER_STATE_WAIT_SHIPPING);
        return orderStates.contains(orderStateId) && ObjectUtil.equal(orderIsPaid, StateCode.ORDER_PAID_STATE_NO);
    }


    /**
     * 是否可以退货
     *
     * @param orderStateId 订单状态
     * @param orderIsPaid
     * @return boolean true-可，false-不可
     */
    private boolean ifReturn(Integer orderStateId, Integer orderIsPaid) {
        List<Integer> orderStates = Arrays.asList(StateCode.ORDER_STATE_SHIPPED, StateCode.ORDER_STATE_RECEIVED, StateCode.ORDER_STATE_FINISH);
        return orderStates.contains(orderStateId) && ObjectUtil.equal(orderIsPaid, StateCode.ORDER_PAID_STATE_YES);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean cancel(String orderId, String orderStateNote) {
        boolean flag = false;

        OrderBase orderBase = orderBaseRepository.get(orderId);
        if (orderBase == null) {
            return flag;
        }

        OrderInfo orderInfo = orderInfoRepository.get(orderId);

        //拼团支付，不可取消
        //if (checkPaidFlag && !isCancel(orderInfo.getOrderStateId(), orderInfo.getOrderIsPaid())) {
        if (!isCancel(orderInfo.getOrderStateId(), orderInfo.getOrderIsPaid())) {
            throw new BusinessException(__("无符合取消条件的订单"));
        }

        if (orderBase.getOrderStateId().intValue() != StateCode.ORDER_STATE_CANCEL) {
            flag = editNextState(orderId, orderBase.getOrderStateId(), StateCode.ORDER_STATE_CANCEL, orderStateNote);
            if (!flag) {
                return flag;
            }
        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        QueryWrapper<OrderItem> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("order_id", orderId);
        List<OrderItem> orderItemRows = orderItemRepository.find(itemQueryWrapper);

        //判断是否出库，出库会释放冻结库存，取消出库后的订单，不释放冻结库存
        List<Integer> orderStates = Arrays.asList(StateCode.ORDER_STATE_WAIT_PAY, StateCode.ORDER_STATE_WAIT_REVIEW, StateCode.ORDER_STATE_WAIT_FINANCE_REVIEW, StateCode.ORDER_STATE_PICKING);
        if (orderStates.contains(orderInfo.getOrderStateId())) {
            //部分出库商品数量
            List<StockBillItem> billItems = stockBillItemRepository.find(new QueryWrapper<StockBillItem>().eq("order_id", orderId));
            Map<Long, Integer> billItemQuantityAll = new HashMap<>();

            for (StockBillItem billItem : billItems) {
                if (billItem.getOrderItemId() != null) {
                    Integer quantity = billItemQuantityAll.get(billItem.getItemId());

                    if (quantity != null) {
                        quantity = quantity + billItem.getBillItemQuantity();
                    } else {
                        quantity = billItem.getBillItemQuantity();
                    }

                    billItemQuantityAll.put(billItem.getItemId(), quantity);
                }
            }

            //释放冻结库存
            for (OrderItem orderItem : orderItemRows) {
                // start 释放冻结库存
                Integer orderItemInventoryLock = orderItem.getOrderItemInventoryLock();
                if (ObjectUtil.equal(orderItemInventoryLock, 1001) || (StateCode.ORDER_PAID_STATE_YES == orderInfo.getOrderIsPaid().intValue() && ObjectUtil.equal(orderItemInventoryLock, 1002))) {
                    Integer releaseQuantity = orderItem.getOrderItemQuantity();
                    Integer quantity = billItemQuantityAll.get(orderItem.getItemId());

                    //去掉部分出库商品数量
                    if (quantity != null) {
                        releaseQuantity = releaseQuantity - quantity;

                        //出库未发货，可以注释掉后， 商品数量需要手工入库。
                        ProductEditStockInput input = new ProductEditStockInput();
                        input.setItemId(orderItem.getItemId());
                        input.setItemQuantity(quantity);
                        input.setBillTypeId(StateCode.BILL_TYPE_IN);
                        boolean success = productItemService.editStock(input);
                    }

                    if (releaseQuantity > 0 && productItemRepository.releaseSkuStock(orderItem.getItemId(), releaseQuantity) <= 0) {
                        throw new BusinessException(String.format(__("释放: %s 冻结库存失败!"), orderItem.getItemId()));
                    }
                }
            }
        }

        if (!orderInfo.getOrderIsShipped().equals(StateCode.ORDER_SHIPPED_STATE_NO)) {
            //部分发货
            throw new BusinessException(__("订单部分发货，不可取消，请联系商家！"));
        }

        //todo 判断是否需要退款
        if (!orderInfo.getOrderIsPaid().equals(StateCode.ORDER_PAID_STATE_NO)) {
            OrderReturnInput orderReturnInput = new OrderReturnInput();
            orderReturnInput.setReturnAllFlag(true);
            orderReturnInput.setOrderId(orderId);
            orderReturnInput.setReturnBuyerMessage(orderStateNote);

            orderReturnInput.setUserId(orderBase.getUserId());
            orderReturnInput.setReturnAllFlag(false);
            orderReturnInput.setReturnFlag(StateCode.ORDER_NOT_NEED_RETURN_GOODS);
            orderReturnInput.setReviewFlag(true);

            for (OrderItem orderItem : orderItemRows) {
                OrderReturnItemInputVo returnItemInputVo = new OrderReturnItemInputVo();
                returnItemInputVo.setOrderItemId(orderItem.getOrderItemId());

                //应付金额，去掉代金券额度, 满减。
                BigDecimal orderItemCanRefundAmount = orderItem.getOrderItemCanRefundAmount();
                returnItemInputVo.setReturnRefundAmount(NumberUtil.sub(orderItemCanRefundAmount, orderItem.getOrderItemReturnSubtotal()));
                returnItemInputVo.setReturnItemNum(orderItem.getOrderItemQuantity() - orderItem.getOrderItemReturnNum());

                orderReturnInput.getReturnItems().add(returnItemInputVo);
            }

            String retrunId = orderReturnService.addItem(orderReturnInput);
        }

        //todo 积分退还
        // 积分退还 order_resource_ext1 默认为积分。
        OrderData orderData = orderDataRepository.get(orderId);
        BigDecimal order_resource_ext1 = orderData.getOrderResourceExt1();
        BigDecimal order_resource_ext2 = orderData.getOrderResourceExt2();

        if (ObjectUtil.compare(order_resource_ext1, BigDecimal.ZERO) > 0 && CheckUtil.isNotEmpty(orderBase.getUserId())) {
            String desc = String.format("%s 积分退还，订单号 %s", order_resource_ext1, orderData.getOrderId());

            PointsVo pointsVo = new PointsVo();
            pointsVo.setUserId(orderBase.getUserId());
            pointsVo.setPoints(order_resource_ext1);
            pointsVo.setPointsTypeId(PointsType.POINTS_TYPE_CONSUME_RETRUN);
            pointsVo.setPointsLogDesc(desc);
            pointsVo.setOrderId(orderData.getOrderId());

            if (!userResourceRepository.points(pointsVo)) {
                throw new BusinessException(__("积分操作失败！"));
            }
        }

        // 订单取消优惠券退还
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setOrderId("");
        userVoucher.setVoucherStateId(StateCode.VOUCHER_STATE_UNUSED);
        userVoucherRepository.edit(userVoucher, new QueryWrapper<UserVoucher>().eq("order_id", orderId));

        if (orderInfo.getOrderIsPaid().equals(StateCode.ORDER_PAID_STATE_NO)) {

        }

        //取消发票
        orderInvoiceRepository.remove(new QueryWrapper<OrderInvoice>().eq("order_id", orderId).eq("invoice_status", 0));

        return flag;
    }

    /**
     * 支付完成
     *
     * @param orderId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean setPaidYes(String orderId) {
        boolean flag = false;
        OrderInfo orderInfo = orderInfoRepository.get(orderId);
        OrderBase orderBase = orderBaseRepository.get(orderId);

        if (orderInfo.getOrderStateId() == StateCode.ORDER_STATE_WAIT_PAY && orderInfo.getOrderIsPaid() != StateCode.ORDER_PAID_STATE_YES) {
            //库存是否足够
            List<OrderItem> orderItemList = orderItemRepository.find(new QueryWrapper<OrderItem>().eq("order_id", orderId));
            for (OrderItem item : orderItemList) {
                // start 判断增加冻结库存
                if (ObjectUtil.equal(item.getOrderItemInventoryLock(), 1002)) {
                    if (productItemRepository.lockSkuStock(item.getItemId(), item.getOrderItemQuantity()) <= 0) {
                        String format = String.format(__("更改: %s 冻结库存失败!"), item.getItemId());
                        logger.error(format);
                        //不报错，允许执行，日志记录
                        //throw new BusinessException(String.format(__("更改: %s 冻结库存失败!"), item.getItemId()));

                        //库存不足，走自动退款流程
                        cancel(orderId, String.format(__("%s 库存不足，取消订单!"), item.getItemId()));

                        return false;
                    }
                }
                // end
            }

            //获取订单的下一条状态
            Integer nextOrderStateId = getNextOrderStateId(orderInfo.getOrderStateId());

            flag = editNextState(orderId, orderInfo.getOrderStateId(), nextOrderStateId, "");

            if (!flag) {
                return false;
            }

            //更新支付状态
            OrderInfo infoDate = new OrderInfo();
            infoDate.setOrderId(orderId);
            infoDate.setOrderIsPaid(StateCode.ORDER_PAID_STATE_YES);
            flag = orderInfoRepository.edit(infoDate);


            //更新发票订单支付状态
            QueryWrapper<OrderInvoice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id", orderId);

            OrderInvoice orderInvoice = new OrderInvoice();
            orderInvoice.setOrderIsPaid(true);

            orderInvoiceRepository.edit(orderInvoice, queryWrapper);

            // 读取订单商品，更新销量
            for (OrderItem orderItem : orderItemList) {
                Long productId = orderItem.getProductId();
                Integer orderItemQuantity = orderItem.getOrderItemQuantity();
                ProductIndex productIndex = productIndexRepository.get(productId);
                productIndex.setProductSaleNum(orderItemQuantity + productIndex.getProductSaleNum());
                productIndexRepository.edit(productIndex);
            }

            // 付款成功，对用户进行提醒
            String messageId = "payment-success-reminding";
            Map<String, Object> args = new HashMap<>();
            args.put("order_id", orderId);
            args.put("product_name", orderInfo.getOrderTitle());
            args.put("order_payment_amount", orderBase.getOrderPaymentAmount());
            args.put("order_add_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            messageService.sendNoticeMsg(orderBase.getUserId(), messageId, args);

            // 提醒商家发货
            messageId = "notice-of-delivery";
            args = new HashMap<>();
            args.put("order_id", orderId);
            args.put("date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Integer adminUserId = configBaseService.getConfig("message_notice_user_id", 10001);
            messageService.sendNoticeMsg(adminUserId, messageId, args);

        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        // Todo: 处理支付成功逻辑

        return flag;
    }

    /**
     * 审核订单
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean review(String orderId, String orderStateNote) {
        OrderBase orderBase = orderBaseRepository.get(orderId);

        if (orderBase == null) {
            return false;
        }

        if (orderBase.getOrderStateId().intValue() == StateCode.ORDER_STATE_WAIT_REVIEW) {
            //获取订单的下一条状态
            Integer nextOrderStateId = getNextOrderStateId(orderBase.getOrderStateId());

            Boolean res = editNextState(orderId, orderBase.getOrderStateId(), nextOrderStateId, orderStateNote);

            return res;
        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }
    }

    /**
     * 财务审核
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean finance(String orderId, String orderStateNote) {
        OrderBase orderBase = orderBaseRepository.get(orderId);

        if (orderBase == null) {
            return false;
        }

        if (orderBase.getOrderStateId().intValue() == StateCode.ORDER_STATE_WAIT_FINANCE_REVIEW) {
            //获取订单的下一条状态
            int nextOrderStateId = getNextOrderStateId(orderBase.getOrderStateId());

            return editNextState(orderId, orderBase.getOrderStateId(), nextOrderStateId, orderStateNote);
        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }
    }

    /**
     * 出库审核
     *
     * @param in
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean picking(OrderPickingInput in) {
        checkOrderReturnWaiting(in.getOrderId());

        if (CollectionUtil.isNotEmpty(in.getItems())) {
            in.setPickingFlag(false);
        }

        OrderBase orderBase = orderBaseRepository.get(in.getOrderId());

        if (orderBase == null) {
            return false;
        }

        if (orderBase.getOrderStateId().intValue() == StateCode.ORDER_STATE_PICKING || orderBase.getOrderStateId().intValue() == StateCode.ORDER_STATE_WAIT_SHIPPING) {

            Integer state = doReviewPicking(in);

            if (CheckUtil.isEmpty(state)) {
                return false;
            }

            if (state.intValue() == StateCode.ORDER_PICKING_STATE_YES && orderBase.getOrderStateId().intValue() == StateCode.ORDER_STATE_PICKING) {
                //获取订单的下一条状态
                Integer nextOrderStateId = getNextOrderStateId(orderBase.getOrderStateId());

                return editNextState(in.getOrderId(), orderBase.getOrderStateId(), nextOrderStateId, "");
            } else {

            }
        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return true;
    }

    /**
     * 判断是否有待审核售后订单条件限制
     *
     * @param orderId
     * @return
     */
    public Boolean checkOrderReturnWaiting(String orderId) {
        List<Serializable> listKey = orderReturnRepository.findKey(new QueryWrapper<OrderReturn>().eq("order_id", orderId).eq("return_state_id", StateCode.RETURN_PROCESS_CHECK));

        if (CollUtil.isNotEmpty(listKey)) {
            throw new BusinessException(String.format(__("有待处理的退款或者退货单: %s，请先处理！"), CollUtil.join(listKey, ",")));
        }

        return true;
    }

    /**
     * 是否可以发货
     *
     * @param orderStateId
     * @return
     */
    private boolean ifShipping(Integer orderStateId) {
        return orderStateId.intValue() == StateCode.ORDER_STATE_WAIT_SHIPPING || orderStateId.intValue() == StateCode.ORDER_STATE_PICKING;
    }

    /**
     * 发货
     *
     * @param in
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean shipping(OrderShippingInput in) {
        checkOrderReturnWaiting(in.getOrderId());

        String orderId = in.getOrderId();
        OrderBase orderBase = orderBaseRepository.get(orderId);

        if (orderBase == null) {
            return false;
        }

        if (ifShipping(orderBase.getOrderStateId())) {
            Integer state = doReviewShipping(in);

            if (CheckUtil.isEmpty(state)) {
                return false;
            }

            if (state.intValue() == StateCode.ORDER_SHIPPED_STATE_YES) {
                //获取订单的下一条状态
                Integer nextOrderStateId = getNextOrderStateId(orderBase.getOrderStateId());

                //当前状态可能为待配货，下一个状态为代发货，则直接更改为已发货。
                if (nextOrderStateId.equals(StateCode.ORDER_STATE_WAIT_SHIPPING)) {
                    nextOrderStateId = StateCode.ORDER_STATE_SHIPPED;
                }

                return editNextState(orderId, orderBase.getOrderStateId(), nextOrderStateId, "");
            } else {
            }
        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return true;
    }

    /**
     * 检测是否发货完成
     *
     * @param orderId
     * @return
     */
    @Override
    public Boolean checkShippingComplete(String orderId) {
        OrderInfo orderInfo = orderInfoRepository.get(orderId);

        if (ObjectUtil.isEmpty(orderInfo)) {
            throw new BusinessException(String.format("订单 %s 不存在！", orderId));
        }

        // 检测是否发货完成
        boolean isComplete = true;

        // 物流记录
        List<OrderLogistics> orderLogistics = orderLogisticsRepository.find(new QueryWrapper<OrderLogistics>().eq("order_id", orderId));

        // StockBill
        List<StockBill> stockBills = stockBillRepository.find(new QueryWrapper<StockBill>().eq("order_id", orderId));

        List<String> ids = CommonUtil.column(orderLogistics, OrderLogistics::getStockBillId);

        for (StockBill bill : stockBills) {
            if (!ids.contains(bill.getStockBillId())) {
                // 完成发货信息
                isComplete = false;
                break;
            }
        }

        if (isComplete) {
            // 判断商品是否全部出库

            // 订单商品
            List<OrderItem> orderItems = orderItemRepository.find(new QueryWrapper<OrderItem>().eq("order_id", orderId));

            // 已出库商品
            List<StockBillItem> billItems = stockBillItemRepository.find(new QueryWrapper<StockBillItem>().eq("order_id", orderId));

            Map<Long, PickingItem> billItemQuantityAll = new HashMap<>();

            for (OrderItem orderItem : orderItems) {
                //todo 扣除同意退货数量
                billItemQuantityAll.put(orderItem.getOrderItemId(), new PickingItem(orderItem.getItemId(), orderItem.getOrderItemId(), orderItem.getOrderItemQuantity() - orderItem.getOrderItemReturnAgreeNum(), orderItem.getOrderItemSalePrice(), orderItem.getProductId()));
            }

            for (StockBillItem billItem : billItems) {
                if (billItem.getOrderItemId() != null) {
                    PickingItem pickingItem = billItemQuantityAll.get(billItem.getOrderItemId());
                    if (pickingItem != null) {
                        pickingItem.setBillItemQuantity(pickingItem.getBillItemQuantity() - billItem.getBillItemQuantity());
                    } else {
                        throw new BusinessException(String.format("出库数据有误 '%s'", billItem.getOrderItemId()));
                    }
                }
            }

            if (!billItemQuantityAll.isEmpty()) {
                for (PickingItem pickingItem : billItemQuantityAll.values()) {
                    if (pickingItem.getBillItemQuantity() > 0) {
                        isComplete = false;
                        break;
                    }
                }

                if (isComplete) {
                    int state = StateCode.ORDER_SHIPPED_STATE_YES;

                    if (orderInfo.getOrderIsShipped().intValue() != state) {
                        orderInfo.setOrderIsShipped(state);
                        orderInfoRepository.edit(orderInfo);

                        //获取订单的下一条状态
                        int nextOrderStateId = getNextOrderStateId(orderInfo.getOrderStateId());
                        editNextState(orderId, orderInfo.getOrderStateId(), nextOrderStateId, "");
                    }
                }
            }
        }

        return isComplete;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addLogistics(OrderLogistics in) {
        Boolean flag = saveLogistics(in);

        checkShippingComplete(in.getOrderId());

        return flag;
    }

    @Override
    public Boolean saveLogistics(OrderLogistics in) {
        Integer logisticsId = in.getLogisticsId();
        Integer ssId = in.getSsId();

        StoreExpressLogistics storeExpressLogistics = storeExpressLogisticsRepository.get(logisticsId);
        StoreShippingAddress storeShippingAddress = storeShippingAddressRepository.get(ssId);

        in.setExpressName(storeExpressLogistics.getExpressName());
        in.setExpressId(storeExpressLogistics.getExpressId());

        in.setLogisticsPhone(storeShippingAddress.getSsIntl() + storeShippingAddress.getSsMobile());
        in.setLogisticsMobile(storeShippingAddress.getSsIntl() + storeShippingAddress.getSsMobile());
        in.setLogisticsContacter(storeShippingAddress.getSsContacter());
        in.setLogisticsAddress(storeShippingAddress.getSsAddress());
        in.setLogisticsPostcode(storeShippingAddress.getSsPostalcode());

        Boolean flag = orderLogisticsRepository.save(in);

        return flag;
    }

    /**
     * 确认收货
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean receive(String orderId, String orderStateNote) {
        OrderBase orderBase = orderBaseRepository.get(orderId);

        if (orderBase == null) {
            return false;
        }

        if (orderBase.getOrderStateId().intValue() == StateCode.ORDER_STATE_SHIPPED) {
            //获取订单的下一条状态
            int nextOrderStateId = getNextOrderStateId(orderBase.getOrderStateId());

            return editNextState(orderId, orderBase.getOrderStateId(), nextOrderStateId, orderStateNote);
        } else {
            throw new BusinessException("未更改到符合条件的订单！");
        }
    }

    // getNextOrderStateId 读取启用配置，根据当前orderStateId获得下一状态 sc_order_process
    private Integer getNextOrderStateId(Integer orderStateId) {
        return configBaseService.getNextOrderStateId(orderStateId);
    }

    /**
     * 添加订单日志
     *
     * @param log
     * @return
     */
    private boolean addOrderStateLog(OrderStateLog log) {
        return orderStateLogRepository.add(log);
    }


    /**
     * 修改订单为下一个待处理状态
     *
     * @param orderId
     * @param orderStateId
     * @param nextOrderStateId
     * @param orderStateNote
     * @return
     */
    @Override
    public Boolean editNextState(String orderId, Integer orderStateId, Integer nextOrderStateId, String orderStateNote) {
        //下一个状态存在
        if (nextOrderStateId.intValue() != StateCode.ORDER_STATE_CANCEL) {
            List<Integer> stateIdList = configBaseService.initOrderProcess();

            if (!stateIdList.contains(nextOrderStateId)) {
                throw new BusinessException("订单下个状态不符合配置要求！");
            }
        }

        //必须更新到记录
        OrderBase orderBase = new OrderBase();
        orderBase.setOrderId(orderId);
        orderBase.setOrderStateId(nextOrderStateId);
        boolean result = orderBaseRepository.edit(orderBase, new QueryWrapper<OrderBase>().eq("order_id", orderId).eq("order_state_id", orderStateId));

        if (!result) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        //订单信息更改
        OrderInfo oldInfo = new OrderInfo();
        oldInfo.setOrderId(orderId);
        oldInfo.setOrderStateId(orderStateId);

        OrderInfo newInfo = orderInfoRepository.get(orderId);
        newInfo.setOrderStateId(nextOrderStateId);

        if (nextOrderStateId.intValue() != StateCode.ORDER_STATE_CANCEL) {
            switch (orderStateId) {
                case StateCode.ORDER_STATE_WAIT_PAY:
                    //newInfo.OrderIsPaid = true //放入支付回调更改
                    break;
                case StateCode.ORDER_STATE_WAIT_REVIEW:
                    oldInfo.setOrderIsReview(false);
                    newInfo.setOrderIsReview(true);
                    break;
                case StateCode.ORDER_STATE_WAIT_FINANCE_REVIEW:
                    oldInfo.setOrderFinanceReview(false);
                    newInfo.setOrderFinanceReview(true);
                    break;
                case StateCode.ORDER_STATE_PICKING:
                    newInfo.setOrderIsOut(StateCode.ORDER_PICKING_STATE_YES);
                    break;
                case StateCode.ORDER_STATE_WAIT_SHIPPING:
                    newInfo.setOrderIsShipped(StateCode.ORDER_SHIPPED_STATE_YES);
                    break;
                case StateCode.ORDER_STATE_SHIPPED:
                    newInfo.setOrderIsReceived(true);
                    newInfo.setOrderReceivedTime(new Date());
                    break;
                default:
                    break;
            }
        }

        //必须更新到记录
        result = orderInfoRepository.edit(newInfo);
        if (!result) {
            throw new BusinessException("未更改到符合条件的订单！");
        }


        Integer userId = 0;
        String userAccount = "";
        ContextUser user = ContextUtil.getLoginUser();

        if (ObjectUtil.isNotEmpty(user)) {
            userId = user.getUserId();
            userAccount = user.getUserAccount();
        }

        OrderStateLog log = new OrderStateLog();
        log.setOrderId(orderId);
        log.setOrderStateId(nextOrderStateId);
        log.setOrderStatePreId(orderStateId);
        log.setUserId(userId);
        log.setUserAccount(userAccount);
        log.setOrderStateNote(orderStateNote);
        log.setOrderStateTime(new Date());

        result = addOrderStateLog(log);

        return result;
    }


    /**
     * 出库审核 - 逻辑封装 - 涉及进销存
     *
     * @param in
     * @return
     */
    @Override
    public Integer doReviewPicking(OrderPickingInput in) {
        //清理数据
        if (CollUtil.isNotEmpty(in.getItems())) {
            Iterator<PickingItem> iterator = in.getItems().iterator();
            while (iterator.hasNext()) {
                PickingItem pickingItem = iterator.next();

                if (pickingItem.getBillItemQuantity() <= 0) {
                    iterator.remove();
                }
            }
        }

        int state = 0;

        //订单商品
        List<OrderItem> orderItems = orderItemRepository.find(new QueryWrapper<OrderItem>().eq("order_id", in.getOrderId()));
        if (orderItems == null) {
            return state;
        }

        //已出库商品
        List<StockBillItem> billItems = stockBillItemRepository.find(new QueryWrapper<StockBillItem>().eq("order_id", in.getOrderId()));


        //已同意退货商品

        //差量商品
        Map<Long, PickingItem> billItemQuantityAll = new HashMap<>();
        Map<Long, PickingItem> billItemWaiting = new HashMap<>();

        for (OrderItem orderItem : orderItems) {
            //todo 扣除同意退货数量
            billItemQuantityAll.put(orderItem.getOrderItemId(), new PickingItem(orderItem.getItemId(), orderItem.getOrderItemId(), orderItem.getOrderItemQuantity() - orderItem.getOrderItemReturnAgreeNum(), orderItem.getOrderItemSalePrice(), orderItem.getProductId()));
        }

        for (StockBillItem billItem : billItems) {
            if (billItem.getOrderItemId() != null) {
                PickingItem value = billItemQuantityAll.get(billItem.getOrderItemId());
                if (value != null) {
                    value.setBillItemQuantity(value.getBillItemQuantity() - billItem.getBillItemQuantity());
                } else {
                    throw new BusinessException(String.format("出库数据有误 '%s'", billItem.getOrderItemId()));
                }
            }
        }

        if (in.getPickingFlag()) {
            billItemWaiting.putAll(billItemQuantityAll);
        } else {
            //todo 指定的出库，需要判断是否符合billItemQuantityAll中的要求。
            for (PickingItem item : in.getItems()) {
                if (item.getBillItemQuantity() > 0) {
                    billItemWaiting.put(item.getOrderItemId(), new PickingItem(item.getItemId(), item.getOrderItemId(), item.getBillItemQuantity(), item.getBillItemPrice(), item.getProductId()));
                }
            }
        }

        if (billItemWaiting.isEmpty()) {
            throw new BusinessException(String.format("无待出库出库数据: %s", in.getOrderId()));
        }

        //商品库存

        //出库单
        Date now = new Date();
        ContextUser user = ContextUtil.getLoginUser();

        String stockBillId = numberSeqService.getNextSeqString("OUT");

        StockBill stockBill = new StockBill();
        stockBill.setStockBillId(stockBillId);
        stockBill.setStockBillChecked(true);
        stockBill.setStockBillDate(now);
        stockBill.setStockBillModifyTime(now);
        stockBill.setStockBillTime(now.getTime());
        stockBill.setBillTypeId(in.getBillTypeId());
        stockBill.setStockTransportTypeId(in.getStockTransportTypeId());
        stockBill.setStoreId(0);
        stockBill.setWarehouseId(0);
        stockBill.setOrderId(in.getOrderId());
        stockBill.setStockBillRemark("");
        stockBill.setEmployeeId(user.getUserId());
        stockBill.setAdminId(user.getUserId());
        stockBill.setStockBillOtherMoney(BigDecimal.ZERO);
        stockBill.setStockBillAmount(BigDecimal.ZERO);  // 订单金额
        stockBill.setStockBillEnable(true);  // 是否有效(BOOL):1-有效; 0-无效
        stockBill.setStockBillSrcId(""); // 关联编号

        //单据金额
        BigDecimal stockBillAmount = BigDecimal.ZERO;

        for (PickingItem pickingItem : billItemWaiting.values()) {
            //单据商品小计
            BigDecimal billItemSubtotal = pickingItem.getBillItemPrice().multiply(BigDecimal.valueOf(pickingItem.getBillItemQuantity()));

            StockBillItem stockBillItem = new StockBillItem();
            stockBillItem.setStockBillId(stockBillId);
            stockBillItem.setOrderId(in.getOrderId());
            stockBillItem.setOrderItemId(pickingItem.getOrderItemId());
            stockBillItem.setItemId(pickingItem.getItemId());
            stockBillItem.setBillItemQuantity(pickingItem.getBillItemQuantity());
            stockBillItem.setBillItemUnitPrice(pickingItem.getBillItemPrice());
            stockBillItem.setBillItemSubtotal(billItemSubtotal);
            stockBillItem.setProductId(pickingItem.getProductId());
            stockBillItem.setWarehouseId(stockBill.getWarehouseId());
            stockBillItem.setStockTransportTypeId(stockBill.getStockTransportTypeId());
            stockBillItem.setBillTypeId(stockBill.getBillTypeId());
            stockBillItem.setStoreId(0);

            OrderItem orderItem = orderItemRepository.get(stockBillItem.getOrderItemId());

            if (orderItem != null) {
                stockBillItem.setProductName(orderItem.getProductName());
                stockBillItem.setItemName(orderItem.getItemName());
            }

            stockBillItemRepository.add(stockBillItem);

            // 释放冻结库存
            if (productItemRepository.pickingSkuStock(pickingItem.getItemId(), pickingItem.getBillItemQuantity()) <= 0) {
                throw new BusinessException(String.format(__("扣减: %s 库存失败!"), pickingItem.getItemId()));
            }

            //
            PickingItem quantityAllItem = billItemQuantityAll.get(pickingItem.getOrderItemId());
            if (quantityAllItem != null) {
                quantityAllItem.setBillItemQuantity(quantityAllItem.getBillItemQuantity() - pickingItem.getBillItemQuantity());
            }

            stockBillAmount = stockBillAmount.add(billItemSubtotal);
        }

        stockBill.setStockBillAmount(stockBillAmount);
        stockBillRepository.add(stockBill);

        //判断是否已经全部出库， 需要修改订单状态
        state = StateCode.ORDER_PICKING_STATE_YES;

        for (PickingItem pickingItem : billItemQuantityAll.values()) {
            if (pickingItem.getBillItemQuantity() > 0) {
                state = StateCode.ORDER_PICKING_STATE_PART;
                break;
            }
        }

        OrderInfo orderInfo = orderInfoRepository.get(in.getOrderId());
        orderInfo.setOrderIsOut(state);
        orderInfoRepository.edit(orderInfo);

        return state;
    }

    /**
     * 发货审核  - 涉及快递单号处理
     *
     * @param in
     * @return
     */
    @Override
    public Integer doReviewShipping(OrderShippingInput in) {
        int state = 0;

        //如果为出库状态
        OrderInfo orderInfo = orderInfoRepository.get(in.getOrderId());
        if (orderInfo.getOrderIsOut().intValue() != StateCode.ORDER_PICKING_STATE_YES) {
            OrderPickingInput pickingInput = new OrderPickingInput();
            pickingInput.setOrderId(in.getOrderId());
            pickingInput.setPickingFlag(true);
            doReviewPicking(pickingInput);
        }

        //发货
        //出库单无对应发货信息的，完成发货操作
        //物流记录
        List<OrderLogistics> orderLogistics = orderLogisticsRepository.find(new QueryWrapper<OrderLogistics>().eq("order_id", in.getOrderId()));
        if (orderLogistics == null) {
            return state;
        }

        //StockBill
        List<StockBill> stockBills = stockBillRepository.find(new QueryWrapper<StockBill>().eq("order_id", in.getOrderId()));
        if (stockBills == null) {
            return state;
        }

        List<String> ids = CommonUtil.column(orderLogistics, OrderLogistics::getStockBillId);
        //List<String> ids = orderLogistics.stream().map(OrderLogistics::getStockBillId).collect(Collectors.toList());

        for (StockBill bill : stockBills) {
            if (!ids.contains(bill.getStockBillId())) {
                //完成发货信息
                OrderLogistics newLogistics = new OrderLogistics();
                newLogistics.setStockBillId(bill.getStockBillId());

                BeanUtils.copyProperties(in, newLogistics);

                Boolean flag = saveLogistics(newLogistics);
                //Boolean flag = orderLogisticsRepository.add(newLogistics);

                if (!flag) {
                    throw new BusinessException(__("发货信息错误"));
                }
            }
        }

        state = StateCode.ORDER_SHIPPED_STATE_YES;
        boolean flag = orderInfoRepository.edit(new OrderInfo().setOrderId(in.getOrderId()).setOrderIsShipped(state));

        StoreExpressLogistics expressLogistics = storeExpressLogisticsRepository.get(in.getLogisticsId());

        if (orderLogistics == null) {
            // 发货通知
            String messageId = "order_complete_shipping";
            Map<String, Object> args = new HashMap<>();
            args.put("order_id", in.getOrderId());
            args.put("logistics_name", expressLogistics.getExpressName());
            args.put("order_tracking_number", orderLogistics.get(0).getOrderTrackingNumber());
            messageService.sendNoticeMsg(orderInfo.getUserId(), messageId, args);
        }

        return state;
    }

    /**
     * 审核订单到某个状态
     *
     * @param orderId
     * @param toOrderStateId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean reviewToState(String orderId, Integer toOrderStateId) {
        checkOrderReturnWaiting(orderId);

        Boolean flag = true;

        try {
            if (toOrderStateId.intValue() == StateCode.ORDER_STATE_CANCEL) {
                return cancel(orderId, "");
            } else {
                int tryCount = 0;
                while (tryCount < 10) {
                    //读取订单
                    OrderBase orderBase = orderBaseRepository.get(orderId);

                    //订单已经为目标状态
                    if (orderBase.getOrderStateId().intValue() == toOrderStateId) {
                        return true;
                        //break;
                    }

                    //获取订单的下一条状态
                    int nextOrderStateId = getNextOrderStateId(orderBase.getOrderStateId());

                    if (nextOrderStateId == StateCode.ORDER_STATE_WAIT_SHIPPING) {
                        OrderPickingInput input = new OrderPickingInput().setOrderId(orderId).setPickingFlag(true);
                        flag = picking(input);
                    } else if (nextOrderStateId == StateCode.ORDER_STATE_SHIPPED) {
                        OrderShippingInput input = new OrderShippingInput().setOrderId(orderId);
                        flag = shipping(input);
                    } else {
                        flag = editNextState(orderId, orderBase.getOrderStateId(), nextOrderStateId, "");
                    }

                    tryCount++;
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return flag;
    }

    /**
     * 订单是否可以取消
     *
     * @param orderStateId
     * @param orderIsPaid  支付后不给取消
     * @return
     */
    private boolean isCancel(Integer orderStateId, Integer orderIsPaid) {
        List<Integer> orderStates = Arrays.asList(StateCode.ORDER_STATE_WAIT_PAY, StateCode.ORDER_STATE_WAIT_REVIEW, StateCode.ORDER_STATE_WAIT_FINANCE_REVIEW, StateCode.ORDER_STATE_PICKING, StateCode.ORDER_STATE_WAIT_SHIPPING);
        //return orderStates.contains(orderStateId) && ObjectUtil.equal(orderIsPaid, StateCode.ORDER_PAID_STATE_NO);
        return orderStates.contains(orderStateId);
    }


    /**
     * 根据用户id获取用户订单统计信息
     *
     * @param userId
     * @return
     */
    @Override
    public OrderNumOutput getOrderStatisticsInfo(Integer userId) {
        OrderNumOutput orderNumOutput = new OrderNumOutput();

        // 全部订单
        // 完成订单数
        CompletableFuture<Void> infoFuture1 = CompletableFuture.runAsync(() -> {
            //已完成
            OrderNumInput orderNumInput = new OrderNumInput();
            orderNumInput.setUserId(userId);
            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_FINISH);
            orderNumInput.setKindId(StateCode.PRODUCT_KIND_ENTITY);
            orderNumOutput.setFinNumEntity(getOrderNum(orderNumInput));

            orderNumInput.setKindId(StateCode.PRODUCT_KIND_FUWU);
            orderNumOutput.setFinNumV(getOrderNum(orderNumInput));

            // 取消订单数
            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_CANCEL);
            orderNumInput.setKindId(StateCode.PRODUCT_KIND_ENTITY);
            orderNumOutput.setCancelNumEntity(getOrderNum(orderNumInput));

            orderNumInput.setKindId(StateCode.PRODUCT_KIND_FUWU);
            orderNumOutput.setCancelNumV(getOrderNum(orderNumInput));
        }, executor);


        CompletableFuture<Void> infoFuture2 = CompletableFuture.runAsync(() -> {
            // 待发货货订单数
            OrderNumInput orderNumInput = new OrderNumInput();
            orderNumInput.setUserId(userId);
            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_PICKING);
            orderNumInput.setKindId(StateCode.PRODUCT_KIND_ENTITY);
            Long orderPickingNum = getOrderNum(orderNumInput);

            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_WAIT_SHIPPING);
            Long orderShippingNum = getOrderNum(orderNumInput);
            orderNumOutput.setWaitShippingNumEntity(orderPickingNum + orderShippingNum);


            orderNumInput.setKindId(StateCode.PRODUCT_KIND_FUWU);
            orderPickingNum = getOrderNum(orderNumInput);

            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_WAIT_SHIPPING);
            orderNumInput.setKindId(StateCode.PRODUCT_KIND_FUWU);
            orderShippingNum = getOrderNum(orderNumInput);
            orderNumOutput.setWaitShippingNumV(orderPickingNum + orderShippingNum);


            // 已发货货订单数
            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_SHIPPED);
            orderNumInput.setKindId(StateCode.PRODUCT_KIND_ENTITY);
            orderNumOutput.setShipNumEntity(getOrderNum(orderNumInput));

            orderNumInput.setKindId(StateCode.PRODUCT_KIND_FUWU);
            orderNumOutput.setShipNumV(getOrderNum(orderNumInput));

        }, executor);

        CompletableFuture<Void> infoFuture3 = CompletableFuture.runAsync(() -> {
            // 等待支付订单数
            OrderNumInput orderNumInput = new OrderNumInput();
            orderNumInput.setUserId(userId);
            orderNumInput.setOrderStateId(StateCode.ORDER_STATE_WAIT_PAY);
            orderNumInput.setKindId(StateCode.PRODUCT_KIND_ENTITY);
            orderNumOutput.setWaitPayNumEntity(getOrderNum(orderNumInput));

            orderNumInput.setKindId(StateCode.PRODUCT_KIND_FUWU);
            orderNumOutput.setWaitPayNumV(getOrderNum(orderNumInput));

            QueryWrapper<OrderReturn> returnQueryWrapper = new QueryWrapper<>();
            returnQueryWrapper.in("return_state_id", StateCode.RETURN_PROCESS_SUBMIT, StateCode.RETURN_PROCESS_CHECK, StateCode.RETURN_PROCESS_RECEIVED, StateCode.RETURN_PROCESS_REFUND).eq("buyer_user_id", userId);

            Long returningNum = orderReturnRepository.count(returnQueryWrapper);
            orderNumOutput.setReturningNum(returningNum);
        }, executor);

        try {
            CompletableFuture.allOf(infoFuture1, infoFuture2, infoFuture3).get();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return orderNumOutput;
    }

    /**
     * 订单数量
     */
    public Long getOrderNum(OrderNumInput in) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(in.getOrderStateId())) {
            queryWrapper.eq("order_state_id", in.getOrderStateId());
        }

        if (ObjectUtil.isNotEmpty(in.getUserId())) {
            queryWrapper.eq("user_id", in.getUserId());
        }

        if (ObjectUtil.isNotEmpty(in.getKindId())) {
            queryWrapper.eq("kind_id", in.getKindId());
        }

        if (ObjectUtil.isNotEmpty(in.getOrderStime())) {
            queryWrapper.ge("create_time", in.getOrderStime());
        }

        if (ObjectUtil.isNotEmpty(in.getOrderEtime())) {
            queryWrapper.le("create_time", in.getOrderEtime());
        }

        return orderInfoRepository.count(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean doUpdateOrders(List<String> orderIds) {
        if (CollectionUtil.isEmpty(orderIds)) {
            throw new BusinessException(__("订单编号为空！"));
        }
        List<OrderInfo> orderInfos = new ArrayList<>();
        for (String orderId : orderIds) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo.setOrderWithdrawConfirm(true);
            orderInfos.add(orderInfo);
        }

        return orderInfoRepository.edit(orderInfos);
    }

    @Override
    @Transactional
    public void autoCancelOrder() {
        // 更新未付款订单，取消超时订单
        List<String> orderIds = orderInfoService.getAutoCancelOrderId();
        for (String orderId : orderIds) {
            cancel(orderId, "系统自动关单");
        }
    }

    @Override
    @Transactional
    public void autoReceive() {
        // 更新为确认收货
        List<String> order_id_receipt = orderInfoService.getAutoFinishOrderId();
        if (CollUtil.isNotEmpty(order_id_receipt)) {
            for (String order_id : order_id_receipt) {
                receive(order_id, __("系统自动收货"));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addOrderInvoice(OrderInvoiceAddReq orderInvoiceAddReq) {
        OrderBase orderBase = orderBaseRepository.get(orderInvoiceAddReq.getOrderId());

        if (orderBase == null) {
            throw new BusinessException(__("订单信息不存在！"));
        }
        OrderInfo orderInfo = orderInfoRepository.get(orderInvoiceAddReq.getOrderId());

        if (orderInfo == null) {
            throw new BusinessException(__("订单详细信息不存在！"));
        }

        UserInvoice userInvoice = userInvoiceService.get(orderInvoiceAddReq.getUserInvoiceId());

        if (userInvoice == null) {
            throw new BusinessException(__("用户发票不存在！"));
        }

        OrderInvoice orderInvoice = new OrderInvoice();
        BeanUtils.copyProperties(orderBase, orderInvoice);
        BeanUtils.copyProperties(userInvoice, orderInvoice);
        orderInvoice.setInvoiceContent(orderInfo.getOrderTitle());
        orderInvoice.setInvoiceAmount(orderBase.getOrderPaymentAmount());
        orderInvoice.setOrderIsPaid(true);
        orderInvoice.setInvoiceStatus(false);
        orderInvoice.setInvoiceTime(new Date().getTime());

        if (!orderInvoiceRepository.save(orderInvoice)) {
            throw new BusinessException(__("保存发票数据失败!"));
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderAddOutput replaceAdd(CheckoutInput orderBase) {
        UserInfo userInfo = userInfoRepository.get(orderBase.getUserId());

        if (userInfo == null) {
            throw new BusinessException(__("买家用户不存在!"));
        }
        //用户名称
        orderBase.setUserNickname(userInfo.getUserNickname());

        return add(orderBase);
    }
}
