package com.suisung.shopsuite.pay.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pay.model.entity.UserExpHistory;
import com.suisung.shopsuite.pay.model.req.UserExpHistoryListReq;
import com.suisung.shopsuite.pay.model.res.SignInfoRes;
import com.suisung.shopsuite.pay.model.res.UserResourceRes;
import com.suisung.shopsuite.pay.service.UserResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Api(tags = "用户资源表-资金账户表 前端控制器")
@RestController
@RequestMapping("/front/pay/userResource")
public class ResourceController extends BaseController {

    @Autowired
    private UserResourceService userResourceService;

    @ApiOperation(value = "获取签到基本信息", notes = "获取签到基本信息")
    @RequestMapping(value = "/getSignInfo", method = RequestMethod.GET)
    public CommonRes<SignInfoRes> getSignInfo(@RequestParam(name = "user_id") Integer userId) {
        userId = ContextUtil.checkLoginUserId();

        return success(userResourceService.getSignInfo(userId));
    }

    @ApiOperation(value = "签到", notes = "签到")
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public CommonRes signIn() {
        Integer userId = ContextUtil.checkLoginUserId();

        userResourceService.sign(userId);

        return success(__("签到成功"));
    }

    @ApiOperation(value = "签到", notes = "签到")
    @RequestMapping(value = "/signState", method = RequestMethod.GET)
    public CommonRes signState() {
        Integer userId = ContextUtil.checkLoginUserId();

        if (userResourceService.getSignState(userId)) {
            return success();
        } else {
            return fail();
        }
    }

    @ApiOperation(value = "用户经验列表", notes = "账户余额信息")
    @RequestMapping(value = "/listsExp", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserExpHistory>> listsExp(UserExpHistoryListReq userExpHistoryListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userExpHistoryListReq.setUserId(userId);
        IPage<UserExpHistory> pageList = userResourceService.listsExp(userExpHistoryListReq);

        return success(pageList);
    }

    @ApiOperation(value = "获取用户资源信息", notes = "获取用户资源信息")
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public CommonRes<UserResourceRes> resource() {
        Integer userId = ContextUtil.checkLoginUserId();

        return success(userResourceService.resource(userId));
    }
}
