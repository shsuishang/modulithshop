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
import com.suisung.shopsuite.pay.model.entity.PaymentType;
import com.suisung.shopsuite.pay.model.req.PaymentTypeAddReq;
import com.suisung.shopsuite.pay.model.req.PaymentTypeEditReq;
import com.suisung.shopsuite.pay.model.req.PaymentTypeListReq;
import com.suisung.shopsuite.pay.service.PaymentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 支付方式表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Api(tags = "支付方式表")
@RestController
@RequestMapping("/manage/pay/paymentType")
public class PaymentTypeController extends BaseController {
    @Autowired
    private PaymentTypeService paymentTypeService;

    @PreAuthorize("hasAuthority('/manage/pay/paymentType/list')")
    @ApiOperation(value = "支付方式表-分页列表查询", notes = "支付方式表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<PaymentType>> list(PaymentTypeListReq paymentTypeListReq) {
        IPage<PaymentType> pageList = paymentTypeService.lists(paymentTypeListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/pay/paymentType/detail')")
    @ApiOperation(value = "支付方式表-通过payment_type_id查询", notes = "支付方式表-通过payment_type_id查询")
    @RequestMapping(value = "/{paymentTypeId}", method = RequestMethod.GET)
    public CommonRes<PaymentType> get(@PathVariable Integer paymentTypeId) {
        PaymentType paymentType = paymentTypeService.get(paymentTypeId);

        return success(paymentType);
    }

    @PreAuthorize("hasAuthority('/manage/pay/paymentType/add')")
    @ApiOperation(value = "支付方式表-添加", notes = "支付方式表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(PaymentTypeAddReq paymentTypeAddReq) {
        PaymentType paymentType = BeanUtil.copyProperties(paymentTypeAddReq, PaymentType.class);
        boolean success = paymentTypeService.add(paymentType);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/paymentType/edit')")
    @ApiOperation(value = "支付方式表-编辑", notes = "支付方式表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(PaymentTypeEditReq paymentTypeEditReq) {
        PaymentType paymentType = BeanUtil.copyProperties(paymentTypeEditReq, PaymentType.class);
        boolean success = paymentTypeService.edit(paymentType);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/paymentType/remove')")
    @ApiOperation(value = "支付方式表-通过payment_type_id删除", notes = "支付方式表-通过payment_type_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("payment_type_id") Integer paymentTypeId) {
        boolean success = paymentTypeService.remove(paymentTypeId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pay/paymentType/removeBatch')")
    @ApiOperation(value = "支付方式表-批量删除", notes = "支付方式表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("payment_type_id") String paymentTypeIds) {
        boolean success = paymentTypeService.remove(Convert.toList(Integer.class, paymentTypeIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

