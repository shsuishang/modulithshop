package com.suisung.shopsuite.account.repository.impl;

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
