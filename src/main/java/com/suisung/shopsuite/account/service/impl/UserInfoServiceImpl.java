package com.suisung.shopsuite.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.excel.UserInfoTemp;
import com.suisung.shopsuite.account.excel.UserInfoTempListener;
import com.suisung.shopsuite.account.model.entity.*;
import com.suisung.shopsuite.account.model.output.UserInfoOutput;
import com.suisung.shopsuite.account.model.req.UserInfoListReq;
import com.suisung.shopsuite.account.model.vo.UserInfoVo;
import com.suisung.shopsuite.account.repository.*;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.account.service.UserInfoService;
import com.suisung.shopsuite.admin.model.entity.UserAdmin;
import com.suisung.shopsuite.admin.repository.UserAdminRepository;
import com.suisung.shopsuite.analytics.dao.AnalyticsOrderDao;
import com.suisung.shopsuite.analytics.dao.AnalytiscTradeDao;
import com.suisung.shopsuite.analytics.model.vo.OrderNumVo;
import com.suisung.shopsuite.analytics.model.vo.TradeAmountVo;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.excel.EasyExcelUtil;
import com.suisung.shopsuite.common.excel.ExcelUtil;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.*;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.vo.ActivityRuleVo;
import com.suisung.shopsuite.marketing.model.vo.VoucherVo;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.repository.UserResourceRepository;
import com.suisung.shopsuite.shop.service.UserVoucherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 用户详细信息表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoRepository, UserInfo, UserInfoListReq> implements UserInfoService {

    @Autowired
    private UserBaseRepository userBaseRepository;

    @Autowired
    private UserDeliveryAddressRepository addressRepository;

    @Autowired
    private UserLevelRepository levelRepository;

    @Autowired
    private UserResourceRepository userResourceRepository;

    @Autowired
    private UserLoginRepository loginRepository;

    @Autowired
    private UserTagBaseRepository tagBaseRepository;

    @Autowired
    private UserTagGroupRepository tagGroupRepository;

    @Autowired
    private UserLoginHistoryRepository loginHistoryRepository;

    @Autowired
    private AnalyticsOrderDao analyticsOrderDao;

    @Autowired
    private AnalytiscTradeDao analytiscTradeDao;

    @Autowired
    private UserBindConnectRepository userBindConnectRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserAdminRepository userAdminRepository;

    @Autowired
    private UserVoucherService userVoucherService;

    @Autowired
    private ActivityBaseService activityBaseService;

    @Autowired
    private LoginService loginService;


    @Override
    public Page<UserInfo> getList(QueryWrapper<UserInfo> queryWrapper, Integer page, Integer size) {

        return lists(queryWrapper, page, size);
    }

    @Override
    public UserInfoOutput getUserData(Integer userId) {
        UserInfoOutput userInfoOutput = new UserInfoOutput();
        //用户基本信息
        UserBase userBase = userBaseRepository.get(userId);

        if (userBase != null) {
            BeanUtils.copyProperties(userBase, userInfoOutput);
        }
        //用户详情信息
        UserInfo userInfo = get(userId);

        if (userInfo != null) {
            BeanUtils.copyProperties(userInfo, userInfoOutput);
            //身份证图片
            if (StrUtil.isNotEmpty(userInfo.getUserIdcardImages())) {
                userInfoOutput.setUserIdcardImageList(Convert.toList(String.class, userInfo.getUserIdcardImages()));
            }

            //用户等级
            UserLevel userLevel = levelRepository.get(userInfo.getUserLevelId());

            if (userLevel != null) {
                userInfoOutput.setUserLevelName(userLevel.getUserLevelName());
            }
            //用户标签、分组
            if (StrUtil.isNotEmpty(userInfo.getTagIds())) {
                List<Integer> tagIds = Convert.toList(Integer.class, userInfo.getTagIds());
                List<UserTagBase> userTagBases = tagBaseRepository.gets(tagIds);

                if (CollectionUtil.isNotEmpty(userTagBases)) {
                    List<String> tagNames = userTagBases.stream().map(UserTagBase::getTagTitle).collect(Collectors.toList());
                    //用户标签
                    userInfoOutput.setTagTitleList(tagNames);
                    userInfoOutput.setTagTitles(String.join("、", tagNames));
                    //用户分组
                    List<Integer> tagGroupIds = userTagBases.stream().map(UserTagBase::getTagGroupId).distinct().collect(Collectors.toList());
                    List<UserTagGroup> tagGroupGroups = tagGroupRepository.gets(tagGroupIds);

                    if (CollectionUtil.isNotEmpty(tagGroupGroups)) {
                        List<String> groupNames = tagGroupGroups.stream().map(UserTagGroup::getTagGroupName).collect(Collectors.toList());
                        userInfoOutput.setTagGroupNames(String.join("、", groupNames));
                    }
                }
            }
        }
        //统计没有取消的订单
        List<Integer> orderState = Arrays.asList(StateCode.ORDER_STATE_WAIT_PAY, StateCode.ORDER_STATE_WAIT_PAID, StateCode.ORDER_STATE_WAIT_REVIEW, StateCode.ORDER_STATE_WAIT_FINANCE_REVIEW, StateCode.ORDER_STATE_PICKING, StateCode.ORDER_STATE_WAIT_SHIPPING, StateCode.ORDER_STATE_SHIPPED, StateCode.ORDER_STATE_RECEIVED, StateCode.ORDER_STATE_FINISH, StateCode.ORDER_STATE_SELF_PICKUP);
        TimeRange month = TimeUtil.month();
        //本月订单
        OrderNumVo orderNum = analyticsOrderDao.getOrderNum(month.getStart(), month.getEnd(), orderState, null, userId, null);

        if (orderNum != null) {
            userInfoOutput.setMonthOrder(orderNum.getOrderNum());
        }

        //总计订单
        OrderNumVo totalNum = analyticsOrderDao.getOrderNum(null, null, orderState, null, userId, null);

        if (totalNum != null) {
            userInfoOutput.setTotalOrder(totalNum.getOrderNum());
        }

        //本月消费金额
        TradeAmountVo tradeAmount = analytiscTradeDao.getTradeAmount(month.getStart(), month.getEnd(), userId);

        if (tradeAmount != null) {
            userInfoOutput.setMonthTrade(tradeAmount.getAmount());
        }
        //总消费金额
        TradeAmountVo totalAmount = analytiscTradeDao.getTradeAmount(null, null, userId);

        if (totalAmount != null) {
            userInfoOutput.setTotalTrade(totalAmount.getAmount());
        }

        //用户地址
        QueryWrapper<UserDeliveryAddress> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.eq("user_id", userId);
        UserDeliveryAddress address = addressRepository.findOne(addressQueryWrapper);

        if (address != null) {
            userInfoOutput.setUdAddress(address.getUdProvince() + address.getUdCity() + address.getUdCounty() + address.getUdAddress());
        }

        //用户资源
        UserResource userResource = userResourceRepository.get(userId);

        if (userResource != null) {
            BeanUtils.copyProperties(userResource, userInfoOutput);
        }
        //用户登录信息
        UserLogin userLogin = loginRepository.get(userId);

        if (userLogin != null) {
            userInfoOutput.setUserRegTime(userLogin.getUserRegTime());
        }

        //登录时间
        QueryWrapper<UserLoginHistory> loginHistoryQueryWrapper = new QueryWrapper<>();
        loginHistoryQueryWrapper.eq("user_id", userId);
        loginHistoryQueryWrapper.orderByDesc("user_login_time");
        IPage<UserLoginHistory> loginHistoryPage = loginHistoryRepository.lists(loginHistoryQueryWrapper, 1, 1);
        if (loginHistoryPage != null && CollectionUtil.isNotEmpty(loginHistoryPage.getRecords())) {
            List<UserLoginHistory> loginHistories = loginHistoryPage.getRecords();
            userInfoOutput.setUserLoginTime(loginHistories.get(0).getUserLoginTime());
        }

        return userInfoOutput;
    }

    /**
     * 修改用户
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean editUser(UserInfo userInfo) {

        return edit(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean passWordEdit(Integer userId, String userPassword) {
        if (CheckUtil.isEmpty(userId)) {
            throw new BusinessException(__("用户Id为空"));
        }

        if (CheckUtil.isEmpty(userPassword)) {
            throw new BusinessException(__("密码为空"));
        }

        //修改密码
        String userSalt = IdUtil.simpleUUID();
        String resetPassWord = SecureUtil.md5(userPassword + userSalt);
        UserBase userBase = new UserBase();
        userBase.setUserId(userId);
        userBase.setUserSalt(userSalt);
        userBase.setUserPassword(resetPassWord);

        return userBaseRepository.edit(userBase);
    }

    @Override
    public boolean addTags(String userIds, String tagIds) {
        if (CheckUtil.isEmpty(userIds)) {
            throw new BusinessException(__("用户编号为空"));
        }
        List<Integer> userIdList = Convert.toList(Integer.class, userIds);
        List<UserInfo> userInfos = new ArrayList<>();
        for (Integer userId : userIdList) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setTagIds(tagIds);
            userInfos.add(userInfo);
        }

        return edit(userInfos);
    }

    /**
     * 删除用户账号
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public boolean removeUser(Integer userId) {
        UserAdmin userAdmin = userAdminRepository.get(userId);

        if (ObjectUtil.isNotEmpty(userAdmin) && userAdmin.getUserIsSuperadmin()) {
            throw new BusinessException(__("该账号为系统管理员，不可删除！"));
        }

        remove(userId);
        userBaseRepository.remove(userId);
        userLoginRepository.remove(userId);
        userResourceRepository.remove(userId);

        QueryWrapper<UserBindConnect> connectQueryWrapper = new QueryWrapper<>();
        connectQueryWrapper.eq("user_id", userId);
        List<Serializable> bindKey = userBindConnectRepository.findKey(connectQueryWrapper);
        userBindConnectRepository.remove(bindKey);

        return true;
    }

    @Override
    public void exportTemp(HttpServletResponse response) {
        //编码问题
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(__("" +
                    "" +
                    "") + "-" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(UserInfoTemp.class, 0, "导出用户信息模版");
            excelWriter.write(new ArrayList<UserInfoTemp>(), writeSheet);
            excelWriter.finish();
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(__("导出Excel编码异常"));
        } catch (IOException e) {
            throw new BusinessException(__("导出Excel文件异常"));
        }

    }

    @Override
    public void exportFile(HttpServletResponse response, List<Integer> userIds) {
        List<UserInfo> userInfoList = gets(userIds);

        if (CollectionUtil.isEmpty(userInfoList)) {
            throw new BusinessException(__("选中数据为空"));
        }
        List<UserInfoVo> userInfoVos = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(userInfo, userInfoVo);

            //状态
            if (userInfo.getUserState() != null) {
                Integer userState = userInfo.getUserState();
                if (userState.equals(StateCode.USER_STATE_LOCKING)) {
                    userInfoVo.setUserState(__("锁定"));
                } else if (userState.equals(StateCode.USER_STATE_NOTACTIVE)) {
                    userInfoVo.setUserState(__("未激活"));
                } else if (userState.equals(StateCode.USER_STATE_ACTIVATION)) {
                    userInfoVo.setUserState(__("已激活"));
                }
            }

            //性别
            if (userInfo.getUserGender() != null) {
                Integer userGender = userInfo.getUserGender();
                if (userGender.equals(0)) {
                    userInfoVo.setUserGender(__("保密"));
                } else if (userGender.equals(1)) {
                    userInfoVo.setUserGender(__("男"));
                } else if (userGender.equals(2)) {
                    userInfoVo.setUserGender(__("女"));
                }
            }

            //生日
            if (userInfo.getUserBirthday() != null) {
                String userBirthday = DateUtil.format(userInfo.getUserBirthday(), "yyyy-MM-dd");
                userInfoVo.setUserBirthday(userBirthday);
            }

            //认证状态
            if (userInfo.getUserIsAuthentication() != null) {
                Integer userIsAuthentication = userInfo.getUserIsAuthentication();
                if (userIsAuthentication.equals(0)) {
                    userInfoVo.setUserIsAuthentication(__("未认证"));
                } else if (userIsAuthentication.equals(1)) {
                    userInfoVo.setUserIsAuthentication(__("待审核"));
                } else if (userIsAuthentication.equals(2)) {
                    userInfoVo.setUserIsAuthentication(__("认证通过"));
                } else if (userIsAuthentication.equals(3)) {
                    userInfoVo.setUserIsAuthentication(__("认证失败"));
                }
            }

            //新人标识
            if (userInfo.getUserNew() != null) {
                userInfoVo.setUserNew(userInfo.getUserNew() ? __("是") : __("不是"));
            }

            userInfoVos.add(userInfoVo);
        }
        ExcelUtil.exportReport(response, 0, "优惠券发放列表", userInfoVos);
    }

    @Override
    public void importTemp(MultipartFile file) throws Exception {
        AnalysisEventListener userInfoTempListener = new UserInfoTempListener();
        Class<?> tempClass = UserInfoTemp.class;

        InputStream inputStream = file.getInputStream();
        EasyExcel.read(inputStream)
                // 注册监听器，可以在这里校验字段
                .registerReadListener(userInfoTempListener)
                .head(tempClass)
                // 设置sheet,默认读取第一个
                .sheet()
                // 设置标题所在行数
                .headRowNumber(1)
                .doReadSync();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addVouchers(List<Integer> userIds, Integer activityId) {
        if (CollectionUtil.isEmpty(userIds)) {
            throw new BusinessException(__("用户编号为空"));
        }
        ActivityBase activityBase = activityBaseService.get(activityId);

        if (activityBase == null) {
            throw new BusinessException(__("活动不存在！"));
        }
        // 判断代金券数量是否足够
        String activityRule = activityBase.getActivityRule();
        ActivityRuleVo activityRuleVo = JSONUtil.parseObject(activityRule, ActivityRuleVo.class);

        if (activityRuleVo == null) {
            throw new BusinessException(__("活动规则为空！"));
        }
        VoucherVo voucherVo = activityRuleVo.getVoucher();

        if (voucherVo == null) {
            throw new BusinessException(__("活动优惠券信息为空！"));
        }
        Integer voucherQuantity = voucherVo.getVoucherQuantity();
        Integer voucher_quantity_use = voucherVo.getVoucherQuantityUse();

        if (NumberUtil.sub(voucherQuantity, voucher_quantity_use).intValue() - userIds.size() < 0) {
            throw new BusinessException(__("用户数大于优惠券剩余数量，添加失败！"));
        }

        for (Integer userId : userIds) {
            userVoucherService.addVoucher(activityId, userId);
        }
    }

}