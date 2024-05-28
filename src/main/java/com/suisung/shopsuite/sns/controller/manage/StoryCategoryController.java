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
package com.suisung.shopsuite.sns.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sns.model.entity.StoryCategory;
import com.suisung.shopsuite.sns.model.req.StoryCategoryAddReq;
import com.suisung.shopsuite.sns.model.req.StoryCategoryEditReq;
import com.suisung.shopsuite.sns.model.req.StoryCategoryListReq;
import com.suisung.shopsuite.sns.service.StoryCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 版块 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-18
 */
@Api(tags = "版块")
@RestController
@RequestMapping("/manage/sns/storyCategory")
public class StoryCategoryController extends BaseController {
    @Autowired
    private StoryCategoryService storyCategoryService;

    @PreAuthorize("hasAuthority('/manage/sns/storyCategory/list')")
    @ApiOperation(value = "版块-分页列表查询", notes = "版块-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<StoryCategory>> list(StoryCategoryListReq storyCategoryListReq) {
        IPage<StoryCategory> pageList = storyCategoryService.lists(storyCategoryListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/sns/storyCategory/detail')")
    @ApiOperation(value = "版块-通过story_category_id查询", notes = "版块-通过story_category_id查询")
    @RequestMapping(value = "/{storyCategoryId}", method = RequestMethod.GET)
    public CommonRes<StoryCategory> get(@PathVariable Integer storyCategoryId) {
        StoryCategory storyCategory = storyCategoryService.get(storyCategoryId);

        return success(storyCategory);
    }

    @PreAuthorize("hasAuthority('/manage/sns/storyCategory/add')")
    @ApiOperation(value = "版块-添加", notes = "版块-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(StoryCategoryAddReq storyCategoryAddReq) {
        StoryCategory storyCategory = BeanUtil.copyProperties(storyCategoryAddReq, StoryCategory.class);
        boolean success = storyCategoryService.add(storyCategory);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sns/storyCategory/edit')")
    @ApiOperation(value = "版块-编辑", notes = "版块-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(StoryCategoryEditReq storyCategoryEditReq) {
        StoryCategory storyCategory = BeanUtil.copyProperties(storyCategoryEditReq, StoryCategory.class);
        boolean success = storyCategoryService.edit(storyCategory);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sns/storyCategory/remove')")
    @ApiOperation(value = "版块-通过story_category_id删除", notes = "版块-通过story_category_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("story_category_id") Integer storyCategoryId) {
        boolean success = storyCategoryService.remove(storyCategoryId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sns/storyCategory/removeBatch')")
    @ApiOperation(value = "版块-批量删除", notes = "版块-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("story_category_id") String storyCategoryIds) {
        boolean success = storyCategoryService.remove(Convert.toList(Integer.class, storyCategoryIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

