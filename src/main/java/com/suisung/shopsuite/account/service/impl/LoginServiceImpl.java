package com.suisung.shopsuite.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.*;
import com.suisung.shopsuite.account.model.input.RegInput;
import com.suisung.shopsuite.account.model.output.UserInfoOutput;
import com.suisung.shopsuite.account.model.req.LoginReq;
import com.suisung.shopsuite.account.model.req.UserMessageListReq;
import com.suisung.shopsuite.account.model.res.*;
import com.suisung.shopsuite.account.repository.*;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.account.service.UserMessageService;
import com.suisung.shopsuite.admin.model.entity.MenuBase;
import com.suisung.shopsuite.admin.model.entity.UserAdmin;
import com.suisung.shopsuite.admin.model.entity.UserRole;
import com.suisung.shopsuite.admin.repository.MenuBaseRepository;
import com.suisung.shopsuite.admin.repository.UserAdminRepository;
import com.suisung.shopsuite.admin.repository.UserRoleRepository;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.*;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.security.JwtSubject;
import com.suisung.shopsuite.common.security.JwtUtil;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.HttpServletUtils;
import com.suisung.shopsuite.common.utils.phone.PhoneModel;
import com.suisung.shopsuite.common.utils.phone.PhoneNumberUtils;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.repository.UserResourceRepository;
import com.suisung.shopsuite.pay.service.UserResourceService;
import com.suisung.shopsuite.shop.model.entity.UserFavoritesItem;
import com.suisung.shopsuite.shop.model.entity.UserVoucher;
import com.suisung.shopsuite.shop.repository.UserFavoritesItemRepository;
import com.suisung.shopsuite.shop.repository.UserVoucherRepository;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.trade.model.input.OrderNumInput;
import com.suisung.shopsuite.trade.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.suisung.shopsuite.common.consts.ConstantJwt.TOKEN_EXPIRED_CODE;
import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * 用户Service
 *
 * @author Xinze
 * @since 2018-12-24 16:10:52
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserBaseRepository userBaseRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserResourceRepository userResourceRepository;

    @Autowired
    private UserResourceService userResourceService;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserAdminRepository userAdminRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private MenuBaseRepository menuBaseRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private UserBindConnectRepository userBindConnectRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private UserLevelRepository userLevelRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserFavoritesItemRepository userFavoritesItemRepository;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserLoginHistoryRepository userLoginHistoryRepository;

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private HttpServletRequest servletRequest;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Override
    public LoginRes login(UserBase user) {
        if (user == null) {
            String message = __("账号不存在");
            throw new BusinessException(message);
        }

        //生成token
        // 签发token
        String access_token = JwtUtil.buildToken(new JwtSubject(user.getUserId(), user.getUserSalt(), 9, 0, 0, 0, 1),
                ConstantJwt.TOKEN_EXPIRETIME, ConstantJwt.TOKEN_KEY);

        LoginRes loginRes = new LoginRes();
        loginRes.setToken(access_token);
        Integer userId = user.getUserId();
        loginRes.setUserId(userId);

        // 记录登录时间
        UserLoginHistory loginHistory = new UserLoginHistory();
        loginHistory.setUserId(userId);
        loginHistory.setUserAccount(user.getUserAccount());

        String ip = HttpServletUtils.getClientIpAddr();
        loginHistory.setUserLoginIp(ip);
        loginHistory.setUserLoginTime(new Date().getTime());
        loginHistory.setUserLoginState(true);
        if (!userLoginHistoryRepository.save(loginHistory)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        return loginRes;
    }

    /**
     * 用户登录
     *
     * @param userId
     * @return
     */
    @Override
    public LoginRes login(Integer userId) {
        UserBase user = userBaseRepository.get(userId);

        return login(user);
    }

    /**
     * 用户登录
     *
     * @param loginReq
     * @return
     */
    @Override
    public LoginRes login(LoginReq loginReq) {
        UserBase user = userBaseRepository.getByUserAccount(loginReq.getUserAccount());

        if (ObjectUtil.isEmpty(user)) {
            //判断是否为手机号
            PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(loginReq.getUserAccount());

            if (phoneModelWithCountry == null) {
                throw new BusinessException(ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED, __("用户名或者密码不正确"));
            } else {
                //尝试手机绑定登录
                String bindId = String.format("%s%d", phoneModelWithCountry.getCountryCodeStr(), phoneModelWithCountry.getNationalNumber());
                UserBindConnect userBindConnect = userBindConnectRepository.get(bindId);

                if (userBindConnect != null) {
                    user = userBaseRepository.get(userBindConnect.getUserId());
                } else {
                    throw new BusinessException(ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED, __("用户名或者密码不正确"));
                }
            }
        }

        //todo 判断密码
        if (!user.getUserPassword().equals(encodePassword(loginReq.getPassword(), user.getUserSalt()))) {
            throw new BusinessException(ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED, __("用户名或者密码不正确"));
        }

        UserInfo userInfo = userInfoRepository.get(user.getUserId());

        if (ObjectUtil.isEmpty(userInfo) || userInfo.getUserState().equals(0)) {
            throw new BusinessException(ConstantMsg.CODE_BUSINESS_VALIDATION_FAILED, __("您的账号已被禁用,请联系管理员"));
        }

        return login(user);
    }

    /**
     * 用户注册
     *
     * @param input
     * @return
     */
    @Override
    public Integer register(RegInput input) {
        Integer userId = null;
        Integer bindType = input.getBindType();

        // 账号，手机，邮箱注册方式走校验
        String userAccount = input.getUserAccount();

        switch (bindType.intValue()) {
            case BindConnectCode.MOBILE: {
                if (!PhoneNumberUtils.isValidNumber(userAccount)) {
                    throw new BusinessException(__("请输入正确的手机号！"));
                }

                PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(userAccount);

                input.setUserMobile(phoneModelWithCountry.getNationalNumber());
                input.setUserIntl(String.format("+%d", phoneModelWithCountry.getCountryCode()));

                // 判断connect绑定操作
                UserBindConnect bindConnect = userBindConnectRepository.get(userAccount);
                if (bindConnect != null && bindConnect.getBindActive()) {
                    throw new BusinessException(__("手机号已经绑定过，不可以使用此手机号注册"));
                }

                break;
            }
            case BindConnectCode.EMAIL: {
                input.setUserEmail(userAccount);

                // 判断connect绑定操作
                UserBindConnect bindConnect = userBindConnectRepository.get(userAccount);
                if (bindConnect != null && bindConnect.getBindActive()) {
                    throw new BusinessException(__("Email已经绑定过，不可以使用此Email注册"));
                }

                break;
            }
            case BindConnectCode.ACCOUNT: {
                //userAccount 不可包含特殊字符 +
                if (userAccount.contains("+")) {
                    throw new BusinessException(__("用户账号不可以包含特殊字符串！"));
                }

                break;
            }
            case BindConnectCode.WEIXIN:
            case BindConnectCode.WEIXIN_XCX: {

                break;
            }
        }

        UserBase dbUserBase = userBaseRepository.getByUserAccount(userAccount);

        if (dbUserBase != null) {
            throw new BusinessException(__("用户已经存在,请更换用户名"));
        }

        Date now = new Date();

        //基础表
        String userSalt = IdUtil.simpleUUID();

        UserBase userBase = new UserBase();
        userBase.setUserAccount(userAccount);
        userBase.setUserPassword(encodePassword(input.getPassword(), userSalt));
        userBase.setUserSalt(userSalt);


        if (!userBaseRepository.save(userBase)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        //
        UserLogin userLogin = new UserLogin();

        userId = userBase.getUserId();
        String ip = HttpServletUtils.getClientIpAddr();

        userLogin.setUserId(userId);
        userLogin.setUserRegDate(DateUtil.parse(DateUtil.today()));
        userLogin.setUserRegTime(now.getTime());
        userLogin.setUserRegIp(ip);
        userLogin.setUserLastloginTime(now.getTime());
        userLogin.setUserLastloginIp(ip);

        if (!userLoginRepository.save(userLogin)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        //
        if (CheckUtil.isEmpty(input.getUserNickName())) {
            input.setUserNickName(input.getUserAccount());
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserAccount(input.getUserAccount());
        userInfo.setUserNickname(input.getUserNickName());

        userInfo.setUserMobile(Convert.toStr(input.getUserMobile()));
        userInfo.setUserIntl(input.getUserIntl());
        userInfo.setUserGender(0);
        userInfo.setUserEmail(input.getUserEmail());
        userInfo.setUserAvatar(input.getUserAvatar());
        userInfo.setUserBirthday(DateUtil.parse("1971-01-01"));
        userInfo.setUserLevelId(1001);

        if (!userInfoRepository.save(userInfo)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        // 是否为手机号注册
        if (bindType.equals(BindConnectCode.MOBILE)) {
            String bindId = input.getUserAccount();

            // connect绑定操作
            UserBindConnect bindConnect = new UserBindConnect();
            bindConnect.setBindOpenid(input.getUserAccount());
            bindConnect.setBindActive(true);

            if (!checkBind(bindId, BindConnectCode.MOBILE, userId, bindConnect)) {
                throw new BusinessException(ResultCode.FAILED);
            }
        }

        // 是否为邮箱注册
        if (bindType.equals(BindConnectCode.EMAIL)) {
            // connect绑定操作
            UserBindConnect bindConnect = new UserBindConnect();
            bindConnect.setBindOpenid(input.getUserAccount());
            bindConnect.setBindActive(true);

            String bindId = input.getUserAccount();

            if (!checkBind(bindId, BindConnectCode.EMAIL, userId, bindConnect)) {
                throw new BusinessException(ResultCode.FAILED);
            }
        }

        // User_Resource初始化
        if (!userResourceService.initUserPoints(userId)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        // 初始化用户经验表
        if (!userResourceService.initUserExperience(userId)) {
            throw new BusinessException(ResultCode.FAILED);
        }

        // 判断传递的活动id

        Integer sourceUserId = getSourceUserId();
        if (CheckUtil.isNotEmpty(sourceUserId)) {
            input.setUserParentId(sourceUserId);
        }

        // 分销用户来源 - 平台推广员功能，佣金平台出
        // 修改用户上级关系
        Integer userParentId = input.getUserParentId();

        //记录渠道来源
        String sourceUccCode = getSourceUccCode();
        if (CheckUtil.isNotEmpty(sourceUccCode)) {
            addChannelSourceUserId(userId, sourceUccCode);
        }

        //初次注册消息
        String messageId = "registration-of-welcome-information";
        HashMap<String, Object> args = new HashMap<>();
        args.put("user_account", input.getUserNickName());
        args.put("register_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        messageService.sendNoticeMsg(userId, messageId, args);

        return userId;
    }


    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public LogoutRes logout() {
        return null;
    }

    /**
     * 手机快捷登录
     *
     * @param input
     * @return
     */
    @Override
    @Transactional
    public LoginRes doSmsLogin(RegInput input) {
        Integer userId = 0;
        String bindId = String.format("%s%d", input.getUserIntl(), input.getUserMobile());

        //判断是否绑定
        UserBindConnect userBindConnect = userBindConnectRepository.get(bindId);

        if (ObjectUtil.isNotEmpty(userBindConnect)) {
            userId = userBindConnect.getUserId();
        } else {
            //doUserBind
            userBindConnect = new UserBindConnect();
            userBindConnect.setBindId(bindId);
            userBindConnect.setBindNickname(input.getUserMobile().toString());
            userBindConnect.setBindType(input.getBindType());

            userId = doUserBind(userBindConnect, input.getActivityId(), true);
        }

        return login(userId);
    }


    @Override
    public String encodePassword(String password, String userSalt) {
        return SecureUtil.md5(password + userSalt);
    }


    /**
     * 修改密码
     *
     * @param userId   用户编号
     * @param password 用户密码
     * @return
     */
    @Override
    public boolean doResetPasswd(Integer userId, String password) {

        // 重置密码
        String userSalt = IdUtil.simpleUUID();
        String encodePassword = encodePassword(password, userSalt);

        UserBase userBase = new UserBase();
        userBase.setUserId(userId);
        userBase.setUserPassword(encodePassword);
        userBase.setUserSalt(userSalt);

        if (!(userBaseRepository.save(userBase))) {
            throw new BusinessException(ResultCode.FAILED);
        }

        return true;
    }

    /**
     * 根据编号查询用户
     *
     * @param userId 账号
     * @return UserBase
     */
    @Override
    public UserBase getByUserId(Integer userId) {
        UserBase user = userBaseRepository.get(userId);

        if (ObjectUtil.isEmpty(user)) {
            throw new BusinessException(TOKEN_EXPIRED_CODE, __("登录已过期"));
        }

        UserInfo userInfo = userInfoRepository.get(userId);

        if (!ObjectUtil.isEmpty(userInfo)) {
            user.setUserNickname(userInfo.getUserNickname());
        }

        UserAdmin userAdmin = userAdminRepository.get(userId);
        user.setRoles(new ArrayList<>());
        user.setAuthorities(new ArrayList<>());

        if (ObjectUtil.isNotEmpty(userAdmin)) {
            //角色编号:0-用户;2-商家;3-门店;9-平台;
            user.setRoleId(ConstantRole.ROLE_ADMIN);

            UserRole userRole = userRoleRepository.get(userAdmin.getUserRoleId());

            if (ObjectUtil.isNotEmpty(userRole)) {
                user.setRoles(new ArrayList<UserRole>() {{
                    add(userRole);
                }});

                user.setAuthorities(menuBaseRepository.gets(Convert.toList(Integer.class, userRole.getMenuIds())));
            }
        }

        user.setSiteId(0);
        user.setStoreId(0);
        user.setChainId(0);

        return user;
    }

    /**
     * 根据编号查询用户
     *
     * @param userId 账号
     * @return UserBase
     */
    @Override
    public UserInfoOutput getInfoByUserId(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            throw new BusinessException(TOKEN_EXPIRED_CODE, __("Token失效，请重新登录"));
        }

        // 获取用户基本信息
        UserBase userBase = getByUserId(userId);

        UserInfoOutput user = BeanUtil.copyProperties(userBase, UserInfoOutput.class);
        user.setRoles(new ArrayList<>());
        user.setPermissions(new ArrayList<>());

        for (UserRole userRole : userBase.getRoles()) {
            if (!user.getRoles().contains(userRole.getUserRoleName())) {
                user.getRoles().add(userRole.getUserRoleName());
            }
        }

        for (MenuBase baseMenu : userBase.getAuthorities()) {
            if (!user.getPermissions().contains(baseMenu.getAuthority())) {
                user.getPermissions().add(baseMenu.getAuthority());
            }
        }

        //用户信息
        UserInfo userInfo = userInfoRepository.get(userId);
        BeanUtil.copyProperties(userInfo, user);

        if (userInfo != null) {
            String userIdcardImages = userInfo.getUserIdcardImages();

            if (StrUtil.isNotEmpty(userIdcardImages)) {
                user.setUserIdcardImageList(Convert.toList(String.class, userIdcardImages));
            }
        }


        // 判断当前用户状态
        if (userInfo.getUserState().intValue() == 0) {
            throw new BusinessException("您的账号已被禁用,请联系管理员");
        }

        //用户资源
        UserResource userResource = userResourceRepository.get(userId);
        BeanUtil.copyProperties(userResource, user);

        // 优惠券 未使用优惠券
        QueryWrapper<UserVoucher> unusedQuery = new QueryWrapper<>();
        unusedQuery.eq("user_id", userId)
                .eq("voucher_state_id", StateCode.VOUCHER_STATE_UNUSED);

        long count = userVoucherRepository.count(unusedQuery);
        user.setVoucher(count);

        // 代付款数量
        OrderNumInput orderNumInput = new OrderNumInput();
        orderNumInput.setUserId(userId);
        orderNumInput.setOrderStateId(StateCode.ORDER_STATE_WAIT_PAY);
        Long orderNum = orderService.getOrderNum(orderNumInput);
        user.setWaitPayNum(orderNum);

        // 收藏数量
        QueryWrapper<UserFavoritesItem> favoritesItemQueryWrapper = new QueryWrapper<>();
        favoritesItemQueryWrapper.eq("user_id", userId);
        long favoritesNum = userFavoritesItemRepository.count(favoritesItemQueryWrapper);
        user.setFavoritesGoodsNum(favoritesNum);

        // 关注数量
        UserMessageListReq userMessageListReq = new UserMessageListReq();
        userMessageListReq.setUserId(userId);
        UserMessageRes messageRes = userMessageService.getMessageNum(userMessageListReq);
        Integer unreadNumber = messageRes.getUnreadNumber();
        user.setUnreadNumber(unreadNumber);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userBaseRepository.getByUserAccount(username);
    }

    @Override
    public LoginPolicyRes protocol(String documentType, Integer state, String protocolsKey) {
        LoginPolicyRes loginPolicyRes = new LoginPolicyRes();

        if ("store".equals(documentType)) {
            ConfigBase storeDescription = configBaseService.get("open_store_description");
            loginPolicyRes.setDocument(storeDescription != null ? storeDescription.getConfigValue() : "");
        } else {
            ConfigBase configBase = configBaseService.get(protocolsKey);
            loginPolicyRes.setDocument(configBase != null ? configBase.getConfigValue() : "");
            // 签订状态
        }

        return loginPolicyRes;
    }

    @Override
    public boolean edit(UserInfo userInfo) {

        if (StrUtil.isNotEmpty(userInfo.getUserNickname())) {
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.eq("user_nickname", userInfo.getUserNickname());
            UserInfo oneUser = userInfoRepository.findOne(userInfoQueryWrapper);

            if (oneUser != null && !userInfo.getUserId().equals(oneUser.getUserId())) {
                throw new BusinessException(__("昵称已经存在，请更换！"));
            }
        }

        return userInfoRepository.edit(userInfo);
    }


    @Override
    @Transactional
    public LoginRes bindMobile(ContextUser user, String user_intl, Long mobile, String new_password) {
        String bind_id = String.format("%s%d", user_intl, mobile);

        if (!PhoneNumberUtils.isValidNumber(bind_id)) {
            throw new BusinessException(__("请输入正确的手机号！"));
        }

        //新用户编号，可能和老用户不一致的... 微信新账户，绑定手机号，手机绑定作为唯一账户有老手机号存在，绑定到老用户上
        Integer user_id_new = doBindMobile(user, user_intl, mobile);
        if (user_id_new == null) {
            throw new BusinessException(__("绑定用户失败"));
        }

        UserBase userBase = userBaseRepository.get(user_id_new);

        // 对账号进行密码修改
        if (StrUtil.isNotBlank(new_password)) {
            String user_account = userBase.getUserAccount();


//            if (!doResetPasswd(user_account, new_password, null)) {
//                throw new BusinessException(ResultCode.FAILED);
//            }
        }

        //登录
        LoginRes login = login(userBase);

        return login;
    }

    @Override
    public boolean unBindMobile(ContextUser user, String user_intl, Long mobile) {
        String bind_id = String.format("%s%d", user_intl, mobile);

        if (!PhoneNumberUtils.isValidNumber(bind_id)) {
            throw new BusinessException(__("请输入正确的手机号！"));
        }

        userBindConnectRepository.remove(bind_id);

        // 如果bind表中存在记录，判断info表中是否存在
        UserInfo userInfo = userInfoRepository.get(user.getUserId());
        userInfo.setUserIntl("");
        userInfo.setUserMobile("");

        boolean edit = userInfoRepository.edit(userInfo);

        return edit;
    }

    @Override
    @Transactional
    public LoginRes reBindMobile(ContextUser user, String user_intl, Long mobile, String new_password) {
        //删除旧手机绑定
        unBindMobile(user, user_intl, mobile);
        LoginRes loginRes = bindMobile(user, user_intl, mobile, "");

        return loginRes;
    }

    @Override
    public Integer doBindMobile(ContextUser currentUser, String user_intl, Long mobile) {
        String bind_id = String.format("%s%d", user_intl, mobile);

        Integer user_id = null;

        // 判断是否已经绑定
        QueryWrapper<UserBindConnect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bind_id", bind_id).eq("bind_type", BindConnectCode.MOBILE);
        UserBindConnect userBindConnect = userBindConnectRepository.findOne(queryWrapper);

        //取得当前手机是否绑定账号
        UserBase oldUserDto = userBaseRepository.getByUserAccount(bind_id);

        //根据手机号查询bind表中是否已存在记录
        if (userBindConnect != null) {
            //同一个用户,不做操作
            if (userBindConnect.getUserId().equals(currentUser.getUserId())) {
                // 如果bind表中存在记录，判断info表中是否存在
                UserInfo userInfo = userInfoRepository.get(currentUser.getUserId());

                if (StrUtil.isBlank(userInfo.getUserIntl()) || StrUtil.isBlank(userInfo.getUserMobile())) {
                    userInfo.setUserIntl(user_intl);
                    userInfo.setUserMobile(mobile.toString());
                    userInfoRepository.edit(userInfo);
                }

                user_id = currentUser.getUserId();
            } else {
                throw new BusinessException(__("手机号已经绑定用户！"));

                /*
                //修改当前登录用户的userid为已存在手机号记录的userid
                UserBindConnect bind_data = new UserBindConnect();
                bind_data.setBindId(currentUser.getUserAccount()); //微信绑定 账号 == bind_id,
                bind_data.setUserId(userBindConnect.getUserId());
                userBindConnectRepository.edit(bind_data);

                //修改记录userinfo中的手机号码
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(userBindConnect.getUserId());
                userInfo.setUserMobile(mobile.toString());
                userInfoRepository.edit(userInfo);

                user_id = userBindConnect.getUserId();

                 */
            }

        } else {
            //若不存在则新增一个绑定信息
            UserBindConnect accountUserBindConnectNew = new UserBindConnect();
            accountUserBindConnectNew.setBindId(bind_id);
            accountUserBindConnectNew.setBindType(BindConnectCode.MOBILE);
            accountUserBindConnectNew.setUserId(currentUser.getUserId());
            accountUserBindConnectNew.setBindActive(true);

            user_id = doUserBind(accountUserBindConnectNew, 0, false);

            // 如果bind表中存在记录，判断info表中是否存在
            UserInfo userInfo = userInfoRepository.get(currentUser.getUserId());
            if (StrUtil.isBlank(userInfo.getUserIntl()) || StrUtil.isBlank(userInfo.getUserMobile())) {
                userInfo.setUserIntl(user_intl);
                userInfo.setUserMobile(mobile.toString());
                userInfoRepository.edit(userInfo);
            }

            if (CheckUtil.isEmpty(user_id)) {
                throw new BusinessException(__("保存用户绑定失败！"));
            }
        }

        return user_id;
    }


    @Override
    public Integer doUserBind(UserBindConnect user_info_row, Integer activity_id, boolean reg_flag) {
        Integer user_id = null;

        String bind_id = user_info_row.getBindId();
        UserBase userBase = userBaseRepository.getByUserAccount(bind_id);

        //unionId 获取，如果存在
        if (user_info_row.getBindUnionid() != null) {
            //检测unionId, 判断用户
            if (userBase == null) {
                //根据unionId，判断已经绑定的用户
                QueryWrapper<UserBindConnect> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("bind_unionid", user_info_row.getBindUnionid());
                UserBindConnect find_bind_row = userBindConnectRepository.findOne(queryWrapper);
                if (find_bind_row != null) {
                    userBase = userBaseRepository.get(find_bind_row.getUserId());

                    //运行到此处
                    //判断bind openid是否存在，不存在则需要添加
                    //支付地方需要调用到openid， 此处必须冗余存放openid
                    UserBindConnect open_bind_row = userBindConnectRepository.get(bind_id);

                    if (null == open_bind_row) {
                        user_info_row.setBindActive(true);
                        user_info_row.setBindType(user_info_row.getBindType());
                        userBindConnectRepository.save(user_info_row);
                    }
                    UserBase UserBase = userBaseRepository.get(find_bind_row.getUserId());
                    if (UserBase != null) {
                        user_id = UserBase.getUserId();
                        UserBindConnect userBindConnect = new UserBindConnect();
                        userBindConnect.setBindId(bind_id);
                        userBindConnect.setUserId(user_id);
                        userBindConnectRepository.edit(userBindConnect);
                        return user_id;
                    }
                }
            }
        } else {
            //throw  new Exception(__('unionId 不存在'));
        }

        //自动注册用户
        if (userBase != null) {
            user_id = userBase.getUserId();
        } else {
            boolean flag = false;

            //需要注册用户
            if (reg_flag) {
                RegInput regInput = new RegInput();
                regInput.setUserAccount(bind_id);
                regInput.setPassword("1231Ss@123" + UUID.randomUUID());
                regInput.setEncrypt(false);
                regInput.setUserNickName(user_info_row.getBindNickname());
                if (CheckUtil.isNotEmpty(activity_id)) regInput.setActivityId(activity_id);

                regInput.setBindType(BindConnectCode.ACCOUNT);

                Integer userId = register(regInput);

                if (CheckUtil.isEmpty(userId)) {
                    throw new BusinessException(__("注册失败！"));
                }

                userBase = userBaseRepository.get(userId);

                if (userBase != null) {
                    user_id = userBase.getUserId();

                    //添加user info
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserId(user_id);
                    userInfo.setUserAccount(user_info_row.getBindId());
                    userInfo.setUserNickname(user_info_row.getBindNickname());
                    userInfo.setUserAvatar(user_info_row.getBindIcon());

                    flag = userInfoRepository.edit(userInfo);
                    flag = checkBind(bind_id, user_info_row.getBindType(), user_id, user_info_row);
                }
            } else {
                //读取用户信息
                user_id = user_info_row.getUserId();
                flag = checkBind(bind_id, user_info_row.getBindType(), user_id, user_info_row);
            }

        }

        return user_id;
    }


    @Override
    public boolean checkBind(String bind_id, int bind_type, Integer user_id, UserBindConnect user_info_row) {
        UserBindConnect bind_row = userBindConnectRepository.get(bind_id);

        if (bind_row != null && CheckUtil.isNotEmpty(bind_row.getUserId())) {
            // 验证通过, 登录成功.
            Integer bind_user_id = bind_row.getUserId();
            if (CheckUtil.isNotEmpty(user_id) && ObjectUtil.equal(user_id, bind_user_id)) {
                throw new BusinessException(__("非法请求,已经登录用户不应该访问到此页面-重复绑定"));
            } else if (CheckUtil.isEmpty(user_id) && ObjectUtil.equal(user_id, bind_user_id)) {
                throw new BusinessException(__("非法请求,错误绑定数据"));
            }
        } else if (bind_row != null && CheckUtil.isEmpty(bind_row.getUserId())) {

            user_info_row.setUserId(user_id);
            user_info_row.setBindId(bind_id);

            if (!userBindConnectRepository.save(user_info_row)) {
                throw new BusinessException("ResultCode.FAILED");
            }
        } else if (bind_row == null) {

            // todo 头像会变动, 需要本地缓存, 生成新网址.
            user_info_row.setBindId(bind_id);
            user_info_row.setBindType(bind_type);
            user_info_row.setUserId(user_id);
            user_info_row.setBindActive(true);

            if (!userBindConnectRepository.save(user_info_row)) {
                throw new BusinessException("ResultCode.FAILED");
            }
        }

        return true;
    }

    @Transactional
    @Override
    public boolean checkAccessToken(Integer bind_type, UserBindConnect bind_data, Integer user_id) {

        String bind_id = bind_data.getBindId();

        // todo 验证验证码
        if (true) {
            bind_data.setBindActive(true);
            bind_data.setUserId(user_id);
            bind_data.setBindType(bind_type);

            // 判断是否已经绑定
            QueryWrapper<UserBindConnect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("bind_id", bind_id).eq("bind_type", bind_type);
            UserBindConnect bind_row = userBindConnectRepository.findOne(queryWrapper);

            if (bind_row != null) {
                Boolean bind_active = bind_row.getBindActive();

                if (bind_active) {
                    Integer bind_user_id = bind_row.getUserId();
                    if (ObjectUtil.equal(bind_user_id, user_id)) {
                        return true;
                    } else {
                        throw new BusinessException(__("其它用户已绑定！"));
                    }
                } else {
                    if (!userBindConnectRepository.edit(bind_data)) {
                        throw new BusinessException(__("绑定修改失败！"));
                    }
                }
            }

            UserInfo userInfo = new UserInfo();

            switch (bind_type) {
                case BindConnectCode.MOBILE:
                    PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(bind_id);
                    String user_intl = String.format("+%d", phoneModelWithCountry.getCountryCode());

                    userInfo.setUserMobile(Convert.toStr(phoneModelWithCountry.getNationalNumber()));
                    userInfo.setUserIntl(user_intl);

                    bind_data.setBindOpenid(bind_id);
                    break;
                case BindConnectCode.EMAIL:
                    userInfo.setUserEmail(bind_id);
                    bind_data.setBindOpenid(bind_id);
                    break;
            }

            userInfo.setUserId(user_id);

            if (!userInfoRepository.edit(userInfo)) {
                throw new BusinessException(__("保存用户数据失败！"));
            }

            if (!userBindConnectRepository.save(bind_data)) {
                throw new BusinessException(__("保存用户绑定失败！"));
            }
        } else {
            throw new BusinessException(__("验证码错误！"));
        }

        return true;
    }


    /**
     * 获取有效绑定
     *
     * @param user_id
     * @param bind_type
     * @return
     */
    @Override
    public UserBindConnect getBind(Integer user_id, int bind_type) {

        QueryWrapper<UserBindConnect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id)
                .eq("bind_active", 1)
                .eq("bind_type", bind_type);
        UserBindConnect bindConnect = userBindConnectRepository.findOne(queryWrapper);

        return bindConnect;
    }

    @Transactional
    @Override
    public boolean saveCertificate(UserInfo userInfo) {
        UserInfo info = userInfoRepository.get(userInfo.getUserId());

        if (info == null) {
            throw new BusinessException(__("用户信息不存在！"));
        }
        Integer userIsAuthentication = info.getUserIsAuthentication();
        if (!userIsAuthentication.equals(StateCode.USER_CERTIFICATION_VERIFY)) {

            if (!userInfoRepository.edit(userInfo)) {
                throw new BusinessException(__("保存用户数据失败！"));
            }
        } else {
            throw new BusinessException(__("已提交，请勿重复提交！"));
        }

        return true;
    }

    @Override
    public IPage<UserLevel> listBaseUserLevel() {

        return userLevelRepository.lists(new QueryWrapper<UserLevel>(), 1, 500);
    }

    @Override
    public ExpRuleRes listsExpRule() {
        ExpRuleRes expRuleRes = new ExpRuleRes();
        expRuleRes.setExp_reg(configBaseService.getConfig("exp_reg"));
        expRuleRes.setExp_login(configBaseService.getConfig("exp_login"));
        expRuleRes.setExp_consume_max(configBaseService.getConfig("exp_consume_max"));
        expRuleRes.setExp_consume_rate(configBaseService.getConfig("exp_consume_rate"));
        expRuleRes.setExp_evaluate_good(configBaseService.getConfig("exp_evaluate_good"));

        return expRuleRes;
    }

    /**
     * 用户来源
     *
     * @return
     */
    @Override
    public Integer getSourceUserId() {
        Integer source_user_id = Convert.toInt(getParameter("source_user_id"));

        if (CheckUtil.isEmpty(source_user_id)) {
            source_user_id = Convert.toInt(getParameter("user_parent_id"));
        }

        return source_user_id;
    }

    @Override
    public String getSourceUccCode() {
        String source_ucc_code = getParameter("source_ucc_code");

        return source_ucc_code;
    }

    @Override
    public boolean addChannelSourceUserId(Integer user_id, String source_ucc_code) {
        boolean flag = false;

        if (CheckUtil.isEmpty(source_ucc_code)) {
            source_ucc_code = getSourceUccCode();
        }

        /*

        if (CheckUtil.isNotEmpty(source_ucc_code)) {
            //读取 ucc_id source_ucc_code
            QueryWrapper<UserChannelCode> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("ucc_code", source_ucc_code);
            UserChannelCode channel_row = UserChannelCodeService.findOne(objectQueryWrapper);

            if (channel_row != null && channel_row.getUcc_enable().equals(1)) {
                //只可以使用一次
                if (channel_row.getUcc_is_unique().equals(1) && (channel_row.getUcc_use_num().intValue() >= 1)) {
                    //throw  new Exception(__("邀请码失效"));
                    flag = false;
                } else {
                    channel_row.setUcc_use_num(channel_row.getUcc_use_num() + 1);
                    channel_row.setUser_id(user_id);
                    channel_row.setUcc_used(1);
                    channel_row.setUcc_use_time(new Date().getTime());
                    flag = UserChannelCodeService.saveOrUpdate(channel_row);

                    //更新用户渠道来源
                    if (flag) {
                        AccountUserInfo userInfo = accountUserInfoService.get(user_id);
                        if (userInfo != null) {
                            userInfo.setUcc_id(channel_row.getUcc_id());
                            accountUserInfoService.saveOrUpdate(userInfo);
                        }
                    }
                }
            } else {
                long time = new Date().getTime();
                //提示邀请码失效
                //throw  new Exception(__("邀请码失效"));
                channel_row = new UserChannelCode();
                channel_row.setUcc_code(source_ucc_code);
                channel_row.setUcc_enable(1);
                channel_row.setUser_id(user_id);
                channel_row.setUser_channel_id(1001);
                channel_row.setUcc_used(1);
                channel_row.setUcc_create_time(time);
                channel_row.setUcc_use_time(time);
                channel_row.setUcc_is_unique(0);
                channel_row.setUcc_use_num(1);

                flag = UserChannelCodeService.saveOrUpdate(channel_row);
            }
        }


         */
        return flag;
    }

    /**
     * 根据key 从 request 中获取值
     *
     * @param key
     * @return
     */
    private String getParameter(String key) {
        String val = null;

        try {
            val = StrUtil.isBlank(key) ? null : servletRequest.getParameter(key);
        } catch (Exception e) {
            logger.error("获取request参数异常：" + e.getMessage(), e);
        }

        return val;
    }
}
