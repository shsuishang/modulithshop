package com.suisung.shopsuite.common.components.poster.core.decorators;

import com.suisung.shopsuite.common.components.poster.core.abst.AbstractPosterDecorator;
import com.suisung.shopsuite.common.components.poster.core.abst.Poster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 标签文本装饰, 一行多个并排标签.
 *
 * @author quaint
 * 21 February 2020
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LabelTextDecorator extends AbstractPosterDecorator {

    /**
     * 字体
     */
    private Font font = new Font(null);

    /**
     * 字体样式
     */
    private int fontStyle = Font.PLAIN;

    /**
     * 字体大小
     */
    private int fontSize = 16;

    /**
     * 字体颜色
     */
    private Color color = new Color(255, 255, 255);

    /**
     * 内容
     */
    private List<String> contentList;

    /**
     * 标签背景
     */
    private BufferedImage tipBg;

    /**
     * 背景高度
     */
    private int bgHeight;

    /**
     * 标签间距
     */
    private int tipMargin;


    public LabelTextDecorator(Poster poster) {
        super(poster);
    }

    @Builder(toBuilder = true)
    public LabelTextDecorator(Poster poster, int positionX, int positionY, int width, int height, Font font, int fontSize, Color color,
                              List<String> contentList, int fontStyle, BufferedImage tipBg, int tipMargin, int bgHeight) {
        super(poster, positionX, positionY, width, height);
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
        this.contentList = contentList;
        this.fontStyle = fontStyle;
        this.tipBg = tipBg;
        this.tipMargin = tipMargin;
        this.bgHeight = bgHeight;
    }

    @Override
    public BufferedImage draw(BufferedImage image) {
        // 绘制 被装饰之前的 图片
        BufferedImage draw = poster.draw(image);
        // 装饰, 绘制文本
        return drawText(draw);
    }

    /**
     * 绘制标签文本具体实现
     *
     * @param image image
     * @return image
     */
    private BufferedImage drawText(BufferedImage image) {

        if (contentList == null || contentList.size() == 0 || tipBg == null) {
            return image;
        }

        // 实现绘制文本
        font = font.deriveFont(fontStyle, fontSize);
        Graphics2D g = image.createGraphics();
        // 设置文字抗锯齿算法
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics(font.deriveFont(fontStyle, fontSize));
        AtomicInteger xAdd = new AtomicInteger(0);
        contentList.forEach(content -> {
            int strW = fm.stringWidth(content);
            int bgH = bgHeight == 0 ? tipBg.getHeight() : bgHeight;
            g.drawImage(tipBg, positionX + xAdd.get(), positionY, strW + fontSize, bgH, null);
            g.drawString(content, positionX + xAdd.get() + fontSize / 2, positionY + fontSize + (bgH - fontSize) * 2 / 5);
            // 标签间距 增加
            xAdd.addAndGet(strW + tipMargin);
        });

        g.dispose();
        return image;
    }

}
