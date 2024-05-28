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
package com.suisung.shopsuite.trade.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserDeliveryAddress;
import com.suisung.shopsuite.account.repository.UserDeliveryAddressRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.model.vo.ProductItemVo;
import com.suisung.shopsuite.pt.repository.ProductBaseRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.shop.model.entity.StoreTransportType;
import com.suisung.shopsuite.shop.model.req.UserVoucherListReq;
import com.suisung.shopsuite.shop.model.res.UserVoucherRes;
import com.suisung.shopsuite.shop.model.vo.OrderFreightVo;
import com.suisung.shopsuite.shop.repository.StoreTransportTypeRepository;
import com.suisung.shopsuite.shop.service.UserVoucherService;
import com.suisung.shopsuite.trade.model.entity.UserCart;
import com.suisung.shopsuite.trade.model.input.CartAddInput;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.input.UserCartSelectInput;
import com.suisung.shopsuite.trade.model.output.CheckoutOutput;
import com.suisung.shopsuite.trade.model.req.UserCartListReq;
import com.suisung.shopsuite.trade.model.vo.CheckoutItemVo;
import com.suisung.shopsuite.trade.model.vo.StoreItemVo;
import com.suisung.shopsuite.trade.repository.UserCartRepository;
import com.suisung.shopsuite.trade.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-26
 */
@Service
public class UserCartServiceImpl extends BaseServiceImpl<UserCartRepository, UserCart, UserCartListReq> implements UserCartService {
    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductBaseRepository productBaseRepository;

    @Autowired
    private UserDeliveryAddressRepository userDeliveryAddressRepository;

    @Autowired
    private StoreTransportTypeRepository storeTransportTypeRepository;

    @Autowired
    private UserVoucherService voucherService;

    @Autowired
    private ActivityBaseRepository activityBaseRepository;

    @Override
    public CheckoutOutput getList(UserCartListReq req) {
        QueryWrapper<UserCart> wrapper = new BaseQueryWrapper<UserCart, UserCartListReq>(req).getWrapper();
        List<UserCart> userCarts = find(wrapper);

        CheckoutInput in = new CheckoutInput();
        in.setUserId(req.getUserId());
        in.setItems(BeanUtil.copyToList(userCarts, CheckoutItemVo.class));

        CheckoutOutput out = checkout(in);

        return out;
    }

    /**
     * 生成订单数据，结算checkout预览及生成订单, 理论上属于订单模块
     * <p>
     * 1、购物车中商品基本信息读取
     * 2、格式化数据（阶梯价计算等等），店铺数据分组，商品仓库分组
     * 3、活动数据、优惠折扣团购等等
     * 4、活动商品数据满赠满减、加价购、换购等等
     * 5、根据选择，计算订单信息，将上一步权限验证并将结果计入数据中, 店铺商品总价 = 加价购商品总价 + 购物车非活动商品总价（按照限时折扣和团购价计算）
     * 6、
     * 7、结算使用部分
     * 7.1、可用店铺优惠券、店铺礼品卡、店铺红包
     * 7.2、可用平台红包、平台优惠券、礼品卡
     * 7.3、最终折扣计算(最终支付价格打折)
     * 7.4、计算每样商品的佣金
     * 7.5、计算最终运费，运费根据重量计费，同一店铺一次发货，商品独立计算快递费用不合理。
     * 9、计算总价
     *
     * @param in
     * @return
     */
    @Override
    public CheckoutOutput checkout(CheckoutInput in) {

        CheckoutOutput out = formatCartRows(in);

        return out;
    }

