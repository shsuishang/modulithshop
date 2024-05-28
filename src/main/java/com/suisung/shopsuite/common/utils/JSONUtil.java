package com.suisung.shopsuite.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON解析工具类
 *
 * @author Xinze
 * @since 2017-06-10 10:10:39
 */
public class JSONUtil {
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    /**
     * 对象转json字符串
     *
     * @param value 对象
     * @return String
     */
    public static String toJSONString(Object value) {
        return toJSONString(value, false);
    }

    /**
     * 对象转json字符串
     *
     * @param value  对象
     * @param pretty 是否格式化输出
     * @return String
     */
    public static String toJSONString(Object value, boolean pretty) {
        if (value != null) {
            if (value instanceof String) {
                return (String) value;
            }
            try {
                if (pretty) {
                    return objectWriter.writeValueAsString(value);
                }
                return objectMapper.writeValueAsString(value);
            } catch (Exception e) {
                logger.error("对象转json字符串", e);
            }
        }
        return null;
    }

    /**
     * json字符串转对象
     *
     * @param json  String
     * @param clazz Class
     * @return T
     */
    public static <T> T parseObject(String json, Class<T> clazz) {

        if (!isTypeJSONObject(json)) {
            throw new RuntimeException("Incorrect json format!");
        }

        if (StrUtil.isNotBlank(json) && clazz != null) {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (Exception e) {
                try {
                    Map tmp = JSONUtil.parseObject(json, Map.class);
                    return BeanUtil.copyProperties(tmp, clazz);
                } catch (Exception ex) {
                    logger.error("json字符串转对象", e);
                    logger.error("json字符串转对象", ex);
                }
            }
        }

        return null;
    }

    /**
     * json字符串转List对象
     *
     * @param json String
     * @param type Class
     * @return T
     */
    public static <T> List<T> parseArray(String json, Class<T> type) {
        List<T> lists = new ArrayList<>();

        if (!isTypeJSONArray(json)) {
            throw new RuntimeException("Incorrect json format!");
        }

        if (StrUtil.isNotBlank(json) && type != null) {
            try {
                List<T> ts = objectMapper.readValue(json, new TypeReference<List<T>>() {
                });

                lists = BeanUtil.copyToList(ts, type);
            } catch (Exception e) {
                logger.error("json字符串转List对象", e);
            }
        }

        return lists;
    }

    /**
     * 判断是否为json对象
     *
     * @param str
     * @return
     */
    public static boolean isTypeJSONObject(String str) {
        return !StrUtil.isBlank(str) && StrUtil.isWrap(StrUtil.trim(str), '{', '}');
    }

    /**
     * 判断是否为json数组
     *
     * @param str
     * @return
     */
    public static boolean isTypeJSONArray(String str) {
        return !StrUtil.isBlank(str) && StrUtil.isWrap(StrUtil.trim(str), '[', ']');
    }

}
