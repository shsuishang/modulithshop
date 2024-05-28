package com.suisung.shopsuite.common.auth;

import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthInterceptor implements HandlerMethodArgumentResolver, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Auth.class)) {
            return true;
        } else return parameter.getMethodAnnotation(Auth.class) != null;
    }

    @Override
    public ContextUser resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        ContextUser user = ContextUtil.getLoginUser();
        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        Auth auth = methodParameter.getParameterAnnotation(Auth.class);
        if (auth.isPlatform() && !user.isPlatform()) {
            throw new BusinessException(ResultCode.NOT_IS_PLATFORM);
        }
        if (auth.isStore() && !user.isStore()) {
            throw new BusinessException(ResultCode.NOT_IS_STORE);
        }
        if (auth.isChain() && !user.isChain()) {
            throw new BusinessException(ResultCode.NOT_IS_CHAIN);
        }
        if (auth.isSubsite() && !user.isSubsite()) {
            throw new BusinessException(ResultCode.NOT_IS_SUBSITE);
        }
        if (auth.isAdmin() && !user.isAdmin()) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        if (auth.isMobile() && !user.isMobile()) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        return user;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (AuthInterceptor.applicationContext == null) {
            AuthInterceptor.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
