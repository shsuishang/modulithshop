package com.suisung.shopsuite.sys.controller.manage;

import com.suisung.shopsuite.common.config.ConfigProperties;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.RSAUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.ReleaseDto;
import com.suisung.shopsuite.core.web.service.CloundService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 打包发布代码
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "打包发布代码")
@RestController
@RequestMapping("/manage/sys/release")
public class ReleaseController extends BaseController {
    @Autowired
    private ConfigBaseService configBaseService;


    @Resource
    private ConfigProperties configProperties;

    @Autowired
    private CloundService cloundService;

    @PreAuthorize("hasAuthority('/manage/sys/release/download')")
    @ApiOperation(value = "打包发布代码", notes = "打包发布代码")
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public CommonRes<?> download(@RequestParam("release_type") String releaseType) {
        ReleaseDto releaseDto = new ReleaseDto();
        String configBaseUserId = configBaseService.getConfig("service_user_id", "");
        String configBaseAppKey = configBaseService.getConfig("service_app_key", "");

        releaseDto.setUrl(ConstantConfig.URL_BASE);
        releaseDto.setAppName(configBaseService.getConfig("site_name"));
        releaseDto.setServiceUserId(configBaseUserId);
        releaseDto.setServiceAppKey(configBaseAppKey);

        try {
            String downloadUrl = "";
            if (releaseType.equals("mp")) {
                String appId = configBaseService.getConfig("wechat_xcx_app_id");
                releaseDto.setAppId(appId);
                downloadUrl = cloundService.getMpWeixin(releaseDto);

            } else {
                downloadUrl = cloundService.getWeb(releaseDto);
            }

            return success(downloadUrl);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }


    @ApiOperation(value = "授权信息", notes = "授权信息")
    @RequestMapping(value = "/getLicence", method = RequestMethod.GET)
    public CommonRes<Map> getLicence() throws Exception {
        String licence = "2+WJU0eC2hmoby4Fx3PHTu1dT1IRV5wMIp24otk29WtRThXgdzWPSyHdl0oeNkFNATwI8m+EXBJPAsd0Bo/WpyVkPzFOz7WAh1AMmYmFN1tI7nMCTWC2UmyP+cBuZukq0AalVfbMyzb9ll+t5LGwGD44DKS3CdsEIhqJEr1JUFCsj/D92lK0XB2pyZgyJCnQxDj2dapPbgsNXzsePheIaqQ3v2YHGViQ11ypUJDCwqO57HcNQLLXW0P0v2tCdGzkY9oi3G4Z1mC2ob15cim4baKgnkHBjg5RgvHqkNd1PC+ePeiwHDdDN7nToM3rOFFzt84+G4twZcBI335Gjyu/yg==";
        String licencePublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5Fa6zAE5sJ9y1qzjhGAFWggKbMAS82xNG8wKglG0k6XojKBTw/7evSwC0aEgYU/BkIPzxNb7j6Oap5iZ43YgFgLI1dGalZJnvmLTmRK4+MqkKI6LlQQyCTZtSDkPNr62jYzSya+pPt0hgBHgk2x6YAns83SYmZf+7NT3qB+uxIgVIJTcO6m+SX3MyQU6cRKlt46+A9GwYiPx6davGxiX4TeeS5/sWiV1+yBb1xovNPjGK9d6N/3ObvSPtNXLnFn5jtwT1UanZPdZMR+oYIlltR9QGE3jnaTxlYTUhkY63GMek94dWbJTBQqpaA6t221iCwh8uawX4sbm4ZoRTy8SRwIDAQAB";

        String s = RSAUtil.decryptByPublicKey(licence, licencePublicKey);
        Map res = new HashMap();
        res.put("licence_str", s);
        res.put("is_authorized", true);

        return success(res);
    }
}

