package com.suisung.shopsuite.cms.controller.front;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.cms.model.entity.ArticleCategory;
import com.suisung.shopsuite.cms.model.req.ArticleBaseListReq;
import com.suisung.shopsuite.cms.model.req.ArticleCategoryListReq;
import com.suisung.shopsuite.cms.model.res.ArticleBaseRes;
import com.suisung.shopsuite.cms.service.ArticleBaseService;
import com.suisung.shopsuite.cms.service.ArticleCategoryService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "文章内容-移动端")
@RestController
@RequestMapping("/front/cms/articleBase")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleBaseService articleBaseService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @ApiOperation(value = "文章列表", notes = "文章列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ArticleBaseRes>> list(ArticleBaseListReq articleBaseListReq) {
        IPage<ArticleBaseRes> pageList = articleBaseService.getArticleBaseList(articleBaseListReq);

        return success(pageList);
    }

    @ApiOperation(value = "文章详情", notes = "文章列表")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<ArticleBaseRes> get(@RequestParam("article_id") Integer articleId) {
        ArticleBaseRes articleBaseRes = articleBaseService.getArticleBase(articleId);

        return success(articleBaseRes);
    }

    @ApiOperation(value = "文章分类-分页列表查询", notes = "文章分类-分页列表查询")
    @RequestMapping(value = "/listCategory", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ArticleCategory>> list(ArticleCategoryListReq articleCategoryListReq) {
        IPage<ArticleCategory> pageList = articleCategoryService.lists(articleCategoryListReq);

        return success(pageList);
    }
}
