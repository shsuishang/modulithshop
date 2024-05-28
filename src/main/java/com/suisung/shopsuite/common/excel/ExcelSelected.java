package com.suisung.shopsuite.common.excel;

import java.lang.annotation.*;

/**
 * 自定义注解标记导出excel的下拉数据集
 */
@Documented
// 作用在字段上
@Target(ElementType.FIELD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSelected {

    /**
     * 固定下拉内容
     */
    String[] source() default {};

    /**
     * 动态下拉内容
     */
    Class<? extends ExcelDynamicSelect>[] sourceClass() default {};

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    int firstRow() default 1;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    int lastRow() default 0x10000;
}