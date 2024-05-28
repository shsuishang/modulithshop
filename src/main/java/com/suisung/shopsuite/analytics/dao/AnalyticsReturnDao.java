package com.suisung.shopsuite.analytics.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.suisung.shopsuite.analytics.model.input.OrderItemNumTimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsOrderItemNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.vo.CommonNumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalyticsReturnDao {
    /**
     * @param startTime
     * @param endTime
     * @return
     */
    CommonNumVo getReturnNum(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("return_state_ids") List<Integer> returnStateIds);

    @InterceptorIgnore(tenantLine = "true")
    List<TimelineOutput> getReturnAmountTimeline(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("return_state_ids") List<Integer> returnStateIds);

    CommonNumVo getReturnAmount(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("return_state_ids") List<Integer> returnStateIds);

    List<TimelineOutput> getReturnTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("return_state_ids") List<Integer> returnStateIds);

    List<TimelineOutput> getReturnItemNumTimeLine(@Param("params") OrderItemNumTimelineInput params);

    CommonNumVo getReturnItemNum(@Param("params") OrderItemNumTimelineInput params);

    List<AnalyticsOrderItemNumOutput> listReturnItemNum(@Param("params") OrderItemNumTimelineInput params);

    @InterceptorIgnore(tenantLine = "true")
    List<TimelineOutput> getReturnCustomerNumTimeline(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
