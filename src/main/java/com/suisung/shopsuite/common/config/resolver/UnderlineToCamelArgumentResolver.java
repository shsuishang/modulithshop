package com.suisung.shopsuite.common.config.resolver;

import com.suisung.shopsuite.common.utils.StringHelpers;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.text.ParseException;
import java.util.Iterator;

public class UnderlineToCamelArgumentResolver extends AbstractCustomizeResolver {
    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
        //return parameter.hasParameterAnnotation(ParameterModel.class);
    }

    /**
     * 装载参数
     *
     * @param methodParameter       方法参数
     * @param modelAndViewContainer 返回视图容器
     * @param nativeWebRequest      本次请求对象
     * @param webDataBinderFactory  数据绑定工厂
     * @return the resolved argument value, or {@code null}
     * @throws Exception in case of errors with the preparation of argument values
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object org = handleParameterNames(methodParameter, nativeWebRequest);
        valid(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory, org);
        return org;
    }

    //处理参数
    private Object handleParameterNames(MethodParameter parameter, NativeWebRequest webRequest) {
        Object obj = BeanUtils.instantiate(parameter.getParameterType());
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Iterator<String> paramNames = webRequest.getParameterNames();
        while (paramNames.hasNext()) {
            String paramName = paramNames.next();
            Object o = webRequest.getParameter(paramName);
            String s = StringHelpers.underLineToCamel(paramName);

            if (wrapper.isWritableProperty(s)) {
                Class<?> propertyType = wrapper.getPropertyType(s);
                if (propertyType.getName().equals("java.util.Date")) {
                    try {
                        wrapper.setPropertyValue(s, DateUtils.parseDate(o.toString(), "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss XXX", "yyyy-MM-dd HH:mm:ssXXX", "yyyy-MM-dd HH:mm:ssZ"));
                    } catch (ParseException e) {
                        wrapper.setPropertyValue(s, null);
                    }
                } else {
                    wrapper.setPropertyValue(s, o);
                }
            }
        }
        return obj;
    }
}