// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.res.ActivityBaseRes;
import com.suisung.shopsuite.marketing.model.vo.*;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.shop.model.entity.UserVoucher;
import com.suisung.shopsuite.shop.model.entity.UserVoucherNum;
import com.suisung.shopsuite.shop.model.req.UserVoucherListReq;
import com.suisung.shopsuite.shop.model.res.UserVoucherRes;
import com.suisung.shopsuite.shop.model.res.VoucherCountRes;
import com.suisung.shopsuite.shop.repository.UserVoucherNumRepository;
import com.suisung.shopsuite.shop.repository.UserVoucherRepository;
import com.suisung.shopsuite.shop.service.StoreBaseService;
import com.suisung.shopsuite.shop.service.UserVoucherService;
import com.suisung.shopsuite.sys.service.CurrencyBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 用户优惠券表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Service
public class UserVoucherServiceImpl extends BaseServiceImpl<UserVoucherRepository, UserVoucher, UserVoucherListReq> implements UserVoucherService {

    @Autowired
    private ActivityBaseService activityBaseService;

    @Autowired
    private CurrencyBaseService currencyBaseService;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private UserVoucherNumRepository userVoucherNumRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public VoucherCountRes getEachVoucherNum(Integer voucherStateId, Integer userId) {
        VoucherCountRes voucherCountRes = new VoucherCountRes();
        QueryWrapper<UserVoucher> userVoucherQueryWrapper = new QueryWrapper<>();
        userVoucherQueryWrapper.eq("user_id", userId);

        if (CheckUtil.isNotEmpty(voucherStateId)) {
            userVoucherQueryWrapper.eq("voucher_state_id", voucherStateId);
        }
        // 全部优惠券数量
        voucherCountRes.setVoucherAllNum(count(userVoucherQueryWrapper));

        QueryWrapper<UserVoucher> offlineQuery = new QueryWrapper<>();
        offlineQuery.eq("user_id", userId);
        offlineQuery.gt("length(writeoff_code)", 0);

        if (CheckUtil.isNotEmpty(voucherStateId)) {
            offlineQuery.eq("voucher_state_id", voucherStateId);
        }
        // 线下优惠券数量
        voucherCountRes.setVoucherOfflinedNum(count(offlineQuery));

        QueryWrapper<UserVoucher> onlineQuery = new QueryWrapper<>();
        onlineQuery.eq("user_id", userId);
        onlineQuery.eq("writeoff_code", "");

        if (CheckUtil.isNotEmpty(voucherStateId)) {
            onlineQuery.eq("voucher_state_id", voucherStateId);
        }

        // 线上优惠券数量
        voucherCountRes.setVoucherOnlinedNum(count(onlineQuery));

        QueryWrapper<UserVoucher> unusedQuery = new QueryWrapper<>();
        unusedQuery.eq("user_id", userId)
                .eq("voucher_state_id", StateCode.VOUCHER_STATE_UNUSED);
        // 未使用优惠券
        voucherCountRes.setVoucherUnusedNum(count(unusedQuery));

        QueryWrapper<UserVoucher> userQuery = new QueryWrapper<>();
        userQuery.eq("user_id", userId)
                .eq("voucher_state_id", StateCode.VOUCHER_STATE_USED);
        // 已使用优惠券
        voucherCountRes.setVoucherUsedNum(count(userQuery));

        QueryWrapper<UserVoucher> timeoutQuery = new QueryWrapper<>();
        timeoutQuery.eq("user_id", userId)
                .eq("voucher_state_id", StateCode.VOUCHER_STATE_TIMEOUT);
        // 已过期优惠券
        voucherCountRes.setVoucherTimeoutNum(count(timeoutQuery));

        return voucherCountRes;
    }

