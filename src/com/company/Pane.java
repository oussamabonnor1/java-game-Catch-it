package com.company;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.sql.Time;
import java.util.*;

/**
 * Created by Oussama on 02/12/2016.
 */
public class Pane extends JPanel {
    //Game Variables
    public int score;
    public int bestScore;
    public int waitTime;
    public long currentTime;

    public boolean movingRight;
    public boolean lose;
    public boolean pause;
    boolean add;
    boolean replay;
    boolean starting;

    Random r = new Random();

    public Ball ball1;
    public Board board;
    public Artifacts artifact;

    public ArrayList<Board> boards = new ArrayList<>();
    public ArrayList<Ball> balls = new ArrayList<>();
    public ArrayList<Artifacts> artifacts = new ArrayList<>();

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
        }

        for (int j = 0; j < artifacts.size(); j++) {
            g.setColor(artifacts.get(j).c);
            g.fillRect(artifacts.get(j).x, artifacts.get(j).y, artifacts.get(j).size, artifacts.get(j).size);
        }

        if (pause) {
            g.setFont(f);
            g.setColor(Color.BLACK);
            g.drawString("PAUSE", getWidth() / 2 - 30, 230);
        }

        if (lose) {
            g.setColor(Color.BLACK);
            g.setFont(f);
            g.drawString("YOU LOST, TRY AGAIN?", 230, 230);
            g.drawString("YES: Press Y      NO: Press N", 150, 250);
        }

        if(starting){
            g.setColor(Color.BLUE);
            g.setFont(f);
            g.drawString("BLUE: Good As New", 30, 230);
            g.setColor(Color.RED);
            g.drawString("READ: Good As Dead", 450, 230);
        }

        g.setFont(f);

        if (bestScore > 0) {
            g.setColor(Color.BLUE);
            g.drawString("Best Score: " + bestScore, 550, 60);
        }

        g.setColor(Color.RED);
        g.drawString("Score: " + score, 20, 60);
        g.setColor(Color.BLACK);
        g.setFont(f2);
        g.drawString("arreter: ECHAPE", 20, 20);
        g.drawString("pause: SPACE", 680, 20);
        g.setColor(boards.get(0).c);
        g.fillRect(boards.get(0).x, boards.get(0).y, boards.get(0).width, boards.get(0).height);
    }

    public void start() {

        repaint();
        pause = false;
        lose = false;
        add = false;
        replay = false;
        movingRight = true;
        starting = true;

        if (score > bestScore) bestScore = score;
        score = 0;

        ball1 = new Ball(r.nextInt(650), r.nextInt(300), Color.black, r.nextInt(50 - 20) + 20, false, false);
        board = new Board(350, 630, Color.black, 120, 350);
        artifact = new Artifacts(r.nextInt(650), 0, Color.RED, r.nextInt(20) + 20, r.nextInt(2));

        replay = false;

        boards.add(board);
        balls.add(ball1);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        starting=false;

        addArtifact();

        while (true) {
            if (!pause) {
                if (waitTime == (int) (System.currentTimeMillis() / 1000 - currentTime)) {
                    artifacts.add(artifact);
                    addArtifact();
                }

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

                    if (balls.get(i).y + balls.get(i).size >= boards.get(0).y && balls.get(i).y + balls.get(i).size <= boards.get(0).y + boards.get(0).height) {
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

                for (int j = 0; j < artifacts.size(); j++) {
                    if (artifacts.get(j).y <= getHeight()) {
                        artifacts.get(j).y += 3;
                    }
                    if (artifacts.get(j).y + artifacts.get(j).size >= boards.get(0).y && artifacts.get(j).y + artifacts.get(j).size <= boards.get(0).y + boards.get(0).height) {
                        if (artifacts.get(j).x >= boards.get(0).x && artifacts.get(j).x <= boards.get(0).x + boards.get(0).width && artifacts.get(j).x + artifacts.get(j).size >= boards.get(0).x && artifacts.get(j).x + artifacts.get(j).size <= boards.get(0).x + boards.get(0).width) {
                            Fontion(j);
                        }
                    }
                    if (artifacts.get(j).y > getHeight() || artifacts.get(j).taken) {
                        artifacts.remove(j);
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
                    Thread.sleep(100);
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

    public void Fontion(int j) {
        switch (artifacts.get(j).fonction) {
            case 0:
                boards.get(0).width = 240;
                break;
            case 1:
                boards.get(0).width = 60;
                break;
        }
        artifacts.get(j).taken = true;
    }

    public void addArtifact() {
        currentTime = System.currentTimeMillis() / 1000;
        waitTime = r.nextInt(5) + 5;
        artifact = new Artifacts(r.nextInt(650), 0, null, r.nextInt(20) + 20, r.nextInt(2));
        if (artifact.fonction == 1) {
            artifact.c = Color.RED;
        } else {
            artifact.c = Color.blue;
        }
    }
}
