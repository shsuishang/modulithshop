package com.suisung.shopsuite.common.web.service.impl;

import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.sys.model.entity.AccessHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RedisService redisService;

    private static final String kerPre = "_msg_|";


    /**
     * @param k 队列原始值
     * @access public
     */
    public String getKey(String k) {
        //进入异步队列
        String key = String.format("%s%s", kerPre, k);

        return key;
    }

    /**
     * 发送短信，邮件，站内信通知信息 - 可以修改异步通知
     *
     * @param user_id        用户ID
     * @param message_tpl_id 消息模板ID
     * @return args 参数
     * @access public
     */
    @Override
    public boolean sendNoticeMsg(Integer user_id, String message_tpl_id, Map args) {
        //进入异步队列
        String key = getKey("notice_msg");

        Map<String, Object> msg_data = new HashMap<>();
        msg_data.put("user_id", user_id);
        msg_data.put("message_tpl_id", message_tpl_id);
        msg_data.put("args", args);
        msg_data.put("time", new Date().getTime());

        Long res = redisService.lPush(key, JSONUtil.toJSONString(msg_data));

        return res > 0;
    }

    //示例，需要移动到计划任务中。
    @Override
    public String receiveNoticeMsg() {
        //进入异步队列
        String key = getKey("notice_msg");
        String data = null;

        Integer times = 100000;

        //如果队列有数据，一直读完
        if (redisService.lSize(key) > 0) {
            data = redisService.rPop(key).toString();
        }

        return data;
    }

    @Override
    public boolean sendAccess(AccessHistory accessDTO) {
        //进入异步队列
        String key = getKey("access");

        Long res = redisService.lPush(key, JSONUtil.toJSONString(accessDTO));

        return res > 0;
    }

    @Override
    public String receiveAccess() {
        //进入异步队列
        String key = getKey("access");
        String data = null;

        //如果队列有数据，一直读完
        if (redisService.lSize(key) > 0) {
            data = redisService.rPop(key).toString();
        }

        return data;
    }

    /**
     * 消息入队列
     *
     * @param accessDTO
     * @return
     */
    @Override
    public boolean sendError(AccessHistory accessDTO) {
        //进入异步队列
        String key = getKey("error");

        Long res = redisService.lPush(key, JSONUtil.toJSONString(accessDTO));

        return res > 0;
    }

    /**
     * 从队列中读取
     *
     * @return
     */
    @Override
    public String receiveError() {
        //进入异步队列
        String key = getKey("error");
        String data = null;

        //如果队列有数据，一直读完
        if (redisService.lSize(key) > 0) {
            data = redisService.rPop(key).toString();
        }

        return data;
    }
}
