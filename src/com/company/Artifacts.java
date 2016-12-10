package com.company;

import java.awt.*;

/**
 * Created by Oussama on 04/12/2016.
 */
public class Artifacts {
    int x, y;
    Color c;
    int size;
    int fonction;
    boolean taken;

    public Artifacts(int x, int y, Color c, int size, int fonction) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.size = size;
        this.fonction = fonction;
        taken = false;
    }
}
