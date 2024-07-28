package com.suisung.shopsuite.sys.job;

import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.SpringUtil;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import com.suisung.shopsuite.sys.model.entity.CrontabBase;
import com.suisung.shopsuite.sys.service.CrontabBaseService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class UpdateProductStatusJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        ProductIndexService productIndexService = SpringUtil.getBean(ProductIndexService.class);

        Date now = new Date();
        CrontabBase crontabBase = new CrontabBase();
        crontabBase.setCrontabId(1003);
        crontabBase.setCrontabLastExeTime(now.getTime());
        CrontabBaseService crontabBaseService = com.suisung.shopsuite.common.utils.SpringUtil.getBean(CrontabBaseService.class);
        crontabBaseService.edit(crontabBase);

        try {
            //自动上架
            productIndexService.autoSaleProduct();
        } catch (Exception e) {
            LogUtil.error(ConstantLog.TASK, e);
        }

    }
}
