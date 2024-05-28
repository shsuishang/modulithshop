package com.suisung.shopsuite.sys.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.model.req.*;
import com.suisung.shopsuite.sys.model.res.ConfigListRes;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.ConfigTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统参数设置表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "系统参数设置表")
@RestController
@RequestMapping("/manage/sys/config")
public class ConfigBaseController extends BaseController {
    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ConfigTypeService configTypeService;

    @PreAuthorize("hasAuthority('/manage/sys/config/list')")
    @ApiOperation(value = "系统参数设置表-分页列表查询", notes = "系统参数设置表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ConfigBase>> list(ConfigBaseListReq configBaseListReq) {
        IPage<ConfigBase> pageList = configBaseService.lists(new BaseQueryWrapper<ConfigBase, ConfigBaseListReq>(configBaseListReq).getWrapper().orderByAsc("config_sort"), configBaseListReq.getPage(), configBaseListReq.getSize());

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/sys/config/add')")
    @ApiOperation(value = "系统参数设置表-添加", notes = "系统参数设置表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(ConfigBaseAddReq configBaseAddReq) {
        ConfigBase configBase = BeanUtil.copyProperties(configBaseAddReq, ConfigBase.class);
        boolean success = configBaseService.add(configBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/config/edit')")
    @ApiOperation(value = "系统参数设置表-编辑", notes = "系统参数设置表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(ConfigBaseEditReq configBaseEditReq) {
        ConfigBase configBase = BeanUtil.copyProperties(configBaseEditReq, ConfigBase.class);
        boolean success = configBaseService.edit(configBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/config/edit')")
    @ApiOperation(value = "系统参数设置表-修改状态", notes = "系统参数设置表-修改状态")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(ConfigBaseStateEditReq configBaseStateEditReq) {
        ConfigBase configBase = BeanUtil.copyProperties(configBaseStateEditReq, ConfigBase.class);
        boolean success = configBaseService.edit(configBase);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/config/remove')")
    @ApiOperation(value = "系统参数设置表-通过config_key删除", notes = "系统参数设置表-通过config_key删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("config_key") String configKey) {
        boolean success = configBaseService.remove(configKey);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/config/removeBatch')")
    @ApiOperation(value = "系统参数设置表-批量删除", notes = "系统参数设置表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.GET)
    public CommonRes<?> removeBatch(@RequestParam("config_key") String configKeys) {
        boolean success = configBaseService.remove(Convert.toList(String.class, configKeys));

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/config/editSite')")
    @ApiOperation(value = "站点设置-保存更改", notes = "站点设置-保存更改")
    @PostMapping(value = "/editSite")
    public CommonRes<?> editSite(@ModelAttribute ConfigBaseEditSiteReq objectMap) {
        boolean success = configBaseService.editSite(objectMap.getConfigs());

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "站点设置", notes = "站点设置")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonRes<ConfigListRes> index(ConfigBaseIndexReq configBaseIndexReq) {
        ConfigListRes configListRes = configBaseService.index(configBaseIndexReq);

        return success(configListRes);
    }

    @ApiOperation(value = "站点帮助", notes = "站点帮助")
    @RequestMapping(value = "/savePcHelp", method = RequestMethod.POST)
    public CommonRes<?> savePcHelp(@RequestParam(name = "pc_help") String pcHelp) {
        ConfigBase configBase = new ConfigBase();
        configBase.setConfigKey("page_pc_help");
        configBase.setConfigValue(pcHelp);
        configBase.setConfigTypeId(0);
        configBase.setConfigDatatype("text");
        configBase.setConfigBuildin(true);
        configBaseService.save(configBase);
        return success();
    }

    @ApiOperation(value = "系统参数设置表-详细配置", notes = "系统参数设置表-详细配置")
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public CommonRes<ConfigBase> getDetail(@RequestParam(name = "config_key") String configKey) {
        ConfigBase configBase = configBaseService.get(configKey);

        return success(configBase);
    }
}

