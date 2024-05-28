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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.pay.model.entity.ConsumeCombine;
import com.suisung.shopsuite.pay.model.entity.ConsumeDeposit;
import com.suisung.shopsuite.pay.model.entity.ConsumeTrade;
import com.suisung.shopsuite.pay.model.vo.WxPayV3Vo;
import com.suisung.shopsuite.pay.repository.ConsumeCombineRepository;
import com.suisung.shopsuite.pay.repository.ConsumeTradeRepository;
import com.suisung.shopsuite.pay.service.TradeTypeService;
import com.suisung.shopsuite.pay.service.impl.ConsumeDepositServiceImpl;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 交易类型表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Api(tags = "交易类型表")
@RestController
@RequestMapping("/front/pay/callback")
public class PaymentCallbackWechatController extends BaseController {
    @Autowired
    private TradeTypeService tradeTypeService;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ConsumeCombineRepository combineRepository;

    @Autowired
    private ConsumeDepositServiceImpl depositServiceImpl;

    @Autowired
    private ConsumeTradeRepository tradeRepository;

    private static final Logger logger = LoggerFactory.getLogger(PaymentCallbackWechatController.class);

    @ApiOperation(value = "微信支付回调", notes = "微信支付回调")
    @RequestMapping(value = "/wechatNotify", method = {RequestMethod.POST, RequestMethod.GET})
    public void wechatNotify(HttpServletRequest request, HttpServletResponse response) {
        WxPayV3Vo wxPayV3Vo = configBaseService.getWxPayV3Vo();

        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            logger.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            //支付通知密文
            String result = HttpKit.readData(request);
            logger.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);

            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            //支付通知明文
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wxPayV3Vo.getApiKey3(), wxPayV3Vo.getPlatformCertPath());
            logger.info("支付通知明文 {}", plainText);

            //具体的业务操作
            if (StrUtil.isNotEmpty(plainText)) {
                ConsumeDeposit consumeDeposit = new ConsumeDeposit();
                JSONObject jsonObject = JSONUtil.parseObj(plainText);
                //订单号
                String outTradeNo = jsonObject.getStr("out_trade_no");
                //订单编号
                String orderId = "";
                ConsumeCombine consumeCombine = combineRepository.get(outTradeNo);

                if (consumeCombine != null) {
                    orderId = consumeCombine.getOrderIds();
                } else {
                    orderId = outTradeNo;
                }

                JSONObject payer = jsonObject.getJSONObject("payer");
                JSONObject amount = jsonObject.getJSONObject("amount");


                consumeDeposit.setDepositNo(outTradeNo);
                consumeDeposit.setDepositTradeNo(jsonObject.getStr("transaction_id"));
                consumeDeposit.setOrderId(orderId);
                consumeDeposit.setDepositSubject(jsonObject.getStr("attach"));
                consumeDeposit.setDepositQuantity(Convert.toInt(jsonObject.getStr("quantity"), 0));
                consumeDeposit.setDepositNotifyTime(new Date().toString());
                consumeDeposit.setDepositSellerId(jsonObject.getStr("mchid"));
                consumeDeposit.setDepositIsTotalFeeAdjust(StrUtil.isNotEmpty(jsonObject.getStr("is_total_fee_adjust")));

                if (StrUtil.isNotEmpty(amount.getStr("total"))) {
                    consumeDeposit.setDepositTotalFee(NumberUtil.div(Convert.toBigDecimal(amount.getStr("total"), BigDecimal.ZERO), 100));
                }

                if (StrUtil.isNotEmpty(amount.getStr("total"))) {
                    consumeDeposit.setDepositPrice(NumberUtil.div(Convert.toBigDecimal(amount.getStr("total"), BigDecimal.ZERO), 100));
                }

                consumeDeposit.setDepositBuyerId(payer.getStr("openid"));
                consumeDeposit.setDepositTime(new Date().getTime());
                consumeDeposit.setDepositPaymentType(StateCode.PAYMENT_TYPE_ONLINE);
                consumeDeposit.setDepositService(jsonObject.getStr("trade_type"));
                consumeDeposit.setDepositSign(jsonObject.getStr("sign"));
                consumeDeposit.setDepositExtraParam(JSONUtil.toJsonStr(consumeDeposit));
                consumeDeposit.setPaymentChannelId(StateCode.PAYMENT_CHANNEL_WECHAT);
                consumeDeposit.setDepositTradeStatus(jsonObject.getStr("trade_state"));


                //判断是否可以联合支付
                Integer storeId = 0;
                Integer chainId = 0;
                ConfigBase tradeMode = configBaseService.get("trade_mode");

                if (tradeMode == null || !"1".equals(tradeMode.getConfigValue())) {
                    QueryWrapper<ConsumeTrade> consumeTradeQueryWrapper = new QueryWrapper<>();
                    consumeTradeQueryWrapper.eq("order_id", outTradeNo);
                    ConsumeTrade consumeTrade = tradeRepository.findOne(consumeTradeQueryWrapper);

                    if (consumeTrade != null) {
                        storeId = consumeTrade.getStoreId();
                        chainId = consumeTrade.getChainId();
                    }
                }
                consumeDeposit.setStoreId(storeId);
                consumeDeposit.setChainId(chainId);

                depositServiceImpl.processDeposit(consumeDeposit);
            }

            //回复微信
            if (StrUtil.isNotEmpty(plainText)) {
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            LogUtil.error(ConstantLog.PAY, e);
        }
    }


    @ApiOperation(value = "判断是否支付完成", notes = "判断是否支付完成")
    @RequestMapping(value = "/wechatCheck", method = RequestMethod.GET)
    public CommonRes<?> wechatCheck(@RequestParam("order_id") String orderId) {
        Integer userId = ContextUtil.getLoginUserId();

        QueryWrapper<ConsumeTrade> tradeQueryWrapper = new QueryWrapper<>();
        tradeQueryWrapper.eq("order_id", orderId);
        tradeQueryWrapper.eq("buyer_id", userId);

        ConsumeTrade payConsumeTrade = tradeRepository.findOne(tradeQueryWrapper);

        if (ObjectUtil.isNotEmpty(payConsumeTrade)) {
            Integer tradeIsPaid = payConsumeTrade.getTradeIsPaid();
            if (tradeIsPaid.equals(StateCode.ORDER_PAID_STATE_YES)) {
                return success();
            } else
                return fail();
        }

        return fail();
    }
}

