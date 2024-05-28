package com.suisung.shopsuite.common.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.suisung.shopsuite.common.pojo.dto.ErrorTypeEnum;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.sys.service.MessageTemplateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
public class ApplicationMsgRunner implements CommandLineRunner {

    @Resource
    private MessageService messageService;

    @Resource
    private MessageTemplateService messageTemplateService;

    @Override
    @Async
    public void run(String... args) {

        while (true) {
            String data = messageService.receiveNoticeMsg();

            if (data != null) {
                JSONObject queue_row = JSONUtil.parseObj(data);

                Integer userId = (Integer) queue_row.get("user_id");

                String messageTplId = queue_row.get("message_tpl_id").toString();
                Map<String, Object> msgArgs = Convert.toMap(String.class, Object.class, queue_row.get("args"));
                msgArgs.put("order_id", msgArgs.get("order_id"));
                msgArgs.put("product_name", msgArgs.get("product_name"));
                msgArgs.put("order_payment_amount", msgArgs.get("order_payment_amount"));
                msgArgs.put("order_add_time", msgArgs.get("order_add_time"));

                Long time = (Long) queue_row.get("time");

                //判断是否超时
                if (true || new Date().getTime() - time < 60 * 10) {
                    //发送消息
                    try {
                        messageTemplateService.send(userId, messageTplId, msgArgs);
                    } catch (Exception e) {
                        LogUtil.error(ErrorTypeEnum.ERR_PSUH_MSG.getValue(), e);
                    }
                }
            }

            try {
                Thread.sleep(50);    //延时0.05s
            } catch (InterruptedException e) {
                LogUtil.error("线程休眠中断异常！" + e.getMessage(), e);
            }

        }
    }

}
