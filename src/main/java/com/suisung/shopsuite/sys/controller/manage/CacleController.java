package com.suisung.shopsuite.sys.controller.manage;

import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.service.RedisService;
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
    private RedisService redisService;

    @PreAuthorize("hasAuthority('/manage/sys/cache/clean')")
    @ApiOperation(value = "清理服务端缓存", notes = "清理服务端缓存")
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public CommonRes<?> clean() {
        Set<String> c_keys = redisService.keys("c:1001:*");
        Set<String> b_keys = redisService.keys("menuTree:*");
        Set<String> s_keys = redisService.keys("productCategoryTree:*");
        Set<String> cf_keys = redisService.keys("configInfo:*");
        Set<String> menu_keys = redisService.keys("menuTree:*");
        Set<String> pc_keys = redisService.keys("productCategoryList:*");
        Set<String> db_keys = redisService.keys("districtBaseTree:*");

        redisService.del(c_keys);
        redisService.del(b_keys);
        redisService.del(s_keys);
        redisService.del(cf_keys);
        redisService.del(menu_keys);
        redisService.del(pc_keys);
        redisService.del(db_keys);


        return success(__("缓存清理成功"));
    }

}
