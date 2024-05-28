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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.pay.model.entity.ConsumeRecord;
import com.suisung.shopsuite.pay.model.req.ConsumeRecordListReq;
import com.suisung.shopsuite.pay.service.ConsumeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 * 交易明细表-账户收支明细-资金流水表-账户金额变化流水 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Api(tags = "账户收支明细")
@RestController
@RequestMapping("/front/pay/consumeRecord")
public class ConsumeController extends BaseController {
    @Autowired
    private ConsumeRecordService consumeRecordService;

    @ApiOperation(value = "交易明细表", notes = "交易明细表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes<BaseListRes<ConsumeRecord>> list(ConsumeRecordListReq consumeRecordListReq) {
        Integer userId = ContextUtil.checkLoginUserId();
        consumeRecordListReq.setUserId(userId);

        if (CheckUtil.isNotEmpty(consumeRecordListReq.getChangeType())) {
            if (consumeRecordListReq.getChangeType() == 1) {
                consumeRecordListReq.setTradeTypeId(CollUtil.join(Arrays.asList(StateCode.TRADE_TYPE_SHOPPING,
                        StateCode.TRADE_TYPE_TRANSFER,
                        StateCode.TRADE_TYPE_WITHDRAW,
                        StateCode.TRADE_TYPE_REFUND_PAY,
                        StateCode.TRADE_TYPE_COMMISSION_TRANSFER), ","));
            }

            if (consumeRecordListReq.getChangeType() == 2) {
                consumeRecordListReq.setTradeTypeId(CollUtil.join(Arrays.asList(
                        StateCode.TRADE_TYPE_DEPOSIT,
                        StateCode.TRADE_TYPE_SALES,
                        StateCode.TRADE_TYPE_COMMISSION,
                        StateCode.TRADE_TYPE_REFUND_GATHERING,
                        StateCode.TRADE_TYPE_TRANSFER_GATHERING
                ), ","));
            }
        }

        IPage<ConsumeRecord> pageList = consumeRecordService.lists(consumeRecordListReq);

        return success(pageList);
    }
}

