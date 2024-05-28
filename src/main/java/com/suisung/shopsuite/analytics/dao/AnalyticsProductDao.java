package com.suisung.shopsuite.analytics.dao;

import com.suisung.shopsuite.analytics.model.input.AnalyticsProductInput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.vo.CommonNumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalyticsProductDao {

    List<TimelineOutput> getProductTimeLine(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    CommonNumVo getProductNum(@Param("params") AnalyticsProductInput input);
}
