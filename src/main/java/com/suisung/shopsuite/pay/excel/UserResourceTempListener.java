package com.suisung.shopsuite.pay.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.getui.push.v2.sdk.common.ApiException;
import com.suisung.shopsuite.common.utils.SpringUtil;
import com.suisung.shopsuite.pay.model.entity.UserResource;
import com.suisung.shopsuite.pay.service.UserResourceService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;


public class UserResourceTempListener extends AnalysisEventListener<UserResourceTemp> {

    private static final int BATCH_COUNT = 1000;    // 每一千条数据保存一次数据

    private ArrayList<UserResource> list = new ArrayList<>();

    public static UserResourceService userResourceService;

    static {
        initDictionary();
    }

    static void initDictionary() {
        userResourceService = SpringUtil.getBean(UserResourceService.class);
    }


    /**
     * 每解析一行，回调该方
     *
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(UserResourceTemp data, AnalysisContext analysisContext) {
        // 解析数据
        UserResource userResource = new UserResource();

        Integer user_id = data.getUser_id();
        userResource.setUserId(user_id);

        BigDecimal user_money = data.getUser_money();
        userResource.setUserMoney(user_money);

        BigDecimal user_points = data.getUser_points();
        userResource.setUserPoints(user_points);

        /*BigDecimal user_recharge_card = data.getUser_recharge_card();
        payUserResource.setUser_recharge_card(user_recharge_card);*/

        list.add(userResource);
        // 每一千条数据保存一次
        if (list.size() >= BATCH_COUNT) {
            // 保存数据
            for (UserResource userResourcei: list) {
                if (!userResourceService.editResource(userResourcei)) {
                    throw new ApiException(__("导入用户资源数据失败！"));
                }
            }
            // 清除数据
            list.clear();
        }
    }

    /**
     * 解析完回调
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size() == 0) return;
        for (UserResource userResource : list) {
            if (!userResourceService.editResource(userResource)) {
                throw new ApiException(__("导入用户数据失败！"));
            }
        }
    }
}
