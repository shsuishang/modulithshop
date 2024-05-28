package com.suisung.shopsuite.common.components.poster.core;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SystemLoadFont {

    //    public static final String FONT_PATH = "static/font/simsun.ttc";
    private static final Logger logger = LoggerFactory.getLogger(SystemLoadFont.class);
    public static final String FONT_PATH = "static/font/SourceHanSansCN-Normal.ttf";

    /**
     * 本地读取方法
     *
     * @param path     文件路径
     * @param style    字体样式
     * @param fontSize 字体大小
     * @return
     */
    public static Font styleFont(String path, int style, float fontSize) {
        Assert.notNull(path);
        Font font = SystemLoadFont.loadStyleFont(path, style, fontSize);// 调用
        return font;
    }

    public static Font styleFont(int style, float fontSize) {
        InputStream fileToStream = getFileToStream(FONT_PATH);
        Font font = SystemLoadFont.loadStyleFont(fileToStream, style, fontSize);// 调用
        return font;
    }

    public static InputStream getFileToStream(String classPath) {
        Resource resource = new ClassPathResource(classPath);
        return resource.getStream();
    }

    /**
     * @param fontFileName 外部字体名
     * @param style        字体样式
     * @param fontSize     字体大小
     * @return
     */
    public static Font loadStyleFont(String fontFileName, int style, float fontSize) {
        try {
            File file = new File(fontFileName);
            FileInputStream in = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
            Font dynamicFontPt = dynamicFont.deriveFont(style, fontSize);
            in.close();
            return dynamicFontPt;
        } catch (Exception e) {//异常处理
            logger.error("字体样式处理异常！" + e.getMessage(), e);
            return new Font("宋体", Font.PLAIN, 20);
        }
    }

    public static Font loadStyleFont(InputStream in, int style, float fontSize) {
        try {
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
            Font dynamicFontPt = dynamicFont.deriveFont(style, fontSize);
            in.close();
            return dynamicFontPt;
        } catch (Exception e) {//异常处理
            logger.error("字体样式处理异常！" + e.getMessage(), e);
            return new Font("宋体", Font.PLAIN, 20);
        }
    }
}