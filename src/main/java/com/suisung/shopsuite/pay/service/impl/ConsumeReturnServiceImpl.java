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
package com.suisung.shopsuite.pay.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.model.v3.RefundAmount;
import com.ijpay.wxpay.model.v3.RefundModel;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.consts.PointsType;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.pay.model.entity.ConsumeDeposit;
import com.suisung.shopsuite.pay.model.entity.ConsumeRecord;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.model.vo.UserPointsVo;
import com.suisung.shopsuite.pay.model.vo.WxPayV3Vo;
import com.suisung.shopsuite.pay.repository.*;
import com.suisung.shopsuite.pay.service.ConsumeReturnService;
import com.suisung.shopsuite.pay.service.UserResourceService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.trade.model.entity.*;
import com.suisung.shopsuite.trade.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Service
@Slf4j
public class ConsumeReturnServiceImpl implements ConsumeReturnService {
    @Autowired
    private ConsumeCombineRepository consumeCombineRepository;

    @Autowired
    private ConsumeTradeRepository consumeTradeRepository;

    @Autowired
    private ConsumeRecordRepository consumeRecordRepository;

    @Autowired
    private UserResourceRepository userResourceRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private OrderDataRepository orderDataRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderReturnItemRepository orderReturnItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ConsumeDepositRepository consumeDepositRepository;

    @Autowired
    private UserResourceService userResourceService;

    @Autowired
    private OrderReturnRepository orderReturnRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private OrderReturnReasonRepository orderReturnReasonRepository;

