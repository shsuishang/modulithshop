package com.suisung.shopsuite.analytics.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.analytics.dao.AnalyticsUserDao;
import com.suisung.shopsuite.analytics.model.input.AnalyticsProductInput;
import com.suisung.shopsuite.analytics.model.input.TimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;
import com.suisung.shopsuite.analytics.model.vo.CommonNumVo;
import com.suisung.shopsuite.analytics.model.vo.RegUserVo;
import com.suisung.shopsuite.analytics.service.AnalyticsUserService;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.TimeRange;
import com.suisung.shopsuite.common.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalyticsUserServiceImpl implements AnalyticsUserService {

    @Autowired
    private AnalyticsUserDao analyticsUserDao;

    /**
     * 计算本月新增用户量，今日新增用户量，昨日新增用户量，以及日环比
     *
     * @return DashboardTopRes
     */
    @Override
    public DashboardTopRes getRegUser() {
        DashboardTopRes topRes = new DashboardTopRes();

        // 获取当日新增用户量
        TimeRange range = TimeUtil.today();
        RegUserVo todayRegUser = analyticsUserDao.getRegUser(range.getStart(), range.getEnd());
        if (todayRegUser != null) {
            topRes.setToday(todayRegUser.getNum());
        }

        //昨日
        range = TimeUtil.yestoday();
        RegUserVo yestodayRegUser = analyticsUserDao.getRegUser(range.getStart(), range.getEnd());

        if (ObjectUtil.isNotEmpty(yestodayRegUser)) {
            topRes.setYestoday(yestodayRegUser.getNum());

            // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
            BigDecimal daym2m = BigDecimal.ZERO;
            if (yestodayRegUser.getNum().compareTo(BigDecimal.ZERO) != 0) {
                daym2m = (todayRegUser.getNum().subtract(yestodayRegUser.getNum())).divide(yestodayRegUser.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                //.multiply(new BigDecimal("100"));
            } else {

            }

            topRes.setDaym2m(daym2m);
        }

        //本月
        range = TimeUtil.month();
        RegUserVo monthRegUser = analyticsUserDao.getRegUser(range.getStart(), range.getEnd());
        if (ObjectUtil.isNotEmpty(monthRegUser)) {
            topRes.setMonth(monthRegUser.getNum());
        }

        return topRes;
    }

    @Override
    public List<TimelineOutput> getUserTimeLine(TimelineInput input) {
        return analyticsUserDao.getUserTimeLine(input.getStime(), input.getEtime());
    }

    /**
     * 会员数
     *
     * @param input
     * @return
     */
    @Override
    public AnalyticsNumOutput getUserNum(TimelineInput input) {

        AnalyticsNumOutput topRes = new AnalyticsNumOutput();

        // 获取当前周期内数据
        RegUserVo regUser = analyticsUserDao.getRegUser(input.getStime(), input.getEtime());
        CommonNumVo currentRegNum = BeanUtil.copyProperties(regUser, CommonNumVo.class);

        if (currentRegNum != null) {
            topRes.setCurrent(currentRegNum.getNum());
        }

        //上个周期
        AnalyticsProductInput preInput = BeanUtil.copyProperties(input, AnalyticsProductInput.class);

        if (CheckUtil.isNotEmpty(input.getStime()) && CheckUtil.isNotEmpty(input.getEtime())) {
            preInput.setStime(input.getStime() - (input.getEtime() - input.getStime()));
            preInput.setEtime(input.getStime());


            RegUserVo preRegUser = analyticsUserDao.getRegUser(preInput.getStime(), preInput.getEtime());
            CommonNumVo preRegNum = BeanUtil.copyProperties(preRegUser, CommonNumVo.class);

            if (ObjectUtil.isNotEmpty(preRegNum)) {
                topRes.setPre(preRegNum.getNum());

                // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
                BigDecimal daym2m = BigDecimal.ZERO;
                if (preRegNum.getNum().compareTo(BigDecimal.ZERO) != 0) {
                    daym2m = (currentRegNum.getNum().subtract(preRegNum.getNum())).divide(preRegNum.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                    //.multiply(new BigDecimal("100"));
                } else {

                }

                topRes.setDaym2m(daym2m);
            }
        }

        return topRes;
    }
}
