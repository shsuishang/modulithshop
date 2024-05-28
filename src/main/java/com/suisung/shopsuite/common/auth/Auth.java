package com.suisung.shopsuite.common.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Lwg
 * Description: Annotated permission verification
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 平台账号
     */
    boolean isPlatform() default false;

    /**
     * 店铺账号
     */
    boolean isStore() default false;

    /**
     * 门店账号
     */
    boolean isChain() default false;

    /**
     * 分站账号
     */
    boolean isSubsite() default false;

    /**
     * 后台管理-admin-app
     */
    boolean isAdmin() default false;

    /**
     * 移动端-mobile-app
     */
    boolean isMobile() default false;

}
