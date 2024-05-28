package com.suisung.shopsuite.shop.controller.front;

import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.shop.model.res.SearchInfoRes;
import com.suisung.shopsuite.shop.service.UserSearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商城移动端首页
 * </p>
 *
 * @author Xinze
 * @since 2021-05-28
 */
@Api(tags = "商城移动端首页")
@RestController
@RequestMapping("/front/shop/mobile")
public class MobileController extends BaseController {

    @Autowired
    private UserSearchHistoryService userSearchHistoryService;

    @ApiOperation(value = "返回搜索关键词", notes = "用户最新搜索记录及系统推荐搜索关键词")
    @RequestMapping(value = "/getSearchInfo", method = RequestMethod.GET)
    public CommonRes<SearchInfoRes> getSearchInfo() {
        return success(userSearchHistoryService.getSearchInfo());
    }

}
