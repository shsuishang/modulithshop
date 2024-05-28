package com.suisung.shopsuite.account.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.suisung.shopsuite.account.dao.UserInfoDao;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户详细信息表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Repository
public class UserInfoRepositoryImpl extends BaseRepositoryImpl<UserInfoDao, UserInfo> implements UserInfoRepository {
    public Map<Integer, UserInfo> getUserInfoMap(List<Integer> userIds) {
        List<UserInfo> userInfos = gets(userIds);
        Map<Integer, UserInfo> userInfoMap = new HashMap<>();

        if (CollectionUtil.isNotEmpty(userInfos)) {
            userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo -> UserInfo, (k1, k2) -> k1));
        }

        return userInfoMap;
    }
}
