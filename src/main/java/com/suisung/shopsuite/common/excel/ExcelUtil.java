package com.suisung.shopsuite.common.excel;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.common.utils.CSVUtils;
import com.suisung.shopsuite.common.utils.LogUtil;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * Author: Lwg
 * Description: Object oriented export tool
 */
public class ExcelUtil {

    /**
     * @param response
     * @param classInst  类实例
     * @param reportName 文件名称
     * @param data       需要导出的数据
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void exportReport(HttpServletResponse response, Integer classInst, String reportName, List<?> data) {

        Class<?> targetClazz = ExportFactory.getExportFactory(classInst);
        Field[] fields = targetClazz.getDeclaredFields();
        // 表头
        String[] heads = new String[fields.length];

        // 处理表头
        heads = dealHeadData(fields);

        // 数据
        List<String[]> values = new ArrayList<>();

        try {
            Object obj = targetClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 格式化预存数据
        List valueList = new ArrayList();
        for (Object tData : data) {
            valueList.add(tData);
        }

        // 数据处理
        values = dealData(valueList, targetClazz);

        for (String[] array : values) {
            for (int i = 0; i < array.length; i++) {
                String value = array[i];
                if (value != null && !value.isEmpty()) {
                    array[i] = "`" + value;
                }
            }
        }

        try {
            CSVUtils.downloadFile(response, CSVUtils.makeTempCSV(__(reportName), heads, values));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理表头数据
     *
     * @param fields
     * @return
     */
    public static String[] dealHeadData(Field[] fields) {
        // 表头
        String[] heads = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            ApiModelProperty apiModelProperty = fields[i].getAnnotation(ApiModelProperty.class);
            String value = apiModelProperty.value();
            heads[i] = __(value);
        }
        return heads;
    }


    /**
     * 处理数据
     *
     * @param list
     * @return
     */
    public static List<String[]> dealData(List<?> list, Class clazz) {
        List<String[]> values = new ArrayList<>();
        for (Object o : list) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                String[] value = new String[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    Object valueObj = fields[i].get(o);

                    String fieldType = fields[i].getType().getName();

                    // 日期类型进行字符转换
                    if (fieldType.equals("java.util.Date") && ObjectUtil.isNotEmpty(valueObj)) {
                        Date dateValue = Convert.toDate(valueObj);
                        valueObj = DateUtil.format(dateValue, "yyyy-MM-dd HH:mm:ss");
                    }

                    // 布尔类型进行字符转换
                    if (fieldType.equals("java.lang.Boolean") && ObjectUtil.isNotEmpty(valueObj)) {
                        ApiModelProperty apiModelProperty = fields[i].getAnnotation(ApiModelProperty.class);
                        String valueStr = apiModelProperty.value();
                        Boolean boolVal = Convert.toBool(valueObj);
                        // 这里默认定义[0为是 - 1为否]
                        Pattern regexPattern = boolVal ? Pattern.compile("1-(.*?);") : Pattern.compile("0-(.*?);");
                        Matcher matcher = regexPattern.matcher(valueStr);
                        if (matcher.find()) {
                            valueObj = matcher.group(1);
                        }
                    }

                    value[i] = Convert.toStr(valueObj);
                }

                values.add(value);
            } catch (IllegalAccessException e) {
                LogUtil.error(__("数据导出错误，错误原因："), e);
            }
        }
        return values;
    }


}
