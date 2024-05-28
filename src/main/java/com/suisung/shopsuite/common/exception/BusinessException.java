package com.suisung.shopsuite.common.exception;

import com.suisung.shopsuite.common.api.IErrorCode;
import com.suisung.shopsuite.core.consts.Constants;

/**
 * 自定义业务异常
 *
 * @author Xinze
 * @since 2018-02-22 11:29:28
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final Integer status = 250;
    private Integer code;

    private IErrorCode errorCode;

    public BusinessException() {
        this(Constants.RESULT_ERROR_MSG);
    }

    public BusinessException(String message) {
        this(0, message);
    }

    public BusinessException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(IErrorCode errorCode, String appendMessage) {
        super(errorCode.getMessage() + ":" + appendMessage);
        this.errorCode = errorCode;
    }

    public BusinessException(Integer code) {
        this(code, Constants.RESULT_ERROR_MSG);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

}