    /**
     * 执行退款操作
     *
     * @param orderReturns
     * @return
     */
    @Override
    public boolean doRefund(List<OrderReturn> orderReturns) {

        List<String> paidReturnIds = new ArrayList<>();

        // 原理退回标记
        boolean orderRefundFlag = configBaseService.getConfig("order_refund_flag", false);
        List<String> orderIds = orderReturns.stream().map(OrderReturn::getOrderId).distinct().collect(Collectors.toList());
        List<Integer> userIds = orderReturns.stream().map(OrderReturn::getBuyerUserId).distinct().collect(Collectors.toList());
        List<String> returnIds = orderReturns.stream().map(OrderReturn::getReturnId).distinct().collect(Collectors.toList());


        List<OrderData> orderDataList = orderDataRepository.gets(orderIds);
        List<UserResource> userResourceList = userResourceRepository.gets(userIds);
        List<OrderInfo> orderInfoList = orderInfoRepository.gets(orderIds);
        QueryWrapper<OrderReturnItem> returnItemQueryWrapper = new QueryWrapper<>();
        returnItemQueryWrapper.in("return_id", returnIds);
        List<OrderReturnItem> orderReturnItems = orderReturnItemRepository.find(returnItemQueryWrapper);
        List<UserInfo> userInfoList = userInfoRepository.gets(userIds);

        List<Long> orderItemIds = orderReturnItems.stream().map(OrderReturnItem::getOrderItemId).distinct().collect(Collectors.toList());
        List<OrderItem> orderItemList = orderItemRepository.gets(orderItemIds);

        Date curDate = new Date();
        DateTime ymdDate = DateUtil.parse(DateUtil.format(curDate, "yyyy-MM-dd"));
        Float pointsVaueRate = configBaseService.getConfig("points_vaue_rate", 0f);

        // 积分抵扣，暂时忽略，不涉及此处支付。
        // 按照次序，依次支付。
        for (OrderReturn orderReturn : orderReturns) {
            Integer userId = orderReturn.getBuyerUserId();
            if (CheckUtil.isEmpty(userId)) {
                throw new BusinessException(__("买家信息有误"));
            }
            Integer buyerStoreId = orderReturn.getBuyerStoreId();
            Integer storeId = orderReturn.getStoreId();
            UserResource userResource = userResourceList.stream().filter(resource -> resource.getUserId().equals(userId)).findFirst().get();

            // 判断是否需要退佣金
            BigDecimal returnCommisionFee = BigDecimal.ZERO;

            // 不是退运费
            String orderId = orderReturn.getOrderId();
            Integer returnIsShippingFee = orderReturn.getReturnIsShippingFee();
            if (CheckUtil.isEmpty(returnIsShippingFee)) {
                Float withdrawReceivedDay = Convert.toFloat(configBaseService.getConfig("withdraw_received_day"));
                if (withdrawReceivedDay == 0) withdrawReceivedDay = 7f;

                if (withdrawReceivedDay >= 0) {
                    OrderInfo orderInfo = orderInfoList.stream().filter(info -> info.getOrderId().equals(orderId)).findFirst().get();
                    Integer orderStateId = orderInfo.getOrderStateId();
                    Integer orderIsPaid = orderInfo.getOrderIsPaid();

                    // 未到可结算时间可退佣金
                    if (!orderStateId.equals(StateCode.ORDER_STATE_FINISH) && !orderIsPaid.equals(StateCode.ORDER_PAID_STATE_YES)) {
                        returnCommisionFee = orderReturn.getReturnCommisionFee();
                    }
                }
            }

            BigDecimal waitingRefundAmount = orderReturn.getReturnRefundAmount();
            if (CheckUtil.isNotEmpty(waitingRefundAmount)) {
                OrderData orderData = orderDataList.stream().filter(data -> data.getOrderId().equals(orderId)).findFirst().get();
                BigDecimal orderPointsFee = orderData.getOrderPointsFee();
                BigDecimal orderRefundAgreePoints = orderData.getOrderRefundAgreePoints();

                BigDecimal buyerUserMoney = waitingRefundAmount;
                BigDecimal buyerUserPoints = BigDecimal.ZERO;

                BigDecimal sellerUserMoney = waitingRefundAmount.negate();
                String returnId = orderReturn.getReturnId();

                // 写入流水
                ConsumeRecord buyerConsumeRecord = new ConsumeRecord();
                buyerConsumeRecord.setOrderId(returnId);
                buyerConsumeRecord.setUserId(userId);
                buyerConsumeRecord.setStoreId(buyerStoreId);
                UserInfo userInfo = userInfoList.stream().filter(info -> info.getUserId().equals(userId)).findFirst().get();
                buyerConsumeRecord.setUserNickname(userInfo.getUserNickname());
                buyerConsumeRecord.setRecordDate(ymdDate);
                buyerConsumeRecord.setRecordYear(DateUtil.year(ymdDate));
                buyerConsumeRecord.setRecordMonth(DateUtil.month(ymdDate) + 1);
                buyerConsumeRecord.setRecordDay(DateUtil.dayOfMonth(ymdDate));
                buyerConsumeRecord.setRecordTitle(__("退款单:") + returnId);
                buyerConsumeRecord.setRecordTime(curDate.getTime());
                buyerConsumeRecord.setPaymentMetId(StateCode.PAYMENT_MET_MONEY);

                // 增加流水
                buyerConsumeRecord.setRecordMoney(waitingRefundAmount);
                buyerConsumeRecord.setTradeTypeId(StateCode.TRADE_TYPE_REFUND_GATHERING);

                // 卖家流水记录
                ConsumeRecord sellerConsumeRecord = new ConsumeRecord();
                BeanUtils.copyProperties(buyerConsumeRecord, sellerConsumeRecord);
                sellerConsumeRecord.setUserId(StateCode.ADMIN_PLANTFORM_USERID);
                sellerConsumeRecord.setStoreId(storeId);
                sellerConsumeRecord.setRecordMoney(NumberUtil.add(waitingRefundAmount.negate(), returnCommisionFee));
                sellerConsumeRecord.setRecordCommissionFee(returnCommisionFee.negate());
                sellerConsumeRecord.setTradeTypeId(StateCode.TRADE_TYPE_REFUND_PAY);

                orderData.setOrderRefundAgreeAmount(NumberUtil.add(waitingRefundAmount, orderRefundAgreePoints));

                // 读取退款单项目
                List<OrderReturnItem> orderReturnItemList = orderReturnItems.stream().filter(orderReturnItem -> orderReturnItem.getReturnId().equals(returnId)).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(orderItemList)) {

                    List<OrderItem> orderItems = new ArrayList<>();

                    for (OrderReturnItem orderReturnItem : orderReturnItemList) {
                        Long orderItemId = orderReturnItem.getOrderItemId();
                        Optional<OrderItem> orderItemOpl = orderItemList.stream().filter(orderItem -> orderItem.getOrderItemId().equals(orderItemId)).findFirst();
                        if (orderItemOpl.isPresent()) {
                            OrderItem orderItem = orderItemOpl.get();
                            BigDecimal returnItemSubtotal = orderReturnItem.getReturnItemSubtotal();
                            Integer returnItemNum = orderReturnItem.getReturnItemNum();
                            BigDecimal orderItemReturnAgreeAmount = orderItem.getOrderItemReturnAgreeAmount();
                            Integer orderItemReturnAgreeNum = Optional.ofNullable(orderItem.getOrderItemReturnAgreeNum()).orElse(0);

                            orderItem.setOrderItemReturnAgreeAmount(NumberUtil.add(orderItemReturnAgreeAmount, returnItemSubtotal));
                            orderItem.setOrderItemReturnAgreeNum(orderItemReturnAgreeNum + returnItemNum);

                            // 未结算才发放用金
                            if (CheckUtil.isNotEmpty(returnCommisionFee)) {
                                BigDecimal returnItemCommisionFee = orderReturnItem.getReturnItemCommisionFee();
                                BigDecimal returnItemCommisionFeeRefund = Optional.ofNullable(orderItem.getOrderItemCommissionFeeRefund()).orElse(BigDecimal.ZERO);
                                orderItem.setOrderItemCommissionFeeRefund(NumberUtil.add(returnItemCommisionFee, returnItemCommisionFeeRefund));
                            }
                            orderItems.add(orderItem);
                        }
                    }

                    if (CollUtil.isNotEmpty(orderItems)) {
                        if (!orderItemRepository.edit(orderItems)) {
                            throw new BusinessException(__("修改订单商品数据失败"));
                        }
                    }
                }

                // 买家数据
                if (!consumeRecordRepository.saveOrUpdate(buyerConsumeRecord)) {
                    throw new BusinessException(__("增加买家流水数据失败"));
                }

                // 如果混合了积分，优先退积分
                if (orderPointsFee.compareTo(BigDecimal.ZERO) > 0 && orderPointsFee.compareTo(orderRefundAgreePoints) > 0) {
                    BigDecimal refundPoints = NumberUtil.round(NumberUtil.min(NumberUtil.sub(orderPointsFee, orderRefundAgreePoints)), 2);
                    buyerUserMoney = NumberUtil.round(NumberUtil.sub(buyerUserMoney, refundPoints), 2);

                    if (pointsVaueRate > 0) {
                        buyerUserPoints = NumberUtil.div(refundPoints, pointsVaueRate);
                    }
                    orderData.setOrderRefundAgreePoints(NumberUtil.add(orderRefundAgreePoints, refundPoints));
                }

                // 操作退款数据
                boolean flag = doRefundOrder(orderRefundFlag, userId, storeId, userResource, orderId, buyerUserMoney, buyerUserPoints, returnId);

                if (!flag) return true;

                if (!orderDataRepository.edit(orderData)) {
                    throw new BusinessException(__("修改详细信息失败"));
                }

                // 修改订单状态
                if (buyerUserMoney != null) {
                    // 流水记录
                    if (!consumeRecordRepository.saveOrUpdate(sellerConsumeRecord)) {
                        throw new BusinessException(__("写入卖家信息失败"));
                    }

                    /*sellerUserMoney = NumberUtil.add(buyerUserMoney.negate(), returnCommisionFee);
                    // todo ? get(商家用户编号)
                    UserResource resource = userResourceRepository.get(0);
                    resource.setUserMoney(NumberUtil.add(userResource.getUserMoney(), sellerUserMoney));
                    if (!userResourceRepository.edit(userResource)) {
                        throw new BusinessException(__("卖家用户退款失败"));
                    }*/
                }
                paidReturnIds.add(returnId);
            }
        }

