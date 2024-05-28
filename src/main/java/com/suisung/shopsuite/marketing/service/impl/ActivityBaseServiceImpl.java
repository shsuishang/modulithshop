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
package com.suisung.shopsuite.marketing.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.account.repository.UserLevelRepository;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.entity.ActivityType;
import com.suisung.shopsuite.marketing.model.req.ActivityBaseListReq;
import com.suisung.shopsuite.marketing.model.res.ActivityBaseRes;
import com.suisung.shopsuite.marketing.model.vo.ActivityRuleVo;
import com.suisung.shopsuite.marketing.model.vo.BuyVo;
import com.suisung.shopsuite.marketing.model.vo.RequirementVo;
import com.suisung.shopsuite.marketing.model.vo.VoucherVo;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.marketing.repository.ActivityTypeRepository;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.shop.model.entity.StoreBase;
import com.suisung.shopsuite.shop.model.entity.UserVoucherNum;
import com.suisung.shopsuite.shop.repository.StoreBaseRepository;
import com.suisung.shopsuite.shop.repository.UserVoucherNumRepository;
import com.suisung.shopsuite.shop.repository.UserVoucherRepository;
import com.suisung.shopsuite.sys.service.CurrencyBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 活动表-通过插件实现	  服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Service
public class ActivityBaseServiceImpl extends BaseServiceImpl<ActivityBaseRepository, ActivityBase, ActivityBaseListReq> implements ActivityBaseService {
    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private StoreBaseRepository storeBaseRepository;

    @Autowired
    private UserVoucherRepository voucherRepository;

    @Autowired
    private CurrencyBaseService currencyBaseService;

    @Autowired
    private UserLevelRepository levelRepository;

    @Autowired
    private UserVoucherNumRepository userVoucherNumRepository;

    /**
     * 取得活动相关产品信息
     *
     * @param activityBaseList
     * @return
     */
    @Override
    public List<ActivityBase> fixActivityData(List<ActivityBase> activityBaseList) {

        if (CollUtil.isEmpty(activityBaseList)) return new ArrayList<>();

        /*
        List<Integer> storeIds = activityBaseList.stream().map(base -> base.getStoreId()).distinct().collect(Collectors.toList());
        QueryWrapper<StoreBase> baseQueryWrapper = new QueryWrapper<>();
        baseQueryWrapper.in("store_id", storeIds);
        List<StoreBase> storeBaseList = storeBaseService.find(baseQueryWrapper);
         */

        //活动分类信息
        /*
        List<Integer> activityTypeIds = activityBaseList.stream().map(base -> base.getActivityTypeId()).distinct().collect(Collectors.toList());
        QueryWrapper<ActivityType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.in("activity_type_id", activityTypeIds);
        List<ActivityType> activityTypeList = activityTypeRepository.getActivityTypeList(typeQueryWrapper);
        */

        List<ActivityType> activityTypeList = activityTypeRepository.getActivityTypeList();
        List<Integer> activityTypeIds = CommonUtil.column(activityTypeList, ActivityType::getActivityTypeId);

        Date now = new Date();
        long time = now.getTime();

        for (ActivityBase baseMap : activityBaseList) {

            Integer activity_id = baseMap.getActivityId();
            //判断活动店铺有效
            Integer activity_state = baseMap.getActivityState();
            // 活动开始时间
            Long activity_starttime = baseMap.getActivityStarttime();
            // 活动结束时间
            Long activity_endtime = baseMap.getActivityEndtime();
            // 活动类型
            Integer activity_type = baseMap.getActivityType();

            if (CheckUtil.isNotEmpty(activity_type) && activity_type.equals(StateCode.GET_VOUCHER_BY_PURCHASE)) {
                //根据售卖优惠券id获取对应商品信息
            }

            switch (activity_state) {
                case StateCode.ACTIVITY_STATE_WAITING:
                    if (activity_starttime.compareTo(time) <= 0) {
                        if (activity_endtime.compareTo(time) <= 0) {
                            baseMap.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
                        } else {
                            baseMap.setActivityState(StateCode.ACTIVITY_STATE_NORMAL);
                        }

                        //更新数据
                        editActivityBase(activity_id, baseMap);
                    }
                    break;
                case StateCode.ACTIVITY_STATE_NORMAL:
                    if (activity_endtime.compareTo(time) < 0) {
                        baseMap.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
                        //更新数据
                        editActivityBase(activity_id, baseMap);
                    }

                    if (activity_starttime.compareTo(time) > 0) {
                        baseMap.setActivityState(StateCode.ACTIVITY_STATE_WAITING);

                        //更新数据
                        editActivityBase(activity_id, baseMap);
                    }
                    break;
                case StateCode.ACTIVITY_STATE_FINISHED:
                    if (activity_starttime.compareTo(time) <= 0) {
                        if (activity_endtime.compareTo(time) <= 0) {
                            baseMap.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
                        } else {
                            baseMap.setActivityState(StateCode.ACTIVITY_STATE_NORMAL);
                        }

                        //更新数据
                        editActivityBase(activity_id, baseMap);
                    }
                    if (activity_starttime.compareTo(time) > 0) {
                        baseMap.setActivityState(StateCode.ACTIVITY_STATE_WAITING);

                        //更新数据
                        editActivityBase(activity_id, baseMap);
                    }

                    break;
                case StateCode.ACTIVITY_STATE_CLOSED:
                    if (activity_starttime.compareTo(time) <= 0 && activity_endtime.compareTo(time) <= 0) {
                        baseMap.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
                        //更新数据
                        editActivityBase(activity_id, baseMap);
                    }

                    break;
            }

            /*
            Integer storeId = baseMap.getStoreId();
            // 获取store_name
            Optional<String> nameOpl = storeBaseList.stream().filter(store -> Objects.equals(store.getStore_id(), storeId)).map(StoreBase::getStore_name).findFirst();
            nameOpl.ifPresent(n -> baseMap.put("store_name", n));
             */
        }

        return activityBaseList;
    }


