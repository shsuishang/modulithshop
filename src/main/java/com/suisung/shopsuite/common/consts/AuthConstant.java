package com.suisung.shopsuite.common.consts;

/**
 * 权限相关常量定义
 */
public interface AuthConstant {

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-app";

    /**
     * 移动端client_id
     */
    String MOBILE_CLIENT_ID = "mobile-app";

    /**
     * 微信client_ID
     */
    String WX_CLIENT_ID = "wx-app";

    /**
     * Oauth2 密钥
     */
    String AUTHORITY_SECRET = "fdafsadf123213~!@fds";
    String AUTHORITY_MOBILE_SECRET = AUTHORITY_SECRET;
    String AUTHORITY_ADMIN_SECRET = AUTHORITY_SECRET;
    String AUTHORITY_WX_SECRET = AUTHORITY_SECRET;

    int ACCESS_TOKEN_VALIDITY_SECONDS = 3600 * 24 * 7;
    int REFRESH_TOKEN_VALIDITY_SECONDS = 3600 * 24 * 21;

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/mall-admin/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * Redis缓存表数据key
     */
    String TABLE_DATA_KEY = "data:";

    /**
     * Redis缓存用户退出登录key
     */
    String LOGOUT_TOKEN_KEY = "token:";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";

    /**
     * RSAUtil解密公钥
     */
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC+8V1Or6R6H3a7TjuvoDa5k0W/niEGg4N+0Nni+KfwHVX05pI7Qdq1J5+q31yORAoiSSNZNW4uWykmeSltC2mHGkQXClU4JmMXnWFyRCENw1iDIIIEsNax4jFBZUaDCn69PxWgp5wwk+d0V7QRYZ9jkgUaJK+BSYa0KMraxVfJwIDAQAB";

    /**
     * RSAUtil加密私钥
     */
    String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAML7xXU6vpHofdrtOO6+gNrmTRb+eIQaDg37Q2eL4p/AdVfTmkjtB2rUnn6rfXI5ECiJJI1k1bi5bKSZ5KW0LaYcaRBcKVTgmYxedYXJEIQ3DWIMgggSw1rHiMUFlRoMKfr0/FaCnnDCT53RXtBFhn2OSBRokr4FJhrQoytrFV8nAgMBAAECgYEAizhN0thw/altQ4YiIoWvZ50M6iAkWN5prp37kNGWrM40etNB1FQ5+ZN636L+3THVUbwqdzLKTy1GX3jqg05VUIf0sKYYepp+skwZmHVprz4EUKsZXRa+3MnMChJcyHdlyuUNs6HriMq6Qc1+fFEOtZFAf3lo2wYNFw5vIKHGQRECQQDxVKa+6m4y7LmWgiGLYghuL/SGXySFhwBh5+zMNl8V7aAbTX/tH6A0s8JXsSI4iChjWPXthKFTrd7h62vJBjeFAkEAztXpNehF18g3e6JEhtjbTmMsgyj13gdSZSRwjO0Y+IsDI1afnZXzwv96OlukGK8185z0bsbhTCOd6rkcRTnduwJBAOqGknlMh4VTylO66PB0d67lSaPgCDT/al67LcOTPzqnMAX4fc6qAl3VJ5Ni39fCckWB6ZVGZCVW/hfdWmUEdqUCQFFWNXuJd82/YnIwAZq1tKhCv8JkXSuO3YwApHIG2wcCQ52l9ubVjSJlrP8+Am3imOjQFB9r/jUe3H7thHyEoPkCQCay3waa0ll2DY+epkrrF/QO7aMa6NIUArRgWUmqw+1/45csBiWPMUrAD/CPDUr9Jvte92NjoAlz649csbgMM3w=";

}
