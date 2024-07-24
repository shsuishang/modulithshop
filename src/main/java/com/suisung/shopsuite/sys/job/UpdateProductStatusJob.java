package com.suisung.shopsuite.sys.job;

import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.SpringUtil;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UpdateProductStatusJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        ProductIndexService productIndexService = SpringUtil.getBean(ProductIndexService.class);

        try {
            //自动上架
            productIndexService.autoSaleProduct();
        } catch (Exception e) {
            LogUtil.error(ConstantLog.TASK, e);
        }

    }
}
