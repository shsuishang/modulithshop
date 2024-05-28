package com.suisung.shopsuite.sys.job;

import cn.hutool.extra.spring.SpringUtil;
import com.suisung.shopsuite.trade.service.OrderService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UpdateOrderStatusJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        OrderService orderService = SpringUtil.getBean(OrderService.class);

        // 自动取消未支付订单
        orderService.autoCancelOrder();

        // 自动确认收货
        orderService.autoReceive();
    }
}
