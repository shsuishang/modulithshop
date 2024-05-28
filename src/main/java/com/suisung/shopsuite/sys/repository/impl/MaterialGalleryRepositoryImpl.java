package com.suisung.shopsuite.sys.repository.impl;

import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.sys.dao.MaterialGalleryDao;
import com.suisung.shopsuite.sys.model.entity.MaterialGallery;
import com.suisung.shopsuite.sys.repository.MaterialGalleryRepository;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 素材分类表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Repository
public class MaterialGalleryRepositoryImpl extends BaseRepositoryImpl<MaterialGalleryDao, MaterialGallery> implements MaterialGalleryRepository {

}
