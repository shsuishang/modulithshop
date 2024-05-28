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
package com.suisung.shopsuite.account.controller.front;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserDeliveryAddress;
import com.suisung.shopsuite.account.model.req.UserDeliveryAddressAddReq;
import com.suisung.shopsuite.account.model.req.UserDeliveryAddressEditReq;
import com.suisung.shopsuite.account.model.req.UserDeliveryAddressListReq;
import com.suisung.shopsuite.account.service.UserDeliveryAddressService;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户地址表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Api(tags = "用户地址表")
@RestController
@RequestMapping("/front/account/userDeliveryAddress")
public class DeliveryAddressController extends BaseController {
    @Autowired
    private UserDeliveryAddressService userDeliveryAddressService;

    @ApiOperation(value = "用户地址表", notes = "用户地址表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserDeliveryAddress>> list(UserDeliveryAddressListReq userDeliveryAddressListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userDeliveryAddressListReq.setUserId(userId);

        IPage<UserDeliveryAddress> pageList = userDeliveryAddressService.lists(userDeliveryAddressListReq);

        return success(pageList);
    }

    @ApiOperation(value = "用户地址表-通过ud_id查询", notes = "用户地址表-通过ud_id查询")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<?> get(@RequestParam("ud_id") Integer udId) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserDeliveryAddress userDeliveryAddress = userDeliveryAddressService.get(udId);

        if (CheckUtil.checkDataRights(userId, userDeliveryAddress, UserDeliveryAddress::getUserId)) {
            return success(userDeliveryAddress);
        } else {

        }

        return fail();
    }

    @ApiOperation(value = "用户地址表", notes = "用户地址表")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserDeliveryAddressAddReq userDeliveryAddressEditReq) {
        Integer userId = ContextUtil.checkLoginUserId();

        UserDeliveryAddress userDeliveryAddress = BeanUtil.copyProperties(userDeliveryAddressEditReq, UserDeliveryAddress.class);
        userDeliveryAddress.setUserId(userId);
        boolean success = userDeliveryAddressService.save(userDeliveryAddress);

        if (success) {
            return success(userDeliveryAddress);
        }

        return fail();
    }


    @ApiOperation(value = "用户地址表-编辑", notes = "用户地址表-编辑")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonRes<?> edit(UserDeliveryAddressEditReq userDeliveryAddressEditReq) {
        boolean success = true;
        Integer userId = ContextUtil.checkLoginUserId();

        UserDeliveryAddress userDeliveryAddress = BeanUtil.copyProperties(userDeliveryAddressEditReq, UserDeliveryAddress.class);
        userDeliveryAddress.setUserId(userId);

        UserDeliveryAddress address = userDeliveryAddressService.get(userDeliveryAddressEditReq.getUdId());

        if (CheckUtil.checkDataRights(userId, address, UserDeliveryAddress::getUserId)) {
            success = userDeliveryAddressService.save(userDeliveryAddress);
        } else {
            success = false;
        }

        if (success) {
            return success(userDeliveryAddress);
        }

        return fail();
    }

    @ApiOperation(value = "用户地址表-通过ud_id删除", notes = "用户地址表-通过ud_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("ud_id") Integer udId) {
        boolean success = true;
        Integer userId = ContextUtil.checkLoginUserId();

        UserDeliveryAddress address = userDeliveryAddressService.get(udId);

        if (CheckUtil.checkDataRights(userId, address, UserDeliveryAddress::getUserId)) {
            success = userDeliveryAddressService.remove(udId);
        } else {
            success = false;
        }

        if (success) {
            return success();
        }

        return fail();
    }
}

