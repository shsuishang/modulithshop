package com.suisung.shopsuite.sys.controller.front;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.suisung.shopsuite.account.service.UserLevelService;
import com.suisung.shopsuite.common.config.ConfigProperties;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.PageBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统参数设置表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Api(tags = "系统参数设置表")
@RestController
@RequestMapping("/front/sys/config")
public class ConfigController extends BaseController {
    @Autowired
    private ConfigBaseService configBaseService;

    @Resource
    private ConfigProperties configProperties;

    @Resource
    private UserLevelService userLevelService;

    @Autowired
    private PageBaseService pageBaseService;


    @ApiOperation(value = "站点配置信息", notes = "站点配置信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonRes<Map<String, Object>> info(@RequestParam(name = "source_ucc_code", required = false) String sourceUccCode) {
        Map<String, Object> res = configBaseService.getSiteInfo(sourceUccCode);

        return success(res);
    }

    @ApiOperation(value = "获取公钥", notes = "站点配置信息")
    @RequestMapping(value = "/publicKey", method = RequestMethod.GET)
    public CommonRes<Map<String, Object>> getPublicKey() {
        Map<String, Object> res = new HashMap<>();

        res.put("public_key", configBaseService.getConfig("public_key", configProperties.getPublicKey()));

        return success(res);
    }

    @ApiOperation(value = "读取移动端语言包数据", notes = "读取移动端语言包数据")
    @RequestMapping(value = "/listTranslateLang", method = RequestMethod.GET)
    public CommonRes<?> listTranslateLang() {
        // todo listTranslateLang
        return success();
    }

    @ApiOperation(value = "根据提货码获取二维码", notes = "根据提货码获取二维码")
    @RequestMapping(value = "/getQrcode", method = RequestMethod.GET)
    public void getQrcode(HttpServletResponse response,
                          @RequestParam(name = "code") String code,
                          @RequestParam(name = "w", defaultValue = "450") Integer width,
                          @RequestParam(name = "h", defaultValue = "450") Integer high) {

        response.setContentType("image/png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedImage image = QrCodeUtil.generate(code, width, high);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            LogUtil.error("根据提货码获取二维码信息异常！" + e.getMessage(), e);
        } finally {
            try {
                assert os != null;
                os.close();
            } catch (IOException e) {
                LogUtil.error("根据提货码获取二维码异常！" + e.getMessage(), e);
            }
        }
    }

    @ApiOperation(value = "加载广告导航", notes = "加载广告导航")
    @RequestMapping(value = "/guide", method = RequestMethod.GET)
    public CommonRes<?> guide() {
        String advertisementImage = configBaseService.getConfig("advertisement_image");
        Integer advertisementOpen = configBaseService.getConfig("advertisement_open", 0);
        String logo = configBaseService.getConfig("site_logo");
        List<String> advertisementImages = Convert.toList(String.class, advertisementImage);


        Map data = new HashMap();
        data.put("items", advertisementImages);
        data.put("advertisement_open", advertisementOpen);
        data.put("logo", logo);

        return success(data);
    }


    @ApiOperation(value = "站点帮助", notes = "站点帮助")
    @RequestMapping(value = "/getPcHelp", method = RequestMethod.GET)
    public CommonRes<Map<String, Object>> getPcHelp() {
        String keys = "page_pc_help";

        String pagePcHelp = configBaseService.getConfig(keys);
        Map<String, Object> res = new HashMap<>();
        res.put("page_pc_help", pagePcHelp);

        return success(res);
    }
}

