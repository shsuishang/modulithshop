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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pay.model.entity.BaseBank;
import com.suisung.shopsuite.pay.model.entity.UserBankCard;
import com.suisung.shopsuite.pay.model.req.UserBankCardListReq;
import com.suisung.shopsuite.pay.model.res.BankRes;
import com.suisung.shopsuite.pay.repository.BaseBankRepository;
import com.suisung.shopsuite.pay.repository.UserBankCardRepository;
import com.suisung.shopsuite.pay.service.UserBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 结算账户表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-28
 */
@Service
public class UserBankCardServiceImpl extends BaseServiceImpl<UserBankCardRepository, UserBankCard, UserBankCardListReq> implements UserBankCardService {

    @Autowired
    private BaseBankRepository bankRepository;

    @Autowired
    private UserBankCardRepository userBankCardRepository;


    @Override
    public BankRes getList(Integer userId) {
        BankRes bankRes = new BankRes();
        //结算账户表信息
        QueryWrapper<UserBankCard> userBankCardQueryWrapper = new QueryWrapper<>();
        userBankCardQueryWrapper.eq("user_id", userId);
        List<UserBankCard> userBankCards = find(userBankCardQueryWrapper);
        bankRes.setUserBankList(userBankCards);

        //结算银行记录
        QueryWrapper<BaseBank> baseBankQueryWrapper = new QueryWrapper<>();
        baseBankQueryWrapper.eq("bank_enable", 1)
                .orderByAsc("bank_order");
        List<BaseBank> baseBanks = bankRepository.find(baseBankQueryWrapper);
        bankRes.setBankList(baseBanks);

        return bankRes;
    }

    @Override
    public UserBankCard getUserBank(Integer userBankId, Integer userId) {
        QueryWrapper<UserBankCard> userBankCardQueryWrapper = new QueryWrapper<>();
        userBankCardQueryWrapper.eq("user_id", userId)
                .eq("user_bank_id", userBankId);
        List<UserBankCard> userBankCards = find(userBankCardQueryWrapper);

        return CollectionUtil.isNotEmpty(userBankCards) ? userBankCards.get(0) : new UserBankCard();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addOrEditUserBank(UserBankCard userBankCard) {
        userBankCard.setUserIntl("");
        userBankCard.setUserBankAmountMoney(BigDecimal.ZERO);
        userBankCard.setUserBankBeginDate(0);
        userBankCard.setUserBankDefault(false);

        //银行卡去重验证
        QueryWrapper<UserBankCard> userBankCardQueryWrapper = new QueryWrapper<>();
        userBankCardQueryWrapper.eq("bank_id", userBankCard.getBankId())
                .eq("user_bank_card_code", userBankCard.getUserBankCardCode());
        List<Serializable> keys = findKey(userBankCardQueryWrapper);
        List<Integer> userBankIdList = Convert.toList(Integer.class, keys);
        Integer userBankId = userBankCard.getUserBankId();


        if (CollectionUtil.isEmpty(userBankIdList) || (userBankId != null && userBankIdList.contains(userBankId))) {
            return userBankCardRepository.saveOrUpdate(userBankCard);
        } else {
            throw new BusinessException(__("此卡已绑定"));
        }
    }
}
