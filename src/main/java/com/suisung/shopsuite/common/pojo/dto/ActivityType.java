package com.suisung.shopsuite.common.pojo.dto;

import java.util.HashMap;
import java.util.Map;

public class ActivityType {

    private static final Map<Integer, String> activityMap = new HashMap<>();

    static {
        activityMap.put(1105, "优惠券");
    }

    public static String getActivityName(Integer activityTypeId) {
        return activityMap.get(activityTypeId);
    }

}
