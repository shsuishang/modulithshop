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
package com.suisung.shopsuite.shop.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.shop.model.entity.StoreExpressLogistics;
import com.suisung.shopsuite.shop.model.req.StoreExpressLogisticsAddReq;
import com.suisung.shopsuite.shop.model.req.StoreExpressLogisticsEditReq;
import com.suisung.shopsuite.shop.model.req.StoreExpressLogisticsListReq;
import com.suisung.shopsuite.shop.model.req.StoreExpressLogisticsStateEditReq;
import com.suisung.shopsuite.shop.service.StoreExpressLogisticsService;
import com.suisung.shopsuite.trade.service.OrderLogisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * <p>
 * 物流 = shop_store_express 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-05-19
 */
@Api(tags = "物流 = shop_store_express")
@RestController
@RequestMapping("/manage/shop/storeExpressLogistics")
public class StoreExpressLogisticsController extends BaseController {
    @Autowired
    private StoreExpressLogisticsService storeExpressLogisticsService;

    @Autowired
    private OrderLogisticsService orderLogisticsService;

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/list')")
    @ApiOperation(value = "物流 = shop_store_express-分页列表查询", notes = "物流 = shop_store_express-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<StoreExpressLogistics>> list(StoreExpressLogisticsListReq storeExpressLogisticsListReq) {
        IPage<StoreExpressLogistics> pageList = storeExpressLogisticsService.lists(storeExpressLogisticsListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/detail')")
    @ApiOperation(value = "物流 = shop_store_express-通过logistics_id查询", notes = "物流 = shop_store_express-通过logistics_id查询")
    @RequestMapping(value = "/{logisticsId}", method = RequestMethod.GET)
    public CommonRes<StoreExpressLogistics> get(@PathVariable Integer logisticsId) {
        StoreExpressLogistics storeExpressLogistics = storeExpressLogisticsService.get(logisticsId);

        return success(storeExpressLogistics);
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/add')")
    @ApiOperation(value = "物流 = shop_store_express-添加", notes = "物流 = shop_store_express-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(StoreExpressLogisticsAddReq storeExpressLogisticsAddReq) {
        StoreExpressLogistics storeExpressLogistics = BeanUtil.copyProperties(storeExpressLogisticsAddReq, StoreExpressLogistics.class);
        boolean success = storeExpressLogisticsService.saveOrUpdate(storeExpressLogistics);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/edit')")
    @ApiOperation(value = "物流 = shop_store_express-编辑", notes = "物流 = shop_store_express-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(StoreExpressLogisticsEditReq storeExpressLogisticsEditReq) {
        StoreExpressLogistics storeExpressLogistics = BeanUtil.copyProperties(storeExpressLogisticsEditReq, StoreExpressLogistics.class);
        boolean success = storeExpressLogisticsService.saveOrUpdate(storeExpressLogistics);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/remove')")
    @ApiOperation(value = "物流 = shop_store_express-通过logistics_id删除", notes = "物流 = shop_store_express-通过logistics_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("logistics_id") Integer logisticsId) {
        boolean success = storeExpressLogisticsService.remove(logisticsId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/removeBatch')")
    @ApiOperation(value = "物流 = shop_store_express-批量删除", notes = "物流 = shop_store_express-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("logistics_id") String logisticsIds) {
        boolean success = storeExpressLogisticsService.remove(Convert.toList(Integer.class, logisticsIds));

        if (success) {
            return success();
        }

        return fail();
    }


    @ApiOperation(value = "物流 = 查看物流", notes = "物流 = 查看物流")
    @RequestMapping(value = "/returnLogistics", method = RequestMethod.GET)
    public CommonRes<Map> returnLogistics(@RequestParam("return_tracking_name") String returnTrackingName,
                                          @RequestParam("return_tracking_number") String returnTrackingNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map map = orderLogisticsService.returnLogistics(returnTrackingName, returnTrackingNumber);

        return success(map);
    }

    @PreAuthorize("hasAuthority('/manage/shop/storeExpressLogistics/editState')")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(StoreExpressLogisticsStateEditReq logisticsStateEditReq) {
        StoreExpressLogistics storeExpressLogistics = BeanUtil.copyProperties(logisticsStateEditReq, StoreExpressLogistics.class);
        boolean success = storeExpressLogisticsService.edit(storeExpressLogistics);

        if (success) {
            return success();
        }

        return fail();
    }
}

