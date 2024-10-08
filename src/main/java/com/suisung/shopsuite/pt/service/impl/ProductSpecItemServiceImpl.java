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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.entity.ProductSpecItem;
import com.suisung.shopsuite.pt.model.req.ProductItemListReq;
import com.suisung.shopsuite.pt.model.req.ProductSpecItemListReq;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.pt.repository.ProductSpecItemRepository;
import com.suisung.shopsuite.pt.service.ProductSpecItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 商品规格值表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Service
public class ProductSpecItemServiceImpl extends BaseServiceImpl<ProductSpecItemRepository, ProductSpecItem, ProductSpecItemListReq> implements ProductSpecItemService {

    @Autowired
    private ProductItemRepository productItemRepository;

    /**
     * 修改商品规格值启用状态
     *
     * @param specItemId
     * @param specItemEnable
     * @return
     */
    @Override
    public boolean editState(Integer specItemId, Boolean specItemEnable) {
        ProductSpecItem productSpecItem = new ProductSpecItem();
        productSpecItem.setSpecItemId(specItemId);
        productSpecItem.setSpecItemEnable(specItemEnable);
        return edit(productSpecItem);
    }

    @Override
    public boolean removeItem(Integer specItemId) {
        ProductItemListReq productItemListReq = new ProductItemListReq();
        productItemListReq.setSpecItemIds(Convert.toStr(specItemId));
        QueryWrapper<ProductItem> productItemQueryWrapper = new BaseQueryWrapper<ProductItem, ProductItemListReq>(productItemListReq).getWrapper();
        List<Serializable> productItemIds = productItemRepository.findKey(productItemQueryWrapper);

        if (CollectionUtil.isNotEmpty(productItemIds)) {
            throw new BusinessException(__("商品规格值已有商品使用，无法删除！"));
        }

        return remove(specItemId);
    }
}
