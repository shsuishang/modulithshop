package com.suisung.shopsuite.sys.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface OssService {

    /**
     * oss文件上传
     *
     * @param file
     * @param inputStream
     * @param materialType
     * @return
     */
    String ossUploadObject(MultipartFile file, InputStream inputStream, String materialType);
}
