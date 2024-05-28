package com.suisung.shopsuite.account.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户等级表-平台 Mapper 接口
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Mapper
public interface UserLevelDao extends BaseMapper<UserLevel> {

}
