package uk.co.datadisk.babybird;

import uk.co.datadisk.mycommonmethods.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FlightPanel extends JPanel {

    private static final long serialVersionUID = -1177196582926473768L;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private static final int SEPERATION = 40;
    private static final Font BIG_FONT = new Font(Font.DIALOG, Font.BOLD, 30);
    private static final String THUD_SOUND = "/sounds/thud.wav";


    private BabyBird babybird;
    private Bird bird = new Bird(HEIGHT);

    Timer timer;
    private ArrayList<Wall> walls = new ArrayList<>();
    private int count = 0;
    FontMetrics fm;

    public FlightPanel(BabyBird babybird) {
        this.babybird = babybird;
        setFont(BIG_FONT);
        fm = getFontMetrics(BIG_FONT);

        setFocusable(true);
        requestFocusInWindow();

        // listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char key = e.getKeyChar();
                if(key == ' '){
                    bird.startFlapping();
                }
            }
        });

        // timer
        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timedAction();
            }
        });

        Wall wall = new Wall(fm);
        walls.add(wall);

        timer.start();
    }

    private void timedAction() {
        // move bird
        int changeY = bird.getChangeY();
        bird.move();

        int paintX = bird.getX();
        int paintY = bird.getY();
        if (changeY > 0){
            paintY -= changeY;
        }
        int paintWidth = bird.getWidth();
        int paintHeight = bird.getHeight() + Math.abs(changeY);
        repaint(paintX, paintY, paintWidth, paintHeight);

        // move walls
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            wall.move();
            paintX = wall.getX();
            paintY = wall.getY();
            paintWidth = wall.getWidth() - wall.getChangeX();
            paintHeight = HEIGHT;
            repaint(paintX, paintY, paintWidth, paintHeight);
            if(wall.isPastWindowEdge()){
                walls.remove(i);
                int points = wall.getPoints();
                babybird.addToScore(points);
            }
        }

        // check for collision
        Wall firstWall  = walls.get(0);
        Rectangle birdBounds = bird.getBounds();
        Rectangle topWallBounds = firstWall.getTopBounds();
        Rectangle bottomWallBounds = firstWall.getBottomBounds();

        if(birdBounds.intersects(topWallBounds) || birdBounds.intersects(bottomWallBounds)){
            nextLife();
        }

        // should another wall be added?
        count++;
        if(count > SEPERATION){
            Wall wall = new Wall(fm);
            walls.add(wall);
            count = 0;
        }
    }

    public Dimension getPreferredSize() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        return size;
    }

    public void paintComponent(Graphics g){
        // background
        g.setColor(Color.BLUE);
        g.fillRect(0,0,WIDTH,HEIGHT);

        // bird
        bird.draw(g);

        // walls
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            wall.draw(g);
        }
    }

    public Bird getBird() {
        return bird;
    }

    private void nextLife() {
        FileIO.playClip(this, THUD_SOUND);
        babybird.nextBird();

        count = 0;

        walls.clear();
        Wall wall = new Wall(fm);
        walls.add(wall);
        repaint();
    }

    public void restart() {
        count = 0;
        bird = new Bird(HEIGHT);
        walls.clear();
        Wall wall = new Wall(fm);
        walls.add(wall);
        repaint();
    }
}
