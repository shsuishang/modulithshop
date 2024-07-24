package com.suisung.shopsuite.common.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.sys.model.entity.CrontabBase;
import com.suisung.shopsuite.sys.repository.CrontabBaseRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.CrontabBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartupRunner.class);


    @Autowired
    private CrontabBaseRepository crontabBaseRepository;

    @Autowired
    private CrontabBaseService crontabBaseService;

    @Autowired
    private ConfigBaseService configBaseService;

    @Override
    @Async
    public void run(String... args) {
        log.info(__("初始化Redis缓存开始..."));
        configBaseService.cleanCache();

        log.info(__("初始化定时任务开始..."));

        QueryWrapper<CrontabBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("crontab_enable", 1);
        List<CrontabBase> baseCrontabs = crontabBaseRepository.find(queryWrapper);
        for (CrontabBase baseCrontab : baseCrontabs) {
            try {
                crontabBaseService.addJob(baseCrontab);
            } catch (Exception e) {
                log.error("错误信息：", e);
            }
        }

        log.info(__("初始化定时任务结束..."));
    }
}
