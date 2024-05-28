package com.suisung.shopsuite.analytics.service;

import com.suisung.shopsuite.analytics.model.input.AnalyticsReturnInput;
import com.suisung.shopsuite.analytics.model.input.OrderItemNumTimelineInput;
import com.suisung.shopsuite.analytics.model.input.TimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsOrderItemNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;

import java.util.List;

public interface AnalyticsReturnService {

    /**
     * 退单销售额统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getReturnAmountTimeline(TimelineInput input);

    /**
     * 退单数量统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getReturnNumTimeline(TimelineInput input);

    /**
     * 商品销量统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getReturnItemNumTimeLine(OrderItemNumTimelineInput input);

    AnalyticsNumOutput getReturnItemNum(OrderItemNumTimelineInput input);

    List<AnalyticsOrderItemNumOutput> listReturnItemNum(OrderItemNumTimelineInput input);

    /**
     * 退单客户统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getReturnCustomerNumTimeline(TimelineInput input);

    /**
     * 退单数量
     *
     * @return
     */
    AnalyticsNumOutput getReturnNum(AnalyticsReturnInput input);

    AnalyticsNumOutput getReturnAmount(AnalyticsReturnInput input);
}
