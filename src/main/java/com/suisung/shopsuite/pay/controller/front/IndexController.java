package com.suisung.shopsuite.pay.controller.front;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pay.model.entity.ConsumeWithdraw;
import com.suisung.shopsuite.pay.model.entity.RechargeLevel;
import com.suisung.shopsuite.pay.model.entity.UserPay;
import com.suisung.shopsuite.pay.model.req.ConsumeWithdrawListReq;
import com.suisung.shopsuite.pay.model.req.RechargeLevelListReq;
import com.suisung.shopsuite.pay.service.RechargeLevelService;
import com.suisung.shopsuite.pay.service.UserPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户资金")
@RestController
@RequestMapping("/front/pay/index")
public class IndexController extends BaseController {

    @Autowired
    private UserPayService userPayService;

    @Autowired
    private RechargeLevelService rechargeLevelService;

    @ApiOperation(value = "获取支付密码", notes = "获取支付密码")
    @RequestMapping(value = "/getPayPasswd", method = RequestMethod.GET)
    public CommonRes<UserPay> getPayPasswd() {
        Integer userId = ContextUtil.checkLoginUserId();
        UserPay userPay = userPayService.getPayPasswd(userId);

        return success(userPay);
    }

    @ApiOperation(value = "修改支付密码", notes = "修改支付密码")
    @RequestMapping(value = "/changePayPassword", method = RequestMethod.POST)
    public CommonRes<?> changePayPassword(@RequestParam(value = "old_pay_password", required = false) String oldPayPassword,
                                          @RequestParam("new_pay_password") String newPayPassword,
                                          @RequestParam("pay_password") String payPassword) {
        Integer userId = ContextUtil.checkLoginUserId();
        boolean success = userPayService.changePayPassword(oldPayPassword, newPayPassword, payPassword, userId);

        if (success) {
            return success();
        }

        return fail();
    }


    @ApiOperation(value = "定额充值表-分页列表查询", notes = "定额充值表-分页列表查询")
    @RequestMapping(value = "/listRechargeLevel", method = RequestMethod.GET)
    public CommonRes <BaseListRes<RechargeLevel>> list(RechargeLevelListReq rechargeLevelListReq) {
        IPage<RechargeLevel> pageList = rechargeLevelService.lists(rechargeLevelListReq);

        return success(pageList);
    }
}
