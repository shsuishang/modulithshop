package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.github.qcloudsms.httpclient.HTTPResponse;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
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

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Service
public class TencentServiceImpl implements ThirdService {

    @Autowired
    private ConfigBaseService configBaseService;

    @Override
    public void send(SmsDto smsDto) {
        // 短信应用SDK AppID  1400开头
        Integer tengxunAppId = Convert.toInt(configBaseService.getConfig("tengxun_sms_app_id"));
        // 短信应用SDK AppKey
        String tengxunAppkey = configBaseService.getConfig("tengxun_sms_app_key");
        // 短信模板ID，需要在短信应用中申请
        Integer tengxunTemplateId = smsDto.getTengxunTemplateId();
        // 签名，使用的是签名内容，而不是签名ID
        String smsSign = configBaseService.getConfig("sms_sign");
        //参数，一定要对应短信模板中的参数顺序和个数，
        String[] params = {smsDto.getContent()};
        //创建ssender对象
        SmsSingleSender ssender = new SmsSingleSender(tengxunAppId, tengxunAppkey);
        //发送
        SmsSingleSenderResult result;
        try {
            result = ssender.sendWithParam("86", smsDto.getMobile(), tengxunTemplateId, params, smsSign, "", "");
            HTTPResponse response = result.getResponse();
            String body = response.body;
            JSONObject res = JSONUtil.parseObj(body);
            Integer resultCode = Convert.toInt(res.get("result"));
            // todo 成功状态码不确定是多少，待配置成功后再试
            if (resultCode != 0) {
                String errmsg = Convert.toStr(res.get("errmsg"));
                LogUtil.error(ErrorTypeEnum.ERR_TENCENT_SERVICE.getValue(), errmsg);
                throw new BusinessException(errmsg);
            }

        } catch (HTTPException | IOException e) {
            LogUtil.error(ErrorTypeEnum.ERR_TENCENT_SERVICE.getValue(), e);
        }
    }

    public COSClient initCOSClient() {
        String tencentSecretId = configBaseService.getConfig("tengxun_app_id");
        String tencentSecretKey = configBaseService.getConfig("tengxun_secret_key");
        String tencentRegion = configBaseService.getConfig("tengxun_region_region");
        COSCredentials cred = new BasicCOSCredentials(tencentSecretId, tencentSecretKey);
        Region region = new Region(tencentRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    @Override
    public String upload(UploadDto uploadDto) {
        try {
            ContextUser user = ContextUtil.getLoginUser();

            if (user == null) {
                //throw new BusinessException(ResultCode.NEED_LOGIN);
            }
            // 指定要上传到 COS 上对象键
            //defaultDir
            String defaultDir = configBaseService.getConfig("tengxun_default_dir", "modulithshop");

            String tencentBucketName = configBaseService.getConfig("tencent_bucket_name");

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

            PutObjectRequest putObjectRequest = new PutObjectRequest(tencentBucketName, folder, uploadDto.getFile());
            COSClient cosClient1 = initCOSClient();
            PutObjectResult putObjectResult = cosClient1.putObject(putObjectRequest);
            // 设置权限(公开读)
            cosClient1.setBucketAcl(tencentBucketName, CannedAccessControlList.PublicRead);
            return putObjectResult.getDateStr();
        } catch (CosClientException e) {
            LogUtil.error(ErrorTypeEnum.ERR_TENCENT_SERVICE.getValue(), e);
        }
        return null;
    }

}
