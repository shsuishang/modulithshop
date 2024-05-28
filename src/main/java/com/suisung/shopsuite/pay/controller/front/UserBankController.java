package com.suisung.shopsuite.pay.controller.front;


import cn.hutool.core.bean.BeanUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.pay.model.entity.UserBankCard;
import com.suisung.shopsuite.pay.model.req.UserBankCardEditReq;
import com.suisung.shopsuite.pay.model.res.BankRes;
import com.suisung.shopsuite.pay.service.UserBankCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "提现账户")
@RestController
@RequestMapping("/front/pay/userBank")
public class UserBankController extends BaseController {

    @Autowired
    private UserBankCardService bankCardService;


    @ApiOperation(value = "银行-账号列表", notes = "银行-账号列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BankRes> list() {
        Integer userId = ContextUtil.getLoginUserId();
        BankRes bankRes = bankCardService.getList(userId);

        return success(bankRes);
    }

    @ApiOperation(value = "提现账户-结算账户", notes = "提现账户-结算账户")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<UserBankCard> get(@RequestParam(name = "user_bank_id") Integer userBankId) {
        Integer userId = ContextUtil.getLoginUserId();
        UserBankCard userBankCard = bankCardService.getUserBank(userBankId, userId);

        return success(userBankCard);
    }

    @ApiOperation(value = "收款账号-添加/编辑", notes = "收款账号-添加/编辑")
    @RequestMapping(value = "/addOrEditUserBank", method = RequestMethod.POST)
    public CommonRes<?> addOrEditUserBank(UserBankCardEditReq userBankCardEditReq) {
        UserBankCard userBankCard = BeanUtil.copyProperties(userBankCardEditReq, UserBankCard.class);
        Integer userId = ContextUtil.getLoginUserId();
        userBankCard.setUserId(userId);
        boolean success = bankCardService.addOrEditUserBank(userBankCard);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "账号列表-通过user_bank_id删除", notes = "结算账户表-通过user_bank_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("user_bank_id") Integer userBankId) {
        boolean success = bankCardService.remove(userBankId);

        if (success) {
            return success();
        }

        return fail();
    }

}
