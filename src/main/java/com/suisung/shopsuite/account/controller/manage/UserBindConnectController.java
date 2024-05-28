package com.suisung.shopsuite.account.controller.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserBindConnect;
import com.suisung.shopsuite.account.model.req.UserBindConnectListReq;
import com.suisung.shopsuite.account.service.UserBindConnectService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户等级表-平台")
@RestController
@RequestMapping("/manage/account/userBindConnect")
public class UserBindConnectController extends BaseController {

    @Autowired
    private UserBindConnectService bindConnectService;

    @PreAuthorize("hasAuthority('/manage/account/userInfo/list')")
    @ApiOperation(value = "用户绑定表-平台-分页列表查询", notes = "用户绑定表-平台-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserBindConnect>> list(UserBindConnectListReq userBindConnectListReq) {
        IPage<UserBindConnect> pageList = bindConnectService.lists(userBindConnectListReq);

        return success(pageList);
    }
}
