package com.suisung.shopsuite.sys.controller.front;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.sys.model.entity.FeedbackBase;
import com.suisung.shopsuite.sys.model.req.FeedbackBaseAddReq;
import com.suisung.shopsuite.sys.model.req.FeedbackBaseListReq;
import com.suisung.shopsuite.sys.model.res.FeedbackTypeRes;
import com.suisung.shopsuite.sys.service.FeedbackBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户反馈反馈")
@RestController
@RequestMapping("/front/sys/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    private FeedbackBaseService feedbackService;

    @ApiOperation(value = "平台反馈-举报", notes = "平台反馈-举报")
    @RequestMapping(value = "/getCategory", method = RequestMethod.GET)
    public CommonRes<List<FeedbackTypeRes>> getCategory() {
        List<FeedbackTypeRes> feedbackTypeResList = feedbackService.getCategory();

        return success(feedbackTypeResList);
    }

    @ApiOperation(value = "平台反馈-反馈列表", notes = "平台反馈-反馈列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<FeedbackBase>> lists(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        FeedbackBaseListReq feedbackBaseListReq = new FeedbackBaseListReq();
        feedbackBaseListReq.setPage(page);
        feedbackBaseListReq.setSize(size);
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }
        feedbackBaseListReq.setUserId(user.getUserId());
        IPage<FeedbackBase> pageList = feedbackService.lists(feedbackBaseListReq);

        return success(pageList);
    }

    @ApiOperation(value = "添加平台反馈-举报", notes = "添加平台反馈-举报")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(FeedbackBaseAddReq feedbackBaseAddReq) {
        FeedbackBase feedbackBase = BeanUtil.copyProperties(feedbackBaseAddReq, FeedbackBase.class);
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            throw new BusinessException(ResultCode.NEED_LOGIN);
        }
        feedbackBase.setUserId(user.getUserId());
        feedbackBase.setUserNickname(user.getUserNickname());

        boolean result = feedbackService.add(feedbackBase);

        if (result) {
            return success();
        }

        return fail();
    }

}
