package com.suisung.shopsuite.sys.job;

import cn.hutool.extra.spring.SpringUtil;
import com.suisung.shopsuite.sys.model.entity.CrontabBase;
import com.suisung.shopsuite.sys.service.CrontabBaseService;
import com.suisung.shopsuite.trade.service.OrderService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class UpdateOrderStatusJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        OrderService orderService = SpringUtil.getBean(OrderService.class);

        Date now = new Date();
        CrontabBase crontabBase = new CrontabBase();
        crontabBase.setCrontabId(1001);
        crontabBase.setCrontabLastExeTime(now.getTime());
        CrontabBaseService crontabBaseService = com.suisung.shopsuite.common.utils.SpringUtil.getBean(CrontabBaseService.class);
        crontabBaseService.edit(crontabBase);

        // 自动取消未支付订单
        orderService.autoCancelOrder();

        // 自动确认收货
        orderService.autoReceive();
    }
}
