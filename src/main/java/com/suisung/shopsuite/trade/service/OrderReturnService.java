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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.core.web.service.IBaseService;
import com.suisung.shopsuite.trade.model.entity.OrderReturn;
import com.suisung.shopsuite.trade.model.entity.OrderReturnItem;
import com.suisung.shopsuite.trade.model.input.OrderReturnInput;
import com.suisung.shopsuite.trade.model.req.OrderReturnListReq;
import com.suisung.shopsuite.trade.model.res.OrderReturnRes;
import com.suisung.shopsuite.trade.model.vo.OrderReturnItemVo;
import com.suisung.shopsuite.trade.model.vo.OrderReturnVo;

import java.util.List;

/**
 * <p>
 * 退款退货表-发货退货,卖家也可以决定不退货退款，买家申请退款不支持。卖家可以主动退款。 服务类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
public interface OrderReturnService extends IBaseService<OrderReturn, OrderReturnListReq> {


    /**
     * @param returnId
     * @return
     */
    OrderReturnVo getByReturnId(String returnId);

    /**
     * 退货单审核-卖家拒绝退款/退货
     *
     * @param orderReturn
     * @return
     */
    boolean refused(OrderReturn orderReturn);

    /**
     * 退货单审核-通过审核
     *
     * @param orderReturn
     * @return
     */
    boolean review(OrderReturn orderReturn, Integer receivingAddress);

    Integer getNextReturnProcess(Integer stateId);

    /**
     * 是否执行退款
     *
     * @param stateId
     * @param nextStateId
     * @return
     */
    boolean checkNeedRefund(Integer stateId, Integer nextStateId);

    /**
     * 处理退单流程
     *
     * @param returnIds
     * @param storeId
     * @param stateId
     * @param orderReturns
     * @param nextStateId
     */
    void dealWithReturn(List<String> returnIds, Integer storeId, Integer stateId, List<OrderReturn> orderReturns, Integer nextStateId);

    /**
     * 修改退单状态
     *
     * @param returnIds
     * @param stateId
     * @param orderReturn
     */
    void editReturnNextState(List<String> returnIds, Integer stateId, OrderReturn orderReturn);

    /**
     * 退款退货列表
     *
     * @param orderReturnListReq
     * @return
     */
    IPage<OrderReturnRes> getList(OrderReturnListReq orderReturnListReq);

    /**
     * 读取退款退货
     *
     * @param returnId
     * @return
     */
    OrderReturnRes getReturn(String returnId);

    /**
     * 取消退款订单
     *
     * @param returnId
     * @param userId
     * @return
     */
    boolean cancel(String returnId, Integer userId);

    /**
     * 订单item详情,列出订单的item，及退款详情
     *
     * @param orderId
     * @param orderItemId
     * @param userId
     * @return
     */
    OrderReturnItemVo returnItem(String orderId, String orderItemId, Integer userId);

    /**
     * 添加退款退货
     *
     * @param orderReturnInput
     * @return
     */
    String addItem(OrderReturnInput orderReturnInput);

    boolean addReturnByItem(OrderReturn orderReturn, List<OrderReturnItem> returnItems);

    /**
     * 保存退款退货表
     *
     * @param returnId
     * @param orderIsSettlemented
     * @return
     */
    boolean saveOrderReturn(String returnId, boolean orderIsSettlemented);

    /**
     * 编辑退款信息
     *
     * @param orderReturn
     * @return
     */
    boolean editReturn(OrderReturn orderReturn);

    /**
     * 退款退货表
     * @param orderReturnListReq
     * @return
     */
    IPage<OrderReturnRes> pageList(OrderReturnListReq orderReturnListReq);
}