    /**
     * 生成订单数据，结算checkout预览及生成订单
     * <p>
     * 1、购物车中商品基本信息读取
     * 2、店铺数据分组，商品仓库分组
     * 3、活动数据、优惠折扣、团购、（阶梯价计算）等等
     * 4、活动商品数据满赠、兑换等等
     * 5、根据选择，计算订单信息，将上一步权限验证并将结果计入数据中, 店铺商品总价 = 加价购商品总价 + 购物车非活动商品总价（按照限时折扣和团购价计算）
     * 6、
     *
     * @param in 购物车数据
     */
    @Override
    public CheckoutOutput formatCartRows(CheckoutInput in) {
        CheckoutOutput out = new CheckoutOutput();
        out.setUserId(in.getUserId());
        BigDecimal orderProductAmount = BigDecimal.ZERO; //商品订单原价
        BigDecimal orderItemAmount = BigDecimal.ZERO; //单品优惠后价格累加
        BigDecimal orderFreightAmount = BigDecimal.ZERO;
        BigDecimal orderMoneyAmount = BigDecimal.ZERO;
        BigDecimal orderDiscountAmount = BigDecimal.ZERO;
        BigDecimal orderPointsAmount = BigDecimal.ZERO;
        BigDecimal orderSpAmount = BigDecimal.ZERO;

        List<Long> itemIds = CommonUtil.column(in.getItems(), CheckoutItemVo::getItemId);
        List<Integer> storeIds = CommonUtil.column(in.getItems(), CheckoutItemVo::getStoreId);

        List<ProductItemVo> productItemList = productBaseRepository.getItems(itemIds, in.getUserId());

        //活动商品数量 多件折 等使用
        Map<Integer, Integer> activityItemQuantityTotalMap = new HashMap<>();

        //店铺分组
        Map<Integer, List<ProductItemVo>> storeItemsMap = new HashMap<>();

        for (ProductItemVo it : productItemList) {
            // 根据店铺分组数据
            List<ProductItemVo> storeItemsList = ObjectUtil.defaultIfNull(storeItemsMap.get(it.getStoreId()), new ArrayList<>());
            if (CollUtil.isEmpty(storeItemsList)) storeItemsMap.put(it.getStoreId(), storeItemsList);

            //设置购物车商品数量
            CheckoutItemVo checkoutItemVo = in.getItems().stream().filter(s -> s.getItemId().equals(it.getItemId())).findFirst().orElse(new CheckoutItemVo());
            ProductItemVo productItemVo = BeanUtil.copyProperties(it, ProductItemVo.class);

            //处理商品购买信息
            productItemVo.setCartId(checkoutItemVo.getCartId());
            productItemVo.setCartQuantity(checkoutItemVo.getCartQuantity());
            productItemVo.setCartSelect(checkoutItemVo.getCartSelect());

            //判断可用库存
            productItemVo.setAvailableQuantity(it.getItemQuantity() - it.getItemQuantityFrozen());
            productItemVo.setIsOnSale(ProductItemRepository.ifOnSale(it));

            //商品是否可销售
            if (checkoutItemVo.getCartSelect()) {
                productItemVo.setCartSelect(productItemVo.getIsOnSale());
            }

            //判断是否在配送区域
            if (checkoutItemVo.getCartSelect()) {
                productItemVo.setCartSelect(true);
            }

            storeItemsList.add(productItemVo);
        }

        UserDeliveryAddress deliveryAddress = new UserDeliveryAddress();

        //配送地址 || 联系方式
        if (CheckUtil.isEmpty(in.getUdId())) {
            // 默认配送地址
            UserDeliveryAddress defaultAddress = userDeliveryAddressRepository.findOne(new QueryWrapper<UserDeliveryAddress>().eq("ud_is_default", 1).eq("user_id", in.getUserId()));

            //配送地址为空，自动将第一个地址填入订单中
            if (defaultAddress == null) {
                deliveryAddress = userDeliveryAddressRepository.findOne(new QueryWrapper<UserDeliveryAddress>().eq("user_id", in.getUserId()));
            } else {
                deliveryAddress = defaultAddress;
            }
        } else {
            deliveryAddress = userDeliveryAddressRepository.get(in.getUdId());
        }

        out.setUserDeliveryAddress(deliveryAddress);

        //店铺信息
        //List<StoreBase> storeLists = storeBaseService.gets(storeIds);
        //可用代金券列表
        UserVoucherListReq userVoucherListReq = new UserVoucherListReq();
        userVoucherListReq.setUserId(in.getUserId());
        userVoucherListReq.setVoucherStateId(StateCode.VOUCHER_STATE_UNUSED);
        userVoucherListReq.setPage(1);
        userVoucherListReq.setSize(ConstantConfig.MAX_LIST_NUM);
        userVoucherListReq.setVoucherEffect(true);
        IPage<UserVoucherRes> voucherResIPage = voucherService.getList(userVoucherListReq);
        List<UserVoucherRes> voucherItems = voucherResIPage.getRecords();

        for (Integer storeId : storeIds) {
            //是否虚拟商品
            Boolean isVirtual = false;
            Integer kindId = 0;

            //是否需要配送
            Boolean isDelivery = false;

            if (in.getDeliveryTypeId() != null) {
                if (in.getDeliveryTypeId().equals(StateCode.DELIVERY_TYPE_EXP)) {
                    isDelivery = true;
                }
            }

            StoreItemVo storeItemVo = new StoreItemVo();
            List<ProductItemVo> items = storeItemsMap.getOrDefault(storeId, new ArrayList<>());

            if (CollUtil.isEmpty(items)) {
                continue;
            }

            BigDecimal productAmount = BigDecimal.ZERO;
            BigDecimal freightAmount = BigDecimal.ZERO;
            BigDecimal discountAmount = BigDecimal.ZERO;
            BigDecimal moneyAmount = BigDecimal.ZERO;
            BigDecimal itemAmount = BigDecimal.ZERO; //商品累加销售总额
            BigDecimal pointsAmount = BigDecimal.ZERO;
            BigDecimal spAmount = BigDecimal.ZERO;
            BigDecimal itemSubtotal = BigDecimal.ZERO;

            //涉及商品个数
            int itemSelectedSize = 0;

            for (ProductItemVo item : items) {
                if (!item.getCartSelect()) {
                    continue;
                }

                itemSelectedSize++;

                //todo 处理单品活动价格

                BigDecimal itemOriSubtotal = item.getItemUnitPrice().multiply(BigDecimal.valueOf(item.getCartQuantity()));
                itemSubtotal = item.getItemSalePrice().multiply(BigDecimal.valueOf(item.getCartQuantity()));
                BigDecimal itemPointsSubtotal = item.getItemUnitPoints().multiply(BigDecimal.valueOf(item.getCartQuantity()));

                //汇总
                productAmount = productAmount.add(itemOriSubtotal);
                itemAmount = itemAmount.add(itemSubtotal);
                moneyAmount = moneyAmount.add(itemSubtotal);
                discountAmount = productAmount.subtract(moneyAmount);
                pointsAmount = pointsAmount.add(itemPointsSubtotal);
                //spAmount = spAmount.add(item.getItemUnitSp());

                item.setItemPointsSubtotal(itemPointsSubtotal);
                item.setItemSubtotal(itemSubtotal);
                item.setItemDiscountAmount(itemOriSubtotal.subtract(itemSubtotal));

                //为虚拟商品，有一个就是虚拟的
                List<Integer> kinds = Arrays.asList(StateCode.PRODUCT_KIND_FUWU, StateCode.PRODUCT_KIND_CARD, StateCode.PRODUCT_KIND_EDU);
                if (kinds.contains(item.getKindId())) {
                    isVirtual = true;
                }

                kindId = item.getKindId();
            }
            // 过滤掉不可使用的优惠券，
            Iterator<UserVoucherRes> iter = voucherItems.iterator();

            while (iter.hasNext()) {
                UserVoucherRes voucherItem = iter.next();
                //指定优惠券
                String itemIdStr = voucherItem.getItemId();
                BigDecimal voucherSubtotal = voucherItem.getVoucherSubtotal();

                if (StrUtil.isNotEmpty(itemIdStr)) {
                    List<Long> itemIdList = Convert.toList(Long.class, itemIdStr);

                    //指定优惠券itemIdList不包含购买的任何商品
                    if (Collections.disjoint(itemIdList, itemIds)) {
                        // 删除不符合条件的优惠券
                        iter.remove();
                        continue;
                    }

                    //使用优惠券的订单金额 （指定）
                    BigDecimal assignProductAmount = BigDecimal.ZERO;
                    for (ProductItemVo item : items) {

                        if (itemIdList.contains(item.getItemId())) {
                            BigDecimal itemSalSubtotal = item.getItemSalePrice().multiply(BigDecimal.valueOf(item.getCartQuantity()));
                            assignProductAmount = assignProductAmount.add(itemSalSubtotal);
                        }
                    }
                    if (ObjectUtil.compare(assignProductAmount, voucherSubtotal) < 0) {
                        iter.remove();
                        continue;
                    }
                }

                //使用优惠券的订单金额 （全部）
                if (ObjectUtil.compare(itemAmount, voucherSubtotal) < 0) {
                    iter.remove();
                }
            }

            storeItemVo.setIsVirtual(isVirtual);
            storeItemVo.setKindId(kindId);
            storeItemVo.setItems(items);
            storeItemVo.setMoneyItemAmount(itemAmount);

            //店铺选中的使用代金券
            UserVoucherRes voucherItemSelected = null;

            for (UserVoucherRes voucherItem : voucherItems) {
                if (voucherItem.getStoreId().equals(storeId) && voucherItem.getUserId().equals(in.getUserId())) {
                    storeItemVo.getVoucherItems().add(voucherItem);

                    //判断是否为选中的
                    if (ObjectUtil.isNotEmpty(in.getUserVoucherIds()) && in.getUserVoucherIds().contains(voucherItem.getUserVoucherId())) {
                        voucherItemSelected = voucherItem;
                    }
                }
            }

            //配送地址

            //1、门店自提，不需要配送运费的。 is_delivery = false
            //2、虚拟商品，不需要配送运费的。 is_delivery = false


            // 是否需要配送地址
            if (isVirtual) {
                //todo 判断是否有需要上门服务，需要计算配送费
                isDelivery = false;
            }

            // 是否需要配送地址， 自提及虚拟到店服务，不需要配送地址。
            if (isDelivery) {
                // 计算运费
                // 如果没有配送地址，则忽略地址选择问题。
                if (deliveryAddress != null && deliveryAddress.getUdCityId() != null) {
                    Integer district_id = deliveryAddress.getUdCityId();
                    freightAmount = calTransportFreight(storeItemVo, district_id);// 配送检测
                } else {
                    //throw new BusinessException(__("请选择正确的收货地址！"));
                }
                // end 运费计算
            }

            //优惠券 voucherItemSelected 修正最终付款价格： moneyAmount， 不修正itemAmount原价
            if (ObjectUtil.isNotEmpty(voucherItemSelected)) {
                storeItemVo.setUserVoucherId(voucherItemSelected.getUserVoucherId());
                storeItemVo.setVoucherAmount(voucherItemSelected.getVoucherPrice());
                //moneyAmount = moneyAmount.subtract(voucherItemSelected.getVoucherPrice());
            }

            moneyAmount = NumberUtil.sub(NumberUtil.add(moneyAmount, freightAmount), storeItemVo.getVoucherAmount());

            storeItemVo.setDiscountAmount(discountAmount);
            storeItemVo.setProductAmount(productAmount);
            storeItemVo.setMoneyAmount(moneyAmount); //下单时候运费及活动会修正此值
            storeItemVo.setMoneyItemAmount(itemAmount);
            storeItemVo.setPointsAmount(pointsAmount);
            storeItemVo.setFreightAmount(freightAmount);

            out.getItems().add(storeItemVo);

            orderProductAmount = orderProductAmount.add(productAmount);
            orderItemAmount = orderItemAmount.add(itemAmount);
            orderMoneyAmount = orderMoneyAmount.add(moneyAmount);
            orderFreightAmount = orderFreightAmount.add(freightAmount);
            orderDiscountAmount = orderDiscountAmount.add(discountAmount);
            orderPointsAmount = orderPointsAmount.add(pointsAmount);
            orderSpAmount = orderSpAmount.add(spAmount);
        }

        out.setOrderProductAmount(orderProductAmount);
        out.setOrderItemAmount(orderProductAmount);
        out.setOrderFreightAmount(orderFreightAmount);
        out.setOrderMoneyAmount(orderMoneyAmount);
        out.setOrderDiscountAmount(orderDiscountAmount);
        out.setOrderPointsAmount(orderPointsAmount);
        out.setOrderSpAmount(orderSpAmount);

        return out;
    }


