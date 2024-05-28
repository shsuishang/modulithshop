package com.suisung.shopsuite.account.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@HeadRowHeight(30)
public class UserInfoTemp {

    @ExcelProperty(index = 0, value = "用户账号")
    @ColumnWidth(value = 15)
    private String userAccount;

    @ExcelProperty(index = 1, value = "用户昵称")
    @ColumnWidth(value = 15)
    private String userNickname;

    @ExcelProperty(index = 2, value = "用户头像")
    @ColumnWidth(value = 20)
    private String userAvatar;

    @ExcelProperty(index = 3, value = "状态(ENUM):0-锁定;1-已激活;2-未激活;")
    private Integer userState;

    @ExcelProperty(index = 4, value = "手机号码")
    @ColumnWidth(value = 15)
    private String userMobile;

    @ExcelProperty(index = 5, value = "国家编码")
    @ColumnWidth(value = 15)
    private String userIntl;

    @ExcelProperty(index = 6, value = "性别(ENUM):0-保密;1-男;  2-女;")
    @ColumnWidth(value = 10)
    private Integer userGender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = "yyyy-MM-dd")
    @ExcelProperty(index = 7, value = "生日")
    @ColumnWidth(value = 20)
    private Date userBirthday;

    @ExcelProperty(index = 8, value = "用户邮箱")
    @ColumnWidth(value = 20)
    private String userEmail;

    @ExcelProperty(index = 9, value = "等级编号")
    @ColumnWidth(value = 15)
    private Integer userLevelId;

    @ExcelProperty(index = 10, value = "认证状态(ENUM):0-未认证;1-待审核;2-认证通过;3-认证失败")
    @ColumnWidth(value = 15)
    private Integer userIsAuthentication;

    @ExcelProperty(index = 11, value = "用户标签")
    @ColumnWidth(value = 15)
    private String tagIds;

    @ExcelProperty(index = 12, value = "用户来源")
    @ColumnWidth(value = 15)
    private Integer userFrom;

    @ExcelProperty(index = 13, value = "新人标识(BOOL)")
    @ColumnWidth(value = 15)
    private Boolean userNew;
}
