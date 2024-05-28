package com.suisung.shopsuite.common.exception;

import com.suisung.shopsuite.common.consts.ConstantMsg;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.CommonRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author Xinze
 * @since 2018-02-22 11:29:30
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonRes<?> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e,
                                                           HttpServletResponse response) {
        CommonUtil.addCrossHeaders(response);
        return new CommonRes<>(Constants.RESULT_ERROR_STATUS, "请求方式不正确").setError(e.toString());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public CommonRes<?> accessDeniedExceptionHandler(AccessDeniedException e, HttpServletResponse response) {
        CommonUtil.addCrossHeaders(response);
        return new CommonRes<>(Constants.RESULT_ERROR_STATUS, ConstantMsg.UNAUTHORIZED_MSG, null, ConstantMsg.UNAUTHORIZED_CODE).setError(e.toString());
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public CommonRes<?> businessExceptionHandler(BusinessException e, HttpServletResponse response) {
        CommonUtil.addCrossHeaders(response);
        return new CommonRes<>(Constants.RESULT_ERROR_STATUS, e.getMessage(), null, e.getCode());
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public CommonRes<?> exceptionHandler(Throwable e, HttpServletResponse response) {
        logger.error(e.getMessage(), e);
        CommonUtil.addCrossHeaders(response);
        return new CommonRes<>(Constants.RESULT_ERROR_STATUS, Constants.RESULT_ERROR_MSG).setError(e.toString());
    }

}
