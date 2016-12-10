package com.company;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Oussama on 02/12/2016.
 */
public class Frame extends JFrame implements KeyListener, MouseMotionListener {

    Pane pane = new Pane();

    public Frame() {
        setTitle("Frame");
        setLocation(520, 120);
        this.setResizable(false);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setSize(800, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        pane.setLayout(null);
        setContentPane(pane);
        pane.start();
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (e.getX() <= this.getWidth() - 120 && e.getX() >= 0 && !pane.pause) {
            for (int i = 0; i < pane.boards.size(); i++) {

                //wich diection are our boards going
                if (pane.boards.get(0).previousX < e.getX()) pane.movingRight = true;
                else if (pane.boards.get(0).previousX > e.getX()) pane.movingRight = false;
                pane.boards.get(0).previousX = e.getX();
                pane.boards.get(0).x = e.getX();
                System.out.println(e.getX());
                pane.repaint();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (pane.lose) {
            if (e.getKeyCode() == KeyEvent.VK_Y) {
                pane.replay = true;
            } else if (e.getKeyCode() == KeyEvent.VK_N) {
                System.exit(0);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pane.pause = !pane.pause;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
