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
import com.suisung.shopsuite.shop.model.entity.UserProductBuy;
import com.suisung.shopsuite.shop.model.req.UserProductBuyAddReq;
import com.suisung.shopsuite.shop.model.req.UserProductBuyEditReq;
import com.suisung.shopsuite.shop.model.req.UserProductBuyListReq;
import com.suisung.shopsuite.shop.service.UserProductBuyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户商品购买数量历史表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-10
 */
@Api(tags = "用户商品购买数量历史表")
@RestController
@RequestMapping("/manage/shop/userProductBuy")
public class UserProductBuyController extends BaseController {
    @Autowired
    private UserProductBuyService userProductBuyService;

    @PreAuthorize("hasAuthority('/manage/shop/userProductBuy/list')")
    @ApiOperation(value = "用户商品购买数量历史表-分页列表查询", notes = "用户商品购买数量历史表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserProductBuy>> list(UserProductBuyListReq userProductBuyListReq) {
        IPage<UserProductBuy> pageList = userProductBuyService.lists(userProductBuyListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/shop/userProductBuy/detail')")
    @ApiOperation(value = "用户商品购买数量历史表-通过product_buy_id查询", notes = "用户商品购买数量历史表-通过product_buy_id查询")
    @RequestMapping(value = "/{productBuyId}", method = RequestMethod.GET)
    public CommonRes<UserProductBuy> get(@PathVariable Long productBuyId) {
        UserProductBuy userProductBuy = userProductBuyService.get(productBuyId);

        return success(userProductBuy);
    }

    @PreAuthorize("hasAuthority('/manage/shop/userProductBuy/add')")
    @ApiOperation(value = "用户商品购买数量历史表-添加", notes = "用户商品购买数量历史表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserProductBuyAddReq userProductBuyAddReq) {
        UserProductBuy userProductBuy = BeanUtil.copyProperties(userProductBuyAddReq, UserProductBuy.class);
        boolean success = userProductBuyService.add(userProductBuy);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/userProductBuy/edit')")
    @ApiOperation(value = "用户商品购买数量历史表-编辑", notes = "用户商品购买数量历史表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserProductBuyEditReq userProductBuyEditReq) {
        UserProductBuy userProductBuy = BeanUtil.copyProperties(userProductBuyEditReq, UserProductBuy.class);
        boolean success = userProductBuyService.edit(userProductBuy);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/userProductBuy/remove')")
    @ApiOperation(value = "用户商品购买数量历史表-通过product_buy_id删除", notes = "用户商品购买数量历史表-通过product_buy_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("product_buy_id") Long productBuyId) {
        boolean success = userProductBuyService.remove(productBuyId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/shop/userProductBuy/removeBatch')")
    @ApiOperation(value = "用户商品购买数量历史表-批量删除", notes = "用户商品购买数量历史表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("product_buy_id") String productBuyIds) {
        boolean success = userProductBuyService.remove(Convert.toList(Long.class, productBuyIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

