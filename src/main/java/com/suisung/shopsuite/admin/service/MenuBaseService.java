package com.suisung.shopsuite.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.admin.model.entity.MenuBase;
import com.suisung.shopsuite.admin.model.req.MenuTreeReq;
import com.suisung.shopsuite.admin.model.res.MenuTreeRes;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
public interface MenuBaseService {

    /**
     * 插入系统菜单表数据
     *
     * @param menuBase 系统菜单表
     * @return boolean
     */
    boolean add(MenuBase menuBase);

    /**
     * 更新 系统菜单表 数据
     *
     * @param menuBase 系统菜单表
     * @return boolean
     */
    boolean edit(MenuBase menuBase);

    /**
     * 根据menuId删除
     *
     * @param menuId
     * @return boolean
     */
    boolean remove(Serializable menuId);

    /**
     * 根据menuId集合删除 系统菜单表 数据
     *
     * @param menuIds
     * @return boolean
     */
    boolean remove(Collection<? extends Serializable> menuIds);

    /**
     * 查询系统菜单表数据
     *
     * @return MenuBase
     */
    MenuBase get(Serializable menuId);

    /**
     * 根据主键读取系统菜单表列表
     *
     * @param menuIds
     * @return List<MenuBase> 系统菜单表类表
     */
    List<MenuBase> gets(Collection<? extends Serializable> menuIds);

    /**
     * 根据条件查询系统菜单表分页信息
     *
     * @param wrapper
     * @param pageNum
     * @param pageSize
     * @return Page<MenuBase>
     */
    Page<MenuBase> lists(QueryWrapper<MenuBase> wrapper, Integer pageNum, Integer pageSize);

    /**
     * 首页左侧树形菜单
     *
     * @return
     */
    List<MenuTreeRes> getMenuTree(MenuTreeReq menuTreeReq);

    /**
     * URl Menu
     *
     * @return
     */
    Map<String, String> getMenuMap();
}
