package com.suisung.shopsuite.analytics.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.analytics.dao.AnalyticsSysDao;
import com.suisung.shopsuite.analytics.model.input.AccessItemTimelineInput;
import com.suisung.shopsuite.analytics.model.input.TimelineInput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsAccessItemOutput;
import com.suisung.shopsuite.analytics.model.output.AnalyticsNumOutput;
import com.suisung.shopsuite.analytics.model.output.TimelineOutput;
import com.suisung.shopsuite.analytics.model.res.DashboardTopRes;
import com.suisung.shopsuite.analytics.model.vo.CommonNumVo;
import com.suisung.shopsuite.analytics.model.vo.VisitorVo;
import com.suisung.shopsuite.analytics.service.AnalyticsSysService;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.TimeRange;
import com.suisung.shopsuite.common.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalyticsSysServiceImpl implements AnalyticsSysService {

    @Autowired
    private AnalyticsSysDao analyticsSysDao;

    /**
     * 计算本月访问量，今日访问量，昨日访问量，以及日环比
     *
     * @return DashboardTopRes
     */
    @Override
    public DashboardTopRes getVisitor() {
        DashboardTopRes topRes = new DashboardTopRes();

        // 获取当日访问量
        TimeRange range = TimeUtil.today();
        VisitorVo todayVisits = analyticsSysDao.getVisitor(range.getStart(), range.getEnd());
        if (todayVisits != null) {
            topRes.setToday(todayVisits.getNum());
        }

        //昨日
        range = TimeUtil.yestoday();
        VisitorVo yestodayVisits = analyticsSysDao.getVisitor(range.getStart(), range.getEnd());

        if (ObjectUtil.isNotEmpty(yestodayVisits)) {
            topRes.setYestoday(yestodayVisits.getNum());

            // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
            BigDecimal daym2m = BigDecimal.ZERO;
            if (yestodayVisits.getNum().compareTo(BigDecimal.ZERO) != 0) {
                daym2m = (todayVisits.getNum().subtract(yestodayVisits.getNum())).divide(yestodayVisits.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                //.multiply(new BigDecimal("100"));
            } else {

            }

            topRes.setDaym2m(daym2m);
        }

        //本月
        range = TimeUtil.month();
        VisitorVo monthVisits = analyticsSysDao.getVisitor(range.getStart(), range.getEnd());
        if (ObjectUtil.isNotEmpty(monthVisits)) {
            topRes.setMonth(monthVisits.getNum());
        }

        return topRes;

    }

    @Override
    public AnalyticsNumOutput getAccessVisitorNum(AccessItemTimelineInput input) {

        AnalyticsNumOutput topRes = new AnalyticsNumOutput();

        // 获取当前周期内数据
        CommonNumVo currentProductNum = analyticsSysDao.getVisitorNum(input.getStime(), input.getEtime());
        if (currentProductNum != null) {
            topRes.setCurrent(currentProductNum.getNum());
        }

        //上个周期
        AccessItemTimelineInput preInput = BeanUtil.copyProperties(input, AccessItemTimelineInput.class);

        if (CheckUtil.isNotEmpty(input.getStime()) && CheckUtil.isNotEmpty(input.getEtime())) {
            preInput.setStime(input.getStime() - (input.getEtime() - input.getStime()));
            preInput.setEtime(input.getStime());

            CommonNumVo preProductNum = analyticsSysDao.getVisitorNum(preInput.getStime(), preInput.getEtime());

            if (ObjectUtil.isNotEmpty(preProductNum)) {
                topRes.setPre(preProductNum.getNum());

                // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
                BigDecimal daym2m = BigDecimal.ZERO;
                if (preProductNum.getNum().compareTo(BigDecimal.ZERO) != 0) {
                    daym2m = (currentProductNum.getNum().subtract(preProductNum.getNum())).divide(preProductNum.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                    //.multiply(new BigDecimal("100"));
                } else {

                }

                topRes.setDaym2m(daym2m);
            }
        }

        return topRes;
    }

    @Override
    public AnalyticsNumOutput getAccessNum(AccessItemTimelineInput input) {

        AnalyticsNumOutput topRes = new AnalyticsNumOutput();

        // 获取当前周期内数据
        CommonNumVo currentProductNum = analyticsSysDao.getAccessNum(input.getStime(), input.getEtime());
        if (currentProductNum != null) {
            topRes.setCurrent(currentProductNum.getNum());
        }

        //上个周期
        AccessItemTimelineInput preInput = BeanUtil.copyProperties(input, AccessItemTimelineInput.class);

        if (CheckUtil.isNotEmpty(input.getStime()) && CheckUtil.isNotEmpty(input.getEtime())) {
            preInput.setStime(input.getStime() - (input.getEtime() - input.getStime()));
            preInput.setEtime(input.getStime());

            CommonNumVo preProductNum = analyticsSysDao.getAccessNum(preInput.getStime(), preInput.getEtime());

            if (ObjectUtil.isNotEmpty(preProductNum)) {
                topRes.setPre(preProductNum.getNum());

                // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
                BigDecimal daym2m = BigDecimal.ZERO;
                if (preProductNum.getNum().compareTo(BigDecimal.ZERO) != 0) {
                    daym2m = (currentProductNum.getNum().subtract(preProductNum.getNum())).divide(preProductNum.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                    //.multiply(new BigDecimal("100"));
                } else {

                }

                topRes.setDaym2m(daym2m);
            }
        }

        return topRes;
    }

    @Override
    public List<TimelineOutput> getAccessItemTimeLine(AccessItemTimelineInput input) {
        return analyticsSysDao.getAccessItemTimeLine(input.getStime(), input.getEtime(), input.getItemId());
    }

    @Override
    public AnalyticsNumOutput getAccessItemNum(AccessItemTimelineInput input) {
        AnalyticsNumOutput topRes = new AnalyticsNumOutput();

        // 获取当前周期内数据
        CommonNumVo currentProductNum = analyticsSysDao.getAccessItemNum(input.getStime(), input.getEtime(), input.getItemId());
        if (currentProductNum != null) {
            topRes.setCurrent(currentProductNum.getNum());
        }

        //上个周期
        AccessItemTimelineInput preInput = BeanUtil.copyProperties(input, AccessItemTimelineInput.class);

        if (CheckUtil.isNotEmpty(input.getStime()) && CheckUtil.isNotEmpty(input.getEtime())) {
            preInput.setStime(input.getStime() - (input.getEtime() - input.getStime()));
            preInput.setEtime(input.getStime());

            CommonNumVo preProductNum = analyticsSysDao.getAccessItemNum(preInput.getStime(), preInput.getEtime(), preInput.getItemId());

            if (ObjectUtil.isNotEmpty(preProductNum)) {
                topRes.setPre(preProductNum.getNum());

                // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
                BigDecimal daym2m = BigDecimal.ZERO;
                if (preProductNum.getNum().compareTo(BigDecimal.ZERO) != 0) {
                    daym2m = (currentProductNum.getNum().subtract(preProductNum.getNum())).divide(preProductNum.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                    //.multiply(new BigDecimal("100"));
                } else {

                }

                topRes.setDaym2m(daym2m);
            }
        }

        return topRes;
    }

    @Override
    public List<TimelineOutput> getAccessItemUserTimeLine(AccessItemTimelineInput input) {
        return analyticsSysDao.getAccessItemUserTimeLine(input.getStime(), input.getEtime(), input.getItemId());
    }

    @Override
    public AnalyticsNumOutput getAccessItemUserNum(AccessItemTimelineInput input) {
        AnalyticsNumOutput topRes = new AnalyticsNumOutput();

        // 获取当前周期内数据
        CommonNumVo currentProductNum = analyticsSysDao.getAccessItemUserNum(input.getStime(), input.getEtime(), input.getItemId());
        if (currentProductNum != null) {
            topRes.setCurrent(currentProductNum.getNum());
        }

        //上个周期
        AccessItemTimelineInput preInput = BeanUtil.copyProperties(input, AccessItemTimelineInput.class);

        if (CheckUtil.isNotEmpty(input.getStime()) && CheckUtil.isNotEmpty(input.getEtime())) {
            preInput.setStime(input.getStime() - (input.getEtime() - input.getStime()));
            preInput.setEtime(input.getStime());

            CommonNumVo preProductNum = analyticsSysDao.getAccessItemUserNum(input.getStime(), input.getEtime(), input.getItemId());

            if (ObjectUtil.isNotEmpty(preProductNum)) {
                topRes.setPre(preProductNum.getNum());

                // 计算日环比 日环比 = (当日数据 - 前一日数据) / 前一日数据 * 100%
                BigDecimal daym2m = BigDecimal.ZERO;
                if (preProductNum.getNum().compareTo(BigDecimal.ZERO) != 0) {
                    daym2m = (currentProductNum.getNum().subtract(preProductNum.getNum())).divide(preProductNum.getNum(), 2, BigDecimal.ROUND_HALF_UP);
                    //.multiply(new BigDecimal("100"));
                } else {

                }

                topRes.setDaym2m(daym2m);
            }
        }

        return topRes;
    }

    @Override
    public List<TimelineOutput> getAccessVisitorTimeLine(TimelineInput input) {
        return analyticsSysDao.getAccessVisitorTimeLine(input.getStime(), input.getEtime());
    }

    @Override
    public List<AnalyticsAccessItemOutput> listAccessItem(AccessItemTimelineInput timelineInput) {
        return analyticsSysDao.listAccessItem(timelineInput);
    }

}
