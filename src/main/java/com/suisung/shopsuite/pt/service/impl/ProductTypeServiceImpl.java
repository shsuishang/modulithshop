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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.ProductBrand;
import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import com.suisung.shopsuite.pt.model.entity.ProductInfo;
import com.suisung.shopsuite.pt.model.entity.ProductType;
import com.suisung.shopsuite.pt.model.output.ProductAssistOutput;
import com.suisung.shopsuite.pt.model.output.ProductSpecOutput;
import com.suisung.shopsuite.pt.model.req.ProductTypeListReq;
import com.suisung.shopsuite.pt.model.res.ProductTypeRes;
import com.suisung.shopsuite.pt.repository.*;
import com.suisung.shopsuite.pt.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 商品类型表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductTypeRepository, ProductType, ProductTypeListReq> implements ProductTypeService {

    @Autowired
    private ProductAssistRepository productAssistRepository;

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductSpecRepository productSpecRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public boolean edit(ProductType productType) {
        String specIds = productType.getSpecIds();

        //读取当前已经存在的type_id
        Integer typeId = productType.getTypeId();

        //修改
        if (CheckUtil.isNotEmpty(typeId)) {
            ProductType productTypeOld = get(typeId);

            if (productTypeOld != null) {
                String specIdsOld = productTypeOld.getSpecIds();

                List<Integer> typeSpecIdRowOld = Convert.toList(Integer.class, specIdsOld);
                List<Integer> typeSpecIdRow = Convert.toList(Integer.class, specIds);

                for (Integer typeSpecIdOld : typeSpecIdRowOld) {
                    //已经使用,本次更新不存在。
                    if (!typeSpecIdRow.contains(typeSpecIdOld)) {
                        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
                        CheckUtil.handleFindInSet(Convert.toList(Integer.class, typeSpecIdOld), "spec_ids", queryWrapper);

                        long count = productInfoRepository.count(queryWrapper);

                        if (count > 0) {
                            throw new BusinessException(String.format("规格 %d 已经被 %d 个SPU商品使用，不可取消关联", typeSpecIdOld, count));
                        }
                    }
                }
            }
        }

        if (StrUtil.isBlank(specIds)) {
            productType.setSpecIds("");
        } else {
        }

        if (StrUtil.isBlank(productType.getBrandIds())) {
            productType.setBrandIds("");
        }

        return super.edit(productType);
    }

    @Override
    public boolean remove(Serializable id) {
        long count = productCategoryRepository.count(new LambdaQueryWrapper<ProductCategory>().eq(ProductCategory::getTypeId, id));

        if (count > 0) {
            throw new BusinessException(String.format(__("有 %d 条分类使用，不可删除"), count));
        }

        return super.remove(id);
    }

    /**
     * 通过类型获取商品规格
     *
     * @param typeId
     * @return
     */
    @Override
    public ProductTypeRes getInfo(Integer typeId) {
        ProductTypeRes productTypeRes = new ProductTypeRes();
        ProductType productType = get(typeId);

        List<ProductAssistOutput> assists = new ArrayList<>();
        List<ProductBrand> brands = new ArrayList<>();
        List<ProductSpecOutput> specs = new ArrayList<>();

        if (productType != null) {
            if (StrUtil.isNotEmpty(productType.getAssistIds())) {
                assists = productAssistRepository.getAssists(productType.getAssistIds());
            }

            if (StrUtil.isNotEmpty(productType.getBrandIds())) {
                brands = productBrandRepository.find(new QueryWrapper<ProductBrand>().in("brand_id", Convert.toList(Integer.class, productType.getBrandIds())));
            }

            if (StrUtil.isNotEmpty(productType.getSpecIds())) {
                specs = productSpecRepository.getSpecs(productType.getSpecIds());
            }
        }

        productTypeRes.setAssists(assists);
        productTypeRes.setBrands(brands);
        productTypeRes.setSpecs(specs);
        return productTypeRes;
    }
}
