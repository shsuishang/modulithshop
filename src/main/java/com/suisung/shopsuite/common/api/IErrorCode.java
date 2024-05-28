package com.suisung.shopsuite.common.api;

/**
 * 封装API的错误码
 */
public interface IErrorCode {
    long getCode();

    long getStatus();

    String getMessage();
}
