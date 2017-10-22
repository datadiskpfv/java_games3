package uk.co.datadisk.fallingbricks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BricksPanel extends JPanel {
    private static final long serialVersionUID = -5695105888428537730L;

    private static final int ROWS = 15;
    private static final int COLS = 10;
    private static final int WIDTH = COLS * Brick.TILE_SIZE;
    private static final int HEIGHT = ROWS * Brick.TILE_SIZE;

    private SBrick brick;

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        return size;
    }

    public BricksPanel() {
        initGUI();
        start();
    }

    private void initGUI() {
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch(code) {
                    case KeyEvent.VK_LEFT:
                       moveLeft();
                       break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                    case KeyEvent.VK_Z:
                        rotateLeft();
                        break;
                    case KeyEvent.VK_X:
                        rotateRight();
                        break;
                }
            }
        });
    }

    private void rotateRight() {
        brick.rotateRight();
        if(isLegal()){
            repaint();
        } else {
            brick.rotateLeft();
        }
    }

    private void rotateLeft() {
        brick.rotateLeft();
        if(isLegal()){
            repaint();
        } else {
            brick.rotateRight();
        }
    }

    private void moveLeft() {
        brick.moveLeft();
        if(isLegal()) {
            repaint();
        } else {
            brick.moveRight();
        }
    }

    private void moveRight() {
        brick.moveRight();
        if(isLegal()){
            repaint();
        } else {
            brick.moveLeft();
        }
    }

    private void start() {
        pickABrick();
    }

    private void pickABrick() {
        int row  = 0;
        int col = COLS / 2;
        brick = new SBrick(row, col);
    }

    public void paintComponent(Graphics g){
        // background
        g.setColor(Color.BLACK);
        g.fillRect(0,0, WIDTH, HEIGHT);


        // fallen bricks


        // falling bricks
        if(brick != null){
            brick.draw(g);
        }
    }

    public boolean isLegal() {
        boolean legal = true;
        int row = brick.getRow();
        int col = brick.getColumn();
        int brickRows = brick.getNumberOfRows();
        int brickCols = brick.getNumberOfCols();

        // if beyond right or left edge of panel
        if(col < 0 || col + brickCols > COLS){
            legal = false;
        }

        // if beyond top or bottom edge of panel
        else if(row < 0 || row + brickRows > ROWS){
            legal = false;
        }

        return legal;
    }
}
