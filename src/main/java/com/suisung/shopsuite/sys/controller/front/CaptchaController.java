package com.suisung.shopsuite.sys.controller.front;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.EmailDto;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.EmailUtil;
import com.suisung.shopsuite.common.utils.phone.PhoneModel;
import com.suisung.shopsuite.common.utils.phone.PhoneNumberUtils;
import com.suisung.shopsuite.common.web.service.VerifyCodeService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.SmsDto;
import com.suisung.shopsuite.core.web.service.CaptchaService;
import com.suisung.shopsuite.core.web.service.CloundService;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Api(tags = "用户基本信息表")
@RestController
@RequestMapping("/front/sys/captcha")
public class CaptchaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private CloundService cloundService;

    @Autowired
    private ConfigBaseService configBaseService;

    @ApiOperation(value = "生成图像验证码", notes = "返回的是图片二进制内容")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void index(@RequestParam(name = "verify_key") String verifyKey, HttpServletResponse response) throws IOException {
        //设置response响应
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        LineCaptcha image = captchaService.image(verifyKey);

        verifyCodeService.setVerifyCode(verifyKey, image.getCode());

        /*
        //把图形验证码凭证放入cookie中
        String tokenId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("imgCodeToken",tokenId);
        cookie.setPath("/");
        response.addCookie(cookie);
         */

        //输出浏览器
        OutputStream out = response.getOutputStream();
        image.write(out);
        out.flush();
        out.close();
    }

    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码")
    @RequestMapping(value = "/mobile", method = RequestMethod.GET)
    public CommonRes<?> sendMobileVerifyCode(@RequestParam(name = "mobile") String verifyKey) {
        boolean flag = true;
        String configBaseUserId = configBaseService.getConfig("service_user_id", "");
        String configBaseAppKey = configBaseService.getConfig("service_app_key", "");
        String verifyCode = RandomUtil.randomNumbers(4);

        if (!PhoneNumberUtils.isValidNumber(verifyKey)) {
            throw new BusinessException(__("手机号码不准确！"));
        }

        PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(verifyKey);

        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(Convert.toStr(phoneModelWithCountry.getNationalNumber()));
        smsDto.setServiceUserId(configBaseUserId);
        smsDto.setServiceAppKey(configBaseAppKey);
        smsDto.setContent(String.format(__("您的验证码: [%s] 5分钟内有效"), verifyCode));

        try {
            flag = cloundService.send(smsDto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        verifyCodeService.setVerifyCode(verifyKey, verifyCode);

        if (flag) {
            return success();
        } else {
            return fail();
        }
    }

    @ApiOperation(value = "发送邮件验证码", notes = "发送邮件验证码")
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public CommonRes<?> sendEmailVerifyCode(@RequestParam(name = "email") String email) {
        boolean flag = true;
        String configBaseUserId = configBaseService.getConfig("service_user_id", "");
        String configBaseAppKey = configBaseService.getConfig("service_app_key", "");
        String verifyCode = RandomUtil.randomNumbers(4);

        if (!CheckUtil.isEmail(email)) {
            throw new BusinessException(__("Email不准确！"));
        }

        QueryWrapper<ConfigBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_type", "email");

        // 获取emailConfig 对象
        List<ConfigBase> configs = configBaseService.find(queryWrapper);
        Map<String, String> configMap = configs.stream().collect(Collectors.toMap(ConfigBase::getConfigKey, ConfigBase::getConfigValue, (k1, k2) -> k2));
        EmailDto emailDto = Convert.convert(EmailDto.class, configMap);
        emailDto.setEmailToAddress(email);
        emailDto.setSubject(String.format(__("%s 注册验证码"), configBaseService.getConfig("site_name", "MallSuite")));
        emailDto.setContent(String.format(__("您的验证码: [%s] 5分钟内有效"), verifyCode));
        try {
            flag = EmailUtil.send(emailDto);
        } catch (Exception e) {
            throw new BusinessException(__("邮件发送失败！"));
        }

        verifyCodeService.setVerifyCode(email, verifyCode);

        if (flag) {
            return success();
        } else {
            return fail();
        }
    }
}

