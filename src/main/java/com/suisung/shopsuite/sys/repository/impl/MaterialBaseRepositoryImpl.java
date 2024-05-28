package com.suisung.shopsuite.sys.repository.impl;

import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.sys.dao.MaterialBaseDao;
import com.suisung.shopsuite.sys.model.entity.MaterialBase;
import com.suisung.shopsuite.sys.repository.MaterialBaseRepository;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 素材表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Repository
public class MaterialBaseRepositoryImpl extends BaseRepositoryImpl<MaterialBaseDao, MaterialBase> implements MaterialBaseRepository {

}
