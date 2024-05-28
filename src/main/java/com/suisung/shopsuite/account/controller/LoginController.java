package com.suisung.shopsuite.account.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.account.model.entity.UserBindConnect;
import com.suisung.shopsuite.account.model.input.RegInput;
import com.suisung.shopsuite.account.model.req.LoginReq;
import com.suisung.shopsuite.account.model.req.RegReq;
import com.suisung.shopsuite.account.model.req.ResetPasswordReq;
import com.suisung.shopsuite.account.model.res.LoginPolicyRes;
import com.suisung.shopsuite.account.model.res.LoginRes;
import com.suisung.shopsuite.account.model.res.LogoutRes;
import com.suisung.shopsuite.account.repository.UserBaseRepository;
import com.suisung.shopsuite.account.repository.UserBindConnectRepository;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.common.config.ConfigProperties;
import com.suisung.shopsuite.common.consts.BindConnectCode;
import com.suisung.shopsuite.common.consts.ConstantMsg;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.RSAUtil;
import com.suisung.shopsuite.common.utils.phone.PhoneModel;
import com.suisung.shopsuite.common.utils.phone.PhoneNumberUtils;
import com.suisung.shopsuite.common.web.service.VerifyCodeService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/front/account/login")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserBindConnectRepository userBindConnectRepository;

    @Autowired
    private UserBaseRepository userBaseRepository;


    @Autowired
    private VerifyCodeService verifyCodeService;

    @Resource
    private ConfigProperties configProperties;

    @Autowired
    private ConfigBaseService configBaseService;

    @ApiOperation(value = "登录接口", notes = "登录接口")
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<LoginRes> login(LoginReq loginReq) throws Exception {
        //loginReq 数据验证
        if (loginReq.getEncrypt()) {
            String decrypt = RSAUtil.decrypt(loginReq.getPassword(), configBaseService.getConfig("private_key", configProperties.getPrivateKey()));
            loginReq.setPassword(decrypt);
        }

        //验证码
        if (CheckUtil.isNotEmpty(loginReq.getVerifyKey())) {
            if (!verifyCodeService.checkVerifyCode(loginReq.getVerifyKey(), loginReq.getVerifyCode())) {
                return fail(__("验证码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
            }
        }

        LoginRes loginRes = loginService.login(loginReq);

        return success(loginRes);
    }


    @ApiOperation(value = "短信登录接口", notes = "短信登录接口")
    @RequestMapping(value = "/doSmsLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<?> doSmsLogin(RegReq regReq) {
        //判断手机号
        PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(regReq.getVerifyKey());

        if (phoneModelWithCountry == null) {
            return fail(__("手机号码不正确"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        //验证码
        if (!verifyCodeService.checkVerifyCode(regReq.getVerifyKey(), regReq.getVerifyCode())) {
            return fail(__("验证码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        RegInput input = new RegInput();
        input.setUserIntl(phoneModelWithCountry.getCountryCodeStr());
        input.setUserMobile(phoneModelWithCountry.getNationalNumber());
        input.setBindType(BindConnectCode.MOBILE);

        LoginRes loginRes = loginService.doSmsLogin(input);

        return success(loginRes);
    }

    @ApiOperation(value = "注册接口", notes = "注册接口")
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<LoginRes> register(RegReq regReq) {
        //loginReq 数据验证

        //验证码
        if (!verifyCodeService.checkVerifyCode(regReq.getVerifyKey(), regReq.getVerifyCode())) {
            return fail(__("验证码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        RegInput input = BeanUtil.copyProperties(regReq, RegInput.class);
        input.setUserAccount(regReq.getUserAccount());
        input.setPassword(regReq.getPassword());
        input.setEncrypt(regReq.getEncrypt());

        input.setUserParentId(regReq.getSourceUserId());

        Integer userId = loginService.register(input);

        return success(loginService.login(userId));
    }


    @ApiOperation(value = "重设密码接口", notes = "重设密码接口")
    @RequestMapping(value = "/setNewPassword", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<?> setNewPassword(ResetPasswordReq req) {
        //验证码
        if (!verifyCodeService.checkVerifyCode(req.getVerifyKey(), req.getVerifyCode())) {
            return fail(__("验证码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        if (CheckUtil.isEmpty(req.getPassword())) {
            return fail(__("密码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }


        Integer userId = null;


        if (req.getBindType().intValue() == BindConnectCode.MOBILE) {
            //判断手机号
            PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(req.getVerifyKey());

            if (phoneModelWithCountry == null) {
                return fail(__("手机号码不正确"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
            }

            String bindId = String.format("%s%d", phoneModelWithCountry.getCountryCodeStr(), phoneModelWithCountry.getNationalNumber());

            //判断是否绑定
            UserBindConnect userBindConnect = userBindConnectRepository.get(bindId);

            if (ObjectUtil.isNotEmpty(userBindConnect)) {
                userId = userBindConnect.getUserId();
            } else {
                return fail(__("手机号码未绑定登录"));
            }
        } else if (req.getBindType().intValue() == BindConnectCode.EMAIL) {
            if (!CheckUtil.isEmail(req.getVerifyKey())) {
                return fail(__("该邮箱不存在"));
            }

            //判断是否绑定
            UserBindConnect userBindConnect = userBindConnectRepository.get(req.getVerifyKey());

            if (ObjectUtil.isNotEmpty(userBindConnect)) {
                userId = userBindConnect.getUserId();
            } else {
                return fail(__("该邮箱绑定不存在"));
            }
        } else if (req.getBindType().intValue() == BindConnectCode.ACCOUNT) {
            userId = ContextUtil.checkLoginUserId();

            UserBase user = userBaseRepository.get(userId);

            if (!user.getUserPassword().equals(loginService.encodePassword(req.getOldPassword(), user.getUserSalt()))) {
                throw new BusinessException(ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED, __("旧密码不正确"));
            }
        }

        loginService.doResetPasswd(userId, req.getPassword());

        return success();
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public CommonRes<?> changePassword(ResetPasswordReq req) {

        if (CheckUtil.isEmpty(req.getPassword())) {
            throw new BusinessException(__("密码有误！"));
        }

        Integer userId = ContextUtil.checkLoginUserId();
        UserBase user = userBaseRepository.get(userId);

        if (!user.getUserPassword().equals(loginService.encodePassword(req.getOldPassword(), user.getUserSalt()))) {
            throw new BusinessException(__("旧密码不正确！"));
        }

        loginService.doResetPasswd(userId, req.getPassword());

        return success();
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<LogoutRes> logout(LoginReq loginReq) {
        LogoutRes logoutRes = loginService.logout();

        return success(logoutRes);
    }

    @ApiOperation(value = "隐私政策", notes = "隐私政策")
    @RequestMapping(value = "/protocol", method = RequestMethod.GET)
    public CommonRes<LoginPolicyRes> protocol(@RequestParam(name = "document_type", required = false) String documentType,
                                              @RequestParam(name = "state", required = false) Integer state,
                                              @RequestParam(name = "protocols_key", required = false, defaultValue = "reg_protocols_description") String protocolsKey) {
        LoginPolicyRes loginPolicyRes = loginService.protocol(documentType, state, protocolsKey);

        return success(loginPolicyRes);
    }
}

