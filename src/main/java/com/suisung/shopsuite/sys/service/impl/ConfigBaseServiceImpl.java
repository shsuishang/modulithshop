package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.account.model.entity.UserLevel;
import com.suisung.shopsuite.account.service.UserLevelService;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.config.ConfigProperties;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.consts.ConstantSys;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.ErrorTypeEnum;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.core.consts.ConstantRedis;
import com.suisung.shopsuite.core.web.model.SelectVo;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pay.model.vo.AliPayVo;
import com.suisung.shopsuite.pay.model.vo.WxPayV3Vo;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.model.entity.ConfigType;
import com.suisung.shopsuite.sys.model.req.ConfigBaseIndexReq;
import com.suisung.shopsuite.sys.model.req.ConfigBaseListReq;
import com.suisung.shopsuite.sys.model.res.ConfigBaseIndexRes;
import com.suisung.shopsuite.sys.model.res.ConfigListRes;
import com.suisung.shopsuite.sys.repository.ConfigBaseRepository;
import com.suisung.shopsuite.sys.repository.ConfigTypeRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.PageBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;
import static com.suisung.shopsuite.common.utils.UploadUtil.getUploadFileDir;

/**
 * <p>
 * 系统参数设置表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
@Service
public class ConfigBaseServiceImpl extends BaseServiceImpl<ConfigBaseRepository, ConfigBase, ConfigBaseListReq> implements ConfigBaseService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigBaseServiceImpl.class);

    @Autowired
    private ConfigTypeRepository configTypeRepository;

    @Resource
    private ConfigProperties configProperties;

    @Resource
    private UserLevelService userLevelService;

    @Autowired
    private PageBaseService pageBaseService;


    private static final Map<Serializable, ConfigBase> global = new HashMap<>();

    private static List<Integer> stateIdList;     //订单启用状态List
    private static List<Integer> returnStateIdList;     //退单启用状态List
    private static List<Integer> stateIdAllList;  //订单所有状态List
    private static List<SelectVo> stateIdSelectList;  //订单状态对象List数据

    private static List<SelectVo> paymentChannelSelectList;//开启的支付方式
    private static List<SelectVo> returnStateSelectList;//退款退货 卖家处理状态
    private static Map<Integer, String> paymentChannelMap;

    @Autowired
    private RedisService redisService;

    /**
     * 根据config_key 获取 config_value
     *
     * @param config_key
     * @return
     */
    public String getConfig(String config_key) {
        ConfigBase configBase = get(config_key);
        if (ObjectUtil.isNull(configBase)) return "";

        return configBase.getConfigValue();
    }

    /**
     * 根据config_key 获取 config_value
     * config_value为空则返回默认值
     *
     * @param config_key
     * @param defaultValue
     * @return
     */
    public boolean getConfig(String config_key, Boolean defaultValue) {
        String config_value = getConfig(config_key);
        boolean isNumber = NumberUtil.isNumber(config_value);
        if (!isNumber) return defaultValue;
        return "1".equals(config_value);
    }

    /**
     * 根据config_key 获取 config_value
     * config_value为空则返回默认值
     *
     * @param config_key
     * @param defaultValue
     * @return
     */
    public String getConfig(String config_key, String defaultValue) {
        String config_value = getConfig(config_key);
        if (StrUtil.isBlank(config_value)) return defaultValue;
        return config_value;
    }

    /**
     * 根据config_key 获取 config_value
     * config_value为空则返回默认值
     *
     * @param config_key
     * @param defaultValue
     * @return
     */
    public Integer getConfig(String config_key, Integer defaultValue) {
        String config_value = getConfig(config_key);
        boolean isNumber = NumberUtil.isNumber(config_value);
        if (!isNumber) return defaultValue;
        return Convert.toInt(config_value);
    }

    @Override
    public Float getConfig(String config_key, Float defaultValue) {
        String config_value = getConfig(config_key);
        boolean isNumber = NumberUtil.isNumber(config_value);
        if (!isNumber) return defaultValue;
        return Convert.toFloat(config_value);
    }

    @Override
    public BigDecimal getConfig(String config_key, BigDecimal defaultValue) {
        String config_value = getConfig(config_key);
        boolean isNumber = NumberUtil.isNumber(config_value);
        if (!isNumber) return defaultValue;
        return Convert.toBigDecimal(config_value);
    }

    @Override
    public ConfigBase get(Serializable configKey) {

        if (!global.containsKey(configKey)) {
            ConfigBase configBase = super.get(configKey);

            global.put(configKey, configBase);
        }

        return global.get(configKey);
    }

    @CacheEvict(value = {"configInfo"}, allEntries = true)
    @Override
    public boolean edit(ConfigBase data) {
        boolean flag = super.edit(data);

        global.remove(data.getConfigKey());

        if (data.getConfigKey().equals("sc_order_process")) {
            //初始化StateIdRow 启用的订单状态
            stateIdList = initOrderProcess();
            //初始化订单状态选项
            stateIdSelectList = initOrderStateList();
        }

        if (data.getConfigKey().equals("sc_return_process")) {
            returnStateIdList = initReturnProcess();
        }

        if (data.getConfigKey().equals("money_pay_enable")
                || data.getConfigKey().equals("wechat_pay_enable")
                || data.getConfigKey().equals("alipay_enable")
                || data.getConfigKey().equals("offline_pay_enable")) {
            paymentChannelSelectList = initPaymentChannelList();
        }

        return flag;
    }

    @CacheEvict(value = {"configInfo"}, allEntries = true)
    @Override
    public boolean save(ConfigBase data) {
        boolean flag = super.save(data);

        global.remove(data.getConfigKey());

        if (data.getConfigKey().equals("sc_order_process")) {
            //初始化StateIdRow 启用的订单状态
            stateIdList = initOrderProcess();
            //初始化订单状态选项
            stateIdSelectList = initOrderStateList();
        }

        if (data.getConfigKey().equals("sc_return_process")) {
            returnStateIdList = initReturnProcess();
        }

        if (data.getConfigKey().equals("money_pay_enable")
                || data.getConfigKey().equals("wechat_pay_enable")
                || data.getConfigKey().equals("alipay_enable")
                || data.getConfigKey().equals("offline_pay_enable")) {
            paymentChannelSelectList = initPaymentChannelList();
        }

        return flag;
    }

    @Override
    public boolean remove(Serializable configKey) {
        ConfigBase configBase = get(configKey);

        if (configBase.getConfigBuildin()) {
            throw new BusinessException(__("系统内置，不可删除"));
        }

        boolean flag = super.remove(configKey);

        global.remove(configKey);

        return flag;
    }

    public String getDefaultImage() {
        ConfigBase configBase = get("default_image");

        return configBase.getConfigValue();
    }

    private void manageSingle(ConfigBase configBase, Map<String, Object> map) {

        String newString = configBase.getConfigOptions().replaceAll("\\r?\\n", "|");
        String[] options = newString.split("\\|");
        Map<String, String> optionsList = new HashMap<>();

        if (options.length > 0) {
            manageSplit(map, options, optionsList);
        }
    }

    private void manageSplit(Map<String, Object> map, String[] options, Map<String, String> optionsList) {
        for (String option : options) {

            if (option != null) {
                String str = option.replaceAll(":|：|\\s+", "|");
                String[] item = str.split("\\|");
                optionsList.put(item[0], item[1]);
            }

        }

        map.put("optionsList", optionsList);
    }

    @Override
    public ConfigListRes index(ConfigBaseIndexReq configBaseIndexReq) {
        ConfigListRes configListRes = new ConfigListRes();
        List<ConfigBaseIndexRes> resList = new ArrayList<>();

        // 获取配置类型列表
        QueryWrapper<ConfigType> configTypeQueryWrapper = new QueryWrapper<>();
        configTypeQueryWrapper.eq("config_type_module", configBaseIndexReq.getConfigTypeModule());
        configTypeQueryWrapper.orderByAsc("config_type_sort");
        List<ConfigType> configTypeList = configTypeRepository.find(configTypeQueryWrapper);

        // 获取配置项列表
        QueryWrapper<ConfigBase> configBaseQueryWrapper = new QueryWrapper<>();
        configBaseQueryWrapper.eq("config_enable", true);
        configBaseQueryWrapper.orderByAsc("config_sort");
        List<ConfigBase> configBaseList = find(configBaseQueryWrapper);

        if (!CollectionUtils.isEmpty(configTypeList)) {
            for (ConfigType configType : configTypeList) {
                ConfigBaseIndexRes configBaseIndexRes = new ConfigBaseIndexRes();
                configBaseIndexRes.setConfigTypeId(configType.getConfigTypeId());
                configBaseIndexRes.setConfigTypeName(configType.getConfigTypeName());
                // 查询配置项列表
                List<Map<String, Object>> itemList = new ArrayList<>();

                if (!CollectionUtils.isEmpty(configBaseList)) {
                    for (ConfigBase configBase : configBaseList) {

                        if (configType.getConfigTypeId().equals(configBase.getConfigTypeId())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("config_key", configBase.getConfigKey());
                            map.put("config_title", configBase.getConfigTitle());
                            map.put("config_value", configBase.getConfigValue());
                            map.put("config_datatype", configBase.getConfigDatatype());
                            map.put("config_note", configBase.getConfigNote());

                            switch (configBase.getConfigDatatype()) {
                                case ConstantSys.CHECKBOX:
                                    //复选框
                                    String newString = configBase.getConfigOptions().replaceAll("\\r?\\n", "|");
                                    String[] options = newString.split("\\|");
                                    Map<String, String> optionsList = new HashMap<>();

                                    if (options.length > 0) {
                                        manageSplit(map, options, optionsList);
                                        // 选择值
                                        map.put("config_value", configBase.getConfigValue().split(","));
                                    }

                                    break;
                                case ConstantSys.RADIO:
                                case ConstantSys.SELECT:

                                    // 下拉选择框
                                    //单选框
                                    manageSingle(configBase, map);

                                    break;

                                case ConstantSys.IMAGE:

                                    // 单图片
                                    map.put("config_value", configBase.getConfigValue());

                                    break;
                                case ConstantSys.IMAGES:

                                    // 多图片
                                    String[] images = configBase.getConfigValue().split(",");

                                    if (images.length > 0) {
                                        // 图片地址
                                        List<String> list = new ArrayList<>(Arrays.asList(images));
                                        map.put("config_value", list);
                                    }
                                    break;
                                default:
                                    break;
                            }

                            itemList.add(map);
                        }
                    }
                    configBaseIndexRes.setItems(itemList);
                }

                resList.add(configBaseIndexRes);
            }
        }
        configListRes.setItems(resList);

        return configListRes;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editSite(Map<String, Object> objectMap) {
        boolean result = false;

        if (!objectMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();

                // 数组处理
                if (val instanceof List<?>) {
                    List<?> list = (List<?>) val;
                    // 初始化URL数组
                    List<String> item = new ArrayList<>();

                    if (list.size() > 0) {
                        for (Object v : list) {
                            String value = String.valueOf(v);
                            item.add(value);
                        }
                    }
                    // 逗号拼接
                    val = String.join(",", item);
                }

                // 查询记录
                ConfigBase configBase = get(key);

                if (configBase == null) {
                    throw new BusinessException(__("该数据不存在"));
                }

                // 更新记录
                configBase.setConfigValue(ObjectUtil.isEmpty(val) ? "" : String.valueOf(val));
                result = edit(configBase);
            }
        }

        return result;
    }

    @Override
    public List<SelectVo> getOrderStateList() {
        if (stateIdSelectList.size() > 0) {
            return stateIdSelectList;
        } else {
            return initOrderStateList();
        }
    }

    /**
     * 读取配置，根据当前orderStateId获得下一状态 sc_order_process
     *
     * @param orderStateId
     * @return
     */
    @Override
    public Integer getNextOrderStateId(Integer orderStateId) {
        Integer nextOrderStateId = 0;

        int index = stateIdList.indexOf(orderStateId);

        if (index == -1) {
            //如果为待出库， 修正为代发货
            if (orderStateId.intValue() == StateCode.ORDER_STATE_PICKING) {

            }

            nextOrderStateId = 0;
            //throw  new BusinessException(__("订单当前状态配置数据有误！"));
        } else {
            //最后一个
            if (stateIdList.size() == index + 1) {
                nextOrderStateId = StateCode.ORDER_STATE_FINISH;

            } else {
                nextOrderStateId = stateIdList.get(index + 1);
            }
        }

        return nextOrderStateId;
    }

    /**
     * 读取配置，根据当前returnStateId获得下一状态 sc_return_process
     *
     * @param returnStateId
     * @return
     */
    @Override
    public Integer getNextReturnStateId(Integer returnStateId) {
        Integer nextReturnStateId = 0;

        int index = returnStateIdList.indexOf(returnStateId);

        if (index == -1) {
            nextReturnStateId = 0;
            //throw  new BusinessException(__("订单当前状态配置数据有误！"));
        } else {
            //最后一个
            if (returnStateIdList.size() == index + 1) {
                nextReturnStateId = StateCode.RETURN_PROCESS_FINISH;

            } else {
                nextReturnStateId = returnStateIdList.get(index + 1);
            }
        }

        return nextReturnStateId;
    }

    @Override
    public List<SelectVo> getPaymentChannelList() {
        if (paymentChannelSelectList.size() > 0) {
            return paymentChannelSelectList;
        } else {
            return initPaymentChannelList();
        }
    }

    /**
     * 读取配置，根据paymentChannelId读取paymentChannelCode
     *
     * @param paymentChannelId
     * @return
     */
    @Override
    public String getPaymentChannelCode(Integer paymentChannelId) {
        List<SelectVo> channelList = getPaymentChannelList();

        // 创建一个空的Map来存储channelList中的数据
        Map<Integer, SelectVo> channelMap = new HashMap<>();

        // 将channelList转换成Map
        for (SelectVo selectVo : channelList) {
            channelMap.put(selectVo.getValue(), selectVo);
        }

        // 使用paymentChannelId查询SelectVo对象并获取Ext1字段
        SelectVo selectedChannel = channelMap.get(paymentChannelId);
        return selectedChannel.getExt2();
    }


    @Override
    public List<SelectVo> getReturnStateList() {
        if (returnStateSelectList.size() > 0) {
            return returnStateSelectList;
        } else {
            return initReturnStateList();
        }
    }

    @Override
    public Boolean init() {
        //初始化StateIdRow 启用的订单状态
        stateIdList = initOrderProcess();

        //初始化订单状态选项
        List<SelectVo> stateIdSelectList = initOrderStateList();


        returnStateIdList = initReturnProcess();

        //初始化initPaymentChannelList
        List<SelectVo> paymentChannelSelectList = initPaymentChannelList();

        //初始化initReturnStateList
        List<SelectVo> returnStateSelectList = initReturnStateList();

        //初始化PaymentChannelMap    map[uint]string   //

        return null;
    }

    /**
     * 处理订单状态
     *
     * @return
     */
    @Override
    public List<Integer> initOrderProcess() {
        //初始化StateIdRow 启用的订单状态
        ConfigBase configBase = get("sc_order_process");

        // 选择值
        List<Integer> stateIdList = Convert.toList(Integer.class, configBase.getConfigValue());

        //从小到大排序
        Collections.sort(stateIdList);

        return stateIdList;
    }

    public List<Integer> getStateIdList() {
        return stateIdList;
    }

    @Override
    public List<Integer> initReturnProcess() {
        ConfigBase configBase = get("sc_return_process");

        // 选择值
        List<Integer> returnStateIdList = Convert.toList(Integer.class, configBase.getConfigValue());

        //从小到大排序
        Collections.sort(returnStateIdList);

        return returnStateIdList;
    }

    public List<Integer> getReturnStateIdList() {
        return returnStateIdList;
    }

    /**
     * 生成服务码
     *
     * @param chainCode
     * @param width
     * @param high
     * @return
     */
    @Override
    public String qrcode(String chainCode, Integer width, Integer high) {
        try {
            chainCode = URLEncoder.encode(chainCode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(__("服务码异常！"));
        }

        return String.format(ConstantConfig.URL_BASE + "/front/sys/config/getQrcode?code=%s&w=%d&h=%d", chainCode, width, high);
    }

    /**
     * 微信平台证书
     *
     * @return
     */
    @Override
    public String getWxPlatformCertPath() {
        //微信平台证书
        File file = new File(getUploadFileDir(), "wx_cert.pem");
        String platformCertPath = file.getAbsolutePath();
        return platformCertPath;
    }

    /**
     * 微信配置
     *
     * @return
     */
    @Override
    public WxPayV3Vo getWxPayV3Vo() {
        WxPayV3Vo wxPayV3Vo = new WxPayV3Vo();

        //商户编号
        String mchid = getConfig("wechat_pay_mchid");
        wxPayV3Vo.setMchId(mchid);

        //密钥keyPath
        String keyPath = getConfig("wechat_pay_apiclient_key");
        wxPayV3Vo.setKeyPath(keyPath);

        String cert = getConfig("wechat_pay_apiclient_cert");
        wxPayV3Vo.setCertPath(cert);

        //证书序列号
        String serialNo = getConfig("wechat_pay_serial_no");
        wxPayV3Vo.setSerialNo(serialNo);

        String v3key = getConfig("wechat_pay_v3_key");
        wxPayV3Vo.setApiKey3(v3key);

        String wxPlatformCertPath = getWxPlatformCertPath();
        logger.info("证书路径: {}", wxPlatformCertPath);

        wxPayV3Vo.setPlatformCertPath(wxPlatformCertPath);

        return wxPayV3Vo;
    }

    /**
     * 支付宝配置
     *
     * @return
     */
    @Override
    public AliPayVo getAliPayVo() {
        AliPayVo aliPayVo = new AliPayVo();

        // AppId
        String alipayAppId = getConfig("alipay_app_id");
        aliPayVo.setAppId(alipayAppId);

        // 私钥
        String alipayRsaPrivateKey = getConfig("alipay_rsa_private_key");
        aliPayVo.setPrivateKey(alipayRsaPrivateKey);

        // 公钥
        String alipayRsaPublicKey = getConfig("alipay_rsa_public_key");
        aliPayVo.setPublicKey(alipayRsaPublicKey);

        // 应用证书路径
        String alipayAppCertPath = getConfig("alipay_app_cert_path");
        aliPayVo.setAppCertPath(alipayAppCertPath);

        // 支付宝证书路径
        String alipayCertPath = getConfig("alipay_cert_path");
        aliPayVo.setAliPayCertPath(alipayCertPath);

        // 支付宝证书根路径
        String alipayRootCertPath = getConfig("alipay_root_cert_path");
        aliPayVo.setAliPayRootCertPath(alipayRootCertPath);

        // serverUrl
        String alipayServerUrl = getConfig("alipay_server_url");
        aliPayVo.setServerUrl(alipayServerUrl);

        return aliPayVo;
    }

    /**
     * 订单状态选项
     *
     * @return
     */
    public List<SelectVo> initOrderStateList() {
        stateIdAllList = new ArrayList<>();
        stateIdSelectList = new ArrayList<>();

        ConfigBase configBase = get("sc_order_process");

        // 选择值
        List<Integer> stateIdList = Convert.toList(Integer.class, configBase.getConfigValue());

        //从小到大排序
        Collections.sort(stateIdList);

        if (configBase.getConfigDatatype().equals(ConstantSys.CHECKBOX)) {
            //复选框
            String newString = configBase.getConfigOptions().replaceAll("\\r?\\n", "|");
            String[] options = newString.split("\\|");

            if (options.length > 0) {
                for (String option : options) {
                    if (option != null) {
                        String str = option.replaceAll(":|：|\\s+", "|");
                        String[] item = str.split("\\|");

                        Integer val = Convert.toInt(item[0]);

                        if (stateIdList.contains(val)) {
                            SelectVo selectVo = new SelectVo();
                            selectVo.setValue(Convert.toInt(item[0]));
                            selectVo.setLabel(item[1]);

                            stateIdSelectList.add(selectVo);
                        }

                        stateIdAllList.add(val);
                    }
                }
            }
        }


        SelectVo selectVo = new SelectVo();
        selectVo.setValue(StateCode.ORDER_STATE_CANCEL);
        selectVo.setLabel(__("交易取消"));
        stateIdSelectList.add(selectVo);

        return stateIdSelectList;
    }

    /**
     * 取得支付渠道
     *
     * @return
     */
    public List<SelectVo> initPaymentChannelList() {
        paymentChannelSelectList = new ArrayList<>();
        paymentChannelMap = new HashMap<>();

        // 获取配置类型列表
        QueryWrapper<ConfigType> configTypeQueryWrapper = new QueryWrapper<>();
        configTypeQueryWrapper.eq("config_type_module", 1004);
        configTypeQueryWrapper.orderByAsc("config_type_sort");
        List<ConfigType> configTypes = configTypeRepository.find(configTypeQueryWrapper);

        for (ConfigType configType : configTypes) {
            try {

                //1403	微信支付 wechat_pay_enable
                //1401	支付宝支付 alipay_enable
                //1422	线下支付 offline_pay_enable
                //1406	余额支付 money_pay_enable
                //1413	积分支付 points_pay_enable

                String id = "";
                String ck = "";
                String img = "";

                if (configType.getConfigTypeId().equals(1403)) {
                    id = "wxpay";
                    ck = "wechat_pay_enable";
                    img = "wechat_pay_logo";
                } else if (configType.getConfigTypeId().equals(1401)) {
                    id = "alipay";
                    ck = "alipay_enable";
                    img = "alipay_logo";
                } else if (configType.getConfigTypeId().equals(1422)) {
                    id = "offline";
                    ck = "offline_pay_enable";
                    img = "offline_pay_logo";
                } else if (configType.getConfigTypeId().equals(1406)) {
                    id = "money";
                    ck = "money_pay_enable";
                    img = "money_pay_logo";
                } else if (configType.getConfigTypeId().equals(1413)) {
                    id = "points";
                    ck = "points_pay_enable";
                    img = "points_pay_logo";
                } else {
                    throw new Exception(__("支付类型有误！"));
                }

                ConfigBase one = get(ck);
                ConfigBase logo = get(img);

                //启用的
                SelectVo selectVo = new SelectVo();
                selectVo.setValue(configType.getConfigTypeId());
                selectVo.setLabel(configType.getConfigTypeName());
                selectVo.setExt1(logo.getConfigValue());
                selectVo.setExt2(id);
                selectVo.setEnable(Convert.toBool(one.getConfigValue()));

                paymentChannelSelectList.add(selectVo);
                paymentChannelMap.put(configType.getConfigTypeId(), configType.getConfigTypeName());
            } catch (Exception e) {
                LogUtil.error(ConstantLog.PAY, e);
            }
        }

        return paymentChannelSelectList;
    }

    /**
     * 退款退货 卖家处理状态
     *
     * @return
     */
    private List<SelectVo> initReturnStateList() {
        returnStateSelectList = new ArrayList<>();

        String[] labels = {
                __("提交退单"),
                __("退单审核"),
                __("收货确认"),
                __("退款确认"),
                __("收款确认"),
                __("完成"),
                __("拒绝退货"),
                __("买家取消")
        };

        Integer[] values = {
                StateCode.RETURN_PROCESS_SUBMIT,
                StateCode.RETURN_PROCESS_CHECK,
                StateCode.RETURN_PROCESS_RECEIVED,
                StateCode.RETURN_PROCESS_REFUND,
                StateCode.RETURN_PROCESS_RECEIPT_CONFIRMATION,
                StateCode.RETURN_PROCESS_FINISH,
                StateCode.RETURN_PROCESS_REFUSED,
                StateCode.RETURN_PROCESS_CANCEL
        };
        for (int i = 0; i < labels.length; i++) {
            SelectVo selectVo = new SelectVo();
            selectVo.setLabel(labels[i]);
            selectVo.setValue(values[i]);
            returnStateSelectList.add(selectVo);
        }

        return returnStateSelectList;
    }


    @Override
    public BigDecimal getDistUserLevelConfig(String key, Integer userLevelId, BigDecimal defaultValue) {

        List<Map> fx_level_config_rows = Convert.toList(Map.class, JSONUtil.parseArray(getConfig("fx_level_config", "[]")));
        if (CheckUtil.isNotEmpty(userLevelId)) {
            //动态修正分销相关参数
            //根据等级修正，使用表格设定数据
            String[] config_tmp_row = {"plantform_fx_cps_rate_1",
                    "plantform_fx_cps_rate_2",
                    "plantform_fx_cps_rate_3",
                    "sale_prize_sp_rate_1",
                    "sale_prize_sp_rate_2",
                    "buy_prize_da_rate",
                    "buy_prize_ca_rate",
                    "sale_prize_da_rate",
                    "sale_prize_ca_rate",
                    "buy_prize_pt_rate",
                    "sale_prize_pt_rate",
                    "sale_prize_distributor_rate",
                    "sale_prize_salesperson_rate",
                    "plantform_fx_member_points",
                    "plantform_fx_rebate"};
            if (ArrayUtil.contains(config_tmp_row, key)) {
                //用户等级
                if (CheckUtil.isEmpty(userLevelId)) {
                    userLevelId = 1001;
                }

                Map fx_level_config_row_result = new HashMap();
                for (Map fx_level_config_row : fx_level_config_rows) {
                    if (ObjectUtil.equal(userLevelId, Convert.toInt(fx_level_config_row.get("user_level_id")))) {
                        fx_level_config_row_result = fx_level_config_row;
                        break;
                    }
                }
                if (fx_level_config_row_result != null) {
                    return Convert.toBigDecimal(fx_level_config_row_result.get(key), BigDecimal.ZERO);
                }
            } else {
                throw new BusinessException(String.format(__("分销配置信息异常！ %s"), key));
            }
        } else {
            Integer user_level_id = 1001;

            Map fx_level_config_row_result = new HashMap();
            for (Map fx_level_config_row : fx_level_config_rows) {
                if (ObjectUtil.equal(user_level_id, Convert.toInt(fx_level_config_row.get("user_level_id")))) {
                    fx_level_config_row_result = fx_level_config_row;
                    break;
                }
            }

            BigDecimal result = Convert.toBigDecimal(fx_level_config_row_result.get(key), BigDecimal.ZERO);
            if (ObjectUtil.isNotNull(result)) return result;
        }

        return BigDecimal.ZERO;
    }


    /**
     * 是否启用进销存管理
     *
     * @return
     */
    @Override
    public boolean ifInvoicing() {
        return getConfig("invoicing_enable", false);
    }

    @Override
    public boolean ifPlantformFx() {
        return getConfig("plantform_fx_enable", false);
    }

    /**
     * 是否启用供应商市场
     *
     * @return
     */
    @Override
    public boolean ifSupplierMarket() {
        return getConfig("supplier_market_enable", false);
    }

    /**
     * 读取初始化配置信息
     *
     * @param sourceUccCode
     * @return
     */
    @Cacheable(value = {"configInfo"})
    @Override
    public Map<String, Object> getSiteInfo(String sourceUccCode) {
        String keys = "site_name,site_meta_keyword,site_meta_description,site_version,copyright,icp_number,site_company_name,site_address,site_tel,account_login_bg,site_admin_logo,site_mobile_logo,site_pc_logo,date_format,time_format,cache_enable,cache_expire,site_status,advertisement_open,wechat_connect_auto,wechat_app_id,product_spec_edit,default_image,product_salenum_flag,b2b_flag,hall_b2b_enable,product_ziti_flag,plantform_fx_enable,plantform_fx_gift_point,plantform_fx_withdraw_min_amount,plantform_poster_bg,plantform_commission_withdraw_mode,product_poster_bg,live_mode_xcx,kefu_type_id,withdraw_received_day,withdraw_monthday,default_shipping_district,points_enable,voucher_enable,b2b_enable,chain_enable,edu_enable,hall_enable,multilang_enable,sns_enable,subsite_enable,supplier_enable,im_enable,chat_global,wechat_mp_qrcode";


        List<String> objects = (List<String>) Convert.toList(keys);
        List<ConfigBase> configBases = gets(objects);

        Map<String, Object> res = new HashMap<>();

        for (ConfigBase item : configBases) {

            switch (item.getConfigDatatype()) {

                case ConstantSys.RADIO:
                case ConstantSys.SELECT:
                case ConstantSys.NUMBER:

                    // 下拉选择框
                    //单选框
                    res.put(item.getConfigKey(), Convert.toInt(item.getConfigValue()));

                    break;
                default:
                    res.put(item.getConfigKey(), item.getConfigValue());

                    break;
            }
        }

        //订单状态
        List<SelectVo> orderStateList = getOrderStateList();
        res.put("order_state_list", orderStateList);

        //支付渠道
        List<SelectVo> paymentChannelList = getPaymentChannelList();
        res.put("payment_channel_list", paymentChannelList);

        //退款退货 卖家处理状态
        List<SelectVo> returnStateList = getReturnStateList();
        res.put("return_state_list", returnStateList);

        //user_level_name
        Map<Integer, String> userLevelMap = new HashMap<>();
        Map<Integer, Integer> userLevelRateMap = new HashMap<>();
        List<UserLevel> userLevels = userLevelService.find(new QueryWrapper<>());
        for (UserLevel userLevel : userLevels) {
            userLevelMap.put(userLevel.getUserLevelId(), userLevel.getUserLevelName());
            userLevelRateMap.put(userLevel.getUserLevelId(), userLevel.getUserLevelRate());
        }

        res.put("user_level_map", userLevelMap);
        res.put("user_level_rate_map", userLevelRateMap);

        // 错误日志异常类型
        res.put("error_type_list", ErrorTypeEnum.getAllValues());
        //

        //用户中心菜单
        Map userCenterMenu = pageBaseService.getUserCenterMenu();
        res.put("user_center_menu", userCenterMenu);

        //项目版本
        String version = configProperties.getVersion();
        res.put("version", version);
        res.put("site_version", version);

        return res;
    }

    @Override
    public boolean cleanCache() {
        Set<String> c_keys = redisService.keys(ConstantRedis.Cache_NameSpace + "*");
        Set<String> b_keys = redisService.keys("menuTree:*");
        Set<String> s_keys = redisService.keys("productCategoryTree:*");
        Set<String> cf_keys = redisService.keys("configInfo:*");
        Set<String> menu_keys = redisService.keys("menuTree:*");
        Set<String> pc_keys = redisService.keys("productCategoryList:*");
        Set<String> db_keys = redisService.keys("districtBaseTree:*");

        redisService.del(c_keys);
        redisService.del(b_keys);
        redisService.del(s_keys);
        redisService.del(cf_keys);
        redisService.del(menu_keys);
        redisService.del(pc_keys);
        redisService.del(db_keys);

        return false;
    }
}
