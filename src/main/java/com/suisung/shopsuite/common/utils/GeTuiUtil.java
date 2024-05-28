package com.suisung.shopsuite.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.AudienceDTO;
import com.getui.push.v2.sdk.dto.req.Settings;
import com.getui.push.v2.sdk.dto.req.Strategy;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Slf4j
@Component
public class GeTuiUtil {

    /**
     * 消息推送（离线推送）单cid推送
     *
     * @param cid
     * @param title
     * @param content
     */
    public void pushToSingleByCid(String cid, String title, String content, String package_name, String payload) {
        ApiHelper apiHelper = SpringUtil.getBean(ApiHelper.class);
        PushDTO<Audience> pushDTO = this.buildPushDTO(title, content, package_name, payload);
        // 设置接收人信息
        Audience audience = new Audience();
        pushDTO.setAudience(audience);
        audience.addCid(cid);// cid
        // 进行cid单推
        PushApi pushApi = apiHelper.creatApi(PushApi.class);
        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByCid(pushDTO);
        if (!apiResult.isSuccess()) {
            log.error("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
        }
    }

    /**
     * cid批量推
     *
     * @param cidList
     * @param title
     * @param content
     */
    public void pushListByCid(List<String> cidList, String title, String content, String package_name, String payload) {
        ApiHelper apiHelper = SpringUtil.getBean(ApiHelper.class);
        //批量发送
        AudienceDTO audienceDTO = new AudienceDTO();
        PushDTO<Audience> pushDTO = this.buildPushDTO(title, content, package_name, payload);
        PushApi pushApi = apiHelper.creatApi(PushApi.class);
        ApiResult<TaskIdDTO> createApiResult = pushApi.createMsg(pushDTO);
        if (!createApiResult.isSuccess()) {
            log.error(__("推送：创建消息失败") + createApiResult.getMsg());
        }
        // 设置接收人信息
        Audience audience = new Audience();
        pushDTO.setAudience(audience);
        audience.setCid(cidList);
        audienceDTO.setAudience(audience);
        audienceDTO.setTaskid(createApiResult.getData().getTaskId());
        audienceDTO.setAsync(true);
        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushListByCid(audienceDTO);
        if (!apiResult.isSuccess()) {
            log.error("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
        }
    }

    private PushDTO<Audience> buildPushDTO(String title, String content, String package_name, String payload) {
        PushDTO<Audience> pushDTO = new PushDTO<>();
        // 设置推送参数
        //requestid需要每次变化唯一
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        pushDTO.setGroupName("wxb-group");

        //配置推送条件
        // 1: 表示该消息在用户在线时推送个推通道，用户离线时推送厂商通道;
        // 2: 表示该消息只通过厂商通道策略下发，不考虑用户是否在线;
        // 3: 表示该消息只通过个推通道下发，不考虑用户是否在线；
        // 4: 表示该消息优先从厂商通道下发，若消息内容在厂商通道代发失败后会从个推通道下发。
        Strategy strategy = new Strategy();
        strategy.setDef(1);
        strategy.setSt(1);
        Settings settings = new Settings();
        settings.setStrategy(strategy);
        pushDTO.setSettings(settings);

        //消息有效期，走厂商消息需要设置该值
        settings.setTtl(3600000);

        //推送苹果离线通知标题内容
        Alert alert = new Alert();

        //苹果离线通知栏标题
        alert.setTitle(title);

        //苹果离线通知栏内容
        alert.setBody(content);
        Aps aps = new Aps();

        //1表示静默推送(无通知栏消息)，静默推送时不需要填写其他参数。
        //苹果建议1小时最多推送3条静默消息
        aps.setContentAvailable(0);
        aps.setSound("default");
        aps.setAlert(alert);
        IosDTO iosDTO = new IosDTO();
        iosDTO.setAps(aps);
        iosDTO.setType("notify");
        PushChannel pushChannel = new PushChannel();
        pushChannel.setIos(iosDTO);

        //安卓离线厂商通道推送消息体
        AndroidDTO androidDTO = new AndroidDTO();
        Ups ups = new Ups();
        ThirdNotification notification1 = new ThirdNotification();
        ups.setNotification(notification1);
        //安卓离线展示的标题
        notification1.setTitle(title);
        //安卓离线展示的内容
        notification1.setBody(content);
        notification1.setClickType("intent");
        notification1.setIntent("intent:#Intent;action=android.intent.action.oppopush;launchFlags=0x14000000;component=" + package_name + "/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title=" + title + ";S.content=" + content + ";S.payload=" + payload + ";end");

        //各厂商自有功能单项设置
        //ups.addOption("HW", "/message/android/notification/badge/class", "io.dcloud.PandoraEntry ");
        //ups.addOption("HW", "/message/android/notification/badge/add_num", 1);
        //ups.addOption("HW", "/message/android/notification/importance", "HIGH");
        //ups.addOption("VV","classification",1);
        androidDTO.setUps(ups);
        pushChannel.setAndroid(androidDTO);
        pushDTO.setPushChannel(pushChannel);
        // PushMessage在线走个推通道才会起作用的消息体
        PushMessage pushMessage = new PushMessage();
        pushDTO.setPushMessage(pushMessage);
        pushMessage.setTransmission(" {title:\"" + title + "\",content:\"" + content + "\",payload:\"" + payload + "\"}");
        return pushDTO;
    }
}