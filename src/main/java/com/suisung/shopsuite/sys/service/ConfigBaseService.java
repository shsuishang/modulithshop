package com.suisung.shopsuite.sys.service;

import com.suisung.shopsuite.core.web.model.SelectVo;
import com.suisung.shopsuite.core.web.service.IBaseService;
import com.suisung.shopsuite.pay.model.vo.AliPayVo;
import com.suisung.shopsuite.pay.model.vo.WxPayV3Vo;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.model.req.ConfigBaseIndexReq;
import com.suisung.shopsuite.sys.model.req.ConfigBaseListReq;
import com.suisung.shopsuite.sys.model.res.ConfigListRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统参数设置表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-11-29
 */
public interface ConfigBaseService extends IBaseService<ConfigBase, ConfigBaseListReq> {

    String getConfig(String config_key);

    boolean getConfig(String config_key, Boolean defaultValue);

    String getConfig(String config_key, String defaultValue);

    Integer getConfig(String config_key, Integer defaultValue);

    Float getConfig(String config_key, Float defaultValue);

    BigDecimal getConfig(String config_key, BigDecimal defaultValue);

    ConfigBase get(Serializable configKey);

    boolean edit(ConfigBase data);

    boolean save(ConfigBase data);

    boolean remove(Serializable configKey);

    String getDefaultImage();

    /**
     * 站点设置
     *
     * @param configBaseIndexReq
     * @return
     */
    ConfigListRes index(ConfigBaseIndexReq configBaseIndexReq);

    /**
     * 站点设置-保存更改
     *
     * @param objectMap
     * @return
     */
    boolean editSite(Map<String, Object> objectMap);

    /**
     * 读取订单状态选项
     *
     * @return
     */
    List<SelectVo> getOrderStateList();

    /**
     * 读取配置，根据当前orderStateId获得下一状态 sc_order_process
     *
     * @param orderStateId
     * @return
     */
    Integer getNextOrderStateId(Integer orderStateId);

    /**
     * 读取配置，根据当前returnStateId获得下一状态 sc_return_process
     *
     * @param returnStateId
     * @return
     */
    Integer getNextReturnStateId(Integer returnStateId);

    /**
     * 支付渠道
     *
     * @return
     */
    List<SelectVo> getPaymentChannelList();

    /**
     * 读取配置，根据paymentChannelId读取paymentChannelCode
     *
     * @param paymentChannelId
     * @return
     */
    String getPaymentChannelCode(Integer paymentChannelId);

    /**
     * 初始化配置
     *
     * @return
     */
    Boolean init();

    /**
     * 退款退货 卖家处理状态
     *
     * @return
     */
    List<SelectVo> getReturnStateList();

    /**
     * 订单启用状态List
     *
     * @return
     */
    List<Integer> initOrderProcess();

    List<Integer> getStateIdList();

    List<Integer> initReturnProcess();

    List<Integer> getReturnStateIdList();

    /**
     * 生成服务码
     *
     * @param chainCode
     * @param width
     * @param high
     * @return
     */
    String qrcode(String chainCode, Integer width, Integer high);

    /**
     * 微信平台证书
     *
     * @return
     */
    String getWxPlatformCertPath();

    /**
     * 微信配置
     *
     * @return
     */
    WxPayV3Vo getWxPayV3Vo();

    /**
     * 微信配置
     *
     * @return
     */
    AliPayVo getAliPayVo();


    /**
     * 用户分销佣金比例
     *
     * @return
     */
    BigDecimal getDistUserLevelConfig(String key, Integer userLevelId, BigDecimal defaultValue);


    /**
     * 是否启用进销存管理
     *
     * @return
     */
    boolean ifInvoicing();

    /**
     * 是否启用分销
     *
     * @return
     */
    boolean ifPlantformFx();

    /**
     * 是否启用供应商市场
     *
     * @return
     */
    boolean ifSupplierMarket();

    /**
     * 读取初始化配置信息
     *
     * @param sourceUccCode
     * @return
     */
    Map<String, Object> getSiteInfo(String sourceUccCode);

    boolean cleanCache();
}
