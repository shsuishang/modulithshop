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
package com.suisung.shopsuite.trade.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.shop.repository.StoreAnalyticsRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.trade.model.entity.OrderBase;
import com.suisung.shopsuite.trade.model.entity.OrderComment;
import com.suisung.shopsuite.trade.model.entity.OrderInfo;
import com.suisung.shopsuite.trade.model.input.OrderCommentInput;
import com.suisung.shopsuite.trade.model.req.OrderCommentItemReq;
import com.suisung.shopsuite.trade.model.req.OrderCommentListReq;
import com.suisung.shopsuite.trade.repository.OrderCommentRepository;
import com.suisung.shopsuite.trade.repository.OrderInfoRepository;
import com.suisung.shopsuite.trade.service.OrderCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 订单店铺评价表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-01
 */
@Service
public class OrderCommentServiceImpl extends BaseServiceImpl<OrderCommentRepository, OrderComment, OrderCommentListReq> implements OrderCommentService {

    @Autowired
    private StoreAnalyticsRepository storeAnalyticsRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private OrderCommentRepository orderCommentRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    /**
     * 添加订单评论
     *
     * @param input
     */
    @Override
    public void addOrderComment(OrderCommentInput input) {
        ContextUser user = ContextUtil.getLoginUser();
        String orderId = input.getOrderId();
        OrderBase orderBase = input.getOrderBase();
        OrderCommentItemReq commentItemReq = input.getCommentItemReq();
        List<String> commentImage = input.getCommentImage();

        // 添加订单评论
        OrderComment orderComment = new OrderComment();
        BeanUtils.copyProperties(commentItemReq, orderComment);
        BigDecimal pointsEvaluateGood = configBaseService.getConfig("points_evaluate_good", BigDecimal.ZERO);
        orderComment.setCommentPoints(pointsEvaluateGood);
        orderComment.setStoreId(orderBase.getStoreId());
        orderComment.setStoreName(orderBase.getStoreName());

        assert user != null;
        orderComment.setUserId(user.getUserId());
        orderComment.setUserName(user.getUserNickname());
        orderComment.setCommentContent(orderComment.getCommentContent());
        if (CollUtil.isNotEmpty(commentImage)) {
            orderComment.setCommentImage(commentImage.toString());
        }
        orderComment.setCommentEnable(Boolean.TRUE);
        orderComment.setCommentTime(new Date());
        orderComment.setCommentStoreDescCredit(input.getStoreDesccredit().intValue());
        orderComment.setCommentStoreServiceCredit(input.getStoreServicecredit().intValue());
        orderComment.setCommentStoreDeliveryCredit(input.getStoreDeliverycredit().intValue());
        orderComment.setCommentScores((NumberUtil.add(input.getStoreDesccredit(), input.getStoreServicecredit(), input.getStoreDeliverycredit())).intValue() / 3);
        if (!orderCommentRepository.save(orderComment)) {
            throw new BusinessException(__("订单评论信息保存失败！"));
        }

        // 添加店铺分析数据
//        StoreAnalytics storeAnalytics = storeAnalyticsRepository.get(orderBase.getStoreId());
//        storeAnalytics.setStoreDesccredit(NumberUtil.add(storeAnalytics.getStoreDesccredit(), input.getStoreDesccredit()));
//        storeAnalytics.setStoreServicecredit(NumberUtil.add(storeAnalytics.getStoreServicecredit(), input.getStoreServicecredit()));
//        storeAnalytics.setStoreDeliverycredit(NumberUtil.add(storeAnalytics.getStoreDeliverycredit(), input.getStoreDeliverycredit()));
//        storeAnalytics.setStoreEvaluationNum(storeAnalytics.getStoreEvaluationNum() + 1);
//        if (!storeAnalyticsRepository.edit(storeAnalytics)) {
//            throw new BusinessException(__("店铺评论信息修改失败！"));
//        }

        // 修改订单信息
        OrderInfo shopOrderInfo = new OrderInfo();
        shopOrderInfo.setOrderId(orderId);
        shopOrderInfo.setOrderBuyerEvaluationStatus(StateCode.ORDER_EVALUATION_YES);
        if (!orderInfoRepository.edit(shopOrderInfo)) {
            throw new BusinessException(__("订单评论状态修改失败！"));
        }

        // 差评提醒
        if (commentItemReq.getCommentScores() == 1) {
            // todo 站内信
        }

    }
}
