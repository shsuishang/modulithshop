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
package com.suisung.shopsuite.marketing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.service.IBaseService;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.entity.ActivityItem;
import com.suisung.shopsuite.marketing.model.req.ActivityBaseEditReq;
import com.suisung.shopsuite.marketing.model.req.ActivityBaseListReq;
import com.suisung.shopsuite.marketing.model.req.ActivityItemBatchPriceEditReq;
import com.suisung.shopsuite.marketing.model.res.ActivityBaseRes;
import com.suisung.shopsuite.marketing.model.res.ActivityItemRes;

import java.util.List;

/**
 * <p>
 * 活动表-通过插件实现	  服务类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
public interface ActivityBaseService extends IBaseService<ActivityBase, ActivityBaseListReq> {

    List<ActivityBase> fixActivityData(List<ActivityBase> activityBaseList);

    boolean editActivityBase(Integer activity_id, ActivityBase data);

    /**
     * 活动表-优惠券
     *
     * @param activityBaseListReq
     * @return
     */
    IPage<ActivityBaseRes> voucherList(ActivityBaseListReq activityBaseListReq);

    /**
     * 活动详情，修改汇率
     *
     * @param activityIds
     * @param currencyId
     * @return
     */
    List<ActivityBaseRes> getActivityBases(List<Integer> activityIds, Integer currencyId);

    /**
     * 活动表 - 通过插件实现
     *
     * @param activityBaseListReq
     * @return
     */
    IPage<ActivityBaseRes> getList(ActivityBaseListReq activityBaseListReq);

    /**
     * 活动表 - 添加
     *
     * @param activityBase
     * @return
     */
    boolean addActivityBase(ActivityBase activityBase);

    /**
     * 活动表 - 编辑
     *
     * @param activityBase
     * @return
     */
    boolean updateActivityBase(ActivityBase activityBase);

    /**
     * 活动表 - 删除
     *
     * @param activityId
     * @param storeId
     * @return
     */
    boolean removeActivity(Integer activityId, Integer storeId);

    /**
     * 活动商品列表
     *
     * @param activityId
     * @return
     */
    List<ActivityItemRes> getActivityBuyItems(Integer activityId);

    /**
     * 活动-添加商品
     *
     * @param activityBaseEditReq
     * @return
     */
    boolean addActivityBuyItems(ActivityBaseEditReq activityBaseEditReq);

    /**
     * 活动-删除商品
     *
     * @param activityBaseEditReq
     * @return
     */
    boolean removeActivityBuyItems(ActivityBaseEditReq activityBaseEditReq);

    /**
     * 活动-修改商品活动价格
     *
     * @param activityItem
     * @return
     */
    boolean editActivityItem(ActivityItem activityItem);

    /**
     * 活动-统一折扣修改价格
     *
     * @param activityItemBatchPriceEditReq
     * @return
     */
    boolean editBatchPrice(ActivityItemBatchPriceEditReq activityItemBatchPriceEditReq);
}
