package com.suisung.shopsuite.sys.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.MaterialGallery;
import com.suisung.shopsuite.sys.model.req.MaterialGalleryAddReq;
import com.suisung.shopsuite.sys.model.req.MaterialGalleryEditReq;
import com.suisung.shopsuite.sys.model.req.MaterialGalleryListReq;
import com.suisung.shopsuite.sys.service.MaterialGalleryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 素材分类表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Api(tags = "素材分类表")
@RestController
@RequestMapping("/manage/sys/material")
public class MaterialGalleryController extends BaseController {
    @Autowired
    private MaterialGalleryService materialGalleryService;

    @PreAuthorize("hasAuthority('/manage/sys/material/list')")
    @ApiOperation(value = "素材分类表-分页列表查询", notes = "素材分类表-分页列表查询")
    @RequestMapping(value = "/listGallery", method = RequestMethod.GET)
    public CommonRes<BaseListRes<MaterialGallery>> list(MaterialGalleryListReq materialGalleryListReq) {
        materialGalleryListReq.setSidx("gallery_sort");
        IPage<MaterialGallery> pageList = materialGalleryService.lists(materialGalleryListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/sys/material/add')")
    @ApiOperation(value = "素材分类表-添加", notes = "素材分类表-添加")
    @RequestMapping(value = "/addGallery", method = RequestMethod.POST)
    public CommonRes<?> add(MaterialGalleryAddReq materialGalleryAddReq) {
        MaterialGallery materialGallery = BeanUtil.copyProperties(materialGalleryAddReq, MaterialGallery.class);
        boolean success = materialGalleryService.add(materialGallery);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/material/edit')")
    @ApiOperation(value = "素材分类表-编辑", notes = "素材分类表-编辑")
    @RequestMapping(value = "/editGallery", method = RequestMethod.POST)
    public CommonRes<?> edit(MaterialGalleryEditReq materialGalleryEditReq) {
        MaterialGallery materialGallery = BeanUtil.copyProperties(materialGalleryEditReq, MaterialGallery.class);
        boolean success = materialGalleryService.edit(materialGallery);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/material/remove')")
    @ApiOperation(value = "素材分类表-通过gallery_id删除", notes = "素材分类表-通过gallery_id删除")
    @RequestMapping(value = "/removeGallery", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("gallery_id") String galleryId) {
        boolean success = materialGalleryService.remove(galleryId);

        if (success) {
            return success();
        }

        return fail();
    }
}

