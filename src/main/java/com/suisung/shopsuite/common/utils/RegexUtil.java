package com.suisung.shopsuite.common.utils;

import java.util.Date;

public class RegexUtil {

    /**
     * 判断给定的字符串是否符合时间格式 HH:mm。
     *
     * @param timeStr 给定的时间字符串，格式为 HH:mm。
     * @return 如果给定的字符串符合时间格式，则返回 true；否则返回 false。
     */
    public static boolean isValidTimeFormat(String timeStr) {
        String pattern = "^([0-1]\\d|2[0-3]):([0-5]\\d)$";
        return timeStr.matches(pattern);
    }

    /**
     * 判断给出的时间是否在当前时间范围内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isWithinTimeRange(long startTime, long endTime) {
        Date currentTime = new Date();
        long currentTimeMillis = currentTime.getTime();
        return currentTimeMillis >= startTime && currentTimeMillis <= endTime;
    }

    /**
     * 判断给出的时间是否不在当前时间范围内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isNotWithinTimeRange(long startTime, long endTime) {
        Date currentTime = new Date();
        long currentTimeMillis = currentTime.getTime();
        return currentTimeMillis < startTime || currentTimeMillis > endTime;
    }

}
