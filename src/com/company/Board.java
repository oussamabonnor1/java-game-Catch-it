package com.company;

import java.awt.*;

/**
 * Created by Oussama on 03/12/2016.
 */
public class Board {
    int x, y;
    Color c;
    int width, height;
    int previousX;

    public Board(int x, int y, Color c, int width, int previousX) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.width = width;
        height = 20;
        this.previousX = previousX;
    }
}
