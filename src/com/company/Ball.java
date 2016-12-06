package com.company;

import java.awt.*;

/**
 * Created by Oussama on 02/12/2016.
 */
public class Ball {
    int x, y;
    Color c;
    int size;
    boolean reverseX, reverseY;

    public Ball(int x, int y, Color c, int size, boolean reverseX, boolean reverseY) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.size = size;
        this.reverseX = reverseX;
        this.reverseY = reverseY;
    }
}
