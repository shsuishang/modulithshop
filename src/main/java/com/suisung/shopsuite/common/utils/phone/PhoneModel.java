package com.suisung.shopsuite.common.utils.phone;

import lombok.Data;

@Data
public class PhoneModel {

    /**
     * 国家名称
     */
    private String countryName;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 运营商：移动/电信/联通
     */
    private String carrier;

    /**
     * 国家代码
     */
    private int countryCode;

    /**
     * 国家代码
     */
    private String countryCodeStr;

    /**
     * 号码
     */
    private long nationalNumber;

}

