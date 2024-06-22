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
package com.suisung.shopsuite.trade.controller.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.trade.model.entity.UserCart;
import com.suisung.shopsuite.trade.model.input.CartAddInput;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.input.UserCartSelectInput;
import com.suisung.shopsuite.trade.model.output.CheckoutOutput;
import com.suisung.shopsuite.trade.model.req.*;
import com.suisung.shopsuite.trade.model.vo.CartBatVo;
import com.suisung.shopsuite.trade.model.vo.CheckoutItemVo;
import com.suisung.shopsuite.trade.service.UserCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-26
 */
@Api(tags = "购物车表")
@RestController
@RequestMapping("/front/trade/cart")
public class CartController extends BaseController {
    @Autowired
    private UserCartService userCartService;

    @ApiOperation(value = "购物车表-分页列表查询", notes = "购物车表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<CheckoutOutput> list(UserCartListReq userCartListReq) {
        Integer userId = ContextUtil.checkLoginUserId();

        userCartListReq.setUserId(userId);

        CheckoutOutput list = userCartService.getList(userCartListReq);

        return success(list);
    }

    @ApiOperation(value = "购物车表-添加", notes = "购物车表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(UserCartAddReq userCartAddReq) {
        Integer userId = ContextUtil.checkLoginUserId();

        CartAddInput userCart = BeanUtil.copyProperties(userCartAddReq, CartAddInput.class);
        userCart.setUserId(userId);

        boolean success = userCartService.addCart(userCart);

        if (success) {
            return success(userCart);
        }

        return fail();
    }

    @ApiOperation(value = "购物车表-编辑", notes = "购物车表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(UserCartEditReq userCartEditReq) {
        Integer userId = ContextUtil.checkLoginUserId();

        UserCart userCart = BeanUtil.copyProperties(userCartEditReq, UserCart.class);
        boolean success = userCartService.edit(userCart);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "购物车表-通过cart_id删除", notes = "购物车表-通过cart_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("cart_id") Long cartId) {
        Integer userId = ContextUtil.checkLoginUserId();

        boolean success = userCartService.remove(cartId);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "购物车表-批量删除", notes = "购物车表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("cart_id") String cartIds) {
        Integer userId = ContextUtil.checkLoginUserId();

        boolean success = userCartService.remove(Convert.toList(Long.class, cartIds));

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "修改购物车数量", notes = "修改购物车数量")
    @RequestMapping(value = "/editQuantity", method = RequestMethod.POST)
    public CommonRes<?> editQuantity(UserCartEditReq userCartEditReq) {
        Integer userId = ContextUtil.checkLoginUserId();

        UserCart userCart = BeanUtil.copyProperties(userCartEditReq, UserCart.class);
        boolean success = userCartService.editQuantity(userCart, userId);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "购物车-选中商品", notes = "购物车-选中商品")
    @RequestMapping(value = "/sel", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonRes<?> sel(UserCartSelectReq req) {
        Integer userId = ContextUtil.checkLoginUserId();

        UserCartSelectInput input = BeanUtil.copyProperties(req, UserCartSelectInput.class);
        input.setUserId(userId);

        boolean success = userCartService.sel(input);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "批量添加购物车", notes = "批量添加购物车")
    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public CommonRes<?> addBatch(UserCartAddBatReq userCartAddReq) {
        Integer userId = ContextUtil.checkLoginUserId();

        List<CartBatVo> cartBatVos = JSONUtil.parseArray(userCartAddReq.getPar(), CartBatVo.class);

        boolean success = false;
        for (CartBatVo cartBatVo : cartBatVos) {
            CartAddInput userCart = new CartAddInput();
            userCart.setItemId(cartBatVo.getItemId());
            userCart.setCartQuantity(cartBatVo.getQuantity());
            userCart.setUserId(userId);

            success = userCartService.addCart(userCart);
        }

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "结算  确认订单- - 收货地址 支付方式 配送方式  配送时间 发票  ， 可选优惠券，礼品卡等等从 虚拟商品不经过购物车", notes = "结算")
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public CommonRes<?> checkout(OrderCheckoutReq req) {
        Integer userId = ContextUtil.checkLoginUserId();

        CheckoutInput input = BeanUtil.copyProperties(req, CheckoutInput.class);

        List<CheckoutItemVo> items = new ArrayList<>();

        //优惠券
        List<Integer> ts = Convert.toList(Integer.class, req.getUserVoucherIds());

        //处理数据
        input.setUserId(userId);
        input.setItems(items);
        input.setUserVoucherIds(ts);

        List<String> itemInfoRow = StrUtil.split(req.getCartId(), ",");

        for (String item : itemInfoRow) {
            long[] item_row = StrUtil.splitToLong(item, "|");

            if (item_row[1] <= 0) {
                throw new BusinessException(__("购买数量最低为 1 哦~"));
            }

            CheckoutItemVo checkoutItemVo = new CheckoutItemVo();
            checkoutItemVo.setItemId(item_row[0]);
            checkoutItemVo.setCartQuantity(Convert.toInt(item_row[1]));
            checkoutItemVo.setCartId(item_row[2]);

            items.add(checkoutItemVo);
        }

        CheckoutOutput success = userCartService.checkout(input);

        return success(success);
    }
}