    /**
     * 配送区域判断及运费, 并修正最终数据
     * <p>
     * 1、单品在一个订单中，运费规则计算
     * 2、配送区域问题
     *
     * @param storeItemVo 最总数据
     * @param district_id 配送地区
     */
    @Override
    public BigDecimal calTransportFreight(StoreItemVo storeItemVo, Integer district_id) {
        //运费模板  商品数量
        Map<Integer, Integer> ttIdsMap = new HashMap();

        List transport_type_none_ids = new ArrayList();
        List delivery_item_none_row = new ArrayList();

        //todo 如果为礼包等等不计费运费商品，可以计算运费时候，从items中忽略

        // 运费
        BigDecimal freight = BigDecimal.ZERO;
        BigDecimal freightFreeAmountMax = BigDecimal.ZERO;

        // 按照店铺订单计算运费
        Integer store_id = 0;
        boolean ifEnabledFreeFreight = false;

        List<ProductItemVo> items = storeItemVo.getItems();

        //处理 ttIdsMap
        for (ProductItemVo pi : items) {
            Integer quantity = ObjectUtil.defaultIfNull(ttIdsMap.get(pi.getTransportTypeId()), 0);
            quantity = quantity + pi.getCartQuantity();

            ttIdsMap.put(pi.getTransportTypeId(), quantity);
        }


        if (ifEnabledFreeFreight) {

        } else {

            List<Integer> ttIds = CommonUtil.column(storeItemVo.getItems(), ProductItemVo::getTransportTypeId);
            List<StoreTransportType> storeTransportTypes = new ArrayList<>();

            // 判断运费方式，如果发现同一个订单计费模式不一致，报错，禁止下单。
            if (CollUtil.isNotEmpty(ttIds)) {
                storeTransportTypes = storeTransportTypeRepository.gets(ttIds);
                List<Integer> transport_type_pricing_method = storeTransportTypes.stream().map(s -> s.getTransportTypePricingMethod()).distinct().collect(Collectors.toList());

                if (transport_type_pricing_method.size() > 1) {
                    throw new BusinessException(__("所选商品运费模式不统一，请拆分下单！"));
                }
            } else {
                throw new BusinessException(__("商品运费设置有误！请联系商家检查商品设置！"));
            }


            Integer transport_type_pricing_method = null;

            BigDecimal moneyItemAmount = storeItemVo.getMoneyItemAmount();
            /*
            Integer transport_type_id = ttIds.get(0);

            Optional<StoreTransportType> transportTypeOpl = transport_type_rows.stream().filter(s -> ObjectUtil.equal(s.getTransport_type_id(), transport_type_id)).findFirst();
            StoreTransportType transportType = transportTypeOpl.orElseGet(StoreTransportType::new);
            Integer transport_type_pricing_method = transportType.getTransport_type_pricing_method();

             */

            if (false && (transport_type_pricing_method != null && 4 == transport_type_pricing_method)) {
                // 按照配送区域计费
                // 根据经纬度计算运费
            } else {

                // 按件计费
                for (Integer tt_id : ttIds) {
                    Integer quantity = Convert.toInt(ttIdsMap.get(tt_id));
                    Integer tt_transport_type_id = Convert.toInt(tt_id);

                    //此处免运费，传递的是transport_type中的设置。
                    Optional<StoreTransportType> transportTypeOpl = storeTransportTypes.stream().filter(s -> ObjectUtil.equal(s.getTransportTypeId(), tt_id)).findFirst();
                    StoreTransportType transportType = transportTypeOpl.orElseGet(StoreTransportType::new);
                    BigDecimal freightFreeAmount = transportType.getTransportTypeFreightFree();

                    freightFreeAmountMax = NumberUtil.max(freightFreeAmountMax, freightFreeAmount);

                    OrderFreightVo data = storeTransportTypeRepository.calFreight(tt_transport_type_id, district_id, quantity, moneyItemAmount, freightFreeAmount);

                    boolean type_freight = data.getCanDelivery();
                    BigDecimal _freight = data.getFreight();

                    if (!type_freight) {
                        // 配送不到这个区域，提示删除商品
                        transport_type_none_ids.add(tt_transport_type_id);
                    } else {
                        freight = NumberUtil.add(freight, _freight);
                    }
                }


                // 配送区域无货设置；
                if (CollUtil.isNotEmpty(transport_type_none_ids)) {
                    for (ProductItemVo item_row : items) {
                        // 配送区域 库存问题。
                        Integer _transport_type_id = item_row.getTransportTypeId();
                        boolean show_oos = transport_type_none_ids.contains(_transport_type_id);
                        item_row.setIsOos(show_oos);

                        delivery_item_none_row.add(item_row);
                    }
                }
            }
        }

        if (storeItemVo.getMoneyItemAmount().compareTo(freightFreeAmountMax) < 0) {
            storeItemVo.setFreightFreeBalance(NumberUtil.sub(freightFreeAmountMax, storeItemVo.getMoneyItemAmount()).abs());
        } else {
            storeItemVo.setFreightFreeBalance(BigDecimal.ZERO);
        }

        return freight;
    }


