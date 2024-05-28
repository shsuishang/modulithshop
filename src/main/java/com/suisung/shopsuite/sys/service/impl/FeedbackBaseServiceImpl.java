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
package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.FeedbackBase;
import com.suisung.shopsuite.sys.model.entity.FeedbackCategory;
import com.suisung.shopsuite.sys.model.entity.FeedbackType;
import com.suisung.shopsuite.sys.model.req.FeedbackBaseListReq;
import com.suisung.shopsuite.sys.model.res.FeedbackTypeRes;
import com.suisung.shopsuite.sys.repository.FeedbackBaseRepository;
import com.suisung.shopsuite.sys.repository.FeedbackCategoryRepository;
import com.suisung.shopsuite.sys.repository.FeedbackTypeRepository;
import com.suisung.shopsuite.sys.service.FeedbackBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台反馈表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Service
public class FeedbackBaseServiceImpl extends BaseServiceImpl<FeedbackBaseRepository, FeedbackBase, FeedbackBaseListReq> implements FeedbackBaseService {

    @Autowired
    private FeedbackCategoryRepository categoryRepository;

    @Autowired
    private FeedbackTypeRepository typeRepository;


    @Override
    public List<FeedbackTypeRes> getCategory() {
        List<FeedbackTypeRes> feedbackTypeResList = new ArrayList<>();
        //平台反馈类型表
        QueryWrapper<FeedbackType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("feedback_type_enable", 1);
        List<FeedbackType> feedbackTypes = typeRepository.find(typeQueryWrapper);

        if (CollectionUtil.isEmpty(feedbackTypes)) {
            return feedbackTypeResList;
        }
        List<Integer> typeIds = feedbackTypes.stream().map(FeedbackType::getFeedbackTypeId).collect(Collectors.toList());
        //反馈分类
        QueryWrapper<FeedbackCategory> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.in("feedback_type_id", typeIds)
                .eq("feedback_category_enable", 1);
        List<FeedbackCategory> feedbackCategories = categoryRepository.find(categoryQueryWrapper);
        for (FeedbackType feedbackType : feedbackTypes) {
            FeedbackTypeRes feedbackTypeRes = new FeedbackTypeRes();
            BeanUtils.copyProperties(feedbackType, feedbackTypeRes);

            if (CollectionUtil.isNotEmpty(feedbackCategories)) {
                List<FeedbackCategory> feedbackCategoryList = feedbackCategories.stream().filter(item -> item.getFeedbackTypeId().equals(feedbackType.getFeedbackTypeId())).collect(Collectors.toList());
                feedbackTypeRes.setRows(feedbackCategoryList);
            }
            feedbackTypeResList.add(feedbackTypeRes);
        }

        return feedbackTypeResList;
    }
}
