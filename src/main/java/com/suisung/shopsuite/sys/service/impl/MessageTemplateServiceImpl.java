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
package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.account.model.entity.UserBindConnect;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.entity.UserLogin;
import com.suisung.shopsuite.account.repository.UserBindConnectRepository;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.account.repository.UserLoginRepository;
import com.suisung.shopsuite.account.service.UserMessageService;
import com.suisung.shopsuite.account.service.WechatService;
import com.suisung.shopsuite.common.consts.BindConnectCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.EmailDto;
import com.suisung.shopsuite.common.pojo.dto.ErrorTypeEnum;
import com.suisung.shopsuite.common.utils.EmailUtil;
import com.suisung.shopsuite.common.utils.GeTuiUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.WxHttpUtil;
import com.suisung.shopsuite.common.utils.phone.PhoneModel;
import com.suisung.shopsuite.common.utils.phone.PhoneNumberUtils;
import com.suisung.shopsuite.core.web.model.SmsDto;
import com.suisung.shopsuite.core.web.service.CloundService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.model.entity.MessageTemplate;
import com.suisung.shopsuite.sys.model.entity.WechatTplmsg;
import com.suisung.shopsuite.sys.model.req.MessageTemplateListReq;
import com.suisung.shopsuite.sys.model.vo.WxTelMsgPushVo;
import com.suisung.shopsuite.sys.repository.MessageTemplateRepository;
import com.suisung.shopsuite.sys.repository.WechatTplmsgRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 消息模板表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-29
 */
@Service
@Slf4j
public class MessageTemplateServiceImpl extends BaseServiceImpl<MessageTemplateRepository, MessageTemplate, MessageTemplateListReq> implements MessageTemplateService {

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserBindConnectRepository userBindConnectRepository;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatTplmsgRepository wechatTplmsgRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private GeTuiUtil geTuiUtil;

    @Autowired
    private CloundService cloundService;


