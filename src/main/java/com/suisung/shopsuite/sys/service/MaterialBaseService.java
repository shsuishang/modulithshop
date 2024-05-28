package com.suisung.shopsuite.sys.service;

import com.suisung.shopsuite.core.web.service.IBaseService;
import com.suisung.shopsuite.sys.model.entity.MaterialBase;
import com.suisung.shopsuite.sys.model.req.MaterialBaseListReq;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 素材表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
public interface MaterialBaseService extends IBaseService<MaterialBase, MaterialBaseListReq> {
    /**
     * 异步删除文件
     *
     * @param files 文件数组
     */
    void deleteFileAsync(List<File> files);
}
