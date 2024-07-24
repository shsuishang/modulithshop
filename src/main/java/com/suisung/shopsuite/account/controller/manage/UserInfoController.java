package com.suisung.shopsuite.account.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.input.RegInput;
import com.suisung.shopsuite.account.model.output.UserInfoOutput;
import com.suisung.shopsuite.account.model.req.UserInfoAddReq;
import com.suisung.shopsuite.account.model.req.UserInfoEditReq;
import com.suisung.shopsuite.account.model.req.UserInfoListReq;
import com.suisung.shopsuite.account.model.req.UserInfoStateEditReq;
import com.suisung.shopsuite.account.service.LoginService;
import com.suisung.shopsuite.account.service.UserInfoService;
import com.suisung.shopsuite.common.consts.BindConnectCode;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <p>
 * 用户详细信息表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-12-09
 */
@Api(tags = "用户详细信息表")
@RestController
@RequestMapping("/manage/account/userInfo")
public class UserInfoController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LoginService loginService;

    @PreAuthorize("hasAuthority('/manage/account/userInfo/list')")
    @ApiOperation(value = "用户详细信息表-分页列表查询", notes = "用户详细信息表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes
            <BaseListRes<UserInfo>> list(UserInfoListReq userInfoListReq) {
        IPage<UserInfo> pageList = userInfoService.getList(new BaseQueryWrapper<UserInfo, UserInfoListReq>(userInfoListReq).getWrapper(), userInfoListReq.getPage(), userInfoListReq.getSize());

        return success(pageList);
    }

    @ApiOperation(value = "用户详细信息表-通过user_id查询", notes = "用户详细信息表-通过user_id查询")
    @RequestMapping(value = "/getUserData", method = RequestMethod.GET)
    public CommonRes<UserInfoOutput> getUserData(@RequestParam(value = "user_id", required = false) Integer userId) {

        if (CheckUtil.isEmpty(userId)) {
            userId = ContextUtil.checkLoginUserId();
        }
        UserInfoOutput userInfoOutput = userInfoService.getUserData(userId);

        return success(userInfoOutput);
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/add')")
    @ApiOperation(value = "用户详细信息表-添加", notes = "用户详细信息表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserInfoAddReq userInfoAddReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoAddReq, UserInfo.class);

        //手机号注册
        RegInput in = BeanUtil.copyProperties(userInfoAddReq, RegInput.class);
        in.setBindType(BindConnectCode.ACCOUNT);

        if (CheckUtil.isEmpty(userInfoAddReq.getPassword())) {
            in.setPassword("Shopsuite@2018" + UUID.randomUUID());
        }

        //in.setBindType(BindConnectCode.MOBILE);
        //in.setUserAccount(String.format("%s%s", in.getUserIntl(), in.getUserMobile()));

        Integer userId = loginService.register(in);

        //手机绑定
        ContextUser user = new ContextUser();
        user.setUserId(userId);
        Integer uid = loginService.doBindMobile(user, in.getUserIntl(), in.getUserMobile());

        userInfo.setUserId(userId);
        boolean success = userInfoService.save(userInfo);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/edit')")
    @ApiOperation(value = "用户详细信息表-编辑", notes = "用户详细信息表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserInfoEditReq userInfoEditReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoEditReq, UserInfo.class);
        boolean success = userInfoService.editUser(userInfo);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/editState')")
    @ApiOperation(value = "用户详细信息表-编辑", notes = "用户详细信息表-编辑")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(UserInfoStateEditReq userInfoStateEditReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoStateEditReq, UserInfo.class);
        boolean success = userInfoService.edit(userInfo);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/remove')")
    @ApiOperation(value = "用户详细信息表-通过user_id删除", notes = "用户详细信息表-通过user_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("user_id") Integer userId) {
        boolean success = userInfoService.removeUser(userId);

        if (success) {
            return success();
        }

        return fail();
    }


    @PreAuthorize("hasAuthority('/manage/account/userInfo/edit')")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @RequestMapping(value = "/passWordEdit", method = RequestMethod.POST)
    public CommonRes<?> passWordEdit(@RequestParam("user_id") Integer userId,
                                     @RequestParam("user_password") String userPassword) {
        boolean success = userInfoService.passWordEdit(userId, userPassword);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/edit')")
    @ApiOperation(value = "批量设置标签", notes = "批量设置标签")
    @RequestMapping(value = "/addTags", method = RequestMethod.POST)
    public CommonRes<?> addTags(@RequestParam("user_ids") String userIds,
                                @RequestParam("tag_ids") String tagIds) {
        boolean success = userInfoService.addTags(userIds, tagIds);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/list')")
    @ApiOperation(value = "导出模版", notes = "导出模版")
    @RequestMapping(value = "/exportTemp", method = {RequestMethod.POST, RequestMethod.GET})
    public void exportTemp(HttpServletResponse response) {
        userInfoService.exportTemp(response);
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/list')")
    @ApiOperation(value = "导出指定用户详细信息", notes = "导出指定用户详细信息")
    @RequestMapping(value = "/exportFile", method = RequestMethod.POST)
    public void exportFile(HttpServletResponse response, @RequestParam(name = "user_ids") String userIds) {
        userInfoService.exportFile(response, Convert.toList(Integer.class, userIds));
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/add')")
    @ApiOperation(value = "导入用户信息", notes = "导入用户信息")
    @RequestMapping(value = "/importTemp", method = RequestMethod.POST)
    public CommonRes<?> importTemp(@RequestParam MultipartFile file) throws Exception {
        userInfoService.importTemp(file);

        return success();
    }

    @PreAuthorize("hasAuthority('/manage/account/userInfo/edit')")
    @ApiOperation(value = "批量发放优惠券", notes = "批量发放优惠券")
    @RequestMapping(value = "/addVouchers", method = RequestMethod.POST)
    public CommonRes<?> addVouchers(@RequestParam(name = "user_ids") String userIds,
                                    @RequestParam(name = "activity_id") Integer activityId) {
        userInfoService.addVouchers(Convert.toList(Integer.class, userIds), activityId);

        return success();
    }
}

