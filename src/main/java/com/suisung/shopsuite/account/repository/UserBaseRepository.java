package com.suisung.shopsuite.account.repository;

import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.core.web.repository.IBaseRepository;

/**
 * <p>
 * 用户基本信息表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
public interface UserBaseRepository extends IBaseRepository<UserBase> {
    /**
     * 根据账号查询用户
     *
     * @param username 账号
     * @return UserBase
     */
    UserBase getByUserAccount(String username);
}
