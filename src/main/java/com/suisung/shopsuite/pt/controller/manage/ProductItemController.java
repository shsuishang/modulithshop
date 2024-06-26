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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.input.ProductEditStockInput;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.model.req.ProductEditStockReq;
import com.suisung.shopsuite.pt.model.req.ProductItemListReq;
import com.suisung.shopsuite.pt.model.req.ProductItemStateEditReq;
import com.suisung.shopsuite.pt.model.res.ItemListRes;
import com.suisung.shopsuite.pt.service.ProductCategoryService;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import com.suisung.shopsuite.pt.service.ProductItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品SKU表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Api(tags = "商品SKU表")
@RestController
@RequestMapping("/manage/pt/productItem")
public class ProductItemController extends BaseController {
    @Autowired
    private ProductItemService productItemService;
    @Resource
    private ProductIndexService productIndexService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @PreAuthorize("hasAuthority('/manage/pt/productBase/list')")
    @ApiOperation(value = "商品SKU表-分页列表查询", notes = "商品SKU表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<ItemListRes> list(ProductItemListReq req) {
        ProductItemInput input = new ProductItemInput();
        BeanUtils.copyProperties(req, input);

        input.setItemId(Convert.toList(Long.class, req.getItemId()));

        if (CheckUtil.isNotEmpty(req.getCategoryId())) {
            List<Integer> categoryLeafs = productCategoryService.getCategoryLeafs(req.getCategoryId());
            if (CollUtil.isNotEmpty(categoryLeafs)) {
                input.setCategoryId(CollUtil.join(categoryLeafs, ","));
            } else {
                input.setCategoryId(req.getCategoryId().toString());
            }
        }

        ItemListRes pageList = productIndexService.listItem(input);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage/pt/productBase/edit')")
    @ApiOperation(value = "更改库存", notes = "更改库存")
    @RequestMapping(value = "/editStock", method = RequestMethod.POST)
    public CommonRes<?> editStock(ProductEditStockReq req) {
        ProductEditStockInput input = BeanUtil.copyProperties(req, ProductEditStockInput.class);
        boolean success = productItemService.editStock(input);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productBase/edit')")
    @ApiOperation(value = "修改状态-是否启用", notes = "修改状态-是否启用")
    @RequestMapping(value = "/editState", method = RequestMethod.POST)
    public CommonRes<?> editState(ProductItemStateEditReq req) {
        ProductItem productItem = BeanUtil.copyProperties(req, ProductItem.class);
        boolean success = productItemService.editState(productItem);

        if (success) {
            return success();
        }

        return fail();
    }

}

