package com.suisung.shopsuite.analytics.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.suisung.shopsuite.analytics.model.input.OrderItemNumTimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsOrderItemNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.res.AmountRes;
import com.suisung.shopsuite.analytics.model.vo.CommonNumVo;
import com.suisung.shopsuite.analytics.model.vo.OrderNumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalyticsOrderDao {
    /**
     * @param startTime
     * @param endTime
     * @param orderStateId
     * @param orderIsPaid
     * @param userId
     * @param kindId
     * @return
     */
    OrderNumVo getOrderNum(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("order_state_id") List<Integer> orderStateId, @Param("order_is_paid") List<Integer> orderIsPaid, @Param("user_id") Integer userId, @Param("kind_id") Integer kindId);

    List<TimelineOutput> getPayTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @InterceptorIgnore(tenantLine = "true")
    List<AmountRes> getSaleOrderAmount(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    CommonNumVo getOrderAmount(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<TimelineOutput> getOrderTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<TimelineOutput> getOrderItemNumTimeLine(@Param("params") OrderItemNumTimelineInput params);

    CommonNumVo getOrderItemNum(@Param("params") OrderItemNumTimelineInput params);

    List<AnalyticsOrderItemNumOutput> listOrderItemNum(@Param("params") OrderItemNumTimelineInput params);

    @InterceptorIgnore(tenantLine = "true")
    List<TimelineOutput> getOrderCustomerNumTimeline(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