    @Override
    public boolean addCart(CartAddInput in) {
        //流程可配置 节点触发api调用

        //todo  判断库存，提示加入购物车
        if (in.getCartQuantity() <= 0) {
            throw new BusinessException(__("最低备货数量 1 件，请确认！"));
        }

        ProductItem productItem = productItemRepository.get(in.getItemId());

        if (!productItem.getItemEnable().equals(StateCode.PRODUCT_STATE_NORMAL)) {
            throw new BusinessException(__("商品未上架，不可加入购物车！"));
        }

        //判断可用库存
        int availableQuantity = productItem.getItemQuantity() - productItem.getItemQuantityFrozen();


        UserCart cart = repository.findOne(new QueryWrapper<UserCart>().eq("user_id", in.getUserId()).eq("item_id", in.getItemId()));

        if (ObjectUtil.isEmpty(cart)) {
            if (in.getCartQuantity() > availableQuantity) {
                throw new BusinessException(String.format(__("库存可用数量 %d 件，请确认！"), availableQuantity));
            }

            cart = BeanUtil.copyProperties(in, UserCart.class);
            cart.setProductId(productItem.getProductId());
            cart.setItemId(productItem.getItemId());
        } else {
            if (in.getCartQuantity() + cart.getCartQuantity() > availableQuantity) {
                throw new BusinessException(String.format(__("库存可用数量 %d 件，请确认！"), availableQuantity));
            }

            cart.setCartQuantity(cart.getCartQuantity() + in.getCartQuantity());
        }

        return repository.save(cart);
    }


