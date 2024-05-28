package com.suisung.shopsuite.sys.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.MaterialBase;
import com.suisung.shopsuite.sys.model.req.MaterialBaseEditReq;
import com.suisung.shopsuite.sys.model.req.MaterialBaseListReq;
import com.suisung.shopsuite.sys.service.MaterialBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.suisung.shopsuite.common.utils.UploadUtil.getUploadBaseDir;
import static com.suisung.shopsuite.common.utils.UploadUtil.getUploadSmDir;

/**
 * <p>
 * 素材表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Api(tags = "素材表")
@RestController
@RequestMapping("/manage/sys/material")
public class MaterialBaseController extends BaseController {
    @Autowired
    private MaterialBaseService materialBaseService;

    @PreAuthorize("hasAuthority('/manage/sys/material/list')")
    @ApiOperation(value = "素材表-分页列表查询", notes = "素材表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<MaterialBase>> list(MaterialBaseListReq materialBaseListReq) {
        IPage<MaterialBase> pageList = materialBaseService.lists(materialBaseListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/sys/material/edit')")
    @ApiOperation(value = "素材表-编辑", notes = "素材表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(MaterialBaseEditReq materialBaseEditReq) {
        MaterialBase materialBase = BeanUtil.copyProperties(materialBaseEditReq, MaterialBase.class);
        boolean success = materialBaseService.edit(materialBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/material/remove')")
    @ApiOperation(value = "素材表-通过material_id删除", notes = "素材表-通过material_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("material_id") String materialId) {
        MaterialBase record = materialBaseService.get(materialId);

        if (materialBaseService.remove(materialId)) {
            if (StrUtil.isNotBlank(record.getMaterialPath())) {
                materialBaseService.deleteFileAsync(Arrays.asList(
                        new File(getUploadBaseDir(), record.getMaterialPath()),
                        new File(getUploadSmDir(), record.getMaterialPath())
                ));
            }
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('/manage/sys/material/removeBatch')")
    @ApiOperation(value = "素材表-批量删除", notes = "素材表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("material_id") String materialIds) {
        List<Long> ids = Convert.toList(Long.class, materialIds);

        List<MaterialBase> MaterialBases = materialBaseService.gets(ids);
        if (materialBaseService.remove(ids)) {
            List<File> files = new ArrayList<>();
            for (MaterialBase record : MaterialBases) {
                if (StrUtil.isNotBlank(record.getMaterialPath())) {
                    files.add(new File(getUploadBaseDir(), record.getMaterialPath()));
                    files.add(new File(getUploadSmDir(), record.getMaterialPath()));
                }
            }
            materialBaseService.deleteFileAsync(files);
            return success("删除成功");
        }
        return fail("删除失败");
    }
}

