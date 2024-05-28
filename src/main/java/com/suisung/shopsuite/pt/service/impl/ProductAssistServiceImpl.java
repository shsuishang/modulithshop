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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.ProductAssist;
import com.suisung.shopsuite.pt.model.entity.ProductAssistItem;
import com.suisung.shopsuite.pt.model.entity.ProductCategory;
import com.suisung.shopsuite.pt.model.req.ProductAssistListReq;
import com.suisung.shopsuite.pt.model.res.ProductAssistRes;
import com.suisung.shopsuite.pt.repository.ProductAssistItemRepository;
import com.suisung.shopsuite.pt.repository.ProductAssistRepository;
import com.suisung.shopsuite.pt.repository.ProductTypeRepository;
import com.suisung.shopsuite.pt.service.ProductAssistService;
import com.suisung.shopsuite.pt.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 商品辅助属性表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Service
public class ProductAssistServiceImpl extends BaseServiceImpl<ProductAssistRepository, ProductAssist, ProductAssistListReq> implements ProductAssistService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductAssistItemRepository productAssistItemRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    public boolean remove(Serializable id) {
        ProductAssist productAssist = get(id);

        long count = productAssistItemRepository.count(new LambdaQueryWrapper<ProductAssistItem>().eq(ProductAssistItem::getAssistId, id));
        if (count > 0) {
            throw new BusinessException(String.format(__("有 %d 个属性选项，不可删除！"), count));
        }

        boolean flag = super.remove(id);

        //更新product_type assistsIds
        //productTypeRepository.updateAssistIds(productAssist.getTypeId());
        productTypeRepository.removeAssistIds(productAssist.getAssistId());


        return flag;
    }

    @Override
    public boolean add(ProductAssist a) {
        boolean flag = super.add(a);
        //boolean b = productTypeRepository.updateAssistIds(a.getTypeId());

        return flag;
    }

    /**
     * 获取属性树形数据
     *
     * @return
     */
    @Override
    public List<ProductAssistRes> getTree() {
        List<ProductAssist> assistLists = repository.find(new QueryWrapper<ProductAssist>().orderByAsc("assist_id"));

        List<Integer> categoryIds = CommonUtil.column(assistLists, ProductAssist::getCategoryId);
        List<ProductCategory> categories = productCategoryService.gets(categoryIds);

        List<ProductAssistRes> assistRes = new ArrayList<>();

        for (ProductCategory category : categories) {
            List<ProductAssist> assists = new ArrayList<>();

            for (ProductAssist assist : assistLists) {
                if (assist.getCategoryId().equals(category.getCategoryId())) {
                    assists.add(assist);
                }
            }

            if (assists.size() > 0) {
                ProductAssistRes assistRe = new ProductAssistRes();
                assistRe.setAssistId(category.getCategoryId());
                assistRe.setAssistName(category.getCategoryName());

                assistRe.setChildren(CollUtil.isEmpty(assists) ? null : assists);
                assistRes.add(assistRe);
            }
        }

        return assistRes;
    }
}
