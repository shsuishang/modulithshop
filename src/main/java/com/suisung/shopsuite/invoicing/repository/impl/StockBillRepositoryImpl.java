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
package com.suisung.shopsuite.invoicing.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.invoicing.dao.StockBillDao;
import com.suisung.shopsuite.invoicing.model.entity.StockBill;
import com.suisung.shopsuite.invoicing.model.entity.StockBillItem;
import com.suisung.shopsuite.invoicing.model.vo.StockBillVo;
import com.suisung.shopsuite.invoicing.repository.StockBillRepository;
import com.suisung.shopsuite.invoicing.service.StockBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 商品出入库单据表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Repository
public class StockBillRepositoryImpl extends BaseRepositoryImpl<StockBillDao, StockBill> implements StockBillRepository {
    @Autowired
    private StockBillItemService stockBillItemService;

    @Override
    public List<StockBillVo> findDetail(QueryWrapper<StockBill> queryWrapper) {
        List<StockBillVo> out = new ArrayList<>();
        List<StockBill> stockBills = find(queryWrapper);

        if (CollUtil.isNotEmpty(stockBills)) {
            out = BeanUtil.copyToList(stockBills, StockBillVo.class);

            List<String> ids = null;
            ids = CommonUtil.column(stockBills, StockBill::getStockBillId);

            if (CollUtil.isNotEmpty(ids)) {
                List<StockBillItem> billItems = stockBillItemService.find(new QueryWrapper<StockBillItem>().in("stock_bill_id", ids));

                if (CollUtil.isNotEmpty(billItems)) {
                    // 处理为map
                    Map<String, List<StockBillItem>> billItemMap = new HashMap<>();

                    for (StockBillItem item : billItems) {
                        if (!billItemMap.containsKey(item.getStockBillId())) {
                            billItemMap.put(item.getStockBillId(), new ArrayList<>());
                        }

                        billItemMap.get(item.getStockBillId()).add(item);
                    }

                    for (StockBillVo vo : out) {
                        List<StockBillItem> stockBillItems = billItemMap.get(vo.getStockBillId());

                        if (CollectionUtil.isNotEmpty(stockBillItems)) {
                            for (StockBillItem stockBillItem : stockBillItems) {
                                String itemSpecName = StrUtil.replaceChars(stockBillItem.getItemName(), ",", " ");
                                stockBillItem.setProductItemName(stockBillItem.getProductName() + " " + itemSpecName);
                            }
                        }

                        vo.setItems(stockBillItems);
                    }
                }
            }
        }

        return out;
    }
}
