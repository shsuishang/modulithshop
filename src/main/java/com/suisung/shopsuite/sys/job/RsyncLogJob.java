package com.suisung.shopsuite.sys.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.admin.service.MenuBaseService;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.consts.ConstantLog;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.utils.LogUtil;
import com.suisung.shopsuite.common.utils.SpringUtil;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.pt.model.entity.ProductIndex;
import com.suisung.shopsuite.pt.model.entity.ProductItem;
import com.suisung.shopsuite.pt.repository.ProductIndexRepository;
import com.suisung.shopsuite.pt.repository.ProductItemRepository;
import com.suisung.shopsuite.sys.model.entity.AccessHistory;
import com.suisung.shopsuite.sys.model.entity.CrontabBase;
import com.suisung.shopsuite.sys.model.entity.LogAction;
import com.suisung.shopsuite.sys.repository.AccessHistoryRepository;
import com.suisung.shopsuite.sys.repository.LogActionRepository;
import com.suisung.shopsuite.sys.service.CrontabBaseService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

public class RsyncLogJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        Logger logger = LoggerFactory.getLogger(RsyncLogJob.class);
        MessageService messageService = SpringUtil.getBean(MessageService.class);
        ProductItemRepository productItemRepository = SpringUtil.getBean(ProductItemRepository.class);
        UserInfoRepository userInfoRepository = SpringUtil.getBean(UserInfoRepository.class);
        LogActionRepository logActionRepository = SpringUtil.getBean(LogActionRepository.class);
        AccessHistoryRepository accessHistoryRepository = SpringUtil.getBean(AccessHistoryRepository.class);
        ProductIndexRepository productIndexRepository = SpringUtil.getBean(ProductIndexRepository.class);
        MenuBaseService menuBaseService = SpringUtil.getBean(MenuBaseService.class);

        Date now = new Date();
        CrontabBase crontabBase = new CrontabBase();
        crontabBase.setCrontabId(1006);
        crontabBase.setCrontabLastExeTime(now.getTime());
        CrontabBaseService crontabBaseService = SpringUtil.getBean(CrontabBaseService.class);
        crontabBaseService.edit(crontabBase);

        try {
            int times = 10000;
            HashMap<Long, Integer> product_map = new HashMap<>();
            HashMap<Integer, Integer> store_map = new HashMap<>();

            Calendar c = Calendar.getInstance();
            Date curDate = new Date();
            long time = curDate.getTime();

            //如果队列有数据，一直读完
            while (times > 0) {
                String data = messageService.receiveAccess();

                if (null == data) break;

                AccessHistory queue_row = JSONUtil.parseObject(data, AccessHistory.class);
                assert queue_row != null;
                queue_row.setAccessData(JSONUtil.toJSONString(queue_row.getAccessReq()));

                Date date = new Date(queue_row.getAccessTime());
                c.setTime(date);

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);

                queue_row.setAccessYear(year);
                queue_row.setAccessMonth(month);
                queue_row.setAccessDay(day);
                queue_row.setAccessHour(hour);

                queue_row.setAccessDate(date);
                queue_row.setAccessDatetime(date);

                Map<String, String> access_req = queue_row.getAccessReq();

                queue_row.setAccessType(Convert.toInt(access_req.getOrDefault("access_type", String.valueOf(StateCode.SOURCE_TYPE_OTHER))));
                queue_row.setAccessFrom(Convert.toInt(access_req.getOrDefault("access_from", String.valueOf(StateCode.SOURCE_FROM_OTHER))));

                //商品访问量
                if (queue_row.getAccessUrl().equals("/front/pt/product/detail")) {
                    Long item_id = Convert.toLong(access_req.get("item_id"));
                    ProductItem productItem = productItemRepository.get(item_id);

                    if (item_id != null && productItem != null) {
                        queue_row.setItemId(item_id);
                        Long pid = productItem.getProductId();
                        product_map.put(pid, product_map.containsKey(pid) ? product_map.get(pid) + 1 : 1);
                    }
                }

                //店铺访问量
                if (queue_row.getAccessUrl().equals("/mobile/shop/store/info")) {
                    Integer store_id = Convert.toInt(access_req.get("store_id"));

                    if (CheckUtil.isNotEmpty(store_id))
                        store_map.put(store_id, store_map.containsKey(store_id) ? store_map.get(store_id) + 1 : 1);
                }

                // 访问日志
                if (queue_row.getAccessUrl().startsWith("/manage") && queue_row.getAccessMethod().equals("POST")) {
                    LogAction logAction = new LogAction();
                    BeanUtil.copyProperties(queue_row, logAction);

                    Integer user_id = logAction.getUserId();
                    UserInfo userInfo = userInfoRepository.get(user_id);

                    if (userInfo != null) {
                        logAction.setUserAccount(userInfo.getUserAccount());
                        logAction.setUserName(userInfo.getUserNickname());
                        logAction.setLogUrl(queue_row.getAccessUrl());
                        logAction.setLogMethod(queue_row.getAccessMethod());

                        String accessData = queue_row.getAccessData();
                        accessData = accessData.substring(0, Math.min(accessData.length(), 2000));

                        logAction.setLogParam(accessData);
                        logAction.setLogIp(queue_row.getAccessIp());
                        logAction.setLogDate(queue_row.getAccessDate());
                        logAction.setLogTime(curDate.getTime());

                        Map<String, String> menuMap = menuBaseService.getMenuMap();
                        String actionName = ObjectUtil.defaultIfNull(menuMap.get(queue_row.getAccessUrl()), "");
                        logAction.setLogName(actionName);

                        // 空字段
                        logAction.setActionTypeId(0);
                        logActionRepository.add(logAction);
                    }
                } else {
                    //
                    String scId = String.format("%s%s%s%s%s", queue_row.getAccessOs(), queue_row.getAccessBrowserName(), queue_row.getAccessBrowserVersion(), queue_row.getAccessLang(), queue_row.getAccessIp());
                    queue_row.setAccessClientId(SecureUtil.md5(scId));
                    accessHistoryRepository.add(queue_row);
                }

                times--;
                Thread.sleep(1);    //延时0.001s
            }

            if (ObjectUtil.isNotEmpty(product_map)) {
                List<Long> productIds = new ArrayList<>(product_map.keySet());
                if (CollUtil.isNotEmpty(productIds)) {
                    List<ProductIndex> productIndices = productIndexRepository.gets(productIds);
                    for (Long productId : product_map.keySet()) {
                        for (ProductIndex productIndex : productIndices) {
                            productIndex.setProductClick(productIndex.getProductClick() + product_map.get(productId));
                        }
                    }
                    productIndexRepository.saveOrUpdate(productIndices);
                }
            }

            logger.info("后台执行 日志队列  - 完成");

        } catch (Exception e) {
            LogUtil.error(ConstantLog.TASK, e);
        }

    }
}
