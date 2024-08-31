package com.suisung.shopsuite.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.utils.phone.PhoneNumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class CheckUtil {

    /**
     * 校验数据 不为null和0
     *
     * @param param
     * @return
     */
    public static boolean isNotEmpty(Integer param) {
        return param != null && param != 0;
    }

    public static boolean isNotEmpty(Long param) {
        return param != null && param != 0;
    }

    public static boolean isNotEmpty(BigDecimal param) {
        return param != null && param.floatValue() != 0;
    }

    public static boolean isNotEmpty(Float param) {
        return param != null && param != 0;
    }

    public static boolean isNotEmpty(Double param) {
        return param != null && param != 0;
    }

    public static boolean isNotEmpty(String param) {
        return StringUtils.isNotEmpty(param);
    }

    public static boolean isEmpty(Integer param) {
        return !isNotEmpty(param);
    }

    public static boolean isEmpty(Long param) {
        return !isNotEmpty(param);
    }

    public static boolean isEmpty(BigDecimal param) {
        return !isNotEmpty(param);
    }

    public static boolean isEmpty(Float param) {
        return !isNotEmpty(param);
    }

    public static boolean isEmpty(Double param) {
        return !isNotEmpty(param);
    }

    public static boolean isEmpty(String param) {
        return StringUtils.isEmpty(param);
    }

    /**
     * 手机号码校验(三大运营商最新号段 合作版 2021-03)
     * 移动号段：
     * 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 195 198
     * <p>
     * 联通号段：
     * 130 131 132 145 146 155 156 166 167 171 175 176 185 186 196
     * <p>
     * 电信号段：
     * 133 149 153 173 174 177 180 181 189 191 193 199
     * <p>
     * 虚拟运营商:
     * 162 165 167 170 171
     * <p>
     * 13开头排序：(0-9)（134 135 136 137 138 139 130 131 132 133）
     * 14开头排序：(5-9)（147 148 145 146 149）
     * 15开头排序：(0-3|5-9)（150 151 152 157 158 159 155 156 153）
     * 16开头排序：(6-7)（166 167）
     * 17开头排序：(1-8)（172 178 171 175 176 173 174 177）
     * 18开头排序：(0-9)（182 183 184 187 188 185 186 180 181 189）
     * 19开头排序：(1|3|5|6|8|9)（195 198 196 191 193 199）
     *
     * @param phone 手机号码 +8618898989898 (检验国内手机)
     * @return 是否属于三大运营商号段范围
     */
    public static boolean isMobile(String phone, String diallingCode) {
        return PhoneNumberUtils.isValidNumber(phone);
    }

    /**
     * 邮箱格式校验
     *
     * @param email 邮箱
     * @return
     */
    public static boolean isEmail(String email) {
        return Validator.isEmail(email);
    }

    /**
     * 校验数据的某个字段是否为指定值
     *
     * @param value 指定值的字段值
     * @param data  需要对比的数据
     * @param key   字段名
     * @return
     */
    public static boolean checkDataRights(Integer value, List<Map> data, String key) {
        if (CollUtil.isEmpty(data)) return false;
        for (Map datum : data) {
            if (!checkDataRights(value, datum, key)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDataRights(Integer value, Map data, String key) {
        if (CollUtil.isEmpty(data)) return false;
        Integer _value = Convert.toInt(data.get(key));
        return ObjectUtil.equal(_value, value);
    }

    public static <T> boolean checkDataRights(Integer value, List<T> data, Function<T, Integer> idMapper) {
        if (CollUtil.isEmpty(data)) return false;
        for (T datum : data) {
            if (!checkDataRights(value, datum, idMapper)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean checkDataRights(Integer value, T data, Function<T, Integer> idMapper) {
        if (ObjectUtil.isEmpty(data)) return false;
        Object _value = idMapper.apply(data);
        return ObjectUtil.equal(_value, value);
    }

    public static String addslashes(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char src = str.charAt(i);
            switch (src) {
                case '\'':
                    sb.append("''");// hibernate转义多个单引号必须用两个单引号
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                default:
                    sb.append(src);
                    break;
            }
        }

        return sb.toString();
    }


    /**
     * 处理 Find_In_Set 条件的字段
     *
     * @param params
     * @param fieldName
     * @param column_row
     */
    public static <T, M> void handleFindInSet(List<M> params, String fieldName, QueryWrapper<T> column_row) {
        if (CollUtil.isEmpty(params) || StrUtil.isBlank(fieldName) || column_row == null) return;

        StringBuilder strSql = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (i == params.size() - 1) {
                strSql.append("FIND_IN_SET ( ('" + CheckUtil.addslashes(params.get(i).toString()) + "'), " + fieldName + ")");
                break;
            }
            strSql.append("FIND_IN_SET ( ('" + CheckUtil.addslashes(params.get(i).toString()) + "'), " + fieldName + ") or ");
        }

        //column_row.apply("(" + strSql + ")", params);
        column_row.apply("(" + strSql + ")");
    }

    public static int bkdrHash(String str) {
        int seed = 131;
        int hash = 0;

        for (int i = 0; i != str.length(); ++i) {
            hash = seed * hash + str.charAt(i);
        }

        return (hash & 0x7FFFFFFF);
    }

    public static int getPlantformUid(String service_user_id, String user_id) {
        String puid = String.format("%s-%s", service_user_id, user_id);
        return bkdrHash(puid);
    }

    /**
     * 判断当前是否为 JAR 方式启动
     *
     * @return 当为 true 时为 JAR 方式启动，否则为非 JAR
     */
    public static boolean isInJar() {
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("");
            if (resources.hasMoreElements()) {
                log.debug("Application is in JAR!");
                return StringUtils.equalsIgnoreCase(resources.nextElement().getProtocol(), "jar");
            }
        } catch (IOException e) {
            LogUtil.error("Failed load resource, ", e);
            throw new RuntimeException(e);
        }
        log.debug("Application is not in JAR!");
        return false;
    }

    /**
     * 功能描述:
     *
     * @param: 判断是否是日期格式
     * @return:
     * @auther: 陈旺
     * @date: 2022/8/8 18:41
     */

    public static boolean isDate(String date_string) {
        if (isEmpty(date_string)) {
            return false;
        }
        try {
            long time = DateUtil.parse(date_string).getTime();
            return time > 0;
            // DateUtils.parseDate(date_string, parsePatterns);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasField(Class c, String fieldName) {
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }
}
