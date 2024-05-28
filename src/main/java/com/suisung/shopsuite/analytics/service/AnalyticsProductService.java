package com.suisung.shopsuite.analytics.service;

import com.suisung.shopsuite.analytics.model.input.AnalyticsProductInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;

public interface AnalyticsProductService {
    /**
     * 订单数量
     *
     * @return
     */
    AnalyticsNumOutput getProductNum(AnalyticsProductInput input);
}