    /**
     * 发送短信，邮件，站内信通知信息，微信模板消息 - 可以修改异步通知
     *
     * @param userId
     * @param messageTplId
     * @param args
     * @return
     */
    @Override
    public void send(Integer userId, String messageTplId, Map<String, Object> args) throws BusinessException {
        Integer adminUserId = configBaseService.getConfig("message_notice_user_id", 10001);
        args.put("site_name", configBaseService.getConfig("site_name", "ModulithShop"));
        args.put("date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        UserInfo userInfo = userInfoRepository.get(adminUserId);
        args.put("user_name", userInfo.getUserNickname());

        String linkUrl = Optional.ofNullable(Convert.toStr(args.get("link_url"))).orElse("");
        args.put("link_url", linkUrl);

        Map<String, Object> userSetting = new HashMap<>();

        QueryWrapper<UserBindConnect> connectQueryWrapper = new QueryWrapper<>();
        connectQueryWrapper.eq("user_id", userId).eq("bind_type", BindConnectCode.MOBILE);
        UserBindConnect bindConnect = userBindConnectRepository.findOne(connectQueryWrapper);
        if (bindConnect != null) {
            userSetting.put("mobile", bindConnect.getBindId());
        }

        connectQueryWrapper.clear();
        connectQueryWrapper.eq("user_id", userId).eq("bind_type", BindConnectCode.EMAIL);
        bindConnect = userBindConnectRepository.findOne(connectQueryWrapper);

        if (bindConnect != null) {
            userSetting.put("email", bindConnect.getBindId());
        }

        connectQueryWrapper.clear();
        connectQueryWrapper.eq("user_id", userId).eq("bind_type", BindConnectCode.WEIXIN);
        bindConnect = userBindConnectRepository.findOne(connectQueryWrapper);

        if (bindConnect != null) {
            userSetting.put("wechat", bindConnect.getBindId());
            userSetting.put("bind_openid", bindConnect.getBindOpenid());
        }

        MessageTemplate template = get(messageTplId);
        if (template != null) {
            // 站内信
            if (template.getMessageEnable()) {
                String msgContent = dealMessageTemplate(template.getMessageContent(), args);
                userMessageService.sendSysNotice(adminUserId, userId, msgContent, 1);
            }

            // 发送邮件
            if (template.getMessageEmailEnable() && CollUtil.isNotEmpty(userSetting)) {
                String email = Convert.toStr(userSetting.get("email"));
                if (StrUtil.isNotEmpty(email)) {
                    String messageEmailTitle = dealMessageTemplate(template.getMessageEmailTitle(), args);
                    String messageEmailContent = dealMessageTemplate(template.getMessageEmailContent(), args);

                    // 获取emailConfig 对象
                    QueryWrapper<ConfigBase> configBaseQueryWrapper = new QueryWrapper<>();
                    configBaseQueryWrapper.eq("config_type", "email");
                    List<ConfigBase> configBases = configBaseService.find(configBaseQueryWrapper);
                    Map<String, String> configMap = configBases.stream().collect(Collectors.toMap(ConfigBase::getConfigKey, ConfigBase::getConfigValue, (k1, k2) -> k2));
                    EmailDto emailDto = Convert.convert(EmailDto.class, configMap);
                    emailDto.setEmailToAddress(email);
                    emailDto.setSubject(messageEmailTitle);
                    emailDto.setContent(messageEmailContent);
                    try {
                        EmailUtil.send(emailDto);
                    } catch (Exception e) {
                        LogUtil.error(ErrorTypeEnum.ERR_PSUH_MSG.getValue(), e);
                    }
                }
            }

            // app推送
            if (template.getMessageAppEnable() && CollUtil.isNotEmpty(userSetting)) {
                String package_name = configBaseService.getConfig("unipush_packagename", "");//包名
                if (StrUtil.isEmpty(package_name)) {
                    throw new BusinessException(__("无效的App包名"));
                }
                String messageAppTitle = dealMessageTemplate(template.getMessageEmailTitle(), args);
                String messageApp = dealMessageTemplate(template.getMessageApp(), args);

                UserLogin userLogin = userLoginRepository.get(userId);
                if (userLogin != null && StrUtil.isNotEmpty(userLogin.getUserClientid())) {
                    try {
                        if (package_name.isEmpty()) {
                            throw new Exception(__("无效的App包名"));
                        }

                        //调用推送
                        List<String> cidList = Convert.toList(String.class, userLogin.getUserClientid());

                        for (String cid : cidList) {
                            String title = messageAppTitle;
                            String content = messageApp;
                            String payload = "";

                            if (cid.isEmpty()) {
                                throw new Exception(__("无效的终端标识（cid）"));
                            } else if (title.isEmpty()) {
                                throw new Exception(__("无效的通知标题（title）"));
                            } else if (content.isEmpty()) {
                                throw new Exception(__("无效的通知内容（content）"));
                            }

                            geTuiUtil.pushToSingleByCid(cid, title, content, package_name, payload);
                        }
                    } catch (Exception e) {
                        LogUtil.error(ErrorTypeEnum.ERR_PSUH_MSG.getValue(), e);
                    }
                }
            }

            // 微信模板消息
            if (template.getMessageWechatEnable() && CollUtil.isNotEmpty(userSetting)) {
                String openId = Convert.toStr(userSetting.get("bind_openid"));
                //从bind表中读取用户公众号openid
                if (StrUtil.isNotEmpty(openId)) {
                    String accessToken = wechatService.getMpAccessToken(true);
                    String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
                    QueryWrapper<WechatTplmsg> tplmsgQueryWrapper = new QueryWrapper<>();
                    tplmsgQueryWrapper.eq("message_id", messageTplId);
                    tplmsgQueryWrapper.eq("tplmsg_enable", 1);
                    WechatTplmsg wechatTplmsg = wechatTplmsgRepository.findOne(tplmsgQueryWrapper);
                    if (wechatTplmsg != null) {
                        String wechatTplData = getWechatTplData(openId, wechatTplmsg, args);
                        String result = WxHttpUtil.request(WxHttpUtil.MethodType.POST, WxHttpUtil.WxType.MP, accessToken, url, null, wechatTplData);
                        JSONObject resultJson = JSONUtil.parseObj(result);
                        Integer errcode = Convert.toInt(resultJson.get("errcode"));
                        String errmsg = Convert.toStr(resultJson.get("errmsg"));
                        if (errcode != 0) {
                            LogUtil.error(ErrorTypeEnum.ERR_PSUH_MSG.getValue(), errmsg);
                        }
                    }
                }
            }

            // 短信通知
            String[] activeProfiles = environment.getActiveProfiles();
            String activeProfile = activeProfiles[0];
            // 生产环境才允许短信发送
            if (template.getMessageSmsEnable() && !userSetting.isEmpty() && !activeProfile.equals("prod")) {

                String mobile = (String) userSetting.get("mobile");
                if (StrUtil.isNotEmpty(mobile)) {
                    String messageSms = dealMessageTemplate(template.getMessageSms(), args);

                    String configBaseUserId = configBaseService.getConfig("service_user_id", "");
                    String configBaseAppKey = configBaseService.getConfig("service_app_key", "");
                    PhoneModel phoneModelWithCountry = PhoneNumberUtils.getPhoneModelWithCountry(mobile);

                    SmsDto smsDto = new SmsDto();
                    smsDto.setMobile(Convert.toStr(phoneModelWithCountry.getNationalNumber()));
                    smsDto.setServiceUserId(configBaseUserId);
                    smsDto.setServiceAppKey(configBaseAppKey);
                    smsDto.setContent(messageSms);

                    try {
                        cloundService.send(smsDto);
                    } catch (Exception e) {
                        LogUtil.error(ErrorTypeEnum.ERR_PSUH_MSG.getValue(), e);
                    }
                }
            }
        }
    }

    /**
     * 消息格式处理
     *
     * @param msgContent
     * @param args
     * @return
     */
    public String dealMessageTemplate(String msgContent, Map<String, Object> args) {
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            String variable = "[" + entry.getKey() + "]";
            Object value = entry.getValue();
            msgContent = msgContent.replace(variable, value.toString());
        }
        return msgContent;
    }

