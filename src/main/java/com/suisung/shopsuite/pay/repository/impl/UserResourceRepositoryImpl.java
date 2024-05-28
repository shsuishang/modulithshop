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
package com.suisung.shopsuite.pay.repository.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.PointsType;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.pay.dao.UserResourceDao;
import com.suisung.shopsuite.pay.model.entity.ConsumeRecord;
import com.suisung.shopsuite.pay.model.entity.UserPointsHistory;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.model.vo.MoneyVo;
import com.suisung.shopsuite.pay.model.vo.UserPointsVo;
import com.suisung.shopsuite.pay.repository.ConsumeRecordRepository;
import com.suisung.shopsuite.pay.repository.UserPointsHistoryRepository;
import com.suisung.shopsuite.pay.repository.UserResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;


/**
 * <p>
 * 用户资源表-资金账户表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Repository
public class UserResourceRepositoryImpl extends BaseRepositoryImpl<UserResourceDao, UserResource> implements UserResourceRepository {
    @Autowired
    private UserPointsHistoryRepository userPointsHistoryRepository;

    @Autowired
    private ConsumeRecordRepository consumeRecordRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 余额操作入口   区分正负数，未负数则减少
     */
    @Override
    @Transactional
    public boolean money(MoneyVo vo) {
        BigDecimal money = vo.getRecordTotal();
        Integer tradeTypeDeposit = vo.getTradeTypeDeposit();

        Date date = new Date();
        DateTime cur_time = DateUtil.parse(DateUtil.format(date, "yyyy-MM-dd"));

        ConsumeRecord consume_record_row = new ConsumeRecord();

        consume_record_row.setOrderId(vo.getOrderId());
        consume_record_row.setUserId(vo.getUserId());
        UserInfo userInfo = userInfoRepository.get(vo.getUserId());
        consume_record_row.setUserNickname(userInfo != null ? userInfo.getUserNickname() : "");
        consume_record_row.setRecordTime(date.getTime());
        consume_record_row.setRecordDate(cur_time);
        consume_record_row.setRecordYear(DateUtil.year(date));
        consume_record_row.setRecordMonth(DateUtil.month(date));
        consume_record_row.setRecordDay(DateUtil.dayOfMonth(date));
        consume_record_row.setRecordDesc(vo.getRecordDesc());

        if (ObjectUtil.equal(StateCode.PAYMENT_TYPE_OFFLINE, vo.getPaymentTypeId())) {
            consume_record_row.setRecordTitle(__("线下"));
        } else {
            consume_record_row.setRecordTitle(__("线上"));
        }

        UserResource userResource = get(vo.getUserId());
        if (userResource == null) {
            userResource = new UserResource();
            userResource.setUserId(vo.getUserId());
        }

        //通用判断
        String title = vo.getRecordDesc() + " " + StateCode.TRADE_TYPE_MAP.get(tradeTypeDeposit);

        switch (vo.getTradeTypeDeposit()) {
            case StateCode.TRADE_TYPE_WITHDRAW:
                BigDecimal user_money_frozen = ObjectUtil.defaultIfNull(userResource.getUserMoneyFrozen(), BigDecimal.ZERO);
                if (ObjectUtil.compare(money, BigDecimal.ZERO) > 0 && ObjectUtil.compare(user_money_frozen, money.negate()) < 0) {
                    throw new BusinessException(__("金额不足！"));
                }
                userResource.setUserMoneyFrozen(NumberUtil.add(user_money_frozen, money));
                break;
            case StateCode.TRADE_TYPE_SHOPPING_CARD:
            case StateCode.TRADE_TYPE_DEPOSIT_CARD:
                BigDecimal user_recharge_card = ObjectUtil.defaultIfNull(userResource.getUserRechargeCard(), BigDecimal.ZERO);
                if (ObjectUtil.compare(money, BigDecimal.ZERO) < 0 && ObjectUtil.compare(user_recharge_card, money.negate()) < 0) {
                    throw new BusinessException(__("金额不足！"));
                }
                userResource.setUserRechargeCard(NumberUtil.add(user_recharge_card, money));
                break;
            case StateCode.TRADE_TYPE_DEPOSIT:
            case StateCode.TRADE_TYPE_TRANSFER:
            case StateCode.TRADE_TYPE_COMMISSION:
            default:
                BigDecimal default_user_money = ObjectUtil.defaultIfNull(userResource.getUserMoney(), BigDecimal.ZERO);
                if (ObjectUtil.compare(money, BigDecimal.ZERO) < 0 && ObjectUtil.compare(default_user_money, money.negate()) < 0) {
                    throw new BusinessException(__("金额不足！"));
                }
                userResource.setUserMoney(NumberUtil.add(default_user_money, money));
                break;
        }

        consume_record_row.setRecordTitle(title);

        if (ObjectUtil.equal(tradeTypeDeposit, StateCode.TRADE_TYPE_DEPOSIT_CARD) || ObjectUtil.equal(tradeTypeDeposit, StateCode.TRADE_TYPE_SHOPPING_CARD)) {
            consume_record_row.setPaymentMetId(StateCode.PAYMENT_MET_RECHARGE_CARD);
        } else {
            consume_record_row.setPaymentMetId(StateCode.PAYMENT_MET_MONEY);
        }
        consume_record_row.setRecordTotal(money);

        if (money.compareTo(BigDecimal.ZERO) > 0) {
            consume_record_row.setRecordMoney(NumberUtil.sub(money, vo.getRecordCommissionFee()));
        } else {
            consume_record_row.setRecordMoney(NumberUtil.add(money, vo.getRecordCommissionFee()));
        }

        consume_record_row.setRecordCommissionFee(vo.getRecordCommissionFee()); // 佣金
        consume_record_row.setTradeTypeId(tradeTypeDeposit);

        // 买家数据
        if (!consumeRecordRepository.add(consume_record_row)) {
            throw new BusinessException("ResultCode.FAILED");
        }

        if (!edit(userResource)) {
            throw new BusinessException("ResultCode.FAILED");
        }

        //余额改变
        String messageId = "balance-change-reminder";
        HashMap<String, Object> args = new HashMap<>();
        args.put("user_money", userResource.getUserMoney());
        args.put("change_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        args.put("des", vo.getRecordDesc());
        args.put("user_money_freeze", 0);

        messageService.sendNoticeMsg(vo.getUserId(), messageId, args);

        return true;
    }

    /**
     * 积分操作统一入口 统一变更为正数，具体加减操作根据操作类型划分
     *
     * @return
     */
    @Override
    @Transactional
    public boolean points(UserPointsVo vo) {

        Integer points_kind_id = null;
        Date date = new Date();

        // 通用判断,注册和
        switch (vo.getPointsTypeId()) {
            case PointsType.POINTS_TYPE_REG:
                // 注册只可以触发一次
                long count = userPointsHistoryRepository.count(new QueryWrapper<UserPointsHistory>().eq("user_id", vo.getUserId()).eq("points_type_id", vo.getPointsTypeId()));

                if (count > 0) {
                    return false;
                }

                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_LOGIN:
                // 登录，每天只可以触发一次
                long num = userPointsHistoryRepository.count(new QueryWrapper<UserPointsHistory>().eq("user_id", vo.getUserId()).eq("points_type_id", vo.getPointsTypeId()).eq("points_log_date", date));

                if (num > 0) {
                    return false;
                }

                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_EVALUATE_PRODUCT:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_EVALUATE_STORE:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_CONSUME:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_OTHER:
                break;
            case PointsType.POINTS_TYPE_EXCHANGE_PRODUCT:
                points_kind_id = PointsType.POINTS_MINUS;
                break;
            case PointsType.POINTS_TYPE_EXCHANGE_VOUCHER:
                points_kind_id = PointsType.POINTS_MINUS;
                break;
            case PointsType.POINTS_TYPE_EXCHANGE_SP:
                points_kind_id = PointsType.POINTS_MINUS;
                break;
            case PointsType.POINTS_TYPE_TRANSFER_ADD:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_TRANSFER_MINUS:
                points_kind_id = PointsType.POINTS_MINUS;
                break;
            case PointsType.POINTS_TYPE_CONSUME_RETRUN:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_FX_FANS:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_UP_SELLER:
                points_kind_id = PointsType.POINTS_ADD;
                break;
            case PointsType.POINTS_TYPE_DEDUCTION:
                points_kind_id = PointsType.POINTS_MINUS;
                break;
            default:
                break;
        }

        if (points_kind_id == null) {
            if (ObjectUtil.compare(vo.getPoints(), BigDecimal.ZERO) > 0) {
                points_kind_id = PointsType.POINTS_ADD;
            } else {
                points_kind_id = PointsType.POINTS_MINUS;
            }
        }

        //取正数
        BigDecimal points = vo.getPoints().abs();

        UserPointsHistory data = new UserPointsHistory();
        data.setPointsKindId(points_kind_id); // 类型(ENUM):1-获取积分;2-消费积分;
        data.setPointsTypeId(vo.getPointsTypeId()); // 积分类型(ENUM):1-会员注册;2-会员登录;3-商品评论;4-购买商品;5-管理员操作;7-积分换购商品;8-积分兑换优惠券
        data.setUserId(vo.getUserId());
        data.setPointsLogPoints(points); // 改变积分
        data.setPointsLogDesc(vo.getPointsLogDesc()); // 描述
        data.setPointsLogDate(DateUtil.beginOfDay(date)); // 积分日期
        data.setPointsLogTime(date.getTime());
        data.setStoreId(vo.getStoreId());
        data.setUserIdOther(vo.getUserIdOther());

        return addPoints(data);
    }


    /**
     * 插入
     * 积分变动值points_log_points均为正数，增减由points_kind_id 控制和判断
     */
    public boolean addPoints(UserPointsHistory history) {
        Integer userId = history.getUserId();
        BigDecimal pointsLogPoints = history.getPointsLogPoints();

        // points_log_points均为正数，增减由points_kind_id 控制和判断
        BigDecimal changPoints = BigDecimal.ZERO;

        if (history.getPointsKindId() == PointsType.POINTS_ADD) {
            changPoints = pointsLogPoints;
        } else {
            changPoints = pointsLogPoints.negate();
        }

        // 当前积分 这里不走缓存（走缓存出现异常，缓存不能会滚）
        UserResource userResource = get(userId);
        BigDecimal userPoints = userResource.getUserPoints();

        if (ObjectUtil.compare(changPoints, BigDecimal.ZERO) < 0 && ObjectUtil.compare(userPoints, changPoints) < 0) {
            throw new BusinessException(__("积分不足！"));
        }

        userResource.setUserPoints(NumberUtil.add(userPoints, changPoints));

        if (!save(userResource)) {
            throw new BusinessException(__("修改积分数据失败！"));
        }

        history.setUserPoints(userResource.getUserPoints());
        if (!userPointsHistoryRepository.save(history)) {
            throw new BusinessException(__("保存积分日志失败！"));
        }

        return true;
    }
}
