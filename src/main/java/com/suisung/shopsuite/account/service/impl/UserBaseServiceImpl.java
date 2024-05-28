package com.suisung.shopsuite.account.service.impl;

import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.account.model.req.UserBaseListReq;
import com.suisung.shopsuite.account.repository.UserBaseRepository;
import com.suisung.shopsuite.account.service.UserBaseService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Service
public class UserBaseServiceImpl extends BaseServiceImpl<UserBaseRepository, UserBase, UserBaseListReq> implements UserBaseService {
}