    @Override
    public IPage<UserVoucherRes> getList(UserVoucherListReq voucherListReq) {
        IPage<UserVoucherRes> voucherResPage = new Page<>();
        QueryWrapper<UserVoucher> voucherQueryWrapper = new QueryWrapper<>();

        if (CheckUtil.isNotEmpty(voucherListReq.getUserId())) {
            voucherQueryWrapper.eq("user_id", voucherListReq.getUserId());
        }

        if (CheckUtil.isNotEmpty(voucherListReq.getActivityId())) {
            voucherQueryWrapper.eq("activity_id", voucherListReq.getActivityId());
        }

        if (CheckUtil.isNotEmpty(voucherListReq.getStoreId())) {
            voucherQueryWrapper.eq("store_id", voucherListReq.getStoreId());
        }
        //优惠券是否生效
        long time = new Date().getTime();

        if (voucherListReq.getVoucherEffect()) {
            voucherQueryWrapper.le("voucher_start_date", time);
            voucherQueryWrapper.ge("voucher_end_date", time);
        }
        // 处理全部券1、线下券2、线上券3
        if (CheckUtil.isNotEmpty(voucherListReq.getVoucherUserWay())) {
            switch (voucherListReq.getVoucherUserWay()) {
                case 2:
                    voucherQueryWrapper.ne("writeoff_code", "");
                    break;
                case 3:
                    voucherQueryWrapper.eq("writeoff_code", "");
                    break;
            }
        }

        if (CheckUtil.isNotEmpty(voucherListReq.getVoucherStateId())) {
            voucherQueryWrapper.eq("voucher_state_id", voucherListReq.getVoucherStateId());
        }

        voucherQueryWrapper.orderByDesc("user_voucher_time");
        voucherQueryWrapper.orderByAsc("voucher_state_id");
        IPage<UserVoucher> voucherPage = lists(voucherQueryWrapper, voucherListReq.getPage(), voucherListReq.getSize());

        if (voucherPage != null && CollectionUtil.isNotEmpty(voucherPage.getRecords())) {
            BeanUtils.copyProperties(voucherPage, voucherResPage);
            voucherResPage.setRecords(BeanUtil.copyToList(voucherPage.getRecords(), UserVoucherRes.class));

            List<UserVoucher> userVoucherList = voucherPage.getRecords();
            List<UserVoucherRes> userVoucherReList = new ArrayList<>();
            Long currentTime = new Date().getTime();

            for (UserVoucher userVoucher : userVoucherList) {
                UserVoucherRes userVoucherRes = new UserVoucherRes();
                BeanUtils.copyProperties(userVoucher, userVoucherRes);
                userVoucherRes.setId(userVoucher.getUserVoucherId());
                long voucherEndDate = userVoucher.getVoucherEndDate();

                if (userVoucher.getVoucherStateId().equals(StateCode.VOUCHER_STATE_UNUSED)) {
                    if (voucherEndDate < currentTime) {
                        userVoucherRes.setVoucherStateId(StateCode.VOUCHER_STATE_TIMEOUT);
                        // 更新数据
                        UserVoucher voucher = new UserVoucher();
                        voucher.setUserVoucherId(userVoucher.getUserVoucherId());
                        voucher.setVoucherStateId(StateCode.VOUCHER_STATE_TIMEOUT);
                        edit(voucher);
                    }
                    //未生效标记
                    Long voucherStartDate = userVoucher.getVoucherStartDate();

                    if (voucherStartDate > currentTime) {
                        userVoucherRes.setVoucherEffect(false);
                    }
                }

                userVoucherReList.add(userVoucherRes);
            }

            voucherResPage.setRecords(userVoucherReList);
        }

        return voucherResPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserVoucher addVoucher(Integer activityId, Integer userId) {
        UserVoucher voucher = new UserVoucher();
        voucher.setUserVoucherTime(new Date());
        voucher.setActivityId(activityId);
        voucher.setVoucherStateId(StateCode.VOUCHER_STATE_UNUSED);
        voucher.setUserId(userId);

        ActivityBase activityBase = activityBaseService.get(activityId);

        if (activityBase == null) {
            throw new BusinessException(__("活动不存在！"));
        }

        if (!activityBase.getActivityState().equals(StateCode.ACTIVITY_STATE_NORMAL)) {
            throw new BusinessException(__("活动未开启,领取失败！"));
        }
        //判断优惠券是否有等级限制
        String activityUseLevel = activityBase.getActivityUseLevel();

        if (StrUtil.isNotEmpty(activityUseLevel)) {
            List<Integer> userLevels = Convert.toList(Integer.class, activityUseLevel);

            if (CollectionUtil.isNotEmpty(userLevels)) {
                UserInfo userInfo = userInfoRepository.get(userId);

                if (userInfo == null) {
                    throw new BusinessException(__("用户信息不存在！"));
                }
                if (!userLevels.contains(userInfo.getUserLevelId())) {
                    throw new BusinessException(__("不属于该优惠券指定的会员等级，领取失败！"));
                }
            }
        }

        // 判断代金券数量是否足够
        String activityRule = activityBase.getActivityRule();
        ActivityRuleVo activityRuleVo = com.suisung.shopsuite.common.utils.JSONUtil.parseObject(activityRule, ActivityRuleVo.class);

        if (activityRuleVo == null) {
            throw new BusinessException(__("活动规则为空！"));
        }
        VoucherVo voucherVo = activityRuleVo.getVoucher();

        if (voucherVo == null) {
            throw new BusinessException(__("活动优惠券信息为空！"));
        }

        Integer voucherQuantity = voucherVo.getVoucherQuantity();
        Integer voucherQuantityFree = voucherVo.getVoucherQuantityFree();

        if (NumberUtil.add(voucherQuantity, voucherQuantityFree).intValue() <= 0) {
            throw new BusinessException(__("代金券已经被抢完,领取失败！"));
        }

        Integer voucherPreQuantity = voucherVo.getVoucherPreQuantity();
        Integer voucherSize = 0;
        UserVoucherNum userVoucherNum = userVoucherNumRepository.findOne(new QueryWrapper<UserVoucherNum>().eq("activity_id", activityId).eq("user_id", userId));

        if (ObjectUtil.isNotEmpty(userVoucherNum)) {
            voucherSize = userVoucherNum.getUvnNum();
        }

        if (voucherSize < voucherPreQuantity) {
            RequirementVo requirement = activityRuleVo.getRequirement();
            BuyVo buy = requirement.getBuy();
            List<Long> item = buy.getItem();
            BigDecimal subtotal = buy.getSubtotal();
            BigDecimal voucherPrice = voucherVo.getVoucherPrice();
            voucher.setVoucherSubtotal(subtotal);
            voucher.setVoucherPrice(voucherPrice);
            voucher.setVoucherStartDate(voucherVo.getVoucherStartDate());
            voucher.setVoucherEndDate(voucherVo.getVoucherEndDate());
            voucher.setStoreId(activityBase.getStoreId());
            voucher.setActivityName(activityBase.getActivityName());
            voucher.setActivityRule(activityBase.getActivityRule());

            if (CollUtil.isNotEmpty(item)) {
                voucher.setItemId(CollUtil.join(item, ","));
            } else {
            }

            Integer activityType = activityBase.getActivityType();
            Integer pointsNeeded = 0;

            if (activityType.equals(StateCode.GET_VOUCHER_BY_POINT)) {
                PointsVo points = requirement.getPoints();
                pointsNeeded = points.getNeeded();

                if (pointsNeeded <= 0) {
                    throw new BusinessException(__("该活动信息错误，积分需大于0！"));
                }
                //TODO 积分操作
            }

            if (!add(voucher)) {
                throw new BusinessException(__("用户优惠券领取失败！"));
            }

            if (ObjectUtil.isNotEmpty(userVoucherNum)) {
                userVoucherNum.setUvnNum(userVoucherNum.getUvnNum() + 1);
                if (!userVoucherNumRepository.edit(userVoucherNum)) {
                    throw new BusinessException(__("优惠券领取失败！"));
                }
            } else {
                userVoucherNum = new UserVoucherNum();
                userVoucherNum.setActivityId(activityId);
                userVoucherNum.setUserId(userId);
                userVoucherNum.setUvnNum(1);
                if (!userVoucherNumRepository.add(userVoucherNum)) {
                    throw new BusinessException(__("优惠券领取失败！"));
                }
            }

            //活动数据
            Integer voucherQuantityUse = voucherVo.getVoucherQuantityUse() == null ? 1 : voucherVo.getVoucherQuantityUse() + 1;

            voucherVo.setVoucherQuantityUse(voucherQuantityUse);
            voucherVo.setVoucherQuantityFree(-voucherQuantityUse);
            activityRuleVo.setVoucher(voucherVo);

            ActivityBase activity = new ActivityBase();
            activity.setActivityId(activityId);
            activity.setActivityRule(JSONUtil.toJsonStr(activityRuleVo));

            //是否领完判断
            if (voucherQuantity <= voucherQuantityUse) {
                activity.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
            }

            if (!activityBaseService.edit(activity)) {
                throw new BusinessException(__("更新优惠券信息失败！"));
            }

            if (activityType.equals(StateCode.GET_VOUCHER_BY_POINT)) {
                //TODO 积分操作

            }

        } else {
            throw new BusinessException(__("领取数量超限！"));
        }

        String messageId = "coupons-to-the-accounts";
        Map<String, Object> args = new HashMap<>();
        args.put("name", activityBase.getActivityTitle());
        String endTime = DateUtil.format(DateUtil.date(activityBase.getActivityEndtime()), "yyyy-MM-dd HH:mm:ss");
        args.put("endtime", endTime);
        messageService.sendNoticeMsg(userId, messageId, args);

        return voucher;
    }

    @Override
    public UserVoucherRes getVoucher(Integer activityId, Integer userVoucherId, Integer currencyId) {
//        //是否为商家后台访问
//        if (user == null || !(user.isAdmin() && user.isStore())) {
//            // 修改汇率
//            BigDecimal currencyRate = currencyBaseService.getCurrencyRate(currencyId);
//            voucher.setVoucherPrice(NumberUtil.mul(voucher.getVoucherPrice(), currencyRate));
//            voucher.setVoucherSubtotal(NumberUtil.mul(voucher.getVoucherSubtotal(), currencyRate));
//        }

        UserVoucher voucher = null;

        if (CheckUtil.isNotEmpty(userVoucherId)) {
            voucher = get(userVoucherId);
        }

        UserVoucherRes userVoucherRes = new UserVoucherRes();
        List<ActivityBaseRes> activityBases = activityBaseService.getActivityBases(Collections.singletonList(activityId), currencyId);

        if (ObjectUtil.isEmpty(voucher)) {
            if (CollectionUtil.isNotEmpty(activityBases)) {
                ActivityBaseRes activityBaseRes = activityBases.get(0);
                BeanUtils.copyProperties(activityBaseRes, userVoucherRes);

                BuyVo buy = activityBaseRes.getActivityRuleJson().getRequirement().getBuy();
                VoucherVo voucherVo = activityBaseRes.getActivityRuleJson().getVoucher();
                userVoucherRes.setVoucherSubtotal(buy.getSubtotal());
                userVoucherRes.setVoucherPrice(voucherVo.getVoucherPrice());
                userVoucherRes.setVoucherStartDate(voucherVo.getVoucherStartDate());
                userVoucherRes.setVoucherEndDate(voucherVo.getVoucherEndDate());
                userVoucherRes.setStoreId(activityBaseRes.getStoreId());
                userVoucherRes.setActivityName(activityBaseRes.getActivityName());
                userVoucherRes.setActivityRule(activityBaseRes.getActivityRule());
                
                /*
                //storeName
                StoreBase storeBase = storeBaseService.get(userVoucherRes.getStoreId());

                if (storeBase != null) {
                    userVoucherRes.setStoreName(storeBase.getStoreName());
                }
                */
                //userVoucherRes.setActivityName(activityBaseRes.getActivityName());
                //userVoucherRes.setActivityRuleJson(activityBaseRes.getActivityRuleJson());
                userVoucherRes.setActivityState(activityBaseRes.getActivityState());
                List<Long> item = activityBaseRes.getActivityRuleJson().getRequirement().getBuy().getItem();
                if (CollUtil.isNotEmpty(item)) {
                    voucher.setItemId(CollUtil.join(item, ","));
                } else {
                }
            } else {
                userVoucherRes.setActivityState(StateCode.ACTIVITY_STATE_CLOSED);
            }
        } else {
            BeanUtils.copyProperties(voucher, userVoucherRes);

            if (CollectionUtil.isNotEmpty(activityBases)) {
                ActivityBaseRes activityBaseRes = activityBases.get(0);
                userVoucherRes.setActivityState(activityBaseRes.getActivityState());
            }
        }

        userVoucherRes.setItemIds(Convert.toList(String.class, userVoucherRes.getItemId()));

        ActivityRuleVo activityRuleVo = com.suisung.shopsuite.common.utils.JSONUtil.parseObject(userVoucherRes.getActivityRule(), ActivityRuleVo.class);
        userVoucherRes.setActivityRuleJson(activityRuleVo);

        Integer voucherStateId = userVoucherRes.getVoucherStateId();

        if (CheckUtil.isNotEmpty(voucherStateId) && voucherStateId.equals(StateCode.VOUCHER_STATE_UNUSED)) {
            if (userVoucherRes.getVoucherEndDate() < userVoucherRes.getVoucherStartDate()) {
                //更新数据
                UserVoucher userVoucher = new UserVoucher();
                userVoucher.setUserVoucherId(userVoucherRes.getUserVoucherId());
                userVoucher.setVoucherStateId(StateCode.VOUCHER_STATE_TIMEOUT);
                edit(userVoucher);

                userVoucherRes.setVoucherStateId(StateCode.VOUCHER_STATE_TIMEOUT);
            }
        }

        return userVoucherRes;
    }
}
