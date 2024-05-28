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

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pay.model.entity.UserPay;
import com.suisung.shopsuite.pay.model.req.UserPayListReq;
import com.suisung.shopsuite.pay.repository.UserPayRepository;
import com.suisung.shopsuite.pay.service.UserPayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 用户基础信息表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-31
 */
@Service
public class UserPayServiceImpl extends BaseServiceImpl<UserPayRepository, UserPay, UserPayListReq> implements UserPayService {


    @Override
    public UserPay getPayPasswd(Integer userId) {
        UserPay userPay = get(userId);

        if (userPay == null) {
            throw new BusinessException(ResultCode.PAY_PWD_FAILED);
        }

        return userPay;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean changePayPassword(String oldPayPassword, String newPayPassword, String payPassword, Integer userId) {
        UserPay userPay = get(userId);
        boolean result = false;

        if (userPay == null) {

            if (StrUtil.isEmpty(newPayPassword) || StrUtil.isEmpty(payPassword)) {
                throw new BusinessException(__("密码不能为空！"));
            }

            if (!payPassword.equals(newPayPassword)) {
                throw new BusinessException(__("两次输入密码不一致！"));
            }
            UserPay pay = new UserPay();
            String userPaySalt = IdUtil.simpleUUID();
            pay.setUserId(userId);
            pay.setUserPayPasswd(SecureUtil.md5(userPaySalt + SecureUtil.md5(newPayPassword)));
            pay.setUserPaySalt(userPaySalt);
            result = add(pay);
        } else {
            //修改密码
            String oldSalt = userPay.getUserPaySalt();
            //判断旧密码是否正确
            String oldPassword = SecureUtil.md5(oldSalt + SecureUtil.md5(oldPayPassword));

            if (!userPay.getUserPayPasswd().equals(oldPassword)) {
                throw new BusinessException(__("原支付密码不正确！"));
            }
            //新密码加密
            String newSalt = IdUtil.simpleUUID();
            newPayPassword = SecureUtil.md5(newSalt + SecureUtil.md5(newPayPassword));

            if (newPayPassword.equals(oldPassword)) {
                throw new BusinessException(__("新密码不能与原密码相同！"));
            }
            userPay.setUserPayPasswd(newPayPassword);
            userPay.setUserPaySalt(newSalt);
            result = edit(userPay);
        }

        return result;
    }
}
