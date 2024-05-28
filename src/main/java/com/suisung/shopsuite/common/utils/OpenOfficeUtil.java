package com.suisung.shopsuite.common.utils;

import cn.hutool.core.util.StrUtil;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;

/**
 * OpenOfficeUtil
 *
 * @author Xinze
 * @since 2018-12-14 08:38:19
 */
public class OpenOfficeUtil {
    private static final Logger logger = LoggerFactory.getLogger(OpenOfficeUtil.class);

    // 支持转换pdf的文件后缀列表
    private static final String[] CAN_CONVERTER_FILES = new String[]{
            "doc", "docx", "xls", "xlsx", "ppt", "pptx"
    };

    /**
     * 文件转pdf
     *
     * @param filePath   源文件路径
     * @param outDir     输出目录
     * @param officeHome OpenOffice安装路径
     * @return File
     */
    public static File converterToPDF(String filePath, String outDir, String officeHome) {
        return converterToPDF(filePath, outDir, officeHome, true);
    }

    /**
     * 文件转pdf
     *
     * @param filePath   源文件路径
     * @param outDir     输出目录
     * @param officeHome OpenOffice安装路径
     * @param cache      是否使用上次转换过的文件
     * @return File
     */
    public static File converterToPDF(String filePath, String outDir, String officeHome, boolean cache) {
        if (StrUtil.isBlank(filePath)) {
            return null;
        }
        File srcFile = new File(filePath);
        if (!srcFile.exists()) {
            return null;
        }
        // 是否转换过
        String outPath = Base64.getEncoder().encodeToString(filePath.getBytes())
                .replace("/", "-").replace("+", "-");
        File outFile = new File(outDir, outPath + ".pdf");
        if (cache && outFile.exists()) {
            return outFile;
        }
        // 转换
        OfficeManager officeManager = null;
        try {
            officeManager = getOfficeManager(officeHome);
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            return converterFile(srcFile, outFile, converter);
        } catch (Exception e) {
            logger.error("文件转pdf", e);
        } finally {
            if (officeManager != null) {
                officeManager.stop();
            }
        }
        return null;
    }

    /**
     * 转换文件
     *
     * @param inFile    源文件
     * @param outFile   输出文件
     * @param converter OfficeDocumentConverter
     * @return File
     */
    public static File converterFile(File inFile, File outFile, OfficeDocumentConverter converter) {
        if (!outFile.getParentFile().exists()) {
            if (!outFile.getParentFile().mkdirs()) {
                return outFile;
            }
        }
        converter.convert(inFile, outFile);
        return outFile;
    }

    /**
     * 判断文件后缀是否可以转换pdf
     *
     * @param path 文件路径
     * @return boolean
     */
    public static boolean canConverter(String path) {
        try {
            String suffix = path.substring(path.lastIndexOf(".") + 1);
            return Arrays.asList(CAN_CONVERTER_FILES).contains(suffix);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 连接并启动OpenOffice
     *
     * @param officeHome OpenOffice安装路径
     * @return OfficeManager
     */
    public static OfficeManager getOfficeManager(String officeHome) {
        if (officeHome == null || officeHome.trim().isEmpty()) return null;
        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
        config.setOfficeHome(officeHome);  // 设置OpenOffice安装目录
        OfficeManager officeManager = config.buildOfficeManager();
        officeManager.start();  // 启动OpenOffice服务
        return officeManager;
    }

}
