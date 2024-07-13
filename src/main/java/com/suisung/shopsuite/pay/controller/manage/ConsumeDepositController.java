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
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pay.model.entity.ConsumeDeposit;
import com.suisung.shopsuite.pay.model.req.ConsumeDepositListReq;
import com.suisung.shopsuite.pay.model.req.ConsumeDepositOfflinePayReq;
import com.suisung.shopsuite.pay.model.req.ConsumeDepositReviewEditReq;
import com.suisung.shopsuite.pay.service.ConsumeDepositService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 支付表-支付回调callback使用-确认付款 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Api(tags = "支付表-支付回调callback使用-确认付款")
@RestController
@RequestMapping("/manage/pay/consumeDeposit")
public class ConsumeDepositController extends BaseController {
    @Autowired
    private ConsumeDepositService consumeDepositService;

    @PreAuthorize("hasAuthority('/manage/pay/consumeDeposit/list')")
    @ApiOperation(value = "支付表-支付回调callback使用-确认付款-分页列表查询", notes = "支付表-支付回调callback使用-确认付款-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ConsumeDeposit>> list(ConsumeDepositListReq consumeDepositListReq) {
        IPage<ConsumeDeposit> pageList = consumeDepositService.lists(consumeDepositListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/pay/consumeDeposit/editReview')")
    @ApiOperation(value = "支付表-收款确认", notes = "支付表-收款确认")
    @RequestMapping(value = "/editReview", method = RequestMethod.POST)
    public CommonRes<?> editReview(ConsumeDepositReviewEditReq consumeDepositReviewEditReq) {
        ConsumeDeposit consumeDeposit = BeanUtil.copyProperties(consumeDepositReviewEditReq, ConsumeDeposit.class);
        boolean success = consumeDepositService.edit(consumeDeposit);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderBase/finance')")
    @ApiOperation(value = "线下支付接口", notes = "线下支付接口")
    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    public CommonRes<?> offlinePay(ConsumeDepositOfflinePayReq req) {
        ConsumeDeposit consumeDeposit = BeanUtil.copyProperties(req, ConsumeDeposit.class);

        //交易号 == 流水号
        consumeDeposit.setDepositNo(req.getDepositTradeNo());

        List<ConsumeDeposit> deposits = consumeDepositService.find(new QueryWrapper<ConsumeDeposit>().eq("deposit_trade_no", consumeDeposit.getDepositTradeNo()));
        if (CollUtil.isNotEmpty(deposits)) {
            return fail(__("支付凭证号已经存在！"));
        }


        Long deposit_id = consumeDepositService.offlinePay(consumeDeposit);

        if (CheckUtil.isNotEmpty(deposit_id)) {
            return success(deposit_id);
        }

        return fail();
    }
}

