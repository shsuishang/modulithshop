package com.suisung.shopsuite.account.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserMessage;
import com.suisung.shopsuite.account.model.input.UserMessageAddInput;
import com.suisung.shopsuite.account.model.req.UserMessageListReq;
import com.suisung.shopsuite.account.model.res.MessageRes;
import com.suisung.shopsuite.account.model.res.UserMessageRes;
import com.suisung.shopsuite.account.service.UserMessageService;
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

import java.util.List;

/**
 * <p>
 * 用户站内信 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-27
 */
@Api(tags = "用户站内信")
@RestController
@RequestMapping("/front/account/userMessage")
public class MessageController extends BaseController {

    @Autowired
    private UserMessageService messageService;

    @ApiOperation(value = "IM配置", notes = "IM配置接口")
    @RequestMapping(value = "/getImConfig", method = RequestMethod.GET)
    public CommonRes<?> getImConfig() {
        return success();
    }

    @ApiOperation(value = "客服配置", notes = "客服配置接口")
    @RequestMapping(value = "/getKefuConfig", method = RequestMethod.GET)
    public CommonRes<?> getKefuConfig() {
        return success();
    }

    @ApiOperation(value = "用户通知消息", notes = "用户消息列表接口")
    @RequestMapping(value = "/getNotice", method = RequestMethod.GET)
    public CommonRes<List<UserMessage>> getNotice() {
        Integer userId = ContextUtil.checkLoginUserId();
        List<UserMessage> userMessages = messageService.getNotice(userId);

        return success(userMessages);
    }

    @ApiOperation(value = "用户通知消息数量", notes = "用户通知消息数量")
    @RequestMapping(value = "/getMsgCount", method = RequestMethod.GET)
    public CommonRes<UserMessageRes> getMsgCount(@RequestParam(value = "recently_flag", required = false) Integer recentlyFlag) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserMessageRes userMessageRes = messageService.getMsgCount(recentlyFlag, userId);

        return success(userMessageRes);
    }

    @ApiOperation(value = "短消息列表数据", notes = "短消息列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserMessageRes>> list(UserMessageListReq userMessageListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userMessageListReq.setUserId(userId);
        IPage<UserMessageRes> pageList = messageService.getList(userMessageListReq);

        return success(pageList);
    }

    @ApiOperation(value = "读取短消息", notes = "读取短消息")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonRes<UserMessage> get(@RequestParam(value = "message_id") Integer messageId) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserMessage userMessage = messageService.getById(messageId, userId);

        return success(userMessage);
    }

    @ApiOperation(value = "设置为已读", notes = "设置为已读")
    @RequestMapping(value = "/setRead", method = RequestMethod.POST)
    public CommonRes<?> setRead(@RequestParam(value = "message_id", required = false) Integer messageId,
                                @RequestParam(value = "user_other_id", required = false) Integer userOtherId) {
        Integer userId = ContextUtil.checkLoginUserId();
        boolean success = messageService.setRead(messageId, userOtherId, userId);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "添加短消息", notes = "添加短消息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<UserMessageRes> add(UserMessageAddInput messageAddInput) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserMessageRes userMessageRes = messageService.addMessage(messageAddInput, userId);

        return success(userMessageRes);
    }

    @ApiOperation(value = "读取分页列表", notes = "读取分页列表")
    @RequestMapping(value = "/listChatMsg", method = RequestMethod.GET)
    public CommonRes<BaseListRes<MessageRes>> listChatMsg(UserMessageListReq userMessageListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userMessageListReq.setUserId(userId);
        IPage<MessageRes> pageList = messageService.listChatMsg(userMessageListReq);

        return success(pageList);
    }


    @ApiOperation(value = "消息中心-信息数", notes = "消息中心-信息数")
    @RequestMapping(value = "/getMessageNum", method = RequestMethod.GET)
    public CommonRes<UserMessageRes> getMessageNum(UserMessageListReq userMessageListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userMessageListReq.setUserId(userId);
        UserMessageRes userMessageRes = messageService.getMessageNum(userMessageListReq);

        return success(userMessageRes);
    }
}

