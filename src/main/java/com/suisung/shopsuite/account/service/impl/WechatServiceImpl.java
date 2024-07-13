package com.suisung.shopsuite.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.account.model.entity.UserBindConnect;
import com.suisung.shopsuite.account.model.res.LoginRes;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.account.service.UserBindConnectService;
import com.suisung.shopsuite.account.service.WechatService;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.BindConnectCode;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.ErrorTypeEnum;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.WxHttpUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;


/**
 * <p>
 * 系统参数设置表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-04-14
 */
@Service
public class WechatServiceImpl implements WechatService {

    private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Autowired
    private UserBindConnectService userBindConnectService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private RedisService redisService;

    private final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    /**
     * 接入校验
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     */
    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String wechat_token = configBaseService.getConfig("wechat_token");
        String[] arr = new String[]{wechat_token, timestamp, nonce};
        Arrays.sort(arr);
        // 2.将三个参数字符串拼接成一个字符串进行sha1加密
        String str = arr[0] + arr[1] + arr[2];
        str = DigestUtils.sha1Hex(str);
        // 3.将加密后的字符串和signature比较
        return str.equalsIgnoreCase(signature);
    }

    /**
     * 将接受到的消息转化成map
     *
     * @param
     * @return
     */
    @Override
    public Map<String, String> parseRequest(InputStream is) {
        Map<String, String> map = new HashMap<String, String>();
        // 1.通过io流得到文档对象
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(is);
        } catch (DocumentException e) {
            logger.error(__("io流读取文档对象异常！") + e.getMessage(), e);
        }
        // 2.通过文档对象得到根节点对象
        Element root = document.getRootElement();
        // 3.通过根节点对象获取所有子节点对象
        List<Element> elements = root.elements();
        // 4.将所有节点放入map
        for (Element element : elements) {
            map.put(element.getName(), element.getStringValue());
        }
        return map;
    }

    @Override
    public void redirectToWxCode(HttpServletRequest request, HttpServletResponse response) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE#wechat_redirect";
        // 获取活动id
        String activity_id = request.getParameter("activity_id");
        try {
            String wechat_callback_url = URLEncoder.encode((ConstantConfig.URL_API_MOBILE.equals("https://demo.modulithshop.cn") ? "https://test.suteshop.com" : ConstantConfig.URL_API_MOBILE) + "/account/wechat/callbackMp?activity_id=" + activity_id, "UTF-8");
            String wechat_app_id = configBaseService.getConfig("wechat_app_id");
            url = url.replace("APPID", wechat_app_id).replace("REDIRECT_URI", wechat_callback_url).replace("SCOPE", "snsapi_userinfo");
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送get请求获取AccessToken
     */
    public String getMpToken() {
        String wechat_app_id = configBaseService.getConfig("wechat_app_id");
        String wechat_app_secret = configBaseService.getConfig("wechat_app_secret");
        String url = GET_TOKEN_URL.replace("APPID", wechat_app_id).replace("APPSECRET", wechat_app_secret);
        String tokenStr = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(tokenStr);
        String token = (String) jsonObject.get("access_token");
        Integer expiresIn = Convert.toInt(jsonObject.get("expires_in"), 0);
        if (StrUtil.isBlank(token)) {
            String errmsg = Convert.toStr(jsonObject.get("errmsg"));
            LogUtil.error(ConstantLog.NETWORK, String.format("获取公众号access_token失败！失败原因：{}", errmsg));
            throw new BusinessException(__("获取公众号access_token失败！"));

        }
        redisService.set(StateCode.WX_MP_ACCESSTOKEN, Convert.toStr(jsonObject.get("access_token")), expiresIn);
        return token;
    }

    /**
     * @param useCacheFlag 是否从缓存中读取
     *                     获取AccessToken  向外提供
     */
    public String getMpAccessToken(boolean useCacheFlag) {
        if (useCacheFlag) {
            if (!redisService.hasKey(StateCode.WX_MP_ACCESSTOKEN)) {
                return getMpToken();
            }
            return Convert.toStr(redisService.get(StateCode.WX_MP_ACCESSTOKEN));
        } else {
            return getMpToken();
        }
    }

    /**
     * 发送get请求获取AccessToken
     */
    private String getXcxToken() {
        String wechat_app_id = configBaseService.getConfig("wechat_xcx_app_id");
        String wechat_app_secret = configBaseService.getConfig("wechat_xcx_app_secret");
        String url = GET_TOKEN_URL.replace("APPID", wechat_app_id).replace("APPSECRET", wechat_app_secret);
        String tokenStr = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(tokenStr);
        String token = (String) jsonObject.get("access_token");
        Integer expiresIn = Convert.toInt(jsonObject.get("expires_in"), 0);
        if (StrUtil.isBlank(token)) {
            String errmsg = Convert.toStr(jsonObject.get("errmsg"));
            LogUtil.error(ConstantLog.NETWORK, String.format("获取小程序access_token失败！失败原因：{}", errmsg));
            throw new BusinessException(__("获取小程序access_token失败！"));
        }
        redisService.set(StateCode.WX_XCX_ACCESSTOKEN, Convert.toStr(jsonObject.get("access_token")), expiresIn);
        return token;
    }

    /**
     * 获取AccessToken  向外提供
     */
    public String getXcxAccessToken(boolean useCacheFlag) {
        if (useCacheFlag) {
            if (!redisService.hasKey(StateCode.WX_XCX_ACCESSTOKEN)) {
                return getXcxToken();
            }
            return Convert.toStr(redisService.get(StateCode.WX_XCX_ACCESSTOKEN));
        } else {
            return getXcxToken();
        }
    }

    @Override
    public LoginRes callbackMp(HttpServletResponse response, Integer activity_id, String code) {
        String vxUserInfo = getVxUserInfo(code);
        UserBindConnect vxUserBindConnect = getVxMpUserBindConnect(vxUserInfo);
        Integer user_id = loginService.doUserBind(vxUserBindConnect, activity_id, true);

        LoginRes login = loginService.login(user_id);
        return login;

        /*
        String wxUserToken = login.getToken();

        //Callback Url
        String redirectUrl = ConstantConfig.URL_H5 + "/pages/login/login?uid=" + user_id + "&ukey=" + wxUserToken;
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new BusinessException(__("微信登录跳转失败！"));
        }
         */
    }

    private String getVxUserInfo(String code) {
        String wechat_app_id = configBaseService.getConfig("wechat_app_id");
        String wechat_app_secret = configBaseService.getConfig("wechat_app_secret");
        //通过code获取网页授权的access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url = url.replace("APPID", wechat_app_id).replace("CODE", code).replace("SECRET", wechat_app_secret);
        String string = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(string);
        String accessToken = (String) jsonObject.get("access_token");
        String openid = (String) jsonObject.get("openid");
        //通过token获取用户信息
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        getUserInfoUrl = getUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
        return HttpUtil.get(getUserInfoUrl);
    }

    private String getVxUserInfo(String access_token, String openid) {
        //通过token获取用户信息
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        getUserInfoUrl = getUserInfoUrl.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
        return HttpUtil.get(getUserInfoUrl);
    }

    private UserBindConnect getVxMpUserBindConnect(String vxUserInfo) {
        JSONObject jsonObject = JSONUtil.parseObj(vxUserInfo);
        String openid = (String) jsonObject.get("openid");
        String unionid = (String) jsonObject.get("unionid");
        String nickname = (String) jsonObject.get("nickname");
        Integer sex = (Integer) jsonObject.get("sex");
        String city = (String) jsonObject.get("city");
        String province = (String) jsonObject.get("province");
        String country = (String) jsonObject.get("country");
        String headimgurl = (String) jsonObject.get("headimgurl");
        UserBindConnect accountUserBindConnect = new UserBindConnect();
        accountUserBindConnect.setBindId(openid);
        accountUserBindConnect.setBindOpenid(openid);
        accountUserBindConnect.setBindUnionid(unionid);
        accountUserBindConnect.setBindNickname(nickname);
        accountUserBindConnect.setBindIcon(headimgurl);
        accountUserBindConnect.setBindType(BindConnectCode.WEIXIN);
        accountUserBindConnect.setBindActive(true);
        return accountUserBindConnect;
    }

    @Override
    public LoginRes checkAppLogin(String code) {
        if (StrUtil.isEmpty(code)) {
            throw new BusinessException(__("传入的code值为空"));
        }
        Map<String, String> data = getJsCode2Session(code);
        if (data == null) {
            throw new BusinessException(__("数据异常"));
        }
        UserBindConnect accountUserBindConnect = new UserBindConnect();
        accountUserBindConnect.setBindId(data.get("openid"));
        accountUserBindConnect.setBindOpenid(data.get("openid"));
        accountUserBindConnect.setBindUnionid(data.get("unionid"));
        accountUserBindConnect.setBindType(BindConnectCode.WEIXIN_XCX);

        UserBindConnect userBindConnect = userBindConnectService.get(data.get("openid"));

        //判断是否存在bind_unionid
        if (userBindConnect == null && ObjectUtil.isNotEmpty(data.get("unionid"))) {
            QueryWrapper<UserBindConnect> where = new QueryWrapper<>();
            where.eq("bind_unionid", data.get("unionid"));
            userBindConnect = userBindConnectService.findOne(where);
        }

        Integer user_id = null;
        if (userBindConnect != null) {
            user_id = userBindConnect.getUserId();
            return loginService.login(user_id);
        }

        return null;
    }

    private Map<String, String> getJsCode2Session(String code) {
        //从微信服务器获得session_key,openid,unionid
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

        String wechat_xcx_app_id = configBaseService.getConfig("wechat_xcx_app_id");
        String wechat_xcx_app_secret = configBaseService.getConfig("wechat_xcx_app_secret");

        url = url.replace("APPID", wechat_xcx_app_id)
                .replace("SECRET", wechat_xcx_app_secret)
                .replace("JSCODE", code);
        String wxResult = HttpUtil.get(url);

        JSONObject jsonObject = JSONUtil.parseObj(wxResult);
        String sessionKey = (String) jsonObject.get("session_key");
        String unionid = (String) jsonObject.get("unionid");
        String openid = (String) jsonObject.get("openid");

        if (StrUtil.isEmpty(sessionKey) || StrUtil.isEmpty(openid)) {
            throw new BusinessException(__("微信登录,调用官方接口失败"));
        }
        Map<String, String> data = new HashMap();
        data.put("sessionKey", sessionKey);
        data.put("unionid", unionid);
        data.put("openid", openid);

        return data;
    }

    @Override
    public LoginRes jsCode2Session(String code, String encryptedData, String iv, String userInfo, Integer activityId, Integer sourceUserId) {
        UserBindConnect vxUserBindConnect = getVxMiniAppUserBindConnect(code, encryptedData, iv, userInfo);
        Integer user_id = loginService.doUserBind(vxUserBindConnect, activityId, true);

        return loginService.login(user_id);
    }

    @Override
    public LoginRes getUserPhoneNumber(String code, ContextUser userDto) {
        String accessToken = getXcxAccessToken(true);
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        Map params = new HashMap();
        params.put("code", code);
        JSON parseParam = JSONUtil.parse(params);
        String responseStr = WxHttpUtil.request(WxHttpUtil.MethodType.POST, WxHttpUtil.WxType.XCX, accessToken, url, null, Convert.toStr(parseParam));
        JSONObject jsonObject = JSONUtil.parseObj(responseStr);
        Integer errcode = jsonObject.get("errcode", Integer.class);

        if (CheckUtil.isNotEmpty(errcode.intValue()) || errcode != 0) {
            throw new BusinessException(__("获取手机号失败"));
        }

        Object phone_info_obj = jsonObject.get("phone_info");
        JSONObject phone_info = JSONUtil.parseObj(phone_info_obj);
        Long phoneNumber = Convert.toLong(phone_info.get("phoneNumber"));
        String user_intl = "+" + Convert.toStr(phone_info.get("countryCode"));
        Integer user_id = loginService.doBindMobile(userDto, user_intl, phoneNumber);

        if (user_id == null) {
            throw new BusinessException(__("绑定手机号失败"));
        }

        return loginService.login(user_id);
    }

    public UserBindConnect getVxMiniAppUserBindConnect(String code, String encryptedData, String iv, String user_info) {
        //解析用户基本信息
        JSONObject jsonObject = JSONUtil.parseObj(user_info);
        String nickName = (String) jsonObject.get("nickName");
        Integer gender = (Integer) jsonObject.get("gender");
        String language = (String) jsonObject.get("language");
        String city = (String) jsonObject.get("city");
        String province = (String) jsonObject.get("province");
        String country = (String) jsonObject.get("country");
        String avatarUrl = (String) jsonObject.get("avatarUrl");

        UserBindConnect accountUserBindConnect = new UserBindConnect();

        Map<String, String> data = getJsCode2Session(code);
        String sessionKey = data.get("sessionKey");
        accountUserBindConnect.setBindId(data.get("openid"));
        accountUserBindConnect.setBindOpenid(data.get("openid"));
        accountUserBindConnect.setBindUnionid(data.get("unionid"));
        accountUserBindConnect.setBindNickname(nickName);
        accountUserBindConnect.setBindIcon(avatarUrl);
        accountUserBindConnect.setBindType(BindConnectCode.WEIXIN_XCX);

        return accountUserBindConnect;
    }

    @Override
    public Map wxConfig(String url) {
        return getTicketSignature(getJsapiTicket(), url);
    }

    /**
     * 根据code 获取openid
     *
     * @param code
     * @param user
     * @return
     */
    @Override
    public Map getOpenIdByCode(String code, ContextUser user) {
        Map data = new HashMap();

        if (user != null && CheckUtil.isNotEmpty(user.getUserId())) {
            UserBindConnect bind_row = loginService.getBind(user.getUserId(), BindConnectCode.WEIXIN_XCX);

            if (ObjectUtil.isNotEmpty(bind_row) && StrUtil.isNotBlank(bind_row.getBindOpenid())) {
                data.put("openid", bind_row.getBindOpenid());
            }
        }

        //不存在openid , 接口获取
        if (StrUtil.isBlank((CharSequence) data.get("openid"))) {
            Map<String, String> code2session = getJsCode2Session(code);
            data.put("openid", code2session.get("openid"));
        }

        return data;

    }

    /**
     * 获取微信 jsapi_ticket
     *
     * @return
     */
    private String getJsapiTicket() {
        String accessToken = getMpAccessToken(true);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
        String params = "access_token=" + accessToken + "&type=jsapi";
        String result = HttpUtil.get(requestUrl + params);

        JSONObject jsonObject = JSONUtil.parseObj(result);
        String ticket = Convert.toStr(jsonObject.get("ticket"));
        return ticket;
    }

    /**
     * 获取微信 jsapi_ticket 签名
     *
     * @param url
     * @param jsapi_ticket
     * @return
     */
    private Map<String, String> getTicketSignature(String url, String jsapi_ticket) {
        String nonce_str = IdUtil.simpleUUID();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        String wechat_app_id = configBaseService.getConfig("wechat_app_id");
        signature = SecureUtil.sha1(signature);
        Map<String, String> data = new HashMap<>();
        data.put("url", url);
        data.put("appId", wechat_app_id);
//        data.put("jsapi_ticket", jsapi_ticket);
        data.put("nonceStr", nonce_str);
        data.put("timestamp", timestamp);
        data.put("signature", signature);
        return data;
    }

    @Override
    public boolean callbackMessage() {
        return false;
    }

    /**
     * 微信网页登录生成二维码
     *
     * @return
     */
    @Override
    public String getQrCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
        String redirectUrl = ConstantConfig.URL_BASE + "/account/wechat/callbackPc";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String weixin_status = configBaseService.getConfig("weixin_status");
        if (CheckUtil.isEmpty(weixin_status)) {
            throw new BusinessException("网页登录功能未启用");
        }
        String wechat_app_id = configBaseService.getConfig("weixin_app_id");

        String url = String.format(baseUrl, wechat_app_id, redirectUrl, "MallSuite");
        return url;
    }

    /**
     * 微信网页登录回调
     *
     * @param code
     * @param response
     */
    @Override

    public void callbackPc(String code, HttpServletResponse response) {
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        String wechat_app_id = configBaseService.getConfig("weixin_app_id");
        String wechat_app_secret = configBaseService.getConfig("weixin_app_key");
        String accessTokenUrl = String.format(baseAccessTokenUrl, wechat_app_id, wechat_app_secret, code);

        String result = HttpUtil.get(accessTokenUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        Integer errcode = Convert.toInt(jsonObject.get("errcode"));
        if (CheckUtil.isNotEmpty(errcode)) {
            String errmsg = Convert.toStr(jsonObject.get("errmsg"));
            LogUtil.error(ErrorTypeEnum.ERR_WX_JSPI.getValue(), errmsg);
            throw new BusinessException("扫码登录失败!");
        }

        String openid = Convert.toStr(jsonObject.get("openid"));
        String unionid = Convert.toStr(jsonObject.get("unionid"));
        String access_token = Convert.toStr(jsonObject.get("access_token"));

        UserBindConnect accountUserBindConnect = new UserBindConnect();
        accountUserBindConnect.setBindId(openid);
        accountUserBindConnect.setBindOpenid(openid);
        accountUserBindConnect.setBindUnionid(unionid);
        accountUserBindConnect.setBindType(BindConnectCode.WEIXIN);

        String vxUserInfo = getVxUserInfo(access_token, openid);
        UserBindConnect vxUserBindConnect = getVxMpUserBindConnect(vxUserInfo);
        Integer user_id = loginService.doUserBind(vxUserBindConnect, null, true);

        LoginRes login = loginService.login(user_id);

        String wxUserToken = login.getToken();

        String redirectUrl = ConstantConfig.URL_PC + "/#/login/login?ukey=" + wxUserToken;
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new BusinessException(__("微信登录跳转失败！"));
        }
    }

}
