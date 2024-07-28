package com.suisung.shopsuite.sys.job;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.SpringUtil;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import com.suisung.shopsuite.sys.model.entity.CrontabBase;
import com.suisung.shopsuite.sys.service.CrontabBaseService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

public class UpdateActivityStatusJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        ActivityBaseRepository activityBaseRepository = SpringUtil.getBean(ActivityBaseRepository.class);
        ActivityBaseService activityBaseService = SpringUtil.getBean(ActivityBaseService.class);

        long time = new Date().getTime();

        CrontabBase crontabBase = new CrontabBase();
        crontabBase.setCrontabId(1002);
        crontabBase.setCrontabLastExeTime(time);
        CrontabBaseService crontabBaseService = SpringUtil.getBean(CrontabBaseService.class);
        crontabBaseService.edit(crontabBase);

        try {
            // 更新活动状态
            QueryWrapper<ActivityBase> baseQueryWrapper = new QueryWrapper<>();
            baseQueryWrapper.eq("activity_state", StateCode.ACTIVITY_STATE_WAITING).lt("activity_starttime", time).ge("activity_endtime", time);
            List<ActivityBase> activityBases = activityBaseRepository.lists(baseQueryWrapper, 1, 20).getRecords();

            if (CollUtil.isNotEmpty(activityBases)) {
                for (ActivityBase activityBase : activityBases) {
                    activityBase.setActivityState(StateCode.ACTIVITY_STATE_NORMAL);
                    activityBaseService.editActivityBase(activityBase.getActivityId(), activityBase);
                }
            }

            QueryWrapper<ActivityBase> endQueryWrapper = new QueryWrapper<>();
            endQueryWrapper.eq("activity_state", StateCode.ACTIVITY_STATE_NORMAL).le("activity_endtime", time);
            List<ActivityBase> endActivityBases = activityBaseRepository.lists(endQueryWrapper, 1, 20).getRecords();

            if (CollUtil.isNotEmpty(endActivityBases)) {
                for (ActivityBase activityBase : endActivityBases) {
                    activityBase.setActivityState(StateCode.ACTIVITY_STATE_FINISHED);
                    activityBaseService.editActivityBase(activityBase.getActivityId(), activityBase);
                }
            }

        } catch (Exception e) {
            LogUtil.error(ConstantLog.TASK, e);
        }
    }
}
