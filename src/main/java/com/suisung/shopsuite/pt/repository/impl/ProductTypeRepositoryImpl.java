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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.pt.dao.ProductTypeDao;
import com.suisung.shopsuite.pt.model.entity.ProductAssist;
import com.suisung.shopsuite.pt.model.entity.ProductType;
import com.suisung.shopsuite.pt.repository.ProductAssistRepository;
import com.suisung.shopsuite.pt.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
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
@Repository
public class ProductTypeRepositoryImpl extends BaseRepositoryImpl<ProductTypeDao, ProductType> implements ProductTypeRepository {
    @Autowired
    private ProductAssistRepository productAssistRepository;

    @Override
    public boolean remove(Collection<? extends Serializable> ids) {
        boolean res = false;

        for (Serializable id : ids) {
            ProductType productType = get(id);

            if (productType.getTypeBuildin()) {
                throw new BusinessException(String.format(__("编号: %d 系统内置，不可删除"), id));
            }
        }

        return res;
    }

    @Override
    public boolean updateAssistIds(Integer typeId) {
        List<Serializable> typeIdRow = productAssistRepository.findKey(new QueryWrapper<ProductAssist>().eq("type_id", typeId));

        ProductType productType = new ProductType();
        productType.setTypeId(typeId);
        productType.setAssistIds(CollUtil.join(typeIdRow, ","));

        return edit(productType);
    }


    @Override
    public boolean removeAssistIds(Integer assistId) {

        //关联模式，去除选中的编号
        QueryWrapper<ProductType> wrapper = new QueryWrapper<>();
        CheckUtil.handleFindInSet(Arrays.asList(assistId), "assist_ids", wrapper);

        List<ProductType> productTypes = find(wrapper);

        for (ProductType item : productTypes) {
            List<Integer> assistIdList = Convert.toList(Integer.class, item.getAssistIds());
            assistIdList.remove(assistId);

            item.setAssistIds(CollUtil.join(assistIdList, ","));
            edit(item);
        }

        return true;
    }
}
