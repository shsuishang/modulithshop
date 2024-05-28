package com.suisung.shopsuite.sys.service;

import com.suisung.shopsuite.common.pojo.dto.SmsDto;
import com.suisung.shopsuite.common.pojo.dto.UploadDto;

/**
 * 第三方服务
 */
public interface ThirdService {

    void send(SmsDto smsDto);

    String upload(UploadDto uploadDto);

}
