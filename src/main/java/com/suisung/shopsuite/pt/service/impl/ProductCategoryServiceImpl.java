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
package com.suisung.shopsuite.pt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.ProductBrand;
import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import com.suisung.shopsuite.pt.model.entity.ProductType;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import com.suisung.shopsuite.pt.model.req.ProductCategoryListReq;
import com.suisung.shopsuite.pt.model.res.ProductCategoryFilterRes;
import com.suisung.shopsuite.pt.model.res.ProductCategoryRes;
import com.suisung.shopsuite.pt.repository.ProductAssistRepository;
import com.suisung.shopsuite.pt.repository.ProductBrandRepository;
import com.suisung.shopsuite.pt.repository.ProductCategoryRepository;
import com.suisung.shopsuite.pt.repository.ProductTypeRepository;
import com.suisung.shopsuite.pt.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategoryRepository, ProductCategory, ProductCategoryListReq> implements ProductCategoryService {
    @Autowired
    private ProductAssistRepository productAssistRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Cacheable(value = {"productCategoryList"})
    @Override
    public Page<ProductCategory> lists(ProductCategoryListReq a) {
        return super.lists(a);
    }

    /**
     * 获取商品分类树形数据
     *
     * @return
     */
    @Cacheable(value = {"productCategoryTree"})
    @Override
    public List<ProductCategoryRes> getTree(Integer pid, Boolean onlyEnable, String categoryName) {
        QueryWrapper<ProductCategory> objectQueryWrapper = new QueryWrapper<>();

        if (CheckUtil.isNotEmpty(categoryName)) {
            objectQueryWrapper.like("category_name", categoryName);
        }

        List<ProductCategory> dataList = repository.find(objectQueryWrapper);

        List<ProductCategoryRes> categoryRes = dataList.stream().filter(s -> {
            if (onlyEnable) {
                return s.getCategoryIsEnable();
            } else {
                return true;
            }
        }).map(data -> {
            ProductCategoryRes productCategoryRes = new ProductCategoryRes();
            BeanUtils.copyProperties(data, productCategoryRes);
            return productCategoryRes;
        }).collect(Collectors.toList());

        List<ProductCategoryRes> productCategoryRes = CommonUtil.toTreeData(categoryRes, pid,
                ProductCategoryRes::getCategoryParentId,
                ProductCategoryRes::getCategoryId,
                ProductCategoryRes::setChildren
        );

        if (CheckUtil.isNotEmpty(categoryName)) {
            //或者无上级数据，加入列表。 -- 用户树形搜索展示
            List<Integer> columnIds = CommonUtil.column(categoryRes, ProductCategoryRes::getCategoryId);
            for (ProductCategoryRes categoryItem : categoryRes) {
                if (categoryItem.getCategoryParentId().intValue() != 0 && !columnIds.contains(categoryItem.getCategoryParentId())) {

                    ProductCategoryRes router = BeanUtil.copyProperties(categoryItem, ProductCategoryRes.class);

                    List<ProductCategoryRes> c = CommonUtil.toTreeData(categoryRes, categoryItem.getCategoryId(),
                            ProductCategoryRes::getCategoryParentId,
                            ProductCategoryRes::getCategoryId,
                            ProductCategoryRes::setChildren
                    );

                    router.setChildren(c);

                    productCategoryRes.add(router);
                }
            }
        }


        return productCategoryRes;
    }

    /**
     * 修改商品分类启用状态
     *
     * @param categoryId
     * @param categoryIsEnable
     * @return
     */
    @CacheEvict(value = {"productCategoryTree", "productCategoryList", "pcLayoutData"}, allEntries = true)
    @Override
    public boolean editState(Integer categoryId, Boolean categoryIsEnable) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(categoryId);
        productCategory.setCategoryIsEnable(categoryIsEnable);
        return edit(productCategory);
    }

    @Override
    public List<Integer> getCategoryLeafs(Integer pid) {
        List<Integer> ids = new ArrayList<>();
        List<ProductCategoryRes> tree = getTree(pid, false, null);

        CommonUtil.eachTreeData(tree, productCategoryRes -> {
            if (productCategoryRes.getChildren().size() == 0) {
                ids.add(productCategoryRes.getCategoryId());
            }
        }, ProductCategoryRes::getChildren);

        return ids;
    }

    @Override
    public ProductCategoryFilterRes getCategoryFilter(Integer categoryId) {
        ProductCategoryFilterRes output = new ProductCategoryFilterRes();

        //判断是否固定分类读取数据
        if (CheckUtil.isNotEmpty(categoryId)) {
            if (CheckUtil.isNotEmpty(categoryId)) {
                ProductCategory productCategory = repository.get(categoryId);
                output.setInfo(productCategory);

                if (ObjectUtil.isNotEmpty(productCategory)) {
                    //上级分类
                    List<ProductCategory> parentCategoryListById = repository.getParentCategory(categoryId);
                    output.setParent(parentCategoryListById);

                    //下级分类
                    List<ProductCategory> childCategorys = repository.find(new QueryWrapper<ProductCategory>().eq("category_parent_id", categoryId).eq("category_is_enable", true));
                    output.setChildren(childCategorys);

                    //辅助属性
                    ProductType productType = productTypeRepository.get(productCategory.getTypeId());

                    if (ObjectUtil.isNotEmpty(productType)) {
                        List<ProductAssistOutput> assists = productAssistRepository.getAssists(productType.getAssistIds());
                        output.setAssists(assists);

                        //品牌
                        if (CheckUtil.isNotEmpty(productType.getBrandIds())) {
                            List<Integer> brandIds = Convert.toList(Integer.class, productType.getBrandIds());
                            List<ProductBrand> brandList = productBrandRepository.gets(brandIds);
                            output.setBrands(brandList);
                        }
                    }
                }
            }
        }

        return output;
    }

    @CacheEvict(value = {"productCategoryTree", "productCategoryList", "pcLayoutData"}, allEntries = true)
    @Override
    public boolean add(ProductCategory a) {
        return super.add(a);
    }

    @CacheEvict(value = {"productCategoryTree", "productCategoryList", "pcLayoutData"}, allEntries = true)
    @Override
    public boolean edit(ProductCategory a) {
        return super.edit(a);
    }

    @CacheEvict(value = {"productCategoryTree", "productCategoryList", "pcLayoutData"}, allEntries = true)
    @Override
    public boolean remove(Serializable a) {
        return super.remove(a);
    }
}
