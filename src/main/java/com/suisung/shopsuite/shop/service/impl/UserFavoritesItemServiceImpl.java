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
package com.suisung.shopsuite.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.pt.repository.ProductBaseRepository;
import com.suisung.shopsuite.shop.model.entity.UserFavoritesItem;
import com.suisung.shopsuite.shop.model.req.UserFavoritesItemListReq;
import com.suisung.shopsuite.shop.model.res.UserFavoritesItemRes;
import com.suisung.shopsuite.shop.repository.UserFavoritesItemRepository;
import com.suisung.shopsuite.shop.service.UserFavoritesItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 收藏的商品-根据SKU 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-28
 */
@Service
public class UserFavoritesItemServiceImpl extends BaseServiceImpl<UserFavoritesItemRepository, UserFavoritesItem, UserFavoritesItemListReq> implements UserFavoritesItemService {

    @Autowired
    private ProductBaseRepository productBaseRepository;


    @Override
    public IPage<UserFavoritesItemRes> getList(UserFavoritesItemListReq userFavoritesItemListReq) {
        IPage<UserFavoritesItemRes> favoritesItemResPage = new Page<>();
        IPage<UserFavoritesItem> favoritesItemPage = lists(userFavoritesItemListReq);

        if (favoritesItemPage != null && CollectionUtil.isNotEmpty(favoritesItemPage.getRecords())) {
            BeanUtils.copyProperties(favoritesItemPage, favoritesItemResPage);
            List<UserFavoritesItem> favoritesItems = favoritesItemPage.getRecords();
            List<Long> itemIds = favoritesItems.stream().map(UserFavoritesItem::getItemId).mapToLong(Integer::longValue).boxed().collect(Collectors.toList());
            List<ProductItemVo> productItemVos = productBaseRepository.getItems(itemIds, null);

            if (CollectionUtil.isNotEmpty(productItemVos)) {
                Map<Long, ProductItemVo> productItemVoMap = productItemVos.stream().collect(Collectors.toMap(ProductItemVo::getItemId, ProductItemVo -> ProductItemVo, (k1, k2) -> k1));

                List<UserFavoritesItemRes> itemRes = new ArrayList<>();
                for (UserFavoritesItem favoritesItem : favoritesItems) {
                    UserFavoritesItemRes userFavoritesItemRes = new UserFavoritesItemRes();
                    BeanUtils.copyProperties(favoritesItem, userFavoritesItemRes);
                    if (CollUtil.isNotEmpty(productItemVoMap)) {
                        ProductItemVo productItemVo = productItemVoMap.get(favoritesItem.getItemId().longValue());

                        if (productItemVo != null) {
                            userFavoritesItemRes.setProductItemName(productItemVo.getProductItemName());
                            userFavoritesItemRes.setItemUnitPrice(productItemVo.getItemUnitPrice());
                            userFavoritesItemRes.setProductImage(productItemVo.getProductImage());
                        }
                    }

                    itemRes.add(userFavoritesItemRes);
                }
                favoritesItemResPage.setRecords(itemRes);
            }
        }

        return favoritesItemResPage;
    }
}
