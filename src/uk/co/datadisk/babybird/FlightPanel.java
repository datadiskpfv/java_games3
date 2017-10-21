package uk.co.datadisk.babybird;

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

    private BabyBird babybird;
    private Bird bird = new Bird(HEIGHT);

    Timer timer;
    private ArrayList<Wall> walls = new ArrayList<>();

    public FlightPanel(BabyBird babybird) {
        this.babybird = babybird;

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

        Wall wall = new Wall();
        walls.add(wall);

        timer.start();
    }

    private void timedAction() {
        // move bird
        bird.move();

        // move walls

        // check for collision

        // should another wall be added?

        // repaint
        repaint();
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
}
