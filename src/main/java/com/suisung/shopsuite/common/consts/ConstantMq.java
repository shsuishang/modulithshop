package com.suisung.shopsuite.common.consts;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 消息队列相关常量定义
 */
@Component
@ToString
public class ConstantMq {

    public static final Integer INIT = 1; // 初始状态
    public static final Integer FAILURE = 2; // 消息消费失败更改状态
    public static final Integer DELIVERED = 3; // 消息重试消费成功更改状态



    public static final Integer MAX_COUNT = 3; // 消息重新投递最大重试次数
    public static final String SHOP_EXCHANGE = "shop-event-exchange"; // SHOP服务交换机
    public static final String ACCOUNT_EXCHANGE = "account-event-exchange"; // ACCOUNT服务交换机
    public static final String PAY_EXCHANGE = "pay-event-exchange"; // PAY服务交换机
    public static final String SHOP_PAIDYES_QUEUE = "shop.paid_yes.queue"; // 支付回调处理队列
    public static final String SHOP_PAIDYES_ROUTING_KEY = "shop.paid_yes_routing_key"; // 支付回调队列路由键
    public static final String SHOP_MSG_QUEUE = "shop.msg.queue"; // 站内信处理队列
    public static final String SHOP_MSG_ROUTING_KEY = "shop.msg_routing_key"; // 站内信队列路由键
    public static final String PAY_POINT_QUEUE = "pay.point.queue"; // 用户积分处理队列
    public static final String PAY_POINT_ROUTING_KEY = "pay.point_routing_key"; // 用户积分队列路由键
    public static final String PAY_MONEY_QUEUE = "pay.money.queue"; // 用户余额处理队列
    public static final String PAY_MONEY_ROUTING_KEY = "pay.money_routing_key"; // 用户余额队列路由键
    public static final String PAY_TRADE_QUEUE = "pay.trade.queue"; // 订单交易处理队列
    public static final String PAY_TRADE_ROUTING_KEY = "pay.trade_routing_key"; // 订单交易队列路由键

    public static final String PAY_REFUND_QUEUE = "pay.refund.queue"; // 退款处理队列
    public static final String PAY_REFUND_ROUTING_KEY = "pay.refund_routing_key"; // 退款处理队列路由键
    public static final String ACCOUNT_EXPERIENCE_QUEUE = "account.experience.queue"; // 用户经验处理队列
    public static final String ACCOUNT_EXPERIENCE_ROUTING_KEY = "account.experience_routing_key"; // 用户经验队列路由键
    public static final String ACCOUNT_INFO_QUEUE = "account.info.queue"; // 用户详细信息处理队列
    public static final String ACCOUNT_INFO_ROUTING_KEY = "account.info_routing_key"; // 用户详细信息队列路由键
    public static final String ACCOUNT_ANALYTICS_QUEUE = "account.analytics.queue"; // 用户角色信息处理队列
    public static final String ACCOUNT_ANALYTICS_ROUTING_KEY = "account.analytics_routing_key"; // 用户角色信息升级队列路由键
    public static final String ACCOUNT_UPGRADE_QUEUE = "account.upgrade.queue"; // 用户升级处理队列
    public static final String ACCOUNT_UPGRADE_ROUTING_KEY = "account.upgrade_routing_key"; // 用户用户升级队列路由键

}
