package com.suisung.shopsuite.common.security;

import com.suisung.shopsuite.common.consts.ConstantMsg;
import com.suisung.shopsuite.common.utils.CommonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有登录异常处理
 *
 * @author Xinze
 * @since 2019-03-25 00:35:03
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        CommonUtil.responseError(response, 250, ConstantMsg.UNAUTHENTICATED_MSG, ConstantMsg.UNAUTHENTICATED_CODE,
                e.getMessage());
    }

}
