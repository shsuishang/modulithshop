package com.suisung.shopsuite.sys.service.impl;

import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.DictBase;
import com.suisung.shopsuite.sys.model.req.DictBaseListReq;
import com.suisung.shopsuite.sys.repository.DictBaseRepository;
import com.suisung.shopsuite.sys.service.DictBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Service
public class DictBaseServiceImpl extends BaseServiceImpl<DictBaseRepository, DictBase, DictBaseListReq> implements DictBaseService {
    @Override
    public boolean remove(Serializable dictId) {
        DictBase dictBase = get(dictId);

        if (dictBase.getDictBuildin()) {
            throw new BusinessException(__("系统内置，不可删除"));
        }

        return super.remove(dictId);
    }
}
