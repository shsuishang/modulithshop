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
package com.suisung.shopsuite.pay.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.vo.ExperienceVo;
import com.suisung.shopsuite.core.web.service.IBaseService;
import com.suisung.shopsuite.pay.model.entity.UserExpHistory;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.model.req.UserExpHistoryListReq;
import com.suisung.shopsuite.pay.model.req.UserResourceListReq;
import com.suisung.shopsuite.pay.model.res.SignInfoRes;
import com.suisung.shopsuite.pay.model.res.UserResourceRes;
import com.suisung.shopsuite.pay.model.vo.MoneyVo;
import com.suisung.shopsuite.pay.model.vo.UserPointsVo;

import java.math.BigDecimal;

/**
 * <p>
 * 用户资源表-资金账户表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
public interface UserResourceService extends IBaseService<UserResource, UserResourceListReq> {

    /**
     * 获取用户资源列表
     * @param userResourceListReq
     * @return
     */
//    IPage<UserResource> getList(UserResourceListReq userResourceListReq);

    /**
     * 用户注册积分
     *
     * @param userId
     * @return
     */
    boolean initUserPoints(Integer userId);

    /**
     * 初始化用户经验等级
     *
     * @param userId
     * @return
     */
    boolean initUserExperience(Integer userId);

    /**
     * 操作用户经验
     *
     * @param experienceVo
     * @return
     */
    boolean experience(ExperienceVo experienceVo);

    /**
     * 获取签到基本信息
     *
     * @param userId
     * @return
     */
    SignInfoRes getSignInfo(Integer userId);

    /**
     * 签到
     *
     * @return
     */
    void sign(Integer userId);

    /**
     * 当天是否签到
     *
     * @return
     */
    Boolean getSignState(Integer userId);

    /**
     * 修改资金
     *
     * @param moneyVo
     * @return
     */
    boolean updateUserMoney(MoneyVo moneyVo);

    /**
     * 修改积分
     *
     * @param userPointsVo
     * @return
     */
    boolean updatePoints(UserPointsVo userPointsVo);

    /**
     * @param userId
     * @param money
     * @param tradeTypeDeposit
     * @param desc
     * @param paymentTypeId
     * @param withdrawFee
     * @param orderId
     * @return
     */
    boolean money(Integer userId, BigDecimal money, Integer tradeTypeDeposit, String desc, Integer paymentTypeId, BigDecimal withdrawFee, String orderId);

    /**
     * 用户经验列表
     *
     * @param userExpHistoryListReq
     * @return
     */
    IPage<UserExpHistory> listsExp(UserExpHistoryListReq userExpHistoryListReq);

    /**
     * 获取用户资源信息
     *
     * @param userId
     * @return
     */
    UserResourceRes resource(Integer userId);
}
