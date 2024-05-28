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
package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.dao.NumberSeqDao;
import com.suisung.shopsuite.sys.model.entity.NumberSeq;
import com.suisung.shopsuite.sys.model.req.NumberSeqListReq;
import com.suisung.shopsuite.sys.repository.NumberSeqRepository;
import com.suisung.shopsuite.sys.service.NumberSeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 编号管理表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Service
public class NumberSeqServiceImpl extends BaseServiceImpl<NumberSeqRepository, NumberSeq, NumberSeqListReq> implements NumberSeqService {
    @Autowired
    private NumberSeqDao numberSeqDao;

    /**
     * 得到下一个Id
     * 方法走到这里会产生串行化，集群部署这里不能使用单机锁
     *
     * @param prefix
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public synchronized String getNextSeqString(String prefix) {
        String ymd = DateUtil.format(new Date(), "yyyyMMdd");
        prefix = String.format("%s-%s", prefix, ymd);

        Long no = getNextSeqInt(prefix);

        return String.format("%s-%d", prefix, no);
    }

    // GetNextSeqInt 得到下一个编号
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public synchronized Long getNextSeqInt(String prefix) {
        Long number = 0l;

        NumberSeq numberSeq = numberSeqDao.get(prefix);

        if (numberSeq == null) {
            numberSeq = new NumberSeq();
            numberSeq.setPrefix(prefix);
            numberSeq.setNumber(0L);

            if (!add(numberSeq)) {
                throw new BusinessException(__("生成自增长编号错误"));
            }
        } else {
            number = numberSeq.getNumber();
        }

        boolean flag = numberSeqDao.increment(prefix, 1);

        if (!flag) {
            throw new BusinessException(__("生成自增长编号错误"));
        }

        return number + 1;
    }


}
