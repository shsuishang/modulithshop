package com.suisung.shopsuite.common.utils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    /**
     * 驼峰映射的对象转换成下划线形式的对象
     *
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> keyToUnderline(Map<String, T> map) {
        Map<String, T> resultMap = new HashMap<>();
        PropertyNamingStrategy SNAKE_CASE = PropertyNamingStrategy.SNAKE_CASE;
        for (Map.Entry<String, T> entry : map.entrySet()) {
            resultMap.put(((PropertyNamingStrategy.SnakeCaseStrategy) SNAKE_CASE).translate(entry.getKey()), entry.getValue());
        }
        return resultMap;
    }
}
