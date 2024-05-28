package com.suisung.shopsuite.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.MaterialBase;
import com.suisung.shopsuite.sys.model.entity.MaterialGallery;
import com.suisung.shopsuite.sys.model.req.MaterialGalleryListReq;
import com.suisung.shopsuite.sys.repository.MaterialBaseRepository;
import com.suisung.shopsuite.sys.repository.MaterialGalleryRepository;
import com.suisung.shopsuite.sys.service.MaterialGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 素材分类表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Service
public class MaterialGalleryServiceImpl extends BaseServiceImpl<MaterialGalleryRepository, MaterialGallery, MaterialGalleryListReq> implements MaterialGalleryService {
    @Autowired
    private MaterialBaseRepository materialBaseRepository;

    @Override
    public boolean remove(Serializable galleryId) {
        QueryWrapper<MaterialBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gallery_id", galleryId);

        long count = materialBaseRepository.count(queryWrapper);

        if (count > 0) {
            throw new BusinessException(String.format(__("有 %d 条素材使用，不可删除"), count));
        }

        return super.remove(galleryId);
    }

    @Override
    public Page<MaterialGallery> lists(QueryWrapper<MaterialGallery> wrapper, Integer pageNum, Integer pageSize) {
        wrapper.orderByAsc("gallery_sort");
        return super.lists(wrapper, pageNum, pageSize);
    }
}
