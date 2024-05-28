package com.suisung.shopsuite.sys.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Service
public class OssServiceImpl implements OssService {
    private static final Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);

    @Autowired
    private ConfigBaseService configBaseService;

    //oss上传文件
    @Override
    public String ossUploadObject(MultipartFile file, InputStream inputStream, String materialType) {
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

        // 获取文件名全名（带后缀）
        String fileName = file.getOriginalFilename();
        if (StrUtil.isEmpty(fileName)) {
            throw new BusinessException(__("获取文件名全名失败"));
        }

        // 根据用户获取目录名
        String dir = getUserDirName(user) + DateUtil.format(new Date(), "yyyyMMdd") + "/";
        // 创建唯一文件名称
        String uploadName = IdUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf("."));
        // 创建生成文件名称
        String folder = defaultDir.concat("/").concat(materialType).concat("/").concat(dir).concat(uploadName);

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
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            ossClient.putObject(bucket, folder, inputStream, metadata);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.UPLOAD, e);
            throw new BusinessException(__("获取用户信息失败") + e.getMessage());
        }
        return "https://".concat(bucket).concat(".").concat(endpoint).concat("/").concat(folder);
    }


    //根据用户获取目录名
    private String getUserDirName(ContextUser user) {
        if (user == null) {
            return "plantform/";
        }

        if (user.getUserId() == null) {
            throw new BusinessException(__("获取用户信息失败"));
        }

        if (user.isPlatform()) {
            return "media/plantform/";
        } else {
            if (user.isStore()) {
                return String.format("media/store/%d/", user.getUserId());
            } else {
                return String.format("media/user/%d/", user.getUserId());
            }
        }
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName
     * @return
     */
    public String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        switch (fileExtension) {
            case ".bmp":
                return "image/bmp";
            case ".gif":
                return "image/gif";
            case ".jpeg":
            case ".jpg":
            case ".png":
                return "image/jpeg";
            case ".html":
                return "text/html";
            case ".txt":
                return "text/plain";
            case ".vsd":
                return "application/vnd.visio";
            case ".ppt":
            case ".pptx":
                return "application/vnd.ms-powerpoint";
            case ".doc":
            case ".docx":
                return "application/msword";
            case ".xml":
                return "text/xml";
            case ".mp4":
                return "video/mp4";
            case ".awf":
                return "application/vnd.adobe.workflow";
            case ".wav":
                return "audio/wav";
            case ".zip":
                return "application/zip";
            case ".pdf":
                return "application/pdf";
            case ".ogg":
                return "application/ogg";
            case ".js":
                return "application/javascript";
            default:
                return "multipart/form-data";
        }
    }
}
