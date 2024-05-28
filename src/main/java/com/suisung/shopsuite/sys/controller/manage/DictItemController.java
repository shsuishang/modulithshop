package com.suisung.shopsuite.sys.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.DictItem;
import com.suisung.shopsuite.sys.model.req.DictItemAddReq;
import com.suisung.shopsuite.sys.model.req.DictItemEditReq;
import com.suisung.shopsuite.sys.model.req.DictItemListReq;
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
 * 字典项表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-05
 */
@Api(tags = "字典项表")
@RestController
@RequestMapping("/manage/sys/dict")
public class DictItemController extends BaseController {
    @Autowired
    private DictItemService dictItemService;

    @PreAuthorize("hasAuthority('/manage/sys/dict/listItem')")
    @ApiOperation(value = "字典项表-分页列表查询", notes = "字典项表-分页列表查询")
    @RequestMapping(value = "/listItem", method = RequestMethod.GET)
    public CommonRes<BaseListRes<DictItem>> list(DictItemListReq dictItemListReq) {
        IPage<DictItem> pageList = dictItemService.lists(dictItemListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/sys/dict/addItem')")
    @ApiOperation(value = "字典项表-添加", notes = "字典项表-添加")
    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public CommonRes<?> add(DictItemAddReq dictItemAddReq) {
        DictItem dictItem = BeanUtil.copyProperties(dictItemAddReq, DictItem.class);
        boolean success = dictItemService.add(dictItem);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/dict/editItem')")
    @ApiOperation(value = "字典项表-编辑", notes = "字典项表-编辑")
    @RequestMapping(value = "/editItem", method = RequestMethod.POST)
    public CommonRes<?> edit(DictItemEditReq dictItemEditReq) {
        DictItem dictItem = BeanUtil.copyProperties(dictItemEditReq, DictItem.class);
        boolean success = dictItemService.edit(dictItem);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/dict/removeItem')")
    @ApiOperation(value = "字典项表-通过dict_item_id删除", notes = "字典项表-通过dict_item_id删除")
    @RequestMapping(value = "/removeItem", method = RequestMethod.POST)
    public CommonRes<?> removeItem(@RequestParam("dict_item_id") String dictItemId) {
        boolean success = dictItemService.remove(dictItemId);

        if (success) {
            return success();
        }

        return fail();
    }
}

