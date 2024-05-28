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
package com.suisung.shopsuite.cms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.cms.model.entity.ArticleBase;
import com.suisung.shopsuite.cms.model.entity.ArticleCategory;
import com.suisung.shopsuite.cms.model.req.ArticleCategoryListReq;
import com.suisung.shopsuite.cms.model.res.ArticleCategoryRes;
import com.suisung.shopsuite.cms.repository.ArticleBaseRepository;
import com.suisung.shopsuite.cms.repository.ArticleCategoryRepository;
import com.suisung.shopsuite.cms.service.ArticleCategoryService;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 文章分类 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategoryRepository, ArticleCategory, ArticleCategoryListReq> implements ArticleCategoryService {

    @Autowired
    private ArticleBaseRepository categoryBaseRepository;


    @Override
    public List<ArticleCategoryRes> tree(ArticleCategoryListReq articleCategoryListReq) {
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotEmpty(articleCategoryListReq.getCategoryName())) {
            queryWrapper.like("category_name", articleCategoryListReq.getCategoryName());
        }

        queryWrapper.orderByAsc("category_order");
        List<ArticleCategory> articleCategories = find(queryWrapper);
        List<ArticleCategoryRes> categoryResList = articleCategories.stream().map(item -> {
            ArticleCategoryRes articleCategoryRes = new ArticleCategoryRes();
            BeanUtils.copyProperties(item, articleCategoryRes);

            return articleCategoryRes;
        }).collect(Collectors.toList());

        return CommonUtil.toTreeData(categoryResList, 0,
                ArticleCategoryRes::getCategoryParentId,
                ArticleCategoryRes::getCategoryId,
                ArticleCategoryRes::setChildren);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByCategoryId(Integer categoryId) {
        //查询该分类下有没有子分类
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_parent_id", categoryId);
        long categoryNum = count(queryWrapper);

        if (categoryNum > 0) {
            throw new BusinessException(__("该分类下有子分类"));
        }

        //查询该分类下是否有文章引用
        QueryWrapper<ArticleBase> articleBaseQueryWrapper = new QueryWrapper<>();
        articleBaseQueryWrapper.eq("category_id", categoryId);
        long articleNum = categoryBaseRepository.count(articleBaseQueryWrapper);

        if (articleNum > 0) {
            throw new BusinessException(__("该分类下有文章引用"));
        }

        return remove(categoryId);
    }

    @Override
    public List<Integer> getCategoryIdByParentId(Integer categoryParentId, boolean recursive) {
        QueryWrapper<ArticleCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("category_parent_id", categoryParentId);
        List<Serializable> serializableList = findKey(wrapper);
        List<Integer> categoryIds = Convert.toList(Integer.class, serializableList);

        if (recursive && CollectionUtil.isNotEmpty(categoryIds)) {
            List<Integer> rs = getCategoryIdByParentIds(categoryIds, recursive);
            categoryIds.addAll(rs);
        }

        return categoryIds;
    }

    private List<Integer> getCategoryIdByParentIds(List<Integer> categoryParentIds, boolean recursive) {
        QueryWrapper<ArticleCategory> wrapper = new QueryWrapper<>();
        wrapper.in("category_parent_id", categoryParentIds);
        List<Serializable> serializableList = findKey(wrapper);
        return Convert.toList(Integer.class, serializableList);
    }

}
