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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.entity.ActivityItem;
import com.suisung.shopsuite.marketing.model.req.ActivityItemListReq;
import com.suisung.shopsuite.marketing.model.vo.ActivityInfoVo;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.marketing.repository.ActivityItemRepository;
import com.suisung.shopsuite.marketing.service.ActivityItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 参与活动商品表-用户筛选计算 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-18
 */
@Service
public class ActivityItemServiceImpl extends BaseServiceImpl<ActivityItemRepository, ActivityItem, ActivityItemListReq> implements ActivityItemService {
    @Autowired
    private ActivityBaseRepository activityBaseRepository;

    @Override
    public List<ActivityInfoVo> getActivityInfo(List<Long> itemIds) {
        if (CollUtil.isEmpty(itemIds)) {
            return new ArrayList<>();
        }

        // 排他性活动信息
        List<ActivityItem> activityItemList = repository.find(new QueryWrapper<ActivityItem>().in("item_id", itemIds).in("activity_item_state", Arrays.asList(StateCode.ACTIVITY_STATE_NORMAL)));
        List<ActivityInfoVo> output = BeanUtil.copyToList(activityItemList, ActivityInfoVo.class);

        List<Long> activityItemIds = CommonUtil.column(output, ActivityInfoVo::getItemId);

        if (CollUtil.isNotEmpty(output)) {
            List<Integer> activityIds = CommonUtil.column(output, ActivityInfoVo::getActivityId);
            List<ActivityBase> activityBaseList = activityBaseRepository.gets(activityIds);

            for (ActivityInfoVo it : output) {
                ActivityBase itemVo = activityBaseList.stream().filter(s -> s.getActivityId().equals(it.getActivityId())).findFirst().orElse(new ActivityBase());
                it.setActivityBase(itemVo);
            }
        }

        return output;
    }
}
