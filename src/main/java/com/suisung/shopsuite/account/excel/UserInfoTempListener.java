package com.suisung.shopsuite.account.excel;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.input.RegInput;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.account.service.UserInfoService;
import com.suisung.shopsuite.common.consts.BindConnectCode;
import com.suisung.shopsuite.common.utils.WxHttpUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;


public class UserInfoTempListener extends AnalysisEventListener<UserInfoTemp> {

    private final ArrayList<UserInfo> list = new ArrayList<>();


    public static UserInfoService userInfoService;


    public static LoginService loginService;

    static {
        initDictionary();
    }

    static void initDictionary() {
        userInfoService = WxHttpUtil.getBean(UserInfoService.class);
        loginService = WxHttpUtil.getBean(LoginService.class);
    }

    /**
     * 每解析一行，回调该方
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(UserInfoTemp data, AnalysisContext context) {
        initDictionary();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(data, userInfo);
        list.add(userInfo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (list.size() == 0) return;
        for (UserInfo userInfo : list) {
            //手机号注册
            RegInput in = BeanUtil.copyProperties(userInfo, RegInput.class);
            in.setBindType(BindConnectCode.ACCOUNT);
            Integer userId = loginService.register(in);

            //手机绑定
            ContextUser user = new ContextUser();
            user.setUserId(userId);
            Integer uid = loginService.doBindMobile(user, in.getUserIntl(), in.getUserMobile());

            userInfo.setUserId(userId);
            userInfoService.save(userInfo);
        }
        // 清除数据
        list.clear();
    }
}
