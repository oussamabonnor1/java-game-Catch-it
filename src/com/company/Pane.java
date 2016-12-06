package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oussama on 02/12/2016.
 */
public class Pane extends JPanel {
    public int score;
    public int bestScore;
    public boolean movingRight;
    public boolean lose;
    public boolean pause;
    boolean add;
    boolean replay;
    Random r = new Random();

    public Ball ball1;
    public Board board;

    public ArrayList<Board> boards = new ArrayList<>();
    public ArrayList<Ball> balls = new ArrayList<>();

    Font f = new Font("Courier New", Font.BOLD, 30);
    Font f2 = new Font("Courier New", Font.BOLD, 15);

    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.ORANGE);
        g.fillRect(0, 650, getWidth(), getHeight());

        for (int i = 0; i < balls.size(); i++) {
            g.setColor(balls.get(i).c);
            g.fillOval(balls.get(i).x, balls.get(i).y, balls.get(i).size, balls.get(i).size);
            g.setColor(boards.get(0).c);
            g.fillRect(boards.get(0).x, boards.get(0).y, boards.get(0).width, boards.get(0).height);
            g.setColor(Color.BLACK);
            g.setFont(f2);
            g.drawString("arreter: ECHAPE", 20, 20);
            g.drawString("pause: SPACE", 680, 20);
        }

        if (pause) {
            g.setColor(Color.BLACK);
            g.drawString("PAUSE", 400, 230);
        }

        if (lose) {
            g.setColor(Color.BLACK);
            g.drawString("YOU LOST, TRY AGAIN?", 300, 230);
            g.drawString("YES: Press Y      NO: Press N", 300, 250);
        }

        g.setFont(f);

        if (bestScore > 0) {
            g.setColor(Color.BLUE);
            g.drawString("Best Score: " + bestScore, 550, 60);
        }

        g.setColor(Color.RED);
        g.drawString("Score: " + score, 20, 60);
    }

    public void start() {
        repaint();
        pause = false;
        lose = false;
        add = false;
        replay = false;
        movingRight = true;

        if (score > bestScore) bestScore = score;
        score = 0;

        ball1 = new Ball(r.nextInt(650), r.nextInt(300), Color.black, r.nextInt(50 - 20) + 20, false, false);
        board = new Board(350, 630, Color.black, 120, 350);

        replay = false;


        boards.add(board);
        balls.add(ball1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            if (!pause) {
                if (score % 5 == 0 && score != 0 && !add) {
                    addBall();
                }

                for (int i = 0; i < balls.size(); i++) {


                    //change direction of x
                    if (balls.get(i).x <= 0) {
                        balls.get(i).x = 1;
                        balls.get(i).reverseX = false;
                    }

                    if (balls.get(i).x >= getWidth() - balls.get(i).size) {
                        balls.get(i).x = getWidth() - balls.get(i).size - 1;
                        balls.get(i).reverseX = true;
                    }

                    if (balls.get(i).y <= 0) {
                        balls.get(i).y = 1;
                        balls.get(i).reverseY = false;
                    }

                    if (balls.get(i).y + balls.get(i).size >= boards.get(0).y && balls.get(i).y + balls.get(i).size <= boards.get(0).y + 20) {
                        if ((balls.get(i).x >= boards.get(0).x && balls.get(i).x <= boards.get(0).x + boards.get(0).width) || (balls.get(i).x + balls.get(i).size >= boards.get(0).x && balls.get(i).x + balls.get(i).size <= boards.get(0).x + boards.get(0).width)) {
                            if (!balls.get(i).reverseX) {
                                if (!movingRight) {
                                    balls.get(i).reverseX = true;
                                }
                            } else {
                                if (movingRight) {
                                    balls.get(i).reverseX = false;
                                }
                            }
                            balls.get(i).y -= 1;
                            balls.get(i).reverseY = true;
                            score++;
                            repaint();
                            add = false;
                        }
                    }

                    if (!balls.get(i).reverseX) balls.get(i).x += 3 + (score / 3);
                    else balls.get(i).x -= 3 + (score / 3);
                    if (!balls.get(i).reverseY) balls.get(i).y += 3 + (score / 3);
                    else balls.get(i).y -= 3 + (score / 3);


                    if (balls.get(i).y >= getHeight() - balls.get(i).size) {
                        balls.remove(i);
                        if (balls.size() == 0) lose = true;
                    }
                }

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                repaint();

                if (replay) start();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addBall() {
        add = true;
        ball1 = new Ball(r.nextInt(700), r.nextInt(100), Color.BLACK, r.nextInt(50 - 20) + 20, r.nextBoolean(), r.nextBoolean());
        balls.add(ball1);
    }
}
