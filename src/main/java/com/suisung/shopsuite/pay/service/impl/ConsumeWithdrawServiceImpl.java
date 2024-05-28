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
package com.suisung.shopsuite.pay.service.impl;

import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pay.model.entity.ConsumeWithdraw;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.model.req.ConsumeWithdrawListReq;
import com.suisung.shopsuite.pay.repository.ConsumeWithdrawRepository;
import com.suisung.shopsuite.pay.service.ConsumeWithdrawService;
import com.suisung.shopsuite.pay.service.UserResourceService;
import com.suisung.shopsuite.trade.service.OrderInfoService;
import com.suisung.shopsuite.trade.service.OrderReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 提现申请表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-09-20
 */
@Service
public class ConsumeWithdrawServiceImpl extends BaseServiceImpl<ConsumeWithdrawRepository, ConsumeWithdraw, ConsumeWithdrawListReq> implements ConsumeWithdrawService {

    @Autowired
    private UserResourceService userResourceService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderReturnService orderReturnService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editWithdraw(ConsumeWithdraw consumeWithdraw) {
        Integer withdrawId = consumeWithdraw.getWithdrawId();

        ConsumeWithdraw withdraw = get(withdrawId);

        if (withdraw == null) {
            throw new BusinessException(__("该数据不存在！"));
        }

        if (withdraw.getWithdrawState() != 0) {
            throw new BusinessException(__("现提现状态不是申请中！"));
        }
        withdraw.setWithdrawState(consumeWithdraw.getWithdrawState());

        if (StrUtil.isNotEmpty(consumeWithdraw.getWithdrawBankflow())) {
            withdraw.setWithdrawBankflow(consumeWithdraw.getWithdrawBankflow());
        }

        if (StrUtil.isNotEmpty(consumeWithdraw.getWithdrawDesc())) {
            withdraw.setWithdrawDesc(consumeWithdraw.getWithdrawDesc());
        }

        if (consumeWithdraw.getWithdrawOpertime() != null) {
            withdraw.setWithdrawOpertime(consumeWithdraw.getWithdrawOpertime());
        }
        withdraw.setWithdrawUserId(consumeWithdraw.getWithdrawUserId());
        Integer withdrawMode = withdraw.getWithdrawMode();
        if (withdrawMode == 0) {
            //余额提现
            doWithdraw(withdraw);
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doWithdraw(ConsumeWithdraw withdraw) {
        String orderId = withdraw.getOrderId();
        String returnId = withdraw.getReturnId();
        boolean orderIsSettlemented = false;

        if (withdraw.getWithdrawState() == 1) {
            // 执行提现审核通过操作
            userResourceService.money(withdraw.getUserId(), withdraw.getWithdrawAmount().negate(), StateCode.TRADE_TYPE_WITHDRAW, "", StateCode.PAYMENT_TYPE_OFFLINE, withdraw.getWithdrawFee(), null);
            orderIsSettlemented = true;
        } else {
            UserResource userResource = userResourceService.get(withdraw.getUserId());

            if (userResource == null) {
                throw new BusinessException(__("该用户资源不存在！"));
            }
            BigDecimal userMoneyFrozen = userResource.getUserMoneyFrozen();
            BigDecimal userMoney = userResource.getUserMoney();
            userResource.setUserMoney(userMoney.add(withdraw.getWithdrawAmount()));
            userResource.setUserMoneyFrozen(userMoneyFrozen.subtract(withdraw.getWithdrawAmount()));

            if (!userResourceService.edit(userResource)) {
                throw new BusinessException(__("修改用户资源表失败！"));
            }
        }

        if (StrUtil.isNotBlank(orderId)) {
            if (!orderInfoService.saveOrderInfo(orderId, orderIsSettlemented)) {
                throw new BusinessException(__("保存订单信息表失败！"));
            }
        }

        if (StrUtil.isNotBlank(returnId)) {
            if (!orderReturnService.saveOrderReturn(returnId, orderIsSettlemented)) {
                throw new BusinessException(__("保存退款退货表失败！"));
            }
        }

        if (!edit(withdraw)) {
            throw new BusinessException(__("修改提现申请表失败！"));
        }
    }
}
