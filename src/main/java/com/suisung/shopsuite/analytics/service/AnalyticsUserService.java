package com.suisung.shopsuite.analytics.service;

import com.suisung.shopsuite.analytics.model.input.TimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;

import java.util.List;

public interface AnalyticsUserService {

    DashboardTopRes getRegUser();

    /**
     * 会员数统计
     *
     * @param input
     * @return
     */
    List<TimelineOutput> getUserTimeLine(TimelineInput input);


    /**
     * 会员数
     *
     * @param input
     * @return
     */
    AnalyticsNumOutput getUserNum(TimelineInput input);

}
