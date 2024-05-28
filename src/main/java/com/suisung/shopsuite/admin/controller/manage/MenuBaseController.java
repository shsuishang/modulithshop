package com.suisung.shopsuite.admin.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.admin.model.entity.MenuBase;
import com.suisung.shopsuite.admin.model.entity.UserAdmin;
import com.suisung.shopsuite.admin.model.req.MenuBaseAddReq;
import com.suisung.shopsuite.admin.model.req.MenuBaseEditReq;
import com.suisung.shopsuite.admin.model.req.MenuBaseStateEditReq;
import com.suisung.shopsuite.admin.model.req.MenuTreeReq;
import com.suisung.shopsuite.admin.model.res.MenuTreeRes;
import com.suisung.shopsuite.admin.repository.UserAdminRepository;
import com.suisung.shopsuite.admin.service.MenuBaseService;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
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

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "系统菜单表")
@RestController
@RequestMapping("/manage/admin/menu")
public class MenuBaseController extends BaseController {
    @Autowired
    private MenuBaseService menuBaseService;

    @Autowired
    private UserAdminRepository userAdminRepository;

    @ApiOperation(value = "系统菜单表-分页列表查询", notes = "系统菜单表-分页列表查询")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CommonRes<List<MenuTreeRes>> tree(MenuTreeReq menuTreeReq) {
        //设置角色Id
        ContextUser loginUser = ContextUtil.getLoginUser();
        UserAdmin userAdmin = userAdminRepository.get(loginUser.getUserId());

        if (ObjectUtil.isNotEmpty(userAdmin)) {
            menuTreeReq.setUserRoleId(userAdmin.getUserRoleId());
        }

        List<MenuTreeRes> tree = menuBaseService.getMenuTree(menuTreeReq);

        return success(tree);
    }

    @PreAuthorize("hasAuthority('/manage/admin/menu/list')")
    @ApiOperation(value = "系统菜单表-分页列表查询", notes = "系统菜单表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<MenuBase>> list(MenuTreeReq menuTreeReq) {
        IPage<MenuBase> pageList = menuBaseService.lists(new BaseQueryWrapper<MenuBase, MenuTreeReq>(menuTreeReq).getWrapper(), menuTreeReq.getPage(), menuTreeReq.getSize());

        return success(pageList);
    }

    @ApiOperation(value = "系统菜单表-添加", notes = "系统菜单表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(MenuBaseAddReq menuBaseAddReq) {
        MenuBase menuBase = BeanUtil.copyProperties(menuBaseAddReq, MenuBase.class);
        boolean success = menuBaseService.add(menuBase);

        if (success) {
            return success();
        }

        return fail();
    }


    @ApiOperation(value = "系统菜单表-编辑", notes = "系统菜单表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(MenuBaseEditReq menuBaseEditReq) {
        MenuBase menuBase = BeanUtil.copyProperties(menuBaseEditReq, MenuBase.class);
        boolean success = menuBaseService.edit(menuBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "系统菜单表-通过menu_id删除", notes = "系统菜单表-通过menu_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("menu_id") Integer menuId) {
        boolean success = menuBaseService.remove(menuId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/admin/menu/editState')")
    @ApiOperation(value = "系统菜单表-状态修改", notes = "系统菜单表-状态修改")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(MenuBaseStateEditReq menuBaseStateEditReq) {
        MenuBase menuBase = BeanUtil.copyProperties(menuBaseStateEditReq, MenuBase.class);
        boolean success = menuBaseService.edit(menuBase);

        if (success) {
            return success();
        }

        return fail();
    }
}

