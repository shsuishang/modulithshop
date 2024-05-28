package com.suisung.shopsuite.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统参数设置表 Mapper 接口
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Mapper
public interface ConfigBaseDao extends BaseMapper<ConfigBase> {

}