    /**
     * 取得活动相关产品信息
     *
     * @param activity_id
     * @param data
     */
    @Transactional
    public boolean editActivityBase(Integer activity_id, ActivityBase data) {

        if (!repository.save(data)) {
            throw new BusinessException("ResultCode.FAILED");
        }

        return true;
    }

    @Override
    public IPage<ActivityBaseRes> voucherList(ActivityBaseListReq activityBaseListReq) {
        IPage<ActivityBaseRes> activityBaseResPage = new Page<>();
        Integer userId = activityBaseListReq.getUserId();

        if ("index".equals(activityBaseListReq.getMet())) {
            activityBaseListReq.setSize(3);
        }
        // 根据店铺读取活动
        QueryWrapper<ActivityBase> activityQuery = new QueryWrapper<>();

        if (CheckUtil.isNotEmpty(activityBaseListReq.getStoreId())) {
            activityQuery.eq("store_id", activityBaseListReq.getStoreId());
        }

        if (CheckUtil.isNotEmpty(activityBaseListReq.getActivityType())) {
            activityQuery.eq("activity_type", activityBaseListReq.getActivityType());
        } else {
            //activityQuery.in("activity_type", Arrays.asList(0, StateCode.GET_VOUCHER_FREE, StateCode.GET_VOUCHER_BY_POINT, StateCode.GET_VOUCHER_BY_SHARE));
        }

        activityQuery.eq("activity_state", StateCode.ACTIVITY_STATE_NORMAL);
        activityQuery.eq("activity_type_id", StateCode.ACTIVITY_TYPE_VOUCHER);
        activityQuery.orderByDesc("activity_addtime");
        IPage<ActivityBase> activityPage = lists(activityQuery, activityBaseListReq.getPage(), activityBaseListReq.getSize());

        if (activityPage != null && CollectionUtil.isNotEmpty(activityPage.getRecords())) {
            BeanUtils.copyProperties(activityPage, activityBaseResPage);
            List<ActivityBase> activityList = activityPage.getRecords();
            List<ActivityBaseRes> activityBaseResList = new ArrayList<>();
            //store_name
            List<Integer> storeIds = activityList.stream().map(ActivityBase::getStoreId).collect(Collectors.toList());
            List<StoreBase> storeBaseList = storeBaseRepository.gets(storeIds);
            Map<Integer, String> storeNameMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(storeBaseList)) {
                storeNameMap = storeBaseList.stream().collect(Collectors.toMap(StoreBase::getStoreId, StoreBase::getStoreName, (k1, k2) -> k1));
            }

            List<Integer> activityIds = CommonUtil.column(activityList, ActivityBase::getActivityId);
            List<UserVoucherNum> userVoucherNumList = userVoucherNumRepository.find(new QueryWrapper<UserVoucherNum>().in("activity_id", activityIds).eq("user_id", userId));

            List<UserLevel> userLevels = levelRepository.find(new QueryWrapper<>());
            Map<Integer, String> userLevelMap = userLevels.stream()
                    .collect(Collectors.toMap(UserLevel::getUserLevelId, UserLevel::getUserLevelName));

            for (ActivityBase activityBase : activityList) {
                ActivityBaseRes activityBaseRes = new ActivityBaseRes();
                BeanUtils.copyProperties(activityBase, activityBaseRes);
                String activityRule = activityBase.getActivityRule();

                // 会员等级名称
                List<String> activityUseLevelName = new ArrayList<>();
                List<Integer> activityUserLevels = Convert.toList(Integer.class, activityBase.getActivityUseLevel());
                Collections.sort(activityUserLevels);
                for (Integer userLevel : activityUserLevels) {
                    String val = userLevelMap.get(userLevel);
                    activityUseLevelName.add(val);
                }
                if (CollUtil.isNotEmpty(activityUseLevelName)) {
                    activityBaseRes.setActivityUseLevelName(CollUtil.join(activityUseLevelName, "、") + __("专属"));
                }

                //store_name
                if (!storeNameMap.isEmpty()) {
                    activityBaseRes.setStoreName(storeNameMap.get(activityBase.getStoreId()));
                }

                //处理ruleJson 为jsonObject
                ActivityRuleVo activityRuleVo = new ActivityRuleVo();
                if (StrUtil.isNotEmpty(activityRule)) {
                    activityRuleVo = JSONUtil.parseObject(activityRule, ActivityRuleVo.class);
                    activityBaseRes.setActivityRuleJson(activityRuleVo);
                }

                //优惠券达到领取上限ifGain
                Integer voucherSize = 0;
                UserVoucherNum userVoucherNum = userVoucherNumList.stream().filter(s -> ObjectUtil.equal(s.getActivityId(), activityBase.getActivityId())).findFirst().orElse(null);

                if (ObjectUtil.isNotEmpty(userVoucherNum)) {
                    voucherSize = userVoucherNum.getUvnNum();
                }

                if (activityRuleVo != null) {
                    VoucherVo voucher = activityRuleVo.getVoucher();

                    if (voucher != null) {
                        Integer voucherPreQuantity = voucher.getVoucherPreQuantity();
                        activityBaseRes.setIfGain(CheckUtil.isNotEmpty(userId) && voucherPreQuantity > voucherSize);
                    }
                } else {
                    activityBaseRes.setIfGain(false);
                }

                activityBaseResList.add(activityBaseRes);
            }
            activityBaseResPage.setRecords(activityBaseResList);
        }

