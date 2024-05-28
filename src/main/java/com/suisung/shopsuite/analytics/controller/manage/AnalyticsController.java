package com.suisung.shopsuite.analytics.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.suisung.shopsuite.analytics.model.input.*;
import com.suisung.shopsuite.analytics.model.output.AnalyticsAccessItemOutput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsOrderItemNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.req.*;
import com.suisung.shopsuite.analytics.model.res.AmountRes;
import com.suisung.shopsuite.analytics.model.res.DashBoardTimelineRes;
import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;
import com.suisung.shopsuite.analytics.service.*;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage/analytics")
public class AnalyticsController extends BaseController {

    @Autowired
    private AnalyticsOrderService analyticsOrderService;

    @Autowired
    private AnalytiscTradeService analytiscTradeService;

    @Autowired
    private AnalyticsUserService analyticsUserService;

    @Autowired
    private AnalyticsSysService analyticsSysService;

    @Autowired
    private AnalyticsProductService analyticsProductService;

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取销售额")
    @RequestMapping(value = "/trade/getSalesAmount", method = RequestMethod.GET)
    public CommonRes<DashboardTopRes> getSalesAmount() {
        DashboardTopRes topRes = analytiscTradeService.getSalesAmount();

        return success(topRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取销售额")
    @RequestMapping(value = "/order/getOrderAmount", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getOrderAmount(AnalyticsOrderReq req) {
        AnalyticsOrderInput timelineInput = BeanUtil.copyProperties(req, AnalyticsOrderInput.class);
        AnalyticsNumOutput timelineRes = analyticsOrderService.getOrderAmount(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取用户访问量")
    @RequestMapping(value = "/user/getVisitor", method = RequestMethod.GET)
    public CommonRes<DashboardTopRes> getVisitor() {
        DashboardTopRes topRes = analyticsSysService.getVisitor();

        return success(topRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取订单量")
    @RequestMapping(value = "/order/getOrderNumToday", method = RequestMethod.GET)
    public CommonRes<DashboardTopRes> getOrderNumToday() {
        DashboardTopRes topRes = analyticsOrderService.getOrderNum();

        return success(topRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取订单量")
    @RequestMapping(value = "/order/getOrderNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getOrderNum(AnalyticsOrderReq req) {
        AnalyticsOrderInput timelineInput = BeanUtil.copyProperties(req, AnalyticsOrderInput.class);
        AnalyticsNumOutput timelineRes = analyticsOrderService.getOrderNum(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取新增用户")
    @RequestMapping(value = "/user/getRegUser", method = RequestMethod.GET)
    public CommonRes<DashboardTopRes> getRegUser() {
        DashboardTopRes topRes = analyticsUserService.getRegUser();

        return success(topRes);
    }


    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "商品数量")
    @RequestMapping(value = "/product/getProductNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getProductNum(AnalyticsProductReq req) {
        AnalyticsProductInput timelineInput = BeanUtil.copyProperties(req, AnalyticsProductInput.class);
        AnalyticsNumOutput topRes = analyticsProductService.getProductNum(timelineInput);

        return success(topRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/user/getUserTimeLine", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getUserTimeLine(AccessItemTimelineReq req) {
        TimelineInput timelineInput = BeanUtil.copyProperties(req, TimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsUserService.getUserTimeLine(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "获取新增用户")
    @RequestMapping(value = "/user/getUserNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getUserNum(TimelineReq req) {
        TimelineInput timelineInput = BeanUtil.copyProperties(req, TimelineInput.class);
        AnalyticsNumOutput timelineRes = analyticsUserService.getUserNum(timelineInput);

        return success(timelineRes);
    }


    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "仪表板看板柱形图数据", notes = "[订单数据，用户数据，商品数据，销售额数据]")
    @RequestMapping(value = "/order/getDashboardTimeLine", method = RequestMethod.GET)
    public CommonRes<DashBoardTimelineRes> getDashboardTimeLine(TimelineReq req) {
        TimelineInput timelineInput = BeanUtil.copyProperties(req, TimelineInput.class);
        DashBoardTimelineRes dashBoardTimeLineRes = analyticsOrderService.getDashboardTimeLine(timelineInput);

        return success(dashBoardTimeLineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "购买商品顾客数量统计")
    @RequestMapping(value = "/order/getOrderCustomerNumTimeline", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getOrderCustomerNumTimeline(TimelineReq req) {
        TimelineInput timelineInput = BeanUtil.copyProperties(req, TimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsOrderService.getOrderCustomerNumTimeline(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "订单销售金额对比图")
    @RequestMapping(value = "/order/getSaleOrderAmount", method = RequestMethod.GET)
    public CommonRes<List<AmountRes>> getSaleOrderAmount(TimelineReq req) {
        TimelineInput timelineInput = BeanUtil.copyProperties(req, TimelineInput.class);
        List<AmountRes> amountRes = analyticsOrderService.getSaleOrderAmount(timelineInput);

        return success(amountRes);
    }


    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "订单数量统计")
    @RequestMapping(value = "/order/getOrderNumTimeline", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getOrderNumTimeline(TimelineReq req) {
        TimelineInput timelineInput = BeanUtil.copyProperties(req, TimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsOrderService.getOrderNumTimeline(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "订单商品销量统计")
    @RequestMapping(value = "/order/getOrderItemNumTimeLine", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getOrderItemNumTimeLine(OrderItemNumTimelineReq req) {
        OrderItemNumTimelineInput timelineInput = BeanUtil.copyProperties(req, OrderItemNumTimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsOrderService.getOrderItemNumTimeLine(timelineInput);

        return success(timelineRes);
    }


    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "订单商品销量统计")
    @RequestMapping(value = "/order/listOrderItemNum", method = RequestMethod.GET)
    public CommonRes<List<AnalyticsOrderItemNumOutput>> listOrderItemNum(OrderItemNumTimelineReq req) {
        OrderItemNumTimelineInput timelineInput = BeanUtil.copyProperties(req, OrderItemNumTimelineInput.class);
        List<AnalyticsOrderItemNumOutput> timelineRes = analyticsOrderService.listOrderItemNum(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "访问商品统计")
    @RequestMapping(value = "/history/getAccessItemTimeLine", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getAccessItemTimeLine(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsSysService.getAccessItemTimeLine(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "用户访问商品统计")
    @RequestMapping(value = "/history/getAccessItemUserTimeLine", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getAccessItemUserTimeLine(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsSysService.getAccessItemUserTimeLine(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "访客数")
    @RequestMapping(value = "/history/getAccessVisitorTimeLine", method = RequestMethod.GET)
    public CommonRes<List<TimelineOutput>> getAccessVisitorTimeLine(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        List<TimelineOutput> timelineRes = analyticsSysService.getAccessVisitorTimeLine(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "访客数")
    @RequestMapping(value = "/history/getAccessVisitorNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getAccessVisitorNum(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        AnalyticsNumOutput timelineRes = analyticsSysService.getAccessVisitorNum(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "浏览量")
    @RequestMapping(value = "/history/getAccessNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getAccessNum(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        AnalyticsNumOutput topRes = analyticsSysService.getAccessNum(timelineInput);

        return success(topRes);
    }


    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "商品浏览量")
    @RequestMapping(value = "/history/getAccessItemNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getAccessItemNum(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        AnalyticsNumOutput topRes = analyticsSysService.getAccessItemNum(timelineInput);

        return success(topRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "商品浏览排行")
    @RequestMapping(value = "/history/listAccessItem", method = RequestMethod.GET)
    public CommonRes<List<AnalyticsAccessItemOutput>> listAccessItem(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        List<AnalyticsAccessItemOutput> timelineRes = analyticsSysService.listAccessItem(timelineInput);

        return success(timelineRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "商品访客数")
    @RequestMapping(value = "/history/getAccessItemUserNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getAccessItemUserNum(AccessItemTimelineReq req) {
        AccessItemTimelineInput timelineInput = BeanUtil.copyProperties(req, AccessItemTimelineInput.class);
        AnalyticsNumOutput topRes = analyticsSysService.getAccessItemUserNum(timelineInput);

        return success(topRes);
    }

    @PreAuthorize("hasAuthority('/manage/analytics/index/list')")
    @ApiOperation(value = "商品访客数")
    @RequestMapping(value = "/order/getOrderItemNum", method = RequestMethod.GET)
    public CommonRes<AnalyticsNumOutput> getOrderItemNum(OrderItemNumTimelineReq req) {
        OrderItemNumTimelineInput timelineInput = BeanUtil.copyProperties(req, OrderItemNumTimelineInput.class);
        AnalyticsNumOutput topRes = analyticsOrderService.getOrderItemNum(timelineInput);

        return success(topRes);
    }

}
