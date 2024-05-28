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
package com.suisung.shopsuite.trade.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.trade.model.entity.OrderReturnReason;
import com.suisung.shopsuite.trade.model.req.OrderReturnReasonAddReq;
import com.suisung.shopsuite.trade.model.req.OrderReturnReasonEditReq;
import com.suisung.shopsuite.trade.model.req.OrderReturnReasonListReq;
import com.suisung.shopsuite.trade.service.OrderReturnReasonService;
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
 * 退货原因表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-10-30
 */
@Api(tags = "退货原因表")
@RestController
@RequestMapping("/manage/trade/orderReturnReason")
public class OrderReturnReasonController extends BaseController {
    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    @ApiOperation(value = "退货原因表-分页列表查询", notes = "退货原因表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderReturnReason>> list(OrderReturnReasonListReq orderReturnReasonListReq) {
        IPage<OrderReturnReason> pageList = orderReturnReasonService.lists(orderReturnReasonListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturnReason/add')")
    @ApiOperation(value = "退货原因表-添加", notes = "退货原因表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(OrderReturnReasonAddReq orderReturnReasonAddReq) {
        OrderReturnReason orderReturnReason = BeanUtil.copyProperties(orderReturnReasonAddReq, OrderReturnReason.class);
        boolean success = orderReturnReasonService.add(orderReturnReason);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturnReason/edit')")
    @ApiOperation(value = "退货原因表-编辑", notes = "退货原因表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(OrderReturnReasonEditReq orderReturnReasonEditReq) {
        OrderReturnReason orderReturnReason = BeanUtil.copyProperties(orderReturnReasonEditReq, OrderReturnReason.class);
        boolean success = orderReturnReasonService.edit(orderReturnReason);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturnReason/remove')")
    @ApiOperation(value = "退货原因表-通过return_reason_id删除", notes = "退货原因表-通过return_reason_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("return_reason_id") Integer returnReasonId) {
        boolean success = orderReturnReasonService.remove(returnReasonId);

        if (success) {
            return success();
        }

        return fail();
    }
}

