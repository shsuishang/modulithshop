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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pt.model.entity.ProductAssist;
import com.suisung.shopsuite.pt.model.entity.ProductType;
import com.suisung.shopsuite.pt.model.req.ProductAssistAddReq;
import com.suisung.shopsuite.pt.model.req.ProductAssistEditReq;
import com.suisung.shopsuite.pt.model.req.ProductAssistListReq;
import com.suisung.shopsuite.pt.model.res.ProductAssistRes;
import com.suisung.shopsuite.pt.service.ProductAssistService;
import com.suisung.shopsuite.pt.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品辅助属性表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Api(tags = "商品辅助属性表")
@RestController
@RequestMapping("/manage/pt/productAssist")
public class ProductAssistController extends BaseController {
    @Autowired
    private ProductAssistService productAssistService;

    @Autowired
    private ProductTypeService productTypeService;

    @PreAuthorize("hasAuthority('/manage/pt/productAssist/list')")
    @ApiOperation(value = "商品辅助属性表-分页列表查询", notes = "商品辅助属性表-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ProductAssist>> list(ProductAssistListReq productAssistListReq) {
        IPage<ProductAssist> pageList;
        //判断是否有type_id
        if (CheckUtil.isNotEmpty(productAssistListReq.getTypeId())) {
            pageList = new Page<>();
            ProductType productType = productTypeService.get(productAssistListReq.getTypeId());
            List<ProductAssist> productAssistList = productAssistService.gets(Convert.toList(Integer.class, productType.getAssistIds()));

            pageList.setRecords(productAssistList);
        } else {
            pageList = productAssistService.lists(productAssistListReq);
        }

        return success(pageList);
    }

    @ApiOperation(value = "辅助属性 - 递归查询", notes = "辅助属性 - 递归查询")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CommonRes<List<ProductAssistRes>> tree() {
        List<ProductAssistRes> brandRes = productAssistService.getTree();

        return success(brandRes);
    }

    @PreAuthorize("hasAuthority('/manage/pt/productAssist/add')")
    @ApiOperation(value = "商品辅助属性表-添加", notes = "商品辅助属性表-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(ProductAssistAddReq productAssistAddReq) {
        ProductAssist productAssist = BeanUtil.copyProperties(productAssistAddReq, ProductAssist.class);
        boolean success = productAssistService.add(productAssist);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productAssist/edit')")
    @ApiOperation(value = "商品辅助属性表-编辑", notes = "商品辅助属性表-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(ProductAssistEditReq productAssistEditReq) {
        ProductAssist productAssist = BeanUtil.copyProperties(productAssistEditReq, ProductAssist.class);
        boolean success = productAssistService.edit(productAssist);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productAssist/remove')")
    @ApiOperation(value = "商品辅助属性表-通过assist_id删除", notes = "商品辅助属性表-通过assist_id删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("assist_id") Integer assistId) {
        boolean success = productAssistService.remove(assistId);

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage/pt/productAssist/removeBatch')")
    @ApiOperation(value = "商品辅助属性表-批量删除", notes = "商品辅助属性表-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("assist_id") String assistIds) {
        boolean success = productAssistService.remove(Convert.toList(Integer.class, assistIds));

        if (success) {
            return success();
        }

        return fail();
    }
}

