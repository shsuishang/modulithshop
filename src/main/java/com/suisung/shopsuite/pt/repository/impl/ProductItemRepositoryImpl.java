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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.core.consts.ConstantRedis;
import com.suisung.shopsuite.core.thread.RequestContextVariable;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.pt.dao.ProductItemDao;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * <p>
 * 商品SKU表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-03-13
 */
@Repository
public class ProductItemRepositoryImpl extends BaseRepositoryImpl<ProductItemDao, ProductItem> implements ProductItemRepository {
    @Autowired
    private ProductItemDao productItemDao;

    @Autowired
    private RedisService redisService;

    public int lockSkuStock(Long itemId, int cartQuantity) {
        int i = productItemDao.lockSkuStock(itemId, cartQuantity);

        if (i > 0) {
            removeCache(itemId);
        }

        return i;
    }

    public int releaseSkuStock(Long itemId, int cartQuantity) {
        int i = productItemDao.releaseSkuStock(itemId, cartQuantity);

        if (i > 0) {
            removeCache(itemId);
        }

        return i;
    }

    public int pickingSkuStock(Long itemId, int cartQuantity) {
        int i = productItemDao.pickingSkuStock(itemId, cartQuantity);

        if (i > 0) {
            removeCache(itemId);
        }

        return i;
    }

    @Override
    public IPage<Long> listItemKey(Page<ProductItemInput> page, ProductItemInput params) {
        return productItemDao.listItemKey(page, params);
    }

    public boolean removeCache(Long itemId) {
        //删除缓存
        String key = ConstantRedis.Cache_NameSpace + "pt_product_item:" + itemId;
        if (StrUtil.isNotBlank(key)) {
            RequestContextVariable.addCacheKey(key);
            if (!TransactionSynchronizationManager.isActualTransactionActive()) {
                redisService.del(key);
            }
        }

        return true;
    }
}
