package uk.co.datadisk.catandmouse;

import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.security.Key;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 6880416228816810428L;

    private ScorePanel scorePanel;

    private int width = 640;
    private int height = 400;
    private Maze maze;
    private Mouse mouse;
    private Timer timer;
    private Cat cat;
    private int numberOfExtraMice;
    private BufferedImage mouseImage;
    private int mouseOffsetX;
    private int mouseOffsetY;

    public GamePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;

        maze = new Maze();
        width = maze.getWidth();
        height = maze.getHeight();

        initGUI();

        mouse = new Mouse(this, maze);
        cat = new Cat(mouse, maze);
        numberOfExtraMice = maze.getNumberOfExtraMice();
        mouseImage = mouse.getFirstImage();
        mouseOffsetX = mouse.getFirstOffsetX();
        mouseOffsetY = mouse.getFirstOffsetY();

        timer.start();
    }

    private void initGUI() {
        setFocusable(true);
        requestFocusInWindow();

        // listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int direction = Mouse.DIRECTION_NONE;
                int code = e.getKeyCode();
                switch(code) {
                    case KeyEvent.VK_UP:
                        direction = Mouse.DIRECTION_UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        direction = Mouse.DIRECTION_DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        direction = Mouse.DIRECTION_LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = Mouse.DIRECTION_RIGHT;
                        break;
                }
                if(direction != Mouse.DIRECTION_NONE){
                    mouse.turn(direction);
                    mouse.setState(mouse.STATE_RUN);
                    cat.setState(cat.STATE_HUNT);
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT){
                    mouse.setState(mouse.STATE_WAIT);
                    cat.setState(cat.STATE_WANDER);
                    repaint();
                }
            }
        });

        //timer
        timer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeAction();
            }
        });
    }

    private void timeAction() {
        mouse.move();
        cat.move();
        repaint();

        Rectangle catBounds = cat.getBounds();
        Rectangle mouseBounds = mouse.getBounds();
        if(catBounds.intersects(mouseBounds)){
            nextMouse();
        }
    }

    private void nextMouse() {
        timer.stop();
        numberOfExtraMice--;

        if(numberOfExtraMice >= 0){
            String message = "You got caught!";
            JOptionPane.showMessageDialog(this, message);
            mouse = new Mouse(this, maze);
            cat = new Cat(mouse, maze);
            timer.start();
        } else {
            String message = "No more mice";
            endGame(message);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(width, height);
        return size;
    }

    public void paintComponent(Graphics g){
        // background
        g.setColor(Color.GREEN);
        g.fillRect(0,0, width, height);

        // maze
        maze.draw(g);

        // mouse
        mouse.draw(g);

        // extra mice
        for (int i = 0; i < numberOfExtraMice; i++) {
            int x = maze.getExtraMouseX(i) + mouseOffsetX;
            int y = maze.getExtraMouseY(i) + mouseOffsetY;
            g.drawImage(mouseImage, x, y, null);
        }

        // cat
        cat.draw(g);
    }

    public void increaseScore() {
        scorePanel.addToScore(1);
        if (maze.getRemainingCheese() == 0){
            String message = "You got all the cheese!";
            endGame(message);
        }
    }

    private void endGame(String message) {
        timer.stop();
        repaint();

        message += " Do you want to play again?";
        int option = JOptionPane.showConfirmDialog(this, message, "Play Again?", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION){
            newGame();
        } else {
            System.exit(0);
        }
    }

    private void newGame() {
        maze.reset();
        scorePanel.reset();
        mouse = new Mouse(this, maze);
        numberOfExtraMice = maze.getNumberOfExtraMice();
        timer.start();
    }
}
