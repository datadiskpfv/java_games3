package uk.co.datadisk.catandmouse;

import javax.swing.*;
import java.io.IOException;

public class Mouse extends MazeRunner {

    private static final String MOUSE_UP = "/images/mouseUp.gif";
    private static final String MOUSE_DOWN = "/images/mouseDown.gif";
    private static final String MOUSE_LEFT = "/images/mouseLeft.gif";
    private static final String MOUSE_RIGHT = "/images/mouseRight.gif";

    private static final int DEFAULT_SPEED = 10;

    public final MouseRunState STATE_RUN = new MouseRunState(this);
    public final MouseWaitState STATE_WAIT = new MouseWaitState(this);

    private GamePanel gamePanel;


    public Mouse(GamePanel gamePanel, Maze maze) {
        this.gamePanel = gamePanel;
        this.maze = maze;

        x = maze.getMouseX();
        y = maze.getMouseY();

        direction = DIRECTION_DOWN;
        changeX = 0;
        changeY = 1;
        speed = DEFAULT_SPEED;
        state = STATE_WAIT;

        try {
            setImage(DIRECTION_UP, MOUSE_UP);
            setImage(DIRECTION_DOWN, MOUSE_DOWN);
            setImage(DIRECTION_LEFT, MOUSE_LEFT);
            setImage(DIRECTION_RIGHT, MOUSE_RIGHT);
        } catch (IOException e){
            String message = "Could not read the mouse image file";
            JOptionPane.showMessageDialog(null, message);
            System.exit(5);
        }
    }

    public void run() {
        if(!wallInDirection(direction)) {
            x += changeX * speed;
            y += changeY * speed;
        }
    }

    public void stop() {
        changeX = 0;
        changeY = 0;
    }

    public void eatCheese() {
        maze.removeCheese(x, y);
        gamePanel.increaseScore();
    }

    public boolean foundCheese() {
        boolean foundCheese = false;
        if (maze.hasCheeseAt(x, y)){
            foundCheese = true;
        }
        return foundCheese;
    }
}