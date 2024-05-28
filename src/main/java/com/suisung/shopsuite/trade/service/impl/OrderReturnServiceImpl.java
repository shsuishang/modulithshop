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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pay.service.ConsumeReturnService;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.shop.model.entity.StoreShippingAddress;
import com.suisung.shopsuite.shop.repository.StoreShippingAddressRepository;
import com.suisung.shopsuite.shop.service.StoreBaseService;
import com.suisung.shopsuite.sys.model.entity.ExpressBase;
import com.suisung.shopsuite.sys.repository.ExpressBaseRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.NumberSeqService;
import com.suisung.shopsuite.trade.model.entity.*;
import com.suisung.shopsuite.trade.model.input.OrderReturnInput;
import com.suisung.shopsuite.trade.model.req.OrderReturnListReq;
import com.suisung.shopsuite.trade.model.res.OrderReturnRes;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemInputVo;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemVo;
import com.suisung.shopsuite.trade.model.vo.OrderReturnVo;
import com.suisung.shopsuite.trade.repository.*;
import com.suisung.shopsuite.trade.service.OrderReturnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Service
public class OrderReturnServiceImpl extends BaseServiceImpl<OrderReturnRepository, OrderReturn, OrderReturnListReq> implements OrderReturnService {

    @Autowired
    private OrderReturnRepository returnRepository;

    @Autowired
    private OrderReturnItemRepository returnItemRepository;

    @Autowired
    private OrderReturnItemRepository orderReturnItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private OrderDeliveryAddressRepository orderDeliveryAddressRepository;

    @Autowired
    private StoreShippingAddressRepository storeShippingAddressRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private OrderDataRepository orderDataRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderBaseRepository orderBaseRepository;

    @Autowired
    private NumberSeqService numberSeqService;

    @Autowired
    private OrderReturnReasonRepository orderReturnReasonRepository;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private ConsumeReturnService consumeReturnService;

