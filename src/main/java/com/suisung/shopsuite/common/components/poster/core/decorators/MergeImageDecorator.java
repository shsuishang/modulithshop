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
 * 多个图片上下合并 装饰器
 *
 * @author quaint
 * 21 February 2020
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MergeImageDecorator extends AbstractPosterDecorator {

    /**
     * 要绘制的图片
     */
    private List<BufferedImage> images;

    /**
     * 下次绘制需要增加的高度
     */
    private int nextAddHeight;

    public MergeImageDecorator(Poster poster) {
        super(poster);
    }

    @Builder(toBuilder = true)
    public MergeImageDecorator(Poster poster, int positionX, int positionY, int width, int height, List<BufferedImage> images) {
        super(poster, positionX, positionY, width, height);
        this.images = images;
    }

    @Override
    public BufferedImage draw(BufferedImage image) {
        // 绘制 被装饰之前的 图片
        BufferedImage draw = poster.draw(image);
        // 装饰, 绘制头像
        return margeImage(draw);
    }

    /**
     * 合并图片具体实现
     *
     * @param sourceImage sourceImage
     * @return image
     */
    private BufferedImage margeImage(BufferedImage sourceImage) {
        // 需要增加的 y 定位
        AtomicInteger addPositionY = new AtomicInteger();
        // 实现绘制图片
        Graphics2D g = sourceImage.createGraphics();
        images.forEach(image -> {
            g.drawImage(image, positionX, positionY + addPositionY.get(), width, image.getHeight() * width / image.getWidth(), null);
            addPositionY.addAndGet(image.getHeight() * width / image.getWidth());
        });
        g.dispose();
        // 返回下次绘制需要增加的高度, 本次所有图片高度 - 之前固定的高度
        nextAddHeight = addPositionY.get() - height;
        return sourceImage;
    }

}
