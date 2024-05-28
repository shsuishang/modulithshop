package com.suisung.shopsuite.account.service;

import java.util.Map;

/**
 * <p>
 * 小程序二维码 服务类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-18
 */
public interface WxQrCodeService {

    String getAccessToken();

    Map getUnlimited(String preparedUrl, Map param);

}
