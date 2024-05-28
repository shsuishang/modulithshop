package com.suisung.shopsuite.analytics.dao;

import com.suisung.shopsuite.analytics.model.vo.TradeAmountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnalytiscTradeDao {
    TradeAmountVo getTradeAmount(@Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("buyerId") Integer buyerId);
}
