package com.suisung.shopsuite.sys.repository;

import com.suisung.shopsuite.core.web.repository.IBaseRepository;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;

import java.util.List;

/**
 * <p>
 * 系统参数设置表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
public interface ConfigBaseRepository extends IBaseRepository<ConfigBase> {

    List<ConfigBase> gets(List<String> objects);

}
