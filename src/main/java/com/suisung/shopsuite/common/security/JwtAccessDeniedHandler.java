package com.suisung.shopsuite.common.security;

import com.suisung.shopsuite.common.consts.ConstantMsg;
import com.suisung.shopsuite.common.utils.CommonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有访问权限异常处理
 *
 * @author Xinze
 * @since 2019-03-25 00:35:03
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        CommonUtil.responseError(response, 250, ConstantMsg.UNAUTHORIZED_MSG, ConstantMsg.UNAUTHORIZED_CODE, e.getMessage());
    }

}
