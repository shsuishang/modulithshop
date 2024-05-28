package com.suisung.shopsuite.trade.controller.front;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.trade.model.entity.OrderReturn;
import com.suisung.shopsuite.trade.model.input.OrderReturnInput;
import com.suisung.shopsuite.trade.model.req.OrderReturnAddReq;
import com.suisung.shopsuite.trade.model.req.OrderReturnEditReq;
import com.suisung.shopsuite.trade.model.req.OrderReturnListReq;
import com.suisung.shopsuite.trade.model.res.OrderReturnRes;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemInputVo;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemVo;
import com.suisung.shopsuite.trade.service.OrderReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "退款退货")
@RestController
@RequestMapping("/front/trade/orderReturn")
public class ReturnController extends BaseController {

    @Autowired
    private OrderReturnService orderReturnService;

    @ApiOperation(value = "退款退货列表", notes = "退款退货列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderReturnRes>> list(OrderReturnListReq orderReturnListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        orderReturnListReq.setBuyerUserId(userId);
        IPage<OrderReturnRes> page = orderReturnService.getList(orderReturnListReq);

        return success(page);
    }

    @ApiOperation(value = "读取退款退货", notes = "退款退货列表")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<OrderReturnRes> get(@RequestParam("return_id") String returnId) {
        OrderReturnRes orderReturnRes = orderReturnService.getReturn(returnId);

        return success(orderReturnRes);
    }

    @ApiOperation(value = "确认退货物流单号", notes = "确认退货物流单号")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(OrderReturnEditReq orderReturnEditReq) {
        boolean success = false;
        Integer userId = ContextUtil.checkLoginUserId();
        OrderReturn orderReturn = BeanUtil.copyProperties(orderReturnEditReq, OrderReturn.class);
        OrderReturn returnOrder = orderReturnService.get(orderReturnEditReq.getReturnId());

        if (CheckUtil.checkDataRights(userId, returnOrder, OrderReturn::getBuyerUserId)) {
            success = orderReturnService.editReturn(orderReturn);
        }

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "取消退款订单", notes = "取消退款订单")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public CommonRes<?> cancel(@RequestParam(name = "return_id") String returnId) {
        Integer userId = ContextUtil.checkLoginUserId();
        boolean success = orderReturnService.cancel(returnId, userId);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "订单item详情,列出订单的item，及退款详情", notes = "订单item详情,列出订单的item，及退款详情")
    @RequestMapping(value = "/returnItem", method = RequestMethod.GET)
    public CommonRes<OrderReturnItemVo> returnItem(@RequestParam(name = "order_id") String orderId,
                                                   @RequestParam(name = "order_item_id") String orderItemId) {
        Integer userId = ContextUtil.checkLoginUserId();
        OrderReturnItemVo orderReturnItemVo = orderReturnService.returnItem(orderId, orderItemId, userId);

        return success(orderReturnItemVo);
    }

    @ApiOperation(value = "添加退款退货", notes = "添加退款退货")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(OrderReturnAddReq req) {
        Integer userId = ContextUtil.checkLoginUserId();

        OrderReturnInput orderReturnInput = BeanUtil.copyProperties(req, OrderReturnInput.class);
        orderReturnInput.setUserId(userId);
        orderReturnInput.setReturnAllFlag(false);

        OrderReturnItemInputVo returnItemInputVo = new OrderReturnItemInputVo();
        returnItemInputVo.setOrderItemId(req.getOrderItemId());
        returnItemInputVo.setReturnRefundAmount(req.getReturnRefundAmount());
        returnItemInputVo.setReturnItemNum(req.getReturnItemNum());
        orderReturnInput.getReturnItems().add(returnItemInputVo);

        String retrunId = orderReturnService.addItem(orderReturnInput);

        orderReturnInput.setReturnId(retrunId);

        return success(orderReturnInput);
    }
}
