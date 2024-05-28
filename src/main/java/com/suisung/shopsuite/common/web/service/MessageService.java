package com.suisung.shopsuite.common.web.service;

import com.suisung.shopsuite.sys.model.entity.AccessHistory;

import java.util.Map;

public interface MessageService {
    /**
     * @param k 队列原始值
     * @access public
     */
    String getKey(String k);

    /**
     * 消息入队列
     *
     * @param user_id
     * @param message_tpl_id
     * @param args
     * @return
     */
    boolean sendNoticeMsg(Integer user_id, String message_tpl_id, Map args);

    /**
     * 从队列中读取
     *
     * @return
     */
    String receiveNoticeMsg();

    /**
     * 消息入队列
     *
     * @param accessDTO
     * @return
     */
    boolean sendAccess(AccessHistory accessDTO);

    /**
     * 从队列中读取
     *
     * @return
     */
    String receiveAccess();


    /**
     * 消息入队列
     *
     * @param accessDTO
     * @return
     */
    boolean sendError(AccessHistory accessDTO);

    /**
     * 从队列中读取
     *
     * @return
     */
    String receiveError();
}
