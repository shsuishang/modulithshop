package com.suisung.shopsuite.common.config;

import com.suisung.shopsuite.core.config.CoreStartInit;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ShopsuiteInit implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(CoreStartInit.class);

    @Autowired
    ConfigBaseService configBaseService;

    @Override
    @Async
    public void run(String... args) throws Exception {
        log.info("ShopsuiteInit...");
        configBaseService.init();
    }
}
