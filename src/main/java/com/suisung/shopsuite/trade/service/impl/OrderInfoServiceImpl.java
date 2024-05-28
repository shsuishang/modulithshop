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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.trade.model.entity.OrderInfo;
import com.suisung.shopsuite.trade.model.req.OrderInfoListReq;
import com.suisung.shopsuite.trade.repository.OrderInfoRepository;
import com.suisung.shopsuite.trade.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Service
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfoRepository, OrderInfo, OrderInfoListReq> implements OrderInfoService {

    @Autowired
    private ConfigBaseService configBaseService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrderInfo(String orderId, boolean orderIsSettlemented) {
        List<String> orderIds = Convert.toList(String.class, orderId);
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_id", orderIds).eq("order_is_settlemented", 0);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderIsSettlemented(orderIsSettlemented);
        orderInfo.setOrderSettlementTime(new Date());

        return edit(orderInfo, queryWrapper);
    }

    @Override
    public List<String> getAutoCancelOrderId() {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_state_id", StateCode.ORDER_STATE_WAIT_PAY)
                .eq("order_is_paid", StateCode.ORDER_PAID_STATE_NO)
                .eq("payment_type_id", StateCode.PAYMENT_TYPE_ONLINE);

        Float orderAutocancelTime = configBaseService.getConfig("order_autocancel_time", 0.5f);
        int second = NumberUtil.mul(orderAutocancelTime, 60, 60).intValue();
        long time = DateUtil.offsetSecond(new Date(), -second).getTime();
        queryWrapper.lt("create_time", time);

        return Convert.toList(String.class, findKey(queryWrapper));
    }

    @Override
    public List<String> getAutoFinishOrderId() {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_state_id", StateCode.ORDER_STATE_SHIPPED, StateCode.ORDER_STATE_RECEIVED);
        Float orderAutofinishTime = configBaseService.getConfig("order_autofinish_time", 10f);
        int second = NumberUtil.mul(orderAutofinishTime, 24, 60, 60).intValue();
        long time = DateUtil.offsetSecond(new Date(), -second).getTime();
        queryWrapper.lt("update_time", time);
        return Convert.toList(String.class, findKey(queryWrapper));
    }
}
