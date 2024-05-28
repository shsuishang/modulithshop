package com.suisung.shopsuite.common.weblog;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.HttpServletUtils;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.sys.model.entity.AccessHistory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 记录接口调用日志切面
 *
 * @author yinkai
 */
@Component
@Aspect
@Order(0)
public class ControllerLogAspect {

    @Value("${spring.profiles.active}")
    private String env;

    private final static int max_str_length = 1000;

    /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory.getLogger("LOG_STASH_RECORD");

    @Autowired
    private MessageService messageService;

    /**
     * 切点定义在所有http接口
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) " +
            "&& !@annotation(com.suisung.shopsuite.common.weblog.IgnoreLog)" +
            "&& !@within(com.suisung.shopsuite.common.weblog.IgnoreLog)")
    public void webLog() {

    }

    /**
     * 切点定义在所有业务代码接口
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) " +
            "&& !@within(org.springframework.stereotype.Service)")
    public void errLog() {

    }

    @Before(value = "webLog()")
    public void doBefore() {

        HttpServletRequest request = HttpServletUtils.getRequest();
        // 线程变量赋值
        long currentTimeMillis = System.currentTimeMillis();
        String requestURI = request.getRequestURI();

        RequestNoContext.callTime.set(currentTimeMillis);
        RequestNoContext.apiUrl.set(requestURI);
        RequestNoContext.currentThreadId.set(currentTimeMillis);
        StringBuilder log =
                new StringBuilder("\n" + "===============[" + RequestNoContext.currentThreadId.get() + "]请求内容开始==============="
                        + "\n" + "    请求地址:" + requestURI
                        + "\n" + "    请求方式:" + request.getMethod());
        try {
            log.append("\n" + "    接口参数:");
            Map<String, String[]> parameterMap = request.getParameterMap();
            String argsJson = JSONUtil.toJSONString(parameterMap);
            if (StrUtil.isNotEmpty(argsJson) && argsJson.length() > max_str_length) {
                argsJson = "请求参数过大不予展示";
            }
            RequestNoContext.requestParam.set(argsJson);
            log.append(argsJson);
        } catch (Exception e) {
            log.append("\n    无法加载请求参数内容");
        }
        log.append("\n" + "===============[").append(RequestNoContext.currentThreadId.get()).append("]请求内容结束===============");
        logger.info(log.toString());

    }

    @AfterReturning(value = "webLog()", returning = "returns")
    public void afterReturning(Object returns) {

        String returnJson = JSONUtil.toJSONString(returns);
        if (StrUtil.isNotEmpty(returnJson) && returnJson.length() > max_str_length) {
            returnJson = "返回内容过大不予展示";
        }

        long costTime = System.currentTimeMillis() - RequestNoContext.callTime.get();

        logger.info("\n" + "--------------[" + RequestNoContext.currentThreadId.get() + "]返回内容开始----------------"
                + "\n" + "    接口地址:" + RequestNoContext.apiUrl.get()
                + "\n" + "    返回内容:" + returnJson
                + "\n" + "    请求耗时:" + costTime + "ms"
                + "\n" + "--------------[" + RequestNoContext.currentThreadId.get() + "]返回内容结束----------------");

        RequestNoContext.remove();


        try {
            HttpServletRequest request = HttpServletUtils.getRequest();
            String requestURI = request.getRequestURI();

            if (requestURI.startsWith("/manage") || requestURI.startsWith("/front/sys/page") || requestURI.startsWith("/front/pt")) {
                Map<String, String[]> parameterMap = request.getParameterMap();

                //用户端访问
                UserAgent ua = UserAgentUtil.parse(request.getHeader(Header.USER_AGENT.toString()));

                Integer userId = ContextUtil.getLoginUserId();

                AccessHistory accessDTO = new AccessHistory();
                accessDTO.setAccessUrl(requestURI);
                accessDTO.setAccessMethod(request.getMethod());
                accessDTO.setUserId(userId);
                accessDTO.setAccessLang(request.getHeader("Accept-Language").substring(0, 5));
                accessDTO.setAccessOs(ua.getPlatform().toString());
                accessDTO.setAccessBrowserName(ua.getBrowser().toString());
                accessDTO.setAccessBrowserVersion(ua.getVersion());
                accessDTO.setAccessIp(HttpServletUtils.getClientIpAddr());

                if (ua.isMobile()) {
                    accessDTO.setAccessMobile(true);
                    accessDTO.setAccessDevice(1);
                } else {
                    accessDTO.setAccessPc(true);
                    accessDTO.setAccessDevice(3);
                }

                Date date = new Date();
                accessDTO.setAccessTime(date.getTime());

                //处理
                Map<String, String> access_map = new HashMap<String, String>();

                for (String key : parameterMap.keySet()) {
                    if (!parameterMap.containsKey("perm_key") && !parameterMap.containsKey("perm_id")) {
                        access_map.put(key, parameterMap.get(key)[0]);
                    }
                }

                accessDTO.setAccessReq(access_map);
                messageService.sendAccess(accessDTO);
            } else {

            }

        } catch (Exception e) {
        }
    }

    @AfterThrowing(value = "errLog()", throwing = "e")
    public void throwing(JoinPoint joinPoint, Throwable e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];// 得到异常棧的首个元素
        logger.error(String.format("%s::%s:%d", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));

        boolean flag = true;
        if (e != null) {
            String infoStr = e.toString();
            List<String> exList = StrUtil.split(infoStr, ":");
            if (CollUtil.isNotEmpty(exList)) {
                if (exList.get(0).equals("com.suisung.shopsuite.common.exception.BusinessException")) {
                    flag = false;
                }
            }
        }

        if (flag) {
            LogUtil.error(joinPoint, e);
        }

        RequestNoContext.remove();
    }
}
