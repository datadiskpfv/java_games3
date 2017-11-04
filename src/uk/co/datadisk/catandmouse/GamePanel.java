package uk.co.datadisk.catandmouse;

import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 6880416228816810428L;

    private ScorePanel scorePanel;

    private int width = 640;
    private int height = 400;
    private Maze maze;
    private Mouse mouse;
    private Timer timer;

    public GamePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;

        maze = new Maze();
        width = maze.getWidth();
        height = maze.getHeight();

        initGUI();

        mouse = new Mouse(this, maze);

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
        repaint();
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
                    repaint();
                }
            }

            public void KeyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT){
                    mouse.setState(mouse.STATE_WAIT);
                    repaint();
                }
            }
        });

        // timer
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(width, height);
        return size;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.GREEN);
        g.drawRect(0,0, width, height);

        // maze
        maze.draw(g);

        // mouse
        mouse.draw(g);

        // extra mice

        // cat
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
        timer.start();
    }
}
