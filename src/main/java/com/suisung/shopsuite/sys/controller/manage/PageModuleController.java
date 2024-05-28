// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.sys.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.PageModule;
import com.suisung.shopsuite.sys.model.req.PageModuleAddReq;
import com.suisung.shopsuite.sys.model.req.PageModuleEditReq;
import com.suisung.shopsuite.sys.model.req.PageModuleListReq;
import com.suisung.shopsuite.sys.model.vo.PageModuleVo;
import com.suisung.shopsuite.sys.service.PageModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 页面模块表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Api(tags = "页面模块表")
@RestController
@RequestMapping("/manage/sys/pageModule")
public class PageModuleController extends BaseController {
    @Autowired
    private PageModuleService pageModuleService;

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "页面模块表-分页列表查询", notes = "页面模块表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<PageModuleVo>> list(PageModuleListReq pageModuleListReq) {
        IPage<PageModuleVo> pageListVo = pageModuleService.getLists(pageModuleListReq);

        return success(pageListVo);
    }

    @ApiOperation(value = "页面模块表-通过pm_id查询", notes = "页面模块表-通过pm_id查询")
    @RequestMapping(value = "/{pmId}", method = RequestMethod.GET)
    public CommonRes<PageModule> get(@PathVariable Integer pmId) {
        PageModule pageModule = pageModuleService.get(pmId);

        return success(pageModule);
    }

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "页面模块表-添加", notes = "页面模块表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(PageModuleAddReq pageModuleAddReq) {
        PageModule pageModule = BeanUtil.copyProperties(pageModuleAddReq, PageModule.class);
        pageModule.setPmName("");
        pageModule.setPmEnable(false);
        String pmJson = pageModule.getPmJson();
        String url = "\"url\":\"//test.shopsuite.cn";
        String url_new = "\"url\":\"" + ConstantConfig.URL_PC;
        String link = "\"link\":\"//test.shopsuite.cn";
        String link_new = "\"link\":\"" + ConstantConfig.URL_PC;

        if (StrUtil.isNotEmpty(pmJson)) {
            pmJson = pmJson.replace(url, url_new).replace(link, link_new);
            pageModule.setPmJson(pmJson);
        }

        boolean success = pageModuleService.add(pageModule);

        if (success) {
            return success(pageModule);
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "页面模块表-编辑", notes = "页面模块表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(PageModuleEditReq pageModuleEditReq) {
        PageModule pageModule = BeanUtil.copyProperties(pageModuleEditReq, PageModule.class);
        boolean success = pageModuleService.edit(pageModule);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "页面模块表-通过pm_id删除", notes = "页面模块表-通过pm_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("pm_id") Integer pmId) {
        boolean success = pageModuleService.remove(pmId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "PC楼层模板", notes = "PC楼层模板")
    @RequestMapping(value = "/listTpl", method = RequestMethod.GET)
    public CommonRes<?> getModuleTpl() {
        Map moduleTpl = pageModuleService.getModuleTpl();

        if (ObjectUtil.isNotEmpty(moduleTpl)) {
            return success(moduleTpl);
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "页面模块表-启用、禁用", notes = "页面模块表-启用、禁用")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public CommonRes<?> enable(@RequestParam(name = "pm_id") Integer pmId,
                               @RequestParam(name = "usable") String usable,
                               @RequestParam(name = "page_id") Long pageId) {

        if (StrUtil.isNotEmpty(usable)) {
            PageModule pageModule = new PageModule();
            pageModule.setPmId(pmId);
            pageModule.setPageId(pageId);
            pageModule.setPmEnable(usable.equals("usable"));
            boolean success = pageModuleService.edit(pageModule);

            if (success) {
                return success();
            }
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/sys/diy/index')")
    @ApiOperation(value = "页面模块表-拖动", notes = "页面模块表-拖动")
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public CommonRes<?> sort(@RequestParam(name = "page_id") Long pageId,
                             @RequestParam(name = "pm_id_string") String pmIdString) {
        List<Integer> pmIds = Convert.toList(Integer.class, pmIdString.trim().split(","));
        List<PageModule> pageModuleList = pageModuleService.gets(pmIds);

        if (CollectionUtil.isNotEmpty(pageModuleList)) {
            for (PageModule pageModule : pageModuleList) {
                pageModule.setPmOrder(pageModuleList.indexOf(pageModule));
            }

            boolean result = pageModuleService.edit(pageModuleList);

            if (result) {
                return success();
            }
        }

        return fail();
    }
}

