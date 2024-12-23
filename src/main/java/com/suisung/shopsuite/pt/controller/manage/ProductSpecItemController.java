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
package com.suisung.shopsuite.pt.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.BaseOrder;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pt.model.entity.ProductSpecItem;
import com.suisung.shopsuite.pt.model.req.ProductSpecItemAddReq;
import com.suisung.shopsuite.pt.model.req.ProductSpecItemEditReq;
import com.suisung.shopsuite.pt.model.req.ProductSpecItemListReq;
import com.suisung.shopsuite.pt.service.ProductSpecItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品规格值表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Api(tags = "商品规格值表")
@RestController
@RequestMapping("/manage/pt/productSpecItem")
public class ProductSpecItemController extends BaseController {
    @Autowired
    private ProductSpecItemService productSpecItemService;

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/list')")
    @ApiOperation(value = "商品规格值表-分页列表查询", notes = "商品规格值表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ProductSpecItem>> list(ProductSpecItemListReq productSpecItemListReq) {
        //额外排序
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setSidx("spec_item_id");
        baseOrder.setSort(Constants.ORDER_BY_ASC);

        List<BaseOrder> order = new ArrayList<>();
        order.add(baseOrder);

        productSpecItemListReq.setOrder(order);

        IPage<ProductSpecItem> pageList = productSpecItemService.lists(productSpecItemListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/detail')")
    @ApiOperation(value = "商品规格值表-通过spec_item_id查询", notes = "商品规格值表-通过spec_item_id查询")
    @RequestMapping(value = "/{specItemId}", method = RequestMethod.GET)
    public CommonRes<ProductSpecItem> get(@PathVariable Integer specItemId) {
        ProductSpecItem productSpecItem = productSpecItemService.get(specItemId);

        return success(productSpecItem);
    }

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/add')")
    @ApiOperation(value = "商品规格值表-添加", notes = "商品规格值表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(ProductSpecItemAddReq productSpecItemAddReq) {
        ProductSpecItem productSpecItem = BeanUtil.copyProperties(productSpecItemAddReq, ProductSpecItem.class);
        boolean success = productSpecItemService.add(productSpecItem);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/edit')")
    @ApiOperation(value = "商品规格值表-编辑", notes = "商品规格值表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(ProductSpecItemEditReq productSpecItemEditReq) {
        ProductSpecItem productSpecItem = BeanUtil.copyProperties(productSpecItemEditReq, ProductSpecItem.class);
        boolean success = productSpecItemService.edit(productSpecItem);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/remove')")
    @ApiOperation(value = "商品规格值表-通过spec_item_id删除", notes = "商品规格值表-通过spec_item_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("spec_item_id") Integer specItemId) {
        boolean success = productSpecItemService.removeItem(specItemId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/editState')")
    @ApiOperation(value = "商品规格值表-通过spec_item_enable更改状态", notes = "商品规格值表-通过spec_item_enable更改状态")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(@RequestParam("spec_item_id") Integer specItemId,
                                  @RequestParam("spec_item_enable") Boolean specItemEnable) {

        boolean success = productSpecItemService.editState(specItemId, specItemEnable);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productSpec/removeBatch')")
    @ApiOperation(value = "商品规格值表-批量删除", notes = "商品规格值表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("spec_item_id") String specItemIds) {
        boolean success = productSpecItemService.remove(Convert.toList(Integer.class, specItemIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

