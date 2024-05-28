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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.service.UserInfoService;
import com.suisung.shopsuite.cms.model.entity.ArticleBase;
import com.suisung.shopsuite.cms.model.entity.ArticleCategory;
import com.suisung.shopsuite.cms.model.entity.ArticleComment;
import com.suisung.shopsuite.cms.model.entity.ArticleTag;
import com.suisung.shopsuite.cms.model.req.ArticleBaseListReq;
import com.suisung.shopsuite.cms.model.res.ArticleBaseRes;
import com.suisung.shopsuite.cms.repository.ArticleBaseRepository;
import com.suisung.shopsuite.cms.service.ArticleBaseService;
import com.suisung.shopsuite.cms.service.ArticleCategoryService;
import com.suisung.shopsuite.cms.service.ArticleCommentService;
import com.suisung.shopsuite.cms.service.ArticleTagService;
import com.suisung.shopsuite.common.consts.ConstantCms;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 文章内容 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Service
public class ArticleBaseServiceImpl extends BaseServiceImpl<ArticleBaseRepository, ArticleBase, ArticleBaseListReq> implements ArticleBaseService {

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private UserInfoService userInfoService;


    @Override
    public IPage<ArticleBaseRes> getList(ArticleBaseListReq articleBaseListReq) {
        IPage<ArticleBaseRes> articleBaseResPage = new Page<>();
        articleBaseListReq.setSidx("article_sort");
        IPage<ArticleBase> lists = lists(articleBaseListReq);

        List<ArticleBase> records = lists.getRecords();
        List<ArticleBaseRes> resList = records.stream().map(item -> {
            ArticleBaseRes articleBaseRes = new ArticleBaseRes();
            BeanUtils.copyProperties(item, articleBaseRes);

            return articleBaseRes;
        }).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(resList)) {
            resList.forEach(item -> {
                String articleTags = item.getArticleTags();
                if (StrUtil.isNotEmpty(articleTags)) {
                    List<String> arrayToList = Convert.toList(String.class, articleTags);
                    item.setArticleTagList(articleTagService.gets(arrayToList));
                }
            });
        }

        articleBaseResPage.setRecords(resList);
        articleBaseResPage.setPages(lists.getPages());
        articleBaseResPage.setSize(lists.getSize());
        articleBaseResPage.setCurrent(lists.getCurrent());
        articleBaseResPage.setTotal(lists.getTotal());

        return articleBaseResPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addArticleBase(ArticleBase articleBase) {
        boolean result = add(articleBase);

        if (result) {
            //判断所属分类，为分类内容数量+1
            ArticleCategory articleCategory = articleCategoryService.get(articleBase.getCategoryId());

            if (articleCategory != null) {
                articleCategory.setCategoryCount(articleCategory.getCategoryCount() + 1);
                articleCategoryService.edit(articleCategory);
            }

            //文章标签, 为每个标签内容数量+1
            manageArticleTags(articleBase.getArticleTags(), ConstantCms.ADD);
        }

        return result;
    }


