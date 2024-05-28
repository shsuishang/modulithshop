package com.suisung.shopsuite.common.security;

import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.admin.model.entity.MenuBase;
import com.suisung.shopsuite.common.config.ConfigProperties;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.consts.ConstantJwt;
import com.suisung.shopsuite.common.consts.ConstantMsg;
import com.suisung.shopsuite.common.utils.CommonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * 处理携带token的请求过滤器
 *
 * @author Xinze
 * @since 2019-03-30 20:48:05
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private ConfigProperties configProperties;
    @Resource
    private LoginService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String access_token = JwtUtil.getAccessToken(request);
        if (StrUtil.isNotBlank(access_token)) {
            try {
                // 解析token
                Claims claims = JwtUtil.parseToken(access_token, configProperties.getTokenKey());
                JwtSubject jwtSubject = JwtUtil.getJwtSubject(claims);

                //可以不再次读库
                UserBase user = userService.getByUserId(jwtSubject.getUserId());

                if (user == null) {
                    throw new UsernameNotFoundException("User not found");
                }

                List<MenuBase> authorities = new ArrayList<>();

                //处理 clientId
                if (request.getRequestURI().startsWith("/manage")) {
                    user.setClientId(1);

                    authorities = user.getAuthorities().stream()
                            .filter(m -> StrUtil.isNotBlank(m.getAuthority())).collect(Collectors.toList());

                    //处理操作权限判断
                    if (ConstantConfig.URL_BASE.equals("https://demo.modulithshop.cn")) {
                        if (!user.getUserId().equals(10001)) {
                            if (request.getMethod().equals("POST")) {
                                CommonUtil.responseError(response, 250, __("演示系统，不可更改！"), 0, "");
                                return;
                            }
                        }
                    }
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // token将要过期签发新token, 防止突然退出登录
                long expiration = (claims.getExpiration().getTime() - new Date().getTime()) / 1000 / 60;
                if (expiration < configProperties.getTokenRefreshTime()) {
                    String token = JwtUtil.buildToken(jwtSubject, configProperties.getTokenExpireTime(),
                            configProperties.getTokenKey());
                    response.addHeader(ConstantJwt.TOKEN_HEADER_NAME, token);
                    //loginRecordService.saveAsync(user.getUsername(), LoginRecord.TYPE_REFRESH, null, user.getTenantId(), request);
                }
            } catch (ExpiredJwtException e) {
                CommonUtil.responseError(response, 250, ConstantJwt.TOKEN_EXPIRED_MSG, ConstantJwt.TOKEN_EXPIRED_CODE,
                        e.getMessage());
                return;
            } catch (Exception e) {
                CommonUtil.responseError(response, 250, ConstantMsg.BAD_CREDENTIALS_MSG, ConstantMsg.BAD_CREDENTIALS_CODE,
                        e.toString());
                return;
            }
        }
        chain.doFilter(request, response);
    }

}
