package com.suisung.shopsuite.common.config;

import com.alipay.api.AlipayApiException;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.pay.model.vo.AliPayVo;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * 支付配置类
 */
@Configuration
@Slf4j
public class PayConfig {

    @Autowired
    private ConfigBaseService configBaseService;

    /**
     * 支付宝配置
     *
     * @return
     * @throws AlipayApiException
     */
    @Bean
    public AliPayApiConfig aliPayApiConfig() throws AlipayApiException {
        AliPayVo aliPayVo = configBaseService.getAliPayVo();

        String aliPayCertPath = null;
        String appCertPath = null;
        String aliPayRootCertPath = null;
        try {
            aliPayCertPath = streamToString(new FileInputStream(aliPayVo.getAliPayCertPath()));
            appCertPath = streamToString(new FileInputStream(aliPayVo.getAppCertPath()));
            aliPayRootCertPath = streamToString(new FileInputStream(aliPayVo.getAliPayRootCertPath()));
        } catch (FileNotFoundException e) {
            LogUtil.error(ConstantLog.PAY, e);
        }

        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.builder()
                .setAppId(aliPayVo.getAppId())
                .setAliPayPublicKey(aliPayVo.getPublicKey())

                // 配置密钥相对路径启用
                .setAliPayCertContent(aliPayCertPath)
                .setAppCertContent(appCertPath)
                .setAliPayRootCertContent(aliPayRootCertPath)
                // 配置密钥相对路径启用

                .setCharset("UTF-8")
                .setPrivateKey(aliPayVo.getPrivateKey())
                .setServiceUrl(aliPayVo.getServerUrl())
                .setSignType("RSA2")
                .buildByCertContent(); // 配置密钥相对路径启用
        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);
        return aliPayApiConfig;
    }

    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        //new一个StringBuffer用于字符串拼接
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            //当输入流内容读取完毕时
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            //记得关闭流数据 节约内存消耗
            is.close();
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            log.error("输入流内容读取异常！" + e.getMessage(), e);
        }
        return "";
    }

}
