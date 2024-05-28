package com.suisung.shopsuite.account.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户详细信息表 Mapper 接口
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

}
