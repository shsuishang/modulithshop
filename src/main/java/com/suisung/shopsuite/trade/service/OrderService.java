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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.trade.model.entity.OrderLogistics;
import com.suisung.shopsuite.trade.model.input.CheckoutInput;
import com.suisung.shopsuite.trade.model.input.OrderNumInput;
import com.suisung.shopsuite.trade.model.input.OrderPickingInput;
import com.suisung.shopsuite.trade.model.input.OrderShippingInput;
import com.suisung.shopsuite.trade.model.output.OrderAddOutput;
import com.suisung.shopsuite.trade.model.output.OrderNumOutput;
import com.suisung.shopsuite.trade.model.req.OrderInfoListReq;
import com.suisung.shopsuite.trade.model.req.OrderInvoiceAddReq;
import com.suisung.shopsuite.trade.model.vo.OrderVo;

import java.util.List;

/**
 * <p>
 * 订单详细信息-检索不分表也行，cache 服务类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
public interface OrderService {

    /**
     * 读取订单
     *
     * @param orderId
     * @return
     */
    OrderVo detail(String orderId);

    /**
     * 订单搜索查询列表
     *
     * @param in
     * @return
     */
    Page<OrderVo> lists(OrderInfoListReq in);

    /**
     * 新增
     *
     * @param in
     * @return
     */
    OrderAddOutput add(CheckoutInput in);

    /**
     * 取消订单
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    Boolean cancel(String orderId, String orderStateNote);

    /**
     * 支付完成
     *
     * @param orderId
     * @return
     */
    Boolean setPaidYes(String orderId);

    /**
     * 审核订单
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    Boolean review(String orderId, String orderStateNote);

    /**
     * 财务审核
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    Boolean finance(String orderId, String orderStateNote);

    /**
     * 出库审核
     *
     * @param in
     * @return
     */
    Boolean picking(OrderPickingInput in);

    /**
     * 判断是否有待审核售后订单条件限制
     *
     * @param orderId
     * @return
     */
    Boolean checkOrderReturnWaiting(String orderId);

    /**
     * 发货
     *
     * @param in
     * @return
     */
    Boolean shipping(OrderShippingInput in);

    /**
     * 检测是否发货完成
     *
     * @param orderId
     * @return
     */
    Boolean checkShippingComplete(String orderId);

    Boolean addLogistics(OrderLogistics in);

    Boolean saveLogistics(OrderLogistics in);

    /**
     * 确认收货
     *
     * @param orderId
     * @param orderStateNote
     * @return
     */
    Boolean receive(String orderId, String orderStateNote);

    /**
     * 修改订单为下一个待处理状态
     *
     * @param orderId
     * @param orderStateId
     * @param nextOrderStateId
     * @param orderStateNote
     * @return
     */
    Boolean editNextState(String orderId, Integer orderStateId, Integer nextOrderStateId, String orderStateNote);

    /**
     * 出库审核 - 逻辑封装 - 涉及进销存
     *
     * @param in
     * @return
     */
    Integer doReviewPicking(OrderPickingInput in);

    /**
     * 发货审核  - 涉及快递单号处理
     *
     * @param in
     * @return
     */
    Integer doReviewShipping(OrderShippingInput in);

    /**
     * 审核订单到某个状态
     *
     * @param orderId
     * @param toOrderStateId
     * @return
     */
    Boolean reviewToState(String orderId, Integer toOrderStateId);

    Long getOrderNum(OrderNumInput in);

    OrderNumOutput getOrderStatisticsInfo(Integer userId);

    /**
     * 批量修改订单-提现审核
     *
     * @param orderIds
     * @return
     */
    boolean doUpdateOrders(List<String> orderIds);

    /**
     * 自动取消未支付订单
     */
    void autoCancelOrder();

    /**
     * 自动确认收货
     */
    void autoReceive();

    /**
     * 申请订单发票
     *
     * @param orderInvoiceAddReq
     * @return
     */
    boolean addOrderInvoice(OrderInvoiceAddReq orderInvoiceAddReq);

    /**
     * 代客下单
     *
     * @param orderBase
     * @return
     */
    OrderAddOutput replaceAdd(CheckoutInput orderBase);

    /**
     * 判断是否有活动条件限制
     *
     * @param orderId
     * @return
     */
    boolean ifActivity(String orderId);
}
