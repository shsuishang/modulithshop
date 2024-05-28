package com.suisung.shopsuite.common.components.poster;

import com.suisung.shopsuite.common.components.poster.annotation.PosterBackground;
import com.suisung.shopsuite.common.components.poster.annotation.PosterFontCss;
import com.suisung.shopsuite.common.components.poster.core.abst.AbstractDefaultPoster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.awt.image.BufferedImage;

/**
 * 入学通知书
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AdmissionNotice extends AbstractDefaultPoster {

    @PosterBackground(width = 749, height = 1164)
    private BufferedImage backgroundImage;

    @PosterFontCss(posterWidth = 749, position = {0, 600}, center = true, size = 40, color = {219, 131, 0})
    private String name;

    @PosterFontCss(posterWidth = 749, position = {0, 420}, center = true, size = 50, color = {175, 107, 8})
    private String text1;

    @PosterFontCss(posterWidth = 749, position = {0, 720}, center = true, size = 30, color = {219, 131, 0})
    private String text2;

    @PosterFontCss(posterWidth = 749, position = {0, 770}, center = true, size = 30, color = {219, 131, 0})
    private String text3;

    @PosterFontCss(position = {120, 900}, size = 25, color = {219, 131, 0})
    private String text4;

    @PosterFontCss(position = {120, 950}, size = 25, color = {219, 131, 0})
    private String text5;

    @PosterFontCss(position = {120, 1000}, size = 25, color = {219, 131, 0})
    private String text6;

    @Tolerate
    public AdmissionNotice() {
    }
}
