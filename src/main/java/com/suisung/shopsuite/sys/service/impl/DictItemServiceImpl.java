package com.suisung.shopsuite.sys.service.impl;

import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.DictItem;
import com.suisung.shopsuite.sys.model.req.DictItemListReq;
import com.suisung.shopsuite.sys.repository.DictItemRepository;
import com.suisung.shopsuite.sys.service.DictItemService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 字典项表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemRepository, DictItem, DictItemListReq> implements DictItemService {
    @Override
    public boolean remove(Serializable dictItemId) {
        DictItem dictItem = get(dictItemId);

        if (dictItem.getDictItemBuildin()) {
            throw new BusinessException(__("系统内置，不可删除"));
        }

        return super.remove(dictItemId);
    }
}
