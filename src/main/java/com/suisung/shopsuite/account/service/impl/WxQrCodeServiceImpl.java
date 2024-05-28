package com.suisung.shopsuite.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.suisung.shopsuite.account.service.WechatService;
import com.suisung.shopsuite.account.service.WxQrCodeService;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.UploadUtil;
import com.suisung.shopsuite.core.consts.ConstantRedis;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 消息模板表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-18
 */
@Service
public class WxQrCodeServiceImpl implements WxQrCodeService {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ConfigBaseService configBaseService;

    private final String xcxTokenKey = ConstantRedis.Cache_NameSpace + "xcx_token" + ConstantRedis.SEPARATOR;

    public String getAccessToken() {
        if (!redisService.hasKey(xcxTokenKey)) {
            getToken();
        }

        return Convert.toStr(redisService.get(xcxTokenKey));
    }

    private void getToken() {
        String xcx_app_id = configBaseService.getConfig("wechat_xcx_app_id");
        String xcx_app_secret = configBaseService.getConfig("wechat_xcx_app_secret");

        Map<String, Object> params = new HashMap<>();
        params.put("appid", xcx_app_id);
        params.put("secret", xcx_app_secret);
//        params.put("cache_name", "wechat");
        params.put("grant_type", "client_credential");

        JSONObject resultObj = new JSONObject();
        String response = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", params);
        JSONObject jsonObject = JSONUtil.parseObj(response);

        Object errmsg = jsonObject.get("errmsg");
        if (ObjectUtil.isNotEmpty(errmsg)) {
            String errmsgStr = Convert.toStr(resultObj.get("errmsg"));
            if (!errmsgStr.equals("0")) {
                throw new BusinessException(__("获取access_token失败"));
            }
        }
        Integer expiresIn = Convert.toInt(jsonObject.get("expires_in"), 0);
        redisService.set(xcxTokenKey, Convert.toStr(jsonObject.get("access_token")), expiresIn);
    }

    /**
     * 生成小程序太阳码
     *
     * @param preparedUrl
     * @param param
     * @return
     * @throws IOException
     */
    @Override
    public Map getUnlimited(String preparedUrl, Map param) {
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
            String reqUrl = url + getAccessToken();
            Map params = new HashMap();
            Map map = parseUrl(preparedUrl);
            Map<String, Object> lineColor = new HashMap<>();
            lineColor.put("r", 0);
            lineColor.put("g", 0);
            lineColor.put("b", 0);
            params.put("scene", map.get("query"));
            params.put("page", map.get("lastUrl"));
            params.put("width", 430);
            params.put("auto_color", true);
            params.put("line_color", lineColor);
            params.put("is_hyaline", false);

            JSON jsonParams = JSONUtil.parse(params);
            String paramStr = jsonParams.toString();
            String qr_filename = SecureUtil.md5(com.alibaba.fastjson.JSONObject.toJSONString(param)) + "-qrcode.png";

            // 这个地方较为特殊，不能用hutool的工具类进行请求，因此如果获取失败，则自动认定token失效，进行重新获取token
            byte[] bytes = HttpRequest.post(reqUrl).body(paramStr).execute().bodyBytes();
            if (bytes.length < 9999) {
                String accessToken = wechatService.getXcxAccessToken(true);
                reqUrl = url + accessToken;
                bytes = HttpRequest.post(reqUrl).body(paramStr).execute().bodyBytes();
                // 重试两次，如果还是失败就认定为非token问题
                if (bytes.length < 9999) {
                    throw new BusinessException(__("图片生成错误!"));
                }
            }

            Map resultMap = new HashMap();


            String poster_path = String.format("%s/poster_%s/", UploadUtil.getUploadTempDir(), 2);

            IoUtil.write(FileUtil.getOutputStream(poster_path + qr_filename), true, bytes);
            resultMap.put("floder", poster_path + qr_filename);
            resultMap.put("qr_filename", qr_filename);
            return resultMap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static Map parseUrl(String preparedUrl) throws MalformedURLException {
        URL url = new URL(preparedUrl);
        String urlPath = url.getPath();

        Map resultMap = new HashMap();
        urlPath = urlPath.replace("/wap", "");
        urlPath = urlPath.replaceFirst("/", "").replaceAll("h5/", "");
        resultMap.put("lastUrl", urlPath);
        String query = url.getQuery();

        if (StrUtil.isNotEmpty(query)) {
            String[] params = query.split("&");
            List<String> paramList = Convert.toList(String.class, params);
            Iterator<String> it = paramList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next.startsWith("source_ucc_code")) {
                    it.remove();
                }
            }

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < paramList.size(); i++) {
                sb.append(paramList.get(i));
                if (i + 1 < paramList.size()) {
                    sb.append("&");
                }
            }
            query = sb.toString();
            // 如果参数长度超过32位
            if (query.length() > 32) {
                StringBuffer longStr = new StringBuffer();
                for (int i = 0; i < paramList.size(); i++) {
                    String[] split = paramList.get(i).split("=");
                    longStr.append(split[1]);
                    if (i + 1 < paramList.size()) {
                        longStr.append("-");
                    }
                }
                query = "longUrl=" + longStr;
            }
        }

        resultMap.put("query", query);
        return resultMap;
    }

}
