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
package com.suisung.shopsuite.trade.service;

import com.suisung.shopsuite.core.web.service.IBaseService;
import com.suisung.shopsuite.trade.model.entity.UserCart;
import com.suisung.shopsuite.trade.model.input.CartAddInput;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.input.UserCartSelectInput;
import com.suisung.shopsuite.trade.model.output.CheckoutOutput;
import com.suisung.shopsuite.trade.model.req.UserCartListReq;
import com.suisung.shopsuite.trade.model.vo.StoreItemVo;

import java.math.BigDecimal;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-26
 */
public interface UserCartService extends IBaseService<UserCart, UserCartListReq> {

    CheckoutOutput getList(UserCartListReq req);

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
    CheckoutOutput checkout(CheckoutInput in);

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
    CheckoutOutput formatCartRows(CheckoutInput in);

    BigDecimal calTransportFreight(StoreItemVo storeItemVo, Integer district_id);

    boolean addCart(CartAddInput in);

    /**
     * 购物车-选中商品
     *
     * @return
     */
    boolean sel(UserCartSelectInput input);

    /**
     * 修改购物车数量
     *
     * @param userCart
     * @param userId
     * @return
     */
    boolean editQuantity(UserCart userCart, Integer userId);
}
