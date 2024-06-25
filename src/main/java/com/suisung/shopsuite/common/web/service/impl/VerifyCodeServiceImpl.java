package com.suisung.shopsuite.common.web.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.suisung.shopsuite.common.consts.ConstantVerifyCode;
import com.suisung.shopsuite.common.web.service.VerifyCodeService;
import com.suisung.shopsuite.core.consts.ConstantRedis;
import com.suisung.shopsuite.core.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private RedisService redisService;

    private static final String kerPre = "_msg_|";

    /**
     * 校验验证码
     *
     * @param verifyKey
     * @param verifyCode
     */
    public boolean checkVerifyCode(String verifyKey, String verifyCode) {
        if (StrUtil.isEmpty(verifyKey)) {
            return false;
        }

        String key = ConstantVerifyCode.KEY_PREFIX + verifyKey;

        //尝试次数
        String timesKey = ConstantVerifyCode.KEY_PREFIX + verifyKey + ConstantRedis.SEPARATOR + "times";

        Object o = redisService.get(key);
        String code = Convert.toStr(o);

        if (StrUtil.isNotBlank(verifyCode) && StrUtil.isNotBlank(code) && ObjectUtil.equal(verifyCode.toLowerCase(), code.toLowerCase())) {
            // 删除 redis 验证码
            //redisService.del(key);

            //
            if (redisService.hasKey(timesKey)) {
                redisService.del(timesKey);
            }

            return true;
        }

        //使用次数
        Integer times = 0;

        if (redisService.hasKey(timesKey)) {
            times = Convert.toInt(redisService.get(timesKey));
        }

        //尝试十次，失效
        if (times > 10) {
            redisService.del(key);

            if (redisService.hasKey(timesKey)) {
                redisService.del(timesKey);
            }
        } else {
            times++;

            redisService.set(timesKey, ConstantVerifyCode.TIME, ConstantVerifyCode.TIME);
        }

        return false;
    }

    public boolean setVerifyCode(String verifyKey, String verifyCode) {
        redisService.set(ConstantVerifyCode.KEY_PREFIX + verifyKey, verifyCode, ConstantVerifyCode.TIME);

        return true;
    }

    public String getVerifyCode(String verifyKey) {
        String code = Convert.toStr(redisService.get(ConstantVerifyCode.KEY_PREFIX + verifyKey));

        return code;
    }
}
