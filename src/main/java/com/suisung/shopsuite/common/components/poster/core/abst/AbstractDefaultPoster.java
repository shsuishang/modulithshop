package com.suisung.shopsuite.common.components.poster.core.abst;

import java.awt.image.BufferedImage;

/**
 * 默认空白的 poster
 *
 * @author quaint
 * 30 March 2020
 * @since 1.0
 */
public abstract class AbstractDefaultPoster implements Poster {

    @Override
    public BufferedImage draw(BufferedImage image) {
        return image;
    }

}
