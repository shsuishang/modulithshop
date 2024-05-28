package com.suisung.shopsuite.common.components.poster.core.decorators;

import com.suisung.shopsuite.common.components.poster.core.abst.AbstractPosterDecorator;
import com.suisung.shopsuite.common.components.poster.core.abst.Poster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 背景 (绘制的模板,无需成为别人的装饰)
 *
 * @author quaint
 * 21 February 2020
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BackgroundDecorator extends AbstractPosterDecorator {

    /**
     * 背景图
     */
    private BufferedImage bgImage;

    public BackgroundDecorator() {
        super(null);
    }

    @Builder(toBuilder = true)
    public BackgroundDecorator(Poster poster, int positionX, int positionY, int width, int height, BufferedImage bgImage) {
        super(poster, positionX, positionY, width, height);
        this.bgImage = bgImage;
    }


    @Override
    public BufferedImage draw(BufferedImage image) {
        // 装饰, 绘制背景
        return drawBackground(bgImage);
    }

    /**
     * 绘制背景具体实现
     *
     * @param image image
     * @return image
     */
    private BufferedImage drawBackground(BufferedImage image) {

        // 如果宽度没变化, 或者没设置
        if (width == 0 || height == 0) {
            return image;
        }
        // 调整背景宽度
        if (width != image.getWidth() || height != image.getHeight()) {
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            return newImage;
        }
        // 绘制背景
        return image;
    }
}
