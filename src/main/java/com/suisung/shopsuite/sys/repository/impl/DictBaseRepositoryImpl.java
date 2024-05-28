package com.suisung.shopsuite.sys.repository.impl;

import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.sys.dao.DictBaseDao;
import com.suisung.shopsuite.sys.model.entity.DictBase;
import com.suisung.shopsuite.sys.repository.DictBaseRepository;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Repository
public class DictBaseRepositoryImpl extends BaseRepositoryImpl<DictBaseDao, DictBase> implements DictBaseRepository {

}
