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
import cn.hutool.core.util.ObjectUtil;
import com.suisung.shopsuite.common.pojo.dto.ActivityType;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.vo.ActivityInfoVo;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.pt.repository.ProductBaseRepository;
import com.suisung.shopsuite.shop.model.entity.UserProductBrowse;
import com.suisung.shopsuite.shop.model.req.UserProductBrowseListReq;
import com.suisung.shopsuite.shop.model.res.UserProductBrowseRes;
import com.suisung.shopsuite.shop.repository.UserProductBrowseRepository;
import com.suisung.shopsuite.shop.service.UserProductBrowseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品浏览历史表-SPU-不应该直接存数据库 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-28
 */
@Service
public class UserProductBrowseServiceImpl extends BaseServiceImpl<UserProductBrowseRepository, UserProductBrowse, UserProductBrowseListReq> implements UserProductBrowseService {

    @Autowired
    private ProductBaseRepository productBaseRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public List<UserProductBrowseRes> getList(UserProductBrowseListReq userProductBrowseListReq) {
        List<UserProductBrowseRes> userProductBrowseRes = new ArrayList<>();
        Integer userId = userProductBrowseListReq.getUserId();
        String cacheKey = String.format("user_id|%d", userId);
        List<UserProductBrowse> productBrowses = ObjectUtil.defaultIfNull((List<UserProductBrowse>) redisService.get(cacheKey), new ArrayList<>());

        if (CollectionUtil.isNotEmpty(productBrowses)) {
            List<Long> itemIds = productBrowses.stream().map(UserProductBrowse::getItemId).distinct().collect(Collectors.toList());
            List<ProductItemVo> productItemVos = productBaseRepository.getItems(itemIds, null);

            if (CollectionUtil.isNotEmpty(productItemVos)) {
                Map<Long, ProductItemVo> productItemVoMap = productItemVos.stream().collect(Collectors.toMap(ProductItemVo::getItemId, ProductItemVo -> ProductItemVo, (k1, k2) -> k1));
                for (UserProductBrowse productBrows : productBrowses) {
                    UserProductBrowseRes browseRes = new UserProductBrowseRes();
                    BeanUtils.copyProperties(productBrows, browseRes);

                    if (CollUtil.isNotEmpty(productItemVoMap)) {
                        ProductItemVo productItemVo = productItemVoMap.get(productBrows.getItemId());

                        if (productItemVo != null) {
                            browseRes.setProductImage(productItemVo.getProductImage());
                            browseRes.setItemSalePrice(productItemVo.getItemSalePrice());
                            browseRes.setProductItemName(productItemVo.getProductItemName());
                            ActivityInfoVo activityInfo = productItemVo.getActivityInfo();

                            if (activityInfo != null) {
                                Integer activityTypeId = activityInfo.getActivityTypeId();
                                browseRes.setActivityTypeId(activityTypeId);
                                browseRes.setActivityTypeName(ActivityType.getActivityName(activityTypeId));
                            }
                        }
                        userProductBrowseRes.add(browseRes);
                    }
                }
            }
        }

        return userProductBrowseRes;
    }

    @Override
    public List<UserProductBrowse> addBrowser(Long itemId, Integer userId) {
        UserProductBrowse productBrowse = new UserProductBrowse();
        productBrowse.setItemId(itemId);
        productBrowse.setUserId(userId);
        productBrowse.setBrowseTime(new Date().getTime());
        String cacheKey = String.format("user_id|%d", userId);

        // 判断里面是否有浏览记录
        List<UserProductBrowse> productBrowses = ObjectUtil.defaultIfNull((List<UserProductBrowse>) redisService.get(cacheKey), new ArrayList<>());
        if (CollectionUtil.isNotEmpty(productBrowses)) {
            /* 去除重复记录 */
            productBrowses.removeIf(s -> ObjectUtil.equal(s.getItemId(), itemId));
            if (productBrowses.size() == 10) {
                productBrowses.remove(productBrowses.size() - 1);
            }
            productBrowses.add(0, productBrowse);
        } else {
            productBrowses.add(productBrowse);
        }
        redisService.set(cacheKey, productBrowses);

        return productBrowses;
    }

    @Override
    public boolean removeBrowser(UserProductBrowseListReq userProductBrowseListReq) {
        Integer userId = userProductBrowseListReq.getUserId();
        String cacheKey = String.format("user_id|%d", userId);
        List<UserProductBrowse> productBrowses = ObjectUtil.defaultIfNull((List<UserProductBrowse>) redisService.get(cacheKey), new ArrayList<>());

        if (CollectionUtil.isNotEmpty(productBrowses)) {
            productBrowses.removeIf(s -> ObjectUtil.equal(s.getItemId(), userProductBrowseListReq.getItemId()));
        }
        redisService.set(cacheKey, productBrowses);

        return true;
    }
}
