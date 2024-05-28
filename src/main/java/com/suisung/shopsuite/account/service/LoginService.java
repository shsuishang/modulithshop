package com.suisung.shopsuite.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.account.model.entity.UserBindConnect;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.model.input.RegInput;
import com.suisung.shopsuite.account.model.output.UserInfoOutput;
import com.suisung.shopsuite.account.model.req.LoginReq;
import com.suisung.shopsuite.account.model.res.ExpRuleRes;
import com.suisung.shopsuite.account.model.res.LoginPolicyRes;
import com.suisung.shopsuite.account.model.res.LoginRes;
import com.suisung.shopsuite.account.model.res.LogoutRes;
import com.suisung.shopsuite.common.web.ContextUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户登录
 *
 * @author Xinze
 * @since 2018-12-24 16:10:52
 */
public interface LoginService extends UserDetailsService {
    LoginRes login(UserBase user);

    LoginRes login(Integer userId);

    /**
     * 用户登录
     *
     * @param loginReq
     * @return
     */
    LoginRes login(LoginReq loginReq);

    /**
     * 用户注册
     *
     * @param input
     * @return
     */
    Integer register(RegInput input);

    /**
     * 退出登录
     *
     * @return
     */
    LogoutRes logout();

    /**
     * 手机快捷登录
     *
     * @param input
     * @return
     */
    LoginRes doSmsLogin(RegInput input);

    /**
     * md5加密用户密码
     *
     * @param password 密码明文
     * @param userSalt 盐
     * @return 密文
     */
    String encodePassword(String password, String userSalt);

    boolean doResetPasswd(Integer userId, String password);

    /**
     * 根据编号查询用户
     *
     * @param userId 账号
     * @return UserBase
     */
    UserBase getByUserId(Integer userId);

    /**
     * 根据编号查询用户
     *
     * @param userId 账号
     * @return UserInfoOutput
     */
    UserInfoOutput getInfoByUserId(Integer userId);

    /**
     * 隐私政策
     *
     * @param documentType
     * @param state
     * @param protocolsKey
     * @return
     */
    LoginPolicyRes protocol(String documentType, Integer state, String protocolsKey);

    /**
     * 修改用户端用户信息
     *
     * @param userInfo
     * @return
     */
    boolean edit(UserInfo userInfo);


    LoginRes bindMobile(ContextUser user, String user_intl, Long mobile, String new_password);

    boolean unBindMobile(ContextUser user, String user_intl, Long mobile);

    LoginRes reBindMobile(ContextUser user, String user_intl, Long mobile, String new_password);

    /**
     * 绑定手机号操作
     *
     * @param user_intl   手机区号
     * @param currentUser 用户登录信息
     */
    Integer doBindMobile(ContextUser currentUser, String user_intl, Long mobile);


    /**
     * 传入各渠道数据信息，完成绑定操作，包含注册绑定
     *
     * @param user_info_row bind_connect数据
     * @param reg_flag      是否需要注册新账号
     * @source_user_id 父级用户id
     * @activity_id 活动id
     * @access public
     */
    Integer doUserBind(UserBindConnect user_info_row, Integer activity_id, boolean reg_flag);


    boolean checkBind(String bind_id, int bind_type, Integer user_id, UserBindConnect user_info_row);

    boolean checkAccessToken(Integer bind_type, UserBindConnect bind_data, Integer user_id);

    UserBindConnect getBind(Integer user_id, int bind_type);

    /**
     * 实名认证保存
     *
     * @param userInfo
     * @return
     */
    boolean saveCertificate(UserInfo userInfo);

    /**
     * 读取平台用户等级
     *
     * @return
     */
    IPage<UserLevel> listBaseUserLevel();

    /**
     * 用户等级规则
     *
     * @return
     */
    ExpRuleRes listsExpRule();


    /**
     * 用户来源
     *
     * @return
     */
    Integer getSourceUserId();

    /**
     * 渠道来源
     *
     * @return
     */
    String getSourceUccCode();

    /**
     * 渠道来源
     *
     * @param user_id
     * @param source_ucc_code
     * @return
     */
    boolean addChannelSourceUserId(Integer user_id, String source_ucc_code);
}
