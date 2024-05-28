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
package com.suisung.shopsuite.cms.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.cms.model.entity.ArticleBase;
import com.suisung.shopsuite.cms.model.req.ArticleBaseAddReq;
import com.suisung.shopsuite.cms.model.req.ArticleBaseEditReq;
import com.suisung.shopsuite.cms.model.req.ArticleBaseListReq;
import com.suisung.shopsuite.cms.model.req.ArticleBaseStateEditReq;
import com.suisung.shopsuite.cms.model.res.ArticleBaseRes;
import com.suisung.shopsuite.cms.service.ArticleBaseService;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.BaseOrder;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章内容 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Api(tags = "文章内容")
@RestController
@RequestMapping("/manage/cms/articleBase")
public class ArticleBaseController extends BaseController {
    @Autowired
    private ArticleBaseService articleBaseService;

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/list')")
    @ApiOperation(value = "文章内容-分页列表查询", notes = "文章内容-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ArticleBaseRes>> list(ArticleBaseListReq articleBaseListReq) {
        //额外排序
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setSidx("article_id");
        baseOrder.setSort(Constants.ORDER_BY_ASC);

        List<BaseOrder> order = new ArrayList<>();
        order.add(baseOrder);
        articleBaseListReq.setOrder(order);

        IPage<ArticleBaseRes> pageList = articleBaseService.getList(articleBaseListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/detail')")
    @ApiOperation(value = "文章内容-通过article_id查询", notes = "文章内容-通过article_id查询")
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public CommonRes<ArticleBase> get(@PathVariable Integer articleId) {
        ArticleBase articleBase = articleBaseService.get(articleId);

        return success(articleBase);
    }

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/add')")
    @ApiOperation(value = "文章内容-添加", notes = "文章内容-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(ArticleBaseAddReq articleBaseAddReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        ArticleBase articleBase = BeanUtil.copyProperties(articleBaseAddReq, ArticleBase.class);
        articleBase.setUserId(userId);
        boolean success = articleBaseService.addArticleBase(articleBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/edit')")
    @ApiOperation(value = "文章内容-编辑", notes = "文章内容-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(ArticleBaseEditReq articleBaseEditReq) {
        ArticleBase articleBase = BeanUtil.copyProperties(articleBaseEditReq, ArticleBase.class);
        boolean success = articleBaseService.editArticleBase(articleBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/editState')")
    @ApiOperation(value = "文章内容-修改状态", notes = "文章内容-修改状态")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(ArticleBaseStateEditReq articleBaseStateEditReq) {
        ArticleBase articleBase = BeanUtil.copyProperties(articleBaseStateEditReq, ArticleBase.class);
        boolean success = articleBaseService.editArticleBase(articleBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/remove')")
    @ApiOperation(value = "文章内容-通过article_id删除", notes = "文章内容-通过article_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("article_id") Integer articleId) {
        boolean success = articleBaseService.removeById(articleId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/cms/articleBase/removeBatch')")
    @ApiOperation(value = "文章内容-批量删除", notes = "文章内容-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("article_id") String articleIds) {
        boolean success = articleBaseService.removeBatch(Convert.toList(Integer.class, articleIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

