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
package com.suisung.shopsuite.trade.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.excel.ExcelUtil;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.trade.model.entity.OrderStateLog;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.input.OrderPickingInput;
import com.suisung.shopsuite.trade.model.input.OrderShippingInput;
import com.suisung.shopsuite.trade.model.output.OrderAddOutput;
import com.suisung.shopsuite.trade.model.req.*;
import com.suisung.shopsuite.trade.model.res.*;
import com.suisung.shopsuite.trade.model.vo.CheckoutItemVo;
import com.suisung.shopsuite.trade.model.vo.OrderVo;
import com.suisung.shopsuite.trade.model.vo.PickingItem;
import com.suisung.shopsuite.trade.service.OrderService;
import com.suisung.shopsuite.trade.service.OrderStateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 订单详细信息-检索不分表也行，cache 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Api(tags = "订单详细信息-检索不分表也行，cache")
@RestController
@RequestMapping("/manage/trade/orderBase")
public class OrderBaseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderBaseController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStateLogService orderStateLogService;

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/list')")
    @ApiOperation(value = "订单详细信息", notes = "订单详细信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderVo>> list(OrderInfoListReq orderInfoListReq) {
        Page<OrderVo> pageList = orderService.lists(orderInfoListReq);

        return success(pageList);
    }

    @ApiOperation(value = "订单详细信息", notes = "订单详细信息")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public CommonRes<OrderVo> detail(@RequestParam("order_id") String orderId) {
        OrderVo orderBase = orderService.detail(orderId);

        return success(orderBase);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/add')")
    @ApiOperation(value = "代客下单", notes = "代客下单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(OrderAddReq orderBaseAddReq) {
        CheckoutInput orderBase = BeanUtil.copyProperties(orderBaseAddReq, CheckoutInput.class);

        if (StrUtil.isEmpty(orderBaseAddReq.getProductItems())) {
            throw new BusinessException(__("商品数据为空！"));
        }
        List<CheckoutItemVo> checkoutItemVos = JSONUtil.parseArray(orderBaseAddReq.getProductItems(), CheckoutItemVo.class);

        if (CollectionUtil.isEmpty(checkoutItemVos)) {
            throw new BusinessException(__("商品数据错误！"));
        }

        orderBase.setItems(checkoutItemVos);
        OrderAddOutput output = orderService.replaceAdd(orderBase);

        if (CollUtil.isNotEmpty(output.getOrderIds())) {
            return success(output);
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/cancel')")
    @ApiOperation(value = "取消订单", notes = "取消订单")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public CommonRes<OrderCancelRes> cancel(OrderCancelReq req) {
        OrderCancelRes res = new OrderCancelRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");
        String orderCancelReason = req.getOrderCancelReason();

        for (String orderId : orderIdList) {
            try {
                Boolean flag = orderService.cancel(orderId, orderCancelReason);

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);
//                throw new BusinessException(e.getMessage());
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/review')")
    @ApiOperation(value = "审核订单", notes = "审核订单")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public CommonRes<OrderReviewRes> review(OrderReviewReq req) {
        OrderReviewRes res = new OrderReviewRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");
        String orderReviewReason = req.getOrderReviewReason();

        for (String orderId : orderIdList) {
            try {
                Boolean flag = orderService.review(orderId, orderReviewReason);

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);

                if (orderIdList.size() == 1) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/finance')")
    @ApiOperation(value = "财务审核", notes = "财务审核")
    @RequestMapping(value = "/finance", method = RequestMethod.POST)
    public CommonRes<OrderFinanceRes> finance(OrderFinanceReq req) {
        OrderFinanceRes res = new OrderFinanceRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");
        String orderFinanceReason = req.getOrderFinanceReason();

        for (String orderId : orderIdList) {
            try {
                Boolean flag = orderService.finance(orderId, orderFinanceReason);

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);

                if (orderIdList.size() == 1) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/picking')")
    @ApiOperation(value = "出库审核", notes = "出库审核")
    @RequestMapping(value = "/picking", method = RequestMethod.POST)
    public CommonRes<OrderPickingRes> picking(OrderPickingReq req) {
        OrderPickingRes res = new OrderPickingRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");
        List<PickingItem> pickingItems = new ArrayList<>();

        if (StrUtil.isNotEmpty(req.getItems())) {
            pickingItems = JSONUtil.parseArray(req.getItems(), PickingItem.class);
        }

        for (String orderId : orderIdList) {
            try {
                OrderPickingInput input = new OrderPickingInput();
                BeanUtils.copyProperties(req, input);
                input.setOrderId(orderId);
                input.setItems(pickingItems);

                Boolean flag = orderService.picking(input);

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);

                if (orderIdList.size() == 1) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/shipping')")
    @ApiOperation(value = "发货审核", notes = "发货审核")
    @RequestMapping(value = "/shipping", method = RequestMethod.POST)
    public CommonRes<OrderShippingRes> shipping(OrderShippingReq req) {
        OrderShippingRes res = new OrderShippingRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");

        for (String orderId : orderIdList) {
            try {
                OrderShippingInput input = new OrderShippingInput();
                BeanUtils.copyProperties(req, input);
                input.setOrderId(orderId);

                Boolean flag = orderService.shipping(input);

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);

                if (orderIdList.size() == 1) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/receive')")
    @ApiOperation(value = "收货审核", notes = "收货审核")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonRes<OrderReceiveRes> receive(OrderReceiveReq req) {
        OrderReceiveRes res = new OrderReceiveRes();
        List<String> orderIdRes = res.getOrderId();
        List<String> orderIdList = StrUtil.split(req.getOrderId(), ",");

        for (String orderId : orderIdList) {
            try {
                Boolean flag = orderService.receive(orderId, "");

                if (flag) {
                    orderIdRes.add(orderId);
                }
            } catch (Exception e) {
                LogUtil.error(ConstantLog.TRADE, e);

                if (orderIdList.size() == 1) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }

        if (CollUtil.isEmpty(orderIdRes)) {
            throw new BusinessException("未更改到符合条件的订单！");
        }

        return success(res);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/list')")
    @ApiOperation(value = "订单日志", notes = "订单日志")
    @RequestMapping(value = "/listStateLog", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderStateLog>> listStateLog(OrderStateLogListReq req) {
        Page<OrderStateLog> pageList = orderStateLogService.lists(req);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/finance')")
    @ApiOperation(value = "批量修改订单-提现审核", notes = "批量修改订单-提现审核")
    @RequestMapping(value = "/doUpdateOrders", method = RequestMethod.POST)
    public CommonRes<?> doUpdateOrders(@RequestParam("order_ids") String orderIds) {
        boolean success = orderService.doUpdateOrders(Convert.toList(String.class, orderIds));

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/list')")
    @ApiOperation(value = "导出在线订单", notes = "导出在线订单")
    @RequestMapping(value = "/exportFile", method = RequestMethod.GET)
    public void exportFile(OrderInfoListReq orderInfoListReq, HttpServletResponse response) {
        Page<OrderVo> pageList = orderService.lists(orderInfoListReq);
        List<OrderVo> orderVos = pageList.getRecords();
        ExcelUtil.exportReport(response, 1, __("在线订单"), orderVos);
    }

}