        if (CollUtil.isNotEmpty(paidReturnIds)) {
            // 远程服务器订单更改放入
            // 本地服务器订单更改
            if (!setReturnPaidYes(paidReturnIds)) {
                throw new BusinessException(ResultCode.FAILED);
            }
        }

        return true;
    }


    /**
     * 修改为退款已支付状态
     *
     * @param returnIds
     * @return
     */
    @Override
    public boolean setReturnPaidYes(List<String> returnIds) {
        if (CollUtil.isEmpty(returnIds)) return false;

        OrderReturn orderReturn = new OrderReturn();
        orderReturn.setReturnIsPaid(true);
        orderReturn.setReturnIsPaid(false);

        QueryWrapper<OrderReturn> returnQueryWrapper = new QueryWrapper<>();
        returnQueryWrapper.in("return_id", returnIds);
        if (!orderReturnRepository.edit(orderReturn, returnQueryWrapper)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        List<OrderReturn> orderReturnList = orderReturnRepository.gets(returnIds);
        List<String> orderIds = orderReturnList.stream().map(OrderReturn::getOrderId).distinct().collect(Collectors.toList());

        if (CollUtil.isEmpty(orderIds)) return false;

        return true;
    }

    /**
     * 操作退款数据
     *
     * @param orderRefundFlag 退款标志
     * @param userId          用户编号
     * @param storeId         店铺编号
     * @param userResource    用户资源数据
     * @param orderId         订单编号
     * @param buyerUserMoney  买家用户余额
     * @param buyerUserPoints 买家用户积分
     * @param returnId        退款编号
     */
    private boolean doRefundOrder(boolean orderRefundFlag, Integer userId, Integer storeId, UserResource userResource, String orderId, BigDecimal buyerUserMoney, BigDecimal buyerUserPoints, String returnId) {
        if (orderRefundFlag) {
            // 读取在线支付信息，如果无在线支付信息，则余额支付，否则在线支付【联合支付】判断
            QueryWrapper<ConsumeDeposit> depositQueryWrapper = new QueryWrapper<>();
            depositQueryWrapper.apply(StrUtil.isNotEmpty(orderId), "FIND_IN_SET ('" + orderId + "', order_id )");
            ConsumeDeposit consumeDeposit = consumeDepositRepository.findOne(depositQueryWrapper);
            if (consumeDeposit != null) {
                Integer paymentChannelId = consumeDeposit.getPaymentChannelId();
                BigDecimal depositTotalFee = consumeDeposit.getDepositTotalFee();
                String channelCode = configBaseService.getPaymentChannelCode(paymentChannelId);

                // 微信，支付宝支付
                if (Arrays.asList("alipay", "wxpay").contains(channelCode)) {
                    BigDecimal dMoney = NumberUtil.round(NumberUtil.sub(buyerUserMoney, depositTotalFee), 2);
                    if (dMoney.compareTo(BigDecimal.ZERO) > 0) {
                        userResource.setUserMoney(NumberUtil.add(userResource.getUserMoney(), dMoney));
                        if (!userResourceRepository.edit(userResource)) {
                            throw new BusinessException(__("用户退款失败"));
                        }
                    }
                    if (buyerUserPoints.compareTo(BigDecimal.ZERO) > 0) {
                        if (!userResourceService.updatePoints(new UserPointsVo(userId, buyerUserPoints, PointsType.POINTS_TYPE_CONSUME_RETRUN, null, null, returnId, storeId))) {
                            throw new BusinessException(__("用户退积分失败"));
                        }
                    }

                    String depositTradeNo = consumeDeposit.getDepositTradeNo();
                    OrderReturn aReturn = new OrderReturn();
                    aReturn.setReturnId(returnId);
                    aReturn.setReturnChannelCode(channelCode);
                    aReturn.setDepositTradeNo(depositTradeNo);
                    aReturn.setPaymentChannelId(paymentChannelId);
                    aReturn.setTradePaymentAmount(depositTotalFee);
                    if (!orderReturnRepository.edit(aReturn)) {
                        throw new BusinessException(__("修改退单信息失败"));
                    }
                    // 执行第三方接口退款流程
                    doOnLineRefund(returnId);
                    return false;
                }
            }

            if (buyerUserMoney.compareTo(BigDecimal.ZERO) > 0) {
                userResource.setUserMoney(NumberUtil.add(userResource.getUserMoney(), buyerUserMoney));
                if (!userResourceRepository.edit(userResource)) {
                    throw new BusinessException(__("用户退款失败！"));
                }
            }

            if (buyerUserPoints.compareTo(BigDecimal.ZERO) > 0) {
                if (!userResourceRepository.points(new UserPointsVo(userId, buyerUserPoints, PointsType.POINTS_TYPE_CONSUME_RETRUN, returnId, null, returnId, storeId))) {
                    throw new BusinessException(__("用户退积分失败"));
                }
            }

            OrderReturn orderReturn = new OrderReturn();
            orderReturn.setReturnId(returnId);
            orderReturn.setReturnChannelFlag(1);
            if (!orderReturnRepository.edit(orderReturn)) {
                throw new BusinessException(__("修改退单信息失败"));
            }
        }
        return true;
    }

    /**
     * 执行线上支付退款
     *
     * @param returnId
     */
    private void doOnLineRefund(String returnId) {
        OrderReturn orderReturn = orderReturnRepository.get(returnId);
        String returnChannelCode = orderReturn.getReturnChannelCode();
        String depositTradeNo = orderReturn.getDepositTradeNo();
        BigDecimal tradePaymentAmount = orderReturn.getTradePaymentAmount();

        OrderReturn shopOrderReturn = new OrderReturn();
        shopOrderReturn.setReturnChannelFlag(1);
        shopOrderReturn.setOrderId(orderReturn.getOrderId());

        try {
            if ("alipay".equals(returnChannelCode)) {
                doAliPayRefund(returnId, depositTradeNo, tradePaymentAmount, shopOrderReturn);
            } else if ("wxpay".equals(returnChannelCode)) {
                doWxPayRefund(depositTradeNo, tradePaymentAmount, shopOrderReturn);
            }
            // 更新退款状态
            updateRefundOrderReturn(shopOrderReturn);
        } catch (AlipayApiException e) {
            LogUtil.error(ConstantLog.NETWORK, e);
        }
    }

    /**
     * 支付宝退款
     *
     * @param returnId
     * @param depositTradeNo
     * @param tradePaymentAmount
     * @param shopOrderReturn
     * @throws AlipayApiException
     */
    private void doAliPayRefund(String returnId, String depositTradeNo, BigDecimal tradePaymentAmount, OrderReturn shopOrderReturn) throws AlipayApiException {
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setOutTradeNo("");
        if (StrUtil.isNotEmpty(depositTradeNo)) {
            refundModel.setTradeNo(depositTradeNo);
        }
        refundModel.setRefundAmount(NumberUtil.toStr(tradePaymentAmount));
        try {
            String body = AliPayApi.tradeRefundToResponse(refundModel).getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            JSONObject alipay_trade_refund_response = (JSONObject) jsonObject.get("alipay_trade_refund_response");
            String msg = Convert.toStr(alipay_trade_refund_response.get("msg"));
            String code = Convert.toStr(alipay_trade_refund_response.get("code"));
            if (!"Success".equals(msg) || !"10000".equals(code)) {
                LogUtil.error(ConstantLog.NETWORK, String.format("return_id : %s 原路退回出错", returnId));
            }
            DateTime successTime = DateUtil.parse(Convert.toStr(alipay_trade_refund_response.get("gmt_refund_pay")), "yyyy-MM-dd HH:mm:ss");
            String outTradeNo = Convert.toStr(alipay_trade_refund_response.get("out_trade_no"));
            String tradeNo = Convert.toStr(alipay_trade_refund_response.get("trade_no"));

            shopOrderReturn.setDepositTradeNo(tradeNo);
            shopOrderReturn.setReturnChannelTransId(outTradeNo);
            shopOrderReturn.setReturnChannelTime(successTime);
        } catch (AlipayApiException e) {
            LogUtil.error(ConstantLog.NETWORK, e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 微信退款
     *
     * @param depositTradeNo
     * @param tradePaymentAmount
     * @param shopOrderReturn
     */
    private void doWxPayRefund(String depositTradeNo, BigDecimal tradePaymentAmount, OrderReturn shopOrderReturn) {
        if (StrUtil.isEmpty(depositTradeNo)) {
            depositTradeNo = "";
        }
        // 设置订单金额 单位为分且最小为1
        int amount = NumberUtil.mul(NumberUtil.round(tradePaymentAmount, 2), 100).intValue();

        WxPayV3Vo wxPayV3Vo = configBaseService.getWxPayV3Vo();
        String outRefundNo = PayKit.generateStr();
        String returnBuyerMessage = shopOrderReturn.getReturnBuyerMessage();

        RefundModel refundModel = new RefundModel()
                .setOut_refund_no(outRefundNo)
                .setTransaction_id(depositTradeNo)
                .setReason(StrUtil.isNotBlank(returnBuyerMessage) ? returnBuyerMessage : __("系统退款"))
                .setNotify_url(wxPayV3Vo.getNotifyUrl())
                .setAmount(new RefundAmount().setRefund(amount).setTotal(amount).setCurrency("CNY"))
                .setGoods_detail(null);

        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.REFUND.toString(),
                    wxPayV3Vo.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Vo.getKeyPath(),
                    JSON.toJSONString(refundModel)
            );

            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Vo.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            log.info(__("退款响应 {}"), response.getBody());
            if (!verifySignature) {
                LogUtil.error(ConstantLog.NETWORK, __("退款验签失败！"));
                throw new BusinessException(__("退款验签失败！"));
            }

            String responseBody = response.getBody();
            JSONObject refundObj = JSON.parseObject(responseBody);
            String status = refundObj.getString("status");
            if (StrUtil.isNotEmpty(status) && "PROCESSING".equals(status)) {
                shopOrderReturn.setDepositTradeNo(depositTradeNo);
                String refundId = refundObj.getString("refund_id");
                shopOrderReturn.setReturnChannelTransId(refundId);
                shopOrderReturn.setReturnChannelTime(new Date());
            } else {
                String errMsg = refundObj.getString("message");
                throw new BusinessException(errMsg);
            }

        } catch (Exception e) {
            LogUtil.error(ConstantLog.NETWORK, e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 更新退款订单状态
     *
     * @param orderReturn
     */
    public void updateRefundOrderReturn(OrderReturn orderReturn) {
        QueryWrapper<OrderReturn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderReturn.getOrderId())
                .eq("deposit_trade_no", orderReturn.getDepositTradeNo());
        OrderReturn dbOrderReturn = orderReturnRepository.findOne(queryWrapper);
        if (dbOrderReturn != null && CheckUtil.isEmpty(dbOrderReturn.getReturnChannelFlag())) {
            log.info(String.format("更新退款订单状态！returnId : %s", dbOrderReturn.getReturnId()));
            dbOrderReturn.setReturnChannelTransId(orderReturn.getReturnChannelTransId());
            dbOrderReturn.setReturnChannelTime(orderReturn.getReturnChannelTime());
            dbOrderReturn.setReturnChannelFlag(orderReturn.getReturnChannelFlag());
            if (!orderReturnRepository.edit(dbOrderReturn)) {
                LogUtil.error(ConstantLog.NETWORK, String.format("returnId : %s 退款失败！", orderReturn.getReturnId()));
            }
        }
    }

    /**
     * 获取证书序列号
     */
    private String getSerialNumber() {
        //证书序列号
        String serialNo = configBaseService.getConfig("wechat_pay_serial_no");

        if (StrUtil.isEmpty(serialNo)) {
            //这个是证书文件，后续要调整成读取证书文件的服务器存放地址  微信支付证书
            String cert = configBaseService.getConfig("wechat_pay_apiclient_cert");

            if (cert != null) {
                // 获取证书序列号
                X509Certificate certificate = PayKit.getCertificate(cert);
                if (certificate != null) {
                    serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
                }
            }
        }

        return serialNo;
    }

}
