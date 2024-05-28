package com.suisung.shopsuite.analytics.dao;

import com.suisung.shopsuite.analytics.model.input.AccessItemTimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsAccessItemOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.vo.CommonNumVo;
import com.suisung.shopsuite.analytics.model.vo.VisitorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalyticsSysDao {
    VisitorVo getVisitor(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    CommonNumVo getVisitorNum(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    CommonNumVo getAccessNum(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<TimelineOutput> getAccessItemTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("itemId") Long itemId);

    CommonNumVo getAccessItemNum(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("itemId") Long itemId);

    List<TimelineOutput> getAccessItemUserTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("itemId") Long itemId);

    CommonNumVo getAccessItemUserNum(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("itemId") Long itemId);

    List<TimelineOutput> getAccessVisitorTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<AnalyticsAccessItemOutput> listAccessItem(@Param("params") AccessItemTimelineInput params);
}
