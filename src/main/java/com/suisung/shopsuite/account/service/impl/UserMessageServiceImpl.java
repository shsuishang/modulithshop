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
package com.suisung.shopsuite.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.entity.UserMessage;
import com.suisung.shopsuite.account.model.input.UserMessageAddInput;
import com.suisung.shopsuite.account.model.req.UserMessageListReq;
import com.suisung.shopsuite.account.model.res.*;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.account.repository.UserMessageRepository;
import com.suisung.shopsuite.account.service.UserMessageService;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.ProductImage;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.repository.ProductImageRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 短消息-聊天记录 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Service
public class UserMessageServiceImpl extends
        BaseServiceImpl<UserMessageRepository, UserMessage, UserMessageListReq> implements UserMessageService {

    @Autowired
    private UserMessageRepository messageRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    @Override
    public List<UserMessage> getNotice(Integer userId) {
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userId);
        messageQueryWrapper.eq("message_type", 1);
        messageQueryWrapper.gt("message_time", DateUtil.offsetDay(new Date(), -10).getTime());

        return find(messageQueryWrapper);
    }

    @Override
    public UserMessageRes getMsgCount(Integer recentlyFlag, Integer userId) {
        UserMessageRes userMessageRes = new UserMessageRes();
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userId)
                .eq("message_kind", 2)
                .eq("message_is_read", 0);
        long count = count(messageQueryWrapper);
        userMessageRes.setNum((int) count);

        //构造最后一个用户聊天网址
        if (count > 0L) {
            QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId)
                    .eq("message_kind", 2)
                    .eq("message_is_read", 0)
                    .orderByDesc("message_time");

            if (CheckUtil.isNotEmpty(recentlyFlag)) {
                long time = new Date().getTime();
                queryWrapper.gt("message_time", (time - 60 * 5 * 1000));
            }
            UserMessage message = messageRepository.findOne(queryWrapper);

            if (message != null) {
                userMessageRes.setMsgRow(message);
            }
        }

        return userMessageRes;
    }

    @Override
    public IPage<UserMessageRes> getList(UserMessageListReq userMessageListReq) {
        IPage<UserMessageRes> userMessageResPage = new Page<>();
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userMessageListReq.getUserId())
                .orderByDesc("message_time")
                .orderByAsc("message_is_read");

        if (CheckUtil.isNotEmpty(userMessageListReq.getUserOtherId())) {
            messageQueryWrapper.eq("user_other_id", userMessageListReq.getUserOtherId());
        }

        if (CheckUtil.isNotEmpty(userMessageListReq.getMessageKind())) {
            messageQueryWrapper.eq("message_kind", userMessageListReq.getMessageKind());
        } else {
            messageQueryWrapper.eq("message_kind", 2);
        }

        if (userMessageListReq.getMessageIsRead() != null) {
            messageQueryWrapper.eq("message_is_read", userMessageListReq.getMessageIsRead());
        }

        if (StrUtil.isNotEmpty(userMessageListReq.getUserNickname())) {
            messageQueryWrapper.eq("user_nickname", userMessageListReq.getUserNickname());
        }
        IPage<UserMessage> userMessagePage = lists(messageQueryWrapper, userMessageListReq.getPage(), userMessageListReq.getSize());

        if (userMessagePage != null && CollectionUtil.isNotEmpty(userMessagePage.getRecords())) {
            BeanUtils.copyProperties(userMessagePage, userMessageResPage);
            List<UserMessage> userMessageList = userMessagePage.getRecords();
            //用户头像
            Map<Integer, String> userMap = getUserAvatarMap(userMessageList);
            //相关用户头像
            Map<Integer, String> otherUserMap = getOtherUserAvatarMap(userMessageList);
            List<UserMessageRes> userMessageRes = new ArrayList<>();
            for (UserMessage userMessage : userMessageList) {
                UserMessageRes messageRes = new UserMessageRes();
                BeanUtils.copyProperties(userMessage, messageRes);

                if (userMessage.getMessageKind().equals(1)) {

                    if (userMap != null && !userMap.isEmpty()) {
                        messageRes.setUserAvatar(userMap.get(userMessage.getUserId()));
                    }
                } else {

                    if (otherUserMap != null && !otherUserMap.isEmpty()) {
                        messageRes.setUserOtherAvatar(otherUserMap.get(userMessage.getUserOtherId()));
                    }
                }
                userMessageRes.add(messageRes);
            }
            userMessageResPage.setRecords(userMessageRes);
        }

        return userMessageResPage;
    }

    private Map<Integer, String> getOtherUserAvatarMap(List<UserMessage> userMessageList) {
        List<Integer> otherUserIds = userMessageList.stream().map(UserMessage::getUserOtherId).distinct().collect(Collectors.toList());
        List<UserInfo> otherUserInfos = userInfoRepository.gets(otherUserIds);

        if (CollectionUtil.isEmpty(otherUserInfos)) {
            return null;
        }

        return otherUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getUserAvatar, (k1, k2) -> k1));
    }

    private Map<Integer, String> getUserAvatarMap(List<UserMessage> userMessageList) {
        List<Integer> userIds = userMessageList.stream().map(UserMessage::getUserId).distinct().collect(Collectors.toList());
        List<UserInfo> userInfos = userInfoRepository.gets(userIds);

        if (CollectionUtil.isEmpty(userInfos)) {
            return null;
        }

        return userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getUserAvatar, (k1, k2) -> k1));
    }

    @Override
    public UserMessage getById(Integer messageId, Integer userId) {
        UserMessage userMessage = get(messageId);

        if (userMessage == null) {
            return null;
        }

        if (userId.equals(userMessage.getUserId())) {
            UserMessage message = new UserMessage();
            message.setMessageId(messageId);
            message.setMessageIsRead(true);
            edit(message);
        } else {
            return null;
        }

        return userMessage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean setRead(Integer messageId, Integer userOtherId, Integer userId) {
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userId);

        if (CheckUtil.isNotEmpty(messageId)) {
            messageQueryWrapper.eq("message_id", messageId);
        }

        if (CheckUtil.isNotEmpty(userOtherId)) {
            messageQueryWrapper.eq("user_other_id", userOtherId);
        }

        UserMessage userMessage = new UserMessage();
        userMessage.setMessageIsRead(true);

        return edit(userMessage, messageQueryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserMessageRes addMessage(UserMessageAddInput messageAddInput, Integer userId) {
        // 判断接收者是否存在
        UserInfo userInfo = new UserInfo();
        String userOtherNickname = messageAddInput.getUserNickname();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(userOtherNickname)) {
            userInfoQueryWrapper.eq("user_nickname", userOtherNickname);
            userInfo = userInfoRepository.findOne(userInfoQueryWrapper);
        } else {
            Integer userOtherId = messageAddInput.getUserOtherId();

            if (userOtherId == null) {
                String to = messageAddInput.getTo();

                if (StrUtil.isNotEmpty(to)) {
                    JSONObject jsonObject = JSONUtil.parseObj(to);
                    userOtherId = Convert.toInt(jsonObject.get("friend_id"));

                    if (userOtherId == null) {
                        String name = Convert.toStr(jsonObject.get("name"));
                        userInfoQueryWrapper.eq("user_nickname", name);
                        userInfo = userInfoRepository.findOne(userInfoQueryWrapper);
                    }
                }
            } else {
                userInfo = userInfoRepository.get(userOtherId);
            }
        }
        String messageContent = messageAddInput.getMessageContent();

        if (StrUtil.isEmpty(messageContent)) {
            if (StrUtil.isNotEmpty(messageAddInput.getMine())) {
                JSONObject mineObj = JSONUtil.parseObj(messageAddInput.getMine());
                messageContent = Convert.toStr(mineObj.get("content"));
            }
        }

        UserMessageRes userMessageRes = new UserMessageRes();

        if (userInfo != null) {
            //用户信息
            UserInfo user = userInfoRepository.get(userId);

            if (user == null) {
                throw new BusinessException(__("用户详细信息不存在"));
            }
            Date now = new Date();
            //发件箱
            UserMessage send = new UserMessage();
            //message_kind=1则为当前用户发送的消息
            send.setUserId(user.getUserId());
            send.setUserNickname(user.getUserNickname());
            send.setUserOtherId(userInfo.getUserId());
            send.setUserOtherNickname(userInfo.getUserNickname());
            send.setMessageTitle(messageAddInput.getMessageTitle());
            send.setMessageContent(messageContent);
            send.setMessageLength(messageAddInput.getMessageLength());
            send.setMessageW(messageAddInput.getMessageW());
            send.setMessageH(messageAddInput.getMessageH());

            send.setMessageIsRead(true);
            send.setMessageIsDelete(false);
            send.setMessageType(2);
            send.setMessageKind(1);
            send.setMessageCat(StrUtil.isNotEmpty(messageAddInput.getMessageCat()) ? messageAddInput.getMessageCat() : "text");
            send.setMessageTime(now.getTime());

            //收件箱
            UserMessage inbox = new UserMessage();
            inbox.setUserId(userInfo.getUserId());
            inbox.setUserNickname(userInfo.getUserNickname());
            inbox.setUserOtherId(user.getUserId());
            inbox.setUserOtherNickname(user.getUserNickname());
            inbox.setMessageTitle(messageAddInput.getMessageTitle());
            inbox.setMessageContent(messageContent);
            inbox.setMessageLength(messageAddInput.getMessageLength());
            inbox.setMessageW(messageAddInput.getMessageW());
            inbox.setMessageH(messageAddInput.getMessageH());

            inbox.setMessageIsRead(false);
            inbox.setMessageIsDelete(false);
            inbox.setMessageType(2);
            inbox.setMessageKind(2);
            inbox.setMessageCat(StrUtil.isNotEmpty(messageAddInput.getMessageCat()) ? messageAddInput.getMessageCat() : "text");
            inbox.setMessageTime(now.getTime());

            if (!add(send)) {
                throw new BusinessException(__("保存发件箱失败"));
            }

            if (!add(inbox)) {
                throw new BusinessException(__("保存收件箱失败"));
            }
            userMessageRes.setMessageId(send.getMessageId());
            userMessageRes.setMessageOtherId(inbox.getMessageId());
        }

        return userMessageRes;
    }

    @Override
    public IPage<MessageRes> listChatMsg(UserMessageListReq userMessageListReq) {
        IPage<MessageRes> messageResPage = new Page<>();
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userMessageListReq.getUserId());
        messageQueryWrapper.eq("user_other_id", userMessageListReq.getUserOtherId());

        if (CheckUtil.isNotEmpty(userMessageListReq.getSourceType()) && !userMessageListReq.getSourceType().equals(2311)) {
            messageQueryWrapper.orderByAsc("message_time");
        } else {
            messageQueryWrapper.orderByDesc("message_time");
        }

        IPage<UserMessage> userMessageIPage = lists(messageQueryWrapper, userMessageListReq.getPage(), userMessageListReq.getSize());

        if (userMessageIPage != null) {
            BeanUtils.copyProperties(userMessageIPage, messageResPage);

            if (CollectionUtil.isNotEmpty(userMessageIPage.getRecords())) {
                List<UserMessage> userMessageList = userMessageIPage.getRecords();
                //用户头像
                Map<Integer, String> userMap = getUserAvatarMap(userMessageList);
                //相关用户头像
                Map<Integer, String> otherUserMap = getOtherUserAvatarMap(userMessageList);

                List<MessageRes> messageResList = new ArrayList<>();
                for (UserMessage item : userMessageList) {
                    MessageRes messageRes = new MessageRes();
                    messageRes.setType("user");
                    //站内信数据
                    UserMessageRes userMessageRes = new UserMessageRes();
                    userMessageRes.setMessageId(item.getMessageId());
                    userMessageRes.setMessageCat(item.getMessageCat());
                    //站内信userInfo
                    MessageUserInfoRes messageUserInfoRes = new MessageUserInfoRes();

                    if (item.getMessageKind().equals(1)) {
                        Integer userId = item.getUserId();
                        messageUserInfoRes.setUsername(item.getUserNickname());
                        messageUserInfoRes.setUid(getPlantFormUid(userId));

                        if (userMap != null && !userMap.isEmpty()) {
                            messageUserInfoRes.setFace(userMap.get(userId));
                        }
                    } else {
                        Integer otherUserId = item.getUserOtherId();
                        messageUserInfoRes.setUsername(item.getUserOtherNickname());
                        messageUserInfoRes.setUid(getPlantFormUid(otherUserId));

                        if (otherUserMap != null && !otherUserMap.isEmpty()) {
                            messageUserInfoRes.setFace(otherUserMap.get(otherUserId));
                        }
                    }

                    //站内信content
                    MessageContentRes messageContentRes = new MessageContentRes();

                    switch (item.getMessageCat()) {
                        case "text":
                        case "blessing":
                        case "order":
                            messageContentRes.setMessageContent(item.getMessageContent());
                            break;
                        case "img":
                            messageContentRes.setMessageContent(item.getMessageContent());
                            messageContentRes.setMessageW(item.getMessageW());
                            messageContentRes.setMessageH(item.getMessageH());
                            break;
                        case "voice":
                        case "video":
                            messageContentRes.setMessageContent(item.getMessageContent());
                            messageContentRes.setMessageLength(item.getMessageLength());
                            break;
                        case "item":
                            String messageContent = item.getMessageContent();

                            if (StrUtil.isNotEmpty(messageContent) && NumberUtil.isNumber(messageContent)) {
                                //读取商品信息
                                ProductItem productItem = productItemRepository.get(Convert.toLong(messageContent));

                                if (productItem != null) {
                                    messageContentRes.setItemUnitPrice(productItem.getItemUnitPrice());
                                    messageContentRes.setProductItemName(productItem.getItemName());
                                    messageContentRes.setItemId(productItem.getItemId());

                                    QueryWrapper<ProductImage> productImageQueryWrapper = new QueryWrapper<>();
                                    productImageQueryWrapper.eq("product_id", productItem.getProductId());
                                    ProductImage productImage = productImageRepository.findOne(productImageQueryWrapper);
                                    messageContentRes.setProductImage(productImage != null ? productImage.getItemImageDefault() : "");
                                    messageContentRes.setMessageContent(item.getMessageContent());
                                }
                            }
                            break;
                        default:
                            messageContentRes.setMessageContent(item.getMessageContent());
                            break;
                    }

                    userMessageRes.setUserinfo(messageUserInfoRes);
                    userMessageRes.setContent(messageContentRes);
                    messageRes.setMsg(userMessageRes);
                    messageResList.add(messageRes);
                }
                messageResPage.setRecords(messageResList);
            }
        }

        return messageResPage;
    }

    @Override
    public void sendSysNotice(Integer adminUserId, Integer userId, String msgContent, int msgType) {
        //收件箱
        UserMessage other = new UserMessage();

        other.setUserId(userId);
        other.setUserOtherId(adminUserId);
        other.setMessageContent(msgContent);

        other.setMessageIsRead(false);
        other.setMessageIsDelete(false);
        other.setMessageType(msgType);
        other.setMessageKind(2);
        other.setMessageTime(new Date().getTime());

        if (!add(other)) {
            throw new BusinessException(__("消息发送失败"));
        }
    }

    private Integer getPlantFormUid(Integer userId) {
        ConfigBase configBase = configBaseService.get("service_user_id");

        if (configBase != null) {
            String serviceUserId = configBase.getConfigValue();
            String puid = String.format("%s-%s", serviceUserId, userId);
            return bkdrHash(puid);
        }
        return null;
    }

    private Integer bkdrHash(String str) {
        int seed = 131;
        int hash = 0;
        for (int i = 0; i != str.length(); ++i) {
            hash = seed * hash + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    @Override
    public MessageNoticeRes getPlantFromNotice() {
        Integer userId = ContextUtil.checkLoginUserId();
        MessageNoticeRes noticeRes = new MessageNoticeRes();

        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userId);
        messageQueryWrapper.eq("message_is_read", 0);
        messageQueryWrapper.eq("message_type", 1);
        messageQueryWrapper.gt("message_time", DateUtil.offsetDay(new Date(), -10).getTime());

        List<UserMessage> userMessages = find(messageQueryWrapper);

        List<UserMessageRes> userMessageResList = BeanUtil.copyToList(userMessages, UserMessageRes.class);
        List<Integer> userIds = userMessageResList.stream().map(UserMessageRes::getUserId).distinct().collect(Collectors.toList());
        List<UserInfo> userInfos = userInfoRepository.gets(userIds);
        for (UserMessageRes messageRes : userMessageResList) {
            for (UserInfo userInfo : userInfos) {
                if (messageRes.getUserId().equals(userInfo.getUserId())) {
                    messageRes.setUserAvatar(userInfo.getUserAvatar());
                }
            }
        }

        noticeRes.setItems(userMessageResList);
        noticeRes.setTotal(userMessages.size());

        return noticeRes;
    }

    @Override
    public boolean clearNotice() {
        Integer userId = ContextUtil.checkLoginUserId();
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userId);
        messageQueryWrapper.eq("message_is_read", 0);
        messageQueryWrapper.eq("message_type", 1);

        UserMessage userMessage = new UserMessage();
        userMessage.setMessageIsRead(true);

        if (!edit(userMessage, messageQueryWrapper)) {
            throw new BusinessException(__("消息清空失败！"));
        }

        return true;
    }

    @Override
    public UserMessageRes getMessageNum(UserMessageListReq userMessageListReq) {
        UserMessageRes userMessageRes = new UserMessageRes();
        QueryWrapper<UserMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("user_id", userMessageListReq.getUserId());
        messageQueryWrapper.eq("message_kind", 2);
        QueryWrapper<UserMessage> queryWrapperOne = messageQueryWrapper.clone();
        QueryWrapper<UserMessage> queryWrapperTwo = messageQueryWrapper.clone();

        queryWrapperOne.eq("message_is_read", 0);
        //未读数
        long unread = count(queryWrapperOne);
        userMessageRes.setUnreadNumber((int) unread);
        //已读数
        queryWrapperTwo.eq("message_is_read", 1);
        long read = count(queryWrapperTwo);
        userMessageRes.setRedNumber((int) read);

        return userMessageRes;
    }

}
