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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.shop.model.entity.StoreConfig;
import com.suisung.shopsuite.shop.model.req.StoreConfigListReq;
import com.suisung.shopsuite.shop.repository.StoreConfigRepository;
import com.suisung.shopsuite.shop.service.StoreConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 店铺参数设置表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-07
 */
@Service
public class StoreConfigServiceImpl extends BaseServiceImpl<StoreConfigRepository, StoreConfig, StoreConfigListReq> implements StoreConfigService {


    @Override
    public Integer getNextReturnProcess(Integer storeId, Integer stateId) {
        StoreConfig storeConfig = get(storeId);

        if (storeConfig == null || StrUtil.isEmpty(storeConfig.getScOrderReturnProcess())) {
            throw new BusinessException(__("退货单状态配置数据为空"));
        }

        if (stateId == null) {
            throw new BusinessException(__("当前退货单状态为空"));
        }

        List<Integer> processList = Convert.toList(Integer.class, storeConfig.getScOrderReturnProcess());

        if (CollectionUtil.isNotEmpty(processList)) {
            int index = processList.indexOf(stateId);

            if (index == -1) {
                throw new BusinessException(__("退货单状态配置数据有误"));
            }

            if (processList.size() >= index + 2) {
                return processList.get(index + 1);
            } else {
                //客户确认收货
                if (storeId.equals(StateCode.RETURN_PROCESS_CHECK)
                        || storeId.equals(StateCode.RETURN_PROCESS_RECEIVED)
                        || storeId.equals(StateCode.RETURN_PROCESS_REFUND)
                        || storeId.equals(StateCode.RETURN_PROCESS_RECEIPT_CONFIRMATION)) {
                    return StateCode.RETURN_PROCESS_FINISH;
                } else {
                    throw new BusinessException(__("退货单状态配置数据有误"));
                }
            }
        }
        return null;
    }

    @Override
    public boolean checkNeedRefund(Integer stateId, Integer nextStateId) {
        Integer processRefund = StateCode.RETURN_PROCESS_MAP.get(StateCode.RETURN_PROCESS_REFUND);
        Integer processReceiptConfirmation = StateCode.RETURN_PROCESS_MAP.get(StateCode.RETURN_PROCESS_RECEIPT_CONFIRMATION);
        Integer processReturnStateId = StateCode.RETURN_PROCESS_MAP.get(stateId);
        Integer processReturnNextStateId = StateCode.RETURN_PROCESS_MAP.get(nextStateId);

        return ObjectUtil.notEqual(stateId, nextStateId)
                && processReturnStateId <= processRefund
                && processReturnNextStateId >= processReceiptConfirmation;
    }
}
