package com.suisung.shopsuite.account.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.account.dao.UserLevelDao;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.repository.UserLevelRepository;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户等级表-平台 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Repository
public class UserLevelRepositoryImpl extends BaseRepositoryImpl<UserLevelDao, UserLevel> implements UserLevelRepository {
    //user_level_name
    private static final Map<Integer, String> userLevelMap = new HashMap<>();

    private static final Map<Integer, Integer> userLevelRateMap = new HashMap<>();

    public void initUserLevelData() {
        List<UserLevel> userLevels = find(new QueryWrapper<>());

        for (UserLevel userLevel : userLevels) {
            userLevelMap.put(userLevel.getUserLevelId(), userLevel.getUserLevelName());
            userLevelRateMap.put(userLevel.getUserLevelId(), userLevel.getUserLevelRate());
        }
    }

    @Override
    public Map<Integer, Integer> getUserLevelRateMap() {
        if (ObjectUtil.isEmpty(userLevelRateMap)) {
            initUserLevelData();
        }

        return userLevelRateMap;
    }

    @Override
    public Map<Integer, UserLevel> getUserLevelMap(List<Integer> userlevelIds) {
        //通过列user_level_id值查询UserLevel对象的集合
        List<UserLevel> userLevels = gets(userlevelIds);
        //创建有关对象集合UserLevel的map集合
        Map<Integer, UserLevel> userLevelMap = new HashMap<>();
        //将列user_level_id值对应的UserLevel对象映射过去 key为对象的user_level_id value值为对象
        if (CollectionUtil.isNotEmpty(userLevels)) {
            //userCardMap = userCards.stream().collect(Collectors.toMap(UserCard::getUserLevelId, UserInfo -> UserInfo, (k1, k2) -> k1));
         userLevelMap = userLevels.stream().collect(Collectors.toMap(UserLevel::getUserLevelId, userLevel -> userLevel, (k1, k2) -> k1));
        }
        return userLevelMap;
    }

    @Override
    public boolean add(UserLevel a) {
        boolean add = super.add(a);
        initUserLevelData();

        return add;
    }

    @Override
    public boolean edit(UserLevel a) {
        boolean edit = super.edit(a);
        initUserLevelData();

        return edit;
    }
}
