// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.trade.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.ExpressBase;
import com.suisung.shopsuite.sys.repository.ExpressBaseRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.trade.model.entity.OrderDeliveryAddress;
import com.suisung.shopsuite.trade.model.entity.OrderLogistics;
import com.suisung.shopsuite.trade.model.req.OrderLogisticsListReq;
import com.suisung.shopsuite.trade.model.req.OrderLogisticsTraceReq;
import com.suisung.shopsuite.trade.repository.OrderDeliveryAddressRepository;
import com.suisung.shopsuite.trade.repository.OrderLogisticsRepository;
import com.suisung.shopsuite.trade.service.OrderLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 订单发货物流信息表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Service
public class OrderLogisticsServiceImpl extends BaseServiceImpl<OrderLogisticsRepository, OrderLogistics, OrderLogisticsListReq> implements OrderLogisticsService {

    @Autowired
    private OrderDeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ExpressBaseRepository expressBaseRepository;

    private final String requestUrl = "https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    public static Map<String, String> stateMap = new HashMap<String, String>();

    static {
        stateMap.put("0", "没有记录");
        stateMap.put("1", "已揽收");
        stateMap.put("2", "运输途中");
        stateMap.put("201", "到达目的城市");
        stateMap.put("202", "派件中");
        stateMap.put("211", "已投放快递柜或驿站");
        stateMap.put("3", "已签收");
        stateMap.put("301", "正常签收");
        stateMap.put("302", "派件异常后最终签收");
        stateMap.put("304", "代收签收");
        stateMap.put("311", "快递柜或驿站签收");
        stateMap.put("4", "问题件");
        stateMap.put("401", "发货无信息");
        stateMap.put("402", "超时未签收");
        stateMap.put("403", "超时未更新");
        stateMap.put("404", "拒收(退件)");
        stateMap.put("405", "派件异常");
        stateMap.put("406", "退货签收");
        stateMap.put("407", "退货未签收");
        stateMap.put("412", "快递柜或驿站超时未取");
    }

    public String orderOnlineByJson(String orderTrackingNumber, String shipperCode, String CustomerName) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //组装应用级参数
        String RequestData = "{" +
                "'OrderCode': ''," +
                "'shipperCode': " + "'" + shipperCode + "'" + ',' +
                "'CustomerName': " + "'" + CustomerName + "'" + ',' +
                "'logisticCode':" + "'" + orderTrackingNumber + "'" + ',' + '}';

        // 组装系统级参数
        Map params = new HashMap<>();
        String appId = configBaseService.getConfig("kuaidiniao_e_business_id");
        String appKey = configBaseService.getConfig("kuaidiniao_app_key");

        params.put("RequestData", urlEncoder(RequestData, "UTF-8"));
        params.put("EBusinessID", appId);
        params.put("RequestType", "8002");//快递查询接口指令8002/地图版快递查询接口指令8004
        String dataSign = encrypt(RequestData, appKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = HttpUtil.post(requestUrl, params);

        return result;
    }

    /**
     * MD5加密
     * str 内容
     * charset 编码方式
     *
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * str 内容
     * charset 编码方式
     *
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = Base64.encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * content 内容
     * keyValue AppKey
     * charset 编码方式
     *
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    @Override
    public Map trace(OrderLogisticsTraceReq req) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map resultMap = new HashMap();
        OrderDeliveryAddress orderDeliveryAddress = deliveryAddressRepository.get(req.getOrderId());

        if (orderDeliveryAddress == null) {
            throw new BusinessException(__("未找到收货地址"));
        }
        String mobile = orderDeliveryAddress.getDaMobile();
        try {
            mobile = mobile.substring(7, 11);
        } catch (Exception e) {
            throw new BusinessException(__("收货信息中手机号填写错误"));
        }
        String orderTrackingNumber = req.getOrderTrackingNumber();

        if (StrUtil.isEmpty(orderTrackingNumber)) {
            throw new BusinessException(__("订单物流单号为空"));
        }
        String channel = configBaseService.getConfig("logistics_channel", "kuaidi100");
        Integer expressId = req.getExpressId();
        ExpressBase expressBase = expressBaseRepository.get(expressId);

        if (expressBase == null) {
            throw new BusinessException(__("系统中未配置该物流信息，请检查发货信息是否正确！"));
        }
        String shipperCode;

        if ("kuaidi100".equals(channel)) {
            shipperCode = expressBase.getExpressPinyin100();
        } else {
            shipperCode = expressBase.getExpressPinyin();
        }
        String logisticsInfoStr = orderOnlineByJson(orderTrackingNumber, shipperCode, mobile);
        JSONObject logisticsInfo = JSONUtil.parseObj(logisticsInfoStr);
        String StateEx = (String) logisticsInfo.get("StateEx");

        Integer state = logisticsInfo.get("State", Integer.class);
        if (state == 0) {
            String reason = logisticsInfo.get("Reason", String.class);
            throw new BusinessException(__("非系统错误，请联系管理员检查物流配置项，或检查发货信息是否真实有效！错误信息：{" + reason + "}"));
        }

        if (stateMap.get(StateEx) == null) {
            throw new BusinessException(__("物流状态异常"));
        }
        resultMap.put("shipperCode", logisticsInfo.get("ShipperCode"));
        resultMap.put("logisticCode", logisticsInfo.get("LogisticCode"));
        resultMap.put("state", logisticsInfo.get("State"));
        resultMap.put("stateEx", StateEx);
        resultMap.put("express_state", stateMap.get(StateEx));
        resultMap.put("traces", logisticsInfo.get("Traces"));

        //订单发货物流信息
        if (CheckUtil.isNotEmpty(req.getOrderLogisticsId())) {
            OrderLogistics orderLogistics = get(req.getOrderLogisticsId());
            resultMap.put("orderLogistics", orderLogistics);
        }

        return resultMap;
    }

    @Override
    public Map returnLogistics(String returnTrackingName, String returnTrackingNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        QueryWrapper<ExpressBase> expressBaseQueryWrapper = new QueryWrapper<>();
        expressBaseQueryWrapper.eq("express_name", returnTrackingName);
        ExpressBase expressBase = expressBaseRepository.findOne(expressBaseQueryWrapper);

        if (expressBase == null) {
            throw new BusinessException(__("系统中未配置该物流信息，请检查发货信息是否正确！"));
        }

        String logisticsInfoStr = orderOnlineByJson(returnTrackingNumber, expressBase.getExpressPinyin(), "");
        JSONObject logisticsInfo = JSONUtil.parseObj(logisticsInfoStr);
        Map resultMap = new HashMap();
        String StateEx = (String) logisticsInfo.get("StateEx");

        Integer state = logisticsInfo.get("State", Integer.class);
        if (state == 0) {
            String reason = logisticsInfo.get("Reason", String.class);
            throw new BusinessException(__("非系统错误，请联系管理员检查物流配置项，或检查发货信息是否真实有效！错误信息：{" + reason + "}"));
        }
        if (stateMap.get(StateEx) == null) {
            throw new BusinessException(__("物流状态异常"));
        }

        resultMap.put("shipperCode", logisticsInfo.get("ShipperCode"));
        resultMap.put("logisticCode", logisticsInfo.get("LogisticCode"));
        resultMap.put("state", logisticsInfo.get("State"));
        resultMap.put("stateEx", StateEx);
        resultMap.put("express_state", stateMap.get(StateEx));
        resultMap.put("traces", logisticsInfo.get("Traces"));

        return resultMap;
    }

}
