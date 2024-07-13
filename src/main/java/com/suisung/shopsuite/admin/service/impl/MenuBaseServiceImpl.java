package com.suisung.shopsuite.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.admin.model.entity.MenuBase;
import com.suisung.shopsuite.admin.model.entity.UserAdmin;
import com.suisung.shopsuite.admin.model.entity.UserRole;
import com.suisung.shopsuite.admin.model.req.MenuTreeReq;
import com.suisung.shopsuite.admin.model.res.MenuTreeRes;
import com.suisung.shopsuite.admin.model.res.Meta;
import com.suisung.shopsuite.admin.repository.MenuBaseRepository;
import com.suisung.shopsuite.admin.repository.UserAdminRepository;
import com.suisung.shopsuite.admin.repository.UserRoleRepository;
import com.suisung.shopsuite.admin.service.MenuBaseService;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Service
public class MenuBaseServiceImpl implements MenuBaseService {
    @Autowired
    private MenuBaseRepository menuBaseRepository;

    @Autowired
    private UserAdminRepository userAdminRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @CacheEvict(value = {"menuTree"}, allEntries = true)
    public boolean add(MenuBase menuBase) {
        menuBase.setMenuTime(new Date());
        return menuBaseRepository.add(menuBase);
    }


    @CacheEvict(value = {"menuTree"}, allEntries = true)
    public boolean edit(MenuBase menuBase) {
        return menuBaseRepository.edit(menuBase);
    }


    @CacheEvict(value = {"menuTree"}, allEntries = true)
    public boolean remove(Serializable menuId) {
        MenuBase menuBase = get(menuId);

        if (menuBase.getMenuBuildin()) {
            throw new BusinessException(__("系统内置，不可删除"));
        }

        return menuBaseRepository.remove(menuId);
    }

    @CacheEvict(value = {"menuTree"}, allEntries = true)
    public boolean remove(Collection<? extends Serializable> menuIds) {
        return menuBaseRepository.remove(menuIds);
    }

    public MenuBase get(Serializable menuId) {
        return menuBaseRepository.get(menuId);
    }

    public List<MenuBase> gets(Collection<? extends Serializable> menuIds) {
        return menuBaseRepository.gets(menuIds);
    }

    public Page<MenuBase> lists(QueryWrapper<MenuBase> wrapper, Integer pageNum, Integer pageSize) {
        return menuBaseRepository.lists(wrapper, pageNum, pageSize);
    }

    @Cacheable(value = {"menuTree"})
    @Override
    public List<MenuTreeRes> getMenuTree(MenuTreeReq menuTreeReq) {
        QueryWrapper<MenuBase> baseQueryWrapper = new QueryWrapper<>();
        baseQueryWrapper.eq("menu_role", 1);
        baseQueryWrapper.eq("menu_enable", 1);
        baseQueryWrapper.orderByAsc("menu_sort");

        if (menuTreeReq.getType().equals(2)) {
        } else {
            baseQueryWrapper.eq("menu_type", menuTreeReq.getType());
        }

        if (ObjectUtil.isNotEmpty(menuTreeReq.getMenuTitle())) {
            baseQueryWrapper.like("menu_title", menuTreeReq.getMenuTitle());
        }

        List<MenuBase> menuBases = menuBaseRepository.find(baseQueryWrapper);

        //start 将未开启功能菜单隐藏
        Iterator<MenuBase> it = menuBases.iterator();

        while (it.hasNext()) {
            MenuBase tmp = it.next();

            // 判断功能是否开启
            if (!tmp.getMenuFunc().equals("") && !configBaseService.getConfig(tmp.getMenuFunc(), false)) {
                it.remove();
            } else {
                //无权限可移除
            }
        }
        //end

        // begin 为了菜单显示隐藏判断
        String[] menuIds = new String[0];

        ContextUser loginUser = ContextUtil.getLoginUser();
        UserAdmin userAdmin = userAdminRepository.get(loginUser.getUserId());

        if (ObjectUtil.isNotEmpty(userAdmin)) {
            UserRole userRole = userRoleRepository.get(userAdmin.getUserRoleId());
            menuIds = userRole.getMenuIds().split(",");
        }

        List<MenuBase> menuAll = menuBaseRepository.find(new QueryWrapper<>());

        /*
        //start 将未开启功能菜单隐藏
        Iterator<MenuBase> it = menuAll.iterator();

        while (it.hasNext()) {
            MenuBase tmp = it.next();

            // 判断功能是否开启
            if (!tmp.getMenuFunc().equals("") && !configBaseService.getConfig(tmp.getMenuFunc(), false)) {
                it.remove();
            } else {
                //无权限可移除
            }
        }
        //end
         */

        for (MenuBase c : menuBases) {
            //判断权限隐藏
            c.setMenuHidden(!checkChildShow(menuAll, c, menuIds));
        }
        // end 为了菜单显示隐藏判断

        List<MenuTreeRes> menuTreeRes = buildRouters(menuBases, 0);

        //或者无上级数据，加入列表。 -- 用户树形搜索展示
        List<Integer> columnIds = CommonUtil.column(menuBases, MenuBase::getMenuId);
        for (MenuBase baseMenu : menuBases) {
            if (baseMenu.getMenuParentId().intValue() != 0 && !columnIds.contains(baseMenu.getMenuParentId())) {
                MenuTreeRes router = conversionRouter(baseMenu);

                List<MenuTreeRes> c = buildRouters(menuBases, baseMenu.getMenuId());
                router.setChildren(c);
                menuTreeRes.add(router);
            }
        }

        return menuTreeRes;
    }

