package com.suisung.shopsuite.sys.repository.impl;

import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.sys.dao.DictItemDao;
import com.suisung.shopsuite.sys.model.entity.DictItem;
import com.suisung.shopsuite.sys.repository.DictItemRepository;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 字典项表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Repository
public class DictItemRepositoryImpl extends BaseRepositoryImpl<DictItemDao, DictItem> implements DictItemRepository {

}
