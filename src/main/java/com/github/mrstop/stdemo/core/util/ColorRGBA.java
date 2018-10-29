package com.github.mrstop.stdemo.core.util;

public class ColorRGBA {
    public final short red;
    public final short green;
    public final short blue;
    public final short alpha;

    public ColorRGBA() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.alpha = 0;
    }

    public ColorRGBA(short red, short green, short blue, short alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
}
