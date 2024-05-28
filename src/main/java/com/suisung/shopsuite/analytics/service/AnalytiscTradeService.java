package com.suisung.shopsuite.analytics.service;

import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;

public interface AnalytiscTradeService {
    /**
     * 仪表盘销售额统计
     *
     * @return
     */
    DashboardTopRes getSalesAmount();

}
