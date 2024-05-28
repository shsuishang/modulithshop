package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import com.obs.services.ObsClient;
import com.qcloud.cos.exception.CosClientException;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.ErrorTypeEnum;
import com.suisung.shopsuite.common.pojo.dto.SmsDto;
import com.suisung.shopsuite.common.pojo.dto.UploadDto;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.ThirdUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.ThirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Service
public class HuaweiServiceimpl implements ThirdService {

    @Autowired
    private ConfigBaseService configBaseService;

    @Override
    public void send(SmsDto smsDto) {
        String huaweiSmsKey = configBaseService.getConfig("huawei_sms_access_key");
        String huaweiSmsKeySecret = configBaseService.getConfig("huawei_sms_access_secret");
        // 初始化
        Uni.init(huaweiSmsKey, huaweiSmsKeySecret);
        // 若使用简易验签模式仅传入第一个参数即可
        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<>();
        templateData.put("code", smsDto.getContent());
        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(smsDto.getMobile())
                .setSignature("UniSMS")
                .setTemplateId("login_tmpl")
                .setTemplateData(templateData);
        // 发送短信
        try {
            UniResponse res = message.send();
            // todo
        } catch (UniException e) {
            LogUtil.error(ErrorTypeEnum.ERR_HUAWEI_SERVICE.getValue(), e);
        }
    }

    @Override
    public String upload(UploadDto uploadDto) {
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            //throw new BusinessException(ResultCode.NEED_LOGIN);
        }
        // 指定要上传到 COS 上对象键
        //defaultDir
        String defaultDir = configBaseService.getConfig("huawei_default_dir", "modulithshop");

        if (user == null) {
            defaultDir = defaultDir.concat("/").concat("guest");
        }
        MultipartFile file = uploadDto.getMultipartfile();
        String fileName = file.getOriginalFilename();
        if (StrUtil.isEmpty(fileName)) {
            throw new BusinessException(__("获取文件名全名失败"));
        }

        String dir = ThirdUtil.getUserDirName(user) + DateUtil.format(new Date(), "yyyyMMdd") + "/";
        // 创建唯一文件名称
        String uploadName = IdUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf("."));
        // 创建生成文件名称
        String folder = defaultDir.concat("/").concat(uploadDto.getMaterialType()).concat("/").concat(dir).concat(uploadName);
        ObsClient obsClient = createTransferManager();
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            String bucketname = configBaseService.getConfig("huawei_bucketname");
            obsClient.putObject(bucketname, folder, uploadDto.getFile());
            String huaweiBucket = configBaseService.getConfig("huawei_bucket");
            return huaweiBucket + fileName;
        } catch (CosClientException e) {
            LogUtil.error(ErrorTypeEnum.ERR_HUAWEI_SERVICE.getValue(), e);
        } finally {
            shutdownTransferManager(obsClient);
        }

        return null;
    }

    ObsClient createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        String ak = configBaseService.getConfig("huawei_access_key");
        String sk = configBaseService.getConfig("huawei_secret_key");
        String endPoint = configBaseService.getConfig("huawei_endpoint");
        ObsClient obsClient = null;
        try {
            obsClient = new ObsClient(ak, sk, endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obsClient;
    }

    void shutdownTransferManager(ObsClient obsClient) {
        try {
            obsClient.close();
        } catch (IOException e) {
            LogUtil.error(ErrorTypeEnum.ERR_HUAWEI_SERVICE.getValue(), e);
        }
    }
}
