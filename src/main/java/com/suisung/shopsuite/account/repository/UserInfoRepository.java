package com.suisung.shopsuite.account.repository;

import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.core.web.repository.IBaseRepository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户详细信息表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
public interface UserInfoRepository extends IBaseRepository<UserInfo> {
    Map<Integer, UserInfo> getUserInfoMap(List<Integer> userIds);
}
