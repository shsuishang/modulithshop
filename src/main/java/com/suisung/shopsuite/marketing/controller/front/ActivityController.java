package com.suisung.shopsuite.marketing.controller.front;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.marketing.model.req.ActivityBaseListReq;
import com.suisung.shopsuite.marketing.model.res.ActivityBaseRes;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.model.req.ProductItemListReq;
import com.suisung.shopsuite.pt.model.res.ItemListRes;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "参与活动商品表-用户筛选计算")
@RestController
@RequestMapping("/front/marketing/activityBase")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityBaseService activityBaseService;

    @Resource
    private ProductIndexService productIndexService;

    @ApiOperation(value = "活动表-优惠券列表", notes = "活动表-优惠券列表")
    @RequestMapping(value = "/listVoucher", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ActivityBaseRes>> listVoucher(ActivityBaseListReq activityBaseListReq) {
        Integer userId = ContextUtil.getLoginUserId();
        activityBaseListReq.setUserId(userId);
        IPage<ActivityBaseRes> activityPage = activityBaseService.voucherList(activityBaseListReq);

        return success(activityPage);
    }

    @ApiOperation(value = "活动表", notes = "活动表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ActivityBaseRes>> list(ActivityBaseListReq activityBaseListReq) {
        Integer userId = ContextUtil.getLoginUserId();
        IPage<ActivityBaseRes> activityPage = activityBaseService.getList(activityBaseListReq);

        return success(activityPage);
    }

    @ApiOperation(value = "活动商品详情", notes = "活动商品详情")
    @RequestMapping(value = "/getActivityInfo", method = RequestMethod.GET)
    public CommonRes<ItemListRes> getActivityInfo(ProductItemListReq req) {
        ProductItemInput input = new ProductItemInput();
        BeanUtils.copyProperties(req, input);

        input.setItemId(Convert.toList(Long.class, req.getItemId()));

        ItemListRes pageList = productIndexService.listItem(input);

        return success(pageList);
    }
}
