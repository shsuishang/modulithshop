package com.suisung.shopsuite.analytics.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.analytics.dao.AnalytiscTradeDao;
import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;
import com.suisung.shopsuite.analytics.model.vo.TradeAmountVo;
import com.suisung.shopsuite.analytics.service.AnalytiscTradeService;
import com.suisung.shopsuite.common.utils.TimeRange;
import com.suisung.shopsuite.common.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AnalytiscTradeServiceImpl implements AnalytiscTradeService {

    @Autowired
    private AnalytiscTradeDao analytiscTradeDao;

    /**
     * 计算本月销售额，今日销售额，昨日销售额，以及日环比
     *
     * @return DashboardTopRes
     */
    @Override
    public DashboardTopRes getSalesAmount() {
        DashboardTopRes topRes = new DashboardTopRes();

        // 获取当日交易额
        TimeRange range = TimeUtil.today();
        TradeAmountVo todayTradeAmount = analytiscTradeDao.getTradeAmount(range.getStart(), range.getEnd(), null);

        if (todayTradeAmount == null) {
            todayTradeAmount = new TradeAmountVo();
        } else {
            topRes.setToday(todayTradeAmount.getAmount());
        }

        //昨日
        range = TimeUtil.yestoday();
        TradeAmountVo yestodayTradeAmount = analytiscTradeDao.getTradeAmount(range.getStart(), range.getEnd(), null);

        if (ObjectUtil.isNotEmpty(yestodayTradeAmount)) {
            topRes.setYestoday(yestodayTradeAmount.getAmount());

            // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
            BigDecimal daym2m = BigDecimal.ZERO;
            if (yestodayTradeAmount.getAmount().compareTo(BigDecimal.ZERO) != 0) {
                daym2m = (todayTradeAmount.getAmount().subtract(yestodayTradeAmount.getAmount())).divide(yestodayTradeAmount.getAmount(), 2, BigDecimal.ROUND_HALF_UP);
                //.multiply(new BigDecimal("100"));
            } else {

            }

            topRes.setDaym2m(daym2m);
        }

        //本月
        range = TimeUtil.month();
        TradeAmountVo monthTradeAmount = analytiscTradeDao.getTradeAmount(range.getStart(), range.getEnd(), null);
        if (ObjectUtil.isNotEmpty(monthTradeAmount)) {
            topRes.setMonth(monthTradeAmount.getAmount());
        }

        return topRes;
    }

}
