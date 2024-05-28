package com.suisung.shopsuite.sys.controller.front;

import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.sys.model.entity.ExpressBase;
import com.suisung.shopsuite.sys.service.ExpressBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 快递表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "快递表")
@RestController
@RequestMapping("/front/sys/express")
public class ExpressController extends BaseController {

    @Autowired
    private ExpressBaseService expressBaseService;

    @ApiOperation(value = "快递表-获取列表数据", notes = "快递表-获取列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<?> list() {
        List<ExpressBase> expressBaseList = expressBaseService.getList();
        return success(expressBaseList);
    }

}
