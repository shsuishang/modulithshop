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
package com.suisung.shopsuite.admin.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.admin.model.entity.UserRole;
import com.suisung.shopsuite.admin.model.req.UserRoleAddReq;
import com.suisung.shopsuite.admin.model.req.UserRoleEditReq;
import com.suisung.shopsuite.admin.model.req.UserRoleListReq;
import com.suisung.shopsuite.admin.service.UserRoleService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 权限组表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-22
 */
@Api(tags = "权限组表")
@RestController
@RequestMapping("/manage/admin/userRole")
public class UserRoleController extends BaseController {
    @Autowired
    private UserRoleService userRoleService;

    @PreAuthorize("hasAuthority('/manage/admin/userRole/list')")
    @ApiOperation(value = "权限组表-分页列表查询", notes = "权限组表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserRole>> list(UserRoleListReq userRoleListReq) {
        IPage<UserRole> pageList = userRoleService.lists(userRoleListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/admin/userRole/detail')")
    @ApiOperation(value = "权限组表-通过user_role_id查询", notes = "权限组表-通过user_role_id查询")
    @RequestMapping(value = "/{userRoleId}", method = RequestMethod.GET)
    public CommonRes<UserRole> get(@PathVariable Integer userRoleId) {
        UserRole userRole = userRoleService.get(userRoleId);

        return success(userRole);
    }

    @PreAuthorize("hasAuthority('/manage/admin/userRole/add')")
    @ApiOperation(value = "权限组表-添加", notes = "权限组表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserRoleAddReq userRoleAddReq) {
        UserRole userRole = BeanUtil.copyProperties(userRoleAddReq, UserRole.class);
        boolean success = userRoleService.add(userRole);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/admin/userRole/edit')")
    @ApiOperation(value = "权限组表-编辑", notes = "权限组表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserRoleEditReq userRoleEditReq) {
        UserRole userRole = BeanUtil.copyProperties(userRoleEditReq, UserRole.class);
        boolean success = userRoleService.edit(userRole);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/admin/userRole/remove')")
    @ApiOperation(value = "权限组表-通过user_role_id删除", notes = "权限组表-通过user_role_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("user_role_id") Integer userRoleId) {
        boolean success = userRoleService.remove(userRoleId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/admin/userRole/removeBatch')")
    @ApiOperation(value = "权限组表-批量删除", notes = "权限组表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("user_role_id") String userRoleIds) {
        boolean success = userRoleService.remove(Convert.toList(Integer.class, userRoleIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

