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

import cn.hutool.core.util.NumberUtil;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.shop.model.entity.StoreAnalytics;
import com.suisung.shopsuite.shop.model.req.StoreAnalyticsListReq;
import com.suisung.shopsuite.shop.repository.StoreAnalyticsRepository;
import com.suisung.shopsuite.shop.service.StoreAnalyticsService;
import com.suisung.shopsuite.trade.model.vo.StoreAnalyticsVo;
import com.suisung.shopsuite.trade.model.vo.StoreCreditVo;
import com.suisung.shopsuite.trade.model.vo.StoreDeliverycreditVo;
import com.suisung.shopsuite.trade.model.vo.StoreInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 店铺统计表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-01
 */
@Service
public class StoreAnalyticsServiceImpl extends BaseServiceImpl<StoreAnalyticsRepository, StoreAnalytics, StoreAnalyticsListReq> implements StoreAnalyticsService {
    @Override
    public List<StoreInfoVo> getAnalytics(List<Integer> storeIds, List<StoreInfoVo> infoVoList) {
        List<StoreAnalytics> analyticsList = gets(storeIds);

        List<StoreAnalyticsVo> analyticsVoList = new ArrayList<>();
        analyticsList.forEach(storeAnalytics -> {
            StoreAnalyticsVo storeAnalyticsVo = new StoreAnalyticsVo();
            BeanUtils.copyProperties(storeAnalytics, storeAnalyticsVo);
            analyticsVoList.add(storeAnalyticsVo);
        });

        for (StoreAnalyticsVo storeAnalyticsVo : analyticsVoList) {
            Integer storeEvaluationNum = storeAnalyticsVo.getStoreEvaluationNum();
            if (CheckUtil.isNotEmpty(storeEvaluationNum)) {
                BigDecimal storeDesccredit = storeAnalyticsVo.getStoreDesccredit();
                BigDecimal storeServicecredit = storeAnalyticsVo.getStoreServicecredit();
                BigDecimal storeDeliverycredit = storeAnalyticsVo.getStoreDeliverycredit();

                float desccredit = NumberUtil.div(storeDesccredit, storeEvaluationNum, 1).floatValue();
                float servicecredit = NumberUtil.div(storeServicecredit, storeEvaluationNum, 1).floatValue();
                float deliverycredit = NumberUtil.div(storeDeliverycredit, storeEvaluationNum, 1).floatValue();

                StoreCreditVo storeCreditVo = new StoreCreditVo();
                StoreDeliverycreditVo storeDesccreditVo = new StoreDeliverycreditVo(desccredit, "__", "equal", " ", __("描述相符"));
                StoreDeliverycreditVo storeServicecreditVo = new StoreDeliverycreditVo(servicecredit, "__", "equal", " ", __("服务态度"));
                StoreDeliverycreditVo storeDeliverycreditVo = new StoreDeliverycreditVo(deliverycredit, "__", "equal", " ", __("发货速度"));
                storeCreditVo.setStoreDesccredit(storeDesccreditVo);
                storeCreditVo.setStoreServicecredit(storeServicecreditVo);
                storeCreditVo.setStoreDeliverycredit(storeDeliverycreditVo);
                storeAnalyticsVo.setStoreCredit(storeCreditVo);
            }
        }

        for (StoreInfoVo storeInfoVo : infoVoList) {
            for (StoreAnalyticsVo storeAnalyticsVo : analyticsVoList) {
                if (storeInfoVo.getStoreId().equals(storeAnalyticsVo.getStoreId())) {
                    BeanUtils.copyProperties(storeAnalyticsVo, storeInfoVo);
                }
            }
        }

        return infoVoList;
    }
}
