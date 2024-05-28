package com.suisung.shopsuite.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.sys.model.entity.LogError;
import com.suisung.shopsuite.sys.repository.LogErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class LogUtil implements ApplicationContextAware {

    private static ThreadPoolExecutor executor;

    private static ApplicationContext applicationContext;
    private static LogErrorRepository logErrorRepository;

    public static void error(Integer errType, String errMsg) {
        error(errType, errMsg, null, null);
    }

    public static void error(Integer errType, Throwable throwable) {
        error(errType, null, null, throwable);
    }

    public static void error(Integer errType, String errMsg, Throwable throwable) {
        error(errType, errMsg, null, throwable);
    }

    public static void error(String errMsg, Throwable throwable) {
        error(null, errMsg, null, throwable);
    }

    public static void error(JoinPoint joinPoint, Throwable throwable) {
        error(null, null, joinPoint, throwable);
    }

    public static void error(Integer errType, String errMsg, JoinPoint joinPoint, Throwable throwable) {
        if (errType == null) {
            errType = ConstantLog.DEFAULT;
        }

        if (throwable != null) {
            if (errMsg != null) {
                log.error(errMsg, throwable);
            } else {
                log.error(errType.toString(), throwable);
            }
        } else {
            log.error(errMsg);
        }

        Integer finalErrType = errType;
        CompletableFuture.runAsync(() -> {
            boolean flag = true;

            if (throwable != null) {
                String infoStr = throwable.toString();
                List<String> exList = StrUtil.split(infoStr, ":");
                if (CollUtil.isNotEmpty(exList)) {
                    if (exList.get(0).equals("com.suisung.shopsuite.common.exception.BusinessException")) {
                        flag = false;
                    }
                }
            }

            if (flag) {

                Date curDate = new Date();

                LogError logError = new LogError();
                logError.setLogErrorType(finalErrType);
                if (joinPoint != null) {
                    // 类名
                    String clazzName = joinPoint.getTarget().getClass().getName();
                    // 方法名
                    String methodName = joinPoint.getSignature().getName();
                    logError.setLogErrorName(String.format("%s:%s", clazzName, methodName));
                    logError.setLogErrorLine(methodName);
                }

                StackTraceElement stackTraceElement = throwable.getStackTrace()[0];// 得到异常棧的首个元素

                logError.setLogErrorLine(String.format("%s::%s:%d", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));

                logError.setLogErrorTime(curDate.getTime());
                logError.setLogErrorInfo(errMsg);

                if (throwable != null) {
                    StringWriter errLog = new StringWriter();
                    PrintWriter writer = new PrintWriter(errLog);
                    throwable.printStackTrace(writer);
                    logError.setLogErrorInfo(errLog.toString());
                }

                logError.setLogErrorDate(curDate);
                logError.setLogTime(curDate.getTime());

                logErrorRepository.save(logError);
            }
        }, executor).exceptionally(ex -> {
            log.error("保存错误失败，失败原因：", ex);
            return null;
        });
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        if (LogUtil.applicationContext == null) {
            LogUtil.applicationContext = applicationContext;
        }
        if (LogUtil.logErrorRepository == null) {
            LogUtil.logErrorRepository = applicationContext.getBean(LogErrorRepository.class);
        }
        if (LogUtil.executor == null) {
            LogUtil.executor = applicationContext.getBean(ThreadPoolExecutor.class);
        }
    }
}
