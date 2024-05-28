package com.suisung.shopsuite.account.controller.front;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.model.output.UserInfoOutput;
import com.suisung.shopsuite.account.model.req.RegReq;
import com.suisung.shopsuite.account.model.req.UserInfoEditReq;
import com.suisung.shopsuite.account.model.res.ExpRuleRes;
import com.suisung.shopsuite.account.model.res.LoginRes;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantMsg;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.phone.PhoneModel;
import com.suisung.shopsuite.common.utils.phone.PhoneNumberUtils;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.common.web.service.VerifyCodeService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/front/account/user")
public class UserController extends BaseController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private VerifyCodeService verifyCodeService;


    @ApiOperation(value = "用户基本信息", notes = "用户基本信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonRes<?> getUserInfo() {
        Integer userId = ContextUtil.getLoginUserId();
        if (CheckUtil.isNotEmpty(userId)) {
            UserInfoOutput userBase = loginService.getInfoByUserId(ContextUtil.getLoginUserId());
            userBase.setClientId(ContextUtil.getLoginUser().getClientId());
            return success(userBase);
        } else {
            return fail(__("尚未登录"));
        }
    }

    @ApiOperation(value = "修改用户端用户信息", notes = "修改用户端用户信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserInfoEditReq userInfoEditReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoEditReq, UserInfo.class);
        Integer userId = ContextUtil.checkLoginUserId();
        userInfo.setUserId(userId);
        boolean success = loginService.edit(userInfo);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "绑定手机号", notes = "绑定手机号")
    @RequestMapping(value = "/bindMobile", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<?> bindMobile(RegReq req) {
        //判断手机号
        PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(req.getVerifyKey());

        if (phoneModelWithCountry == null) {
            return fail(__("手机号码不正确"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        //验证码
        if (!verifyCodeService.checkVerifyCode(req.getVerifyKey(), req.getVerifyCode())) {
            return fail(__("验证码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        ContextUser loginUser = ContextUtil.getLoginUser();
        LoginRes loginRes = loginService.bindMobile(loginUser, phoneModelWithCountry.getCountryCodeStr(), phoneModelWithCountry.getNationalNumber(), "");

        return success(loginRes);
    }


    @ApiOperation(value = "重新绑定手机号", notes = "重新绑定手机号")
    @RequestMapping(value = "/unBindMobile", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<?> unBindMobile(RegReq req) {
        //判断手机号
        PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(req.getVerifyKey());

        if (phoneModelWithCountry == null) {
            return fail(__("手机号码不正确"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        //验证码
        if (!verifyCodeService.checkVerifyCode(req.getVerifyKey(), req.getVerifyCode())) {
            return fail(__("验证码有误"), ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED);
        }

        ContextUser loginUser = ContextUtil.getLoginUser();

        //绑定新手机
        boolean flag = loginService.unBindMobile(loginUser, phoneModelWithCountry.getCountryCodeStr(), phoneModelWithCountry.getNationalNumber());

        if (flag) {
            return success();
        } else {
            return fail();
        }
    }

    @ApiOperation(value = "实名认证保存", notes = "实名认证保存")
    @RequestMapping(value = "/saveCertificate", method = RequestMethod.POST)
    public CommonRes<?> saveCertificate(UserInfoEditReq userInfoEditReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoEditReq, UserInfo.class);
        Integer userId = ContextUtil.checkLoginUserId();
        userInfo.setUserId(userId);
        userInfo.setUserIsAuthentication(StateCode.USER_CERTIFICATION_VERIFY);
        boolean success = loginService.saveCertificate(userInfo);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "读取平台用户等级", notes = "读取平台用户等级")
    @RequestMapping(value = "/listBaseUserLevel", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserLevel>> listBaseUserLevel() {
        IPage<UserLevel> pageList = loginService.listBaseUserLevel();

        return success(pageList);
    }

    @ApiOperation(value = "用户等级规则", notes = "用户等级规则")
    @RequestMapping(value = "/listsExpRule", method = RequestMethod.GET)
    public CommonRes<ExpRuleRes> listsExpRule() {
        ExpRuleRes expRuleRes = loginService.listsExpRule();

        return success(expRuleRes);
    }
}

