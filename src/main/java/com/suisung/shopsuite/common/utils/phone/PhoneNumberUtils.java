package com.suisung.shopsuite.common.utils.phone;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;

public class PhoneNumberUtils {
    /**
     * 直辖市
     */
    private final static String[] MUNICIPALITY = {"北京市", "天津市", "上海市", "重庆市"};

    /**
     * 自治区
     */
    private final static String[] AUTONOMOUS_REGION = {"新疆", "内蒙古", "西藏", "宁夏", "广西"};

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    /**
     * 提供与电话号码相关的运营商信息
     */
    private static final PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();

    /**
     * 提供与电话号码有关的地理信息
     */
    private static final PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    /**
     * 中国大陆区区号
     */
    private final static int COUNTRY_CODE_CHINA = 86;

    /**
     * 判断手机号是否有效（中国）
     *
     * @param phoneNumber 手机号码
     * @return true-有效 false-无效
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        return checkPhoneNumber(COUNTRY_CODE_CHINA, phoneNumber);
    }

    /**
     * 判断手机号是否有效（国际）
     *
     * @param countryCode 国家代码
     * @param phoneNumber 号码
     * @return true-有效 false-无效
     */
    public static boolean checkPhoneNumber(int countryCode, String phoneNumber) {
        long phone = Long.parseLong(phoneNumber);
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(countryCode);
        pn.setNationalNumber(phone);
        return phoneNumberUtil.isValidNumber(pn);
    }

    /**
     * 根据手机号 判断手机运营商
     *
     * @param phoneNumber 手机号码
     * @return 如：广东省广州市移动
     */
    public static String getCarrier(String phoneNumber) {
        long phone = Long.parseLong(phoneNumber);
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(COUNTRY_CODE_CHINA);
        pn.setNationalNumber(phone);
        // 返回结果只有英文，自己转成成中文
        String carrierEn = carrierMapper.getNameForNumber(pn, Locale.ENGLISH);
        String carrierZh = "";
        switch (carrierEn) {
            case "China Mobile":
                carrierZh += "移动";
                break;
            case "China Unicom":
                carrierZh += "联通";
                break;
            case "China Telecom":
                carrierZh += "电信";
                break;
            default:
                break;
        }
        return carrierZh;
    }

    /**
     * 根据手机号 获取手机归属地
     *
     * @param phoneNumber 手机号码
     * @return 如：广东省广州市
     */
    public static String getGeo(String phoneNumber) {
        return getGeo(COUNTRY_CODE_CHINA, phoneNumber);
    }

    /**
     * 根据手机号 获取手机归属地
     *
     * @param phoneNumber 手机号码
     * @return 如：广东省广州市
     */
    public static String getGeo(int code, String phoneNumber) {
        long phone = Long.parseLong(phoneNumber);
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(code);
        pn.setNationalNumber(phone);
        return geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
    }

    /**
     * 输入手机号码，返回手机号信息
     *
     * @param phoneNumber 带国家代码的手机号，如：
     *                    +8613111111111（中国）
     *                    +8109099956017（日本）
     * @return {@link PhoneModel}
     */
    public static PhoneModel getPhoneModelWithCountry(String phoneNumber) {
        if (!isValidNumber(phoneNumber)) {
            return null;
        }
        Phonenumber.PhoneNumber pn;
        try {
            pn = phoneNumberUtil.parse(phoneNumber, "CH");
        } catch (NumberParseException e) {
            return null;
        }
        int countryCode = pn.getCountryCode();
        long nationalNumber = pn.getNationalNumber();
        PhoneModel model = new PhoneModel();
        model.setCountryCode(countryCode);
        model.setCountryCodeStr(String.format("+%d", countryCode));
        model.setNationalNumber(nationalNumber);
        String geo = geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
        // 直辖市
        for (String val : MUNICIPALITY) {
            if (geo.equals(val)) {
                model.setProvinceName(val.replace("市", ""));
                model.setCityName(val);
                return model;
            }
        }
        // 自治区
        for (String val : AUTONOMOUS_REGION) {
            if (geo.startsWith(val)) {
                model.setProvinceName(val);
                model.setCityName(geo.replace(val, ""));
                return model;
            }
        }
        // 其它
        String[] splitArr = geo.split("省");
        if (splitArr.length == 2) {
            model.setProvinceName(splitArr[0]);
            model.setCityName(splitArr[1]);
            return model;
        }
        model.setCountryName(geo);
        return model;
    }

    /**
     * 判定手机号是否可用
     *
     * @param phoneNumber 带区号的号码，如：
     *                    +8613111111111（中国）
     *                    +8109099956017（日本）
     */
    public static boolean isValidNumber(String phoneNumber) {
        Phonenumber.PhoneNumber pn;
        try {
            pn = phoneNumberUtil.parse(phoneNumber, "CH");
        } catch (NumberParseException e) {
            return false;
        }
        return phoneNumberUtil.isValidNumber(pn);
    }

}
