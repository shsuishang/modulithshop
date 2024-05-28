package com.suisung.shopsuite.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.web.ContextUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.suisung.shopsuite.common.consts.ConstantJwt.TOKEN_EXPIRED_CODE;
import static com.suisung.shopsuite.common.utils.I18nUtil.__;

public class ContextUtil {
    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static ContextUser getLoginUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object object = authentication.getPrincipal();
                if (object instanceof UserBase) {
                    ContextUser contextUser = BeanUtil.copyProperties(object, ContextUser.class);
                    return contextUser;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 获取当前登录的userId
     *
     * @return userId
     */
    public static Integer getLoginUserId() {
        ContextUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUserId();
    }

    /**
     * 获取当前登录的userId
     *
     * @return userId
     */
    public static Integer checkLoginUserId() {
        ContextUser loginUser = getLoginUser();

        if (loginUser == null) {
            throw new BusinessException(TOKEN_EXPIRED_CODE, __("请重新登录"));
        } else {

        }

        return loginUser.getUserId();
    }
}
