package com.suisung.shopsuite.account.controller.front;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserInvoice;
import com.suisung.shopsuite.account.model.req.UserInvoiceAddReq;
import com.suisung.shopsuite.account.model.req.UserInvoiceEditReq;
import com.suisung.shopsuite.account.model.req.UserInvoiceListReq;
import com.suisung.shopsuite.account.service.UserInvoiceService;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户发票管理")
@RestController
@RequestMapping("/front/account/userInvoice")
public class InvoiceController extends BaseController {

    @Autowired
    private UserInvoiceService userInvoiceService;

    @Autowired
    private ConfigBaseService configBaseService;

    @ApiOperation(value = "发票列表", notes = "发票列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserInvoice>> list(UserInvoiceListReq userInvoiceListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userInvoiceListReq.setUserId(userId);

        IPage<UserInvoice> pageList = userInvoiceService.lists(userInvoiceListReq);

        return success(pageList);
    }

    @ApiOperation(value = "用户发票管理表-通过user_invoice_id查询", notes = "用户发票管理表-通过user_invoice_id查询")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<?> get(@RequestParam("user_invoice_id") Integer userInvoiceId) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserInvoice userInvoice = userInvoiceService.get(userInvoiceId);

        if (CheckUtil.checkDataRights(userId, userInvoice, UserInvoice::getUserId)) {

            return success(userInvoice);
        }

        return fail();
    }

    @ApiOperation(value = "添加用户发票管理", notes = "添加用户发票管理")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserInvoiceAddReq userInvoiceAddReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserInvoice userInvoice = BeanUtil.copyProperties(userInvoiceAddReq, UserInvoice.class);
        userInvoice.setUserId(userId);
        boolean success = userInvoiceService.save(userInvoice);

        if (success) {
            return success(userInvoice);
        }

        return fail();
    }

    @ApiOperation(value = "用户发票管理表-通过user_invoice_id删除", notes = "用户发票管理表-通过user_invoice_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("user_invoice_id") Integer userInvoiceId) {
        boolean success = false;
        Integer userId = ContextUtil.checkLoginUserId();

        UserInvoice userInvoice = userInvoiceService.get(userInvoiceId);

        if (CheckUtil.checkDataRights(userId, userInvoice, UserInvoice::getUserId)) {
            success = userInvoiceService.remove(userInvoiceId);
        }

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "用户发票管理表-编辑", notes = "用户发票管理表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserInvoiceEditReq userInvoiceEditReq) {
        boolean success = false;
        Integer userId = ContextUtil.checkLoginUserId();
        UserInvoice userInvoice = BeanUtil.copyProperties(userInvoiceEditReq, UserInvoice.class);
        userInvoice.setUserId(userId);
        UserInvoice invoice = userInvoiceService.get(userInvoiceEditReq.getUserInvoiceId());

        if (CheckUtil.checkDataRights(userId, invoice, UserInvoice::getUserId)) {
            success = userInvoiceService.edit(userInvoice);
        }

        if (success) {
            return success(userInvoice);
        }

        return fail();
    }

    @ApiOperation(value = "获取订单页发票提示", notes = "获取订单页发票提示")
    @RequestMapping(value = "/getInvoiceTips", method = RequestMethod.GET)
    public CommonRes<?> getInvoiceTips() {
        String invoiceTips = configBaseService.getConfig("invoice_tips", "");
        return success(invoiceTips);
    }


}
