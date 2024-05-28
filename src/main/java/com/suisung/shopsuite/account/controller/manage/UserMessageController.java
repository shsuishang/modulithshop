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
import com.suisung.shopsuite.account.model.entity.UserMessage;
import com.suisung.shopsuite.account.model.req.UserMessageAddReq;
import com.suisung.shopsuite.account.model.req.UserMessageEditReq;
import com.suisung.shopsuite.account.model.req.UserMessageListReq;
import com.suisung.shopsuite.account.model.req.UserMessageStateEditReq;
import com.suisung.shopsuite.account.model.res.MessageNoticeRes;
import com.suisung.shopsuite.account.service.UserMessageService;
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
 * 短消息-聊天记录 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Api(tags = "短消息-聊天记录")
@RestController
@RequestMapping("/manage/account/userMessage")
public class UserMessageController extends BaseController {
    @Autowired
    private UserMessageService userMessageService;

    @PreAuthorize("hasAuthority('/manage/account/userMessage/list')")
    @ApiOperation(value = "短消息-聊天记录-分页列表查询", notes = "短消息-聊天记录-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserMessage>> list(UserMessageListReq userMessageListReq) {
        IPage<UserMessage> pageList = userMessageService.lists(userMessageListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/account/userMessage/detail')")
    @ApiOperation(value = "短消息-聊天记录-通过message_id查询", notes = "短消息-聊天记录-通过message_id查询")
    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET)
    public CommonRes<UserMessage> get(@PathVariable Integer messageId) {
        UserMessage userMessage = userMessageService.get(messageId);

        return success(userMessage);
    }

    @PreAuthorize("hasAuthority('/manage/account/userMessage/add')")
    @ApiOperation(value = "短消息-聊天记录-添加", notes = "短消息-聊天记录-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserMessageAddReq userMessageAddReq) {
        UserMessage userMessage = BeanUtil.copyProperties(userMessageAddReq, UserMessage.class);
        boolean success = userMessageService.add(userMessage);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userMessage/edit')")
    @ApiOperation(value = "短消息-聊天记录-编辑", notes = "短消息-聊天记录-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserMessageEditReq userMessageEditReq) {
        UserMessage userMessage = BeanUtil.copyProperties(userMessageEditReq, UserMessage.class);
        boolean success = userMessageService.edit(userMessage);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userMessage/editState')")
    @ApiOperation(value = "短消息-聊天记录-状态修改", notes = "短消息-聊天记录-状态修改")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(UserMessageStateEditReq userMessageStateEditReq) {
        UserMessage userMessage = BeanUtil.copyProperties(userMessageStateEditReq, UserMessage.class);
        boolean success = userMessageService.edit(userMessage);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userMessage/remove')")
    @ApiOperation(value = "短消息-聊天记录-通过message_id删除", notes = "短消息-聊天记录-通过message_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("message_id") Integer messageId) {
        boolean success = userMessageService.remove(messageId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/account/userMessage/removeBatch')")
    @ApiOperation(value = "短消息-聊天记录-批量删除", notes = "短消息-聊天记录-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("message_id") String messageIds) {
        boolean success = userMessageService.remove(Convert.toList(Integer.class, messageIds));

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "用户通知消息", notes = "用户消息列表接口")
    @RequestMapping(value = "/getNotice", method = RequestMethod.GET)
    public CommonRes<MessageNoticeRes> getNotice() {
        MessageNoticeRes noticeRes = userMessageService.getPlantFromNotice();
        return success(noticeRes);
    }

    @ApiOperation(value = "清空通知消息", notes = "用户消息列表接口")
    @RequestMapping(value = "/clearNotice", method = RequestMethod.POST)
    public CommonRes<?> clearNotice() {
        boolean success = userMessageService.clearNotice();

        if (success) {
            return success();
        }

        return fail();
    }

}

