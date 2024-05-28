package com.suisung.shopsuite.account.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.model.req.UserLevelAddReq;
import com.suisung.shopsuite.account.model.req.UserLevelEditReq;
import com.suisung.shopsuite.account.model.req.UserLevelListReq;
import com.suisung.shopsuite.account.service.UserLevelService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户等级表-平台 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-09
 */
@Api(tags = "用户等级表-平台")
@RestController
@RequestMapping("/manage/account/userLevel")
public class UserLevelController extends BaseController {
    @Autowired
    private UserLevelService userLevelService;

    @ApiOperation(value = "用户等级表-平台-分页列表查询", notes = "用户等级表-平台-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserLevel>> list(UserLevelListReq userLevelListReq) {
        IPage<UserLevel> pageList = userLevelService.lists(userLevelListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/account/userLevel/add')")
    @ApiOperation(value = "用户等级表-平台-添加", notes = "用户等级表-平台-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserLevelAddReq userLevelAddReq) {
        UserLevel userLevel = BeanUtil.copyProperties(userLevelAddReq, UserLevel.class);
        boolean success = userLevelService.add(userLevel);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userLevel/edit')")
    @ApiOperation(value = "用户等级表-平台-编辑", notes = "用户等级表-平台-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserLevelEditReq userLevelEditReq) {
        UserLevel userLevel = BeanUtil.copyProperties(userLevelEditReq, UserLevel.class);
        boolean success = userLevelService.edit(userLevel);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userLevel/remove')")
    @ApiOperation(value = "用户等级表-平台-通过user_level_id删除", notes = "用户等级表-平台-通过user_level_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("user_level_id") Integer userLevelId) {
        boolean success = userLevelService.remove(userLevelId);

        if (success) {
            return success();
        }

        return fail();
    }
}

