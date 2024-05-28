package com.suisung.shopsuite.sys.controller.front;

import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.sys.model.req.DistrictBaseListReq;
import com.suisung.shopsuite.sys.model.res.DistrictBaseRes;
import com.suisung.shopsuite.sys.service.DistrictBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 地区管理 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "地区管理")
@RestController
@RequestMapping("/front/sys/district")
public class DistrictController extends BaseController {
    @Autowired
    private DistrictBaseService districtBaseService;

    @ApiOperation(value = "区域管理", notes = "获得地址区域")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CommonRes<List<DistrictBaseRes>> tree(DistrictBaseListReq DistrictBaseListReq) {
        List<DistrictBaseRes> districtTree = districtBaseService.getTree(DistrictBaseListReq);

        return success(districtTree);
    }
}

