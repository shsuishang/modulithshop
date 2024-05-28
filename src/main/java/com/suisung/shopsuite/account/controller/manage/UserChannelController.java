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
package com.suisung.shopsuite.account.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserChannel;
import com.suisung.shopsuite.account.model.req.UserChannelAddReq;
import com.suisung.shopsuite.account.model.req.UserChannelEditReq;
import com.suisung.shopsuite.account.model.req.UserChannelListReq;
import com.suisung.shopsuite.account.service.UserChannelService;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户渠道表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-10-24
 */
@Api(tags = "用户渠道表")
@RestController
@RequestMapping("/manage/account/userChannel")
public class UserChannelController extends BaseController {
    @Autowired
    private UserChannelService userChannelService;

    @PreAuthorize("hasAuthority('/manage/account/userChannel/list')")
    @ApiOperation(value = "用户渠道表-分页列表查询", notes = "用户渠道表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserChannel>> list(UserChannelListReq userChannelListReq) {
        IPage<UserChannel> pageList = userChannelService.lists(userChannelListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/account/userChannel/detail')")
    @ApiOperation(value = "用户渠道表-通过user_channel_id查询", notes = "用户渠道表-通过user_channel_id查询")
    @RequestMapping(value = "/{userChannelId}", method = RequestMethod.GET)
    public CommonRes<UserChannel> get(@PathVariable Integer userChannelId) {
        UserChannel userChannel = userChannelService.get(userChannelId);

        return success(userChannel);
    }

    @PreAuthorize("hasAuthority('/manage/account/userChannel/add')")
    @ApiOperation(value = "用户渠道表-添加", notes = "用户渠道表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserChannelAddReq userChannelAddReq) {
        UserChannel userChannel = BeanUtil.copyProperties(userChannelAddReq, UserChannel.class);
        boolean success = userChannelService.add(userChannel);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userChannel/edit')")
    @ApiOperation(value = "用户渠道表-编辑", notes = "用户渠道表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserChannelEditReq userChannelEditReq) {
        UserChannel userChannel = BeanUtil.copyProperties(userChannelEditReq, UserChannel.class);
        boolean success = userChannelService.edit(userChannel);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userChannel/remove')")
    @ApiOperation(value = "用户渠道表-通过user_channel_id删除", notes = "用户渠道表-通过user_channel_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("user_channel_id") Integer userChannelId) {
        boolean success = userChannelService.remove(userChannelId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userChannel/removeBatch')")
    @ApiOperation(value = "用户渠道表-批量删除", notes = "用户渠道表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("user_channel_id") String userChannelIds) {
        boolean success = userChannelService.remove(Convert.toList(Integer.class, userChannelIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