    /**
     * URl Menu
     *
     * @return
     */
    @Cacheable(value = {"menuTree"})
    @Override
    public Map<String, String> getMenuMap() {
        Map<String, String> menuMap = new HashMap<>();

        List<MenuBase> menuBases = menuBaseRepository.find(new QueryWrapper<>());
        for (MenuBase baseMenu : menuBases) {

            if (CheckUtil.isNotEmpty(baseMenu.getMenuParentId())) {
                for (MenuBase sec : menuBases) {
                    if (sec.getMenuId().equals(baseMenu.getMenuParentId())) {

                        String format = String.format("%s - %s", sec.getMenuTitle(), baseMenu.getMenuTitle());

                        menuMap.put(baseMenu.getMenuPermission(), format);
                        continue;
                    }
                }
            }


        }

        return menuMap;
    }

    /**
     * 构造路由节点
     *
     * @param menus 菜单列表
     * @param pid   上级菜单编号
     * @return
     */
    public List<MenuTreeRes> buildRouters(List<MenuBase> menus, Integer pid) {
        List<MenuTreeRes> routers = new LinkedList<>();
        for (MenuBase baseMenu : menus) {
            if (baseMenu.getMenuParentId().intValue() == pid.intValue()) {
                MenuTreeRes router = conversionRouter(baseMenu);

                List<MenuTreeRes> c = buildRouters(menus, baseMenu.getMenuId());
                router.setChildren(c);
                routers.add(router);
            }
        }

        return routers;
    }

    /**
     * 判断权限隐藏
     *
     * @param menus   菜单列表
     * @param tn      当前菜单
     * @param menuIds 有权限菜单编号
     * @return
     */
    public boolean checkChildShow(List<MenuBase> menus, MenuBase tn, String[] menuIds) {
        boolean flag = false;

        if (tn.getMenuId().intValue() == 4058) {
            boolean ddd = true;
        }

        if (tn.getMenuHidden()) {
            flag = false;
        } else {
            if (ArrayUtil.contains(menuIds, tn.getMenuId().toString())) {
                flag = true;
            } else {
                //当前菜单无权限，判断所有子节点都不存在权限中，则隐藏菜单, 使用递归判断实现
                for (MenuBase c : menus) {
                    if (tn.getMenuId().equals(c.getMenuParentId())) {
                        flag = checkChildShow(menus, c, menuIds);

                        if (flag) {
                            break;
                        }
                    }
                }
            }
        }

        return flag;
    }


    /**
     * BaseMenu 转换成 Router
     *
     * @param menu 目录对象
     * @return
     */
    private MenuTreeRes conversionRouter(MenuBase menu) {
        MenuTreeRes router = BeanUtil.copyProperties(menu, MenuTreeRes.class);

        router.setChildren(new ArrayList<>());
        router.setPath(menu.getMenuPath());
        router.setName(menu.getMenuName());

        if (StrUtil.isNotBlank(menu.getMenuUrl())) router.setRedirect(menu.getMenuUrl());
        Boolean menu_hidden = menu.getMenuHidden();
        router.setComponent(menu.getMenuComponent());
        router.setMeta(new Meta());
        if (menu_hidden != null && menu_hidden == true) router.getMeta().setHidden(true);

        router.getMeta().setDot(menu.getMenuDot());
        router.getMeta().setTitle(menu.getMenuTitle());
        router.getMeta().setIcon(menu.getMenuIcon());
        router.getMeta().setNoClosable(!menu.getMenuClose());
        router.getMeta().setBadge(menu.getMenuBubble());
        router.getMeta().setMenuId(menu.getMenuId());
        return router;
    }
}
