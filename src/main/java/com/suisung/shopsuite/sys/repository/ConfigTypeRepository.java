package com.suisung.shopsuite.sys.repository;

import com.suisung.shopsuite.core.web.repository.IBaseRepository;
import com.suisung.shopsuite.sys.model.entity.ConfigType;

import java.io.Serializable;

/**
 * <p>
 * 配置分组表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
public interface ConfigTypeRepository extends IBaseRepository<ConfigType> {
    boolean remove(Serializable id);
}
