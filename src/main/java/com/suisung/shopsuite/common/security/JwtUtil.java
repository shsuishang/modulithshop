package com.suisung.shopsuite.common.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.suisung.shopsuite.common.consts.ConstantJwt;
import com.suisung.shopsuite.common.utils.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author Xinze
 * @since 2018-01-21 16:30:59
 */
public class JwtUtil {

    /**
     * 获取请求中的access_token
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getAccessToken(HttpServletRequest request) {
        String access_token = ServletUtil.getHeaderIgnoreCase(request, ConstantJwt.TOKEN_HEADER_NAME);
        if (StrUtil.isNotBlank(access_token)) {
            if (access_token.startsWith(ConstantJwt.TOKEN_TYPE)) {
                access_token = StrUtil.removePrefix(access_token, ConstantJwt.TOKEN_TYPE).trim();
            }
        } else {
            access_token = request.getParameter(ConstantJwt.TOKEN_PARAM_NAME);
        }
        return access_token;
    }

    /**
     * 生成token
     *
     * @param subject          载体
     * @param expire           过期时间
     * @param base64EncodedKey base64编码的Key
     * @return token
     */
    public static String buildToken(JwtSubject subject, Long expire, String base64EncodedKey) {
        return buildToken(JSONUtil.toJSONString(subject), expire, decodeKey(base64EncodedKey));
    }

    /**
     * 生成token
     *
     * @param subject 载体
     * @param expire  过期时间
     * @param key     密钥
     * @return token
     */
    public static String buildToken(String subject, Long expire, Key key) {
        Date expireDate = new Date(new Date().getTime() + 1000 * expire);
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expireDate)
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token            token
     * @param base64EncodedKey base64编码的Key
     * @return Claims
     */
    public static Claims parseToken(String token, String base64EncodedKey) {
        return parseToken(token, decodeKey(base64EncodedKey));
    }

    /**
     * 解析token
     *
     * @param token token
     * @param key   密钥
     * @return Claims
     */
    public static Claims parseToken(String token, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取JwtSubject
     *
     * @param claims Claims
     * @return JwtSubject
     */
    public static JwtSubject getJwtSubject(Claims claims) {
        return JSONUtil.parseObject(claims.getSubject(), JwtSubject.class);
    }

    /**
     * 生成Key
     *
     * @return Key
     */
    public static Key randomKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * base64编码key
     *
     * @return String
     */
    public static String encodeKey(Key key) {
        return Encoders.BASE64.encode(key.getEncoded());
    }

    /**
     * base64编码Key
     *
     * @param base64EncodedKey base64编码的key
     * @return Key
     */
    public static Key decodeKey(String base64EncodedKey) {
        if (StrUtil.isBlank(base64EncodedKey)) {
            return null;
        }
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedKey));
    }

}
