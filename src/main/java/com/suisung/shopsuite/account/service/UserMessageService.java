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
package com.suisung.shopsuite.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.model.entity.UserMessage;
import com.suisung.shopsuite.account.model.input.UserMessageAddInput;
import com.suisung.shopsuite.account.model.req.UserMessageListReq;
import com.suisung.shopsuite.account.model.res.MessageNoticeRes;
import com.suisung.shopsuite.account.model.res.MessageRes;
import com.suisung.shopsuite.account.model.res.UserMessageRes;
import com.suisung.shopsuite.core.web.service.IBaseService;

import java.util.List;

/**
 * <p>
 * 短消息-聊天记录 服务类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
public interface UserMessageService extends IBaseService<UserMessage, UserMessageListReq> {

    /**
     * 用户通知消息
     *
     * @param userId
     * @return
     */
    List<UserMessage> getNotice(Integer userId);

    /**
     * 用户通知消息数量
     *
     * @param recentlyFlag
     * @param userId
     * @return
     */
    UserMessageRes getMsgCount(Boolean recentlyFlag, Integer userId);

    /**
     * 短消息列表数据
     *
     * @param userMessageListReq
     * @return
     */
    IPage<UserMessageRes> getList(UserMessageListReq userMessageListReq);

    /**
     * 读取短消息
     *
     * @param messageId
     * @param userId
     * @return
     */
    UserMessage getById(Integer messageId, Integer userId);

    /**
     * 设置为已读
     *
     * @param messageId
     * @param userOtherId
     * @param userId
     * @return
     */
    boolean setRead(Integer messageId, Integer userOtherId, Integer userId);

    /**
     * 添加短消息
     *
     * @param messageAddInput
     * @param userId
     * @return
     */
    UserMessageRes addMessage(UserMessageAddInput messageAddInput, Integer userId);

    /**
     * 读取分页列表
     *
     * @param userMessageListReq
     * @return
     */
    IPage<MessageRes> listChatMsg(UserMessageListReq userMessageListReq);

    /**
     * 系统通知消息
     *
     * @param adminUserId
     * @param userId
     * @param msgContent
     * @param msgType
     */
    void sendSysNotice(Integer adminUserId, Integer userId, String msgContent, int msgType);

    /**
     * 获取系统通知消息
     *
     * @return
     */
    MessageNoticeRes getPlantFromNotice();

    /**
     * 清空未读消息
     *
     * @return
     */
    boolean clearNotice();

    /**
     * 消息中心-信息数
     *
     * @param userMessageListReq
     * @return
     */
    UserMessageRes getMessageNum(UserMessageListReq userMessageListReq);
}
