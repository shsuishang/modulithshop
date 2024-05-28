package com.suisung.shopsuite.common.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.repository.ProductIndexRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.shop.model.entity.UserVoucher;
import com.suisung.shopsuite.shop.repository.UserVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Component
public class ApplicationTaskRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ApplicationTaskRunner.class);

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private ActivityBaseRepository activityBaseRepository;

    @Autowired
    private ActivityBaseService activityBaseService;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Override
    @Async
    public void run(String... args) {
        log.info(__("初始化Task..."));

        //一直死循环执行。
        while (true) {
            try {
                Date curDate = new Date();
                long time = curDate.getTime();

                // 更新活动状态
                QueryWrapper<ActivityBase> baseQueryWrapper = new QueryWrapper<>();
                baseQueryWrapper.eq("activity_state", StateCode.ACTIVITY_STATE_WAITING).lt("activity_starttime", time).ge("activity_endtime", time);
                List<ActivityBase> activityBases = activityBaseRepository.lists(baseQueryWrapper, 1, 10).getRecords();

                if (CollUtil.isNotEmpty(activityBases)) {
                    for (ActivityBase activityBase : activityBases) {
                        activityBase.setActivityState(StateCode.ACTIVITY_STATE_NORMAL);
                        activityBaseService.editActivityBase(activityBase.getActivityId(), activityBase);
                    }
                }

                QueryWrapper<ActivityBase> endQueryWrapper = new QueryWrapper<>();
                endQueryWrapper.eq("activity_state", StateCode.ACTIVITY_STATE_NORMAL).le("activity_endtime", time);
                List<ActivityBase> endActivityBases = activityBaseRepository.lists(endQueryWrapper, 1, 10).getRecords();

                if (CollUtil.isNotEmpty(endActivityBases)) {
                    for (ActivityBase activityBase : endActivityBases) {
                        activityBase.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
                        activityBaseService.editActivityBase(activityBase.getActivityId(), activityBase);
                    }
                }

                // 定时上架
                QueryWrapper<ProductIndex> indexQueryWrapper = new QueryWrapper<>();
                indexQueryWrapper.eq("product_verify_id", StateCode.PRODUCT_VERIFY_PASSED).eq("product_state_id", StateCode.PRODUCT_STATE_OFF_THE_SHELF).le("product_sale_time", time);
                List<ProductIndex> productIndices = productIndexRepository.lists(indexQueryWrapper, 1, 10).getRecords();

                List<ProductItem> productItemList = new ArrayList<>();
                if (CollUtil.isNotEmpty(productIndices)) {
                    List<Long> productIds = productIndices.stream().map(ProductIndex::getProductId).distinct().collect(Collectors.toList());
                    QueryWrapper<ProductItem> itemQueryWrapper = new QueryWrapper<>();
                    itemQueryWrapper.eq("product_id", productIds);
                    productItemList = productItemRepository.find(itemQueryWrapper);
                }

                for (ProductIndex index : productIndices) {
                    for (ProductItem item : productItemList) {
                        if (index.getProductId().equals(item.getProductId())) {
                            Long productId = index.getProductId();
                            //判断item是否全部下架状态
                            if (ObjectUtil.equal(item.getItemEnable(), StateCode.PRODUCT_STATE_NORMAL)) {
                                break;
                            }

                            index.setProductId(productId);
                            index.setProductStateId(StateCode.PRODUCT_STATE_ILLEGAL);
                            if (!productIndexRepository.edit(index)) {
                                throw new BusinessException(ResultCode.FAILED);
                            }
                        }
                    }
                }

                // 优惠券更新
                QueryWrapper<UserVoucher> voucherQueryWrapper = new QueryWrapper<>();
                voucherQueryWrapper.eq("voucher_state_id", StateCode.VOUCHER_STATE_UNUSED).lt("voucher_end_date", time);
                List<Serializable> userVoucherIds = userVoucherRepository.findKey(voucherQueryWrapper);
                UserVoucher voucher = new UserVoucher();
                voucher.setVoucherStateId(StateCode.VOUCHER_STATE_TIMEOUT);
                for (Serializable user_voucher_id : userVoucherIds) {
                    voucher.setUserVoucherId(Convert.toInt(user_voucher_id));
                    if (!userVoucherRepository.edit(voucher)) {
                        throw new BusinessException(ResultCode.FAILED);
                    }
                }

            } catch (Exception e) {
                LogUtil.error(ConstantLog.TASK, e);
            }

            try {
                Thread.sleep(1000 * 60);
                //Thread.sleep(1000);    //延时1s
            } catch (InterruptedException e) {
                LogUtil.error(ConstantLog.TASK, e);
            }
        }
    }
}
