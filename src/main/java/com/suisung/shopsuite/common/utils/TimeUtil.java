package com.suisung.shopsuite.common.utils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TimeUtil {
    /**
     * 当天
     *
     * @return
     */
    public static TimeRange today() {
        Date date = new Date();
        long start = DateUtil.beginOfDay(date).getTime();
        long end = date.getTime();

        TimeRange range = new TimeRange();
        range.setStart(start);
        range.setEnd(end);

        return range;
    }

    /**
     * 昨天
     *
     * @return
     */
    public static TimeRange yestoday() {
        Date date = DateUtil.offsetDay(new Date(), -1);

        long start = DateUtil.beginOfDay(date).getTime();
        long end = DateUtil.endOfDay(date).getTime();

        TimeRange range = new TimeRange();
        range.setStart(start);
        range.setEnd(end);

        return range;
    }


    /**
     * 最近N天
     *
     * @return
     */
    public static TimeRange lastNDays(int offset) {
        Date now = new Date();
        Date date = DateUtil.offsetDay(now, -offset + 1);

        long start = DateUtil.beginOfDay(date).getTime();
        long end = DateUtil.endOfDay(now).getTime();

        TimeRange range = new TimeRange();
        range.setStart(start);
        range.setEnd(end);

        return range;
    }

    /**
     * 本月
     *
     * @return
     */
    public static TimeRange month() {
        Date now = new Date();

        long start = DateUtil.beginOfMonth(now).getTime();
        long end = DateUtil.endOfMonth(now).getTime();

        TimeRange range = new TimeRange();
        range.setStart(start);
        range.setEnd(end);

        return range;
    }


    /**
     * 最近N月
     *
     * @return
     */
    public static TimeRange lastNMonths(int offset) {
        Date now = new Date();
        Date date = DateUtil.offsetMonth(now, -offset + 1);

        long start = DateUtil.beginOfMonth(date).getTime();
        long end = DateUtil.endOfMonth(now).getTime();

        TimeRange range = new TimeRange();
        range.setStart(start);
        range.setEnd(end);

        return range;
    }
}