    @Autowired
    private ExpressBaseRepository expressBaseRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public OrderReturnVo getByReturnId(String returnId) {
        OrderReturnVo orderReturnVo = new OrderReturnVo();
        //商品信息
        OrderReturn orderReturn = get(returnId);
        if (orderReturn == null) {
            return orderReturnVo;
        }
        BeanUtils.copyProperties(orderReturn, orderReturnVo);
        //客户名称
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("user_id", orderReturn.getBuyerUserId());
        List<UserInfo> userInfos = userInfoRepository.find(userInfoQueryWrapper);

        if (CollectionUtil.isNotEmpty(userInfos)) {
            orderReturnVo.setBuyerUserName(userInfos.get(0).getUserNickname());
        }

        List<OrderReturnItemVo> orderReturnItemVos = new ArrayList<>();
        //查询订单退货详情表
        QueryWrapper<OrderReturnItem> orderReturnItemQueryWrapper = new QueryWrapper<>();
        orderReturnItemQueryWrapper.eq("return_id", orderReturn.getReturnId());
        List<OrderReturnItem> orderReturnItems = orderReturnItemRepository.find(orderReturnItemQueryWrapper);
        if (CollectionUtil.isNotEmpty(orderReturnItems)) {
            for (OrderReturnItem orderReturnItem : orderReturnItems) {
                OrderReturnItemVo orderReturnItemVo = new OrderReturnItemVo();
                BeanUtils.copyProperties(orderReturnItem, orderReturnItemVo);
                if (StrUtil.isNotEmpty(orderReturnItem.getReturnItemImage())) {
                    orderReturnItemVo.setReturnItemImageList(Convert.toList(String.class, orderReturnItem.getReturnItemImage()));
                }
                orderReturnItemVos.add(orderReturnItemVo);
            }
        }
        //订单商品名称、单价
        if (CollectionUtil.isNotEmpty(orderReturnItemVos)) {
            List<Long> orderItemIds = orderReturnItemVos.stream().map(OrderReturnItemVo::getOrderItemId).distinct().collect(Collectors.toList());
            List<OrderItem> orderItemList = orderItemRepository.gets(orderItemIds);

            if (CollectionUtil.isNotEmpty(orderItemList)) {
                Map<Long, OrderItem> orderItemMap = orderItemList.stream().collect(Collectors.toMap(OrderItem::getOrderItemId, OrderItem -> OrderItem));
                for (OrderReturnItemVo orderReturnItemVo : orderReturnItemVos) {

                    if (CollUtil.isNotEmpty(orderItemMap)) {
                        OrderItem orderItem = orderItemMap.get(orderReturnItemVo.getOrderItemId());

                        if (orderItem != null) {
                            String productName = orderItem.getProductName();
                            String itemName = orderItem.getItemName();
                            orderReturnItemVo.setProductItemName(productName + " " + itemName);
                            orderReturnItemVo.setItemUnitPrice(orderItem.getItemUnitPrice());
                        }
                    }
                }
            }
        }
        orderReturnVo.setReturnItemList(orderReturnItemVos);
        //收货信息
        QueryWrapper<OrderDeliveryAddress> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.eq("order_id", orderReturn.getOrderId());
        List<OrderDeliveryAddress> orderDeliveryAddresses = orderDeliveryAddressRepository.find(addressQueryWrapper);

        if (CollectionUtil.isNotEmpty(orderDeliveryAddresses)) {
            BeanUtils.copyProperties(orderDeliveryAddresses.get(0), orderReturnVo);
        }

        return orderReturnVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean refused(OrderReturn orderReturn) {
        Integer storeId = orderReturn.getStoreId();
        OrderReturn orderReturnData = get(orderReturn.getReturnId());
        if (true || CheckUtil.checkDataRights(storeId, orderReturnData, OrderReturn::getStoreId)) {
            orderReturn.setReturnStateId(StateCode.RETURN_PROCESS_REFUSED);

            if (!edit(orderReturn)) {
                throw new BusinessException(__("拒绝退款/退货失败"));
            }

            // 通知买家退货退款成功
            String messageId = "refunds-and-reminders";
            Map<String, Object> args = new HashMap<>();
            args.put("order_id", orderReturn.getOrderId());
            args.put("return_refund_amount", orderReturn.getReturnRefundAmount());
            messageService.sendNoticeMsg(orderReturn.getBuyerUserId(), messageId, args);

        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean review(OrderReturn orderReturn, Integer receivingAddress) {
        //获取storeId
        Integer storeId = orderReturn.getStoreId();

        List<String> returnIds = Convert.toList(String.class, orderReturn.getReturnId());
        List<OrderReturn> orderReturns = gets(returnIds);

        if (true || CheckUtil.checkDataRights(storeId, orderReturns, OrderReturn::getStoreId)) {
            //判断退货类型
            if (orderReturn.getReturnFlag().equals(StateCode.ORDER_NOT_NEED_RETURN_GOODS)) {
                //修改退款退货表信息
                if (!edit(orderReturn)) {
                    throw new BusinessException(__("修改退款退货表信息失败"));
                }
                //不用退货
                dealWithReturn(returnIds, storeId, StateCode.RETURN_PROCESS_CHECK, orderReturns, StateCode.RETURN_PROCESS_FINISH);
            } else {
                StoreShippingAddress storeShippingAddress = storeShippingAddressRepository.get(receivingAddress);
                if (storeShippingAddress != null) {
                    String returnAddr = storeShippingAddress.getSsProvince() + storeShippingAddress.getSsCity() + storeShippingAddress.getSsCounty() + storeShippingAddress.getSsAddress();
                    String returnMobile = storeShippingAddress.getSsMobile();
                    String returnContactName = storeShippingAddress.getSsName();
                    orderReturn.setReturnAddr(returnAddr);
                    orderReturn.setReturnTel(returnMobile);
                    orderReturn.setReturnContactName(returnContactName);
                    //修改退款退货表信息
                    if (!edit(orderReturn)) {
                        throw new BusinessException(__("修改退款退货表信息失败"));
                    }
                    //需要退货
                    dealWithReturn(returnIds, storeId, StateCode.RETURN_PROCESS_CHECK, orderReturns, null);
                } else {
                    throw new BusinessException(__("请选择收货人"));
                }
            }
        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        // 通知买家退货退款成功
        String messageId = "refunds-and-reminders";
        Map<String, Object> args = new HashMap<>();
        args.put("order_id", orderReturn.getReturnId());
        args.put("return_refund_amount", orderReturn.getReturnRefundAmount());
        messageService.sendNoticeMsg(orderReturn.getBuyerUserId(), messageId, args);

        return true;
    }

    @Override
    public Integer getNextReturnProcess(Integer stateId) {
        return configBaseService.getNextReturnStateId(stateId);
    }

    @Override
    public boolean checkNeedRefund(Integer stateId, Integer nextStateId) {
        Integer processRefund = StateCode.RETURN_PROCESS_MAP.get(StateCode.RETURN_PROCESS_REFUND);
        Integer processReceiptConfirmation = StateCode.RETURN_PROCESS_MAP.get(StateCode.RETURN_PROCESS_RECEIPT_CONFIRMATION);
        Integer processReturnStateId = StateCode.RETURN_PROCESS_MAP.get(stateId);
        Integer processReturnNextStateId = StateCode.RETURN_PROCESS_MAP.get(nextStateId);

        return ObjectUtil.notEqual(stateId, nextStateId)
                && processReturnStateId <= processRefund
                && processReturnNextStateId >= processReceiptConfirmation;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dealWithReturn(List<String> returnIds, Integer storeId, Integer stateId, List<OrderReturn> orderReturns, Integer nextStateId) {

        if (CollectionUtil.isEmpty(returnIds) ||
                CollectionUtil.isEmpty(orderReturns)) {
            throw new BusinessException(__("请选择需要审核的订单"));
        }

        //获取订单下一个状态
        if (CheckUtil.isEmpty(nextStateId)) {
            nextStateId = getNextReturnProcess(stateId);

            if (CheckUtil.isEmpty(nextStateId)) {
                throw new BusinessException(__("读取退单状态有误"));
            }
        }

        OrderReturn orderReturn = new OrderReturn();
        orderReturn.setReturnStateId(nextStateId);
        orderReturn.setReturnStoreTime(new Date());

        //商家 收货确认0ReturnReturn，增加库存
        if (stateId.equals(StateCode.RETURN_PROCESS_RECEIVED)) {
            //更改物流状态 已签收
            orderReturn.setReturnItemStateId(StateCode.ORDER_STATE_FINISH);

            /*
            //查询订单退货详情表，得到退货商品数量
            QueryWrapper<OrderReturnItem> returnItemQueryWrapper = new QueryWrapper<>();
            returnItemQueryWrapper.in("return_id", returnIds);
            List<OrderReturnItem> orderReturnItems = orderReturnItemRepository.find(returnItemQueryWrapper);

            if (CollectionUtil.isNotEmpty(orderReturnItems)) {
                for (OrderReturnItem orderReturnItem : orderReturnItems) {

                    //查询订单商品表，拿到商品编号
                    OrderItem orderItem = orderItemRepository.get(orderReturnItem.getOrderItemId());

                    //查询商品SKU表 修改库存数量
                    if (orderItem != null) {
                        ProductItem productItem = productItemRepository.get(orderItem.getItemId());

                        if (productItem != null) {
                            productItem.setItemQuantity(productItem.getItemQuantity() + orderReturnItem.getReturnItemNum());

                            if (!productItemRepository.edit(productItem)) {
                                throw new BusinessException(__("增加库存失败"));
                            }
                        }
                    }
                }
            }
            */
        }

        //执行真正退款逻辑
        if (checkNeedRefund(stateId, nextStateId)) {
            // 卖家账户扣款，买家账户增加
            // 佣金问题
            orderReturn.setReturnIsPaid(true);
            orderReturn.setReturnFinishTime(new Date());
            //执行退款操作
            // todo
            if (!consumeReturnService.doRefund(orderReturns)) {
                throw new BusinessException(__("退款失败"));
            }
        }

        //修改退单状态
        editReturnNextState(returnIds, stateId, orderReturn);

        // 当前状态 - 旧状态
        if (stateId.equals(StateCode.RETURN_PROCESS_CHECK)) {
            for (OrderReturn item : orderReturns) {
                String orderId = item.getOrderId();
                Integer returnIsShippingFee = item.getReturnIsShippingFee();

                if (CheckUtil.isEmpty(returnIsShippingFee)) {
                    QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
                    orderItemQueryWrapper.eq("order_id", orderId);
                    List<OrderItem> orderItemList = orderItemRepository.find(orderItemQueryWrapper);

                    if (CollectionUtil.isEmpty(orderItemList)) {
                        throw new BusinessException(__("订单商品列表为空"));
                    }

                    BigDecimal orderItemCanRefundAmount = orderItemList.stream().map(OrderItem::getOrderItemCanRefundAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

                    // 当前已经同意退款额度 = 所有单品退款额度
                    QueryWrapper<OrderReturnItem> returnItemQueryWrapper = new QueryWrapper<>();
                    returnItemQueryWrapper.eq("order_id", orderId);
                    returnItemQueryWrapper.in("return_state_id", StateCode.RETURN_PROCESS_RECEIVED, StateCode.RETURN_PROCESS_REFUND, StateCode.RETURN_PROCESS_RECEIPT_CONFIRMATION, StateCode.RETURN_PROCESS_FINISH);
                    List<OrderReturnItem> orderReturnItemList = orderReturnItemRepository.find(returnItemQueryWrapper);

                    if (CollectionUtil.isEmpty(orderReturnItemList)) {
                        throw new BusinessException(__("订单退货详情列表为空"));
                    }
                    BigDecimal returnItemSubtotal = orderReturnItemList.stream().map(OrderReturnItem::getReturnItemSubtotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

                    // 全部退款完成
                    if (ObjectUtil.compare(orderItemCanRefundAmount, returnItemSubtotal) == 0) {
                        OrderData orderData = new OrderData();
                        orderData.setOrderId(orderId);
                        orderData.setOrderRefundStatus(2);

                        if (!orderDataRepository.edit(orderData)) {
                            throw new BusinessException(__("修改订单详细信息失败"));
                        }

                        /*
                        // 取消订单
                        OrderInfo orderInfo = orderInfoRepository.get(orderId);
                        if (orderInfo == null) {
                            throw new BusinessException(__("订单信息表为空"));
                        }
                        List<Integer> states = Arrays.asList(StateCode.ORDER_STATE_SHIPPED, StateCode.ORDER_STATE_RECEIVED, StateCode.ORDER_STATE_FINISH);

                        if (!states.contains(orderInfo.getOrderStateId())) {
                            //未发货，整单退，需要取消订单不存在。
                            //orderService.cancel(orderId, __("售后取消订单"));

                            // 未发货，退运费 - 判断运费是否存在
                            OrderData data = orderDataRepository.get(orderId);

                            if (data != null && data.getOrderShippingFee().compareTo(BigDecimal.ZERO) > 0) {
                                // 1、生成退运费售后服务单
                                ContextUser user = ContextUtil.getLoginUser();

                                if (user == null) {
                                    throw new BusinessException(ResultCode.NEED_LOGIN);
                                }
                                OrderReturn orderResult = new OrderReturn();
                                Integer buyUserId = orderInfo.getUserId();
                                Integer stoId = orderInfo.getStoreId();
                                Integer siteId = orderInfo.getSubsiteId();
                                BigDecimal orderShippingFee = data.getOrderShippingFee();
                                BigDecimal orderPointsFee = data.getOrderPointsFee();
                                BigDecimal orderRefundAgreePoints = data.getOrderRefundAgreePoints();
                                BigDecimal roundPoint = NumberUtil.round(NumberUtil.sub(orderPointsFee, orderRefundAgreePoints), 2);
                                orderResult.setOrderId(orderId);
                                orderResult.setBuyerUserId(buyUserId);
                                orderResult.setReturnIsShippingFee(1);
                                orderResult.setReturnReasonId(0);
                                orderResult.setReturnBuyerMessage(__("全部退款退运费"));
                                orderResult.setStoreId(stoId);
                                orderResult.setReturnRefundAmount(orderShippingFee);
                                orderResult.setReturnShippingFee(orderShippingFee);
                                orderResult.setReturnRefundPoint(NumberUtil.min(roundPoint, orderShippingFee));
                                orderResult.setReturnStateId(StateCode.RETURN_PROCESS_CHECK);
                                orderResult.setReturnAddTime(new Date().getTime());
                                orderResult.setReturnTel("");
                                orderResult.setReturnStoreUserId(user.getUserId());
                                orderResult.setSubsiteId(siteId);

                                String typeCode = stateCodeService.getCode(StateCode.ORDER_TYPE_FX, "stateCodeCode");
                                String returnId = numberSeqService.getNextSeqString(typeCode);
                                orderResult.setReturnId(returnId);

                                if (!add(orderResult)) {
                                    throw new BusinessException(__("生成退运费订单失败"));
                                }

                                // 修正退款总额 为 加上运费的。
                                BigDecimal orderRefundAmount = data.getOrderRefundAmount();
                                data.setOrderRefundAmount(NumberUtil.add(orderRefundAmount, orderShippingFee));

                                if (!orderDataRepository.edit(data)) {
                                    throw new BusinessException(__("修改订单详细信息退款总额失败"));
                                }
                            }
                        }
                        */
                    } else {
                        // 部分退款
                        OrderData orderData = new OrderData();
                        orderData.setOrderId(orderId);
                        orderData.setOrderRefundStatus(1);

                        if (!orderDataRepository.edit(orderData)) {
                            throw new BusinessException(__("部分退款失败"));
                        }
                    }

                    //修改订单同步状态， 重新同步
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrderId(orderId);
                    orderInfo.setOrderIsSync(false);

                    if (!orderInfoRepository.edit(orderInfo)) {
                        throw new BusinessException(__("修改订单同步状态失败"));
                    }
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editReturnNextState(List<String> returnIds, Integer stateId, OrderReturn orderReturn) {
        //修改退款退货表 退单状态
        QueryWrapper<OrderReturn> returnQueryWrapper = new QueryWrapper<>();
        returnQueryWrapper.in("return_id", returnIds);
        returnQueryWrapper.eq("return_state_id", stateId);

        if (!returnRepository.edit(orderReturn, returnQueryWrapper)) {
            throw new BusinessException(__("修改退单状态失败"));
        }

        //修改订单退货详情表 退单状态
        OrderReturnItem orderReturnItem = new OrderReturnItem();
        orderReturnItem.setReturnStateId(orderReturn.getReturnStateId());
        QueryWrapper<OrderReturnItem> returnItemQueryWrapper = new QueryWrapper<>();
        returnItemQueryWrapper.in("return_id", returnIds);

        if (!returnItemRepository.edit(orderReturnItem, returnItemQueryWrapper)) {
            throw new BusinessException(__("修改订单退货详情状态失败"));
        }
    }

    @Override
    public IPage<OrderReturnRes> getList(OrderReturnListReq orderReturnListReq) {
        IPage<OrderReturnRes> orderReturnResPage = new Page<>();
        orderReturnListReq.setSidx("return_add_time");
        orderReturnListReq.setSort("DESC");
        IPage<OrderReturn> orderReturnIPage = lists(orderReturnListReq);

        if (orderReturnIPage == null || CollectionUtil.isEmpty(orderReturnIPage.getRecords())) {
            return orderReturnResPage;
        }

        BeanUtils.copyProperties(orderReturnIPage, orderReturnResPage);
        List<OrderReturnRes> orderReturnResList = new ArrayList<>();
        //退款退货表信息
        List<OrderReturn> returnList = orderReturnIPage.getRecords();
        //通过returnId 获得订单退货详情表信息
        List<String> returnIds = returnList.stream().map(OrderReturn::getReturnId).distinct().collect(Collectors.toList());
        QueryWrapper<OrderReturnItem> returnItemQuery = new QueryWrapper<>();
        returnItemQuery.in("return_id", returnIds);
        List<OrderReturnItem> orderReturnItems = returnItemRepository.find(returnItemQuery);

        //订单退货详情 Map
        Map<String, List<OrderReturnItem>> orderReturnItemMap = new HashMap<>();
        //通过orderItemId 获得订单商品表
        Map<String, List<OrderItem>> orderItemMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(orderReturnItems)) {
            orderReturnItemMap = orderReturnItems.stream().collect(Collectors.groupingBy(OrderReturnItem::getReturnId));

            List<Long> orderItemIds = orderReturnItems.stream().map(OrderReturnItem::getOrderItemId).distinct().collect(Collectors.toList());
            List<OrderItem> orderItems = orderItemRepository.gets(orderItemIds);

            if (CollectionUtil.isNotEmpty(orderItems)) {
                orderItemMap = orderItems.stream().collect(Collectors.groupingBy(OrderItem::getOrderId));
            }
        }

        //封装数据
        for (OrderReturn orderReturn : returnList) {
            OrderReturnRes orderReturnRes = new OrderReturnRes();
            BeanUtils.copyProperties(orderReturn, orderReturnRes);

            //退货商品总数量
            if (CollUtil.isNotEmpty(orderReturnItemMap)) {
                List<OrderReturnItem> returnItems = orderReturnItemMap.get(orderReturn.getReturnId());

                if (CollectionUtil.isNotEmpty(returnItems)) {
                    orderReturnRes.setReturnNum(returnItems.stream().mapToInt(OrderReturnItem::getReturnItemNum).sum());
                }
            }
            List<OrderReturnItemVo> orderReturnItemVos = new ArrayList<>();

            if (CollUtil.isNotEmpty(orderItemMap)) {
                List<OrderItem> orderItems = orderItemMap.get(orderReturn.getOrderId());

                if (CollectionUtil.isNotEmpty(orderItems)) {
                    for (OrderItem orderItem : orderItems) {
                        OrderReturnItemVo orderReturnItemVo = new OrderReturnItemVo();
                        BeanUtils.copyProperties(orderItem, orderReturnItemVo);
                        orderReturnItemVos.add(orderReturnItemVo);
                    }
                }
            }
            orderReturnRes.setItems(orderReturnItemVos);
            orderReturnResList.add(orderReturnRes);
        }

        if (CollectionUtil.isNotEmpty(orderReturnResList)) {
            orderReturnResPage.setRecords(orderReturnResList);
        }

        return orderReturnResPage;
    }

    @Override
    public OrderReturnRes getReturn(String returnId) {
        OrderReturnRes orderReturnRes = new OrderReturnRes();
        OrderReturn orderReturn = get(returnId);

        if (orderReturn == null) return null;

        BeanUtils.copyProperties(orderReturn, orderReturnRes);

        //订单退货详情表
        QueryWrapper<OrderReturnItem> orderReturnItemQueryWrapper = new QueryWrapper<>();
        orderReturnItemQueryWrapper.eq("return_id", returnId);
        List<OrderReturnItem> orderReturnItems = returnItemRepository.find(orderReturnItemQueryWrapper);

        if (CollectionUtil.isNotEmpty(orderReturnItems)) {
            //订单退货详情 退款金额
            BigDecimal reduce = orderReturnItems.stream().map(OrderReturnItem::getReturnItemSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderReturnRes.setSubmitReturnRefundAmount(reduce);
            //订单商品
            List<Long> orderItemIds = orderReturnItems.stream().map(OrderReturnItem::getOrderItemId).distinct().collect(Collectors.toList());
            List<OrderItem> orderItems = orderItemRepository.gets(orderItemIds);
            Map<Long, OrderItem> orderItemMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(orderItems)) {
                orderItemMap = orderItems.stream().collect(Collectors.toMap(OrderItem::getOrderItemId, OrderItem -> OrderItem, (k1, k2) -> k1));
            }

            //商品信息
            List<OrderReturnItemVo> orderReturnItemVos = new ArrayList<>();

            List<Integer> returnReasonIds = orderReturnItems.stream().map(OrderReturnItem::getReturnReasonId)
                    .filter(CheckUtil::isNotEmpty).collect(Collectors.toList());
            List<OrderReturnReason> returnReasonList = orderReturnReasonRepository.gets(returnReasonIds);

            //退款原因
            if (CheckUtil.isNotEmpty(orderReturn.getReturnReasonId())) {
                Optional<OrderReturnReason> reasonOptional = returnReasonList.stream().filter(orderReturnReason -> orderReturnReason.getReturnReasonId().equals(orderReturn.getReturnReasonId())).findFirst();
                if (reasonOptional.isPresent()) {
                    OrderReturnReason returnReason = reasonOptional.get();
                    orderReturnRes.setReturnReasonName(returnReason.getReturnReasonName());
                }
            }

            for (OrderReturnItem orderReturnItem : orderReturnItems) {
                OrderReturnItemVo orderReturnItemVo = new OrderReturnItemVo();
                BeanUtils.copyProperties(orderReturnItem, orderReturnItemVo);
                //订单商品
                if (CollUtil.isNotEmpty(orderItemMap)) {
                    OrderItem orderItem = orderItemMap.get(orderReturnItem.getOrderItemId());

                    if (orderItem != null) {
                        BeanUtils.copyProperties(orderItem, orderReturnItemVo);
                    }
                }
                //退款凭证
                if (StrUtil.isNotEmpty(orderReturnItem.getReturnItemImage())) {
                    orderReturnItemVo.setReturnItemImageList(Convert.toList(String.class, orderReturnItem.getReturnItemImage()));
                }
                orderReturnItemVos.add(orderReturnItemVo);
            }
            orderReturnRes.setItems(orderReturnItemVos);
        }

        return orderReturnRes;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cancel(String returnId, Integer userId) {
        OrderReturn orderReturn = get(returnId);

        if (orderReturn == null) {
            return false;
        }

        if (!CheckUtil.checkDataRights(userId, orderReturn, OrderReturn::getBuyerUserId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        orderReturn.setReturnStateId(StateCode.RETURN_PROCESS_CANCEL);

        //修改退款退货表 处理状态
        if (!edit(orderReturn)) {
            throw new BusinessException(__("修改退款退货表处理状态失败"));
        }

        //修改订单退货详情表 处理状态
        QueryWrapper<OrderReturnItem> orderReturnItemQueryWrapper = new QueryWrapper<>();
        orderReturnItemQueryWrapper.eq("return_id", returnId);
        OrderReturnItem orderReturnItem = new OrderReturnItem();
        orderReturnItem.setReturnStateId(StateCode.RETURN_PROCESS_CANCEL);

        if (!returnItemRepository.edit(orderReturnItem, orderReturnItemQueryWrapper)) {
            throw new BusinessException(__("修改订单退货详情表处理状态失败"));
        }

        List<OrderReturnItem> orderReturnItems = returnItemRepository.find(orderReturnItemQueryWrapper);

        Integer returnItemNumSum = 0;

        if (CollectionUtil.isNotEmpty(orderReturnItems)) {
            for (OrderReturnItem returnItem : orderReturnItems) {
                //退货商品数量
                Integer returnItemNum = returnItem.getReturnItemNum();
                returnItemNumSum = returnItemNumSum + returnItemNum;

                //退款总额
                BigDecimal returnItemSubtotal = returnItem.getReturnItemSubtotal();
                //订单商品信息
                Long orderItemId = returnItem.getOrderItemId();
                OrderItem orderItem = orderItemRepository.get(orderItemId);

                if (orderItem != null) {
                    orderItem.setOrderItemReturnNum(orderItem.getOrderItemReturnNum() - returnItemNum);
                    orderItem.setOrderItemReturnSubtotal(NumberUtil.sub(orderItem.getOrderItemReturnSubtotal(), returnItemSubtotal));
                    //修改订单商品表退货信息
                    if (!orderItemRepository.edit(orderItem)) {
                        throw new BusinessException(__("修改订单商品表退货信息失败"));
                    }
                }
            }
        }

        //修正申请退款总额
        OrderData orderData = orderDataRepository.get(orderReturn.getOrderId());

        if (orderData != null) {
            orderData.setOrderRefundAmount(orderData.getOrderRefundAmount().subtract(orderReturn.getReturnRefundAmount()));
            orderData.setOrderReturnNum(orderData.getOrderReturnNum() - returnItemNumSum);

            if (!orderDataRepository.edit(orderData)) {
                throw new BusinessException(__("修正申请退款总额失败"));
            }
        }

        return true;
    }

    @Override
    public OrderReturnItemVo returnItem(String orderId, String orderItemId, Integer userId) {
        OrderReturnItemVo orderReturnItemVo = new OrderReturnItemVo();
        //订单信息
        OrderInfo orderInfo = orderInfoRepository.get(orderId);

        if (orderInfo == null) {
            return null;
        }

        if (!CheckUtil.checkDataRights(userId, orderInfo, OrderInfo::getUserId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        //订单商品表
        OrderItem orderItem = orderItemRepository.get(orderItemId);

        if (orderItem == null) {
            return null;
        }

        if (!orderItem.getOrderId().equals(orderInfo.getOrderId())) {
            throw new BusinessException(__("订单信息有误"));
        }
        //商品信息
        BeanUtils.copyProperties(orderItem, orderReturnItemVo);
        BigDecimal itemRefundAmount = BigDecimal.ZERO;
        Integer itemReturnNum = 0;

        //退款退货信息
        List<Integer> returnStateIds = Arrays.asList(StateCode.RETURN_PROCESS_FINISH, StateCode.RETURN_PROCESS_CHECK, StateCode.RETURN_PROCESS_RECEIVED, StateCode.RETURN_PROCESS_REFUND, StateCode.RETURN_PROCESS_RECEIPT_CONFIRMATION, StateCode.RETURN_PROCESS_REFUSED, StateCode.RETURN_PROCESS_SUBMIT);
        QueryWrapper<OrderReturn> orderReturnQueryWrapper = new QueryWrapper<>();
        orderReturnQueryWrapper.eq("order_id", orderId);
        orderReturnQueryWrapper.in("return_state_id", returnStateIds);
        List<OrderReturn> orderReturns = find(orderReturnQueryWrapper);

        if (CollectionUtil.isNotEmpty(orderReturns)) {
            List<String> returnIds = orderReturns.stream().map(OrderReturn::getReturnId).collect(Collectors.toList());

            //订单退货详情表信息
            if (CollectionUtil.isNotEmpty(returnIds)) {
                QueryWrapper<OrderReturnItem> orderReturnItemQueryWrapper = new QueryWrapper<>();
                orderReturnItemQueryWrapper.in("return_id", returnIds);
                orderReturnItemQueryWrapper.eq("order_item_id", orderItemId);
                orderReturnItemQueryWrapper.in("return_state_id", returnStateIds);
                List<OrderReturnItem> orderReturnItems = orderReturnItemRepository.find(orderReturnItemQueryWrapper);

                if (CollectionUtil.isNotEmpty(orderReturnItems)) {
                    itemRefundAmount = orderReturnItems.stream().map(OrderReturnItem::getReturnItemSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                    itemReturnNum = orderReturnItems.stream().map(OrderReturnItem::getReturnItemNum).reduce(0, Integer::sum);
                }
            }
        }

        BigDecimal orderItemCanRefundAmount = orderItem.getOrderItemCanRefundAmount();
        orderReturnItemVo.setCanRefundAmount(NumberUtil.sub(orderItemCanRefundAmount, itemRefundAmount));
        orderReturnItemVo.setCanRefundNum(orderItem.getOrderItemQuantity() - itemReturnNum);
        //退货原因集合
        List<OrderReturnReason> orderReturnReasons = orderReturnReasonRepository.find(new QueryWrapper<OrderReturnReason>().orderByAsc("return_reason_sort"));

        if (CollectionUtil.isNotEmpty(orderReturnReasons)) {
            orderReturnItemVo.setReturnReasonList(orderReturnReasons);
        }

        return orderReturnItemVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addItem(OrderReturnInput orderReturnInput) {
        String returnId = "";
        Integer userId = orderReturnInput.getUserId();
        //是否有店铺
        Integer buyerStoreId = storeBaseService.getStoreId(userId);

        List<Long> orderItemIds = CommonUtil.column(orderReturnInput.getReturnItems(), OrderReturnItemInputVo::getOrderItemId);


        OrderInfo orderInfo = orderInfoRepository.get(orderReturnInput.getOrderId());

        if (orderInfo == null) {
            throw new BusinessException(__("此订单信息有误！"));
        }

        OrderBase orderBase = orderBaseRepository.get(orderReturnInput.getOrderId());

        if (orderBase == null) {
            throw new BusinessException(__("订单详细信息有误！"));
        }

        /*
        List<Integer> contractTypeIds = Convert.toList(Integer.class, productIndex.getContractTypeIds());

        if (CollectionUtil.isNotEmpty(contractTypeIds)) {
            //start判断是否可以申请退款
            // 1、商品是否允许
            boolean denyReturn = contractTypeIds.contains(StateCode.CONTRACT_TYPE_DENY_RETURN);

            // 2、是否已经可结算, 进入可结算，不允许退货。
            Integer orderStateId = orderInfo.getOrderStateId();
            if (orderStateId.equals(StateCode.ORDER_STATE_RECEIVED) || orderStateId.equals(StateCode.ORDER_STATE_FINISH)) {
                Long withdrawTime = orderBaseRepository.getWithdrawTime();

                if (withdrawTime.compareTo(orderInfo.getOrderReceivedTime().getTime()) > 0) {
                    denyReturn = true;
                }
            }

            if (denyReturn) {
                throw new BusinessException(__("此商品不允许退货！"));
            }
        }
         */

        // 判断此订单商品是否有正在审核的退款单
        QueryWrapper<OrderReturn> orderReturnQueryWrapper = new QueryWrapper<>();
        orderReturnQueryWrapper.eq("order_id", orderReturnInput.getOrderId());
        orderReturnQueryWrapper.eq("return_state_id", StateCode.RETURN_PROCESS_CHECK);
        List<OrderReturn> orderReturns = find(orderReturnQueryWrapper);

        if (CollectionUtil.isNotEmpty(orderReturns)) {
            List<String> returnIds = orderReturns.stream().map(OrderReturn::getReturnId).collect(Collectors.toList());
            QueryWrapper<OrderReturnItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.in("order_item_id", orderItemIds);
            itemQueryWrapper.in("return_id", returnIds);
            List<OrderReturnItem> orderReturnItems = returnItemRepository.find(itemQueryWrapper);

            if (CollectionUtil.isNotEmpty(orderReturnItems)) {
                throw new BusinessException(__("此订单有商品退货审核中！"));
            }
        }

        if (CheckUtil.checkDataRights(userId, orderBase, OrderBase::getUserId)) {
            Integer storeId = orderBase.getStoreId();

            if (StrUtil.isNotEmpty(orderReturnInput.getReturnTel()) && !PhoneUtil.isMobile(orderReturnInput.getReturnTel())) {
                throw new BusinessException(__("手机号输入有误！"));
            }

            //封装数据
            OrderReturn orderReturn = new OrderReturn();
            orderReturn.setOrderId(orderReturnInput.getOrderId());
            orderReturn.setBuyerUserId(userId);
            orderReturn.setBuyerStoreId(buyerStoreId);
            orderReturn.setReturnReasonId(orderReturnInput.getReturnReasonId());
            orderReturn.setReturnBuyerMessage(orderReturnInput.getReturnBuyerMessage());
            orderReturn.setStoreId(storeId);
            orderReturn.setReturnRefundAmount(BigDecimal.ZERO);
            orderReturn.setReturnCommisionFee(BigDecimal.ZERO);
            orderReturn.setReturnShippingFee(BigDecimal.ZERO);
            orderReturn.setReturnStateId(StateCode.RETURN_PROCESS_CHECK);
            orderReturn.setReturnTel(orderReturnInput.getReturnTel());
            orderReturn.setSubsiteId(orderInfo.getSubsiteId());
            orderReturn.setReturnFlag(orderReturnInput.getReturnFlag());

            //是否退运费
            OrderData orderData = orderDataRepository.get(orderReturnInput.getOrderId());
            BigDecimal orderShippingFee = orderData.getOrderShippingFee();
            if (orderReturnInput.getReviewFlag()) {
                orderReturn.setReturnShippingFee(orderShippingFee);
            } else if (orderInfo.getOrderIsOut().equals(StateCode.ORDER_PICKING_STATE_NO)) {
                orderReturn.setReturnShippingFee(orderShippingFee);
            }

            List<OrderReturnItem> returnItems = new ArrayList<>();

            for (OrderReturnItemInputVo itemInputVo : orderReturnInput.getReturnItems()) {
                OrderReturnItem returnItem = new OrderReturnItem();

                if (itemInputVo.getReturnItemNum() <= 0) {
                    throw new BusinessException(String.format(__("%s:退款数量不正确！"), itemInputVo.getOrderItemId()));
                }

                returnItem.setOrderItemId(itemInputVo.getOrderItemId());
                returnItem.setOrderId(orderReturnInput.getOrderId());
                returnItem.setReturnItemNum(itemInputVo.getReturnItemNum());
                returnItem.setReturnItemSubtotal(itemInputVo.getReturnRefundAmount());
                returnItem.setReturnReasonId(orderReturnInput.getReturnReasonId());
                returnItem.setReturnItemNote(orderReturnInput.getReturnBuyerMessage());
                returnItem.setReturnItemImage(orderReturnInput.getReturnItemImage());
                returnItem.setReturnStateId(StateCode.RETURN_PROCESS_CHECK);

                returnItems.add(returnItem);
            }

            if (!addReturnByItem(orderReturn, returnItems)) {
                throw new BusinessException(ResultCode.FAILED);
            }

            returnId = orderReturn.getReturnId();

            // 七天无理由自动退货
            //if (contractTypeIds.contains(StateCode.CONTRACT_TYPE_7_RETURN) && orderInfo.getOrderIsOut().equals(StateCode.ORDER_PICKING_STATE_NO)) {
            if (orderReturnInput.getReviewFlag()) {
                if (orderInfo.getOrderIsShipped().equals(StateCode.ORDER_SHIPPED_STATE_NO)) {
                    orderReturn.setReturnStoreMessage(__("取消订单，自动审核"));

                    //退单审核
                    review(orderReturn, 0);
                } else {
                    //部分发货，可以
                }
            } else if (orderInfo.getOrderIsOut().equals(StateCode.ORDER_PICKING_STATE_NO)) {
                orderReturn.setReturnStoreMessage(__("未出库，自动审核"));

                //退单审核
                review(orderReturn, 0);
            }
        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        return returnId;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addReturnByItem(OrderReturn orderReturn, List<OrderReturnItem> returnItems) {

        List<Long> orderItemIds = CommonUtil.column(returnItems, OrderReturnItem::getOrderItemId);
        List<OrderItem> orderItems = orderItemRepository.gets(orderItemIds);

        if (CollUtil.isEmpty(orderItems)) {
            throw new BusinessException(__("订单商品信息有误！"));
        }

        String orderId = orderReturn.getOrderId();
        OrderData orderData = orderDataRepository.get(orderId);

        for (OrderReturnItem returnItem : returnItems) {
            OrderItem orderItem = orderItems.stream().filter(s -> s.getOrderItemId().equals(returnItem.getOrderItemId())).findFirst().orElse(null);

            if (orderItem == null) {
                throw new BusinessException(__("订单商品信息有误！"));
            }

            BigDecimal returnItemSubtotal = returnItem.getReturnItemSubtotal();

            BigDecimal orderItemCanRefundAmount = orderItem.getOrderItemCanRefundAmount();
            BigDecimal orderItemReturnAgreeAmount = orderItem.getOrderItemReturnAgreeAmount();
            BigDecimal newAmount = NumberUtil.sub(orderItemCanRefundAmount, orderItemReturnAgreeAmount);

            if (returnItemSubtotal.compareTo(BigDecimal.ZERO) < 0 || returnItemSubtotal.compareTo(newAmount) > 0) {
                throw new BusinessException(__("退货单金额错误！"));
            }

            Integer orderItemQuantity = orderItem.getOrderItemQuantity();
            Integer returnNum = ObjectUtil.defaultIfNull(orderItem.getOrderItemReturnAgreeNum(), 0);
            Integer returnItemNum = returnItem.getReturnItemNum();

            if ((orderItemQuantity - returnNum) < returnItemNum) {
                throw new BusinessException(__("退货单商品数量错误！"));
            }

            BigDecimal returnAmount = CheckUtil.isNotEmpty(orderReturn.getReturnRefundAmount()) ? orderReturn.getReturnRefundAmount() : BigDecimal.ZERO;
            orderReturn.setReturnRefundAmount(NumberUtil.add(returnAmount, returnItemSubtotal));

            // 退还佣金计算
            BigDecimal orderItemCommissionRate = orderItem.getOrderItemCommissionRate();
            returnItem.setReturnItemCommisionFee(NumberUtil.div(NumberUtil.mul(returnItemSubtotal, orderItemCommissionRate), 100));

            BigDecimal returnCommisionFee = orderReturn.getReturnCommisionFee();
            BigDecimal returnItemCommisionFee = returnItem.getReturnItemCommisionFee();
            orderReturn.setReturnCommisionFee(NumberUtil.add(returnCommisionFee, returnItemCommisionFee));


            //Order Data 修改
            BigDecimal orderRefundAmount = CheckUtil.isNotEmpty(orderData.getOrderRefundAmount()) ? orderData.getOrderRefundAmount() : BigDecimal.ZERO;
            orderData.setOrderRefundAmount(NumberUtil.add(orderRefundAmount, returnItemSubtotal));

            Integer orderReturnNum = ObjectUtil.defaultIfNull(orderData.getOrderReturnNum(), 0);
            orderData.setOrderReturnNum(orderReturnNum + returnItemNum);

            //订单SKU
            orderItem.setOrderItemReturnNum(orderItem.getOrderItemReturnNum() + returnItemNum);
            orderItem.setOrderItemReturnSubtotal(NumberUtil.add(orderItem.getOrderItemReturnSubtotal(), returnItemSubtotal));

            if (!orderItemRepository.edit(orderItem)) {
                throw new BusinessException(__("修改订单信息失败！"));
            }
        }

        //退运费
        BigDecimal returnShippingFee = CheckUtil.isNotEmpty(orderReturn.getReturnShippingFee()) ? orderReturn.getReturnShippingFee() : BigDecimal.ZERO;
        BigDecimal returnRefundAmount = orderReturn.getReturnRefundAmount();
        orderReturn.setReturnRefundAmount(NumberUtil.add(returnShippingFee, returnRefundAmount));

        orderReturn.setReturnAddTime(new Date().getTime());
        orderReturn.setReturnType(StateCode.ORDER_RETURN);

        String returnId = numberSeqService.getNextSeqString("RT");
        orderReturn.setReturnId(returnId);

        for (OrderReturnItem returnItem : returnItems) {
            returnItem.setReturnId(returnId);
        }

        if (!add(orderReturn)) {
            throw new BusinessException(__("保存退货基础单失败！"));
        }

        if (!orderReturnItemRepository.saves(returnItems)) {
            throw new BusinessException(__("保存退货单失败！"));
        }

        if (orderData != null) {
            if (!orderDataRepository.edit(orderData)) {
                throw new BusinessException(__("修改订单详细信息失败！"));
            }
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrderReturn(String returnId, boolean orderIsSettlemented) {
        List<String> returnIds = Convert.toList(String.class, returnId);
        QueryWrapper<OrderReturn> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("return_id", returnIds).eq("return_is_settlemented", 0);
        OrderReturn orderReturn = new OrderReturn();
        orderReturn.setReturnIsSettlemented(orderIsSettlemented);
        orderReturn.setReturnSettlementTime(new Date());

        return edit(orderReturn, queryWrapper);
    }

    @Override
    public boolean editReturn(OrderReturn orderReturn) {
        Integer expressId = orderReturn.getExpressId();
        if (CheckUtil.isNotEmpty(expressId)) {
            ExpressBase expressBase = expressBaseRepository.get(expressId);
            orderReturn.setReturnTrackingName(expressBase.getExpressName());
        }
        return edit(orderReturn);
    }

}