    private void manageArticleTags(String articleTags, String type) {

        if (articleTags != null) {
            List<String> arrayToList = Convert.toList(String.class, articleTags);
            List<ArticleTag> articleTagList = articleTagService.gets(arrayToList);

            if (CollectionUtil.isNotEmpty(articleTagList)) {
                for (ArticleTag articleTag : articleTagList) {

                    if (type.equals(ConstantCms.ADD)) {
                        articleTag.setTagCount(articleTag.getTagCount() + 1);
                    }

                    if (type.equals(ConstantCms.SUBTRACT)) {
                        //当大于0才减去
                        articleTag.setTagCount(articleTag.getTagCount() > 0 ? articleTag.getTagCount() - 1 : 0);
                    }

                    articleTagService.edit(articleTag);
                }

            }

        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editArticleBase(ArticleBase articleBase) {
        ArticleBase byId = get(articleBase.getArticleId());

        if (byId == null) {
            throw new BusinessException(__("该数据不存在"));
        }

        Integer categoryId = byId.getCategoryId();
        String articleTags = byId.getArticleTags();
        boolean result = edit(articleBase);

        if (result) {

            //所属分类，判断是否更改
            if (!categoryId.equals(articleBase.getCategoryId())) {

                //上一个所属分类内容数量-1
                ArticleCategory lastArticleCategory = articleCategoryService.get(categoryId);

                if (lastArticleCategory != null) {
                    //当大于0才减去
                    lastArticleCategory.setCategoryCount(lastArticleCategory.getCategoryCount() > 0 ? lastArticleCategory.getCategoryCount() - 1 : 0);
                    articleCategoryService.edit(lastArticleCategory);
                }

                //最新所属分类内容数量+1
                ArticleCategory newArticleCategory = articleCategoryService.get(articleBase.getCategoryId());

                if (newArticleCategory != null) {
                    newArticleCategory.setCategoryCount(newArticleCategory.getCategoryCount() + 1);
                    articleCategoryService.edit(newArticleCategory);
                }

            }
            //文章标签 判断是否更改

            if (articleTags == null && articleBase.getArticleTags() != null) {
                manageArticleTags(articleBase.getArticleTags(), ConstantCms.ADD);
            }

            if (articleTags != null && articleBase.getArticleTags() == null) {
                manageArticleTags(articleTags, ConstantCms.SUBTRACT);
            }

            if (articleTags != null && articleBase.getArticleTags() != null && !articleTags.equals(articleBase.getArticleTags())) {
                manageArticleTags(articleTags, ConstantCms.SUBTRACT);
                manageArticleTags(articleBase.getArticleTags(), ConstantCms.ADD);
            }

        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Integer articleId) {
        ArticleBase articleBase = get(articleId);

        if (articleBase == null) {
            throw new BusinessException(__("该数据不存在"));
        }

        boolean result = remove(articleId);

        if (result) {
            articleBaseRelevance(articleBase);
        }

        return result;
    }

    private void articleBaseRelevance(ArticleBase articleBase) {

        if (articleBase != null) {
            //所属分类内容数量-1
            ArticleCategory newArticleCategory = articleCategoryService.get(articleBase.getCategoryId());

            if (newArticleCategory != null) {
                newArticleCategory.setCategoryCount(newArticleCategory.getCategoryCount() > 0 ? newArticleCategory.getCategoryCount() - 1 : 0);
                articleCategoryService.edit(newArticleCategory);
            }

            //文章标签, 为每个标签内容数量-1
            manageArticleTags(articleBase.getArticleTags(), ConstantCms.SUBTRACT);
            // 删除该文章下所有评论
            QueryWrapper<ArticleComment> articleCommentQueryWrapper = new QueryWrapper<>();
            articleCommentQueryWrapper.eq("article_id", articleBase.getArticleId());
            List<Serializable> keys = articleCommentService.findKey(articleCommentQueryWrapper);

            if (!CollectionUtils.isEmpty(keys)) {
                articleCommentService.remove(keys);
            }

        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeBatch(List<Integer> articleIds) {
        List<ArticleBase> articleBaseList = gets(articleIds);

        if (CollectionUtils.isEmpty(articleBaseList)) {
            throw new BusinessException(__("数据不存在"));
        }

        boolean result = remove(articleIds);

        if (result) {
            for (ArticleBase articleBase : articleBaseList) {
                articleBaseRelevance(articleBase);
            }
        }

        return result;
    }

    @Override
    public IPage<ArticleBaseRes> getArticleBaseList(ArticleBaseListReq articleBaseListReq) {
        IPage<ArticleBaseRes> articleBaseResPage = new Page<>();
        QueryWrapper<ArticleBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_status", 1);
        queryWrapper.orderByAsc("article_sort");
        queryWrapper.orderByDesc("article_id");

        if (CheckUtil.isNotEmpty(articleBaseListReq.getCategoryId())) {
            Integer categoryId = articleBaseListReq.getCategoryId();
            List<Integer> categoryIds = articleCategoryService.getCategoryIdByParentId(categoryId, true);
            if (CollUtil.isNotEmpty(categoryIds)) {
                queryWrapper.in("category_id", categoryIds);
            } else {
                queryWrapper.in("category_id", categoryId);
            }
        }

        if (CheckUtil.isNotEmpty(articleBaseListReq.getArticleType())) {
            queryWrapper.eq("article_type", articleBaseListReq.getArticleType());
        }

        if (CheckUtil.isNotEmpty(articleBaseListReq.getUserId())) {
            queryWrapper.eq("user_id", articleBaseListReq.getUserId());
        }

        if (CheckUtil.isNotEmpty(articleBaseListReq.getTagId())) {
            Integer tagId = articleBaseListReq.getTagId();
            queryWrapper.apply(tagId != null, "FIND_IN_SET ('" + tagId + "', article_tags )");
        }
        IPage<ArticleBase> articleBaseIPage = lists(queryWrapper, articleBaseListReq.getPage(), articleBaseListReq.getSize());

        if (articleBaseIPage != null && CollectionUtil.isNotEmpty(articleBaseIPage.getRecords())) {
            List<ArticleBase> articleList = articleBaseIPage.getRecords();
            BeanUtils.copyProperties(articleBaseIPage, articleBaseResPage);
            List<ArticleBaseRes> baseResList = new ArrayList<>();
            //用户昵称
            List<Integer> userIds = articleList.stream().map(ArticleBase::getUserId).distinct().collect(Collectors.toList());
            List<UserInfo> userInfos = userInfoService.gets(userIds);
            Map<Integer, String> nickNameMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(userInfos)) {
                nickNameMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getUserNickname, (k1, k2) -> k1));
            }

            for (ArticleBase articleBase : articleList) {
                ArticleBaseRes articleBaseRes = new ArticleBaseRes();
                BeanUtils.copyProperties(articleBase, articleBaseRes);

                if (!nickNameMap.isEmpty()) {
                    articleBaseRes.setUserNickname(nickNameMap.get(articleBase.getUserId()));
                }
                String articleExcerpt = articleBase.getArticleExcerpt();

                if (StrUtil.isBlank(articleExcerpt)) {
                    String articleContent = ObjectUtil.defaultIfNull(articleBase.getArticleContent(), "");
                    String expected = StrUtil.subPre(articleContent, 120) + "......";
                    articleBaseRes.setArticleExcerpt(expected);
                }
                baseResList.add(articleBaseRes);
            }
            articleBaseResPage.setRecords(baseResList);
        }

        return articleBaseResPage;
    }

    @Override
    public ArticleBaseRes getArticleBase(Integer articleId) {
        ArticleBaseRes articleBaseRes = new ArticleBaseRes();
        ArticleBase articleBase = get(articleId);

        if (articleBase != null) {
            BeanUtils.copyProperties(articleBase, articleBaseRes);
            UserInfo userInfo = userInfoService.get(articleBase.getUserId());

            if (userInfo != null) {
                articleBaseRes.setUserNickname(userInfo.getUserNickname());
            }
        }

        return articleBaseRes;
    }

    @Cacheable(value = {"pcFooterArticle"})
    public List<Map> getPcFooterArticle() {
        QueryWrapper<ArticleCategory> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("category_is_leaf", 1);
        List<ArticleCategory> articleCategoryList = articleCategoryService.find(categoryQueryWrapper);
        List<Integer> idList = articleCategoryList.stream().map(ArticleCategory::getCategoryId).collect(Collectors.toList());

        QueryWrapper<ArticleBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id", idList);
        queryWrapper.eq("article_status", 1);
        queryWrapper.eq("article_type", 1);
        List<ArticleBase> cmsArticleBaseList = find(queryWrapper);

        List<Map> resultList = new ArrayList<>();
        for (ArticleCategory cmsArticleCategory : articleCategoryList) {
            List articleList = new ArrayList();
            HashMap<Object, Object> map = new HashMap();
            for (ArticleBase cmsArticleBase : cmsArticleBaseList) {
                if (cmsArticleCategory.getCategoryId().equals(cmsArticleBase.getCategoryId())) {
                    articleList.add(cmsArticleBase);
                }
            }
            if (CollUtil.isNotEmpty(articleList)) {
                map.put("category", cmsArticleCategory);
                map.put("articleList", articleList);
                resultList.add(map);
            }
        }

        return resultList;
    }
}
