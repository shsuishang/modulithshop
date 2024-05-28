package com.suisung.shopsuite.analytics.service;

import com.suisung.shopsuite.analytics.model.input.AnalyticsOrderInput;
import com.suisung.shopsuite.analytics.model.input.OrderItemNumTimelineInput;
import com.suisung.shopsuite.analytics.model.input.TimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsOrderItemNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.res.AmountRes;
import com.suisung.shopsuite.analytics.model.res.DashBoardTimelineRes;
import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;

import java.util.List;

public interface AnalyticsOrderService {

    /**
     * 仪表盘数据
     *
     * @param input
     * @return
     */
    DashBoardTimelineRes getDashboardTimeLine(TimelineInput input);

    /**
     * 订单销售额统计
     *
     * @param input
     * @return
     */
    List<AmountRes> getSaleOrderAmount(TimelineInput input);

    /**
     * 订单数量统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getOrderNumTimeline(TimelineInput input);

    /**
     * 商品销量统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getOrderItemNumTimeLine(OrderItemNumTimelineInput input);

    AnalyticsNumOutput getOrderItemNum(OrderItemNumTimelineInput input);

    List<AnalyticsOrderItemNumOutput> listOrderItemNum(OrderItemNumTimelineInput input);

    /**
     * 订单客户统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getOrderCustomerNumTimeline(TimelineInput input);

    /**
     * 订单数量
     *
     * @return
     */
    DashboardTopRes getOrderNum();

    AnalyticsNumOutput getOrderNum(AnalyticsOrderInput input);

    AnalyticsNumOutput getOrderAmount(AnalyticsOrderInput input);
}
