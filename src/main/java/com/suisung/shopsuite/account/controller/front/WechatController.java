package com.suisung.shopsuite.account.controller.front;

import com.suisung.shopsuite.account.model.res.LoginRes;
import com.suisung.shopsuite.account.service.WechatService;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Api(tags = "微信操作控制器")
@RestController
@RequestMapping("/front/account/wechat")
public class WechatController extends BaseController {

    @Autowired
    private WechatService wechatService;

    /**
     * <pre>
     * 验证推送过来的消息的正确性
     * </pre>
     *
     * @param signature 消息签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return the boolean
     */
    private final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @ApiOperation(value = "验证签名")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String checkSignature(@RequestParam(name = "timestamp") String timestamp,
                                 @RequestParam(name = "signature") String signature,
                                 @RequestParam(name = "nonce") String nonce,
                                 @RequestParam(name = "echostr") String echostr) {
        return wechatService.checkSignature(timestamp, nonce, signature) == true ? echostr : null;
    }

    @ApiOperation(value = "公众号登录 - 获取code请求")
    @RequestMapping(value = "/redirectToWxCode", method = RequestMethod.GET)
    public void redirectToWxCode(HttpServletRequest request, HttpServletResponse response) {
        wechatService.redirectToWxCode(request, response);
    }

    @ApiOperation(value = "公众号登录 - 公众号授权回调")
    @RequestMapping(value = "/callbackMp", method = RequestMethod.GET)
    public CommonRes<?> callbackMp(HttpServletResponse response,
                                   @RequestParam(name = "activity_id", required = false) Integer activity_id,
                                   @RequestParam(name = "code") String code) {
        LoginRes loginRes = wechatService.callbackMp(response, activity_id, code);

        return success(loginRes);
    }

    @ApiOperation(value = "用户登录验证")
    @RequestMapping(value = "/checkAppLogin", method = RequestMethod.GET)
    public CommonRes<?> checkAppLogin(@RequestParam(name = "code") String code) {
        LoginRes checkAppLogin = wechatService.checkAppLogin(code);

        if (checkAppLogin != null) {
            return success(checkAppLogin);
        }

        return fail(__("请先登录"));
    }

    @ApiOperation(value = "获取微信配置请求")
    @RequestMapping(value = "/wxConfig", method = RequestMethod.GET)
    public CommonRes<?> wxConfig(@RequestParam(name = "href") String url) {
        return success(wechatService.wxConfig(url));
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/jsCode2Session", method = RequestMethod.GET)
    public CommonRes<?> jsCode2Session(@RequestParam(name = "code") String code,
                                       @RequestParam(name = "encryptedData") String encryptedData,
                                       @RequestParam(name = "iv") String iv,
                                       @RequestParam(name = "user_info") String userInfo,
                                       @RequestParam(name = "activity_id", required = false) Integer activityId,
                                       @RequestParam(name = "source_user_id", required = false) Integer sourceUserId) {
        return success(wechatService.jsCode2Session(code, encryptedData, iv, userInfo, activityId, sourceUserId));
    }

    @ApiOperation(value = "小程序获取手机号")
    @RequestMapping(value = "/getUserPhoneNumber", method = RequestMethod.GET)
    public CommonRes<?> getUserPhoneNumber(@RequestParam(name = "code") String code) {
        return success(wechatService.getUserPhoneNumber(code, ContextUtil.getLoginUser()));
    }

    @ApiOperation(value = "获取getXcxAccessToken-向外提供")
    @RequestMapping(value = "/getXcxAccessToken", method = RequestMethod.GET)
    public String getXcxAccessToken(@RequestParam(name = "useCacheFlag") boolean useCacheFlag) {
        return wechatService.getXcxAccessToken(useCacheFlag);
    }

    @ApiOperation(value = "根据code 获取openid")
    @RequestMapping(value = "/getOpenIdByCode", method = RequestMethod.GET)
    public CommonRes<?> getOpenIdByCode(@RequestParam(name = "code") String code) {
        return success(wechatService.getOpenIdByCode(code, ContextUtil.getLoginUser()));
    }

    @ApiOperation(value = "微信通知消息回调")
    @RequestMapping(value = "/callbackMessage", method = RequestMethod.GET)
    public CommonRes<?> callbackMessage() {
        return success(wechatService.callbackMessage());
    }

    @ApiOperation(value = "微信网页登录生成二维码")
    @RequestMapping(value = "/getQrCode", method = RequestMethod.GET)
    public CommonRes<?> getQrCode() {
        return success(wechatService.getQrCode());
    }

    @ApiOperation(value = "二维码登录 - 微信网页回调")
    @RequestMapping(value = "/callbackPc", method = RequestMethod.GET)
    public void callbackPc(String code, HttpServletResponse response) {
        wechatService.callbackPc(code, response);
    }

}
