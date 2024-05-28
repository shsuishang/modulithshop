package com.suisung.shopsuite.sys.quartz.service.impl;

import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.sys.quartz.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    private final String PATH_PREFIX = "com.suisung.shopsuite.sys.job.";

    /**
     * 新增一个定时任务
     *
     * @param jName  任务名称
     * @param jGroup 任务组
     * @param tName  触发器名称
     * @param tGroup 触发器组
     * @param cName  任务实现类名称(类名称，不带class 后缀)
     * @param cron   cron表达式
     */
    @Override
    public void addJob(String jName, String jGroup, String tName, String tGroup, String cron, String cName) {

        Class<Job> clazz = null;
        try {
            clazz = (Class<Job>) Class.forName(PATH_PREFIX + cName);
        } catch (ClassNotFoundException e) {
            throw new BusinessException(__("定时任务脚本不存在！"));

        }

        try {
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jName, jGroup).build();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(tName, tGroup).startNow().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new BusinessException(__("添加定时任务失败！"));
        }
    }

    /**
     * 暂停定时任务
     *
     * @param jName  任务名
     * @param jGroup 任务组
     */
    @Override
    public void pauseJob(String jName, String jGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jName, jGroup));
        } catch (Exception e) {
            throw new BusinessException(__("暂停定时任务失败！"));
        }
    }

    /**
     * 继续定时任务
     *
     * @param jName  任务名
     * @param jGroup 任务组
     */
    @Override
    public void resumeJob(String jName, String jGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            throw new BusinessException(__("继续定时任务失败！"));
        }
    }

    /**
     * 删除定时任务
     *
     * @param jName  任务名
     * @param jGroup 任务组
     */
    @Override
    public void deleteJob(String jName, String jGroup) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            throw new BusinessException(__("删除定时任务失败！"));
        }
    }

}
