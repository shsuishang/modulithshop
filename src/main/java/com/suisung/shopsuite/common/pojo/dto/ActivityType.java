package com.suisung.shopsuite.common.pojo.dto;

import java.util.HashMap;
import java.util.Map;

public class ActivityType {

    private static final Map<Integer, String> activityMap = new HashMap<>();

    static {
        activityMap.put(1103, "限时折扣");
        activityMap.put(1105, "优惠券");
        activityMap.put(1132, "A+B组合套餐");
        activityMap.put(1137, "弹窗活动");
    }

    public static String getActivityName(Integer activityTypeId) {
        return activityMap.get(activityTypeId);
    }

}
