package com.suisung.shopsuite.account.service.impl;

import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.model.req.UserLevelListReq;
import com.suisung.shopsuite.account.repository.UserLevelRepository;
import com.suisung.shopsuite.account.service.UserLevelService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户等级表-平台 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Service
public class UserLevelServiceImpl extends BaseServiceImpl<UserLevelRepository, UserLevel, UserLevelListReq> implements UserLevelService {
}
