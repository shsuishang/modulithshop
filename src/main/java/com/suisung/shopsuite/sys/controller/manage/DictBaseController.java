package com.suisung.shopsuite.sys.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.DictBase;
import com.suisung.shopsuite.sys.model.req.DictBaseAddReq;
import com.suisung.shopsuite.sys.model.req.DictBaseEditReq;
import com.suisung.shopsuite.sys.model.req.DictBaseListReq;
import com.suisung.shopsuite.sys.service.DictBaseService;
import com.suisung.shopsuite.sys.service.DictItemService;
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
 * 字典类型表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Api(tags = "字典类型表")
@RestController
@RequestMapping("/manage/sys/dict")
public class DictBaseController extends BaseController {
    @Autowired
    private DictBaseService dictBaseService;

    @Autowired
    private DictItemService dictItemService;

    @PreAuthorize("hasAuthority('/manage/sys/dict/list')")
    @ApiOperation(value = "字典类型表-分页列表查询", notes = "字典类型表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<DictBase>> list(DictBaseListReq dictBaseListReq) {
        IPage<DictBase> pageList = dictBaseService.lists(dictBaseListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/sys/dict/add')")
    @ApiOperation(value = "字典类型表-添加", notes = "字典类型表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(DictBaseAddReq dictBaseAddReq) {
        DictBase dictBase = BeanUtil.copyProperties(dictBaseAddReq, DictBase.class);
        boolean success = dictBaseService.add(dictBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/dict/edit')")
    @ApiOperation(value = "字典类型表-编辑", notes = "字典类型表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(DictBaseEditReq dictBaseEditReq) {
        DictBase dictBase = BeanUtil.copyProperties(dictBaseEditReq, DictBase.class);
        boolean success = dictBaseService.edit(dictBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/dict/remove')")
    @ApiOperation(value = "字典类型表-通过dict_id删除", notes = "字典类型表-通过dict_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("dict_id") String dictId) {
        boolean success = dictBaseService.remove(dictId);

        if (success) {
            return success();
        }

        return fail();
    }
}

