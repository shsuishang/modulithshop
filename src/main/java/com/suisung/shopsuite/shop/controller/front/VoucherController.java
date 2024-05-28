package com.suisung.shopsuite.shop.controller.front;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.shop.model.entity.UserVoucher;
import com.suisung.shopsuite.shop.model.req.UserVoucherListReq;
import com.suisung.shopsuite.shop.model.res.UserVoucherRes;
import com.suisung.shopsuite.shop.model.res.VoucherCountRes;
import com.suisung.shopsuite.shop.service.UserVoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "会员优惠券")
@RestController
@RequestMapping("/front/shop/userVoucher")
public class VoucherController extends BaseController {

    @Autowired
    private UserVoucherService voucherService;

    @ApiOperation(value = "列举出不同优惠券的数量", notes = "列举出不同优惠券的数量")
    @RequestMapping(value = "/getEachVoucherNum", method = RequestMethod.GET)
    public CommonRes<VoucherCountRes> getEachVoucherNum(@RequestParam(value = "voucher_state_id", required = false) Integer voucherStateId) {
        Integer userId = ContextUtil.checkLoginUserId();
        VoucherCountRes voucherCountRes = voucherService.getEachVoucherNum(voucherStateId, userId);

        return success(voucherCountRes);
    }

    @ApiOperation(value = "会员优惠券列表", notes = "会员优惠券列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserVoucherRes>> list(UserVoucherListReq voucherListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        voucherListReq.setUserId(userId);
        IPage<UserVoucherRes> voucherResIPage = voucherService.getList(voucherListReq);

        return success(voucherResIPage);
    }

    @ApiOperation(value = "领取代金券", notes = "领取代金券")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(@RequestParam("activity_id") Integer activityId) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserVoucher success = voucherService.addVoucher(activityId, userId);

        return success(success);
    }

    @ApiOperation(value = "读取优惠券信息", notes = "读取优惠券信息")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<UserVoucherRes> get(@RequestParam(value = "activity_id") Integer activityId,
                                         @RequestParam(value = "user_voucher_id", required = false) Integer userVoucherId,
                                         @RequestParam(value = "currency_id", required = false) Integer currencyId) {
        UserVoucherRes userVoucherRes = voucherService.getVoucher(activityId, userVoucherId, currencyId);

        return success(userVoucherRes);
    }


}
