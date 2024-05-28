package com.suisung.shopsuite.trade.model.vo;

import com.suisung.shopsuite.shop.model.entity.StoreAnalytics;
import lombok.Data;

import java.io.Serializable;

@Data
public class StoreAnalyticsVo extends StoreAnalytics implements Serializable {

    private StoreCreditVo storeCredit;

}
