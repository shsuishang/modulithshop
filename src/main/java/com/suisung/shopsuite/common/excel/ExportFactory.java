package com.suisung.shopsuite.common.excel;


import com.suisung.shopsuite.account.model.vo.UserInfoVo;
import com.suisung.shopsuite.trade.model.vo.OrderVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExportFactory {
    private static final Log logger = LogFactory.getLog(ExportFactory.class);
    private static final Map<Integer, Class> exportMap = new ConcurrentHashMap();


    /**
     * 存放导出对象
     */
    static {
        exportMap.put(0, UserInfoVo.class);
        exportMap.put(1, OrderVo.class);
    }

    /**
     * 获取实例类
     *
     * @param classKey
     * @return
     */
    public static Class getExportFactory(Integer classKey) {
        try {
            return exportMap.get(classKey);
        } catch (Exception e) {
            logger.error("ExportFactory:", e);
        }
        return null;
    }

}