        return activityBaseResPage;
    }

    @Override
    public List<ActivityBaseRes> getActivityBases(List<Integer> activityIds, Integer currencyId) {
        List<ActivityBase> activityBases = gets(activityIds);
        List<ActivityBaseRes> activityBaseResList = new ArrayList<>();
        // 修改汇率
        BigDecimal currencyRate = currencyBaseService.getCurrencyRate(currencyId);

        if (CollectionUtil.isNotEmpty(activityBases)) {
            for (ActivityBase activityBase : activityBases) {
                ActivityBaseRes activityBaseRes = new ActivityBaseRes();
                BeanUtils.copyProperties(activityBase, activityBaseRes);
                Integer activityTypeId = activityBase.getActivityTypeId();
                String activityRule = activityBase.getActivityRule();
                ActivityRuleVo activityRuleVo = new ActivityRuleVo();

                switch (activityTypeId) {
                    //店铺优惠券
                    case StateCode.ACTIVITY_TYPE_VOUCHER:
                        activityRuleVo = com.suisung.shopsuite.common.utils.JSONUtil.parseObject(activityRule, ActivityRuleVo.class);

                        if (activityRuleVo != null) {
                            VoucherVo voucher = activityRuleVo.getVoucher();

                            if (voucher != null) {
                                BigDecimal voucherPrice = voucher.getVoucherPrice();
                                voucher.setVoucherPrice(NumberUtil.mul(voucherPrice, currencyRate));
                                RequirementVo requirement = activityRuleVo.getRequirement();

                                if (requirement != null) {
                                    BuyVo buy = requirement.getBuy();

                                    if (buy != null) {
                                        BigDecimal subtotal = buy.getSubtotal();
                                        buy.setSubtotal(NumberUtil.mul(subtotal, currencyRate));
                                    }
                                }
                            }
                        }

                        break;
                }
                activityBaseRes.setActivityRuleJson(activityRuleVo);
                activityBaseResList.add(activityBaseRes);
            }
        }

        return activityBaseResList;
    }

    @Override
    public IPage<ActivityBaseRes> getList(ActivityBaseListReq activityBaseListReq) {
        IPage<ActivityBaseRes> activityBaseResPage = new Page<>();
        activityBaseListReq.setSidx("activity_id");
        activityBaseListReq.setSort("Desc");
        IPage<ActivityBase> activityBaseIPage = lists(activityBaseListReq);

        if (activityBaseIPage == null || CollectionUtil.isEmpty(activityBaseIPage.getRecords())) {
            return activityBaseResPage;
        }
        BeanUtils.copyProperties(activityBaseIPage, activityBaseResPage);
        List<ActivityBase> activityBaseList = activityBaseIPage.getRecords();
        //修正活动最新状态
        List<ActivityBase> activityBases = fixActivityData(activityBaseList);
        List<ActivityBaseRes> activityBaseRes = new ArrayList<>();
        for (ActivityBase activityBase : activityBases) {
            ActivityBaseRes activityRes = new ActivityBaseRes();
            BeanUtils.copyProperties(activityBase, activityRes);
            //会员等级处理
            String activityUseLevel = activityBase.getActivityUseLevel();

            if (StrUtil.isNotEmpty(activityUseLevel)) {
                List<Integer> levelIds = Convert.toList(Integer.class, activityUseLevel);
                List<UserLevel> userLevelList = levelRepository.gets(levelIds);

                if (CollectionUtil.isNotEmpty(userLevelList)) {
                    List<String> useLevelList = userLevelList.stream().map(UserLevel::getUserLevelName).collect(Collectors.toList());
                    String useLevelStr = String.join(",", useLevelList);
                    activityRes.setUseLevel(useLevelStr);
                }
            }
            String activityRule = activityBase.getActivityRule();

            if (StrUtil.isNotEmpty(activityRule)) {
                activityRes.setActivityRuleJson(JSONUtil.parseObject(activityRule, ActivityRuleVo.class));
            }

            activityBaseRes.add(activityRes);
        }
        activityBaseResPage.setRecords(activityBaseRes);

        return activityBaseResPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addActivityBase(ActivityBase activityBase) {

        if (activityBase == null) {
            return false;
        }
        activityBase.setActivityName(activityBase.getActivityTitle());
        long currentTime = new Date().getTime();

        if (currentTime < activityBase.getActivityStarttime()) {
            activityBase.setActivityState(StateCode.ACTIVITY_STATE_WAITING);
        } else if (currentTime > activityBase.getActivityEndtime()) {
            activityBase.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
        } else {
            activityBase.setActivityState(StateCode.ACTIVITY_STATE_NORMAL);
        }

        return add(activityBase);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateActivityBase(ActivityBase activityBase) {
        Integer activityId = activityBase.getActivityId();
        ActivityBase activity = get(activityId);
        activityBase.setActivityName(activityBase.getActivityTitle());

        if (activityBase.getActivityStarttime() < new Date().getTime() && activityBase.getActivityState().equals(StateCode.ACTIVITY_STATE_NORMAL)) {
            //throw new BusinessException(__("活动已开始不可进行修改！"));
        }

        return editActivityBase(activityId, activityBase);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeActivity(Integer activityId, Integer storeId) {
        ActivityBase activity = get(activityId);

        if (!CheckUtil.checkDataRights(storeId, activity, ActivityBase::getStoreId)) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        List<Integer> activityState = Arrays.asList(StateCode.ACTIVITY_STATE_NORMAL);

        if (activityState.contains(activity.getActivityState())) {
            throw new BusinessException(__("该状态无法删除！"));
        }

        /*
        if (activity.getActivityStarttime() < new Date().getTime()) {
            throw new BusinessException(__("活动已开始不可进行删除！"));
        }
         */

        if (!remove(activityId)) {
            throw new BusinessException(__("删除失败！"));
        }

        return true;
    }
}
