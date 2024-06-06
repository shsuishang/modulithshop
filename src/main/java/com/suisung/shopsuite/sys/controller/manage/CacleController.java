package com.suisung.shopsuite.sys.controller.manage;

import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 清理服务端缓存 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Api(tags = "清理服务端缓存")
@RestController
@RequestMapping("/manage/sys/cache")
public class CacleController extends BaseController {

    @Autowired
    private ConfigBaseService configBaseService;

    @PreAuthorize("hasAuthority('/manage/sys/cache/clean')")
    @ApiOperation(value = "清理服务端缓存", notes = "清理服务端缓存")
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public CommonRes<?> clean() {
        configBaseService.cleanCache();

        return success(__("缓存清理成功"));
    }

}
