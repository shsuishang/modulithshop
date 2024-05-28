package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
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

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Service
public class AliServiceImpl implements ThirdService {

    @Autowired
    private ConfigBaseService configBaseService;

    @Override
    public void send(SmsDto smsDto) {
        String aliyunSmsAccessKeyId = configBaseService.getConfig("aliyun_sms_access_key_id");
        String aliyunSmsAccessKeySecret = configBaseService.getConfig("aliyun_sms_access_key_secret");
        String aliyunSmsRegionId = configBaseService.getConfig("aliyun_sms_region_id");
        String smsSign = configBaseService.getConfig("sms_sign");

        DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsRegionId, aliyunSmsAccessKeyId, aliyunSmsAccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(smsDto.getMobile());
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(smsSign);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsDto.getTemplateCode());
        // 选填：设置模板参数，多个参数用逗号隔开
        Map<String, Object> paramMap = smsDto.getParamMap();
        if (CollUtil.isNotEmpty(paramMap)) {
            String str = paramMap.toString();
            str = str.replaceAll("=", ":");
            request.setTemplateParam(str);
        }
        // 获取发送结果
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            if (!response.getCode().equals("OK")) {
                String errMsg = response.getMessage();
                LogUtil.error(ErrorTypeEnum.ERR_ALI_SERVICE.getValue(), errMsg);
                throw new BusinessException(errMsg);
            }
        } catch (ClientException e) {
            LogUtil.error(ErrorTypeEnum.ERR_ALI_SERVICE.getValue(), e);
        }
    }

    @Override
    public String upload(UploadDto uploadDto) {
        ContextUser user = ContextUtil.getLoginUser();

        if (user == null) {
            //throw new BusinessException(ResultCode.NEED_LOGIN);
        }

        //aliyun_endpoint
        String endpoint = configBaseService.getConfig("aliyun_endpoint", "");

        //accessKeyId
        String accessKeyId = configBaseService.getConfig("aliyun_access_key_id", "");

        //accessKeySecret
        String accessKeySecret = configBaseService.getConfig("aliyun_access_key_secret", "");

        //bucket
        String bucket = configBaseService.getConfig("aliyun_bucket", "");

        //defaultDir
        String defaultDir = configBaseService.getConfig("aliyun_default_dir", "modulithshop");

        if (user == null) {
            defaultDir = defaultDir.concat("/").concat("guest");
        }

        MultipartFile file = uploadDto.getMultipartfile();
        InputStream inputStream = uploadDto.getInputStream();
        // 获取文件名全名（带后缀）
        String fileName = file.getOriginalFilename();
        if (StrUtil.isEmpty(fileName)) {
            throw new BusinessException(__("获取文件名全名失败"));
        }

        // 根据用户获取目录名
        String dir = ThirdUtil.getUserDirName(user) + DateUtil.format(new Date(), "yyyyMMdd") + "/";
        // 创建唯一文件名称
        String uploadName = IdUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf("."));
        // 创建生成文件名称
        String folder = defaultDir.concat("/").concat(uploadDto.getMaterialType()).concat("/").concat(dir).concat(uploadName);

        try {
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            //以输入流的形式上传文件
            //文件大小
            Long fileSize = file.getSize();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(inputStream.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(ThirdUtil.getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            ossClient.putObject(bucket, folder, inputStream, metadata);
        } catch (Exception e) {
            LogUtil.error(ErrorTypeEnum.ERR_ALI_SERVICE.getValue(), e);
            throw new BusinessException(__("获取用户信息失败") + e.getMessage());
        }
        return "https://".concat(bucket).concat(".").concat(endpoint).concat("/").concat(folder);
    }
}