    @Override
    public boolean sel(UserCartSelectInput input) {
        Integer userId = input.getUserId();
        Long cartId = input.getCartId();
        Integer storeId = input.getStoreId();
        String action = input.getAction();

        List<Serializable> cartIds = new ArrayList<>();

        if ("all".equals(action)) {
            QueryWrapper<UserCart> cartQueryWrapper = new QueryWrapper<>();
            cartQueryWrapper.eq("user_id", userId);
            cartIds = findKey(cartQueryWrapper);
        } else if ("store".equals(action)) {
            QueryWrapper<UserCart> cartQueryWrapper = new QueryWrapper<>();
            cartQueryWrapper.eq("user_id", userId).eq("store_id", storeId);
            cartIds = findKey(cartQueryWrapper);
        } else {
            cartIds = Arrays.asList(cartId);
        }

        QueryWrapper<UserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("cart_id", cartIds);

        UserCart cart = new UserCart();
        cart.setCartSelect(input.getCartSelect());

        if (!edit(cart, queryWrapper)) {
            throw new BusinessException(__("更改购物车选中状态失败"));
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editQuantity(UserCart userCart, Integer userId) {
        UserCart cart = get(userCart.getCartId());
        boolean result = false;

        if (cart != null && cart.getUserId().equals(userId)) {
            //减少
            if (userCart.getCartQuantity().equals(0)) {
                result = remove(userCart.getCartId());
            } else {
                //增加
                ProductItem productItem = productItemRepository.get(cart.getItemId());

                if (productItem == null) {
                    throw new BusinessException(__("该商品不存在！"));
                }
                //判断可用库存
                int availableQuantity = productItem.getItemQuantity() - productItem.getItemQuantityFrozen();

                if (userCart.getCartQuantity() > availableQuantity) {
                    throw new BusinessException(String.format(__("库存可用数量 %d 件，请确认！"), availableQuantity));
                }

                cart.setCartQuantity(userCart.getCartQuantity());
                result = edit(cart);
            }
        }

        return result;
    }

}
