package com.suisung.shopsuite.common.consts;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 相关常量定义
 */
@Component
@ToString
public class ConstantRole {
    public static Integer ROLE_USER = 0; //用户
    public static Integer ROLE_SELLER = 2; //商家
    public static Integer ROLE_CHAIN = 3; //门店
    public static Integer ROLE_ADMIN = 9; //平台
}
