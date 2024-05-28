package com.suisung.shopsuite.common.components.poster.core.decorators;

import com.suisung.shopsuite.common.components.poster.core.abst.AbstractPosterDecorator;
import com.suisung.shopsuite.common.components.poster.core.abst.Poster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * 图片装饰
 *
 * @author quaint
 * 21 February 2020
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageDecorator extends AbstractPosterDecorator {

    /**
     * 要绘制的图片
     */
    private BufferedImage image;

    /**
     * 是否修改为圆形
     */
    private boolean circle;

    public ImageDecorator(Poster poster) {
        super(poster);
    }

    @Builder(toBuilder = true)
    public ImageDecorator(Poster poster, int positionX, int positionY, int width, int height, BufferedImage image, boolean circle) {
        super(poster, positionX, positionY, width, height);
        this.image = image;
        this.circle = circle;
    }

    @Override
    public BufferedImage draw(BufferedImage image) {
        // 绘制 被装饰之前的 图片
        BufferedImage draw = poster.draw(image);
        // 装饰, 绘制头像
        return drawImage(draw);
    }

    /**
     * 绘制图片具体实现
     *
     * @param sourceImage sourceImage
     * @return image
     */
    private BufferedImage drawImage(BufferedImage sourceImage) {

        if (image == null) {
            return sourceImage;
        }

        // 如果没有指定 宽度, 使用默认宽度
        if (width == 0 || height == 0) {
            width = image.getWidth();
            height = image.getHeight();
        }

        // 实现绘制图片
        Graphics2D g = sourceImage.createGraphics();

        if (circle) {
            // 设置形状
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, image.getWidth(), image.getHeight());

            BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2 = output.createGraphics();
            output = g2.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
            g2 = output.createGraphics();

            // 将背景设置为透明。如果注释该段代码，默认背景为白色.也可通过g2.setPaint(paint) 设置背景色
            g2.setComposite(AlphaComposite.Clear);
            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g2.setClip(shape);
            // 使用 setRenderingHint 设置抗锯齿
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
            image = output;

        }
        g.drawImage(image, positionX, positionY, width, height, null);
        g.dispose();

        return sourceImage;
    }

}
