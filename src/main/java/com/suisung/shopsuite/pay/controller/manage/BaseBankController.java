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
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pay.model.entity.BaseBank;
import com.suisung.shopsuite.pay.model.req.BaseBankAddReq;
import com.suisung.shopsuite.pay.model.req.BaseBankEditReq;
import com.suisung.shopsuite.pay.model.req.BaseBankListReq;
import com.suisung.shopsuite.pay.service.BaseBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 结算银行记录表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-28
 */
@Api(tags = "结算银行记录表")
@RestController
@RequestMapping("/manage/pay/baseBank")
public class BaseBankController extends BaseController {
    @Autowired
    private BaseBankService baseBankService;

    @PreAuthorize("hasAuthority('/manage/pay/baseBank/list')")
    @ApiOperation(value = "结算银行记录表-分页列表查询", notes = "结算银行记录表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<BaseBank>> list(BaseBankListReq baseBankListReq) {
        IPage<BaseBank> pageList = baseBankService.lists(baseBankListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/pay/baseBank/detail')")
    @ApiOperation(value = "结算银行记录表-通过bank_id查询", notes = "结算银行记录表-通过bank_id查询")
    @RequestMapping(value = "/{bankId}", method = RequestMethod.GET)
    public CommonRes<BaseBank> get(@PathVariable Integer bankId) {
        BaseBank baseBank = baseBankService.get(bankId);

        return success(baseBank);
    }

    @PreAuthorize("hasAuthority('/manage/pay/baseBank/add')")
    @ApiOperation(value = "结算银行记录表-添加", notes = "结算银行记录表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(BaseBankAddReq baseBankAddReq) {
        BaseBank baseBank = BeanUtil.copyProperties(baseBankAddReq, BaseBank.class);
        boolean success = baseBankService.add(baseBank);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/baseBank/edit')")
    @ApiOperation(value = "结算银行记录表-编辑", notes = "结算银行记录表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(BaseBankEditReq baseBankEditReq) {
        BaseBank baseBank = BeanUtil.copyProperties(baseBankEditReq, BaseBank.class);
        boolean success = baseBankService.edit(baseBank);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/baseBank/remove')")
    @ApiOperation(value = "结算银行记录表-通过bank_id删除", notes = "结算银行记录表-通过bank_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("bank_id") Integer bankId) {
        boolean success = baseBankService.remove(bankId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/baseBank/removeBatch')")
    @ApiOperation(value = "结算银行记录表-批量删除", notes = "结算银行记录表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("bank_id") String bankIds) {
        boolean success = baseBankService.remove(Convert.toList(Integer.class, bankIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

