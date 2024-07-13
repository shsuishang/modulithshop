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
package com.suisung.shopsuite.pay.controller.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayRequest;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.enums.v3.OtherApiEnum;
import com.ijpay.wxpay.model.v3.*;
import com.suisung.shopsuite.account.model.entity.UserBindConnect;
import com.suisung.shopsuite.account.repository.UserBindConnectRepository;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.auth.Auth;
import com.suisung.shopsuite.common.consts.BindConnectCode;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.pojo.dto.ErrorTypeEnum;
import com.suisung.shopsuite.common.pojo.dto.PayType;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.HttpServletUtils;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.pay.model.entity.ConsumeTrade;
import com.suisung.shopsuite.pay.model.entity.UserPay;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.model.input.PaymentInput;
import com.suisung.shopsuite.pay.model.output.PayOutput;
import com.suisung.shopsuite.pay.model.output.ProcessPayOutput;
import com.suisung.shopsuite.pay.model.req.PaymentReq;
import com.suisung.shopsuite.pay.model.res.*;
import com.suisung.shopsuite.pay.model.vo.AliPayVo;
import com.suisung.shopsuite.pay.model.vo.PayMetVo;
import com.suisung.shopsuite.pay.model.vo.WxPayV3Vo;
import com.suisung.shopsuite.pay.repository.ConsumeCombineRepository;
import com.suisung.shopsuite.pay.repository.ConsumeTradeRepository;
import com.suisung.shopsuite.pay.repository.UserPayRepository;
import com.suisung.shopsuite.pay.repository.UserResourceRepository;
import com.suisung.shopsuite.pay.service.ConsumeTradeService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.trade.model.entity.OrderInfo;
import com.suisung.shopsuite.trade.service.OrderInfoService;
import com.suisung.shopsuite.trade.service.OrderService;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;
import static com.suisung.shopsuite.common.utils.UploadUtil.getUploadFileDir;

