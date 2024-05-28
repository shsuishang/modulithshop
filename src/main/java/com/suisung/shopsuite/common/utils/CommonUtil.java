package com.suisung.shopsuite.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.consts.ConstantJwt;
import com.suisung.shopsuite.core.web.CommonRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 常用工具方法
 *
 * @author Xinze
 * @since 2017-06-10 10:10:22
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    // 生成uuid的字符
    private static final String[] chars = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 生成8位uuid
     *
     * @return String
     */
    public static String randomUUID8() {
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();
    }

    /**
     * 生成16位uuid
     *
     * @return String
     */
    public static String randomUUID16() {
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 16; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();
    }

    /**
     * 检查List是否有重复元素
     *
     * @param list   List
     * @param mapper 获取需要检查的字段的Function
     * @param <T>    数据的类型
     * @param <R>    需要检查的字段的类型
     * @return boolean
     */
    public static <T, R> boolean checkRepeat(List<T> list, Function<? super T, ? extends R> mapper) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j && mapper.apply(list.get(i)).equals(mapper.apply(list.get(j)))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * List转为树形结构
     *
     * @param data           List
     * @param parentId       顶级的parentId
     * @param parentIdMapper 获取parentId的Function
     * @param idMapper       获取id的Function
     * @param consumer       赋值children的Consumer
     * @param <T>            数据的类型
     * @param <R>            parentId的类型
     * @return List<T>
     */
    public static <T, R> List<T> toTreeData(List<T> data, R parentId,
                                            Function<? super T, ? extends R> parentIdMapper,
                                            Function<? super T, ? extends R> idMapper,
                                            BiConsumer<T, List<T>> consumer) {
        List<T> result = new ArrayList<>();
        for (T d : data) {
            R dParentId = parentIdMapper.apply(d);
            if (ObjectUtil.equals(parentId, dParentId)) {
                R dId = idMapper.apply(d);
                List<T> children = toTreeData(data, dId, parentIdMapper, idMapper, consumer);
                consumer.accept(d, children);
                result.add(d);
            }
        }
        return result;
    }

    /**
     * 遍历树形结构数据
     *
     * @param data     List
     * @param consumer 回调
     * @param mapper   获取children的Function
     * @param <T>      数据的类型
     */
    public static <T> void eachTreeData(List<T> data, Consumer<T> consumer, Function<T, List<T>> mapper) {
        for (T d : data) {
            consumer.accept(d);
            List<T> children = mapper.apply(d);
            if (children != null && children.size() > 0) {
                eachTreeData(children, consumer, mapper);
            }
        }
    }

    /**
     * 获取集合中的第一条数据
     *
     * @param records 集合
     * @return 第一条数据
     */
    public static <T> T listGetOne(List<T> records) {
        return records == null || records.size() == 0 ? null : records.get(0);
    }

    /**
     * 支持跨域
     *
     * @param response HttpServletResponse
     */
    public static void addCrossHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Expose-Headers", ConstantJwt.TOKEN_HEADER_NAME);
    }

    /**
     * 输出错误信息
     *
     * @param response HttpServletResponse
     * @param code     错误码
     * @param message  提示信息
     * @param error    错误信息
     */
    public static void responseError(HttpServletResponse response, Integer status, String message, Integer code, String error) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(JSONUtil.toJSONString(new CommonRes<>(status, message, null, code, error)));
            out.flush();
        } catch (IOException e) {
            logger.error("响应失败", e);
        }
    }

    /**
     * 接受一个类型为 List<T> 的列表和一个字符串类型的字段名 fieldName，并返回一个类型为 List<R> 的列表。
     * <p>
     * List<String> names2 = column(data, "name");
     *
     * @param list
     * @param fieldName
     * @param <T>
     * @param <R>
     * @return
     * @throws NoSuchFieldException
     */
    public static <T, R> List<R> column(List<T> list, String fieldName) throws NoSuchFieldException {
        return list.stream()
                .map(e -> {
                    try {
                        return (R) e.getClass().getField(fieldName).get(e);
                    } catch (IllegalAccessException | NoSuchFieldException ex) {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }

    /**
     * 接受一个类型为 List<T> 的列表和一个字符串类型的字段名 fieldName，并返回一个类型为 List<R> 的列表。
     * <p>
     * List<String> names2 = column(data, "name");
     *
     * @param list
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     * @throws NoSuchFieldException
     */
    public static <T, R> List<R> column(List<T> list, Function<? super T, ? extends R> mapper) {
        return list.stream()
                .map(mapper).filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }


    /**
     * page分页数据 转换mobile 需要格式类型
     *
     * @param page
     * @return
     */
    public static Map toMap(IPage page) {
        Map<Object, Object> data = new HashMap<>();
        List items = ObjectUtil.defaultIfNull(page.getRecords(), new ArrayList());

        data.put("total", page.getPages());
        data.put("page", page.getCurrent());
        data.put("records", page.getTotal());
        data.put("size", page.getSize());
        data.put("items", Convert.toList(Map.class, items));

        return data;
    }
}
