package com.suisung.shopsuite.account.repository;

import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.core.web.repository.IBaseRepository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户等级表-平台 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
public interface UserLevelRepository extends IBaseRepository<UserLevel> {
    Map<Integer, Integer> getUserLevelRateMap();
    Map<Integer, UserLevel> getUserLevelMap(List<Integer> userlevelIds);
}
