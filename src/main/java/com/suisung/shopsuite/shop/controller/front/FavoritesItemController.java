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
package com.suisung.shopsuite.shop.controller.front;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.shop.model.entity.UserFavoritesItem;
import com.suisung.shopsuite.shop.model.req.UserFavoritesItemListReq;
import com.suisung.shopsuite.shop.model.res.UserFavoritesItemRes;
import com.suisung.shopsuite.shop.service.UserFavoritesItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 收藏的商品-根据SKU 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-08-28
 */
@Api(tags = "收藏的商品-根据SKU")
@RestController
@RequestMapping("/front/shop/userFavoritesItem")
public class FavoritesItemController extends BaseController {
    @Autowired
    private UserFavoritesItemService userFavoritesItemService;

    @ApiOperation(value = "收藏的商品列表", notes = "收藏的商品列表")
    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public CommonRes<BaseListRes<UserFavoritesItemRes>> list(UserFavoritesItemListReq userFavoritesItemListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        userFavoritesItemListReq.setUserId(userId);
        userFavoritesItemListReq.setSort("Desc");
        IPage<UserFavoritesItemRes> pageList = userFavoritesItemService.getList(userFavoritesItemListReq);

        return success(pageList);
    }

    @ApiOperation(value = "收藏的商品", notes = "收藏的商品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(@RequestParam("item_id") Integer itemId) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserFavoritesItem userFavoritesItem = new UserFavoritesItem();
        userFavoritesItem.setItemId(itemId);
        userFavoritesItem.setUserId(userId);
        userFavoritesItem.setFavoritesItemTime(new Date().getTime());

        boolean success = userFavoritesItemService.add(userFavoritesItem);

        if (success) {
            return success();
        }

        return fail();
    }

    @ApiOperation(value = "收藏的商品删除", notes = "收藏的商品删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("item_id") Long itemId) {
        Integer userId = ContextUtil.checkLoginUserId();
        UserFavoritesItem userFavoritesItem = userFavoritesItemService.findOne(new QueryWrapper<UserFavoritesItem>().eq("user_id", userId).eq("item_id", itemId));

        if (ObjectUtil.isNotEmpty(userFavoritesItem)) {
            boolean success = userFavoritesItemService.remove(userFavoritesItem.getFavoritesItemId());

            if (success) {
                return success();
            }
        }

        return fail();
    }
}