    /**
     * 获取微信模板数据
     *
     * @param openId
     * @param wechatTplmsg
     * @param args
     * @return
     */
    public String getWechatTplData(String openId, WechatTplmsg wechatTplmsg, Map args) {
        String tplmsg_number = wechatTplmsg.getTplmsgNumber();
        WxTelMsgPushVo wxTelMsgPushVo = new WxTelMsgPushVo();
        wxTelMsgPushVo.setTouser(openId);
        wxTelMsgPushVo.setTemplate_id(wechatTplmsg.getTplmsgTplId());
        wxTelMsgPushVo.setUrl(Convert.toStr(args.get("link_url")));

        // 通用信息
        WxTelMsgPushVo.BaseValue BaseValue = new WxTelMsgPushVo.BaseValue();
        WxTelMsgPushVo.DetailValue DetailValue = new WxTelMsgPushVo.DetailValue();
        DetailValue.setValue(wechatTplmsg.getTplmsgTitle());
        BaseValue.setFirst(DetailValue);

        WxTelMsgPushVo.DetailValue keyword1 = new WxTelMsgPushVo.DetailValue();
        WxTelMsgPushVo.DetailValue keyword2 = new WxTelMsgPushVo.DetailValue();
        WxTelMsgPushVo.DetailValue keyword3 = new WxTelMsgPushVo.DetailValue();
        WxTelMsgPushVo.DetailValue keyword4 = new WxTelMsgPushVo.DetailValue();

        WxTelMsgPushVo.DetailValue remark = new WxTelMsgPushVo.DetailValue();
        // 平台提醒
        remark.setValue(wechatTplmsg.getTplmsgRemark());
        BaseValue.setRemark(remark);

        switch (tplmsg_number) {
            // 会员注册
            case "OPENTM416037452": {
                // 用户名
                keyword1.setValue(Convert.toStr(args.get("user_account")));
                BaseValue.setKeyword1(keyword1);
                // 注册时间
                keyword2.setValue(Convert.toStr(args.get("register_time")));
                BaseValue.setKeyword2(keyword2);
                // 用户状态
                keyword3.setValue(Convert.toStr("未认证"));
                BaseValue.setKeyword3(keyword3);
                break;
            }
            // 手机绑定通知
            case "OPENTM207372650": {
                // 账号
                keyword1.setValue("");
                BaseValue.setKeyword1(keyword1);
                // 注册时间
                keyword2.setValue("");
                BaseValue.setKeyword2(keyword2);
                break;
            }
            // 验证码通知
            case "OPENTM411737351": {
                // 验证码
                keyword1.setValue("");
                BaseValue.setKeyword1(keyword1);
                // 通知时间
                keyword2.setValue(DateUtil.now());
                BaseValue.setKeyword2(keyword2);
                break;
            }
            // 付款-下单成功通知
            case "OPENTM406087303": {
                // 单号
                keyword1.setValue(Convert.toStr(args.get("order_id")));
                BaseValue.setKeyword1(keyword1);
                // 商品
                keyword2.setValue(Convert.toStr(args.get("product_name")));
                BaseValue.setKeyword2(keyword2);
                // 金额
                keyword3.setValue(Convert.toStr(args.get("order_payment_amount")));
                BaseValue.setKeyword3(keyword3);
                // 时间
                keyword4.setValue(Convert.toStr(args.get("order_add_time")));
                BaseValue.setKeyword4(keyword4);
                break;
            }
            // 订单-支付成功 （ 和下单成功通知一样，选其一）
            case "OPENTM207498902": {
                // 用户名
                keyword1.setValue("");
                BaseValue.setKeyword1(keyword1);
                // 订单号
                keyword2.setValue("");
                BaseValue.setKeyword2(keyword2);
                // 订单金额
                keyword3.setValue("");
                BaseValue.setKeyword3(keyword3);
                // 商品信息
                keyword4.setValue("");
                BaseValue.setKeyword2(keyword4);
                break;
            }
            // 订单发货通知
            case "OPENTM201541214": {
                // 订单编号
                keyword1.setValue(Convert.toStr(args.get("order_id")));
                BaseValue.setKeyword1(keyword1);
                // 物流公司
                keyword2.setValue(Convert.toStr(args.get("logistics_name")));
                BaseValue.setKeyword2(keyword2);
                // 物流单号
                keyword3.setValue(Convert.toStr(args.get("order_tracking_number")));
                BaseValue.setKeyword3(keyword3);
                break;
            }
            // 自提订单通知
            case "OPENTM201594720": {
                // 自提码
                keyword1.setValue(Convert.toStr(args.get("chain_code")));
                BaseValue.setKeyword1(keyword1);
                // 商品详情
                keyword2.setValue(Convert.toStr(args.get("product_name")));
                BaseValue.setKeyword2(keyword2);
                // 提货地址
                keyword3.setValue(Convert.toStr(args.get("chain_address")));
                BaseValue.setKeyword3(keyword3);
                // 提货时间
                keyword4.setValue(Convert.toStr(args.get("chain_date")));
                BaseValue.setKeyword4(keyword4);
                break;
            }
            // 订单完成通知
            case "OPENTM202521011": {
                // 订单号
                keyword1.setValue(Convert.toStr(args.get("order_id")));
                BaseValue.setKeyword1(keyword1);
                // 完成时间
                keyword2.setValue("");
                BaseValue.setKeyword2(keyword2);
                break;
            }
            // 资产-余额变动通知
            case "OPENTM207679800": {
                // 变动时间
                keyword1.setValue(Convert.toStr(args.get("change_time")));
                BaseValue.setKeyword1(keyword1);
                // 变动金额
                keyword2.setValue(Convert.toStr(args.get("user_money")));
                BaseValue.setKeyword2(keyword2);
                // 账户余额
                keyword3.setValue("*****");
                BaseValue.setKeyword3(keyword3);
                break;
            }
            // 店铺商品审核失败通知
            case "OPENTM202113860": {
                // 商品名称
                keyword1.setValue("");
                BaseValue.setKeyword1(keyword1);
                // 失败原因
                keyword2.setValue("");
                BaseValue.setKeyword2(keyword2);
                break;
            }
            // 商品库存不足通知
            case "OPENTM401800206": {
                // 店铺名称
                keyword1.setValue(Convert.toStr(args.get("store_name")));
                BaseValue.setKeyword1(keyword1);
                // 商品名称
                keyword2.setValue(Convert.toStr(args.get("product_item_name")));
                BaseValue.setKeyword2(keyword2);
                // 库存数量
                keyword3.setValue(Convert.toStr(args.get("item_quantity")));
                BaseValue.setKeyword3(keyword3);
                break;
            }
            // 商品违规下架通知
            case "OPENTM204398863": {
                // 商品名称
                keyword1.setValue(Convert.toStr(args.get("product_name")));
                BaseValue.setKeyword1(keyword1);
                // 下架时间
                keyword2.setValue(DateUtil.now());
                BaseValue.setKeyword2(keyword2);
                // 下架原因
                keyword3.setValue(Convert.toStr(args.get("des")));
                BaseValue.setKeyword3(keyword3);
                break;
            }
            // 商品删除通知
            case "OPENTM400808168": {
                // 删除原因
                keyword1.setValue(Convert.toStr(args.get("des")));
                BaseValue.setKeyword1(keyword1);
                // 删除时间
                keyword2.setValue(DateUtil.now());
                BaseValue.setKeyword2(keyword2);
                break;
            }
            // 订单退货申请通知
            case "OPENTM411365483": {
                // 退货单号
                keyword1.setValue(Convert.toStr(args.get("return_id")));
                BaseValue.setKeyword1(keyword1);
                // 关联订单号
                keyword2.setValue(Convert.toStr(args.get("id")));
                BaseValue.setKeyword2(keyword2);
                // 商品信息
                keyword3.setValue(Convert.toStr(args.get("order_item")));
                BaseValue.setKeyword3(keyword3);
                // 申请时间
                keyword4.setValue(DateUtil.now());
                BaseValue.setKeyword4(keyword4);
                break;
            }
            // 订单退款退货成功通知
            case "OPENTM206905995": {
                // 退货订单号
                keyword1.setValue(Convert.toStr(args.get("order_id")));
                BaseValue.setKeyword1(keyword1);
                // 退货商品
                keyword2.setValue("**");
                BaseValue.setKeyword2(keyword2);
                // 退货数量
                keyword3.setValue("**");
                BaseValue.setKeyword3(keyword3);
                // 退货金额
                keyword4.setValue(Convert.toStr(args.get("return_refund_amount")));
                BaseValue.setKeyword4(keyword4);
                break;
            }
            // 账号解除绑定
            case "OPENTM407327732": {
                // 账号
                keyword1.setValue(Convert.toStr(args.get("")));
                BaseValue.setKeyword1(keyword1);
                // 状态
                keyword2.setValue("");
                BaseValue.setKeyword2(keyword2);
                break;
            }
            // 优惠券领取到账通知
            case "OPENTM206433586": {
                // 会员卡名称
                keyword1.setValue(Convert.toStr(args.get("name")));
                BaseValue.setKeyword1(keyword1);
                // 会员卡号
                keyword2.setValue("**");
                BaseValue.setKeyword2(keyword2);
                // 领取时间
                keyword3.setValue(DateUtil.now());
                BaseValue.setKeyword3(keyword3);
                break;
            }
        }
        wxTelMsgPushVo.setData(BaseValue);
        return JSON.toJSONString(wxTelMsgPushVo);
    }

}
