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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.CrontabBase;
import com.suisung.shopsuite.sys.model.req.CrontabBaseListReq;
import com.suisung.shopsuite.sys.quartz.service.QuartzService;
import com.suisung.shopsuite.sys.repository.CrontabBaseRepository;
import com.suisung.shopsuite.sys.service.CrontabBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 计划任务表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Service
public class CrontabBaseServiceImpl extends BaseServiceImpl<CrontabBaseRepository, CrontabBase, CrontabBaseListReq> implements CrontabBaseService {

    @Autowired
    private QuartzService quartzService;

    private final String JOB_NAME_PREFIX = "JOB_";
    private final String TRIGGER_NAME_PREFIX = "TRIGGER_";
    private final String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP";
    private final String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER_GROUP";

    @Transactional
    @Override
    public boolean updateCrontab(CrontabBase crontabBase) {

        Boolean crontabEnable = crontabBase.getCrontabEnable();
        Integer crontab_id = crontabBase.getCrontabId();

        // 保存
        if (CheckUtil.isNotEmpty(crontab_id)) {
            if (!add(crontabBase)) {
                throw new BusinessException(ResultCode.FAILED);
            }

            if (crontabEnable) {
                addJob(crontabBase);
            }
        } else {
            // 修改/判断是否改变定时任务状态
            // 开启/关闭定时任务
            if (!edit(crontabBase)) {
                throw new BusinessException(ResultCode.FAILED);
            }

            quartzService.deleteJob(JOB_NAME_PREFIX + crontab_id, JOB_GROUP_NAME);
            if (crontabEnable) {
                addJob(crontabBase);
            }
        }

        return true;
    }

    @Override
    @Transactional
    public boolean removeCrontab(String crontabId) {
        boolean flag = remove(crontabId);
        if (flag) {
            quartzService.deleteJob(JOB_NAME_PREFIX + crontabId, JOB_GROUP_NAME);
        }
        return flag;
    }

    /**
     * 添加定时任务
     *
     * @param crontabBase
     */
    @Override
    public void addJob(CrontabBase crontabBase) {
        String crontab_file = crontabBase.getCrontabFile();
        Integer crontab_id = crontabBase.getCrontabId();
        String cron = getCron(crontabBase);
        quartzService.addJob(JOB_NAME_PREFIX + crontab_id, JOB_GROUP_NAME, TRIGGER_NAME_PREFIX + crontab_id, TRIGGER_GROUP_NAME, cron, crontab_file);
    }

    /**
     * 获取cron 表达式
     *
     * @param crontabBase
     * @return
     */
    private String getCron(CrontabBase crontabBase) {
        String crontabMinute = crontabBase.getCrontabMinute();
        String crontabHour = crontabBase.getCrontabHour();
        String crontabDay = crontabBase.getCrontabDay();
        String crontabWeek = crontabBase.getCrontabWeek();
        String crontabMonth = crontabBase.getCrontabMonth();

        if (crontabDay != null && !StrUtil.equals("*", crontabDay) && crontabWeek != null && !StrUtil.equals("?", crontabWeek) || (ObjectUtil.equal(crontabDay, crontabWeek) && StrUtil.equals("*", crontabWeek))) {
            throw new BusinessException(__("每天和每周不能同时选择！"));
        }

        if (crontabWeek != null && !StrUtil.equals("?", crontabWeek) && crontabMonth != null && !StrUtil.equals("*", crontabMonth) || (ObjectUtil.equal(crontabWeek, crontabMonth) && StrUtil.equals("*", crontabMonth))) {
            throw new BusinessException(__("每周和每月不能同时选择！"));
        }

        StringBuilder cron = new StringBuilder();
        String space = " ";

        cron.append("0").append(space).append(crontabMinute).append(space).append(crontabHour).append(space).append(crontabDay).append(space).append(crontabMonth).append(space).append(crontabWeek);

        return cron.toString();
    }

}
