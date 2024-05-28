package com.suisung.shopsuite.sys.repository.impl;

import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.sys.dao.ConfigTypeDao;
import com.suisung.shopsuite.sys.model.entity.ConfigType;
import com.suisung.shopsuite.sys.repository.ConfigTypeRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


/**
 * <p>
 * 配置分组表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Repository
public class ConfigTypeRepositoryImpl extends BaseRepositoryImpl<ConfigTypeDao, ConfigType> implements ConfigTypeRepository {
    @Override
    public boolean remove(Serializable id) {
//        ConfigType configType = get(id);
//
//        if (configType.getConfigTypeBuildin()) {
//            throw new BusinessException(__("系统内置，不可删除"));
//        }

        return super.remove(id);
    }
}
