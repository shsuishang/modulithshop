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
package com.suisung.shopsuite.pay.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.pay.model.entity.RechargeLevel;
import com.suisung.shopsuite.pay.model.req.RechargeLevelAddReq;
import com.suisung.shopsuite.pay.model.req.RechargeLevelEditReq;
import com.suisung.shopsuite.pay.model.req.RechargeLevelListReq;
import com.suisung.shopsuite.pay.service.RechargeLevelService;
import com.suisung.shopsuite.common.utils.I18nUtil;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 定额充值表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2024-07-16
 */
@Api(tags = "定额充值表")
@RestController
@RequestMapping("/manage/pay/rechargeLevel")
public class RechargeLevelController extends BaseController {
    @Autowired
    private RechargeLevelService rechargeLevelService;

    @PreAuthorize("hasAuthority('/manage/pay/rechargeLevel/list')")
    @ApiOperation(value = "定额充值表-分页列表查询", notes = "定额充值表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes <BaseListRes<RechargeLevel>> list(RechargeLevelListReq rechargeLevelListReq) {
        IPage<RechargeLevel> pageList = rechargeLevelService.lists(rechargeLevelListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/pay/rechargeLevel/detail')")
    @ApiOperation(value = "定额充值表-通过recharge_level_id查询", notes = "定额充值表-通过recharge_level_id查询")
    @RequestMapping(value = "/{rechargeLevelId}", method = RequestMethod.GET)
    public CommonRes<RechargeLevel> get(@PathVariable Integer rechargeLevelId) {
        RechargeLevel rechargeLevel = rechargeLevelService.get(rechargeLevelId);

        return success(rechargeLevel);
    }

    @PreAuthorize("hasAuthority('/manage/pay/rechargeLevel/add')")
    @ApiOperation(value = "定额充值表-添加", notes = "定额充值表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(RechargeLevelAddReq rechargeLevelAddReq) {
        RechargeLevel rechargeLevel = BeanUtil.copyProperties(rechargeLevelAddReq, RechargeLevel.class);
        boolean success = rechargeLevelService.add(rechargeLevel);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/rechargeLevel/edit')")
    @ApiOperation(value = "定额充值表-编辑", notes = "定额充值表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(RechargeLevelEditReq rechargeLevelEditReq) {
        RechargeLevel rechargeLevel = BeanUtil.copyProperties(rechargeLevelEditReq, RechargeLevel.class);
        boolean success = rechargeLevelService.edit(rechargeLevel);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/rechargeLevel/remove')")
    @ApiOperation(value = "定额充值表-通过recharge_level_id删除", notes = "定额充值表-通过recharge_level_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("recharge_level_id") Integer rechargeLevelId) {
        boolean success = rechargeLevelService.remove(rechargeLevelId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/rechargeLevel/removeBatch')")
    @ApiOperation(value = "定额充值表-批量删除", notes = "定额充值表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("recharge_level_id") String rechargeLevelIds) {
        boolean success = rechargeLevelService.remove(Convert.toList(Integer.class, rechargeLevelIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

