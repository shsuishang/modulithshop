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
package com.suisung.shopsuite.marketing.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.marketing.model.entity.*;
import com.suisung.shopsuite.marketing.model.req.*;
import com.suisung.shopsuite.marketing.model.res.ActivityBaseRes;
import com.suisung.shopsuite.marketing.model.res.ActivityItemRes;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 活动表-通过插件实 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Api(tags = "活动表-通过插件实现")
@RestController
@RequestMapping("/manage/marketing/activityBase")
public class ActivityBaseController extends BaseController {

    @Autowired
    private ActivityBaseService activityBaseService;

    @PreAuthorize("hasAuthority('/manage/marketing/activityBase/list')")
    @ApiOperation(value = "活动分页列表查询", notes = "活动分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ActivityBaseRes>> list(ActivityBaseListReq activityBaseListReq) {
        IPage<ActivityBaseRes> pageList = activityBaseService.getList(activityBaseListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/marketing/activityBase/detail')")
    @ApiOperation(value = "活动通过activity_id查询", notes = "活动通过activity_id查询")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<ActivityBase> get(@RequestParam("activity_id") Integer activityId) {
        ActivityBase activityBase = activityBaseService.get(activityId);

        return success(activityBase);
    }

    @PreAuthorize("hasAuthority('/manage/marketing/activityBase/add')")
    @ApiOperation(value = "活动添加", notes = "活动添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(ActivityBaseAddReq activityBaseAddReq) {
        ActivityBase activityBase = BeanUtil.copyProperties(activityBaseAddReq, ActivityBase.class);
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }
        activityBase.setUserId(user.getUserId());
        activityBase.setStoreId(user.getStoreId());
        activityBase.setSubsiteId(user.getSiteId().toString());
        boolean success = activityBaseService.addActivityBase(activityBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/marketing/activityBase/edit')")
    @ApiOperation(value = "活动表-编辑", notes = "活动表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(ActivityBaseEditReq activityBaseEditReq) {
        ActivityBase activityBase = BeanUtil.copyProperties(activityBaseEditReq, ActivityBase.class);
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        activityBase.setStoreId(user.getStoreId());
        boolean success = activityBaseService.updateActivityBase(activityBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/marketing/activityBase/edit')")
    @ApiOperation(value = "活动编辑", notes = "活动编辑")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(ActivityBaseEditReq activityBaseEditReq) {
        ActivityBase activityBase = activityBaseService.get(activityBaseEditReq.getActivityId());
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        activityBase.setActivityState(activityBaseEditReq.getActivityState());

        boolean success = activityBaseService.editActivityBase(activityBaseEditReq.getActivityId(), activityBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/marketing/activityBase/remove')")
    @ApiOperation(value = "活动通过activity_id删除", notes = "活动通过activity_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("activity_id") Integer activityId) {
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        boolean success = activityBaseService.removeActivity(activityId, user.getStoreId());

        if (success) {
            return success();
        }

        return fail();
    }
}
