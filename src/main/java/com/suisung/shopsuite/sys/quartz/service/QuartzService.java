package com.suisung.shopsuite.sys.quartz.service;

import org.springframework.stereotype.Service;

@Service
public interface QuartzService {

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
    void addJob(String jName, String jGroup, String tName, String tGroup, String cron, String cName);

    /**
     * 暂停定时任务
     *
     * @param jName  任务名
     * @param jGroup 任务组
     */
    void pauseJob(String jName, String jGroup);

    /**
     * 继续定时任务
     *
     * @param jName  任务名
     * @param jGroup 任务组
     */
    void resumeJob(String jName, String jGroup);

    /**
     * 删除定时任务
     *
     * @param jName  任务名
     * @param jGroup 任务组
     */
    void deleteJob(String jName, String jGroup);

}
