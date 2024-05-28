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
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.trade.model.entity.OrderReturn;
import com.suisung.shopsuite.trade.model.req.OrderReturnEditReq;
import com.suisung.shopsuite.trade.model.req.OrderReturnListReq;
import com.suisung.shopsuite.trade.model.vo.OrderReturnVo;
import com.suisung.shopsuite.trade.service.OrderReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 * 退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Api(tags = "退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。")
@RestController
@RequestMapping("/manage/trade/orderReturn")
public class OrderReturnController extends BaseController {
    @Autowired
    private OrderReturnService orderReturnService;

    @PreAuthorize("hasAuthority('/manage/trade/orderReturn/list')")
    @ApiOperation(value = "退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。-分页列表查询", notes = "退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<OrderReturn>> list(OrderReturnListReq orderReturnListReq) {
        IPage<OrderReturn> pageList = orderReturnService.lists(orderReturnListReq);

        return success(pageList);
    }

    @ApiOperation(value = "退款退货详情", notes = "退款退货详情")
    @RequestMapping(value = "/getByReturnId", method = RequestMethod.GET)
    public CommonRes<OrderReturnVo> getByReturnId(@RequestParam("return_id") String return_id) {
        OrderReturnVo orderReturnVo = orderReturnService.getByReturnId(return_id);

        return success(orderReturnVo);
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturn/edit')")
    @ApiOperation(value = "退货单审核-卖家拒绝退款/退货", notes = "退货单审核-卖家拒绝退款/退货")
    @RequestMapping(value = "/refused", method = RequestMethod.POST)
    public CommonRes<?> refused(OrderReturnEditReq orderReturnEditReq) {
        OrderReturn orderReturn = BeanUtil.copyProperties(orderReturnEditReq, OrderReturn.class);
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        orderReturn.setStoreId(user.getStoreId());
        boolean success = orderReturnService.refused(orderReturn);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturn/edit')")
    @ApiOperation(value = "退货单审核-通过审核", notes = "退货单审核-通过审核")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public CommonRes<?> review(OrderReturnEditReq orderReturnEditReq) {
        OrderReturn orderReturn = BeanUtil.copyProperties(orderReturnEditReq, OrderReturn.class);
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        orderReturn.setStoreId(user.getStoreId());
        boolean success = orderReturnService.review(orderReturn, orderReturnEditReq.getReceivingAddress());

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturn/edit')")
    @ApiOperation(value = "退货单审核-确认收货", notes = "退货单审核-确认收货")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonRes<?> receive(@RequestParam("return_id") String return_id) {
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }
        Integer storeId = user.getStoreId();
        OrderReturn orderReturn = orderReturnService.get(return_id);
        if (true || CheckUtil.checkDataRights(storeId, orderReturn, OrderReturn::getStoreId)) {
            orderReturnService.dealWithReturn(Arrays.asList(return_id), storeId, StateCode.RETURN_PROCESS_RECEIVED, Arrays.asList(orderReturn), null);
        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        return success();
    }

    @PreAuthorize("hasAuthority('/manage/trade/orderReturn/edit')")
    @ApiOperation(value = "退货单审核-确认收款", notes = "退货单审核-确认收款")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public CommonRes<?> refund(@RequestParam("return_id") String return_id) {
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }
        Integer storeId = user.getStoreId();
        OrderReturn orderReturn = orderReturnService.get(return_id);
        if (true || CheckUtil.checkDataRights(storeId, orderReturn, OrderReturn::getStoreId)) {
            orderReturnService.dealWithReturn(Arrays.asList(return_id), storeId, StateCode.RETURN_PROCESS_REFUND, Arrays.asList(orderReturn), null);
        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        return success();
    }
}

