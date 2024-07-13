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
package com.suisung.shopsuite.shop.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.shop.dao.StoreTransportTypeDao;
import com.suisung.shopsuite.shop.model.entity.StoreTransportItem;
import com.suisung.shopsuite.shop.model.entity.StoreTransportType;
import com.suisung.shopsuite.shop.model.vo.OrderFreightVo;
import com.suisung.shopsuite.shop.model.vo.StoreTransportItemVo;
import com.suisung.shopsuite.shop.repository.StoreTransportItemRepository;
import com.suisung.shopsuite.shop.repository.StoreTransportTypeRepository;
import com.suisung.shopsuite.sys.repository.DistrictBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;


/**
 * <p>
 * 自定义物流运费及售卖区域类型表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-11
 */
@Repository
public class StoreTransportTypeRepositoryImpl extends BaseRepositoryImpl<StoreTransportTypeDao, StoreTransportType> implements StoreTransportTypeRepository {
    @Autowired
    private DistrictBaseRepository districtBaseRepository;

    @Autowired
    private StoreTransportItemRepository storeTransportItemRepository;

    /**
     * 配送区域信息及运费
     *
     * @param transport_type_id
     * @param district_id
     * @return
     */
    @Override
    public StoreTransportItemVo getFreight(Integer transport_type_id, Integer district_id) {
        StoreTransportItemVo storeTransportItemVo = null;

        if (CheckUtil.isNotEmpty(transport_type_id)) {
            StoreTransportType storeTransportType = get(transport_type_id);

            if (ObjectUtil.isNotEmpty(storeTransportType)) {
                storeTransportItemVo = BeanUtil.copyProperties(storeTransportType, StoreTransportItemVo.class);


                // 全部免运费，任何地区都配送
                boolean transport_type_free = storeTransportType.getTransportTypeFree();

                if (transport_type_free) {
                    StoreTransportItem storeTransportItem = new StoreTransportItem();
                    storeTransportItem.setTransportItemDefaultPrice(BigDecimal.ZERO);
                    storeTransportItemVo.setItem(storeTransportItem);
                } else {
                    QueryWrapper<StoreTransportItem> itemQueryWrapper = new QueryWrapper<>();
                    itemQueryWrapper.eq("transport_type_id", transport_type_id);
                    CheckUtil.handleFindInSet(Arrays.asList(district_id), "transport_item_city_ids", itemQueryWrapper);

                    StoreTransportItem storeTransportItem = storeTransportItemRepository.findOne(itemQueryWrapper);

                    storeTransportItemVo.setItem(storeTransportItem);
                }
            }
        }

        return storeTransportItemVo;
    }

    /**
     * 运费计算， 如果一个订单，都多个货物，则计算方式为：以最大基础运费为基础 +  每个商品递增部分。
     *
     * @return array $product_freight_info 返回的查询内容
     * @access public
     * @var int $district_id 用户所在区域
     * @var int $quantity 购买数量
     * @var int $order_total 订单总额度
     * @var int $post_free_max 免运费最大值
     * @var int $transport_type_id 配送及运费模板
     */
    @Override
    public OrderFreightVo calFreight(Integer transport_type_id, Integer district_id, Integer quantity, BigDecimal order_total, BigDecimal post_free_max) {
        OrderFreightVo data = new OrderFreightVo();
        data.setCanDelivery(true);
        data.setFreightFreeMin(post_free_max);

        if (CheckUtil.isEmpty(transport_type_id)) {
            throw new BusinessException("transport_type_id is wrong！");
        }

        // 配送区域及运费transport_type_id
        StoreTransportItemVo storeTransportItemVo = getFreight(transport_type_id, district_id);

        if (storeTransportItemVo != null) {
            StoreTransportItem item = storeTransportItemVo.getItem();

            //可售区域
            if (!storeTransportItemVo.getTransportTypeFree() && ObjectUtil.isEmpty(item)) {
                data.setCanDelivery(false);
            }

            if (item != null) {
                BigDecimal transport_type_freight_free = storeTransportItemVo.getTransportTypeFreightFree();
                post_free_max = NumberUtil.max(post_free_max, transport_type_freight_free);
                data.setFreightFreeMin(post_free_max);

                if (transport_type_freight_free.compareTo(BigDecimal.ZERO) > 0 && ObjectUtil.compare(order_total, transport_type_freight_free) >= 0) {
                    // 订单免运费
                    return data;
                } else {
                    Integer transport_item_default_num = item.getTransportItemDefaultNum();
                    Integer transport_item_add_num = item.getTransportItemAddNum();

                    BigDecimal add_num = NumberUtil.max(BigDecimal.ZERO, NumberUtil.sub(quantity, transport_item_default_num));
                    BigDecimal transport_item_default_price = item.getTransportItemDefaultPrice();
                    if (CheckUtil.isNotEmpty(add_num) && CheckUtil.isNotEmpty(transport_item_add_num)) {
                        BigDecimal transport_item_add_price = item.getTransportItemAddPrice();
                        BigDecimal sum = NumberUtil.add(transport_item_default_price, NumberUtil.mul(transport_item_add_price, NumberUtil.div(add_num, transport_item_add_num)));

                        data.setFreight(sum);
                    } else {
                        // 默认运费
                        data.setFreight(transport_item_default_price);
                    }

                    return data;
                }
            }
        } else {
            data.setFreight(BigDecimal.ZERO);
        }

        return data;
    }
}
