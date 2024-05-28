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
package com.suisung.shopsuite.pt.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.pt.dao.ProductSpecDao;
import com.suisung.shopsuite.pt.model.entity.ProductSpec;
import com.suisung.shopsuite.pt.model.entity.ProductSpecItem;
import com.suisung.shopsuite.pt.model.output.ProductSpecOutput;
import com.suisung.shopsuite.pt.repository.ProductSpecItemRepository;
import com.suisung.shopsuite.pt.repository.ProductSpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;


/**
 * <p>
 * 商品规格表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Repository
public class ProductSpecRepositoryImpl extends BaseRepositoryImpl<ProductSpecDao, ProductSpec> implements ProductSpecRepository {

    @Autowired
    private ProductSpecItemRepository productSpecItemRepository;

    @Override
    public boolean remove(Collection<? extends Serializable> ids) {
        QueryWrapper<ProductSpecItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("spec_id", ids);
        List<Serializable> spec_item_ids = productSpecItemRepository.findKey(queryWrapper);
        if (CollUtil.isNotEmpty(spec_item_ids)) {
            throw new BusinessException(String.format(__("不能删除正在被商品规格值表使用的规格！商品规格值编号【%s】"), CollUtil.join(spec_item_ids, ",")));
        }

        return super.remove(ids);
    }

    /**
     * 获取商品规格
     *
     * @return
     */
    @Override
    public List<ProductSpecOutput> getSpecs(String specIds) {
        List<Integer> specIdList = Convert.toList(Integer.class, specIds);

        QueryWrapper<ProductSpec> specQueryWrapper = new QueryWrapper<>();
        specQueryWrapper.in("spec_id", specIdList);
        List<ProductSpec> productSpec = find(specQueryWrapper);

        List<ProductSpecOutput> specOutputList = BeanUtil.copyToList(productSpec, ProductSpecOutput.class);
        /*
        List<ProductSpecOutput> specOutputList = productSpec.stream().map(spec -> {
            ProductSpecOutput productSpecOutput = new ProductSpecOutput();
            BeanUtils.copyProperties(spec, productSpecOutput);
            return productSpecOutput;
        }).collect(Collectors.toList());

         */

        QueryWrapper<ProductSpecItem> specItemQueryWrapper = new QueryWrapper<>();
        specItemQueryWrapper.in("spec_id", specIdList);
        List<ProductSpecItem> specItemList = productSpecItemRepository.find(specItemQueryWrapper);

        for (ProductSpecOutput ps : specOutputList) {
            List<ProductSpecItem> specItems = new ArrayList<>();
            for (ProductSpecItem specItem : specItemList) {
                if (ps.getSpecId().equals(specItem.getSpecId())) {
                    specItems.add(specItem);
                }
            }

            ps.setItems(specItems);
        }


        //从小到大排序
        specOutputList.sort(Comparator.comparing(ProductSpecOutput::getSpecSort));

        //排序，image第一个显示
        specOutputList.sort(Comparator.comparing(obj -> obj.getSpecFormat()));


        return specOutputList;
    }
}