/**
 * <p>
 * 唤起支付
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Api(tags = "唤起支付")
@RestController
@RequestMapping("/front/pay/consumeDeposit")
public class PaymentIndexController extends BaseController {

    @Autowired
    private ConsumeTradeRepository tradeRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ConsumeCombineRepository combineRepository;

    @Autowired
    private UserBindConnectRepository userBindConnectRepository;

    @Autowired
    private ConsumeTradeService consumeTradeService;

    @Autowired
    private UserResourceRepository userResourceRepository;

    @Autowired
    private UserPayRepository userPayRepository;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderService orderService;


    private static final Logger logger = LoggerFactory.getLogger(PaymentIndexController.class);

    @RequestMapping(value = "/moneyPay", method = RequestMethod.POST)
    public CommonRes<?> money(@Auth ContextUser user, PaymentReq req) {
        Integer userId = ContextUtil.checkLoginUserId();

        req.setPaymentChannelId(StateCode.PAYMENT_MET_MONEY);
        req.setDepositPaymentType(StateCode.PAYMENT_TYPE_ONLINE);

        //处理订单支付结果
        PayMetVo payInfo = new PayMetVo();
        payInfo.setPaymentMetId(StateCode.PAYMENT_MET_MONEY);
        payInfo.setPaymentChannelId(req.getPaymentChannelId());
        payInfo.setPaymentTypeId(req.getDepositPaymentType());
        payInfo.setPmMoney(req.getPmMoney());

        MoneyPayRes res = new MoneyPayRes();
        res.setOrderId(req.getOrderId());

        //判断余额是否足够，不够报错！
        UserResource userResourceBuy = userResourceRepository.get(userId);
        if (userResourceBuy.getUserMoney().compareTo(payInfo.getPmMoney()) >= 0) {

            checkPayPasswd(user.getUserId(), req.getPassword());

            ProcessPayOutput processPayOutput = consumeTradeService.processPay(req.getOrderId(), payInfo);

            if (processPayOutput.getPaid()) {
                res.setPaid(processPayOutput.getPaid());
                res.setStatusCode(200);
            } else {
                res.setStatusCode(250);
            }

            return success(res);
        } else {
            res.setPaid(false);
            res.setStatusCode(250);

            return fail(res);
        }
    }


    @RequestMapping(value = "/offlinePay", method = RequestMethod.POST)
    public CommonRes<?> offlinePay(@Auth ContextUser user, PaymentReq req) {
        Integer userId = ContextUtil.checkLoginUserId();

        req.setPaymentChannelId(StateCode.PAYMENT_MET_OFFLINE);
        req.setDepositPaymentType(StateCode.PAYMENT_TYPE_OFFLINE);

        MoneyPayRes res = new MoneyPayRes();
        res.setOrderId(req.getOrderId());


        if (CheckUtil.isNotEmpty(req.getOrderId())) {
            // 线下支付，修改线下支付状态

            List<String> orderIds = Convert.toList(String.class, req.getOrderId());

            QueryWrapper<OrderInfo> infoQueryWrapper = new QueryWrapper<>();
            infoQueryWrapper.in("order_id", orderIds);
            
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setPaymentTypeId(StateCode.PAYMENT_TYPE_OFFLINE);

            if (!orderInfoService.edit(orderInfo, infoQueryWrapper)) {
                throw new BusinessException(__("修改线下支付状态失败！"));
            }

            //修改订单为待发货状态
            for (String orderId : orderIds) {
                if (!orderService.editNextState(orderId, StateCode.ORDER_STATE_WAIT_PAY, StateCode.ORDER_STATE_PICKING, __("线下支付"))) {
                    throw new BusinessException(__("修改订单为待发货状态失败！"));
                }
            }

            res.setPaid(true);
            return success(res);
        } else {
            res.setPaid(false);
            res.setStatusCode(250);

            return fail(res);
        }
    }

    /**
     * 验证支付密码
     *
     * @param userId
     * @param password
     * @return
     */
    public void checkPayPasswd(Integer userId, String password) {

        UserPay userPay = userPayRepository.get(userId);
        if (userPay != null && StrUtil.isNotBlank(userPay.getUserPayPasswd())) {

            String userPaySalt = userPay.getUserPaySalt();
            String saltPassword = SecureUtil.md5(userPaySalt + SecureUtil.md5(password));

            if (!StrUtil.equals(saltPassword, userPay.getUserPayPasswd())) {
                throw new BusinessException(__("支付密码错误！"));
            }
        } else {
            throw new BusinessException(ResultCode.PAY_PWD_FAILED);
        }
    }

    private String savePlatformCert(String associatedData, String nonce, String cipherText, String certPath) {
        try {
            AesUtil aesUtil = new AesUtil(configBaseService.getConfig("wechat_pay_v3_key").getBytes(StandardCharsets.UTF_8));
            // 平台证书密文解密
            // encrypt_certificate 中的  associated_data nonce  ciphertext
            String publicKey = aesUtil.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    cipherText
            );
            // 保存证书
            FileWriter writer = new FileWriter(certPath);
            writer.write(publicKey);
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
            return certificate.getSerialNumber().toString(16).toUpperCase();
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return e.getMessage();
        }
    }

    @RequestMapping("/get")
    public String v3Get() {
        File file = new File(getUploadFileDir(), "wx_cert.pem");
        String platformCertPath = file.getAbsolutePath();

        // 获取平台证书列表
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.toString(),
                    OtherApiEnum.GET_CERTIFICATES.toString(),
                    configBaseService.getConfig("wechat_pay_mchid"),
                    getSerialNumber(),
                    null,
                    configBaseService.getConfig("wechat_pay_apiclient_key"),
                    ""
            );

            String timestamp = response.getHeader("Wechatpay-Timestamp");
            String nonceStr = response.getHeader("Wechatpay-Nonce");
            String serialNumber = response.getHeader("Wechatpay-Serial");
            String signature = response.getHeader("Wechatpay-Signature");

            String body = response.getBody();
            int status = response.getStatus();

            logger.info("serialNumber: {}", serialNumber);
            logger.info("status: {}", status);
            logger.info("body: {}", body);
            int isOk = 200;
            if (status == isOk) {
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                // 默认认为只有一个平台证书
                JSONObject encryptObject = dataArray.getJSONObject(0);
                JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
                String associatedData = encryptCertificate.getStr("associated_data");
                String cipherText = encryptCertificate.getStr("ciphertext");
                String nonce = encryptCertificate.getStr("nonce");
                String serialNo = encryptObject.getStr("serial_no");
                final String platSerialNo = savePlatformCert(associatedData, nonce, cipherText, platformCertPath);
                logger.info("平台证书序列号: {} serialNo: {}", platSerialNo, serialNo);
            }
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, platformCertPath);
            System.out.println("verifySignature:" + verifySignature);
            return body;
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return null;
        }
    }

    @RequestMapping(value = "/wechatNativePay", method = RequestMethod.POST)
    public CommonRes<WechatNativePayRes> wechatNativePay(PaymentReq req) {
        WxPayV3Vo wxPayV3Vo = configBaseService.getWxPayV3Vo();
        //weixin_app_id
        String appId = configBaseService.getConfig("wechat_app_id");
        wxPayV3Vo.setAppId(appId);

        req.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_WECHAT);

        PayOutput output = getPayResult(req);

        WechatNativePayRes wechatNativePayRes = new WechatNativePayRes();
        wechatNativePayRes.setOrderId(req.getOrderId());
        wechatNativePayRes.setPaymentAmount(Convert.toBigDecimal(output.getAmount()).divide(BigDecimal.valueOf(100)));

        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(appId)
                    .setMchid(wxPayV3Vo.getMchId())
                    .setDescription("在线购物")
                    .setOut_trade_no(output.getTradeNo())
                    .setTime_expire(timeExpire)
                    .setAttach(output.getTitle())
                    .setNotify_url(wxPayV3Vo.getDomain().concat("/front/pay/callback/wechatNotify"))
                    .setAmount(new Amount().setTotal(output.getAmount()))
                    .setPayer(new Payer().setOpenid(req.getOpenid()));

            logger.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.NATIVE_PAY.toString(),
                    wxPayV3Vo.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Vo.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );

            logger.info("统一下单响应 {}", response);

            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Vo.getPlatformCertPath());

            logger.info("verifySignature: {}", verifySignature);

            if (response.getStatus() == 200 && verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String codeUrl = jsonObject.getStr("code_url");
                wechatNativePayRes.setCodeUrl(codeUrl);

                return success(wechatNativePayRes);
            } else {
                LogUtil.error(ConstantLog.PAY, JSONUtil.toJsonStr(response));
            }

            wechatNativePayRes.setResponse(JSONUtil.toJsonStr(response));
            return fail(wechatNativePayRes);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return fail(wechatNativePayRes);
        }
    }

    @RequestMapping(value = "/wechatAppletPay", method = RequestMethod.POST)
    public CommonRes<WechatJSAPIPayRes> wechatAppletPay(PaymentReq req) {
        WxPayV3Vo wxPayV3Vo = configBaseService.getWxPayV3Vo();
        //weixin_app_id
        String appId = configBaseService.getConfig("wechat_xcx_app_id");
        wxPayV3Vo.setAppId(appId);

        req.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_WECHAT);

        PaymentInput paymentInput = BeanUtil.copyProperties(req, PaymentInput.class);
        paymentInput.setOrderId(Convert.toList(String.class, req.getOrderId()));

        List<ConsumeTrade> consumeTrades = checkTrade(paymentInput);
        //订单id
        //是否为联合支付
        String tradeNo = combineRepository.getTradeNo(paymentInput.getOrderId());

        //标题
        List<String> titles = consumeTrades.stream().map(ConsumeTrade::getTradeTitle).collect(Collectors.toList());
        String title = CollUtil.join(titles, ",");

        //付款金额
        BigDecimal tradePaymentAmount = consumeTrades.stream().map(ConsumeTrade::getTradePaymentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        WechatJSAPIPayRes wechatJSAPIPayRes = new WechatJSAPIPayRes();

        if (tradePaymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            wechatJSAPIPayRes.setPaid(true);
            //throw new BusinessException(__("无需支付！"));
        }

        wechatJSAPIPayRes.setOrderId(req.getOrderId());

        // 设置订单金额 单位为分且最小为1
        int amount = NumberUtil.mul(NumberUtil.round(tradePaymentAmount, 2), 100).intValue();

        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(appId)
                    .setMchid(wxPayV3Vo.getMchId())
                    .setDescription(__("在线购物"))
                    .setOut_trade_no(tradeNo)
                    .setTime_expire(timeExpire)
                    .setAttach(title)
                    .setNotify_url(wxPayV3Vo.getDomain().concat("/front/pay/callback/wechatNotify"))
                    .setAmount(new Amount().setTotal(amount))
                    .setPayer(new Payer().setOpenid(req.getOpenid()));

            logger.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.JS_API_PAY.toString(),
                    wxPayV3Vo.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Vo.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );

            logger.info("统一下单响应 {}", response);

            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Vo.getPlatformCertPath());

            logger.info("verifySignature: {}", verifySignature);

            if (response.getStatus() == 200 && verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                Map<String, String> map = WxPayKit.jsApiCreateSign(appId, prepayId, wxPayV3Vo.getKeyPath());
                wechatJSAPIPayRes.setData(map);

                return success(wechatJSAPIPayRes);
            } else {
                LogUtil.error(ConstantLog.PAY, JSONUtil.toJsonStr(response));
            }

            wechatJSAPIPayRes.setResponse(JSONUtil.toJsonStr(response));
            return fail(wechatJSAPIPayRes);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return fail(wechatJSAPIPayRes);
        }
    }

    @RequestMapping(value = "/wechatAppPay", method = RequestMethod.POST)
    public CommonRes<WechatAppPayRes> wechatAppPay(PaymentReq req) {
        req.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_WECHAT);

        PayOutput output = getPayResult(req);

        //weixin_app_id
        String appId = configBaseService.getConfig("weixin_app_id");

        //商户编号
        String mchid = configBaseService.getConfig("wechat_pay_mchid");

        //密钥keyPath
        String keyPath = configBaseService.getConfig("wechat_pay_apiclient_key");

        //domain 项目域名
        String domain = ConstantConfig.URL_BASE;

        //微信平台证书
        File file = new File(getUploadFileDir(), "wx_cert.pem");
        String platformCertPath = file.getAbsolutePath();

        //App支付
        WechatAppPayRes wechatAppPayRes = new WechatAppPayRes();
        wechatAppPayRes.setOrderId(req.getOrderId());

        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(appId)
                    .setMchid(mchid)
                    .setDescription("在线购物")
                    .setOut_trade_no(output.getTradeNo())
                    .setTime_expire(timeExpire)
                    .setAttach(output.getTitle())
                    .setNotify_url(domain.concat("/front/pay/callback/wechatNotify"))
                    .setAmount(new Amount().setTotal(output.getAmount()));
            logger.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.NATIVE_PAY.toString(),
                    mchid,
                    getSerialNumber(),
                    null,
                    keyPath,
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );
            logger.info("统一下单响应 {}", response);
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, platformCertPath);
            logger.info("verifySignature: {}", verifySignature);
            if (response.getStatus() == 200 && verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                Map<String, String> map = WxPayKit.appCreateSign(appId, mchid, prepayId, keyPath);
                wechatAppPayRes.setData(map);
                logger.info("唤起支付参数:{}", map);
                return success(wechatAppPayRes);
            } else {
                LogUtil.error(ConstantLog.PAY, JSONUtil.toJsonStr(response));
            }

            wechatAppPayRes.setResponse(JSONUtil.toJsonStr(response));
            return fail(wechatAppPayRes);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return fail(wechatAppPayRes);
        }
    }

    private List<ConsumeTrade> checkTrade(PaymentInput input) {
        QueryWrapper<ConsumeTrade> tradeQueryWrapper = new QueryWrapper<>();
        tradeQueryWrapper.in("order_id", input.getOrderId());
        List<ConsumeTrade> consumeTrades = tradeRepository.find(tradeQueryWrapper);

        if (CollectionUtil.isEmpty(consumeTrades)) {
            throw new BusinessException(__("交易订单不存在"));
        }

        //判断是否可以联合支付

        //是否包含已付款
        List<Integer> isPaidList = consumeTrades.stream().map(ConsumeTrade::getTradeIsPaid).collect(Collectors.toList());
        if (isPaidList.contains(StateCode.ORDER_PAID_STATE_YES)) {
            throw new BusinessException(__("存在订单状态不为待付款状态！"));
        }

        return consumeTrades;
    }

    /**
     * 获取证书序列号
     */
    private String getSerialNumber() {
        //证书序列号
        String serialNo = configBaseService.getConfig("wechat_pay_serial_no");

        if (StrUtil.isEmpty(serialNo)) {
            //这个是证书文件，后续要调整成读取证书文件的服务器存放地址  微信支付证书
            String cert = configBaseService.getConfig("wechat_pay_apiclient_cert");

            if (cert != null) {
                // 获取证书序列号
                X509Certificate certificate = PayKit.getCertificate(cert);
                if (certificate != null) {
                    serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
                }
            }
        }

        return serialNo;
    }

    @RequestMapping(value = "/wechatJSAPIPay", method = RequestMethod.POST)
    public CommonRes<WechatJSAPIPayRes> wechatJSAPIPay(PaymentReq req) {
        WxPayV3Vo wxPayV3Vo = configBaseService.getWxPayV3Vo();
        //weixin_app_id
        String appId = configBaseService.getConfig("wechat_app_id");
        wxPayV3Vo.setAppId(appId);

        req.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_WECHAT);

        PayOutput output = getPayResult(req);

        Integer userId = ContextUtil.getLoginUserId();
        QueryWrapper<UserBindConnect> connectQueryWrapper = new QueryWrapper<>();
        connectQueryWrapper.eq("user_id", userId).eq("bind_type", BindConnectCode.WEIXIN);
        UserBindConnect bindConnect = userBindConnectRepository.findOne(connectQueryWrapper);
        if (bindConnect == null || StrUtil.isEmpty(bindConnect.getBindOpenid())) {
            throw new BusinessException(__("当前账号非微信渠道注册！"));
        }

        req.setOpenid(bindConnect.getBindOpenid());

        WechatJSAPIPayRes wechatJSAPIPayRes = new WechatJSAPIPayRes();
        wechatJSAPIPayRes.setOrderId(req.getOrderId());

        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(appId)
                    .setMchid(wxPayV3Vo.getMchId())
                    .setDescription("在线购物")
                    .setOut_trade_no(output.getTradeNo())
                    .setTime_expire(timeExpire)
                    .setAttach(output.getTitle())
                    .setNotify_url(wxPayV3Vo.getDomain().concat("/front/pay/callback/wechatNotify"))
                    .setAmount(new Amount().setTotal(output.getAmount()))
                    .setPayer(new Payer().setOpenid(req.getOpenid()));

            logger.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.JS_API_PAY.toString(),
                    wxPayV3Vo.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Vo.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );

            logger.info("统一下单响应 {}", response);

            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Vo.getPlatformCertPath());

            logger.info("verifySignature: {}", verifySignature);

            if (response.getStatus() == 200 && verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                Map<String, String> map = WxPayKit.jsApiCreateSign(appId, prepayId, wxPayV3Vo.getKeyPath());
                wechatJSAPIPayRes.setData(map);

                return success(wechatJSAPIPayRes);
            } else {
                LogUtil.error(ConstantLog.PAY, JSONUtil.toJsonStr(response));
            }

            wechatJSAPIPayRes.setResponse(JSONUtil.toJsonStr(response));
            return fail(wechatJSAPIPayRes);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return fail(wechatJSAPIPayRes);
        }
    }


    @RequestMapping(value = "/wechatH5Pay", method = RequestMethod.POST)
    public CommonRes<WechatJSAPIPayRes> wechatH5Pay(PaymentReq req) {
        WxPayV3Vo wxPayV3Vo = configBaseService.getWxPayV3Vo();
        //weixin_app_id
        String appId = configBaseService.getConfig("wechat_app_id");
        wxPayV3Vo.setAppId(appId);

        req.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_WECHAT);

        PayOutput output = getPayResult(req);

        WechatJSAPIPayRes wechatJSAPIPayRes = new WechatJSAPIPayRes();
        wechatJSAPIPayRes.setOrderId(req.getOrderId());

        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);

            H5Info h5Info = new H5Info();
            h5Info.setType("Wap");
            h5Info.setApp_url(ConstantConfig.URL_BASE);
            h5Info.setApp_name("随商商城");
            SceneInfo sceneInfo = new SceneInfo();
            sceneInfo.setPayer_client_ip(HttpServletUtils.getClientIpAddr());
            sceneInfo.setH5_info(h5Info);

            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(appId)
                    .setMchid(wxPayV3Vo.getMchId())
                    .setDescription("在线购物")
                    .setOut_trade_no(output.getTradeNo())
                    .setTime_expire(timeExpire)
                    .setAttach(output.getTitle())
                    .setNotify_url(wxPayV3Vo.getDomain().concat("/front/pay/callback/wechatNotify"))
                    .setAmount(new Amount().setTotal(output.getAmount()))
                    .setScene_info(sceneInfo)
                    .setPayer(new Payer().setOpenid(req.getOpenid()));

            logger.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.H5_PAY.toString(),
                    wxPayV3Vo.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Vo.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );

            logger.info("统一下单响应 {}", response);

            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Vo.getPlatformCertPath());

            logger.info("verifySignature: {}", verifySignature);

            if (response.getStatus() == 200 && verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String mwebUrl = jsonObject.getStr("h5_url");
                String prepayId = jsonObject.getStr("prepay_id");
                Map<String, String> map = WxPayKit.jsApiCreateSign(appId, prepayId, wxPayV3Vo.getKeyPath());
                wechatJSAPIPayRes.setMwebUrl(mwebUrl);
                wechatJSAPIPayRes.setData(map);

                return success(wechatJSAPIPayRes);
            } else {
                LogUtil.error(ConstantLog.PAY, JSONUtil.toJsonStr(response));
            }

            wechatJSAPIPayRes.setResponse(JSONUtil.toJsonStr(response));
            return fail(wechatJSAPIPayRes);
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
            return fail(wechatJSAPIPayRes);
        }
    }

    @RequestMapping(value = "/alipayPay", method = RequestMethod.POST)
    public CommonRes<AliPayRes> alipayPay(PaymentReq req) {
        return getAliPayResCommonRes(req, PayType.ALIPAY_H5);
    }

    @RequestMapping(value = "/alipayPcPay", method = RequestMethod.POST)
    public CommonRes<AliPayRes> alipayPcPay(PaymentReq req) {
        return getAliPayResCommonRes(req, PayType.ALI_PC_PAY);
    }

    /**
     * 获取支付宝支付返回信息
     *
     * @param req
     * @param payType
     * @return
     */
    private CommonRes<AliPayRes> getAliPayResCommonRes(PaymentReq req, PayType payType) {
        AliPayVo aliPayVo = configBaseService.getAliPayVo();

        req.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_ALIPAY);

        PayOutput output = getPayResult(req);

        String returnUrl = aliPayVo.getReturnUrl();
        String notifyUrl = aliPayVo.getNotifyUrl();

        String body = ObjectUtil.defaultIfBlank(output.getTitle(), output.getTradeNo());

        try {
            AlipayTradeWapPayModel parameter = new AlipayTradeWapPayModel();
            parameter.setSubject(output.getTitle());
            parameter.setOutTradeNo(output.getTradeNo());
            parameter.setBody(body);
            BigDecimal amount = NumberUtil.div(new BigDecimal(output.getAmount()), new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            parameter.setTotalAmount(amount.toString());
            parameter.setProductCode("FAST_INSTANT_TRADE_PAY");

            AlipayRequest<?> aliPayRequest;
            if (payType.equals(PayType.ALIPAY_H5)) {
                aliPayRequest = new AlipayTradeWapPayRequest();
            } else if (payType.equals(PayType.ALI_PC_PAY)) {
                aliPayRequest = new AlipayTradePagePayRequest();
            } else {
                throw new BusinessException(ResultCode.VALIDATE_FAILED);
            }

            aliPayRequest.setReturnUrl(returnUrl + "?out_trade_no=" + output.getTradeNo());
            aliPayRequest.setNotifyUrl(notifyUrl);
            aliPayRequest.setBizModel(parameter);
            String mwebUrl = AliPayApi.pageExecute(aliPayRequest, "GET").getBody();

            AliPayRes res = new AliPayRes();
            res.setMwebUrl(mwebUrl);
            res.setOrderId(output.getTradeNo());
            res.setStatusCode(200);
            res.setPaid(false);
            return success(res);
        } catch (Exception e) {
            LogUtil.error(ErrorTypeEnum.ERR_ALI_PAY.getValue(), e);
            throw new BusinessException(__("支付宝支付失败！"));
        }
    }

    /**
     * 获取业务返回数据
     *
     * @param req
     * @return
     */
    @NotNull
    private PayOutput getPayResult(PaymentReq req) {
        PaymentInput paymentInput = BeanUtil.copyProperties(req, PaymentInput.class);
        paymentInput.setOrderId(Convert.toList(String.class, req.getOrderId()));

        List<ConsumeTrade> consumeTrades = checkTrade(paymentInput);
        //订单id
        //是否为联合支付
        String tradeNo = combineRepository.getTradeNo(paymentInput.getOrderId());

        //标题
        List<String> titles = consumeTrades.stream().map(ConsumeTrade::getTradeTitle).collect(Collectors.toList());
        String title = CollUtil.join(titles, ",");

        //付款金额
        BigDecimal tradePaymentAmount = consumeTrades.stream().map(ConsumeTrade::getTradePaymentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (tradePaymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(__("无需支付！"));
        }
        // 设置订单金额 单位为分且最小为1
        int amount = NumberUtil.mul(NumberUtil.round(tradePaymentAmount, 2), 100).intValue();
        return new PayOutput(tradeNo, title, amount);
    }

}

