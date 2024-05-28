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
package com.suisung.shopsuite.shop.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.shop.model.entity.StoreEmployee;
import com.suisung.shopsuite.shop.model.req.StoreEmployeeAddReq;
import com.suisung.shopsuite.shop.model.req.StoreEmployeeEditReq;
import com.suisung.shopsuite.shop.model.req.StoreEmployeeListReq;
import com.suisung.shopsuite.shop.service.StoreEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 卖家用户表—公司员工employee-通过user id启用用户中心 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-08-07
 */
@Api(tags = "卖家用户表—公司员工employee-通过user id启用用户中心")
@RestController
@RequestMapping("/manage/shop/storeEmployee")
public class StoreEmployeeController extends BaseController {
    @Autowired
    private StoreEmployeeService storeEmployeeService;

    @PreAuthorize("hasAuthority('/manage/shop/storeEmployee/list')")
    @ApiOperation(value = "卖家用户表—公司员工employee-通过user id启用用户中心-分页列表查询", notes = "卖家用户表—公司员工employee-通过user id启用用户中心-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<StoreEmployee>> list(StoreEmployeeListReq storeEmployeeListReq) {
        IPage<StoreEmployee> pageList = storeEmployeeService.lists(storeEmployeeListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeEmployee/detail')")
    @ApiOperation(value = "卖家用户表—公司员工employee-通过user id启用用户中心-通过employee_id查询", notes = "卖家用户表—公司员工employee-通过user id启用用户中心-通过employee_id查询")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
    public CommonRes<StoreEmployee> get(@PathVariable Integer employeeId) {
        StoreEmployee storeEmployee = storeEmployeeService.get(employeeId);

        return success(storeEmployee);
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeEmployee/add')")
    @ApiOperation(value = "卖家用户表—公司员工employee-通过user id启用用户中心-添加", notes = "卖家用户表—公司员工employee-通过user id启用用户中心-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(StoreEmployeeAddReq storeEmployeeAddReq) {
        StoreEmployee storeEmployee = BeanUtil.copyProperties(storeEmployeeAddReq, StoreEmployee.class);
        boolean success = storeEmployeeService.add(storeEmployee);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeEmployee/edit')")
    @ApiOperation(value = "卖家用户表—公司员工employee-通过user id启用用户中心-编辑", notes = "卖家用户表—公司员工employee-通过user id启用用户中心-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(StoreEmployeeEditReq storeEmployeeEditReq) {
        StoreEmployee storeEmployee = BeanUtil.copyProperties(storeEmployeeEditReq, StoreEmployee.class);
        boolean success = storeEmployeeService.edit(storeEmployee);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeEmployee/remove')")
    @ApiOperation(value = "卖家用户表—公司员工employee-通过user id启用用户中心-通过employee_id删除", notes = "卖家用户表—公司员工employee-通过user id启用用户中心-通过employee_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("employee_id") Integer employeeId) {
        boolean success = storeEmployeeService.remove(employeeId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeEmployee/removeBatch')")
    @ApiOperation(value = "卖家用户表—公司员工employee-通过user id启用用户中心-批量删除", notes = "卖家用户表—公司员工employee-通过user id启用用户中心-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("employee_id") String employeeIds) {
        boolean success = storeEmployeeService.remove(Convert.toList(Integer.class, employeeIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

