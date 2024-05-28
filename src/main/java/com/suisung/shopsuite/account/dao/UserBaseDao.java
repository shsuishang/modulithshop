package com.suisung.shopsuite.account.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suisung.shopsuite.account.model.entity.UserBase;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户基本信息表 Mapper 接口
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Mapper
public interface UserBaseDao extends BaseMapper<UserBase> {

}
