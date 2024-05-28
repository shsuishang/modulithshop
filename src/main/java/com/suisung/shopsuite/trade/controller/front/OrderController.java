package com.suisung.shopsuite.trade.controller.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pt.service.ProductCommentService;
import com.suisung.shopsuite.trade.model.entity.OrderBase;
import com.suisung.shopsuite.trade.model.entity.OrderInvoice;
import com.suisung.shopsuite.trade.model.entity.OrderReturnItem;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.output.OrderAddOutput;
import com.suisung.shopsuite.trade.model.req.*;
import com.suisung.shopsuite.trade.model.res.OrderCancelRes;
import com.suisung.shopsuite.trade.model.res.OrderCommentRes;
import com.suisung.shopsuite.trade.model.res.OrderReceiveRes;
import com.suisung.shopsuite.trade.model.vo.CheckoutItemVo;
import com.suisung.shopsuite.trade.model.vo.OrderVo;
import com.suisung.shopsuite.trade.repository.OrderReturnItemRepository;
import com.suisung.shopsuite.trade.service.OrderBaseService;
import com.suisung.shopsuite.trade.service.OrderInvoiceService;
import com.suisung.shopsuite.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-26
 */
@Api(tags = "订单表")
@RestController
@RequestMapping("/front/trade/order")
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductCommentService productCommentService;

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private OrderReturnItemRepository orderReturnItemRepository;

    @ApiOperation(value = "订单列表信息", notes = "订单列表信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderVo>> list(OrderInfoListReq orderInfoListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        orderInfoListReq.setUserId(userId);
        Page<OrderVo> pageList = orderService.lists(orderInfoListReq);

        return success(pageList);
    }

    @ApiOperation(value = "订单详细信息", notes = "订单详细信息")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public CommonRes<?> detail(@RequestParam("order_id") String orderId) {
        Integer userId = ContextUtil.checkLoginUserId();
        OrderVo orderBase = orderService.detail(orderId);

        if (CheckUtil.checkDataRights(userId, orderBase, OrderVo::getUserId)) {
            return success(orderBase);
        }

        return fail();
    }

    @ApiOperation(value = "订单详细信息", notes = "订单详细信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(OrderAddReq orderBaseAddReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        ContextUser user = ContextUtil.getLoginUser();
        CheckoutInput input = BeanUtil.copyProperties(orderBaseAddReq, CheckoutInput.class);

        //处理数据
        input.setUserId(user.getUserId());
        input.setUserNickname(user.getUserNickname());

        //消息
        Map<String, String> orderMessageMap = JSONUtil.parseObject(orderBaseAddReq.getOrderMessage(), Map.class);

        // Convert keys to integers and create a new Map<Integer, String>
        Map<Integer, String> orderMessage = new HashMap<>();
        for (Map.Entry<String, String> entry : orderMessageMap.entrySet()) {
            Integer key = Integer.parseInt(entry.getKey());
            String value = entry.getValue();
            orderMessage.put(key, value);
        }

        //优惠券
        List<Integer> ts = Convert.toList(Integer.class, orderBaseAddReq.getUserVoucherIds());

        //下单商品
        List<CheckoutItemVo> items = new ArrayList<>();

        input.setUserVoucherIds(ts);
        input.setMessage(orderMessage);
        input.setItems(items);

        List<String> itemInfoRow = StrUtil.split(orderBaseAddReq.getCartId(), ",");

        for (String item : itemInfoRow) {
            long[] item_row = StrUtil.splitToLong(item, "|");

            if (item_row[1] <= 0) {
                throw new BusinessException(__("购买数量最低为 1 哦~"));
            }

            CheckoutItemVo checkoutItemVo = new CheckoutItemVo();
            checkoutItemVo.setItemId(item_row[0]);
            checkoutItemVo.setCartQuantity(Convert.toInt(item_row[1]));
            checkoutItemVo.setCartId(item_row[2]);

            items.add(checkoutItemVo);
        }

        OrderAddOutput success = orderService.add(input);

        if (CollUtil.isNotEmpty(success.getOrderIds())) {
            return success(success);
        }

        return fail();
    }

    @ApiOperation(value = "取消订单", notes = "取消订单")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public CommonRes<OrderCancelRes> cancel(OrderCancelReq req) {
        Integer userId = ContextUtil.checkLoginUserId();
        OrderCancelRes res = new OrderCancelRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");
        String orderCancelReason = req.getOrderCancelReason();

        List<OrderBase> orderBaseList = orderBaseService.gets(orderIdList);
        if (!CheckUtil.checkDataRights(userId, orderBaseList, OrderBase::getUserId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        for (String orderId : orderIdList) {
            try {
                Boolean flag = orderService.cancel(orderId, orderCancelReason);

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);
                throw new BusinessException(e.getMessage());
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @ApiOperation(value = "确认收货", notes = "确认收货")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonRes<OrderReceiveRes> receive(OrderReceiveReq req) {
        Integer userId = ContextUtil.checkLoginUserId();
        OrderReceiveRes res = new OrderReceiveRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");


        List<OrderBase> orderBaseList = orderBaseService.gets(orderIdList);
        if (!CheckUtil.checkDataRights(userId, orderBaseList, OrderBase::getUserId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        QueryWrapper<OrderReturnItem> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("order_id", req.getOrderId());
        List<OrderReturnItem> orderReturnItems = orderReturnItemRepository.find(itemQueryWrapper);
        if (CollUtil.isNotEmpty(orderReturnItems)) {
            Optional<OrderReturnItem> itemOptional = orderReturnItems.stream().filter(item -> !item.getReturnStateId().equals(StateCode.RETURN_PROCESS_FINISH) && !item.getReturnStateId().equals(StateCode.RETURN_PROCESS_CANCEL)).findFirst();
            if (itemOptional.isPresent()) {
                throw new BusinessException(__("有订单在售后处理中，不能确认收货"));
            }
        }

        for (String orderId : orderIdList) {
            try {
                Boolean flag = orderService.receive(orderId, "");

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);
                throw new BusinessException(e.getMessage());
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @ApiOperation(value = "订单评价-读取订单商品", notes = "订单评价-读取订单商品")
    @RequestMapping(value = "/storeEvaluationWithContent", method = RequestMethod.GET)
    public CommonRes<OrderCommentRes> storeEvaluationWithContent(@RequestParam(name = "order_id") String order_id) {
        return success(productCommentService.storeEvaluationWithContent(order_id));
    }

    @ApiOperation(value = "订单评价", notes = "订单评价")
    @RequestMapping(value = "/addOrderComment", method = RequestMethod.POST)
    public CommonRes addOrderComment(OrderCommentReq orderCommentReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        OrderBase orderBase = orderBaseService.get(orderCommentReq.getOrderId());

        if (CheckUtil.checkDataRights(userId, orderBase, OrderBase::getUserId)) {
            productCommentService.addOrderComment(orderCommentReq);
        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        return success();
    }

    @ApiOperation(value = "用户中心订单数量", notes = "用户中心订单数量")
    @RequestMapping(value = "/getOrderNum", method = RequestMethod.GET)
    public CommonRes<?> getOrderNum() {
        Integer userId = ContextUtil.checkLoginUserId();
        return success(orderService.getOrderStatisticsInfo(userId));
    }

    @Autowired
    private OrderInvoiceService orderInvoiceService;

    @ApiOperation(value = "订单发票管理表-分页列表查询", notes = "订单发票管理表-分页列表查询")
    @RequestMapping(value = "/listInvoice", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderInvoice>> listInvoice(OrderInvoiceListReq orderInvoiceListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        orderInvoiceListReq.setUserId(userId);
        IPage<OrderInvoice> pageList = orderInvoiceService.lists(orderInvoiceListReq);

        return success(pageList);
    }

    @ApiOperation(value = "申请订单发票", notes = "申请订单发票")
    @RequestMapping(value = "/addOrderInvoice", method = RequestMethod.POST)
    public CommonRes<?> addOrderInvoice(OrderInvoiceAddReq orderInvoiceAddReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        orderInvoiceAddReq.setUserId(userId);
        boolean success = orderService.addOrderInvoice(orderInvoiceAddReq);

        if (success) {
            return success();
        }

        return fail();
    }
}
