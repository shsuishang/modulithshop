package com.suisung.shopsuite.pay.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@HeadRowHeight(50)
public class UserResourceTemp {

    @ExcelProperty(index = 0, value = "用户id")
    private Integer user_id;

    @ExcelProperty(index = 1, value = "用户余额")
    private BigDecimal user_money;

    @ExcelProperty(index = 2, value = "积分")
    private BigDecimal user_points;
}
